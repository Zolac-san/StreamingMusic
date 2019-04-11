package m.project.test.Network.UserServer;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;

import m.project.test.Music.Music;
import m.project.test.Network.QueueServerRequest;
import m.project.test.Settings.PreferenceGetter;


public class UserServer {
    private static final String TAG = "UserServer";
    private static UserServer instance = null;

    private static final String IpServer = "10.126.3.68:3000";

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

        /*String url = "http://" + PreferenceGetter.getValue("server_user") + "/login?";
        try{
            //parse but if error not a problem because we have setup a basic url
            url += "username="+URLEncoder.encode(username,"utf8")+"&password="+ URLEncoder.encode(password,"utf8");


        }catch (Exception e){
            System.err.println("Error parse url");
        }

        makeRequestGet(url,"login",listener);*/
        String url = "http://" + PreferenceGetter.getValue("server_user") + "/login";
        JSONObject params = new JSONObject();
        try {
            params.put("username",username);
            params.put("password",password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        makeRequestPost(url,"login",params,listener);
    }


    public void register(String username, String password ,final ListenerRequestUser listener){
        /*
        String url = "http://" +IpServer + "/register?";
        try{
            //parse but if error not a problem because we have setup a basic url
            url += "username="+URLEncoder.encode(username,"utf8")+"&password="+ URLEncoder.encode(password,"utf8");


        }catch (Exception e){
            System.err.println("Error parse url");
        }

        makeRequestGet(url,"register",listener);*/

        String url = "http://" + PreferenceGetter.getValue("server_user") + "/register";
        JSONObject params = new JSONObject();
        try {
            params.put("username",username);
            params.put("password",password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        makeRequestPost(url,"register",params,listener);


    }

    public void createPlaylist(int id,String namePlaylist, final ListenerRequestUser listener){
        /*String url = "http://" +IpServer + "/createPlaylist?";
        try{
            //parse but if error not a problem because we have setup a basic url
            url += "name="+URLEncoder.encode(namePlaylist,"utf8")+"&id="+ id;


        }catch (Exception e){
            System.err.println("Error parse url");
        }

        makeRequestGet(url,"createPlaylist",listener);*/
        String url = "http://" + PreferenceGetter.getValue("server_user") + "/createPlaylist";
        JSONObject params = new JSONObject();
        try {
            params.put("name",namePlaylist);
            params.put("id",id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        makeRequestPost(url,"createPlaylist",params,listener);
    }

    public void getPlaylist(int id,String namePlaylist, final ListenerRequestUser listener){
        /*String url = "http://" +IpServer + "/getPlaylist?";
        try{
            //parse but if error not a problem because we have setup a basic url
            url += "name="+URLEncoder.encode(namePlaylist,"utf8")+"&id="+ id;


        }catch (Exception e){
            System.err.println("Error parse url");
        }

        makeRequestGet(url,"getPlaylist",listener);*/
        String url = "http://" + PreferenceGetter.getValue("server_user") + "/getPlaylist";
        JSONObject params = new JSONObject();
        try {
            params.put("name",namePlaylist);
            params.put("id",id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        makeRequestPost(url,"getPlaylist",params,listener);
    }

    public void addToPlaylist(int id, String namePlaylist, Music music, final ListenerRequestUser listener){
        /*String url = "http://" +IpServer + "/addToPlaylist?";
        try{
            //parse but if error not a problem because we have setup a basic url
            url += "name="+URLEncoder.encode(namePlaylist,"utf8")+"&id="+ id+"&music="+URLEncoder.encode(music.toJsonString(),"utf8");


        }catch (Exception e){
            System.err.println("Error parse url");
        }

        makeRequestGet(url,"getPlaylist",listener);*/
        String url = "http://" + PreferenceGetter.getValue("server_user") + "/addToPlaylist";
        JSONObject params = new JSONObject();
        try {
            params.put("name",namePlaylist);
            params.put("id",id);
            params.put("music",music.toJsonString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        makeRequestPost(url,"addToPlaylist",params,listener);
    }

    public void deleteToPlaylist(int id, String namePlaylist, Music music, final ListenerRequestUser listener){
        /*String url = "http://" +IpServer + "/deleteToPlaylist?";
        try{
            //parse but if error not a problem because we have setup a basic url
            url += "name="+URLEncoder.encode(namePlaylist,"utf8")+"&id="+ id+"&music="+music.toJsonString();


        }catch (Exception e){
            System.err.println("Error parse url");
        }

        makeRequestGet(url,"deleteToPlaylist",listener);*/
        String url = "http://" + PreferenceGetter.getValue("server_user") + "/deleteToPlaylist";
        JSONObject params = new JSONObject();
        try {
            params.put("name",namePlaylist);
            params.put("id",id);
            params.put("music",music.toJsonString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        makeRequestPost(url,"deleteToPlaylist",params,listener);
    }

    public void playPlaylist(int id, String namePlaylist,  final ListenerRequestUser listener){

        String url = "http://" + PreferenceGetter.getValue("server_user") + "/playPlaylist";
        JSONObject params = new JSONObject();
        try {
            params.put("name",namePlaylist);
            params.put("id",id);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        makeRequestPost(url,"playPlaylist",params,listener);
    }


    private void makeRequestGet(String url, final String typeRequest, final ListenerRequestUser listener){
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
                listener.getResultUser(new ResponseUser(typeRequest,true));
            }
        });
        // Add the request to the RequestQueue.
        QueueServerRequest.getInstance().addRequest(request);
    }

    private void makeRequestPost(String url, final String typeRequest, final JSONObject params, final ListenerRequestUser listener){
        Log.i(TAG,"Start send : "+url);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url,params,
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
                listener.getResultUser(new ResponseUser(typeRequest,true));
            }
        });
        // Add the request to the RequestQueue.
        QueueServerRequest.getInstance().addRequest(request);
    }
}
