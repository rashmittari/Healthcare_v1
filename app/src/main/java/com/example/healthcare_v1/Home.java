package com.example.healthcare_v1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class Home extends AppCompatActivity {
    Button logoutBtn,mRepoBtn,bookBtn,bookCard;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        fAuth =FirebaseAuth.getInstance();
        logoutBtn=findViewById(R.id.logoutbutton);
        mRepoBtn=findViewById(R.id.mreports);
        bookBtn=findViewById(R.id.bookbtn);
        bookCard=findViewById(R.id.bookcardeologist);

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fAuth.signOut();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });
        mRepoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),test.class));
            }
        });
        bookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String category = "Dentist";

                Intent intent= new Intent(getApplicationContext(),bookinglist.class);
                intent.putExtra("profession", category);
                startActivity(intent);
                //startActivity(new Intent(getApplicationContext(),bookinglist.class));
                finish();
            }
        });

        bookCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String category = "Cardeologist";

                Intent intent= new Intent(getApplicationContext(),bookinglist.class);
                intent.putExtra("profession", category);
                startActivity(intent);
                //startActivity(new Intent(getApplicationContext(),bookinglist.class));
                finish();
            }
        });
    }
}