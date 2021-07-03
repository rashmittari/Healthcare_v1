package com.example.healthcare_v1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class PharmacistHome extends AppCompatActivity {

    RelativeLayout pprofile,updateStock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacist_home);

        pprofile=findViewById(R.id.manageProfileButton);
        updateStock=findViewById(R.id.updateStocksButton);

        updateStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MedicineNameEntry.class));
                //finish();
            }
        });
        pprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),PManageProfile.class));
                //finish();
            }
        });
    }
}