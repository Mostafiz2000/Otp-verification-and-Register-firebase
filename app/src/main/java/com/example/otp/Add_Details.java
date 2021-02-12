package com.example.otp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Add_Details extends AppCompatActivity {
EditText firstname,lastname,email;
FirebaseAuth fauth;
FirebaseFirestore firebaseFirestore;
    String userID;
Button savebutton;
    public static final String TAG = "TAG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__details);
        firstname=findViewById(R.id.firstName);
        lastname=findViewById(R.id.lastName);
        email=findViewById(R.id.email);
        savebutton=findViewById(R.id.saveBtn);
        fauth=FirebaseAuth.getInstance();
        userID = fauth.getCurrentUser().getUid();
        firebaseFirestore=FirebaseFirestore.getInstance();
        final DocumentReference docRef = firebaseFirestore.collection("users").document(userID);
        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!firstname.getText().toString().isEmpty()&&!lastname.getText().toString().isEmpty()&&!email.getText().toString().isEmpty())
                {
                    Map<String,Object> user = new HashMap<>();
                    user.put("first",firstname.getText().toString());
                    user.put("last",lastname.getText().toString());
                    user.put("email",email.getText().toString());
                    docRef.set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                            Log.d(TAG, "onSuccess: User Profile Created." + userID);
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            finish();}
                            else {
                                Toast.makeText(Add_Details.this, "Data is not Inserted", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
                else{
                    Toast.makeText(Add_Details.this, "All field Are Required", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

    }
}