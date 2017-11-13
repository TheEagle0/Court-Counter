package com.example.theeagle.e_library.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import com.example.theeagle.e_library.R;
import com.example.theeagle.e_library.data.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

public class InformationActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText name_et, address_et, birthDate_et;
     Button add_btn, gender_btn;
    private ImageView profile_iv;
    private int GalleryRequest;
    private Uri selectedImageUri;
    String name;
     String address;
     String birthDate;
     String gender;
    private User user;
    private String imageRef;
    private String userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        imageRef="image/";
        userRef="User";
        GalleryRequest = 1;
        initViews();
    }

    private void initViews() {
        name_et = findViewById(R.id.name_et);
        address_et = findViewById(R.id.address_et);
        birthDate_et = findViewById(R.id.birth_date_et);
        add_btn = findViewById(R.id.add_btn);
        add_btn.setOnClickListener(this);
        gender_btn = findViewById(R.id.gender_btn);
        gender_btn.setOnClickListener(this);
        profile_iv = findViewById(R.id.information_iv);
        profile_iv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_btn:
                addNewUser();
                break;
            case R.id.gender_btn:
                showGenderDialog();
                break;
            case R.id.information_iv:
                pickImage();
                break;
        }
    }

    private void addNewUser() {
   name=name_et.getText().toString();
   address=address_et.getText().toString();
   birthDate=birthDate_et.getText().toString();
   gender=gender_btn.getText().toString();
   user=new User(name,address,birthDate,gender);
   user.setName(name);
   user.setAddress(address);
   user.setBirthDate(birthDate);
   uploadImage(selectedImageUri);

    }

    private void uploadImage(Uri selectedImageUri) {
        FirebaseStorage.getInstance().getReference().child(imageRef).putFile(selectedImageUri)
                .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()){
                    String image_url=task.getResult().getDownloadUrl().toString();
                    user.setImageUrl(image_url);
                }
                setNewUser();
            }
        });
    }

    private void setNewUser() {
        FirebaseDatabase.getInstance().getReference(userRef)
                .child(FirebaseDatabase.getInstance().getReference(userRef).push().getKey()).setValue(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                startActivity(new Intent(InformationActivity.this,MyBooksActivity.class));
                finish();
            }
        });
    }


    private void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, GalleryRequest);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GalleryRequest && resultCode == RESULT_OK) {
            selectedImageUri = data.getData();
            readFileFromSelectedURI();
        }
    }

    private void readFileFromSelectedURI() {
        Cursor cursor = getContentResolver().query(selectedImageUri,
                new String[]{MediaStore.Images.Media.DATA}, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                String imagePath = cursor.getString(0);
                cursor.close();
                Bitmap image = BitmapFactory.decodeFile(imagePath);
                profile_iv.setImageBitmap(image);
            }
        }
    }
    private void showGenderDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Select Gender").setCancelable(false).setItems(new  String[]{"Male","Female"}
        , new  DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which==0){
                    gender_btn.setText(R.string.male);
                }
                else {
                    gender_btn.setText(R.string.female);
                }
            }
        }).show();
    }

}