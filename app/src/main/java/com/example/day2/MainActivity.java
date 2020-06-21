package com.example.day2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button_first);
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user!= null){
            Intent intent = new Intent(MainActivity.this,TimerClockActivity.class);

            String email = user.getEmail();
            int i = 0;
            for (i = 0; i < email.length(); i++) {
                if(email.charAt(i)=='@'){
                    break;
                }
            }
            Person1.hashcode = email.substring(0,i);
            startActivity(intent);
            finish();
        }
        Button button1 = findViewById(R.id.button_second);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity2();
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity3();
            }
        });

    }

    private void openActivity2(){
        Intent intent = new Intent(this,Signin.class);
        startActivity(intent);
    }
    private void openActivity3(){
        Intent intent = new Intent(this,Signup.class);
        startActivity(intent);
    }

}
