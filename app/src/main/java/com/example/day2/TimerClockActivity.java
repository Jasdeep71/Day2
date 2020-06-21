package com.example.day2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Time;
import java.util.Locale;

public class TimerClockActivity extends AppCompatActivity {

    private TextView ed11;
    private TextView ed21;
    private TextView ed31;

    private TextView mTextViewCountDown;
    private Button mButtonStartPause;
    private EditText ForTime;
    private Button mButtonSet;
    private Button FinishTime;

    private String input;
    private long mTimeLeftInMillis;
    private long mEndTime;

    private static long mStartTimeInMillis;
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private TimeInitialisation temp = new TimeInitialisation(0,0,0);
    static DatabaseReference myRef;

    private TimeInitialisation UpdateTime(TimeInitialisation p,double NewTime){
        TimeInitialisation per = new TimeInitialisation(NewTime,p.getTotalScore()+NewTime,p.getPresentScore());
        return per;
    }
    private void database(final double ans){
//        Toast.makeText(TimerClockActivity.this,Double.toString(ans),Toast.LENGTH_SHORT).show();
        read(ans);
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference(Person1.hashcode);


//        Toast.makeText(TimerClockActivity.this, Double.toString(temp.getPresentScore())+" - "+Double.toString(temp.getTotalScore())+" - "+temp.getPreviousScore(),Toast.LENGTH_SHORT).show();
//        myRef.setValue(TimeInitialisation.continu);
//                double previous = TimeInitialisation.continu.getPreviousScore();
//                double present = TimeInitialisation.continu.getPresentScore();
//                double total = TimeInitialisation.continu.getTotalScore();
    }
    public void read(final double NewTime){
        // Read from the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference(Person1.hashcode);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                TimeInitialisation value = dataSnapshot.getValue(TimeInitialisation.class);
//              Toast.makeText(TimerClockActivity.this, Double.toString(value.getPresentScore())+" - "+Double.toString(value.getTotalScore())+" - "+value.getPreviousScore(),Toast.LENGTH_SHORT).show();
                ed11.setText(Double.toString(Math.round(value.getPresentScore()*100.0)/100.0));
                ed21.setText(Double.toString(Math.round(NewTime*100.0)/100.0));
                ed31.setText(Double.toString(Math.round((value.getTotalScore()+NewTime)*100.0)/100.0));
            }
            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_clock);
        mTextViewCountDown = findViewById(R.id.text_view_countdown);
        mButtonStartPause = findViewById(R.id.button_start_pause);
        mButtonSet = findViewById(R.id.button_set);
        FinishTime = findViewById(R.id.button_finish);
        ForTime = findViewById(R.id.edit_text_input);

        ed11 = findViewById(R.id.previous);
        ed21 = findViewById(R.id.present);
        ed31 = findViewById(R.id.total);

        ed11.setVisibility(View.INVISIBLE);
        ed21.setVisibility(View.INVISIBLE);
        ed31.setVisibility(View.INVISIBLE);

        ed11.setText("");
        ed21.setText("");
        ed31.setText("");


        mButtonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mStartTimeInMillis == 0) {
                    Toast.makeText(TimerClockActivity.this, "Please enter a number first", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mTimerRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        });
        mButtonSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input = ForTime.getText().toString();
                if (input.length() == 0) {
                    Toast.makeText(TimerClockActivity.this, "Field can't be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                long millisInput = Long.parseLong(input) * 60000;
                if (millisInput == 0) {
                    Toast.makeText(TimerClockActivity.this, "Please enter a positive number", Toast.LENGTH_SHORT).show();
                    return;
                }

                setTime(millisInput);
                mButtonSet.setVisibility(View.INVISIBLE);
                ForTime.setVisibility(View.INVISIBLE);
                updateCountDownText();
            }
        });
        FinishTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    double ans = (double) (-mTimeLeftInMillis + mStartTimeInMillis) / 1000;
                    database(ans);
                if (!ed11.getText().toString().equals("")){
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    myRef = database.getReference(Person1.hashcode);
//                    Toast.makeText(TimerClockActivity.this,ed21.getText().toString()+ed31.getText().toString()+ed11.getText().toString(),Toast.LENGTH_SHORT).show();
                    myRef.setValue(new TimeInitialisation(Double.parseDouble(ed21.getText().toString()),Double.parseDouble(ed31.getText().toString()),Double.parseDouble(ed11.getText().toString())));
                    startActivity(nextpage(ed11.getText().toString(),ed21.getText().toString(),ed31.getText().toString()));
                    finish();
                }
                else{
                    FinishTime.setText("Show My Result");
                }

            }
        });

    }


    public Intent nextpage(String previous,String present,String total){
        Intent intent = new Intent(TimerClockActivity.this,score.class);
//        previous = Math.round(previous*100.0)/100.0;
//        present = Math.round(present*100.0)/100.0;
//        total = Math.round(total*100.0)/100.0;
        String p = previous+"/"+present+"@"+total;
        intent.putExtra("hello",p);
        return intent;

    }
    private void setTime(long milliseconds) {
        mStartTimeInMillis = milliseconds;
        resetTimer();
    }
    private void startTimer() {
        mEndTime = System.currentTimeMillis() + mTimeLeftInMillis;

        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                updateButtons();
            }
        }.start();

        mTimerRunning = true;
        updateButtons();
    }

    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        updateButtons();
    }

    private void resetTimer() {
        mTimeLeftInMillis = mStartTimeInMillis;
        updateCountDownText();
        updateButtons();
    }

    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        mTextViewCountDown.setText(timeLeftFormatted);
    }

    private void updateButtons() {
        if (mTimerRunning) {
            mButtonStartPause.setText("Pause");
            FinishTime.setVisibility(View.INVISIBLE);

        } else {
            mButtonStartPause.setText("Start");

            if (mTimeLeftInMillis < 1000) {
                if(mTimeLeftInMillis!=0) {
                    mButtonStartPause.setVisibility(View.INVISIBLE);
                    FinishTime.setVisibility(View.VISIBLE);
                }
            } else {
                mButtonStartPause.setVisibility(View.VISIBLE);
                if(mStartTimeInMillis!=mTimeLeftInMillis) {
                    FinishTime.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("millisLeft", mTimeLeftInMillis);
        outState.putBoolean("timerRunning", mTimerRunning);
        outState.putLong("endTime", mEndTime);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        mTimeLeftInMillis = savedInstanceState.getLong("millisLeft");
        mTimerRunning = savedInstanceState.getBoolean("timerRunning");
        updateCountDownText();
        updateButtons();

        if (mTimerRunning) {
            mEndTime = savedInstanceState.getLong("endTime");
            mTimeLeftInMillis = mEndTime - System.currentTimeMillis();
            startTimer();
        }
    }
}