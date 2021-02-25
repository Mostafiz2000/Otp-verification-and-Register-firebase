package com.example.otp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Add_Details extends AppCompatActivity {
EditText firstname,lastname,email,Password;
FirebaseAuth fauth;
FirebaseFirestore firebaseFirestore;
    String userID;
Button savebutton;
    public static final String TAG = "TAG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__details);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        firstname=findViewById(R.id.firstName);
        lastname=findViewById(R.id.lastName);
        email=findViewById(R.id.email);
        savebutton=findViewById(R.id.btn);
        Password=findViewById(R.id.password);
        fauth=FirebaseAuth.getInstance();

        firebaseFirestore=FirebaseFirestore.getInstance();

        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!firstname.getText().toString().isEmpty()&&!lastname.getText().toString().isEmpty()&&!email.getText().toString().isEmpty())
                {
                    Map<String,Object> user = new HashMap<>();
                    user.put("full",firstname.getText().toString());
                    user.put("Username",lastname.getText().toString());
                    user.put("Mobile",email.getText().toString());
                    user.put("Password",Password.getText().toString());

                    firebaseFirestore.collection("users")
                            .add(user)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {

                                    Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                    Intent i;
                                    finish();
                                    i = new Intent(getApplicationContext(), Register.class);

                                    startActivity(i);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Error adding document", e);
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