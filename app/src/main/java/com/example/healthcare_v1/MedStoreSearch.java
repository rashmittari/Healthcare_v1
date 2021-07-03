package com.example.healthcare_v1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MedStoreSearch extends AppCompatActivity {

    Button searchMedBtn;
    EditText medicineName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med_store_search);

        searchMedBtn=findViewById(R.id.medicineSearchButton);
        medicineName=findViewById(R.id.medicineSearchName);

        searchMedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Dname= medicineName.getText().toString();

                Intent intent= new Intent(getApplicationContext(),MedStoreResults.class);
                intent.putExtra("drug", Dname);
                startActivity(intent);
                //finish();
            }
        });
    }
}