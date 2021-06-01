package com.example.healthcare_v1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class DrBookingDetails extends AppCompatActivity {

    TextView drnam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dr_booking_details);

        drnam = findViewById(R.id.dispname);

        drnam.setText(getIntent().getExtras().getString("dr_name"));

    }
}