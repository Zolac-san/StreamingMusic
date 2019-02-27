package m.project.test.Network.UserServer;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.net.URLEncoder;

import m.project.test.Network.QueueServerRequest;
import m.project.test.Settings.PreferenceGetter;


public class UserServer {
    private static final String TAG = "UserServer";
    private static UserServer instance = null;

    private static final String IpServer = "192.168.137.1:3000";

    private RequestQueue requestQueue;

    private UserServer(){

    }

    public static synchronized UserServer getInstance()
    {
        if (null == instance)
            instance = new UserServer();
        return instance;
    }

    public void login(String username, String password ,final ListenerRequestUser listener){

        String url = "http://" + PreferenceGetter.getValue("server_user") + "/login?";
        try{
            //parse but if error not a problem because we have setup a basic url
            url += "username="+URLEncoder.encode(username,"utf8")+"&password="+ URLEncoder.encode(password,"utf8");


        }catch (Exception e){
            System.err.println("Error parse url");
        }

        Log.i(TAG,"Start send : "+url);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG,"response : "+response.toString());
                        if(null != response.toString()){
                            listener.getResultUser(new ResponseUser(response));
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG,"error : "+error.toString());
                listener.getResultUser(new ResponseUser("login",true));
            }
        });
        // Add the request to the RequestQueue.
        QueueServerRequest.getInstance().addRequest(request);

    }


    public void register(String username, String password ,final ListenerRequestUser listener){

        String url = "http://" +IpServer + "/register?";
        try{
            //parse but if error not a problem because we have setup a basic url
            url += "username="+URLEncoder.encode(username,"utf8")+"&password="+ URLEncoder.encode(password,"utf8");


        }catch (Exception e){
            System.err.println("Error parse url");
        }

        Log.i(TAG,"Start send : "+url);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG,"response : "+response.toString());
                        if(null != response.toString()){
                            listener.getResultUser(new ResponseUser(response));
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG,"error : "+error.toString());
                listener.getResultUser(new ResponseUser("login",true));
            }
        });
        // Add the request to the RequestQueue.
        QueueServerRequest.getInstance().addRequest(request);

    }
}
