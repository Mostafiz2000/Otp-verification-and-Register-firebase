package com.example.otp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.otp.databinding.ActivityLoginBinding;
import com.example.otp.databinding.ActivityShowloginBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Showlogin extends AppCompatActivity {
private Button b12;
FirebaseFirestore fStore;
FirebaseDatabase database;
    ArrayList<User> users;
ActivityShowloginBinding binding;
UsersAdapter usersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showlogin);
fStore=FirebaseFirestore.getInstance() ;
database=FirebaseDatabase.getInstance();
        binding = ActivityShowloginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

users=new ArrayList<>();



usersAdapter=new UsersAdapter(this,users);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.account,menu);
        return  true;


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                Intent i;
                i=new Intent(getApplicationContext(),Account.class);

                startActivity(i);
                finish();
                return true;
            case R.id.item2:
                DocumentReference docref= fStore.collection("mine")
                        .document("keyw");
                Map<String,Object> map=new HashMap<>();
                map.put("whos2","null");
                docref.update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                });
                i=new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(i);
                finish();
                return true;
            case R.id.item3:
                i=new Intent(getApplicationContext(),Settings.class);
                startActivity(i);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}