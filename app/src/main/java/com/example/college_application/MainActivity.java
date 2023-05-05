package com.example.college_application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    CardView uploadNotice,addGalleryImage,addEBook,addFaculty,deleteNotice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        uploadNotice=findViewById(R.id.addNotice);
        addGalleryImage=findViewById(R.id.addGalleryImage);
        addEBook=findViewById(R.id.addEBook);
        addFaculty=findViewById(R.id.addFaculty);
        deleteNotice=findViewById(R.id.deleteNotice);


        Intent intent4=new Intent(MainActivity.this, DeleteNoticeActivity.class);
        deleteNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent4);
            }
        });


        Intent intent3=new Intent(MainActivity.this,UploadFaculty.class);
        addFaculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent3);
            }
        });


        Intent intent2=new Intent(MainActivity.this,UploadPdfActivity.class);
        addEBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent2);
            }
        });

        Intent intent =new Intent(MainActivity.this, UploadNotice.class);
        uploadNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });

        Intent intent1=new Intent(MainActivity.this,UploadImage.class);
        addGalleryImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent1);
            }
        });
    }
}