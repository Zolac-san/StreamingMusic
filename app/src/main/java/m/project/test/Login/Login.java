package m.project.test.Login;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import m.project.test.MainActivity;
import m.project.test.MyApp;
import m.project.test.Network.TranslateServer.ListenerRequestTranslate;
import m.project.test.Network.TranslateServer.ResponseTranslate;
import m.project.test.R;

public class Login extends AppCompatActivity implements ListenerRequestTranslate {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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

    public void goOnMainActivity(View view){
        moveOnMainActivity();
    }

    public void goOnRegister(View view){
        moveOnRegister();
    }

    private void moveOnMainActivity(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    private void moveOnRegister(){
        Intent intent = new Intent(getApplicationContext(), Register.class);
        startActivity(intent);
    }

    @Override
    public void getResultCommand(ResponseTranslate response) {
        if(response.isError()) return;
        if(response.getCommand().equals("login")){
            moveOnMainActivity();
        }else if(response.getCommand().equals("register")){
            moveOnRegister();
        }
    }
}
