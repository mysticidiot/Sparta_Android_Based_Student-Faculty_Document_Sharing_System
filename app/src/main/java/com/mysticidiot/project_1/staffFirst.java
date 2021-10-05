package com.mysticidiot.project_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class staffFirst extends AppCompatActivity {
    String staff_id;
    Intent sendintent;
    public void notif(View view){
        String sub;
        TextView v = findViewById(view.getId());
        sub = v.getText().toString();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Sections");
        sendintent = new Intent(this,subActivity.class);
        sendintent.putExtra("SUBJECT",sub);
        sendintent.putExtra("ID",staff_id);
        startActivity(sendintent);
    }
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_first);
        sendintent = new Intent(this,subActivity.class);
        final TextView sub1 = (TextView)findViewById(R.id.sub1);
        final TextView sub2 = (TextView)findViewById(R.id.sub2);
        final TextView sub3 = findViewById(R.id.sub3);
        final TextView sub4 = findViewById(R.id.sub4);
        Intent intent = getIntent();
        staff_id = intent.getStringExtra("staff").toString();
        ref = FirebaseDatabase.getInstance().getReference().child("Sections");
        Query query = ref.orderByChild("Staff_ID").equalTo(Integer.parseInt(staff_id));
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int i = 1;
                for(DataSnapshot d : dataSnapshot.getChildren()){
                    Sections s = new Sections();
                    s = d.getValue(Sections.class);
                    String subi = s.getCourse_Title();
                     if(i == 1)
                        sub1.setText(subi);
                    else if(i == 2)
                        sub2.setText(subi);
                    else if(i == 3)
                        sub3.setText(subi);
                    else if(i == 4)
                        sub4.setText(subi);
                    i++;
                }
                int len = (int)dataSnapshot.getChildrenCount();
                }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
