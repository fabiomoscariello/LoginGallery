package com.example.fabiomoscariello.myapp.feature;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {


    private EditText usernameText;
    private EditText passText;
    private CheckBox saveCheck;
    private Button loginButton;

    private boolean isSaved;
    private String username;
    private String password;
    private Context context;
    private Intent intent;
    private static final String TAG = "LoginActivity ";
    private SharedPreferences sharedLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        context = LoginActivity.this;
        Log.d(TAG, TAG + "onCreate");
        usernameText = findViewById(R.id.username_text);
        passText = findViewById(R.id.pass_text);
        saveCheck = findViewById(R.id.ricorda_checkbox);
        loginButton = findViewById(R.id.login_button);
        sharedLogin = context.getSharedPreferences("com.example.fabiomoscariello.myapp", Context.MODE_PRIVATE);
        intent=GalleryActivity.newIntent(context, username, password);
        caricaCredenziali();
        if(isSaved){
            Log.d(TAG,TAG+"LoginDiretto");
            usernameText.setText(username);
            passText.setText(password);
            saveCheck.setChecked(isSaved);
            startActivity(intent);
            finish();
        }
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = usernameText.getText().toString();
                password = passText.getText().toString();
                isSaved=saveCheck.isChecked();
                if(checkLogin()){
                    if(isSaved) {
                        salvaCredenziali();
                    }
                }else{
                    Toast.makeText(context,"User o password errata",Toast.LENGTH_SHORT);
                }
                startActivity(intent);
                Log.d(TAG, "GalleryActivity lanciata");
                finish();

            }
        });

        }

    private void caricaCredenziali() {
        Log.d(TAG,TAG+"caricaCredenziali");
    username=sharedLogin.getString("utente","default");
    password=sharedLogin.getString("password","default");
    isSaved=sharedLogin.getBoolean("salvato",false);
    }

    private void salvaCredenziali() {
        Log.d(TAG,TAG+"salvaCredenziali");
        SharedPreferences.Editor editor = sharedLogin.edit();
        editor.putString("utente", username);
        editor.putString("password",password);
        editor.putBoolean("salvato",isSaved);
        editor.commit();
    }

    public boolean checkLogin(){
        Log.d(TAG,TAG+"checkLogin");
        return username.equals("User") && password.equals("biberon");
    }
}
