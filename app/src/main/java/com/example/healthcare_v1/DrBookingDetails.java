package com.example.healthcare_v1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

    TextView drnam,tgs;
    //
    Spinner Dspinner,Tspinner;
    ArrayList<String> spinnerList;
    ArrayAdapter<String> adapter;
    //
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference notebookRef=db.collection("users").document("test").collection("test3");
    //DocumentReference flop = db.collection("users").document("test").collection("test3").document("31-12-2021");
   // DocumentSnapshot lastResult;

    EditText Date,Time;
    Button checkA,confirmB;
    String date,time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dr_booking_details);

        drnam = findViewById(R.id.dispname);

        drnam.setText(getIntent().getExtras().getString("dr_name"));

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
        //loadNotes();



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


                DocumentReference documentReference= FirebaseFirestore.getInstance().collection("users").document("test")
                        .collection("test3").document(date);
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
                                generateTimeslots();

                                //Log.d(TAG, "No such document");
                                //Toast.makeText(getApplicationContext(),time,Toast.LENGTH_SHORT).show();
                                //tgs.setText(date);

                               /* db.collection("users").document("test")
                                        .collection("test3")
                                        .add(u)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                               // Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                    }
                                });

                                */
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

                db.collection("users").document("test").collection("test3").document(date)
                .update("available", FieldValue.arrayRemove(time));
            }
        });

    }

    private void checkavailabilityofslots() {

        String a="a";
        String b="b";
        String c="c";
        String d="d";
        String e="e";

       /* notebookRef.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        String data = "";
                        ArrayList<String> spinnerList = new ArrayList<String>();

                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                            User user= documentSnapshot.toObject(User.class);

                            for (String tag :user.getAvailable()) {
                                data +="\n-"+tag;

                            }
                            spinnerList.add(data);

                            //data += "\n\n";
                            //data = " ";
                        }
                        tgs.setText(data);
                        Toast.makeText(DrBookingDetails.this,spinnerList.toString(),Toast.LENGTH_LONG).show();
                    }
                });

        */

        db.collection("users").document("test").collection("test3").document(date)
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String data2 = "";
                ArrayList<String> spinnerList2 = new ArrayList<String>();
                User user= documentSnapshot.toObject(User.class);

                for (String string : user.getAvailable()){
                    data2 += "\n-" + string;
                }
                spinnerList2.add(data2);
                tgs.setText(data2);
                Toast.makeText(DrBookingDetails.this,spinnerList2.toString(),Toast.LENGTH_LONG).show();
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

    private void generateTimeslots() {

        DocumentReference documentReference=db.collection("users").document("test")
                .collection("test3").document(date);

        Map<String,Object> u = new HashMap<>();
        u.put("available", Arrays.asList("10:00", "11:00"));

        documentReference.set(u).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(),"date document generated",Toast.LENGTH_SHORT).show();
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
}