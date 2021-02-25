package com.example.otp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

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