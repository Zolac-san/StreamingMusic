package m.project.test.Login;

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

import m.project.test.MyApp;
import m.project.test.Network.TranslateServer.ListenerRequestTranslate;
import m.project.test.Network.TranslateServer.ResponseTranslate;
import m.project.test.Network.UserServer.ListenerRequestUser;
import m.project.test.Network.UserServer.ResponseUser;
import m.project.test.Network.UserServer.UserServer;
import m.project.test.R;
import m.project.test.Settings.SettingsActivity;
import m.project.test.SpeechAudio.ListenerLiveAudioResult;
import m.project.test.User.User;

public class Register extends AppCompatActivity implements ListenerRequestTranslate, ListenerRequestUser, ListenerLiveAudioResult {
    private String TAG = "Register";

    private EditText usernameText, passwordText;
    Animation animateRotateClockwise,animateRotateCounterClockwise;
    ImageView circleLogo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        usernameText = findViewById(R.id.textRegisterUsername);
        passwordText = findViewById(R.id.textRegisterPassword);
        circleLogo = (ImageView)findViewById(R.id.circle_logo_register);
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
     * Retire l'activite de l'application
     */
    private void clearReferences(){
        Activity currActivity = MyApp.getCurrentActivity();
        if (this.equals(currActivity))
            MyApp.setCurrentActivity(null);
    }

    /**
     * Action pour allez sur l'activité d'enregistrement
     * @param view
     */
    public void goRegister(View view){
        finish();
    }

    /**
     * Action pour aller sur l'activité des options
     * @param view
     */
    public void goOnSettings(View view){
        moveOnSettings();
    }

    /**
     * Demarre l'activite des options
     */
    private void moveOnSettings(){
        Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
        startActivity(intent);
    }


    @Override
    public void getResultCommand(ResponseTranslate response) {
        circleLogo.clearAnimation();
        if(response.isError()) return;
        if(response.getCommand().equals("cancel")){
            finish();
        }
        if(response.getCommand().equals("register")){
            UserServer.getInstance().register(usernameText.getText().toString(),passwordText.getText().toString(),this);
        }
        if(response.getCommand().equals("setting")){
            moveOnSettings();
        }
    }

    @Override
    public void getResultUser(ResponseUser response) {
        if (response.getTypeRequest().equals("register")){

            if(!response.isError()) {
                finish();
            }else {
                //error find
                // We can display a message
            }
        }
    }

    @Override
    public void getLiveAudioResult(String liveSpeechResult) {
        if(circleLogo.getAnimation() == null || circleLogo.getAnimation().hasEnded())
            circleLogo.startAnimation(animateRotateCounterClockwise);
    }

    /**
     * Demarre la reconnaissance vocale
     */
    public void startRecord(){
        MyApp.getCurrentVoiceRecorder().startRecord();
        if(circleLogo.getAnimation() == null || circleLogo.getAnimation().hasEnded())
            circleLogo.startAnimation(animateRotateCounterClockwise);
    }

    /**
     * Arrete la reconnaissance vocale
     */
    public void stopRecord(){
        MyApp.getCurrentVoiceRecorder().stopRecord();
    }
}
