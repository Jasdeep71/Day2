package com.example.day2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Time;

public class score extends AppCompatActivity {
    private TextView ed1;
    private TextView ed2;
    private TextView ed3;
    private Button Finish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        Intent intent = getIntent();
        String arr = intent.getStringExtra("hello");
//        Toast.makeText(score.this,arr,Toast.LENGTH_SHORT).show();
        String previous,present,total;
        int first = 0;
        int second = 0;
        for (int i = 0; i < arr.length(); i++) {
            if(arr.charAt(i)=='/'){
                first = i-1;
            }
            if(arr.charAt(i) == '@'){
                second = i-1;
                break;
            }
        }
        previous = arr.substring(0,first+1);
        present = arr.substring(first+2,second+1);
        total = arr.substring(second+2);
        ed1 = findViewById(R.id.ed1);
        ed2 = findViewById(R.id.ed2);
        ed3 = findViewById(R.id.ed3);
        ed1.setText(previous);
        ed2.setText(total);
        ed3.setText(present);
        Finish = findViewById(R.id.endpage);
        Finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
