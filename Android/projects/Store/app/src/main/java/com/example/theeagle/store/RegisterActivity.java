package com.example.theeagle.store;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    public ImageView imageView;
    private static final int GALLERY_REQUEST = 1;
    private Uri selectedImageUri;
    private static final int Request_Image_capture = 2;
    String mCurrentPhotoPath;
    File file;
    private FirebaseAuth mAuth;
    private EditText UserName;
    private EditText Password;
    private EditText Address;
    private EditText User_Email;
     Button Register;
   FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        imageView = findViewById(R.id.iv);
        imageView.setOnClickListener(this);
        Register=findViewById(R.id.register_btn);
        Register.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
        UserName=findViewById(R.id.name_et);
        Password=findViewById(R.id.password_et);
        User_Email =findViewById(R.id.email_et);
        Address=findViewById(R.id.address_et);
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    //Todo Save Author Info & Profile Image
                    Intent intent=new Intent(RegisterActivity.this,MyBooks.class);
                    startActivity(intent);
                }
            }
        };
    }
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.iv)
        showAlertDialog();
        else addUser();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {
            selectedImageUri = data.getData();
            readFileFromSelectedURI();
        } else {
//            dispatchTakePictureIntent();
            Intent CaptureImage=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (CaptureImage.resolveActivity(getPackageManager()) != null) {
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(RegisterActivity.this,
                            "com.example.theeagle.store.Fileprovider", photoFile);
                    CaptureImage.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(CaptureImage, Request_Image_capture);
                }


            Bundle extras = data.getExtras();
            Bitmap image_bitmap = null;
            if (extras != null) {
                image_bitmap = (Bitmap) extras.get("data");
            }
            imageView.setImageBitmap(image_bitmap);
//
        }
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
                imageView.setImageBitmap(image);
            }
        }else {
            setPic();
        }
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chooser").setCancelable(true)
                .setItems(new String[]{"Gallery", "Camera"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == 0) {
                            Intent intent = new Intent(Intent.ACTION_PICK);
                            intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(intent, GALLERY_REQUEST);
                        }else {
                            dispatchTakePictureIntent();
                        }
                    }
                }).show();
    }

    private void dispatchTakePictureIntent() {
        Intent TakePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (TakePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(TakePictureIntent, Request_Image_capture);
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(new Date());
        String imageFileName = "JPEG" + timeStamp + "-";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void galleryAddImage() {
        Intent MediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        MediaScanIntent.setData(contentUri);
        this.sendBroadcast(MediaScanIntent);
    }

    private void setPic() {
        int targetW = imageView.getMaxWidth();
        int targetH = imageView.getHeight();
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;
        Bitmap bitmap=BitmapFactory.decodeFile(mCurrentPhotoPath,bmOptions);
        imageView.setImageBitmap(bitmap);


    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("Path", mCurrentPhotoPath);
        outState.putSerializable("File", file);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mCurrentPhotoPath = savedInstanceState.getString("Path");
       file = (File) savedInstanceState.getSerializable("File");
    }
    public void addUser(){
        String Name=UserName.getText().toString();
        String Email=User_Email.getText().toString();
        String User_Password=Password.getText().toString();
        String User_Address=Address.getText().toString();
        if (Name.isEmpty())
            UserName.setError("Pleas enter your name");
        else if (Email.isEmpty()||!Patterns.EMAIL_ADDRESS.matcher(Email).matches())
            User_Email.setError("Please enter a valid email");
        else if (User_Password.isEmpty()||Password.length()<6)
            Password.setError("Please enter at least 6 characters");
        else if (User_Address.isEmpty())
            Address.setError("Please enter your address");
        else{
        mAuth.createUserWithEmailAndPassword(Email,User_Password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()){
                            if (task.getException()instanceof FirebaseAuthUserCollisionException)
                                Toast.makeText(RegisterActivity.this, R.string.email_already_exist, Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(RegisterActivity.this, R.string.error_in_connection, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this,LogIn.class));
    }
}
