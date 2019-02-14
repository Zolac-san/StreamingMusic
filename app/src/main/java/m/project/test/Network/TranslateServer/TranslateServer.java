package m.project.test.Network.TranslateServer;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import m.project.test.MyApp;

public class TranslateServer {

    private static final String TAG = "TranslateServer";
    private static TranslateServer instance = null;

    private static final String IpServer = "192.168.0.0:5111";

    private RequestQueue requestQueue;

    private TranslateServer(){
        requestQueue = Volley.newRequestQueue(MyApp.getAppContext());
    }

    public static synchronized TranslateServer getInstance()
    {
        if (null == instance)
            instance = new TranslateServer();
        return instance;
    }

    public void request(String sentence,final ListenerRequestTranslate listener){

        String url = "http://" +IpServer;

        Map<String, Object> jsonParams = new HashMap<>();
        jsonParams.put("text", sentence);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url,new JSONObject(jsonParams),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if(null != response.toString()){



                            listener.getResultCommand(new ResponseTranslate(response));
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.getResultCommand(new ResponseTranslate(true));
                    }
                 });
        // Add the request to the RequestQueue.
        requestQueue.add(request);
    }
}
