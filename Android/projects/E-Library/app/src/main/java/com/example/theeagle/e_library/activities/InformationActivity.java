package com.example.theeagle.e_library.activities;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.theeagle.e_library.R;
import com.example.theeagle.e_library.data.User;
import com.example.theeagle.e_library.utilities.BirthDate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import static com.example.theeagle.e_library.R.string.birth_date;

public class InformationActivity extends AppCompatActivity implements View.OnClickListener ,DatePickerDialog.OnDateSetListener{
    private EditText name_et, address_et;
    Button add_btn, gender_btn , birthDate_btn;
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
    String photoRef;
    FirebaseAuth firebaseAuth;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        imageRef = "image/";
        userRef = "User";
        GalleryRequest = 1;
        photoRef = "Photo";
        firebaseAuth = FirebaseAuth.getInstance();

        initViews();
    }

    private void initViews() {
        name_et = findViewById(R.id.name_et);
        address_et = findViewById(R.id.address_et);
        birthDate_btn = findViewById(R.id.birth_date_et);
        birthDate_btn.setOnClickListener(this);
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
            case R.id.birth_date_et:
                DialogFragment dateDialog = new BirthDate();
                dateDialog.show(getSupportFragmentManager(), "datePicker");
                break;
        }
    }

    private void addNewUser() {
        id=FirebaseAuth.getInstance().getCurrentUser().getUid();
        name = name_et.getText().toString();
        address = address_et.getText().toString();
        birthDate = birthDate_btn.getText().toString();
        gender = gender_btn.getText().toString();
        if (name.isEmpty())
            name_et.setError("Please enter your name");
        else if (address.isEmpty())
            address_et.setError("Please enter your address");
        else if (birthDate.equals( R.string.birth_date))
            Toast.makeText(this, "Please select your birth date", Toast.LENGTH_SHORT).show();
        else if (gender.equals(R.string.gender))
            Toast.makeText(this, "Please select your gender", Toast.LENGTH_SHORT).show();
            else {
            user = new User(name, address, birthDate, gender);
            user.setName(name);
            user.setAddress(address);
            user.setBirthDate(birthDate);
            user.setId(id);
            user.setGender(gender);
            uploadImage(selectedImageUri);

        }
    }

    private void uploadImage(final Uri selectedImageUri) {
        if (selectedImageUri!=null){
        FirebaseStorage.getInstance().getReference()
                .child(imageRef + id + ".jpg").putFile(selectedImageUri)
                .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if (task.isSuccessful()) {
                            String image_url = task.getResult().getDownloadUrl().toString();
                            user.setImageUrl(image_url);
                        }
                        setNewUser();
                    }
                });}
                else {
            Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show();
        }
    }

    private void setNewUser() {
        FirebaseDatabase.getInstance().getReference(userRef)
                .child(user.getId())
                        .setValue(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        startActivity(new Intent(InformationActivity.this, MyBooksActivity.class));
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

    private void showGenderDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Gender").setCancelable(false).setItems(new String[]{"Male", "Female"}
                , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            gender_btn.setText(R.string.male);
                        } else {
                            gender_btn.setText(R.string.female);
                        }
                    }
                }).show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date=(dayOfMonth+"/"+(month+1)+"/"+year);
        birthDate_btn.setText(date);

    }
}
