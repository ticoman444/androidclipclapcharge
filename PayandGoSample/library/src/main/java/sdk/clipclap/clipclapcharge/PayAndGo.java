package sdk.clipclap.clipclapcharge;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import droidbox.util.DialogUtils;
import sdk.clipclap.payandgo.R;

/**
 * Created by josedavidmantilla on 12/3/15.
 */
public class PayAndGo extends Button{


    public static String secretKey="MIIwwHc8ksK8AfwUcWJC";
    public static final int  DEVELOPMENT=1;
    public static  final int PRODUCTION=2;
    public static int img= R.mipmap.ic_launcher;
    public static JSONObject jsonObject;
    public static String failureMessage;
    public static String urlCallback;
    public static String buttonColor;
    public static String buttonTextColor;
    public static String buttonText;
    public static String textMessage;
    public static String backGroundColor;
    public static String textMessageColor;
    public static int type;
    private  OnClickListener onClickListener;
    private   Context ctx;
    private SaveTokenListener mSaveTokenListener;

    public static ProgressDialog pd;
    public PayAndGo(Context context) {
        super(context);
        ctx=context;
        super.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(v);
               click();
            }
        });

    }

public void setSaveTokenListener(SaveTokenListener saveTokenListener){
    mSaveTokenListener=saveTokenListener;
}

    public static void resetDialog(){
        try {
            if (pd != null && pd.isShowing()) {
                pd.dismiss();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public static JSONObject getJsonObjectFromMap(Map params) throws JSONException {

        //all the passed parameters from the post request
        //iterator used to loop through all the parameters
        //passed in the post request
        Iterator iter = params.entrySet().iterator();

        //Stores JSON
        JSONObject holder = new JSONObject();

        //using the earlier example your first entry would get email
        //and the inner while would get the value which would be 'foo@bar.com'
        //{ fan: { email : 'foo@bar.com' } }

        //While there is another entry

        while (iter.hasNext())
        {
            Map.Entry pairs = (Map.Entry)iter.next();
            holder.put((String)pairs.getKey(), pairs.getValue());
        }

        //puts email and 'foo@bar.com'  together in map


        return holder;
    }
    private void click(){
        resetDialog();
        pd= DialogUtils.buildProgress(ctx, "Enviando datos");
        pd.show();
        Map<String, String> params= new HashMap<String, String>();

         String url= (type==DEVELOPMENT)?"https://clipclap.co/Development/gatewayClipClap/sdk/clipclap.php":"https://clipclap.co/Production/gatewayClipClap/sdk/clipclap.php";
    final String urlDeep=(type==DEVELOPMENT)?"ClipClapBilleteraD://?type=ClipClapWeb&token=":"ClipClapBilletera://?type=ClipClapWeb&token=";
        try {
            HttpService.post3(ctx,url
                    ,
                   jsonObject, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            resetDialog();
                            Intent intent = new Intent(ctx,PayClipclapActivity.class);
                            try {

                                mSaveTokenListener.saveToken(response.getString("token").toString());
                                intent.putExtra(PayClipclapActivity.URL, urlDeep + response.getString("token").toString() + "&callbackurl="+urlCallback);
                                intent.putExtra(PayClipclapActivity.RIMG, img);
                                if(buttonColor!=null){intent.putExtra(PayClipclapActivity.BUTTONCOLOR,buttonColor);}
                                if(buttonTextColor!=null){intent.putExtra(PayClipclapActivity.TEXTBUTTONCOLOR, textMessageColor);}
                                if(buttonText!=null){intent.putExtra(PayClipclapActivity.TEXTBUTTON, buttonText);}
                                if(textMessage!=null){intent.putExtra(PayClipclapActivity.TEXTMESSAGE, textMessage);}
                                if(textMessageColor!=null){intent.putExtra(PayClipclapActivity.TEXTMESSAGECOLOR, textMessageColor);}
                                if(backGroundColor!=null){intent.putExtra(PayClipclapActivity.BACKGROUNDCOLOR, backGroundColor);}


                                ctx.startActivity(intent);
                            }catch (Exception e){
                                e.printStackTrace();
                                if(failureMessage==null || failureMessage.equals("")){
                                    failureMessage=e.getMessage();
                                }
                                DialogUtils.showMessageDialog(ctx, "Error", failureMessage);
                               // Log.e("ERROR", e.getMessage());
                            }

                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, String responseString) {
                            //      System.err.println("RESPONSE" + responseString);
                            resetDialog();
                            Intent intent = new Intent(ctx,PayClipclapActivity.class);
                            intent.getExtras().putString(PayClipclapActivity.URL,urlDeep+responseString);
                            ctx.startActivity(intent);
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            //     System.err.println("4 " + errorResponse.toString());
                            resetDialog();
                            if(failureMessage==null || failureMessage.equals("")){
                                failureMessage=errorResponse.toString();
                            }
                            DialogUtils.showMessageDialog(ctx, "Error", failureMessage);
                            Log.e("ERROR", errorResponse.toString());
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            resetDialog();
                            if(failureMessage==null || failureMessage.equals("")){
                                failureMessage=responseString;
                            }
                            DialogUtils.showMessageDialog(ctx, "Error", failureMessage);
                            Log.e("ERROR", responseString);
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                            //     System.err.println("4 " + errorResponse.toString());
                            resetDialog();
                            if(failureMessage==null || failureMessage.equals("")){
                                failureMessage=errorResponse.toString();
                            }
                            DialogUtils.showMessageDialog(ctx, "Error", failureMessage);
                            Log.e("ERROR", errorResponse.toString());


                        }
                    });

        }catch (Exception e){
            e.printStackTrace();

        }
    }
    public PayAndGo(Context context, AttributeSet attrs) {
        super(context, attrs);
        ctx=context;
        super.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(v);
                click();
            }
        });
    }

    public PayAndGo(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ctx=context;
        super.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(v);
                click();
            }
        });
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
            onClickListener=l;
    }



}
