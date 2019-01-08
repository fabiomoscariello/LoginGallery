package com.example.fabiomoscariello.myapp.feature;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {


    EditText usernameText;
    EditText passText;
    CheckBox saveCheck;
    Button   loginButton;
    boolean isSaved;
    String username;
    String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        usernameText=findViewById(R.id.username_text);
        passText=findViewById(R.id.pass_text);
        saveCheck=findViewById(R.id.ricorda_checkbox);
        loginButton=findViewById(R.id.login_button);
        username=usernameText.getText().toString();
        password=passText.getText().toString();
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
