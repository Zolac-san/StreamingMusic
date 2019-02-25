package m.project.test.Login;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import m.project.test.MyApp;
import m.project.test.Network.TranslateServer.ListenerRequestTranslate;
import m.project.test.Network.TranslateServer.ResponseTranslate;
import m.project.test.Network.UserServer.ListenerRequestUser;
import m.project.test.Network.UserServer.ResponseUser;
import m.project.test.Network.UserServer.UserServer;
import m.project.test.R;
import m.project.test.User.User;

public class Register extends AppCompatActivity implements ListenerRequestTranslate, ListenerRequestUser {
    private String TAG = "Register";

    private EditText usernameText, passwordText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        usernameText = findViewById(R.id.textRegisterUsername);
        passwordText = findViewById(R.id.textRegisterPassword);

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
        if(response.getCommand().equals("register")){
            UserServer.getInstance().register(usernameText.getText().toString(),passwordText.getText().toString(),this);
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
}
