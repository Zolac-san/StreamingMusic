package com.riviere.MusicStreaming.Settings;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.riviere.MusicStreaming.MyApp;
import com.riviere.MusicStreaming.Network.TranslateServer.ListenerRequestTranslate;
import com.riviere.MusicStreaming.Network.TranslateServer.ResponseTranslate;
import com.riviere.MusicStreaming.Network.TranslateServer.TranslateServer;
import com.riviere.MusicStreaming.R;

/**
 * Activité d'ajout de commande
 */
public class AddCommandActivity extends AppCompatActivity implements ListenerRequestTranslate, AdapterView.OnItemSelectedListener {

    public final static String TAG = "AddCommandActivity";
    private EditText word,afterWord,beforeWord;
    private Spinner listCommand;
    private Button buttonRemoveWord, buttonRemoveBeforeWord, buttonRemoveAfterWord;
    Animation animateRotateClockwise,animateRotateCounterClockwise;
    ImageView circleLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_command);

        MyApp.setCurrentActivity(this);

        word = findViewById(R.id.addCommandWord);
        afterWord = findViewById(R.id.addCommandAfterWord);
        beforeWord = findViewById(R.id.addCommandBeforeWord);
        listCommand = findViewById(R.id.addCommandListCommand);

        buttonRemoveWord = findViewById(R.id.addCommandButtonRemoveTextWord);
        buttonRemoveBeforeWord = findViewById(R.id.addCommandButtonRemoveTextBeforeWord);
        buttonRemoveAfterWord = findViewById(R.id.addCommandButtonRemoveTextAfterWord);

        buttonRemoveWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                word.setText("");
            }
        });

        buttonRemoveBeforeWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beforeWord.setText("");
            }
        });

        buttonRemoveAfterWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                afterWord.setText("");
            }
        });


        word.setInputType(InputType.TYPE_NULL);
        afterWord.setInputType(InputType.TYPE_NULL);
        beforeWord.setInputType(InputType.TYPE_NULL);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.spinner_add_command, getIntent().getStringArrayListExtra("listCommand"));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listCommand.setAdapter(adapter);
        listCommand.setOnItemSelectedListener(this);

        circleLogo = (ImageView)findViewById(R.id.circle_logo_addCommand);
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

    /**
     * Actionpour ajouter une commande
     * @param view
     */
    public void submitAction(View view){
        if(!word.getText().toString().isEmpty() ){
            askServerToAdd();
        }
    }

    /**
     * Fonction pour demander l'ajout de la fonction
     */
    private void askServerToAdd(){
        TranslateServer.getInstance().addCommandRequest(word.getText().toString(),listCommand.getSelectedItem().toString() ,beforeWord.getText().toString(),afterWord.getText().toString(),this);
    }

    /**
     * Actionpour lancer la reconnaisance vocal
     * @param view
     */
    public void reconizeAction(View view){
        if(!MyApp.getCurrentVoiceRecorder().isContinuous()){
            if(!MyApp.getCurrentVoiceRecorder().isRecording()){
                MyApp.getCurrentVoiceRecorder().startRecord();
            }else{
                MyApp.getCurrentVoiceRecorder().stopRecord();
            }
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void getResultCommand(ResponseTranslate response) {
        circleLogo.clearAnimation();
        if(response.getService().equals("addCommand")){
            if(response.isError()){
                Toast.makeText(MyApp.getAppContext() , "error", Toast.LENGTH_SHORT).show();
            }else{
                finish();
            }
        }else{

            String oneWordFromText = response.getText().split(" ")[0].toLowerCase();
            if(!response.isError()){
                if (response.getCommand().equals("cancel")){
                    finish();
                }else if(response.getCommand().equals("addCommand")){
                    if(!word.getText().toString().isEmpty() ){
                        askServerToAdd();
                    }
                }

            }else if(word.isFocused()){
                word.setText(oneWordFromText);
            }else if(afterWord.isFocused()){
                afterWord.setText(oneWordFromText);
            } else if(beforeWord.isFocused()){
                beforeWord.setText(oneWordFromText);
            }
        }


    }

    /**
     * Lance la reconnaissance vocale
     */
    public void startRecord(){
        MyApp.getCurrentVoiceRecorder().startRecord();
        if(circleLogo.getAnimation() == null || circleLogo.getAnimation().hasEnded())
            circleLogo.startAnimation(animateRotateCounterClockwise);
    }

    /**
     * Arrete la reconnaisance vocale
     */
    public void stopRecord(){
        circleLogo.clearAnimation();
        MyApp.getCurrentVoiceRecorder().stopRecord();
    }
}
