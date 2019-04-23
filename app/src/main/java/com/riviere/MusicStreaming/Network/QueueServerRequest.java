package com.riviere.MusicStreaming.Network;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import com.riviere.MusicStreaming.MyApp;

/**
 * Classe permettant de stocker les requete pour la librairie Volley
 */
public class QueueServerRequest {
    private static QueueServerRequest instance = null;

    private RequestQueue queue;

    /**
     * Constructeur
     */
    private QueueServerRequest(){
        queue = Volley.newRequestQueue(MyApp.getAppContext());
    }

    /**
     * Recupere l'instance de la classe
     * @return instance
     */
    public static QueueServerRequest getInstance(){
        if (instance == null)
            instance = new QueueServerRequest();
        return instance;
    }

    /**
     * Rajoute une requete
     * @param request
     * @param <T>
     */
    public <T> void addRequest(Request<T> request){
        queue.add(request);
    }
}
