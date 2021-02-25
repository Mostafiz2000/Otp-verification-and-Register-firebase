package com.example.otp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class Account extends AppCompatActivity {
ImageView imageView;
TextView t1,t2;
FirebaseFirestore fStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        imageView=findViewById(R.id.imageView);
        fStore=FirebaseFirestore.getInstance();
        t1=findViewById(R.id.e1);
        t2=findViewById(R.id.p1);
        getSupportActionBar().hide();
        fStore.collection("mine").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                int f=0;
                List<DocumentSnapshot> snapshotList=queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot snapshot: snapshotList)
                {

String s=""+snapshot.get("whos2");

                    fStore.collection("users").whereEqualTo("Mobile",s).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            int f=0;
                            List<DocumentSnapshot> snapshotList=queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot snapshot: snapshotList)
                            {
                                t1.setText(""+snapshot.get("full"));
                                t2.setText(""+snapshot.get("Mobile"));
                                String image=""+snapshot.get("Image");
                                Glide.with(Account.this)
                                        .asBitmap()
                                        .load("https://firebasestorage.googleapis.com/v0/b/otpfirebaseproject-25749.appspot.com/o/118983282_2837245576499013_4085985561122957882_o.jpg?alt=media&token=e340724b-ff36-4e25-bb28-d83af6c9e590")
                                        .into(imageView);



                            }

                        }
                    });


                }

            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent();
                i.setAction(Intent.ACTION_GET_CONTENT);
                i.setType("Image/*");
                startActivityForResult(i,1000);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null)
        {
            if(data.getData()!=null)
            {
                imageView.setImageURI(data.getData());
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i;
        i=new Intent(getApplicationContext(),Showlogin.class);
        startActivity(i);
        finish();
        return;
    }
}