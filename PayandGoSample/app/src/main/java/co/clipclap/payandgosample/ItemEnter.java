package co.clipclap.payandgosample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;

import droidbox.util.DialogUtils;
import droidbox.util.FieldUtils;
import sdk.clipclap.clipclapcharge.CCBilleteraPayment;
import sdk.clipclap.clipclapcharge.PayAndGo;
import sdk.clipclap.clipclapcharge.SaveTokenListener;

public class ItemEnter extends AppCompatActivity {
    String data="";
    public ItemEnter self;
    ArrayList<Item> items= new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        self= this;

        ListView listView = (ListView)findViewById(R.id.listView);
        final ItemAdapter itemAdapter = new ItemAdapter(this,items);
        listView.setAdapter(itemAdapter);

        itemAdapter.notifyDataSetChanged();
        Button addButton = (Button)findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                items.add(new Item());
                itemAdapter.notifyDataSetChanged();
            }
        });

        Intent intent = getIntent();
        if (intent != null && intent.getData() != null) {
            data= intent.getDataString();
            String cadena = "no entro";
            if (data.contains("ok")) {
                cadena = "pagada";
            } else {
                if (data.contains("cancel")) {
                    cadena = "rechazada";
                } else {
                    if (data.contains("&message"))
                        cadena = data.split("&message")[1];
                }
            }
            Toast.makeText(self, cadena, Toast.LENGTH_LONG).show();

        }






        final PayAndGo button = (PayAndGo)findViewById(R.id.button);



        button.setSaveTokenListener(new SaveTokenListener() {
            @Override
            public void saveToken(String token) {
                //TODO

                Log.i("token",token);


            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {


                    //Create a new Object
                    CCBilleteraPayment ccBilleteraPayment = new CCBilleteraPayment("pKFe1P2iYw6z73srBDBx");

                    PayAndGo.type = PayAndGo.DEVELOPMENT;




                    for (Item item:items){
                        //Get info from UI
                        int value = item.value;
                        String name = item.name;
                        int count = item.count;


                        //Add data into the model (you can add many items) CCBilleteraPayment Contains the available taxes
                        ccBilleteraPayment.addItem(name, count, value, CCBilleteraPayment.IVA_REGULAR_16_);

                    }

                    //Add Total, custom tax and tip
                    EditText total= (EditText)findViewById(R.id.totalValue);
                    if(!FieldUtils.isEmpty(total.getText().toString())){
                        int totalV=Integer.parseInt(total.getText().toString());
                        int tax=0;
                        int tip=0;
                        String description="";
                        total= (EditText)findViewById(R.id.taxValue);
                        if(!FieldUtils.isEmpty(total.getText().toString())){tax=Integer.parseInt(total.getText().toString());}
                        total= (EditText)findViewById(R.id.tipValue);
                        if(!FieldUtils.isEmpty(total.getText().toString())){tip=Integer.parseInt(total.getText().toString());}
                        total= (EditText)findViewById(R.id.description);
                        if(!FieldUtils.isEmpty(total.getText().toString())){description=total.getText().toString();}
                        ccBilleteraPayment.addTotal(description,totalV,tax,tip);
                    }


                    PayAndGo.jsonObject = ccBilleteraPayment.getJSON();



                    PayAndGo.urlCallback = "PayAndGoSample://mydeeplink";

                } catch (Exception e) {
                    DialogUtils.showMessageDialog(self, "Error", "Válide Datos");
                    e.printStackTrace();
                }
            }
        });

    }


}
