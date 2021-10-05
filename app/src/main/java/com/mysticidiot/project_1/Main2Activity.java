package com.mysticidiot.project_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class Main2Activity extends AppCompatActivity {
    String reg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent current = getIntent();
        reg = current.getStringExtra("Student Reg");
        //Toast.makeText(this,reg,Toast.LENGTH_SHORT).show();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Student Section");
        final TextView [] textViews = new TextView[9];
        /*TextView t = new TextView(this);
        t.setVisibility(View.VISIBLE);
        t.setText("Hello");
        linearLayout.addView(t);*/
        Query query = ref.orderByChild("Reg").equalTo(reg);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int i = 0;
                for(DataSnapshot d : dataSnapshot.getChildren()){
                    student_section st = new student_section();
                    st = d.getValue(student_section.class);
                    if(i ==0) {
                        TextView t = findViewById(R.id.t0);
                        t.setText(st.getSubject());
                        t.setOnClickListener(btclk);
                    }
                    else if(i ==1) {
                        TextView t = findViewById(R.id.t1);
                        t.setText(st.getSubject());
                        t.setOnClickListener(btclk);
                    }
                    else if(i ==2) {
                        TextView t = findViewById(R.id.t2);
                        t.setText(st.getSubject());
                        t.setOnClickListener(btclk);
                    }
                    else if(i ==3) {
                        TextView t = findViewById(R.id.t3);
                        t.setText(st.getSubject());
                        t.setOnClickListener(btclk);
                    }
                    else if(i ==4) {
                        TextView t = findViewById(R.id.t4);
                        t.setText(st.getSubject());
                        t.setOnClickListener(btclk);
                    }
                    else if(i ==5) {
                        TextView t = findViewById(R.id.t5);
                        t.setText(st.getSubject());
                        t.setOnClickListener(btclk);
                    }
                    else if(i ==6) {
                        TextView t = findViewById(R.id.t6);
                        t.setText(st.getSubject());
                        t.setOnClickListener(btclk);
                    }
                    else if(i ==7) {
                        TextView t = findViewById(R.id.t7);
                        t.setText(st.getSubject());
                        t.setOnClickListener(btclk);
                    }
                    else if(i == 8){
                        TextView t = findViewById(R.id.t8);
                        t.setHeight(30);
                        t.setText(st.getSubject());
                        t.setOnClickListener(btclk);
                    }
                    else if(i == 9){
                        TextView t = findViewById(R.id.t9);
                        t.setHeight(30);
                        t.setText(st.getSubject());
                        t.setOnClickListener(btclk);
                    }
                    i++;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    final View.OnClickListener btclk = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
                //Toast.makeText(Main2Activity.this,"Hello",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Main2Activity.this,ViewMessages.class);
            TextView v = findViewById(view.getId());
            intent.putExtra("Subject",v.getText().toString());
            intent.putExtra("Reg",reg);
            startActivity(intent);
        }
    };
    public void tt(View view){
        Intent intent = new Intent(this,ttActvity.class);
        startActivity(intent);
    }
}
