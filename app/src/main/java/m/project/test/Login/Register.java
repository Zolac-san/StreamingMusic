package m.project.test.Login;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import m.project.test.MyApp;
import m.project.test.Network.TranslateServer.ListenerRequestTranslate;
import m.project.test.Network.TranslateServer.ResponseTranslate;
import m.project.test.R;

public class Register extends AppCompatActivity implements ListenerRequestTranslate {
    private String TAG = "Register";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Log.i(TAG, "In Register");
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

    public void goRegister(View view){
        Log.i(TAG, "In submit");
        finish();
    }

    @Override
    public void getResultCommand(ResponseTranslate response) {
        if(response.isError()) return;
        if(response.getCommand().equals("cancel")){
            finish();
        }
    }
}
