package com.mysticidiot.project_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.RelativeDateTimeFormatter;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ViewMessages extends AppCompatActivity {
String reg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_messages);

        TextView n1 = findViewById(R.id.n2);
        TextView n2 = findViewById(R.id.n3);
        TextView n3 = findViewById(R.id.n4);
        TextView n4 = findViewById(R.id.n5);
        TextView n5 = findViewById(R.id.n6);
        TextView n6 = findViewById(R.id.n7);
        TextView n7 = findViewById(R.id.n8);
        Intent current = getIntent();
        final String subject = current.getStringExtra("Subject");
        reg = current.getStringExtra("Reg");
        final DatabaseReference message = FirebaseDatabase.getInstance().getReference().child("Messages");
        DatabaseReference section = FirebaseDatabase.getInstance().getReference().child("Student Section");
        Query q = section.orderByChild("Reg").equalTo(reg);
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot d: dataSnapshot.getChildren()){
                    student_section cur = new student_section();
                    cur = d.getValue(student_section.class);
                    if (cur.getSubject().equals(subject)){
                        int section = cur.getSection();
                        //Toast.makeText(ViewMessages.this,Integer.toString(section),Toast.LENGTH_SHORT).show();
                        Query q = message.orderByChild("section").equalTo(section);
                        q.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                int i = 0;
                                for(DataSnapshot d:dataSnapshot.getChildren()){
                                    if(i > 7)
                                        break;
                                    Messages cur = new Messages();
                                    cur = d.getValue(Messages.class);
                                    String message = "Date : " + cur.getDate() + " Time : " + cur.getTime() +"\n" + cur.getMessage();
                                    if(i == 0){
                                        TextView n1 = findViewById(R.id.n1);
                                        n1.setText(message);
                                    }
                                    else if(i == 1){
                                        TextView n2 = findViewById(R.id.n2);
                                        n2.setText(message);
                                    }
                                    else if(i == 2){
                                        TextView n3 = findViewById(R.id.n3);
                                        n3.setText(message);
                                    }
                                    else if(i == 3){
                                        TextView n4 = findViewById(R.id.n4);
                                        n4.setText(message);
                                    }
                                    else if(i == 4){
                                        TextView n5 = findViewById(R.id.n5);
                                        n5.setText(message);
                                    }
                                    else if(i == 5){
                                        TextView n6 = findViewById(R.id.n6);
                                        n6.setText(message);
                                    }
                                    else if(i == 6){
                                        TextView n7 = findViewById(R.id.n7);
                                        n7.setText(message);
                                    }
                                    else if(i == 7){
                                        TextView n8 = findViewById(R.id.n8);
                                        n8.setText(message);
                                    }
                                    i++;
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void back(View view){
        Intent back = new Intent(this,Main2Activity.class);
        back.putExtra("Student Reg",reg);
        startActivity(back);
    }
}
