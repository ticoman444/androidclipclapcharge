package sdk.clipclap.clipclapcharge;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by josedavidmantilla on 12/5/15.
 */
public class CCBilleteraPayment {

    private JSONObject itemClipclap;
    private JSONArray  itemsArray;
    private final String SECRETKEY="secretKey";
    private final String ITEMCOUNT="itemCount";
    private final String ITEMNAME="itemName";
    private final String ITEMVALUE="itemValue";
    private final String TAXID="taxId";
    private final String DETAILS="details";
    public static final int CONSUMO_REGULAR_8=5;
    public static final int IVA_EXCLUIDO_0=4;
    public static final int IVA_EXCENTO_0=3;
    public static final int IVA_REDUCIDO_5=2;
    public static final int IVA_REGULAR_16_=1;
    public static final int IVA_AMPLIADO_20=7;
    public static final int CONSUMO_REDUCIDO_4=6;
    private final String TIPVALUE="tipValue";
    private final String TAXVALUE="taxValue";
    private final String NETVALUE="netValue";
    private final String DESCRIPTION="description";



   public CCBilleteraPayment(String secretKey) throws  Exception{
       itemsArray = new JSONArray();
       itemClipclap= new JSONObject();
       itemClipclap.put(SECRETKEY,secretKey);
       itemClipclap.put(DETAILS,itemsArray);
   }



    public void addItem(String name, int count, int value, int taxId ) throws Exception{
        JSONObject jsonItem = new JSONObject();
        jsonItem.put(ITEMCOUNT,count);
        jsonItem.put(ITEMNAME,name);
        jsonItem.put(ITEMVALUE,value);
        jsonItem.put(TAXID,taxId);
        itemsArray.put(jsonItem);
    }

    public void addItem(String name, int count, int value) throws Exception{
        JSONObject jsonItem = new JSONObject();
        jsonItem.put(ITEMCOUNT,count);
        jsonItem.put(ITEMNAME,name);
        jsonItem.put(ITEMVALUE,value);
        itemsArray.put(jsonItem);
    }


    public void addTotal(String description,int netvalue, int taxValue, int tipValue) throws Exception{
        itemClipclap.put(DESCRIPTION,description);
        itemClipclap.put(NETVALUE, netvalue);
        itemClipclap.put(TAXVALUE, taxValue);
        itemClipclap.put(TIPVALUE,tipValue);
    }


    public JSONObject getJSON(){
        return itemClipclap;
    }


}
