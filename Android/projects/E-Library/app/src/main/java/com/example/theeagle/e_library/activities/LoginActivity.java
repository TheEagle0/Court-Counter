package com.example.theeagle.e_library.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.theeagle.e_library.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText email_et,password_et;
    private Button login_btn,register_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
    }

    private void initViews() {
        email_et=findViewById(R.id.email_login_et);
        password_et=findViewById(R.id.password_login_et);
        login_btn=findViewById(R.id.login_btn);
        login_btn.setOnClickListener(this);
        register_btn=findViewById(R.id.register_btn);
        register_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login_btn:
                break;
            case R.id.register_btn:
                startActivity(new Intent(this,RegisterActivity.class));
                break;
        }
    }
}
