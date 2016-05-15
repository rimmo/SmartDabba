package com.example.rimi.khanasurfing;

import android.app.VoiceInteractor;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class providerpage extends AppCompatActivity {
EditText et1,et2,et3,et4,et5,et6;
    Button btn;
    Spinner spin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_providerpage);
        et1=(EditText)findViewById(R.id.name);
        et2=(EditText)findViewById(R.id.email);
        et3=(EditText)findViewById(R.id.password);
        et4=(EditText)findViewById(R.id.contact);
        et5=(EditText)findViewById(R.id.address);
        btn=(Button)findViewById(R.id.from);
        et6=(EditText)findViewById(R.id.price);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savedata(et1.getText().toString(),et2.getText().toString(),et3.getText().toString(),et4.getText().toString(),et5.getText().toString(),et6.getText().toString(),spin.getSelectedItem().toString());
                Intent i=new Intent(providerpage.this,UploadFoodDetails.class);
                startActivity(i);
            }
        });
    }

    private void savedata(String name, String contact, String address, String email, String pass,String price,String spin) {
        JSONObject data = new JSONObject();
        String Url = "http://kanizfood.ml/MYAPI/insertsupplierdtls";
        try {
            data.put("name", name);
            data.put("contact", contact);
            data.put("address", address);
            data.put("email", email);
            data.put("pass", pass);
            data.put("price", price);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestQueue requestQueue = Volley.newRequestQueue(providerpage.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Url, data, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject response) {
                Log.e("Server  Response", response.toString());
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(jsonObjectRequest);
    }}