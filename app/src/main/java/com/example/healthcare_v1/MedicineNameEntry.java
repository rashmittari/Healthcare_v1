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

import java.util.HashMap;
import java.util.Map;

public class MedicineNameEntry extends AppCompatActivity {

    EditText mednameS;
    Button medupdatebtn;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_name_entry);

        mednameS=findViewById(R.id.mednameS);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        medupdatebtn=findViewById(R.id.medupdatebtn);
        medupdatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Mname= mednameS.getText().toString();

                userID=fAuth.getCurrentUser().getUid();
                DocumentReference documentReference=fStore.collection("users").document("pharmacists")
                        .collection("x").document(userID);//.collection("Medicines").document(Mname);
                Map<String,Object> userP = new HashMap<>();
                userP.put(Mname,"1");

                documentReference.update(userP).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(),"Sucessfully Updated",Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
                startActivity(new Intent(getApplicationContext(),PharmacistHome.class));
                finish();


            }
        });

    }
}