package com.example.college_application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class UploadImage extends AppCompatActivity {
    private Spinner imageCategory;
    private CardView selectImage;
    private Button uploadImage;
    private ImageView galleryImageView;

    private String category;
    private final int REQ=1;
    private Bitmap bitmap;
    private ProgressDialog pd;
    private DatabaseReference reference;
    private StorageReference storageReference;
    String downloadUrl= "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);


        reference= FirebaseDatabase.getInstance().getReference().child("Image");
        storageReference= FirebaseStorage.getInstance().getReference().child("Image");

        imageCategory=findViewById(R.id.image_category);
        selectImage=findViewById(R.id.addGalleryImage);
        uploadImage=findViewById(R.id.uploadImageBtn);
        galleryImageView=findViewById(R.id.galleryImageView);
        pd=new ProgressDialog(this);


        String[] items=new String[]{"Select Category","Convocation","Independence Day","Other Events"};
        imageCategory.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,items));
        imageCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category=imageCategory.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (imageCategory.getText().toString().isEmpty()){
//                    imageCategory.setError("Empty");
//                    imageCategory.requestFocus();}
                  if (bitmap==null) {
                      Toast.makeText(UploadImage.this, "please upload Image", Toast.LENGTH_SHORT).show();
                   // uploadData();
                }
                else if(category.equals("Select Category")){
                    //uploadImage();
                      Toast.makeText(UploadImage.this, "Please Image Category", Toast.LENGTH_SHORT).show();
                }
                else{
                    pd.setMessage("Uploading....");
                    pd.show();
                    uploadImage();
                  }
            }
        });


    }

    private void openGallery() {

        Intent pickImage=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickImage,REQ);

    }
    private void uploadImage() {
        pd.setMessage("Uploading");
        pd.show();
        ByteArrayOutputStream baos =new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,baos);
        byte[] finalimg= baos.toByteArray();
        final StorageReference filePath;
        filePath=storageReference.child(finalimg+"jpg");
        final UploadTask uploadTask=filePath.putBytes(finalimg);
        uploadTask.addOnCompleteListener(UploadImage.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                if (task.isSuccessful()){
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    downloadUrl=String.valueOf(uri);
                                    uploadData();

                                }
                            });
                        }
                    });
                }else {
                    pd.dismiss();
                    Toast.makeText(UploadImage.this,"Something went wrong",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private void uploadData() {
        reference=reference.child(category);
        final String uniqueKey=reference.push().getKey();
      //  String title=imageCategory.getText().toString();

//        Calendar calForDate= Calendar.getInstance();
//        SimpleDateFormat currentDate=new SimpleDateFormat("dd-MM-yy");
//        String date=currentDate.format(calForDate.getTime());
//
//        Calendar calForTime=Calendar.getInstance();
//        SimpleDateFormat currentTime=new SimpleDateFormat("hh:mm a");
//        String time=currentTime.format(calForTime.getTime());
//
//        NoticeData noticeData=new NoticeData(title,downloadUrl,date,time,uniqueKey);
        reference.child(uniqueKey).setValue(downloadUrl).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                pd.dismiss();
                Toast.makeText(UploadImage.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(UploadImage.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();

            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQ && resultCode==RESULT_OK){
            Uri uri =data.getData();
            try {
                bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            galleryImageView.setImageBitmap(bitmap);
        }
    }
}