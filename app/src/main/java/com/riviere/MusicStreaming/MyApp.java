package com.riviere.MusicStreaming;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.riviere.MusicStreaming.Network.TranslateServer.TranslateServer;
import com.riviere.MusicStreaming.SpeechAudio.AndroidReconizer;
import com.riviere.MusicStreaming.SpeechAudio.VoiceRecorder;

/**
 * Classe representant l'application
 */
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
        voiceRecorder = AndroidReconizer.getInstance();
        //voiceRecorder = DroidSpeechListener.getInstance();
        voiceRecorder.launch();

    }

    /**
     * Get app context
     * @return context
     */
    public static Context getAppContext() {
        return MyApp.context;
    }

    /**
     * Get current activity
     * @return current activity
     */
    public static Activity getCurrentActivity() {
        return MyApp.currentActivity;
    }

    /**
     * Set Current activity
     * @param currentActivity : current activity
     */
    public static void setCurrentActivity(Activity currentActivity) {
        MyApp.currentActivity = currentActivity;
    }

    /**
     * Call on close of the application
     */
    public void onClose(){

    }

    /**
     * Get Current voice recorder
     * @return
     */
    public static VoiceRecorder getCurrentVoiceRecorder(){
        return voiceRecorder;
    }



}
