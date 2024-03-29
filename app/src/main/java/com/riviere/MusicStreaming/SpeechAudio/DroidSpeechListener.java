package com.riviere.MusicStreaming.SpeechAudio;

import android.app.Activity;
import android.os.Handler;
import android.util.Log;


import com.vikramezhil.droidspeech.DroidSpeech;
import com.vikramezhil.droidspeech.OnDSListener;
import com.vikramezhil.droidspeech.OnDSPermissionsListener;

import java.util.List;

import com.riviere.MusicStreaming.MyApp;
import com.riviere.MusicStreaming.Network.TranslateServer.ListenerRequestTranslate;
import com.riviere.MusicStreaming.Network.TranslateServer.TranslateServer;

/**
 * Classe implementant la reconnaissance audio DroidSpeech
 */
public class DroidSpeechListener implements OnDSListener, OnDSPermissionsListener, VoiceRecorder {
//public class DroidSpeechListener{
    public final String TAG = "DroidSpeechListener";
    private static DroidSpeechListener instance = null;
    private DroidSpeech droidSpeech;
    private boolean onReset;
    Handler handler ;
    private Runnable updateData;

    private boolean recording;

    private DroidSpeechListener(){

        droidSpeech = new DroidSpeech(MyApp.getAppContext(), null);
        droidSpeech.setOnDroidSpeechListener(this);

        Log.i(TAG,"Start reco");
        droidSpeech.closeDroidSpeechOperations();
        recording = false;
        onReset = false;
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
        Activity current = MyApp.getCurrentActivity();
        if( current instanceof ListenerLiveAudioChangeRms){
            ((ListenerLiveAudioChangeRms)current).onChangeAudioRms(rmsChangedValue);
        }
    }

    @Override
    public void onDroidSpeechLiveResult(String liveSpeechResult) {
        recording = true;
        Log.i(TAG,"live result : " + liveSpeechResult);
        Activity current = MyApp.getCurrentActivity();
        if( current instanceof ListenerLiveAudioResult){
            ((ListenerLiveAudioResult)current).getLiveAudioResult(liveSpeechResult);
        }
    }

    @Override
    public void onDroidSpeechFinalResult(String finalSpeechResult) {
        recording =false;
        Log.i(TAG,finalSpeechResult);
        Activity current = MyApp.getCurrentActivity();
        if( current instanceof ListenerRequestTranslate){
            ListenerRequestTranslate currentListener = (ListenerRequestTranslate) current;
            TranslateServer.getInstance().request(finalSpeechResult,currentListener);
        }

        //Obliger du a un bug de l'api google
        //restart();
    }

    @Override
    public void onDroidSpeechClosedByUser() {

    }

    @Override
    public void onDroidSpeechError(String errorMsg) {
        Log.i(TAG,"Error droid speech: " + errorMsg);
    }


    @Override
    public void onDroidSpeechAudioPermissionStatus(boolean audioPermissionGiven, String errorMsgIfAny) {
        Log.i(TAG, "audio permi  = " + audioPermissionGiven);
    }

    public void launch(){
        Log.i(TAG,"Launch DroidSpeech");
        droidSpeech.startDroidSpeechRecognition();

    }

    public void close(){
        handler.removeCallbacksAndMessages(null);
        droidSpeech.closeDroidSpeechOperations();

    }

    @Override
    public void startRecord() {

    }

    @Override
    public void stopRecord() {

    }

    @Override
    public boolean isContinuous() {
        return true;
    }

    @Override
    public boolean isRecording() {
        return false;
    }

    public void restart(){
        if(onReset) return;
        onReset = true;
        droidSpeech.closeDroidSpeechOperations();
        Handler handler = new Handler();
        Runnable updateData  = new Runnable(){
            public void run(){
                //call the service here
                Log.i(TAG,"Restart speech");
                droidSpeech.startDroidSpeechRecognition();
                onReset = false;
            }
        };
        handler.postDelayed(updateData,4000);
    }
}
