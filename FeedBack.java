package com.example.rimi.khanasurfing;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FeedBack extends AppCompatActivity {
TextView rate,nothanks;
    Button report;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        report=(Button)findViewById(R.id.report);
        nothanks=(TextView)findViewById(R.id.nothanks);
        getSupportActionBar().setTitle("FeedBack");
rate=(TextView)findViewById(R.id.rate);
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("plain/text");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[] { "kaniz19932016@gmail.com" });
                intent.putExtra(Intent.EXTRA_SUBJECT, "subject");
                intent.putExtra(Intent.EXTRA_TEXT, "mail body");
                startActivity(Intent.createChooser(intent, ""));
            }
        });
        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater=getLayoutInflater();
                AlertDialog.Builder adb=new AlertDialog.Builder(FeedBack.this);
                View vw= layoutInflater.inflate(R.layout.activity_rate_app,null);
                adb.setView(vw);
                AlertDialog ad=adb.create();
                ad.show();
            }
        });
        nothanks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences myPrefs = getSharedPreferences("Activity",
                        MODE_PRIVATE);
                SharedPreferences.Editor editor = myPrefs.edit();
                editor.clear();
                editor.commit();
                //AppState.getSingleInstance().setLoggingOut(true);
                setLoginState(true);
                Log.e("logout", "Now log out and start the activity login");
                Intent intent = new Intent(FeedBack.this,
                        nextaftersplashscreen.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }

            private void setLoginState(boolean b) {
                SharedPreferences s=getSharedPreferences("login state",MODE_PRIVATE);
                SharedPreferences.Editor ed = s.edit();
                ed.putBoolean("setLoggingOut", b);
                ed.commit();
            }
        });

    }
}
