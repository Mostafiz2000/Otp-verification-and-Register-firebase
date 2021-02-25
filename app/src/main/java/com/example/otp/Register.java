package com.example.otp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Register extends AppCompatActivity {
    FirebaseAuth fauth;
    EditText phoneNumber,codeEnter;
    Button nextButton;
    ProgressBar progressBar;
    TextView state;
CountryCodePicker codePicker;
String verificationId;
    Boolean verifivationProgress=false;
    FirebaseFirestore fStore;

    PhoneAuthProvider.ForceResendingToken token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        fauth=FirebaseAuth.getInstance();
        phoneNumber=findViewById(R.id.phone);
        codeEnter=findViewById(R.id.codeEnter);
        progressBar=findViewById(R.id.progressBar);
        nextButton=findViewById(R.id.nextBtn);
        state=findViewById(R.id.state);
        codePicker=findViewById(R.id.ccp);
fStore=FirebaseFirestore.getInstance();


        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(verifivationProgress==false){
                if(!phoneNumber.getText().toString().isEmpty()&&phoneNumber.getText().toString().length()==10)
                {
String phonenum="+"+codePicker.getSelectedCountryCode()+phoneNumber.getText().toString();
                    Toast.makeText(Register.this, "Phone No->"+phonenum, Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.VISIBLE);
                    state.setVisibility(View.VISIBLE);
                    state.setText("Sending OTP.....");
                    nextButton.setText("VERIFY");

                    requestOTP(phonenum);

                }else
                {
                    phoneNumber.setError("Phone Number Is not Valid");

                }
            }
                else {

                        String userOtp=codeEnter.getText().toString();

                        if(!userOtp.isEmpty()&&userOtp.length()==6){
                            PhoneAuthCredential credential=PhoneAuthProvider.getCredential(verificationId,userOtp);
verifyAuth(credential);
                        }
                        else
                        {
                            codeEnter.setError("Valid OTP is required");
                        }
                }

            }

        });
    }


    private void checkUserProfile() {
        DocumentReference docRef=fStore.collection("users").document(fauth.getCurrentUser().getUid());
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists())
                {
                    Intent i;

                    i=new Intent(getApplicationContext(),newpassword.class);
                    i.putExtra("phoneNum","0"+phoneNumber.getText().toString());
                    startActivity(i);
                    finish();

                }
                else{
                    Toast.makeText(Register.this, "Invalid PhoneNumber", Toast.LENGTH_SHORT).show();
                    Intent i;
                    i=new Intent(getApplicationContext(),Add_Details.class);

                    startActivity(i);
                    finish();
                }
            }
        });
    }

    private void verifyAuth(PhoneAuthCredential credential) {
        fauth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(Register.this, "Successfull", Toast.LENGTH_SHORT).show();
                   checkUserProfile();
                }
                else
                {
                    Toast.makeText(Register.this, "Authenication Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void requestOTP(final String phonenum) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(phonenum, 60L, TimeUnit.SECONDS, this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                progressBar.setVisibility(View.GONE);
                state.setVisibility(View.GONE);
                codeEnter.setVisibility(View.VISIBLE);
                verificationId=s;
                verifivationProgress=true;
                token=forceResendingToken;

            }

            @Override
            public void onCodeAutoRetrievalTimeOut(String s) {
                super.onCodeAutoRetrievalTimeOut(s);
                Toast.makeText(Register.this, "OTP expired ,Re-Request for OTP", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
verifyAuth(phoneAuthCredential);


            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                progressBar.setVisibility(View.GONE);
                state.setVisibility(View.GONE);
                codeEnter.setVisibility(View.VISIBLE);
                codeEnter.setText(phonenum);
                Toast.makeText(Register.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}