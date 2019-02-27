package m.project.test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vikramezhil.droidspeech.DroidSpeech;
import com.vikramezhil.droidspeech.OnDSListener;
import com.vikramezhil.droidspeech.OnDSPermissionsListener;

import java.util.List;
import java.util.Random;

import m.project.test.Network.TranslateServer.ListenerRequestTranslate;
import m.project.test.Network.TranslateServer.ResponseTranslate;
import m.project.test.Settings.SettingsActivity;

public class MainActivity extends AppCompatActivity implements ListenerRequestTranslate {

    public final String TAG = "MainActivity";
    private TextView resultVoiceText;
    private Button btnStart,btnStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultVoiceText = findViewById(R.id.ResultVoiceText);

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
        resultVoiceText.setText(response.toString());
    }
}
