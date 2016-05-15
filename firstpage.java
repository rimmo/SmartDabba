package com.example.rimi.khanasurfing;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class firstpage extends AppCompatActivity {
    //
    EditText email,pass,cpass,name,contact,address;
    Button submit;
    RadioGroup rg;
    public int token;
    String cpass1;
    String pass1;
int err;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstpage);
        getSupportActionBar().setTitle("User Registration");
        email = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.password);
       // cpass = (EditText) findViewById(R.id.confirm_password);
        name = (EditText) findViewById(R.id.name);
        contact = (EditText) findViewById(R.id.contact);
        address = (EditText) findViewById(R.id.address);

        //rg=(RadioGroup)findViewById(R.id.rg);

        submit= (Button) findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int flag=0;
                if(name.length()==0){
                    name.setError("Enter name");
                    flag=1;
                }
                final String email1 = email.getText().toString();
                if (!isValidEmail(email1)) {
                    email.setError("Invalid Email");
                    flag=2;
                }
                pass1 = pass.getText().toString();
                 if (!isValidPassword(pass1)) {
                    pass.setError("Make a combination of characters and numbers to strong ur password");
                    flag=3;
                }

                if(contact.length()==0){

                    contact.setError("Enter contact number");
                    flag=4;
                }
                if(address.length()==0){
                    //  Toast toast;
                    address.setError("Enter address");
                    flag=5;
                }


                if(flag==0) {
                    saveData(name.getText().toString(), email.getText().toString(), pass.getText().toString(), contact.getText().toString(), address.getText().toString());

                    Toast.makeText(firstpage.this,"You are successfully registered",Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(firstpage.this, nextaftersplashscreen.class);
                    Toast.makeText(firstpage.this,"Please login now",Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
            }

        });

    }
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    private boolean isValidPassword(String pass) {
        if (pass != null && pass.length() > 6) {
            return true;
        }
        return false;
    }
    public void saveData(String name,String email,String password, String contact,String address){
        JSONObject data=new JSONObject();
        String Url = "http://kanizfood.ml/MYAPI/insertuserdtls";
        try {
            data.put("name", name);
            data.put("email", email);
            data.put("pass", password);
            data.put("cont", contact);
            data.put("addr", address);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestQueue requestQueue = Volley.newRequestQueue(firstpage.this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Url, data,
                new Response.Listener<JSONObject>() {
                    public void onResponse(JSONObject response) {
                        Log.e("Server Response", response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //int f=1;
                //err=f;
                Log.e("Server Error", error.toString());
                Toast.makeText(firstpage.this,"Network problem",Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}

