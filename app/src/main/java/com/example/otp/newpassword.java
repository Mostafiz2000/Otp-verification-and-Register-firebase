package com.example.otp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class newpassword extends AppCompatActivity {
    FirebaseFirestore fStore;
    EditText t1,t2;
    Button b1;
    String phone;
    int f=0;
String pnoneNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newpassword);
        fStore=FirebaseFirestore.getInstance();
        Intent intent=getIntent();
        phone = intent.getStringExtra("phoneNum");

t1=findViewById(R.id.add1);

b1=findViewById(R.id.btn);

String id=fStore.collection("users").getId();
b1.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        fStore.collection("users").whereEqualTo("Mobile", phone).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                List<DocumentSnapshot> snapshotList=queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot snapshot: snapshotList)
                {
                    if(f==0){
                   t1.setText(""+snapshot.get("Password"));
                   f=1;}
                    else {
                        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                        finish();
                    }



                }

            }
        });
    }
});


    }
}