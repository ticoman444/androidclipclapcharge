package sdk.clipclap.clipclapcharge;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * @author Luis Guzman -
 */
public class HttpService {

/*
*
* */
//

    private static final String BASE_URLF = "api/1";
    private static AsyncHttpClient client = new AsyncHttpClient();




    public static void post(Context context, String url, JSONObject params, AsyncHttpResponseHandler responseHandler) throws Exception {
        client.setTimeout(50000);
        StringEntity entity = new StringEntity(params.toString());
        System.err.println(getAbsoluteUrl(context, url) + "?" + params.toString());
        client.addHeader("Content-Type", "application/json");
        client.addHeader("Authorization", "Token 1ecbf401c1639b65ef9b34aed93db6e98d9e00a");
        client.post(context,getAbsoluteUrl(context,url), entity,"application/json", responseHandler);

    }


    public static void post3(Context context, String url, JSONObject params, AsyncHttpResponseHandler responseHandler) throws Exception {
        client.setTimeout(50000);
     //   client.addHeader("Content-Type", "application/x-www-form-urlencoded");
        StringEntity entity = new StringEntity(params.toString());
        Log.d("COMPRAS", getAbsoluteUrl(context, url) + "->" + params.toString());
        client.post(context, getAbsoluteUrl(context,url),entity,"application/x-www-form-urlencoded",  responseHandler);

    }




    private static String getAbsoluteUrl(Context context,String relativeUrl) {
        return  relativeUrl;
    }



}
