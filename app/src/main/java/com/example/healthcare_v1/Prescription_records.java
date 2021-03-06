package com.example.healthcare_v1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Prescription_records extends AppCompatActivity {

    FirebaseAuth fAuth;
    String userID;
    FloatingActionButton fb;
    //
    RecyclerView recview;
    ArrayList<model_pdf> userArrayList2;
    myadapterpdf myadapterpdf;
    FirebaseFirestore db;
    ProgressDialog progressDialog;
    //

    TextView openx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_records);

        openx = findViewById(R.id.myaptupcoming2);
        openx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Medical_Records.class));
                finish();
            }
        });

        fAuth = FirebaseAuth.getInstance();
        userID=fAuth.getCurrentUser().getUid();
        //Toast.makeText(getApplicationContext(),userID,Toast.LENGTH_SHORT).show();



        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data... ");
        progressDialog.show();

        recview= findViewById(R.id.recviewPresRecord);
        recview.setHasFixedSize(true);
        recview.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        userArrayList2 = new ArrayList<model_pdf>();
        myadapterpdf = new myadapterpdf(Prescription_records.this,userArrayList2);

        recview.setAdapter(myadapterpdf);

        EventChangeListner2();
    }

    private void EventChangeListner2() {

        //String category = getIntent().getStringExtra("profession");
        fAuth = FirebaseAuth.getInstance();
        userID=fAuth.getCurrentUser().getUid();

        db.collection("users").document("patients").collection("all")
                .document(userID).collection("Prescriptions")
                .orderBy("FileName", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if (error !=null){

                            if (progressDialog.isShowing())
                                progressDialog.dismiss();
                            Log.e("firestore error",error.getMessage());
                            return;
                        }

                        for (DocumentChange dc : value.getDocumentChanges()){

                            if (dc.getType() == DocumentChange.Type.ADDED){

                                userArrayList2.add(dc.getDocument().toObject(model_pdf.class));

                            }

                            myadapterpdf.notifyDataSetChanged();
                            if (progressDialog.isShowing())
                                progressDialog.dismiss();

                        }

                    }
                });


    }
}