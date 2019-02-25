package m.project.test.Login;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import m.project.test.MainActivity;
import m.project.test.MyApp;
import m.project.test.Network.TranslateServer.ListenerRequestTranslate;
import m.project.test.Network.TranslateServer.ResponseTranslate;
import m.project.test.Network.UserServer.ListenerRequestUser;
import m.project.test.Network.UserServer.ResponseUser;
import m.project.test.Network.UserServer.UserServer;
import m.project.test.R;
import m.project.test.User.User;

public class Login extends AppCompatActivity implements ListenerRequestTranslate, ListenerRequestUser {


    public String TAG = "Login";
    EditText usernameText,passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameText = findViewById(R.id.textLoginUsername);
        passwordText = findViewById(R.id.textLoginPassword);

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
            UserServer.getInstance().login(usernameText.getText().toString(),passwordText.getText().toString(),this);
        }else if(response.getCommand().equals("register")){
            moveOnRegister();
        }
    }

    @Override
    public void getResultUser(ResponseUser response) {
        Log.i(TAG,response.toString());
        if (response.getTypeRequest().equals("login")){
            Log.i(TAG,"it's a login action");
            if(!response.isError()) {
                Log.i(TAG,"pass to main activity");
                User.getInstance().setId(response.getId());
                User.getInstance().setUsername(response.getUsername());

                moveOnMainActivity();
            }else {
                //error find
                // We can display a message
            }
        }


    }
}
