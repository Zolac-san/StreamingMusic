package m.project.test.SpeechAudio;

import android.app.Activity;
import android.util.Log;

import com.vikramezhil.droidspeech.DroidSpeech;
import com.vikramezhil.droidspeech.OnDSListener;
import com.vikramezhil.droidspeech.OnDSPermissionsListener;

import java.util.List;

import m.project.test.MyApp;
import m.project.test.Network.TranslateServer.ListenerRequestTranslate;
import m.project.test.Network.TranslateServer.TranslateServer;

public class DroidSpeechListener implements OnDSListener, OnDSPermissionsListener {

    public final String TAG = "DroidSpeechListener";

    private static DroidSpeechListener instance = null;

    private DroidSpeech droidSpeech;

    private DroidSpeechListener(){
        droidSpeech = new DroidSpeech(MyApp.getAppContext(), null);
        droidSpeech.setOnDroidSpeechListener(this);
        droidSpeech.startDroidSpeechRecognition();
    }

    public static DroidSpeechListener getInstance(){
        if(instance == null)
            instance = new DroidSpeechListener();
        return instance;
    }

    @Override
    public void onDroidSpeechSupportedLanguages(String currentSpeechLanguage, List<String> supportedSpeechLanguages) {
        Log.i(TAG, "Current speech language = " + currentSpeechLanguage);
        Log.i(TAG, "Supported speech languages = " + supportedSpeechLanguages.toString());
        if(supportedSpeechLanguages.contains("fr-FR"))
        {
            droidSpeech.setPreferredLanguage("fr-FR");
        }
    }

    @Override
    public void onDroidSpeechRmsChanged(float rmsChangedValue) {

    }

    @Override
    public void onDroidSpeechLiveResult(String liveSpeechResult) {
    }

    @Override
    public void onDroidSpeechFinalResult(String finalSpeechResult) {
        Activity current = MyApp.getCurrentActivity();
        if( current instanceof ListenerRequestTranslate){
            ListenerRequestTranslate currentListener = (ListenerRequestTranslate) current;
            TranslateServer.getInstance().request(finalSpeechResult,currentListener);
        }
    }

    @Override
    public void onDroidSpeechClosedByUser() {

    }

    @Override
    public void onDroidSpeechError(String errorMsg) {

    }


    @Override
    public void onDroidSpeechAudioPermissionStatus(boolean audioPermissionGiven, String errorMsgIfAny) {
        Log.i(TAG, "audio permi  = " + audioPermissionGiven);
    }
}
