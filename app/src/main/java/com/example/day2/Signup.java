package com.example.day2;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.PeriodicSync;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.net.PasswordAuthentication;
import java.util.ArrayList;
import java.util.Set;

public class Signup extends AppCompatActivity {
    private Button submit;
    String username,email,password;

    EditText usernameInput;
    EditText emailInput;
    EditText passwordInput;
    EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        submit = findViewById(R.id.Submit);

        usernameInput = findViewById(R.id.InputName);
        emailInput = findViewById(R.id.EmailInput);
        passwordInput = findViewById(R.id.PasswordInput);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = usernameInput.getText().toString();
                email = emailInput.getText().toString();
                password = passwordInput.getText().toString();
                if(!username.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
                   if(CalculateStrenght(password)>4) {
                        timetostart();
                    }
                }
            }
        });
        Signupclass.Person pers = new Signupclass.Person(email,username,password);
//        p.insert(pers);
    }
    public Signupclass p  = new Signupclass();

    @SuppressLint("SetTextI18n")
    public int CalculateStrenght(String s){
        int upperChars = 0, lowerChars = 0, numbers = 0, specialChars = 0, otherChars = 0, strengthPoints = 0;
        char c;
        int passwordLength = s.length();
        if (passwordLength <= 5) {
            strengthPoints =1;
        }
        else if (passwordLength <= 10) {
            strengthPoints = 2;
        }
        else {
            strengthPoints = 3;
        }
        for (int i = 0; i < passwordLength; i++) {
            c = s.charAt(i);
            if (c >= 'a' && c <= 'z') {
                if (lowerChars == 0) strengthPoints++;
                lowerChars = 1;
            }
            else if (c >= 'A' && c <= 'Z') {
                if (upperChars == 0) strengthPoints++;
                upperChars = 1;
            }
            else if (c >= '0' && c <= '9') {
                if (numbers == 0) strengthPoints++;
                numbers = 1;
            }
            else if (c == '_' || c == '@') {
                if (specialChars == 0) strengthPoints += 1;
                specialChars = 1;
            }
            else {
                if (otherChars == 0) strengthPoints += 2;
                otherChars = 1;
            }
        }

//        if (strengthPoints <= 3)
//        {
//            tvPasswordStrength.setText("Password Strength : LOW");
//            tvPasswordStrength.setBackgroundColor(Color.RED);
//        }
//        else if (strengthPoints <= 6) {
//            tvPasswordStrength.setText("Password Strength : MEDIUM");
//            tvPasswordStrength.setBackgroundColor(Color.YELLOW);
//        }
//        else if (strengthPoints <= 9){
//            tvPasswordStrength.setText("Password Strength : HIGH");
//            tvPasswordStrength.setBackgroundColor(Color.GREEN);

//        }
        return strengthPoints;
    }
    private void timetostart(){
        Intent intent = new Intent(this,Signin.class);
        startActivity(intent);
    }
}
