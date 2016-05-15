package com.example.rimi.khanasurfing;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dmax.dialog.SpotsDialog;

public class nextaftersplashscreen extends AppCompatActivity {
    EditText email, pswd;
    //Spinner spinner;
    //TextView tv,tv2;


    TextView t,tv2;
    Button login;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    AlertDialog.Builder ald;
    //ProgressBar p;
    AlertDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nextaftersplashscreen);
        ald = new AlertDialog.Builder(this);
        //final Spinner spinner = (Spinner) findViewById(R.id.spinner1);
        pDialog=new SpotsDialog(nextaftersplashscreen.this,R.style.Login);
        pDialog.setCancelable(false);
        //spinner.setOnItemSelectedListener(this);
        getSupportActionBar().hide();
//        LayoutInflater inflater = getLayoutInflater();
//        View layout = inflater.inflate(R.layout.activity_nextaftersplashscreen,
//                (ViewGroup) findViewById(R.id.toast_custom));
//
//        TextView text = (TextView) layout.findViewById(R.id.tvtoast);
//        text.setText("Invalid login");
//        text.setTextColor(Color.rgb(0, 132, 219));
//        AlertDialog alertDialog = new AlertDialog.Builder(
//                nextaftersplashscreen.this).create();
        email = (EditText) findViewById(R.id.input_email);
        pswd = (EditText) findViewById(R.id.input_password);
        login = (Button) findViewById(R.id.btn);
        t = (TextView) findViewById(R.id.link_signup);
        //p=(ProgressBar)findViewById(R.id.prg);
        //tv2 = (TextView) findViewById(R.id.textView6);
        //tv2 = (TextView) findViewById(R.id.skp);
       Typeface face = Typeface.createFromAsset(getAssets(), "fonts/droid.ttf");
       t.setTypeface(face);
//        tv2.setTypeface(face);
        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(nextaftersplashscreen.this, "Upload your Details", Toast.LENGTH_LONG).show();
                Intent in = new Intent(nextaftersplashscreen.this, firstpage.class);
                startActivity(in);


            }

        });
//        tv2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent in = new Intent(nextaftersplashscreen.this, User_dtls.class);
//                startActivity(in);
//            }
//        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int flag = 0;
                if (email.length() == 0) {
                    email.setError("Enter email");
                    flag = 1;
                }
                if (pswd.length() == 0) {
                    pswd.setError("Enter password");
                    flag = 2;
                }
                if (flag == 0) {
                    String e = email.getText().toString();
                    String p = pswd.getText().toString();

                    new Login().execute(e,p);
                }
            }
        });

    }
    class Login extends AsyncTask<String, String, String> {
        String ConUrl;
        DataOutputStream dos;
        @Override
        protected void onPreExecute() {
            pDialog.show();
            ConUrl = "http://kanizfood.ml/MYAPI/userlogin";
            //p.setVisibility(View.VISIBLE);

        }

        @Override
        protected String doInBackground(String... args) {
            JSONObject jsonObject= new JSONObject();
            String email, pass;
            email = args[0].toString();
            pass = args[1].toString();
            try {
                URL url=new URL(ConUrl);
                HttpURLConnection htp=(HttpURLConnection)url.openConnection();
                htp.setRequestMethod("POST");
                htp.setRequestProperty("Content-Type", "application/json");
                htp.setDoInput(true);
                htp.setDoOutput(true);
                htp.setUseCaches(false);
                htp.connect();
                jsonObject.put("eml", email);
                jsonObject.put("pass", pass);
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
        protected void onPostExecute(String json) {
            if(json!=null)
            {
                try {
                    JSONObject obj=new JSONObject(json.toString());

                    JSONArray user = obj.getJSONArray("login");
                    JSONObject jb= user.getJSONObject(0);
                    String data=obj.getString("login");

                    if(data.length()>10){
                        LoginDetails.setId(jb.getString("id"));
                        LoginDetails.setFName(jb.getString("name"));
                        LoginDetails.setMob(jb.getString("contact"));
                        LoginDetails.setEmail(jb.getString("email"));
                        LoginDetails.setPass(jb.getString("password"));
                        LoginDetails.setAddrs(jb.getString("address"));
                        //pdialog.dismiss();
                        startActivity(new Intent(nextaftersplashscreen.this, User_dtls.class));
                    }
                    else {
                        ald.setTitle("Error");
                        ald.setMessage("Invalid Login");
                        //ald.setIcon(R.drawable.img2);
                        ald.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Write your code here to execute after dialog closed
                                //Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
                            }
                        });
                        AlertDialog alertDialog = ald.create();
                        alertDialog.show();
                        //Toast.makeText(getApplication(),"Invalid login ",Toast.LENGTH_LONG).show();
                    }
                    //p.setVisibility(View.GONE);
                } catch (JSONException e) {
                    e.printStackTrace();
                    //pdialog.dismiss();
                }
            }else
            {
                Toast.makeText(nextaftersplashscreen.this, "Failed to connect server", Toast.LENGTH_LONG).show();
            }
            pDialog.dismiss();
        }
        }

    }
