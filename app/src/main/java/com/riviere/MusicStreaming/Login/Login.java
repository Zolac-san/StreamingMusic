package com.riviere.MusicStreaming.Login;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.riviere.MusicStreaming.MainActivity;
import com.riviere.MusicStreaming.MyApp;
import com.riviere.MusicStreaming.Network.TranslateServer.ListenerRequestTranslate;
import com.riviere.MusicStreaming.Network.TranslateServer.ResponseTranslate;
import com.riviere.MusicStreaming.Network.UserServer.ListenerRequestUser;
import com.riviere.MusicStreaming.Network.UserServer.ResponseUser;
import com.riviere.MusicStreaming.Network.UserServer.UserServer;
import com.riviere.MusicStreaming.R;
import com.riviere.MusicStreaming.Settings.SettingsActivity;
import com.riviere.MusicStreaming.SpeechAudio.ListenerLiveAudioResult;
import com.riviere.MusicStreaming.User.User;

/**
 * Activite de connexion
 */
public class Login extends AppCompatActivity implements ListenerRequestTranslate, ListenerRequestUser, ListenerLiveAudioResult {


    public String TAG = "Login";
    EditText usernameText,passwordText;
    Animation animateRotateClockwise,animateRotateCounterClockwise;
    ImageView circleLogo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameText = findViewById(R.id.textLoginUsername);
        passwordText = findViewById(R.id.textLoginPassword);
        circleLogo = (ImageView)findViewById(R.id.circle_logo_login);
        animateRotateClockwise = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_clockwise);
        animateRotateCounterClockwise = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_counter_clockwise);
        circleLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!MyApp.getCurrentVoiceRecorder().isContinuous()){

                    if(!MyApp.getCurrentVoiceRecorder().isRecording()){
                        startRecord();
                    }else{
                        stopRecord();
                    }
                }

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyApp.setCurrentActivity(this);

    }

    @Override
    protected void onPause() {
        clearReferences();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        clearReferences();
        super.onDestroy();
    }

    /**
     * Retire l'activité de l'apmlication
     */
    private void clearReferences(){
        Activity currActivity = MyApp.getCurrentActivity();
        if (this.equals(currActivity))
            MyApp.setCurrentActivity(null);
    }


    /**
     * Action pour aller sur l'activiyé principale
     * @param view
     */
    public void goOnMainActivity(View view){

        moveOnMainActivity();
    }


    /**
     * Action pour aller sur l'activité d'enregistrement
     * @param view
     */
    public void goOnRegister(View view){
        moveOnRegister();
    }


    /**
     * Lance l'activité principal
     */
    private void moveOnMainActivity(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    /**
     * Lance l'activite d'enregistrement
     */
    private void moveOnRegister(){
        Intent intent = new Intent(getApplicationContext(), Register.class);
        startActivity(intent);
    }


    /**
     * Action pour lancer l'activité des options
     * @param view
     */
    public void goOnSettings(View view){
        moveOnSettings();
    }

    /**
     * Lance l'activité des options
     */
    private void moveOnSettings(){
        Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
        startActivity(intent);
    }

    @Override
    public void getResultCommand(ResponseTranslate response) {
        circleLogo.clearAnimation();
        if(response.isError()) return;
        if(response.getCommand().equals("login")){
            UserServer.getInstance().login(usernameText.getText().toString(),passwordText.getText().toString(),this);
        }else if(response.getCommand().equals("register")){
            moveOnRegister();
        }else if(response.getCommand().equals("setting")){
            moveOnSettings();
        }

    }

    @Override
    public void getResultUser(ResponseUser response) {
        if (response.getTypeRequest().equals("login")){
            Log.i(TAG,"Login action");
            if(!response.isError()) {
                Log.i(TAG,"pass to main activity");
                User.getInstance().setId(response.getId());
                User.getInstance().setUsername(response.getUsername());

                moveOnMainActivity();
            }else {
                //error find
                Log.i(TAG,"Error login");
                Toast.makeText(MyApp.getAppContext(),"Error Username/Password",Toast.LENGTH_SHORT).show();
            }
        }


    }


    @Override
    public void getLiveAudioResult(String liveSpeechResult) {
        if(circleLogo.getAnimation() == null || circleLogo.getAnimation().hasEnded())
            circleLogo.startAnimation(animateRotateCounterClockwise);
    }

    /**
     * Commence l'enregistrementaudio
     */
    public void startRecord(){
        MyApp.getCurrentVoiceRecorder().startRecord();
        if(circleLogo.getAnimation() == null || circleLogo.getAnimation().hasEnded())
            circleLogo.startAnimation(animateRotateCounterClockwise);
    }

    /**
     * Stop l'enregistrement audio
     */
    public void stopRecord(){
        circleLogo.clearAnimation();
        MyApp.getCurrentVoiceRecorder().stopRecord();
    }
}
