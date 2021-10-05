package com.mysticidiot.project_1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class subActivity extends AppCompatActivity {

//    Button sendbt = findViewById(R.id.button3);
    Button gotofiles;
    EditText mes;
    Intent sub,sendintent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        mes = findViewById(R.id.message);
        TextView subject = findViewById(R.id.textView14);
        sub = getIntent();
        String s = sub.getStringExtra("SUBJECT");
        Toast.makeText(this,s, Toast.LENGTH_SHORT).show();
        subject.setText(s);
        gotofiles = findViewById(R.id.button5);
        gotofiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sub = getIntent();
                String staff_id = sub.getStringExtra("ID");
                String subject = sub.getStringExtra("SUBJECT");
                sendintent = new Intent(subActivity.this,fileActivity.class);
                sendintent.putExtra("SUBJECT",subject);
                sendintent.putExtra("ID",staff_id);
                startActivity(sendintent);
            }
        });
    }

   public void sendmessage(View view){
        final String message = mes.getText().toString();
        //int section = sub.getIntExtra("Num_Sections",1);
        final String staff_id = sub.getStringExtra("ID");
        final String course1 = sub.getStringExtra("SUBJECT");
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Messages");
        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference().child("Sections");
        //Messages m = new Messages();
        Query q = ref1.orderByChild("Staff_ID").equalTo(Integer.parseInt(staff_id));
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot d : dataSnapshot.getChildren()){
                    Sections t = new Sections();
                    t = d.getValue(Sections.class);
                    String course = t.getCourse_Title();
                    if(course.equals(course1)){
                        String sect = d.getKey();
                        int section = Integer.parseInt(sect);
                        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                        String currentTime = new SimpleDateFormat("HH:mm:ss",Locale.getDefault()).format(new Date());
                        Messages m = new Messages();
                        m.setSubject(course);
                        m.setStaff_id(staff_id);
                        m.setMessage(message);
                        m.setSection(section);
                        m.setDate(currentDate);
                        m.setTime(currentTime);
                        ref.push().setValue(m);
                        Toast.makeText(subActivity.this,"Message Sent!",Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
