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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Signup extends AppCompatActivity {
    EditText signupFname,signupEmailid,signupPwd,signupConfirmPwd,signupProfession;
    Button signupBtn;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    Integer usertype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signupFname = findViewById(R.id.signupfullname);
        signupEmailid = findViewById(R.id.signupemailid);
        signupPwd = findViewById(R.id.signuppassword);
        signupConfirmPwd = findViewById(R.id.signupconfirmpassword);
        signupBtn = findViewById(R.id.signupbutton);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        signupProfession=findViewById(R.id.profession);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sfname= signupFname.getText().toString();
                String semail= signupEmailid.getText().toString();
                String spassword= signupPwd.getText().toString();
                String sconfirmpassword= signupConfirmPwd.getText().toString();
                String sprofession= signupProfession.getText().toString();

                if (sfname.isEmpty()){
                    signupFname.setError("field cannot be empty");
                    return;
                }

                if (semail.isEmpty()){
                    signupEmailid.setError("field cannot be empty");
                    return;
                }
                if (spassword.isEmpty()){
                    signupPwd.setError("field cannot be empty");
                    return;
                }
                if (sconfirmpassword.isEmpty()){
                    signupConfirmPwd.setError("field cannot be empty");
                    return;
                }
                if (sprofession.isEmpty()){
                    signupProfession.setError("field is empty");
                    return;
                }
                 if (!spassword.equals(sconfirmpassword)){
                     signupConfirmPwd.setError("passwords do not match");
                 }


                 fAuth.createUserWithEmailAndPassword(semail,spassword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                     @Override
                     public void onSuccess(AuthResult authResult) {

                         fAuth.getCurrentUser().sendEmailVerification();
                         Toast.makeText(Signup.this,"Kindly Verify Email",Toast.LENGTH_LONG).show();

                         //to store users data 14 feb 21
                         userID=fAuth.getCurrentUser().getUid();
                         DocumentReference documentReference=fStore.collection("users").document(userID);
                         Map<String,Object> user = new HashMap<>();
                         user.put("Full Name",sfname);
                         user.put("Email ID",semail);
                         user.put("Profession",sprofession);
                         documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                             @Override
                             public void onSuccess(Void aVoid) {
                             }
                         }).addOnFailureListener(new OnFailureListener() {
                             @Override
                             public void onFailure(@NonNull Exception e) {
                                 Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                             }
                         });

                         startActivity(new Intent(getApplicationContext(),MainActivity.class));
                         finish();
                     }
                 }).addOnFailureListener(new OnFailureListener() {
                     @Override
                     public void onFailure(@NonNull Exception e) {
                         Toast.makeText(Signup.this, "Error occured",Toast.LENGTH_SHORT).show();
                     }
                 });

            }
        });
    }
}