package com.example.healthcare_v1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Patient_Appointments extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Detuser> detuserArrayList;
    My2Adapter my2Adapter;
    FirebaseFirestore db;
    ProgressDialog progressDialog;

    //FirebaseAuth fAuth;
    //String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient__appointments);


        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data... ");
        progressDialog.show();


        recyclerView = findViewById(R.id.recyclerViewPappointments);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db=FirebaseFirestore.getInstance();
        detuserArrayList= new ArrayList<Detuser>();
        my2Adapter = new My2Adapter(Patient_Appointments.this,detuserArrayList);

        recyclerView.setAdapter(my2Adapter);

        Eventchangexlistner();

    }

    private void Eventchangexlistner() {

        //userID=fAuth.getCurrentUser().getUid();
        //Toast.makeText(getApplicationContext(),userID,Toast.LENGTH_SHORT).show();
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
       // Toast.makeText(getApplicationContext(),userID,Toast.LENGTH_SHORT).show();

        db.collection("users").document("patients").collection("all")
                .document(userID).collection("My_appointments")
                .orderBy("status", Query.Direction.DESCENDING)
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

                                detuserArrayList.add(dc.getDocument().toObject(Detuser.class));
                            }

                            my2Adapter.notifyDataSetChanged();
                            if (progressDialog.isShowing())
                                progressDialog.dismiss();
                        }
                    }
                });
    }


}