package com.example.healthcare_v1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClinicDetails extends AppCompatActivity {

    EditText CFname,CLastname,CQualifications,CProfession,CTimings,CAddress,CFees,CMobileno;
    Button uptCbtn;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID,data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinic_details);

        CFname = findViewById(R.id.firstnameC);
        CLastname = findViewById(R.id.lastnameC);
        CQualifications = findViewById(R.id.qualificationsC);
        CProfession = findViewById(R.id.professionC);
        CTimings = findViewById(R.id.timingsC);
        CAddress = findViewById(R.id.addressC);
        CMobileno = findViewById(R.id.mobilenoC);
        CFees = findViewById(R.id.feesC);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        uptCbtn= findViewById(R.id.clinicdetailsupdateBtn);
        uptCbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CFname.getText().toString().isEmpty()){
                    CFname.setError("field is empty");
                    return;
                }
                if(CLastname.getText().toString().isEmpty()){
                    CLastname.setError("field is empty");
                    return;
                }
                if(CQualifications.getText().toString().isEmpty()){
                    CQualifications.setError("field is empty");
                    return;
                }
                if(CProfession.getText().toString().isEmpty()){
                    CProfession.setError("field is empty");
                    return;
                }
                if(CFees.getText().toString().isEmpty()){
                    CFees.setError("field is empty");
                    return;
                }
                if(CTimings.getText().toString().isEmpty()){
                    CTimings.setError("field is empty");
                    return;
                }
                if(CAddress.getText().toString().isEmpty()){
                    CAddress.setError("field is empty");
                    return;
                }
                if(CMobileno.getText().toString().isEmpty()){
                    CMobileno.setError("field is empty");
                    return;
                }

                String z=CFname.getText().toString()+ CLastname.getText().toString();
                String slots = CTimings.getText().toString();
                String[] tagArray2= slots.split("\\s*,\\s*");
                List<String> tags2 = Arrays.asList(tagArray2);

                userID=fAuth.getCurrentUser().getUid();
                DocumentReference documentReference=fStore.collection("users").document("doctors")
                        .collection(CProfession.getText().toString()).document(userID);
                Map<String,Object> userx = new HashMap<>();
                userx.put("name",z);

                documentReference.set(userx).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        //startActivity(new Intent(getApplicationContext(),DoctorHome.class));
                       // Toast.makeText(ClinicDetails.this,"Profile Created",Toast.LENGTH_LONG).show();
                        //finish();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });

                Map<String,Object> user = new HashMap<>();
                user.put("profession",CProfession.getText().toString());
                user.put("address",CAddress.getText().toString());
                user.put("fees",CFees.getText().toString());
                user.put("qualifications",CQualifications.getText().toString());
                user.put("mobile_no",CMobileno.getText().toString());
                user.put("user_id",userID);
                user.put("Time_slots",tags2);
                documentReference.update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        startActivity(new Intent(getApplicationContext(),DoctorHome.class));
                        Toast.makeText(ClinicDetails.this,"Profile Updated also",Toast.LENGTH_LONG).show();
                        //finish();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });

                updateolddetails();


            }
        });





    }

    private void updateolddetails() {

        DocumentReference documentReference2=fStore.collection("users").document(userID);
        //.collection(CProfession.getText().toString()).document(userID);

        Map<String,Object> userq = new HashMap<>();
        userq.put("Type",CProfession.getText().toString());

        documentReference2.update(userq).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                //startActivity(new Intent(getApplicationContext(),DoctorHome.class));
                // Toast.makeText(ClinicDetails.this,"Profile Created",Toast.LENGTH_LONG).show();
                //finish();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}