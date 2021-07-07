package com.example.healthcare_v1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Home extends AppCompatActivity {
    Button logoutBtn,mRepoBtn,bookBtn,bookCard;
    FirebaseAuth fAuth;
    RelativeLayout set,book,myapt,record,reminder,location;

    TextView textViewuserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        textViewuserName= findViewById(R.id.textViewuserName);
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        // Toast.makeText(getApplicationContext(),userID,Toast.LENGTH_SHORT).show();
        DocumentReference documentReference= FirebaseFirestore.getInstance().collection("users")
                .document(userID);
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String fullname = documentSnapshot.getString("Full Name");
                textViewuserName.setText(fullname);
            }
        });


        fAuth =FirebaseAuth.getInstance();
       // logoutBtn=findViewById(R.id.logoutbutton);
        //mRepoBtn=findViewById(R.id.mreports);
       // bookBtn=findViewById(R.id.bookbtn);
       // bookCard=findViewById(R.id.bookcardeologist);

        //new
        record=findViewById(R.id.medicalrecordbutton);
        set=findViewById(R.id.setingButton);
        book=findViewById(R.id.bookappointmentButton);
        myapt=findViewById(R.id.myAppointmentButton);
        reminder=findViewById(R.id.medicineReminderButton);
        location=findViewById(R.id.medicineLocatorButton);
        //new
/*
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fAuth.signOut();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });
        mRepoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Prescription_records.class));
            }
        });

 */

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Doctor_categories.class));
            }
        });

        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Medical_Records.class));
            }
        });

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MedStoreSearch.class));
            }
        });

        myapt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Patient_Appointments.class));
            }
        });
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Text_to_pdf.class));
            }
        });
        reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Coming Soon",Toast.LENGTH_SHORT).show();
            }
        });
/*
        bookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //String category = "Dentist";

                Intent intent= new Intent(getApplicationContext(),UserProfile.class);
                //intent.putExtra("profession", category);
                startActivity(intent);
                //startActivity(new Intent(getApplicationContext(),bookinglist.class));
                //finish();
            }
        });

        //String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        //Toast.makeText(getApplicationContext(),date,Toast.LENGTH_SHORT).show();


        bookCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //String category = "Cardeologist";

                Intent intent= new Intent(getApplicationContext(),Prescription.class);
                //intent.putExtra("profession", category);
                startActivity(intent);
                //startActivity(new Intent(getApplicationContext(),bookinglist.class));
                //finish();
            }
        });

 */
    }
}