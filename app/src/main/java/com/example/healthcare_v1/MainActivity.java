package com.example.healthcare_v1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    TextView createAcc;
    EditText loginEmail,loginPassword;
    Button loginButton;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginEmail=findViewById(R.id.loginemailid);
        loginPassword=findViewById(R.id.loginpassword);
        loginButton=findViewById(R.id.loginbutton);
        fAuth = FirebaseAuth.getInstance();

        createAcc = findViewById(R.id.createnewaccount);
        createAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Signup.class));
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 if(loginEmail.getText().toString().isEmpty()){
                     loginEmail.setError("field is empty");
                     return;
                 }
                if(loginPassword.getText().toString().isEmpty()){
                    loginPassword.setError("field is empty");
                    return;
                }

               fAuth.signInWithEmailAndPassword(loginEmail.getText().toString(),loginPassword.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                   @Override
                   public void onSuccess(AuthResult authResult) {
                       if (fAuth.getCurrentUser().isEmailVerified()) {
                           startActivity(new Intent(getApplicationContext(), Home.class));
                           finish();
                       }
                       else{
                           Toast.makeText(getApplicationContext(),"Please verify email to use this app",Toast.LENGTH_LONG).show();
                       }
                   }
               }).addOnFailureListener(new OnFailureListener() {
                   @Override
                   public void onFailure(@NonNull Exception e) {
                       Toast.makeText(MainActivity.this,"error occured", Toast.LENGTH_SHORT).show();
                   }
               }) ;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser()!=null&&FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()){
            startActivity(new Intent(getApplicationContext(),Home.class));

        }
    }
}