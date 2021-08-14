package com.alitalhacoban.pomodoro;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.hanks.htextview.evaporate.EvaporateTextView;
import com.thekhaeng.pushdownanim.PushDownAnim;


import java.util.ArrayList;

@SuppressWarnings("deprecation")
public class MainActivity extends AppCompatActivity {

    Button work_btn, break_btn;
    Handler handler;
    Runnable runnable_sec, runnable_min, runnable_sec2, getRunnable_min2;


    private boolean isPaused = true;
    private boolean isFirst = true;

    boolean isOnWork = true;
    boolean isOnBreak;

    EvaporateTextView evaporateTextView_min, evaporateTextView_sec;

    int delay_sec = 1000;
    int minute = 25;

    ArrayList<String> arrSec = new ArrayList<>();

    int position_sec = 0;

    String numOfContent = "1";

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isOnBreak = !isOnWork;
        
        textView = findViewById(R.id.stop_resume_textView);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.blink_anim);
        textView.startAnimation(animation);

        for (int i = 59; i >= 0; i--) {
            arrSec.add(String.valueOf(i));
        }

        handler = new Handler();

        work_btn = findViewById(R.id.work_btn);
        break_btn = findViewById(R.id.break_btn);

        evaporateTextView_min = findViewById(R.id.evaporateTextView_min);
        evaporateTextView_sec = findViewById(R.id.evaporateTextView_sec);

        setCounterValues();
        pause();

    }

    public void setCounterValues() {

        handler.postDelayed(runnable_sec = new Runnable() {

            int min = minute - 1;

            public void run() {

                handler.postDelayed(this, delay_sec);
                if (position_sec >= arrSec.size()) {
                    position_sec = 0;
                    min--;

                    if (min < 0) {
                        if (isOnBreak) {
                            setWorkScreen();
                        } else {
                            setBreakScreen();
                        }
                        pause();
                    } else {
                        if (min < 10) {
                            evaporateTextView_min.animateText("0" + min);

                        } else {
                            evaporateTextView_min.animateText(String.valueOf(min));
                        }
                        evaporateTextView_sec.animateText(arrSec.get(position_sec));
                    }

                } else if (position_sec > arrSec.size() - 11) {

                            if (evaporateTextView_sec != null) {
                                evaporateTextView_sec.animateText("0" + arrSec.get(position_sec));
                            }


                } else {

                            if (evaporateTextView_sec != null) {
                                evaporateTextView_sec.animateText(arrSec.get(position_sec));
                            }


                }
                position_sec++;
            }
        }, delay_sec);
    }

    public void workBtnClick(View view) {
        setWorkScreen();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void setWorkScreen() {

        evaporateTextView_min.animateText("25");
        evaporateTextView_sec.animateText("00");

        minute = 25;
        isOnWork = true;
        isOnBreak = false;
        position_sec = 0;
        pause();

        work_btn.setBackgroundDrawable(getResources().getDrawable(R.drawable.active_btn_background));
        break_btn.setBackgroundDrawable(getResources().getDrawable(R.drawable.inactive_btn_background));
        textView.setText(R.string.start_text);

        work_btn.setText(R.string.work_btn_text);
        break_btn.setText(R.string.break_btn_text);

        isFirst = true;
        isPaused = true;

    }

    public void breakBtnClick(View view) {
        setBreakScreen();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void setBreakScreen() {
        evaporateTextView_min.animateText("05");
        evaporateTextView_sec.animateText("00");

        minute = 5;
        position_sec = 0;
        isOnWork = false;
        isOnBreak = true;
        pause();

        break_btn.setBackgroundDrawable(getResources().getDrawable(R.drawable.active_btn_background));
        work_btn.setBackgroundDrawable(getResources().getDrawable(R.drawable.inactive_btn_background));
        textView.setText(R.string.start_text);

        break_btn.setText(R.string.break_btn_text);
        work_btn.setText(R.string.work_btn_text);

        isFirst = true;
        isPaused = true;

    }

    private void pause() {
        handler.removeCallbacks(runnable_sec);
    }

    private void resume() {

       /* leftMin = (60 - position_sec) * 1000;
        handler.postDelayed(runnable_min, leftMin);*/

        handler.postDelayed(runnable_sec, 0);

    }

    public void screenClick(View view) {

        if (isPaused) {
            if (isFirst) {

                if (isOnBreak) {
                    evaporateTextView_min.animateText("04");
                    break_btn.setText(R.string.reset);
                } else {
                    evaporateTextView_min.animateText("24");
                    work_btn.setText(R.string.reset);
                }

                textView.setText(R.string.start_text);
                isFirst = false;
            }

            if (isOnBreak) {
                break_btn.setText(R.string.reset);
            } else {
                work_btn.setText(R.string.reset);
            }

            resume();
            isPaused = false;

            textView.setText(R.string.pause_text);
        } else {
            pause();
            isPaused = true;
            textView.setText(R.string.resume_text);
        }
    }

}