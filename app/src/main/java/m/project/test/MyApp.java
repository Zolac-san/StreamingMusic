package m.project.test;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import m.project.test.Network.TranslateServer.TranslateServer;
import m.project.test.SpeechAudio.DroidSpeechListener;

public class MyApp extends Application {
    private static Context context;

    private static Activity currentActivity;

    private DroidSpeechListener speechReconizer;

    public void onCreate() {
        super.onCreate();
        MyApp.context = getApplicationContext();
        TranslateServer.getInstance(); // For create
        currentActivity= null;
        speechReconizer = DroidSpeechListener.getInstance();
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
}
