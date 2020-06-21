package com.example.day2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Button submit;
    String username,email,password;

    EditText usernameInput;
    EditText emailInput;
    EditText passwordInput;
    public DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        mAuth = FirebaseAuth.getInstance();
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
                       WhenStart();
                       FirebaseDatabase database = FirebaseDatabase.getInstance();
                       int i;
                       for (i = 0; i < email.length(); i++) {
                           if(email.charAt(i)=='@'){
                               break;
                           }
                       }
                       Person1.hashcode = email.substring(0,i);
                       myRef = database.getReference(Person1.hashcode);
                       myRef.setValue(new TimeInitialisation(0,0,0));
                       timetostart();
                    }
                   else{
                       Toast.makeText(Signup.this, "Please enter a password consisting of number, capital letter, special charachteristics and small alphabet", Toast.LENGTH_SHORT).show();
                   }
                }
                else {
                    Toast.makeText(Signup.this, "No field can be left empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void WhenStart() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
            if (!email.isEmpty() && !password.isEmpty()) {
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                } else {
                                    Toast.makeText(Signup.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
//        updateUI(currentUser);
    }


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

//        MaterialButton tvPasswordStrength = null;
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
//
//        }
        return strengthPoints;
    }
    private void timetostart(){
        Intent intent = new Intent(this,Signin.class);
        startActivity(intent);
        finish();
    }
}
