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

public class UserProfile extends AppCompatActivity {

    EditText PFname,PLastname,PAge,PGender,PHeight,PWeight,PMobileno,PDob;
    Button uptCbtn2;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID,data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        PFname = findViewById(R.id.firstnameP);
        PLastname = findViewById(R.id.lastnameP);
        PAge = findViewById(R.id.ageP);
        PGender = findViewById(R.id.genderP);
        PHeight = findViewById(R.id.heightP);
        PWeight = findViewById(R.id.weightP);
        PMobileno = findViewById(R.id.mobilenoP);
        PDob = findViewById(R.id.dobP);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        uptCbtn2= findViewById(R.id.userdataupldBtn);
        uptCbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(PFname.getText().toString().isEmpty()){
                    PFname.setError("field is empty");
                    return;
                }
                if(PLastname.getText().toString().isEmpty()){
                    PLastname.setError("field is empty");
                    return;
                }
                if(PAge.getText().toString().isEmpty()){
                    PAge.setError("field is empty");
                    return;
                }
                if(PGender.getText().toString().isEmpty()){
                    PGender.setError("field is empty");
                    return;
                }
                if(PHeight.getText().toString().isEmpty()){
                    PHeight.setError("field is empty");
                    return;
                }
                if(PWeight.getText().toString().isEmpty()){
                    PWeight.setError("field is empty");
                    return;
                }
                if(PMobileno.getText().toString().isEmpty()){
                    PMobileno.setError("field is empty");
                    return;
                }
                if(PDob.getText().toString().isEmpty()){
                    PDob.setError("field is empty");
                    return;
                }

                String z=PFname.getText().toString()+" "+ PLastname.getText().toString();
               // String slots = CTimings.getText().toString();
                //String[] tagArray2= slots.split("\\s*,\\s*");
                //List<String> tags2 = Arrays.asList(tagArray2);

                userID=fAuth.getCurrentUser().getUid();
                DocumentReference documentReference=fStore.collection("users").document("patients")
                        .collection("all").document(userID);
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
                user.put("age",PAge.getText().toString());
                user.put("gender",PGender.getText().toString());
                user.put("height",PHeight.getText().toString());
                user.put("weight",PWeight.getText().toString());
                user.put("mobile_no",PMobileno.getText().toString());
                user.put("user_id",userID);
                //user.put("Time_slots",tags2);
                documentReference.update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        startActivity(new Intent(getApplicationContext(),Home.class));
                        Toast.makeText(UserProfile.this,"Profile Updated also",Toast.LENGTH_LONG).show();
                        //finish();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}