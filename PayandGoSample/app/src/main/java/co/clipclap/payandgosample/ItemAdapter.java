package co.clipclap.payandgosample;

import android.content.Context;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by josedavidmantilla on 12/7/15.
 */
public class ItemAdapter  extends ArrayAdapter<Item> {


    public ItemAdapter(Context context, List<Item> bankInstitutionses) {
        super(context, R.layout.item, bankInstitutionses);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ItemViewHolder holder;

        if (convertView == null) {

            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item, parent, false);

            //
            holder = new ItemViewHolder();
            holder.count = (EditText) convertView.findViewById(R.id.itemCount);
            holder.name = (EditText) convertView.findViewById(R.id.itemName);
            holder.value = (EditText) convertView.findViewById(R.id.itemValue);
            //
            convertView.setTag(holder);

        } else {
            holder = (ItemViewHolder) convertView.getTag();
        }
        holder.populate(getItem(position));

        holder.value.setId(position);
        holder.value.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    final int position = v.getId();
                    final EditText caption = (EditText) v;
                    try {
                        getItem(position).value = Integer.parseInt(caption.getText().toString());
                    }catch (Exception e){
                        e.printStackTrace();
                        getItem(position).value = 0;
                    }
                }
            }
        });
        holder.count.setId(position);
        holder.count.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    final int position = v.getId();
                    final EditText caption = (EditText) v;
                    try {
                        getItem(position).count = Integer.parseInt(caption.getText().toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                        getItem(position).count = 0;
                    }
                }
            }
        });
        holder.name.setId(position);
        holder.name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    final int position = v.getId();
                    final EditText caption = (EditText) v;
                    try {
                        getItem(position).name = (caption.getText().toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                        getItem(position).name = "";
                    }
                }
            }
        });


        return convertView;
    }

    static class ItemViewHolder {
        public EditText value;
        public EditText name;
        public EditText count;

        void populate(final Item item) {
            if(item.value!=0)
            value.setText(item.value + "");
            else
            value.setText( "");
            name.setText(item.name);
            if(item.count!=0)
            count.setText(item.count + "");
            else
            count.setText( "");



        }

    }

}
