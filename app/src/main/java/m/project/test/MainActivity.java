package m.project.test;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import m.project.test.Music.Music;
import m.project.test.Music.Playlist;
import m.project.test.Network.Ice.ListenerMusicUrl;
import m.project.test.Network.Ice.MusicPlayerManager;
import m.project.test.Network.TranslateServer.ListenerRequestTranslate;
import m.project.test.Network.TranslateServer.ResponseTranslate;
import m.project.test.Network.UserServer.ListenerRequestUser;
import m.project.test.Network.UserServer.ResponseUser;
import m.project.test.Network.UserServer.UserServer;
import m.project.test.Settings.AddCommandActivity;
import m.project.test.Settings.SettingsActivity;
import m.project.test.SpeechAudio.ListenerLiveAudioResult;
import m.project.test.Streaming.PlayerVlc;
import m.project.test.User.User;



public class MainActivity extends AppCompatActivity implements ListenerRequestTranslate, ListenerLiveAudioResult, ListenerRequestUser, ListenerMusicUrl {

    public final String TAG = "MainActivity";
    private TextView resultVoiceText;
    private Button btnStart,btnStop;
    Animation animateRotateClockwise, animateRotateCounterClockwise;
    ImageView circleLogo;

    Music music;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultVoiceText = findViewById(R.id.ResultVoiceText);
        circleLogo = (ImageView)findViewById(R.id.circle_logo);
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

        music = new Music();
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

    private void clearReferences(){
        Activity currActivity = MyApp.getCurrentActivity();
        if (this.equals(currActivity))
            MyApp.setCurrentActivity(null);
    }

    public void goOnSettings(View view){
        moveOnSettings();
    }

    private void moveOnSettings(){
        Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
        startActivity(intent);
    }


    private void moveOnAddCommand(ArrayList<String> listCommand){
        Intent intent = new Intent(getApplicationContext(), AddCommandActivity.class);
        intent.putExtra("listCommand",listCommand);
        startActivity(intent);
    }

    public void getResultCommand(ResponseTranslate response){
        if (response.getCommand().equals("setting")){
            Log.i(TAG,"it's a setting action");
            if(!response.isError()) {
                moveOnSettings();
            }else {
                //error find
                // We can display a message
            }
        }
        else if (response.getCommand().equals("logout")){

            if(!response.isError()) {
                finish();
            }
        }else if(response.getCommand().equals("jouer")){
            music = new Music(response.getTitle(),response.getAuthors(),response.getAlbum());
            MusicPlayerManager.getInstance().play(music.getTittle(),music.authorsToString(),music.getAlbum(),this);
        }else if(response.getCommand().equals("pause")){
            PlayerVlc.getInstance().pause();
        }else if(response.getCommand().equals("rejouer")){
            PlayerVlc.getInstance().replay();
        }else if(response.getCommand().equals("stop")){
            PlayerVlc.getInstance().stop();
        }else if(response.getCommand().equals("continue")){
            PlayerVlc.getInstance().play();
        }else if(response.getCommand().equals("nextMusic")){
            PlayerVlc.getInstance().loadNextMusicPlaylist();
        }else if(response.getCommand().equals("previousMusic")){
            PlayerVlc.getInstance().loadPreviousMusicPlaylist();
        }else if(response.getCommand().equals("createPlaylist")){
            UserServer.getInstance().createPlaylist(User.getInstance().getId(),response.getNamePlaylist(),this);
        }else if(response.getCommand().equals("addToPlaylist")){
            UserServer.getInstance().addToPlaylist(User.getInstance().getId(),response.getNamePlaylist(),music,this);
        }else if(response.getCommand().equals("deleteToPlaylist")) {
            UserServer.getInstance().deleteToPlaylist(User.getInstance().getId(), response.getNamePlaylist(), music, this);
        }else if(response.getCommand().equals("playPlaylist")) {
            Log.i("Hererere","heheheeheheh");
            UserServer.getInstance().playPlaylist(User.getInstance().getId(), response.getNamePlaylist(), this);
        }else if(response.getCommand().equals("addCommand")){
            moveOnAddCommand(response.getListCommand());
        }

        resultVoiceText.setText(response.toString());
        //circleLogo.getAnimation().cancel();
        circleLogo.clearAnimation();

    }


    @Override
    public void getLiveAudioResult(String liveSpeechResult) {
        if(circleLogo.getAnimation() == null || circleLogo.getAnimation().hasEnded())
            circleLogo.startAnimation(animateRotateCounterClockwise);
    }

    public void startRecord(){
        MyApp.getCurrentVoiceRecorder().startRecord();
        if(circleLogo.getAnimation() == null || circleLogo.getAnimation().hasEnded())
            circleLogo.startAnimation(animateRotateCounterClockwise);
    }



    public void stopRecord(){
        MyApp.getCurrentVoiceRecorder().stopRecord();
    }

    @Override
    public void getResultUser(ResponseUser response) {
        if(response.getTypeRequest().equals("playPlaylist")){
            if(response.isError()) return;
            Playlist playlist = new Playlist(response.getPlayListName());
            for(Music music : response.getListMusic()){
                playlist.add(music);
            }
            PlayerVlc.getInstance().playPlaylist(playlist);
        }
    }


    @Override
    public void onResultMusicUrl(String url) {
        PlayerVlc.getInstance().load(url);
        PlayerVlc.getInstance().play();
    }
}
