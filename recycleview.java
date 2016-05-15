package com.example.rimi.khanasurfing;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class recycleview extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    private config cnfg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycleview);


        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        new getData().execute();
    }

    class getData extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            URL url = null;
            try {
                url = new URL("http://kanizfood.ml/MYAPI/getsupplier");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                Log.e("Connection", "Recyclerview");

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String json;
                while ((json = bufferedReader.readLine()) != null) {
                    sb.append(json + "\n");
                }
                Log.e("", sb.toString());
                return sb.toString().trim();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        List<String> data = new ArrayList<>();

        @Override
        protected void onPostExecute(String s) {
            Log.e("Data", s);
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(s);
                JSONArray array = jsonObject.getJSONArray("supplier");
                cnfg = new config(array.length());
                for (int i = 0; i < array.length(); i++) {
                    JSONObject j = array.getJSONObject(i);
//                    String name=j.optString("Name");
//                    String price=j.optString("price");
//                    data.add(name+","+price);
//                    Log.e("value","name "+name+" price"+price);
                    config.names[i] = j.getString(config.TAG_IMAGE_NAME);
                    config.prices[i] = j.getString(config.TAG_IMAGE_PRICE);
                    config.contacts[i] = j.getString(config.TAG_IMAGE_CONTACT);
                    config.address[i]=j.getString(config.TAG_IMAGE_ADDRESS);
                    config.desc[i]=j.getString(config.TAG_IMAGE_DESC);
                }

                //adapter = new MyAdapter(nm,nm);
                //Log.e("fetch data",""+config.names+"  "+config.prices);
                recyclerView.setAdapter(new MyAdapter(config.names, config.prices,config.contacts, config.address,config.desc,recycleview.this));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

}
//        getData();
//    }

//    private void getData() {
//        class GetData extends AsyncTask<Void,Void,String> {
//            ProgressDialog progressDialog;
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//                progressDialog = ProgressDialog.show(recycleview.this, "Fetching Data", "Please wait...",false,false);
//            }
//
//            @Override
//            protected void onPostExecute(String s) {
//                super.onPostExecute(s);
//                progressDialog.dismiss();
//                parseJSON(s);
//            }
//
//            @Override
//            protected String doInBackground(Void... params) {
//                BufferedReader bufferedReader = null;
//                try {
//                    URL url = new URL("http://kanizfood.ml/MYAPI/insertsupplierdtls");
//                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
//                    StringBuilder sb = new StringBuilder();
//
//                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
//
//                    String json;
//                    while((json = bufferedReader.readLine())!= null){
//                        sb.append(json+"\n");
//                    }
//                    Log.e("",sb.toString());
//                    return sb.toString().trim();
//
//                }catch(Exception e){
//                    return null;
//                }
//            }
//        }
//        GetData gd = new GetData();
//        gd.execute();
//    }
//
//    public void showData(){
////        adapter = new MyAdapter(names,prices);// config.bitmaps);
//        recyclerView.setAdapter(adapter);
//    }
//
//    private void parseJSON(String json){
//        try {
//            JSONObject jsonObject = new JSONObject(json);
//            JSONArray array = jsonObject.getJSONArray(config.TAG_JSON_ARRAY);
//
//            config = new config(array.length());
//
//            for(int i=0; i<array.length(); i++){
//                JSONObject j = array.getJSONObject(i);
//                config.names[i] = getName(j);
//                config.prices[i] = getPrice(j);
//            }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//
//    }
//
//    private String getName(JSONObject j){
//        String name = null;
//        try {
//            name = j.getString(config.TAG_IMAGE_NAME);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return name;
//    }
//
//    private String getPrice(JSONObject j){
//        String url = null;
//        try {
//            url = j.getString(config.TAG_IMAGE_URL);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return url;
//    }
//   /* private String getImage(JSONObject j){
//        String url = null;
//        try {
//            url = j.getString(config.TAG_IMAGE_URL);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return url;
//    }*/
//    }
//
//
