package com.mysticidiot.project_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    DatabaseReference reff;
    Staff s;
    public void move(View view){
        TextView reg = findViewById(R.id.editText);
        userInfo user = new userInfo();
        StringBuilder regn = new StringBuilder(reg.getText().toString());
        reff = FirebaseDatabase.getInstance().getReference().child("User");
        if(regn.indexOf("2") == 0) {
            boolean teacher = false;
            user.setRegno(regn.toString());
            int year = regn.toString().charAt(3)-'0';
            student st = new student();
            //st.setReg(regn.toString());
            st.setDept_id(regn.toString().charAt(6)-'0');
            year = regn.toString().charAt(3)-'0';
            if(year == 8)
                st.setSem(5);
            if(year == 7)
                st.setSem(7);
            if(year == 9)
                st.setSem(3);
            DatabaseReference reff1= FirebaseDatabase.getInstance().getReference().child("Student");
            reff1.child(regn.toString()).push().setValue(st);
            user.setTeacher(teacher);
            user.setDept_id(regn.toString().charAt(6)-'0');
            reff.push().setValue(user);
            Intent intent = new Intent(this, Main2Activity.class);
            intent.putExtra("Student Reg",regn.toString());
            Toast.makeText(this, "Welcome to IT department, 3rd year", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        }
        else if(regn.indexOf("1") == 0){
            boolean teacher = true;
            user.setDept_id(6);
            user.setRegno(regn.toString());
            user.setTeacher(teacher);
            reff.push().setValue(user);

            Intent intent = new Intent(this, staffFirst.class);
            intent.putExtra("staff",regn.toString());
            DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference().child("Staff");
            Query query = ref1.orderByChild("ID").equalTo(Integer.parseInt(regn.toString()));
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot d : dataSnapshot.getChildren()){
                        Staff s = d.getValue(Staff.class);
                        String name = s.getName();
                        Toast.makeText(MainActivity.this,"Welcome "+name,Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            startActivity(intent);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
