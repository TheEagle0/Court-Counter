package com.example.theeagle.store;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

     int Permission_All=1;
    String[]Permissions={Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (!hasPermission(this,Permissions)){
            ActivityCompat.requestPermissions(this,Permissions,Permission_All);
        }
    }
public boolean hasPermission(Context context,String...permissions){
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M&& context!=null&&permissions!=null){
        for(String permission :permissions){
            if(ActivityCompat.checkSelfPermission(context,permission)!= PackageManager.PERMISSION_GRANTED){
                return false;
            }

        }
        }
      return true;
}
}
