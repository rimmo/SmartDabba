package com.example.rimi.khanasurfing;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CrdView extends AppCompatActivity {
    TextView tv,desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crd_view);
        tv = (TextView) findViewById(R.id.number);
        desc = (TextView) findViewById(R.id.desc);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                if (ActivityCompat.checkSelfPermission(CrdView.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(callIntent);
            }
        });
//        desc.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                LayoutInflater layoutInflater=getLayoutInflater();
//                AlertDialog.Builder adb=new AlertDialog.Builder(CrdView.this);
//                View vw= layoutInflater.inflate(R.layout.activity_crd_view,null);
//                adb.setView(vw);
//                AlertDialog ad=adb.create();
//                ad.show();
//            }
//        });
    }
}
