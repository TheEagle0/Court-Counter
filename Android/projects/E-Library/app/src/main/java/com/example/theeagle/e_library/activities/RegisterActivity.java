package com.example.theeagle.e_library.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.theeagle.e_library.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText email_et, password_et;
    Button register_btn;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initViews();

        initFireBase();
    }

    private void initFireBase() {
        firebaseAuth = FirebaseAuth.getInstance();
    }

    private void initViews() {
        email_et = findViewById(R.id.email_login_et);
        password_et = findViewById(R.id.password_login_et);
        register_btn = findViewById(R.id.register_btn);
        register_btn.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_btn:
                addNewUser();
                break;
        }

    }
    private void addNewUser() {
        String email = email_et.getText().toString();
        String password = password_et.getText().toString();

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            email_et.setError(getString(R.string.valid_email_waring));
        }
      else if(password.isEmpty() || password.length() < 6) {
            password_et.setError(getString(R.string.password_warning));
        }else {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                  if (task.isSuccessful()){
                      startActivity(new Intent(RegisterActivity.this, InformationActivity.class));
                      finish();
                  }
                      else if (task.getException() instanceof FirebaseAuthUserCollisionException){
                          Toast.makeText(RegisterActivity.this, R.string.user_exist, Toast.LENGTH_SHORT).show();
                      }
                      else {
                      Toast.makeText(RegisterActivity.this, "Connection error", Toast.LENGTH_SHORT).show();
                  }
                  }

                });
    }
    }
}
