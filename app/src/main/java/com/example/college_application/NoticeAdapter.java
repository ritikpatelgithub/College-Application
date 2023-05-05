package com.example.college_application;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.NoticeVIewAdapter> {

    private Context context;
    private ArrayList<NoticeData> list;

    public NoticeAdapter(Context context, ArrayList<NoticeData> list) {
        this.context=context;
        this.list=list;

    }

    @NonNull
    @Override
    public NoticeVIewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.newsfeed_item_layout,parent,false);
        return new NoticeVIewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeVIewAdapter holder, @SuppressLint("RecyclerView") int position) {

        NoticeData currentItem =list.get(position);
        holder.deleteNoticeTitle.setText(currentItem.getTitle());
        try{
            if (currentItem.getImage()!=null) {
                Picasso.get().load(currentItem.getImage()).into(holder.deleteNoticeImage);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        holder.deleteNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setMessage("Are you Sure for Delete the Notice");
                builder.setCancelable(true);
                builder.setPositiveButton(
                        "OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Notice");
                                reference.child(currentItem.getKey()).removeValue()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(context, "Something Went Wrong", Toast.LENGTH_SHORT).show();

                                            }
                                        });
                                notifyItemRemoved(position);

                            }
                        }
                );
                builder.setNegativeButton(
                        "Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }

                );

                AlertDialog dilog=null;
                try{
                    dilog=builder.create();
                }catch (Exception e){
                    e.printStackTrace();
                }

               if (dilog!=null){
                   dilog.show();
               }


//                DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Notice");
//                reference.child(currentItem.getKey()).removeValue()
//                        .addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
//                            }
//                        }).addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Toast.makeText(context, "Something Went Wrong", Toast.LENGTH_SHORT).show();
//
//                            }
//                        });
//                notifyItemRemoved(position);

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class NoticeVIewAdapter extends RecyclerView.ViewHolder{

        private AppCompatButton deleteNotice;
        private TextView deleteNoticeTitle;
        private ImageView deleteNoticeImage;

        public NoticeVIewAdapter(@NonNull View itemView) {
            super(itemView);

            deleteNotice=itemView.findViewById(R.id.deleteNotice);
            deleteNoticeTitle=itemView.findViewById(R.id.deleteNoticeTitle);
            deleteNoticeImage=itemView.findViewById(R.id.deleteNoticeImage);

        }
    }
}
