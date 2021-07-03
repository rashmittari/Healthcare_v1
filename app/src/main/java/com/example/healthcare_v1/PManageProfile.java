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

public class PManageProfile extends AppCompatActivity {

    EditText Pharmaname,locationofpharma;
    Button updatebtn,logoutbtn;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_manage_profile);

        Pharmaname=findViewById(R.id.PharmaName);
        locationofpharma=findViewById(R.id.locationofpharma);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        updatebtn=findViewById(R.id.Updatedatabutton);
        logoutbtn=findViewById(R.id.LogoutbtnP);

        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fAuth.signOut();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });

        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Pharmacyname= Pharmaname.getText().toString();
                String Pharmacylocation= locationofpharma.getText().toString();

                userID=fAuth.getCurrentUser().getUid();
                DocumentReference documentReference=fStore.collection("users").document("pharmacists")
                        .collection("x").document(userID);//.collection("Medicines").document(Mname);
                Map<String,Object> userP = new HashMap<>();
                userP.put("Name",Pharmacyname);
                userP.put("Location",Pharmacylocation);

                documentReference.set(userP).addOnSuccessListener(new OnSuccessListener<Void>() {
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