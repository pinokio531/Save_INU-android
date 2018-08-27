package com.likelioninu.save_inu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class splash extends AppCompatActivity {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        WaitingTime();

        SharedPreferences isFirst = getSharedPreferences("WW2", Context.MODE_PRIVATE);
        Boolean a = isFirst.getBoolean("booleanValue", true);

        if(a==true){
            WaitingTime();
        }
        else{
            WaitingTime2();
        }

    }

    private void WaitingTime() {
        Thread SplashThread = new Thread() {
            @Override
            public void run() {
                try {
                    int wait = 0;
                    while (wait < 1000) {
                        sleep(100);
                        wait += 100;
                    }
                    Intent intent = new Intent(splash.this, select_page.class);
                    startActivity(intent);
                    finish();
                } catch (InterruptedException e) {

                }
            }

        };
        SplashThread.start();
    }

    private void WaitingTime2() {
        Thread SplashThread = new Thread() {
            @Override
            public void run() {
                try {
                    int wait = 0;
                    while (wait < 1000) {
                        sleep(100);
                        wait += 100;
                    }
                    Intent intent = new Intent(splash.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } catch (InterruptedException e) {

                }
            }

        };
        SplashThread.start();
    }
}
