package com.example.rimi.khanasurfing;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class description extends AppCompatActivity {
TextView d;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        d=(TextView)findViewById(R.id.d);
    }
}
