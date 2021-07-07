package com.example.healthcare_v1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Doctor_Appointments extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Detuser> detuserArrayList3;
    My3Adapter my3Adapter;
    FirebaseFirestore db;
    ProgressDialog progressDialog;

    Button filter;
    EditText dateenteredbydr;
    String ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor__appointments);

        String presentdate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        filter = findViewById(R.id.filterbydateBtn);
        dateenteredbydr = findViewById(R.id.dateEntered);
        //ok = dateenteredbydr.getText().toString();

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data... ");
        progressDialog.show();

        recyclerView = findViewById(R.id.recyclerviewdrapts);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db=FirebaseFirestore.getInstance();
        detuserArrayList3= new ArrayList<Detuser>();
        my3Adapter = new My3Adapter(Doctor_Appointments.this,detuserArrayList3);

        recyclerView.setAdapter(my3Adapter);

        Eventchangexylistner(presentdate);

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dateenteredbydr.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"if condition",Toast.LENGTH_SHORT).show();

                }
                else {
                    Toast.makeText(getApplicationContext(),"else condition",Toast.LENGTH_SHORT).show();
                    String string = dateenteredbydr.getText().toString();

                    detuserArrayList3= new ArrayList<Detuser>();
                    my3Adapter = new My3Adapter(Doctor_Appointments.this,detuserArrayList3);

                    recyclerView.setAdapter(my3Adapter);

                    Eventchangexylistner(string);
                }
            }
        });
    }

    private void Eventchangexylistner(String string) {

        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
       // Toast.makeText(getApplicationContext(),userID,Toast.LENGTH_SHORT).show();
        DocumentReference documentReference= FirebaseFirestore.getInstance().collection("users")
                .document(userID);
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String type = documentSnapshot.getString("Type");
                String fullname = documentSnapshot.getString("Full Name");


        db.collection("users").document("doctors").collection(type)
                .document(userID).collection("Appointments")
                .document("all").collection(string)
                //.orderBy("Time", Query.Direction.ASCENDING)
                .whereEqualTo("Date",string).orderBy("Time", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if (error != null){

                            if (progressDialog.isShowing())
                                progressDialog.dismiss();
                            Log.e("firestore error",error.getMessage());
                            return;
                        }

                        for (DocumentChange dc : value.getDocumentChanges()){

                            if (dc.getType() == DocumentChange.Type.ADDED){

                                detuserArrayList3.add(dc.getDocument().toObject(Detuser.class));
                            }

                            my3Adapter.notifyDataSetChanged();
                            if (progressDialog.isShowing())
                                progressDialog.dismiss();
                        }
                    }
                });
            }
        });
    }
}