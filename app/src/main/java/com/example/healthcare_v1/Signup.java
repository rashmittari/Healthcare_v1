package com.example.healthcare_v1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Signup extends AppCompatActivity {
    EditText signupFname,signupEmailid,signupPwd,signupConfirmPwd,signupProfession;
    TextView signupBtn;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID,data;
    Spinner spinner;

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
       // signupProfession=findViewById(R.id.profession);

        //List<String> states = Arrays.asList("u","d","Pharmacist");

       // spinner = findViewById(R.id.spinner);
        //ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //spinner.setAdapter(adapter);

        //
        spinner=findViewById(R.id.spinner);
        ArrayAdapter<CharSequence>adapter=ArrayAdapter.createFromResource(this,R.array.spinnerlist, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                data=parent.getItemAtPosition(position).toString();
                //Toast.makeText(getApplicationContext(),data,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sfname= signupFname.getText().toString();
                String semail= signupEmailid.getText().toString();
                String spassword= signupPwd.getText().toString();
                String sconfirmpassword= signupConfirmPwd.getText().toString();
                String sprofession= spinner.getSelectedItem().toString();
               // String sprofession= spinner.getSelectedItem().toString();
                        //signupProfession.getText().toString();

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
               // if (sprofession.isEmpty()){
                //    signupProfession.setError("field is empty");
                //    return;
                //}
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
                         Toast.makeText(Signup.this, e.getMessage(),Toast.LENGTH_SHORT).show();
                     }
                 });

            }
        });
    }
}