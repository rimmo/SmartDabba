package com.example.rimi.khanasurfing;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {
TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();
        Typeface t2=Typeface.createFromAsset(getAssets(),"fonts/GoodDog.otf");
        tv=(TextView)findViewById(R.id.textView);
        tv.setTypeface(t2);
        Thread timerThread = new Thread() {
            public void run() {
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally
                {
                    Intent intent = new Intent(SplashScreen.this,nextaftersplashscreen.class);
                    startActivity(intent);
                }
            }
        };
        timerThread.start();

    }
    protected void onPause() {
        super.onPause();
        finish();

    }

}

