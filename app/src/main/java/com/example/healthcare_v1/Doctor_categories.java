package com.example.healthcare_v1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class Doctor_categories extends AppCompatActivity {

    RelativeLayout dentist,cardeologist,generalphysician;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_categories);

        dentist=findViewById(R.id.dentistcategory);
        cardeologist=findViewById(R.id.cardeologistcategory);
        dentist.setOnClickListener(new View.OnClickListener() {
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

        cardeologist.setOnClickListener(new View.OnClickListener() {
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