package com.example.rimi.khanasurfing;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import dmax.dialog.SpotsDialog;

public class Edit_profile extends AppCompatActivity {
EditText tv1,tv2,tv3,tv4,tv5,tv6;
    Button save;

    AlertDialog pDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        getSupportActionBar().setTitle("Edit Profile");
        pDialog=new SpotsDialog(Edit_profile.this,R.style.Update);
        pDialog.setCancelable(false);

        tv1=(EditText)findViewById(R.id.name);
        tv2=(EditText)findViewById(R.id.email);
        tv2.setEnabled(false);
        tv3=(EditText)findViewById(R.id.password);
        //tv4=(EditText)findViewById(R.id.confirm_password);
        tv5=(EditText)findViewById(R.id.contact);
        tv6=(EditText)findViewById(R.id.adrs);
        save=(Button)findViewById(R.id.submit);

        tv1.setText(LoginDetails.getFName() );
        tv2.setText(LoginDetails.getEmail());
        tv3.setText(LoginDetails.getPass());
        tv5.setText(LoginDetails.getMob());
        tv6.setText(LoginDetails.getAddrs());

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                new updateDetails().execute(tv1.getText().toString(),
                        tv5.getText().toString(),
                        tv3.getText().toString(),tv6.getText().toString());
                startActivity(new Intent(Edit_profile.this, User_dtls.class));

            }
        });


    }

    class updateDetails extends AsyncTask<String, String, String> {
        String ConUrl;
        DataOutputStream dos;
        @Override
        protected void onPreExecute() {
            pDialog.show();
            ConUrl = "http://kanizfood.ml/MYAPI/updatedetails";
        }

        @Override
        protected String doInBackground(String... args) {
            JSONObject jsonObject= new JSONObject();
            String name, con,pass,area;
            name = args[0].toString();
            con = args[1].toString();
            pass = args[2].toString();
            area = args[3].toString();
            try {
                URL url=new URL(ConUrl);
                HttpURLConnection htp=(HttpURLConnection)url.openConnection();
                htp.setRequestMethod("POST");
                htp.setRequestProperty("Content-Type", "application/json");
                htp.setDoInput(true);
                htp.setDoOutput(true);
                htp.setUseCaches(false);
                htp.connect();
                jsonObject.put("name", name);
                jsonObject.put("con", con);
                jsonObject.put("pass", pass);
                jsonObject.put("addr", area);
                jsonObject.put("Id", LoginDetails.getId().toString());
                dos=new DataOutputStream(htp.getOutputStream());
                dos.writeBytes(jsonObject.toString());
                dos.flush();
                dos.close();
                InputStream inputStream=htp.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                String Line;
                StringBuilder sb=new StringBuilder() ;
                while((Line=bufferedReader.readLine())!=null)
                {
                    sb.append(Line);
                }
                return sb.toString().toString();
            } catch (Exception E) {
                Log.e("Error ", E.getMessage().toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String json)
        {
            if(json!=null)
            {
                try
                {
                    JSONObject obj=new JSONObject(json.toString());
                    if(obj.getString("result").equals("success"))
                    {
                        Toast.makeText(Edit_profile.this, "Update Successfully Done.......", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(Edit_profile.this, "Fail To Update. Try Later", Toast.LENGTH_SHORT).show();
                    }

                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
            else
            {
                Toast.makeText(Edit_profile.this, "Server Error.......", Toast.LENGTH_SHORT).show();
            }
            pDialog.dismiss();
        }
    }
}
