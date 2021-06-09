package com.example.healthcare_v1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class test extends AppCompatActivity {

    Button upBtn;
    TextView  fileNameDisp,fileSelection;
    Uri pdfUri;
    FirebaseAuth fAuth;
    FirebaseFirestore fFirestore;
    FirebaseStorage fStorage;
    String userID;
    ProgressDialog progressDialog;

    //new 3june
    EditText reportnamejava;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        //3june
        reportnamejava=findViewById(R.id.reportname);
        //String justchecking= reportnamejava.getText().toString();
        //

        upBtn=findViewById(R.id.uploadbtn);
        fileNameDisp=findViewById(R.id.filename);
        fileSelection=findViewById(R.id.selectfile);

        fAuth=FirebaseAuth.getInstance();
        fFirestore=FirebaseFirestore.getInstance();
        fStorage=FirebaseStorage.getInstance();

        fileSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)
                {
                    selectPdf();
                }
                else
                    ActivityCompat.requestPermissions(test.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},9);
            }
        });

        upBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userID=fAuth.getCurrentUser().getUid();
                //DocumentReference documentReference=fStore.collection("users").document(userID);
                //                         Map<String,Object> user = new HashMap<>();
                //                         user.put("Full Name",sfname);
                //                         user.put("Email ID",semail);
                //                         user.put("Profession",sprofession);

                if (pdfUri!=null)
                uploadFile(pdfUri);
                else
                    Toast.makeText(getApplicationContext(),"select a file",Toast.LENGTH_SHORT).show();

            }
        });


    }

    private void uploadFile(Uri pdfUri) {

        progressDialog=new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Uploading File...");
        progressDialog.setProgress(0);
        progressDialog.show();

        userID=fAuth.getCurrentUser().getUid();
        //String fileName=System.currentTimeMillis()+"";
        String fileName= reportnamejava.getText().toString();
        StorageReference storageReference=fStorage.getReference();
        storageReference.child("Uploads").child(userID).child(fileName).putFile(pdfUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
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
                    public void onSuccess(Uri uri) {
                        String url = uri.toString();
                        DocumentReference documentReference=fFirestore.collection("users").document(userID).collection("Medical Reports").document(fileName);
                        Map<String,Object> user = new HashMap<>();
                        user.put("URL",url);
                        user.put("FileName",fileName);
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
                Toast.makeText(getApplicationContext(),"uploading failed",Toast.LENGTH_SHORT).show();

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                int currentProgress=(int) (100*snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                progressDialog.setProgress(currentProgress);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        if(requestCode==9 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
        {
            selectPdf();
        }
        else
            Toast.makeText(getApplicationContext(),"Please provide permission",Toast.LENGTH_SHORT).show();
    }

    private void selectPdf() {

        Intent intent=new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,86);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

       if (requestCode==86 && resultCode==RESULT_OK && data!=null)
       {
           pdfUri=data.getData();
           fileNameDisp.setText("File Selected: "+data.getData().getLastPathSegment());

       }
       else {
           Toast.makeText(getApplicationContext(),"please select a file",Toast.LENGTH_SHORT).show();
       }
    }
}