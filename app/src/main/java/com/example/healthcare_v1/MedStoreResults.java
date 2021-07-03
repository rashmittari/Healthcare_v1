package com.example.healthcare_v1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;

public class MedStoreResults extends AppCompatActivity {

    TextView textView;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med_store_results);

        textView=findViewById(R.id.textdata);
        db=FirebaseFirestore.getInstance();

        textView.setText("");
        fetchdata();

    }

    private void fetchdata() {

        String medicine_name = getIntent().getStringExtra("drug");

        db.collection("users").document("pharmacists").collection("x")
                //.document().collection("medicines")
                .whereEqualTo(medicine_name,"1")
                //.whereArrayContains("med",medicine_name)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for (QueryDocumentSnapshot document :queryDocumentSnapshots)
               // {
                    //String data=document.getString(medicine_name);
                   // if(data.contains("1"))
                    textView.append(" "+document.getString("Name")+" : "+document.getString("Location")+"\n\n");

               // }

            }
        });

    }
}