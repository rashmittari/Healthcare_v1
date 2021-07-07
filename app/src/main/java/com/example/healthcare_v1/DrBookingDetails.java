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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DrBookingDetails extends AppCompatActivity {

    TextView drnam,tgs,doctorid,drprofession,drqualifications,drlocation;

    FirebaseFirestore db = FirebaseFirestore.getInstance();


    EditText Date,Time;
    Button checkA,confirmB;
    String date,time,userID;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dr_booking_details);

        firebaseAuth= FirebaseAuth.getInstance();

        //drnam = findViewById(R.id.dispname);
        drnam = findViewById(R.id.userFullName);
        doctorid = findViewById(R.id.textView211);
        drprofession = findViewById(R.id.drproffesion);
        drqualifications = findViewById(R.id.drqualification);
        drlocation = findViewById(R.id.draddress);


        drnam.setText(getIntent().getExtras().getString("dr_name"));
        doctorid.setText(getIntent().getExtras().getString("dr_uid"));
        drprofession.setText(getIntent().getExtras().getString("dr_prof"));
        drqualifications.setText(getIntent().getExtras().getString("dr_qualifications"));
        drlocation.setText(getIntent().getExtras().getString("dr_address"));

        //

        //List<String> states = Arrays.asList("u","d","Pharmacist");
        //List<String> days = Arrays.asList("Monday","Tuesday","Wednesday","Thursday");
       // Dspinner = findViewById(R.id.dayspinner);
        //ArrayAdapter adapter=new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item);
       // adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       // Dspinner.setAdapter(adapter);

        // spinner = findViewById(R.id.spinner);
        //ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //spinner.setAdapter(adapter);
       // milk=new ArrayList<>();
       // milk.add("hello");
        //milk.add("rashmit");
        //milk.add("tari");

       // Dspinner=findViewById(R.id.dayspinner);
       // ArrayAdapter<CharSequence>adapter=ArrayAdapter.createFromResource(this,);
        //ArrayAdapter<CharSequence>adapter=ArrayAdapter.createFromResource(this,R.array.spinnerlist2, android.R.layout.simple_spinner_item);
       // adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
       // Dspinner.setAdapter(adapter);

        /*spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                data=parent.getItemAtPosition(position).toString();
                //Toast.makeText(getApplicationContext(),data,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

         */
        tgs = findViewById(R.id.textViewtags);

        //last idea
        checkA = findViewById(R.id.checkavailabletimeslots);
        confirmB = findViewById(R.id.confirmthebookingbtn);
        Date = findViewById(R.id.dateEntered);
        Time = findViewById(R.id.timeEntered);

         date= Date.getText().toString();
         time= Time.getText().toString();

        checkA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                date= Date.getText().toString();
                time= Time.getText().toString();

                String category = getIntent().getStringExtra("dr_prof");
                String did = getIntent().getStringExtra("dr_uid");


                DocumentReference documentReference= FirebaseFirestore.getInstance().collection("users").document("doctors")
                        .collection(category).document(did).collection("Time Slots").document(date);
                documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        date= Date.getText().toString();
                        time= Time.getText().toString();

                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                //Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                Toast.makeText(getApplicationContext(),"doc exists",Toast.LENGTH_SHORT).show();
                                //tgs.setText(date);
                                checkavailabilityofslots();
                            }
                            else {

                                String category = getIntent().getStringExtra("dr_prof");
                                String did = getIntent().getStringExtra("dr_uid");

                                //db.collection("users").document("test").collection("test3").document(date)
                                db.collection("users").document("doctors").collection(category)
                                        .document(did)
                                        //.collection("Time Slots").document(date)
                                        .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        String data10 = "";
                                        ArrayList<String> spinnerList10 = new ArrayList<String>();
                                        User user= documentSnapshot.toObject(User.class);

                                        for (String string : user.getTime_slots()){
                                            data10 +=  string + "," ;
                                        }
                                        spinnerList10.add(data10);

                                        String data11= spinnerList10.toString();
                                        String[] tagArray= data10.split("\\s*,\\s*");
                                        List<String> tags = Arrays.asList(tagArray);

                                        //tgs.setText(data10);
                                       // Toast.makeText(DrBookingDetails.this,spinnerList10.toString(),Toast.LENGTH_LONG).show();
                                        generateTimeslots(tags);
                                    }
                                });

                               // generateTimeslots();

                                //Log.d(TAG, "No such document");
                                //Toast.makeText(getApplicationContext(),time,Toast.LENGTH_SHORT).show();
                                //tgs.setText(date);


                            }
                        } else {
                            //Log.d(TAG, "get failed with ", task.getException());
                            Toast.makeText(getApplicationContext(),"lol error",Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                //Date.setFocusable(false);
                Date.setEnabled(false);

            }
        });
        confirmB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date= Date.getText().toString();
                time= Time.getText().toString();

                String category = getIntent().getStringExtra("dr_prof");
                String did = getIntent().getStringExtra("dr_uid");

                db.collection("users").document("doctors").collection(category)
                        .document(did).collection("Time Slots").document(date)
                .update("available", FieldValue.arrayRemove(time));

                patientaptdetailsgeneration();
            }
        });
    }

    private void checkavailabilityofslots() {


        String category = getIntent().getStringExtra("dr_prof");
        String did = getIntent().getStringExtra("dr_uid");

        //db.collection("users").document("test").collection("test3").document(date)
        db.collection("users").document("doctors").collection(category)
                .document(did).collection("Time Slots").document(date)
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String data2 = "";
                ArrayList<String> spinnerList2 = new ArrayList<String>();
                User user= documentSnapshot.toObject(User.class);

                for (String string : user.getAvailable()){
                    data2 +=  string + "\n ";
                }
                spinnerList2.add(data2);
                tgs.setText(data2);
                //Toast.makeText(DrBookingDetails.this,spinnerList2.toString(),Toast.LENGTH_LONG).show();
            }
        });



       // CollectionReference citiesRef = db.collection("users").document("test")
        //        .collection("test3");



       /* db.collection("users").document("test").collection("test3")
                //.document().collection("medicines")
                .whereEqualTo(a,"1" )
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

        */


    }

    private void generateTimeslots(List<String> tags) {


        String category = getIntent().getStringExtra("dr_prof");
        String did = getIntent().getStringExtra("dr_uid");

        DocumentReference documentReference= db.collection("users").document("doctors").collection(category)
                .document(did).collection("Time Slots").document(date);

        Map<String,Object> u = new HashMap<>();
        //u.put("available", Arrays.asList("10:00", "11:00"));
       // u.put("available", Arrays.asList(spinnerList10));
        u.put("available", tags);

        documentReference.set(u).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //Toast.makeText(getApplicationContext(),"date document generated",Toast.LENGTH_SHORT).show();
                checkavailabilityofslots();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
   /*
    public void loadNotes(View v){
        notebookRef.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        String data = "";
                        ArrayList<String> spinnerList = new ArrayList<String>();

                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                            User user= documentSnapshot.toObject(User.class);

                            for (String tag :user.getTags()) {
                                data +="\n-"+tag;

                            }
                            spinnerList.add(data);

                            //data += "\n\n";
                            data = " ";
                        }
                        tgs.setText(data);
                        Toast.makeText(DrBookingDetails.this,spinnerList.toString(),Toast.LENGTH_LONG).show();
                    }
                });

    }

    */

    private void patientaptdetailsgeneration() {

        userID=firebaseAuth.getCurrentUser().getUid();
        date= Date.getText().toString();
        time= Time.getText().toString();

        String profession = getIntent().getStringExtra("dr_prof");
        String name = getIntent().getStringExtra("dr_name");
        String qualification = getIntent().getStringExtra("dr_qualifications");
        String address = getIntent().getStringExtra("dr_address");
        String fees = getIntent().getStringExtra("dr_fees");
        //String profession = getIntent().getStringExtra("dr_prof");
        //status,timing,date



        DocumentReference documentReference = db.collection("users").document("patients")
                .collection("all").document(userID).collection("My_appointments")
                .document(date);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        //Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        date=date+"(2)";
                        generation(documentReference,date);
                    } else {
                       // Log.d(TAG, "No such document");
                        generation(documentReference,date);
                    }
                } else {
                    //Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

    }

    private void generation(DocumentReference documentReference, String date) {

        time= Time.getText().toString();

        String profession = getIntent().getStringExtra("dr_prof");
        String name = getIntent().getStringExtra("dr_name");
        String qualification = getIntent().getStringExtra("dr_qualifications");
        String address = getIntent().getStringExtra("dr_address");
        String fees = getIntent().getStringExtra("dr_fees");

        Map<String,Object> z = new HashMap<>();
        //u.put("available", Arrays.asList("10:00", "11:00"));
        // u.put("available", Arrays.asList(spinnerList10));
        z.put("name", name);
        z.put("profession", profession);
        z.put("qualifications", qualification);
        z.put("timings", time);
        z.put("address", address);
        z.put("status", "upcoming");
        z.put("fees", fees);
        z.put("date", date);

        documentReference.set(z).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_SHORT).show();
                //checkavailabilityofslots();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        apointmentdetailsfordoctor();

    }
    private void apointmentdetailsfordoctor() {

        date= Date.getText().toString();
        time= Time.getText().toString();

        String profession = getIntent().getStringExtra("dr_prof");

        String userID = firebaseAuth.getCurrentUser().getUid();
        String did = getIntent().getStringExtra("dr_uid");

        DocumentReference documentReference= FirebaseFirestore.getInstance().collection("users")
                .document("patients").collection("all").document(userID);
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                String name = documentSnapshot.getString("name");
                String age = documentSnapshot.getString("age");
                String gender = documentSnapshot.getString("gender");

                Map<String,Object> a = new HashMap<>();
                a.put("Pname", name);
                a.put("Age", age);
                a.put("Gender", gender);
                a.put("Date", date);
                a.put("Time", time);
                a.put("user_id", userID);

                db.collection("users").document("doctors").collection(profession)
                        .document(did).collection("Appointments")
                        .document("all").collection(date)
                        .add(a).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        startActivity(new Intent(getApplicationContext(),Home.class));
                        Toast.makeText(getApplicationContext(),"Appointment Booked",Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

            }
        });



    }

}