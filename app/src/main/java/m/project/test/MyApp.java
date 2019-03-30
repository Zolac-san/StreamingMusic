package m.project.test;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.media.AudioManager;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

import m.project.test.Network.TranslateServer.TranslateServer;
import m.project.test.SpeechAudio.AndroidReconizer;
import m.project.test.SpeechAudio.DroidSpeechListener;
import m.project.test.SpeechAudio.VoiceRecorder;

public class MyApp extends Application {
    public String TAG = "MyApp";
    private static Context context;

    private static Activity currentActivity;

    private static VoiceRecorder voiceRecorder;



    public void onCreate() {
        Log.i(TAG,"Create app");
        super.onCreate();
        MyApp.context = getApplicationContext();

        TranslateServer.getInstance(); // For create
        currentActivity= null;
        voiceRecorder = AndroidReconizer.getInstance();//DroidSpeechListener.getInstance();
        voiceRecorder.launch();

        /*handler = new Handler();
        updateData  = new Runnable(){
            public void run(){
                //call the service here
                Log.i(TAG,"Reset speech reconizer for no error from android");
                DroidSpeechListener.getInstance().restart();
                ////// set the interval time here
                handler.postDelayed(updateData,30000);
            }
        };
        handler.postDelayed(updateData,30000);*/
    }

    public static Context getAppContext() {
        return MyApp.context;
    }

    public static Activity getCurrentActivity() {
        return MyApp.currentActivity;
    }

    public static void setCurrentActivity(Activity currentActivity) {
        MyApp.currentActivity = currentActivity;
    }
    public void onClose(){

    }

    public static VoiceRecorder getCurrentVoiceRecorder(){
        return voiceRecorder;
    }



}
