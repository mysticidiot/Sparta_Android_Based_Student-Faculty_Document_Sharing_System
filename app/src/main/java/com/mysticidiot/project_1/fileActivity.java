package com.mysticidiot.project_1;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class fileActivity extends AppCompatActivity {
    Button select,upload;
    TextView fileName;
    FirebaseDatabase database;
    FirebaseStorage storage;
    Uri pdfUri;
    Intent sub;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_file);
        storage=FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();
        select = findViewById(R.id.select);
        upload = findViewById(R.id.upload);
        fileName = findViewById(R.id.fileName);
        sub=getIntent();
        String s = sub.getStringExtra("SUBJECT");
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(fileActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
                    selectpdf();
                }
                else {
                    ActivityCompat.requestPermissions(fileActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},9);
                }
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pdfUri!=null){
                    uploadFile(pdfUri);
                }
            }
        });

    }
    private void uploadFile(final Uri pdfUri) {
        final String filename = pdfUri.getLastPathSegment();
        StorageReference storageReference = storage.getReference();
        storageReference.child("Uploads").child(filename).putFile(pdfUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                final String Url = taskSnapshot.getStorage().getDownloadUrl().toString();
                final String staff_id = sub.getStringExtra("ID");
                final String course1 = sub.getStringExtra("SUBJECT");
                final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Files");
                DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference().child("Sections");
                Query q = ref1.orderByChild("Staff_ID").equalTo(Integer.parseInt(staff_id));
                q.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot d : dataSnapshot.getChildren()){
                            Sections t = new Sections();
                            t = d.getValue(Sections.class);
                            String course = t.getCourse_Title();
                            if(course!=null&&course.equals(course1)){
                                String sect = d.getKey();
                                int section = Integer.parseInt(sect);
                                String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                                String currentTime = new SimpleDateFormat("HH:mm:ss",Locale.getDefault()).format(new Date());
                                Files f = new Files();
                                f.setSubject(course);
                                f.setStaff_id(staff_id);
                                f.setFile_url(Url);
                                f.setSection(section);
                                f.setDate(currentDate);
                                f.setTime(currentTime);
                                f.setFile_name(filename);
                                ref.push().setValue(f);
                                Toast.makeText(fileActivity.this,"File uploaded successfully!",Toast.LENGTH_SHORT).show();
                                break;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }


        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(fileActivity.this,"file upload unsuccessful",Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==9&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
            selectpdf();
        }
        else
            Toast.makeText(fileActivity.this,"Please provide permission",Toast.LENGTH_SHORT).show();
    }

    private void selectpdf() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,86);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==86&&resultCode==RESULT_OK&&data!=null){
            pdfUri=data.getData();
            fileName.setText("Selected file is "+pdfUri.getLastPathSegment());
        }
        else {
            Toast.makeText(fileActivity.this,"please select file",Toast.LENGTH_SHORT).show();
        }
    }


}
