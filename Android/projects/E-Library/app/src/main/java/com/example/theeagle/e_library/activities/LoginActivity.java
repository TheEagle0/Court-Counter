package com.example.theeagle.e_library.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.theeagle.e_library.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText email_et,password_et;
    Button login_btn,register_btn;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
        initFirebase();
    }

    private void initFirebase() {
        firebaseAuth=FirebaseAuth.getInstance();
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
                login();
                break;
            case R.id.register_btn:
                startActivity(new Intent(this,RegisterActivity.class));
                break;
        }
    }

    private void login() {
        String email=email_et.getText().toString();
        String password=password_et.getText().toString();
        if (email.isEmpty()||!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            email_et.setError(getString(R.string.valid_email_waring));
        }
        else if (password.isEmpty()||password.length()<6){
            password_et.setError(getString(R.string.password_warning));
        }else {
            firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        startActivity(new Intent(LoginActivity.this,MyBooksActivity.class));
                        finish();
                    }
                }
            });
        }
    }
}
