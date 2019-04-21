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

/**
 * Classe permettant de communiquer avec le serveur utilisateur
 */
public class UserServer {
    private static final String TAG = "UserServer";
    private static UserServer instance = null;

    private static final String IpServer = "10.126.3.68:3000";

    private RequestQueue requestQueue;

    /**
     * Constructeur
     */
    private UserServer(){

    }

    /**
     * Recupere l'instance de la classe
     * @return instance
     */
    public static synchronized UserServer getInstance()
    {
        if (null == instance)
            instance = new UserServer();
        return instance;
    }

    /**
     * Requete pour se connecter
     * @param username
     * @param password
     * @param listener : objet recevant la reponse
     */
    public void login(String username, String password ,final ListenerRequestUser listener){


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

    /**
     * Requete pour s'enregistrer
     * @param username
     * @param password
     * @param listener : objet recevant lareponse
     */
    public void register(String username, String password ,final ListenerRequestUser listener){
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

    /**
     * Requete pour creer une playlist
     * @param id : id de l'utilisateur
     * @param namePlaylist : nom de la playlist
     * @param listener : objet recevant la reponse
     */
    public void createPlaylist(int id,String namePlaylist, final ListenerRequestUser listener){

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

    /**
     * Requete pour recupérer une playlist
     * @param id : id de l'utilisateur
     * @param namePlaylist : nom de la playlist
     * @param listener : objet recevant la reponse
     */
    public void getPlaylist(int id,String namePlaylist, final ListenerRequestUser listener){

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

    /**
     * Requete pour ajouter une musique à une playlist
     * @param id : id de l'utilisateur
     * @param namePlaylist : nom de la playlist
     * @param music : musique à ajouter
     * @param listener : objet recevant la reponse
     */
    public void addToPlaylist(int id, String namePlaylist, Music music, final ListenerRequestUser listener){

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

    /**
     * Requete pour supprimer une musique à une playlist
     * @param id : id de l'utilisateur
     * @param namePlaylist : nom de la playlist
     * @param music : musique à supprimer
     * @param listener : objet recevant la reponse
     */
    public void deleteToPlaylist(int id, String namePlaylist, Music music, final ListenerRequestUser listener){

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

    /**
     * Requete pour recupérer les musiques d'une playlist
     * @param id : id de l'utilisateur
     * @param namePlaylist : nom de la playlist
     * @param listener : objet recevant la reponse
     */
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

    /**
     * Permet d'effectuer une requete de type GET
     * @param url
     * @param typeRequest
     * @param listener
     */
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

    /**
     * Permet d'effectuer une requete de type POST
     * @param url
     * @param typeRequest
     * @param params
     * @param listener
     */
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
