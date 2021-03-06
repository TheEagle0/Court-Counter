package com.example.theeagle.store.Activities;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkFireBase();

    }

    public void checkFireBase() {
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {

            Intent intent = new Intent(MainActivity.this, LogIn.class);
            startActivity(intent);
            finish();

        } else {
            Intent intent = new Intent(MainActivity.this, MyBooks.class);
            startActivity(intent);
            finish();
        }
    }

}

