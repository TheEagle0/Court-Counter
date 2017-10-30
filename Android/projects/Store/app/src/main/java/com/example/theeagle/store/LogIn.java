package com.example.theeagle.store;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LogIn extends AppCompatActivity implements View.OnClickListener {

    int Permission_All = 1;
    String[] Permissions = {android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
    EditText email_et, password_et;
    Button login_btn, register_btn;
    TextView forget_password_tv;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        if (!hasPermission(this, Permissions)) {
            ActivityCompat.requestPermissions(this, Permissions, Permission_All);
        }
        initViews();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public boolean hasPermission(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }

            }
        }
        return true;
    }

    public void initViews() {
        email_et = findViewById(R.id.email_et);
        password_et = findViewById(R.id.password_et);
        login_btn = findViewById(R.id.login_btn);
        register_btn = findViewById(R.id.register_btn);
        forget_password_tv = findViewById(R.id.forget_password_tv);
        login_btn.setOnClickListener(this);
        register_btn.setOnClickListener(this);
        forget_password_tv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_btn:

                login();

                break;
            case R.id.register_btn:
                Intent intent = new Intent(LogIn.this, RegisterActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.forget_password_tv:

                break;
        }
    }

    private void login() {
        String email = email_et.getText().toString();
        String password = password_et.getText().toString();
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            email_et.setError("Please enter a valid email");
            return;
        }
        if (password.isEmpty() || password.length() < 6) {
            password_et.setError("Please enter a password contains at least 6 characters");
            return;
        }
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(LogIn.this, MyBooks.class));
                            finish();
                        }
                    }
                });

    }


}
