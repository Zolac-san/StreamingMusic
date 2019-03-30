package m.project.test.SpeechAudio;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import m.project.test.MyApp;
import m.project.test.Network.TranslateServer.ListenerRequestTranslate;
import m.project.test.Network.TranslateServer.TranslateServer;

import static android.content.ContentValues.TAG;

public class AndroidReconizer implements VoiceRecorder {

    private SpeechRecognizer mSpeechRecognizer;
    private Intent mSpeechRecognizerIntent;
    private boolean mIslistening;

    private static AndroidReconizer instance;
    private boolean recording;

    private AndroidReconizer(){
        recording = false;
        mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(MyApp.getAppContext());
        mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        //mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
          //      this.getPackageName());


        SpeechRecognitionListener listener = new SpeechRecognitionListener();
        mSpeechRecognizer.setRecognitionListener(listener);
    }


    public static AndroidReconizer getInstance(){
        if(instance == null)
            instance = new AndroidReconizer();
        return  instance;
    }

    @Override
    public void launch() {
        //no setup need
    }

    @Override
    public void close() {
        //nosetup to close
    }

    @Override
    public void startRecord() {
        recording = true;
        mSpeechRecognizer.startListening(mSpeechRecognizerIntent);

    }

    @Override
    public void stopRecord() {
        //not need to stop
    }

    @Override
    public boolean isContinuous() {
        return false;
    }

    @Override
    public boolean isRecording() {
        return this.recording;
    }


    protected class SpeechRecognitionListener implements RecognitionListener
    {

        @Override
        public void onBeginningOfSpeech()
        {
            //Log.d(TAG, "onBeginingOfSpeech");
        }

        @Override
        public void onBufferReceived(byte[] buffer)
        {

        }

        @Override
        public void onEndOfSpeech()
        {
            //Log.d(TAG, "onEndOfSpeech");
        }

        @Override
        public void onError(int error)
        {
            Log.i("COL","in");
            mSpeechRecognizer.startListening(mSpeechRecognizerIntent);

            //Log.d(TAG, "error = " + error);
        }

        @Override
        public void onEvent(int eventType, Bundle params)
        {

        }

        @Override
        public void onPartialResults(Bundle partialResults)
        {

        }

        @Override
        public void onReadyForSpeech(Bundle params)
        {
            Log.d(TAG, "onReadyForSpeech"); //$NON-NLS-1$
        }

        @Override
        public void onResults(Bundle results)
        {
            recording =false;
            //Log.d(TAG, "onResults"); //$NON-NLS-1$
            ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            Log.i("AndroidReconizer",matches.toString());
            if(!matches.isEmpty()){


                Activity current = MyApp.getCurrentActivity();
                Log.i("AndroidREconize",current.getPackageName());
                if( current instanceof ListenerRequestTranslate){

                    ListenerRequestTranslate currentListener = (ListenerRequestTranslate) current;
                    TranslateServer.getInstance().request(matches.get(0),currentListener);
                }
            }

        }

        @Override
        public void onRmsChanged(float rmsdB)
        {
        }
    }
}
