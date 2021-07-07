package com.example.healthcare_v1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Confirm_Prescription extends AppCompatActivity {

    Button pdfcreationBtn;

    RecyclerView recyclerCricketers;
    ArrayList<MedicineDetail_modalclass> cricketersList = new ArrayList<>();

    FirebaseFirestore fFirestore;
    FirebaseStorage fStorage;

    Uri pdfUri;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cricketers);

        recyclerCricketers = findViewById(R.id.recycler_cricketers);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        recyclerCricketers.setLayoutManager(layoutManager);

        cricketersList = (ArrayList<MedicineDetail_modalclass>) getIntent().getExtras().getSerializable("list");

        recyclerCricketers.setAdapter(new PrescriptionAdapter(cricketersList));

        //testing start
        fFirestore = FirebaseFirestore.getInstance();
        fStorage = FirebaseStorage.getInstance();
/*
        String data = "";
        for (Cricketer s:cricketersList){
         // Toast.makeText(this, s.getCricketerName(), Toast.LENGTH_LONG).show();
          data+=s.getCricketerName()+",";
        }
        Toast.makeText(this, data, Toast.LENGTH_LONG).show();

        //testing end


 */
        pdfcreationBtn = findViewById(R.id.pdfcreationBtn);
        pdfcreationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = "";
                String useremail = "";
                String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());


                String path = getExternalFilesDir(null).toString()+"/user.pdf";

                File file = new File(path);

                if (!file.exists()) {
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                //Document document = new Document (PageSize.A4);
                Document document = new Document(PageSize.A4);
                try {
                    PdfWriter.getInstance(document, new FileOutputStream(file.getAbsoluteFile()));
                } catch (DocumentException e) {
                    e.printStackTrace();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                document.open();

                for (MedicineDetail_modalclass s:cricketersList){
                    // Toast.makeText(this, s.getCricketerName(), Toast.LENGTH_LONG).show();
                   // data+=s.getCricketerName()+",";
                    username=s.getCricketerName();
                    useremail=s.getTeamName();



                    Font myfont = new Font(Font.FontFamily.HELVETICA, 24, Font.BOLD);

                    Paragraph paragraph= new Paragraph();

                    //paragraph.add(new Paragraph(" Date : " +date, myfont));
                    //paragraph.add(new Paragraph("\n"));
                    //paragraph.add(new Paragraph("\n"));
                    medicineEntries(username,useremail,myfont,document,paragraph);

                    //paragraph.add(new Paragraph("User Email : "+useremail, myfont));


                }
                document.close();
                Toast.makeText(getApplicationContext(), "PDF Created",Toast.LENGTH_SHORT).show();
                //Toast.makeText(getApplicationContext(), data, Toast.LENGTH_LONG).show();
               // pdfUri = Uri.parse(path);
                Uri file2 = Uri.fromFile(new File(path));
                uploading(file2);


                //
            }
        });

    }

    private void uploading(Uri pdfUri) {

        String patient_uid = getIntent().getStringExtra("pat_uid");
        String randomName = FirebaseAuth.getInstance().getUid() +" "+System.currentTimeMillis();



        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        // Toast.makeText(getApplicationContext(),userID,Toast.LENGTH_SHORT).show();
        DocumentReference documentReference= FirebaseFirestore.getInstance().collection("users")
                .document(userID);
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String type = documentSnapshot.getString("Type");
                String fullname = documentSnapshot.getString("Full Name");

                String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                String randomName =date+" "+fullname;

        StorageReference storageReference=fStorage.getReference();
        storageReference.child("Uploads").child("Prescriptions").child(patient_uid).child(randomName).putFile(pdfUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //String url=taskSnapshot.getStorage().getDownloadUrl().toString();
                //String url=taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
                //DocumentReference documentReference=FirebaseFirestore.getInstance().collection("users").document(userID).collection("Medical Reports").document(fileName);
                //Map<String,Object> user = new HashMap<>();
                //user.put("URL",url);
                //user.put("FileName",fileName);

                Task<Uri> task = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri pdfUri) {
                        String url = pdfUri.toString();
                        String patient_uid = getIntent().getStringExtra("pat_uid");
                        //String fullname = documentSnapshot.getString("Full Name");

                        DocumentReference documentReference=fFirestore.collection("users")
                                .document("patients").collection("all").document(patient_uid)
                                .collection("Prescriptions").document(randomName);
                        Map<String,Object> user = new HashMap<>();
                        user.put("URL",url);
                        user.put("FileName",randomName);
                        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                //Toast.makeText(getApplicationContext(),patient_uid,Toast.LENGTH_SHORT).show();
                                //Toast.makeText(getApplicationContext(),randomName,Toast.LENGTH_SHORT).show();
                                Toast.makeText(getApplicationContext(),"Added to Database",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),DoctorHome.class));
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });


                // documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                //     @Override
                //     public void onSuccess(Void aVoid) {
                //    }
                // }).addOnFailureListener(new OnFailureListener() {
                //   @Override
                //    public void onFailure(@NonNull Exception e) {
                //       Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                //   }
                // });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                //int currentProgress=(int) (100*snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                //progressDialog.setProgress(currentProgress);
            }
        });

            }
        });

    }

    private void medicineEntries(String username, String useremail, Font myfont, Document document, Paragraph paragraph) {

        //Paragraph paragraph= new Paragraph();
        paragraph.add(new Paragraph(username +" : " +useremail, myfont));
        paragraph.add(new Paragraph("\n"));

        try {
            document.add(paragraph);
        } catch (DocumentException e) {
            e.printStackTrace();
        }


    }
}
