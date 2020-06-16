package com.example.day2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Signin extends AppCompatActivity {
    private Button timeto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);
//        timeto = findViewById(R.id.button4);
//        timeto.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                timetostart();
//            }
//        });

    }
    public void timetostart(View view){
        Intent intent = new Intent(Signin.this,TimerClockActivity.class);
        startActivity(intent.cloneFilter());
    }

}
