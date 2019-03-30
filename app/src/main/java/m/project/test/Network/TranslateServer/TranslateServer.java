package m.project.test.Network.TranslateServer;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import m.project.test.MyApp;
import m.project.test.Network.QueueServerRequest;
import m.project.test.Settings.PreferenceGetter;
import m.project.test.User.User;

public class TranslateServer {

    private static final String TAG = "TranslateServer";
    private static TranslateServer instance = null;

    private static final String IpServer = "192.168.1.22:5111";

    private RequestQueue requestQueue;

    private TranslateServer(){

    }

    public static synchronized TranslateServer getInstance()
    {
        if (null == instance)
            instance = new TranslateServer();
        return instance;
    }

    public void request(String sentence,final ListenerRequestTranslate listener){

        String url = "http://" + PreferenceGetter.getValue("server_translate") + "/getCommand?text=";

        try{
            //parse but if error not a problem because we have setup a basic url
            url += URLEncoder.encode(sentence,"utf8")+"&id="+ User.getInstance().getId();


        }catch (Exception e){
            System.err.println("Error parse url");
        }

        makeRequestGet(url,listener);

    }

    public void addCommandRequest(String word, String command,String beforeWord, String afterWord,final ListenerRequestTranslate listener){

        String url = "http://" + PreferenceGetter.getValue("server_translate") + "/addCommand";
        try{
            //parse but if error not a problem because we have setup a basic url
            url += "?id="+ User.getInstance().getId() +"&word="+URLEncoder.encode(word,"utf8")+"&command="+URLEncoder.encode(command,"utf8");
            if(!beforeWord.isEmpty()) url+= "&beforeWord="+URLEncoder.encode(beforeWord,"utf8");
            if(!afterWord.isEmpty()) url+="&afterWord="+URLEncoder.encode(afterWord,"utf8");


        }catch (Exception e){
            System.err.println("Error parse url");
        }

        makeRequestGet(url,listener);

    }

    private void makeRequestGet(String url, final ListenerRequestTranslate listener){
        Log.i(TAG,"Start send : "+url);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG,"response : "+response.toString());
                        if(null != response.toString()){
                            listener.getResultCommand(new ResponseTranslate(response));
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                    Log.i(TAG,"error : "+error.toString());
                    listener.getResultCommand(new ResponseTranslate(true));
            }
        });
        // Add the request to the RequestQueue.
        QueueServerRequest.getInstance().addRequest(request);
    }
}
