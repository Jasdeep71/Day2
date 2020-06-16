package com.example.day2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Signin extends AppCompatActivity {
    private Button timeto;
    EditText emailInput1;
    EditText passwordInput1;
    String email;
    String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);
        timeto = findViewById(R.id.button4);
        emailInput1 = findViewById(R.id.editText);
        passwordInput1 = findViewById(R.id.editText2);
        timeto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailInput1.getText().toString();
                password = passwordInput1.getText().toString();
                if(email.isEmpty() || password.isEmpty()){
                    Toast.makeText(Signin.this, "No field can be left empty", Toast.LENGTH_SHORT).show();
                }
                else {
                    timetostart();
                }
            }
        });

    }
    public void timetostart(){
        Intent intent = new Intent(Signin.this,TimerClockActivity.class);
        startActivity(intent.cloneFilter());
    }

}
