package com.example.healthcare_v1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Prescription extends AppCompatActivity implements View.OnClickListener {

    LinearLayout layoutList;
    Button buttonAdd;
    Button buttonSubmitList;

    List<String> dosagelist = new ArrayList<>();
    ArrayList<MedicineDetail_modalclass> medicinelist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription);

        layoutList = findViewById(R.id.layout_list);
        buttonAdd = findViewById(R.id.button_add);
        buttonSubmitList = findViewById(R.id.button_submit_list);


        buttonAdd.setOnClickListener(this);
        buttonSubmitList.setOnClickListener(this);


        dosagelist.add("Dosage");
        dosagelist.add("1-1-1");
        dosagelist.add("1-0-0");
        dosagelist.add("0-1-0");
        dosagelist.add("0-0-1");
        dosagelist.add("1-1-0");
        dosagelist.add("1-0-1");
        dosagelist.add("0-1-1");

    }

    @Override
    public void onClick(View v) {
        //addView();

        switch (v.getId()){

            case R.id.button_add:

                addView();

                break;

            case R.id.button_submit_list:

                if(checkIfValidAndRead()){

                    String patient_uid = getIntent().getStringExtra("patient_uid");

                    Intent intent = new Intent(Prescription.this, Confirm_Prescription.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("list", medicinelist);
                    intent.putExtras(bundle);
                    intent.putExtra("pat_uid",patient_uid);
                    startActivity(intent);

                }

                break;
        }
    }

    private boolean checkIfValidAndRead() {
        medicinelist.clear();

        boolean result = true;

        for (int i=0;i<layoutList.getChildCount();i++){

            View cricketerView = layoutList.getChildAt(i);

            EditText editTextName = (EditText)cricketerView.findViewById(R.id.edit_medicine_name);
            AppCompatSpinner spinnerTeam = (AppCompatSpinner)cricketerView.findViewById(R.id.spinner_dose);

            MedicineDetail_modalclass medicineDetailmodalclass = new MedicineDetail_modalclass();

            if (!editTextName.getText().toString().equals("")){
                medicineDetailmodalclass.setCricketerName(editTextName.getText().toString());
            }else {
                result = false;
                break;
            }

            if(spinnerTeam.getSelectedItemPosition()!=0){
                medicineDetailmodalclass.setTeamName(dosagelist.get(spinnerTeam.getSelectedItemPosition()));
            }else {
                result = false;
                break;
            }

            medicinelist.add(medicineDetailmodalclass);

            //for (Cricketer s:cricketersList){
              //  Toast.makeText(this, s.getCricketerName(), Toast.LENGTH_LONG).show();
            //}
        }

        if(medicinelist.size()==0){
            result = false;
            Toast.makeText(this, "Add Medicines First!", Toast.LENGTH_SHORT).show();
        }else if(!result) {
            Toast.makeText(this, "Enter All Details Correctly!", Toast.LENGTH_SHORT).show();
        }

        return result;
    }

    private void addView() {

        View mediView = getLayoutInflater().inflate(R.layout.row_add_medicine,null,false);

        EditText editText = (EditText)mediView.findViewById(R.id.edit_medicine_name);
        AppCompatSpinner spinnerTeam = (AppCompatSpinner)mediView.findViewById(R.id.spinner_dose);
        ImageView imageClose = (ImageView)mediView.findViewById(R.id.image_remove);

        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, dosagelist);
        spinnerTeam.setAdapter(arrayAdapter);

        imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeView(mediView);
            }
        });

        layoutList.addView(mediView);


    }

    private void removeView(View view) {

        layoutList.removeView(view);

    }
}