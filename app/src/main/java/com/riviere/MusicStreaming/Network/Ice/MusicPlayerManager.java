package com.riviere.MusicStreaming.Network.Ice;

import android.os.AsyncTask;

import com.riviere.MusicStreaming.Settings.PreferenceGetter;

import Ice.Communicator;
import Ice.ObjectPrx;
import Ice.Util;

/**
 * Classe pour communiquer avec le serveur de streaming
 */
public class MusicPlayerManager{


    private static MusicPlayerManager instance = null;

    private MusicPlayer.PlayerPrx player;

    private static  AsyncTask<String,Void,Void> async = null;

    /**
     * Constructeur
     */
    private MusicPlayerManager(){

    }

    /**
     * Retourne l'instance de la classe
     * @return instance
     */
    public static MusicPlayerManager getInstance(){
        if(instance == null)
            instance = new MusicPlayerManager();
        return instance;
    }

    /**
     * Deamnde de jouer une musique
     * @param title : titre
     * @param author : autheur
     * @param album : album
     * @param listener : activité qui recoit la reponse
     */
    public void play(String title,String author,String album,ListenerMusicUrl listener){
        new ClientPlay(listener).execute(title,author,album);

    }


    /**
     * Classe pour effectuer la demande de jouer une musique mais en mode asynchrone
     */
    private class ClientPlay extends AsyncTask<String, Void, String> {

        private ListenerMusicUrl listener;

        public ClientPlay(ListenerMusicUrl listener){
            super();
            this.listener = listener;
        }

        protected String doInBackground(String... params) {
            if(params.length != 3){
                return "";
            }
            try (Communicator communicator = Util.initialize()) {
                String[] ipStreamingServer = PreferenceGetter.getValue("server_streaming").split(":");
                if(ipStreamingServer.length != 2 ) return "ICE Client AsyncTask finished";
                ObjectPrx base = communicator.stringToProxy("SimpleMusicManager:default -h "+ipStreamingServer[0]+" -p "+ipStreamingServer[1]);
                MusicPlayer.PlayerPrx player = MusicPlayer.PlayerPrxHelper.checkedCast(base);
                if(player == null)
                {
                    throw new Error("Invalid proxy");
                }
                String urlStream = player.play(params[0],params[1],params[2]);

                this.listener.onResultMusicUrl(urlStream);
            }

            return "ICE Client AsyncTask finished";
        }
    }




}
