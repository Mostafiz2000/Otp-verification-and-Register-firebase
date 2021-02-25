package com.example.otp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private TextView t1;

    private ImageView i1;
    private RelativeLayout layout1;
    private Button b1,b2;
    private EditText e1,p1;
    private TextView t6,t7;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    Animation topanim,botomanim,sideanim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        b1=(Button)findViewById(R.id.b1);

getSupportActionBar().hide();
        e1=(EditText)findViewById(R.id.e1);
        p1=(EditText)findViewById(R.id.p1);
        t6=(TextView)findViewById(R.id.t11);
        i1=findViewById(R.id.imageView6);
t7=findViewById(R.id.t12);

        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        fStore.collection("mine").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                int f=0;
                List<DocumentSnapshot> snapshotList=queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot snapshot: snapshotList)
                {
                    if("null".contentEquals(""+snapshot.get("whos2")))
                    {
                        Toast.makeText(LoginActivity.this, "Welcome", Toast.LENGTH_SHORT).show();


                    }
                    else
                    {
                        Intent i;
                        i = new Intent(getApplicationContext(), Showlogin.class);

                        i.putExtra("k1",""+snapshot.get("whos2"));
                        startActivity(i);
                    }




                }

            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fStore.collection("users").whereEqualTo("Mobile", e1.getText().toString()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        int f=0;
                        List<DocumentSnapshot> snapshotList=queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot snapshot: snapshotList)
                        {
                            if(f==2)
                                f=2;
                            else f=1;

                            if(p1.getText().toString().contentEquals(""+snapshot.get("Password"))) {
                                Toast.makeText(LoginActivity.this, "OnSucess", Toast.LENGTH_SHORT).show();
                                DocumentReference docref= fStore.collection("mine")
                                        .document("keyw");
                                Map<String,Object> map=new HashMap<>();
                                map.put("whos2",e1.getText().toString());
                                docref.update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                    }
                                });
                                f=2;
                                Intent i;
                                i = new Intent(getApplicationContext(), Showlogin.class);
                                String  sname= e1.getText().toString();
                                i.putExtra("keysname",sname);
                                startActivity(i);
                                finish();
                                p1.setText("");
                            }




                        }
                        if(f==1) Toast.makeText(LoginActivity.this, "Forget Your Password", Toast.LENGTH_SHORT).show();
                        if(f==0)
                        {
                            Toast.makeText(LoginActivity.this, "No Such user", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

        });



        t6.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Intent i;
                i = new Intent(getApplicationContext(), Register.class);
                startActivity(i);
                return false;
            }
        });


t7.setOnTouchListener(new View.OnTouchListener() {
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        Intent i;
        i = new Intent(getApplicationContext(), Add_Details.class);
        startActivity(i);
        return false;
    }
});

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        return;
    }


}