package com.example.theeagle.store.Activities;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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

import com.example.theeagle.store.Data.Book;
import com.example.theeagle.store.R;
import com.example.theeagle.store.Utilities.DatePickerFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

public class AddBook extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
    private Button set_date, get_pdf, upload_pdf;
    private EditText nanme_et, description_et, price_et;
    private ImageView book_image;
    private int GALLERY_REQUEST = 1;
    private int PICK_FILE_REQUEST_CODE = 2;
    private Uri selectedImageUri;
    private Uri pdfFileUri;
    FirebaseDatabase firebaseDatabase;
    public String BOOK_PDF_FOLDER = "Book_Pdf/";
    private Book book;
    public String REF_BOOK = "Book";
    private String bookId;
    private String id;
    private String imageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        imageRef = "image/";
        id=FirebaseAuth.getInstance().getCurrentUser().getUid();
        initViews();
        firebaseDatabase = FirebaseDatabase.getInstance();
        id = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    private void initViews() {
        set_date = findViewById(R.id.set_date);
        set_date.setOnClickListener(this);
        get_pdf = findViewById(R.id.get_book);
        get_pdf.setOnClickListener(this);
        upload_pdf = findViewById(R.id.add_book);
        upload_pdf.setOnClickListener(this);
        book_image = findViewById(R.id.book_image);
        book_image.setOnClickListener(this);
        nanme_et = findViewById(R.id.name_et);
        description_et = findViewById(R.id.description_et);
        price_et = findViewById(R.id.price_et);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.set_date) {
            DialogFragment dateDialog = new DatePickerFragment();
            dateDialog.show(getSupportFragmentManager(), "datePicker");
        } else if (v.getId() == R.id.get_book) {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("application/pdf");
            startActivityForResult(intent, PICK_FILE_REQUEST_CODE);

        } else if (v.getId() == R.id.add_book) {
            addNewBook();
        } else {
            showAlertDialog();
        }
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        set_date.setText(dayOfMonth + "/" + month + "/" + year);

    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chooser").setCancelable(true)
                .setItems(new String[]{"Gallery",}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == 0) {
                            Intent intent = new Intent(Intent.ACTION_PICK);
                            intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(intent, GALLERY_REQUEST);
                        }
                    }
                }).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {
            selectedImageUri = data.getData();
            readFileFromSelectedURI();
        } else if (requestCode == PICK_FILE_REQUEST_CODE && resultCode == RESULT_OK) {
            pdfFileUri = data.getData();
            getPdfFileTitle();
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
                book_image.setImageBitmap(image);
            }
        }
    }

    private void getPdfFileTitle() {
        String pdfFileName = "File Selected";
        Cursor cursor = getContentResolver().query(pdfFileUri, new String[]{MediaStore.Files.FileColumns.DISPLAY_NAME}, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
           pdfFileName = cursor.getString(0);
            get_pdf.setText(pdfFileName);
            get_pdf.setTextColor(Color.RED);
            cursor.close();
        }else {
            String[] split = pdfFileUri.toString().split("/");
            if (split.length > 0 && split[split.length - 1] != null) {
                pdfFileName = split[split.length - 1].replace("%20", " ");
            }
        }
    }

    private void uploadPdfFile(Uri pdfFileUri) {
        FirebaseStorage.getInstance().getReference().child(BOOK_PDF_FOLDER + book.getId() + ".pdf").putFile(pdfFileUri)
                .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    String pdfUrl = task.getResult().getDownloadUrl().toString();
                    book.setPdfUrl(pdfUrl);
                }
                uploadImage(selectedImageUri);
            }


        });

    }

    private void addNewBook() {
        String Name = get_pdf.getText().toString();
        String Description = description_et.getText().toString();
        double price = Double.parseDouble(price_et.getText().toString());
        String Date = set_date.getText().toString();
        String PublisherId = FirebaseAuth.getInstance().getUid();
        bookId = FirebaseDatabase.getInstance().getReference(REF_BOOK).push().getKey();
        book = new Book(bookId, Name, price, PublisherId, Description);
        book.setDescription(Description);
        book.setId(bookId);
        book.setTitle(Name);
        book.setPrice(price);
        book.setPublisherId(PublisherId);
        uploadPdfFile(pdfFileUri);

    }

    private void setBookData() {
        FirebaseDatabase.getInstance().getReference(REF_BOOK)
                .child(book.getId()).setValue(book).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    startActivity(new Intent(AddBook.this, MyBooks.class));
                }
            }
        });
    }

    private void uploadImage(final Uri selectedImageUri) {
        if (selectedImageUri != null) {
            FirebaseStorage.getInstance().getReference()
                    .child(imageRef + book.getId() + ".jpg").putFile(selectedImageUri)
                    .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if (task.isSuccessful()) {
                                String image_url = task.getResult().getDownloadUrl().toString();
                                book.setImageUrl(image_url);
                            }
                            setBookData();
                        }
                    });
        } else {
            Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show();
        }
    }
}