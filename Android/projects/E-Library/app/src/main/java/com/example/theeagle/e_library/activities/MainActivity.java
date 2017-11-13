package com.example.theeagle.e_library.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CheckFireBase();
    }
    private void CheckFireBase(){
        if (FirebaseAuth.getInstance().getCurrentUser()==null){
            startActivity(new Intent(this,LoginActivity.class));
            finish();
        }
        else {
            startActivity(new Intent(this,MyBooksActivity.class));
            finish();
        }
    }
}
