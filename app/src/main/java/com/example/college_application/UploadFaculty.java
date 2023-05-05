package com.example.college_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UploadFaculty extends AppCompatActivity {
    FloatingActionButton fab;
    private RecyclerView csDepartment,civilDepartment,mechanicalDepartment,electricalDepartment;
    private LinearLayout csNodata,civilNodata,mechanicalNodata,electricalNodata;

    private List<TeacherData> list1,list2,list3,list4;
    private DatabaseReference reference,dbRef;
    private TeacherAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_faculty);
        fab=findViewById(R.id.fab);
        csDepartment=findViewById(R.id.csDepartment);
        civilDepartment=findViewById(R.id.civilDepartment);
        mechanicalDepartment=findViewById(R.id.mechanicalDepartment);
        electricalDepartment=findViewById(R.id.electricalDepartment);
        csNodata=findViewById(R.id.csNodata);
        civilNodata=findViewById(R.id.civilNodata);
        mechanicalNodata=findViewById(R.id.mechanicalNodata);
        electricalNodata=findViewById(R.id.electricalNodata);

        reference= FirebaseDatabase.getInstance().getReference().child("Teachers");
        csDepartment();
        civilDepartment();
        mechanicalDepartment();
        electricalDepartment();


        Intent intent=new Intent(UploadFaculty.this,AddTeacher.class);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);

            }
        });
    }

    private void electricalDepartment() {

        dbRef=reference.child("Electrical");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list4=new ArrayList<>();
                if (!snapshot.exists()){
                    electricalNodata.setVisibility(View.VISIBLE);
                    csDepartment.setVisibility(View.GONE);
                }else{
                    electricalNodata.setVisibility(View.GONE);
                    electricalDepartment.setVisibility(View.VISIBLE);
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                        TeacherData data=dataSnapshot.getValue(TeacherData.class);
                        list4.add(data);
                    }
                    electricalDepartment.setHasFixedSize(true);
                    electricalDepartment.setLayoutManager(new LinearLayoutManager(UploadFaculty.this));
                    adapter=new TeacherAdapter(list4, UploadFaculty.this,"Electrical");
                    electricalDepartment.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UploadFaculty.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }


    private void mechanicalDepartment() {

        dbRef=reference.child("Mechanical");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list3=new ArrayList<>();
                if (!snapshot.exists()){
                    mechanicalNodata.setVisibility(View.VISIBLE);
                    mechanicalDepartment.setVisibility(View.GONE);
                }else{
                    mechanicalNodata.setVisibility(View.GONE);
                    mechanicalDepartment.setVisibility(View.VISIBLE);
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                        TeacherData data=dataSnapshot.getValue(TeacherData.class);
                        list3.add(data);
                    }
                    mechanicalDepartment.setHasFixedSize(true);
                    mechanicalDepartment.setLayoutManager(new LinearLayoutManager(UploadFaculty.this));
                    adapter=new TeacherAdapter(list3, UploadFaculty.this,"Mechanical");
                    mechanicalDepartment.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UploadFaculty.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }


    private void civilDepartment() {
        dbRef=reference.child("Civil");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list2=new ArrayList<>();
                if (!snapshot.exists()){
                    civilNodata.setVisibility(View.VISIBLE);
                    civilDepartment.setVisibility(View.GONE);
                }else{
                    civilNodata.setVisibility(View.GONE);
                    civilDepartment.setVisibility(View.VISIBLE);
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                        TeacherData data=dataSnapshot.getValue(TeacherData.class);
                        list2.add(data);
                    }
                    civilDepartment.setHasFixedSize(true);
                    civilDepartment.setLayoutManager(new LinearLayoutManager(UploadFaculty.this));
                    adapter=new TeacherAdapter(list2, UploadFaculty.this,"Civil");
                    civilDepartment.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UploadFaculty.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }



    private void csDepartment() {
        dbRef=reference.child("Computer Science");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    list1=new ArrayList<>();
                    if (!snapshot.exists()){
                        csNodata.setVisibility(View.VISIBLE);
                        csDepartment.setVisibility(View.GONE);
                    }else{
                        csNodata.setVisibility(View.GONE);
                        csDepartment.setVisibility(View.VISIBLE);
                        for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                            TeacherData data=dataSnapshot.getValue(TeacherData.class);
                            list1.add(data);
                        }
                        csDepartment.setHasFixedSize(true);
                        csDepartment.setLayoutManager(new LinearLayoutManager(UploadFaculty.this));
                        adapter=new TeacherAdapter(list1, UploadFaculty.this,"Computer Science");
                        csDepartment.setAdapter(adapter);
                    }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UploadFaculty.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }


}