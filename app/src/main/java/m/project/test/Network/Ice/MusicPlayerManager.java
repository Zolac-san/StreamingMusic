package m.project.test.Network.Ice;

import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import Ice.Communicator;
import Ice.ObjectPrx;
import Ice.Util;
import m.project.test.Streaming.PlayerVlc;


public class MusicPlayerManager{

    private String ipMotherServer = "192.168.1.20";
    private String portMotherServer = "6020";
    private static MusicPlayerManager instance = null;

    private MusicPlayer.PlayerPrx player;

    private static  AsyncTask<String,Void,Void> async = null;

    private MusicPlayerManager(){

    }

    public static MusicPlayerManager getIntance(){
        if(instance == null)
            instance = new MusicPlayerManager();
        return instance;
    }


    public void play(String title,String author,String album){
        new ClientTask().execute();
        //return player.play(title,author,album);
        /*Runnable abc = new Runnable(){

            @Override
            public void run() {
                try (Communicator communicator = Util.initialize()) {
                    ObjectPrx base = communicator.stringToProxy("SimpleMusicManager:default -h "+ipMotherServer+" -p "+portMotherServer);
                    MusicPlayer.PlayerPrx player = MusicPlayer.PlayerPrxHelper.checkedCast(base);
                    if(player == null)
                    {
                        throw new Error("Invalid proxy");
                    }
                    Log.i("HHDHHDHDDHDH",player.play("a","a","a"));

                }
            }
        };
        handler.post(abc);*/
        /*async = new AsyncTask<String,Void,Void>(){


            @Override
            protected Void doInBackground(String... params) {

                try (Communicator communicator = Util.initialize()) {
                    ObjectPrx base = communicator.stringToProxy("SimpleMusicManager:default -h "+ipMotherServer+" -p "+portMotherServer);
                    player = MusicPlayer.PlayerPrxHelper.checkedCast(base);
                    if(player == null)
                    {
                        throw new Error("Invalid proxy");
                    }


                }
                //String urlStream = player.play(title,author,album);
                String urlStream = player.play("a","a","a");
                Log.i("Music play",urlStream);
                //PlayerVlc.getInstance().play(urlStream);
                return null;
            }
        };
        async.execute();*/
    }


    public void stop(String title,String author,String album){

    }


    private class ClientTask extends AsyncTask<String, Void, String> {


        protected String doInBackground(String... params) {

            try (Communicator communicator = Util.initialize()) {
                ObjectPrx base = communicator.stringToProxy("SimpleMusicManager:default -h "+ipMotherServer+" -p "+portMotherServer);
                MusicPlayer.PlayerPrx player = MusicPlayer.PlayerPrxHelper.checkedCast(base);
                if(player == null)
                {
                    throw new Error("Invalid proxy");
                }
                String urlStream = player.play("a","a","a");
                Log.i("HHDHHDHDDHDH",urlStream);
                PlayerVlc.getInstance().play(urlStream);
            }

            return "ICE Client AsyncTask finished";
        }
    }




}
