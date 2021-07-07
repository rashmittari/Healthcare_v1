package com.example.healthcare_v1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.google.firebase.auth.FirebaseAuth;

public class DoctorHome extends AppCompatActivity {
    Button doctorlogoutBtn;
    FirebaseAuth fAuth;
    RelativeLayout clinicDetails,viewApts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_home);

        clinicDetails=findViewById(R.id.updateclinicdetailsButton);
        viewApts=findViewById(R.id.viewupcomingappointmentsButton);

        fAuth =FirebaseAuth.getInstance();
        doctorlogoutBtn=findViewById(R.id.dlogoutbutton);
        doctorlogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fAuth.signOut();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });

        clinicDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ClinicDetails.class));
                //finish();
            }
        });

        viewApts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Doctor_Appointments.class));
                //finish();
            }
        });
    }
}