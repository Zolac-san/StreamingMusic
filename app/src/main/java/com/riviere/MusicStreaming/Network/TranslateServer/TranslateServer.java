package com.riviere.MusicStreaming.Network.TranslateServer;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.net.URLEncoder;

import com.riviere.MusicStreaming.Network.QueueServerRequest;
import com.riviere.MusicStreaming.Settings.PreferenceGetter;
import com.riviere.MusicStreaming.User.User;

/**
 * Classe pour communiquer avec le serveur de de transformation du texte en commande
 */
public class TranslateServer {

    private static final String TAG = "TranslateServer";
    private static TranslateServer instance = null;

    private static final String IpServer = "10.126.3.68:5111";

    private RequestQueue requestQueue;

    /**
     * Constructeur
     */
    private TranslateServer(){

    }

    /**
     * Retourne l'instance
     * @return instance
     */
    public static synchronized TranslateServer getInstance()
    {
        if (null == instance)
            instance = new TranslateServer();
        return instance;
    }


    /**
     * Requete pour transformer une phrase en commande
     * @param sentence : phrase a traduire
     * @param listener : activité recevant le resultat
     */
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

    /**
     * Requete pour ajouter une commande
     * @param word
     * @param command
     * @param beforeWord
     * @param afterWord
     * @param listener : activité recevant la reponse
     */
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

    /**
     * Effectue une requete de type GET
     * @param url
     * @param listener
     */
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
