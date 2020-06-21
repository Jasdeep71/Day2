package com.example.day2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
        mAuth = FirebaseAuth.getInstance();
        timeto = findViewById(R.id.button4);
        emailInput1 = findViewById(R.id.Text);
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
                    SigninUser();
                }
            }
        });

    }
    private FirebaseAuth mAuth;
    private void SigninUser(){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Person1.email = email;
                            int i = 0;
                            for (i = 0; i < email.length(); i++) {
                                if(email.charAt(i)=='@'){
                                    break;
                                }
                            }
                            Person1.hashcode = email.substring(0,i);
                            timetostart();
//                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(Signin.this, "Authentication failed.",Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                        }

                        // ...
                    }
                });
    }
    public void timetostart(){
        Intent intent = new Intent(Signin.this,TimerClockActivity.class);
        startActivity(intent.cloneFilter());
    }

}
