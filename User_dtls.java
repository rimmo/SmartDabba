package com.example.rimi.khanasurfing;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

//import com.google.android.gms.maps.GoogleMap;
public class User_dtls extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, LocationListener {
    GoogleMap googleMap;
    double latitude, longitude;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    NavigationView navigationView;
    private config cnfg;
    AlertDialog ad;
    AutoCompleteTextView txtCity;
    TextView UName,UMail,UMob;
    Button btn;
    List<String> list=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dtls);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        new getData().execute();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

         navigationView = (NavigationView) findViewById(R.id.nav_view);
        changeHeader();
        navigationView.setNavigationItemSelectedListener(this);
//changeHeader();
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, true);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(bestProvider);
        if (location != null) {
            onLocationChanged(location);
        }
        locationManager.requestLocationUpdates(bestProvider, 20000, 0, (LocationListener) this);

        Geocoder geocoder = new Geocoder(User_dtls.this, Locale.getDefault());

        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            String city = addresses.get(0).getLocality();
//            String state = addresses.get(0).getAdminArea();
//            String zip = addresses.get(0).getPostalCode();
//            String country = addresses.get(0).getCountryName();
            String area=addresses.get(0).getSubLocality();
            Log.e("City", city);
            android.support.v7.app.ActionBar ab = getSupportActionBar();
            ab.setTitle(city+" "+ area);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void changeHeader() {
        View v=navigationView.inflateHeaderView(R.layout.nav_header_user_dtls);
        UName=(TextView)v.findViewById(R.id.tv1);
        UMail=(TextView)v.findViewById(R.id.tv2);
        UMob=(TextView)v.findViewById(R.id.tv3);
        UName.setText(LoginDetails.getFName());
        UMail.setText(LoginDetails.getEmail());
        UMob.setText(LoginDetails.getMob());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
ArrayAdapter<String> aad;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_dtls, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.search) {
            LayoutInflater layoutInflater=getLayoutInflater();
            AlertDialog.Builder adb=new AlertDialog.Builder(User_dtls.this);
            View v= layoutInflater.inflate(R.layout.search_city,null);
            adb.setView(v);
            txtCity=(AutoCompleteTextView)v.findViewById(R.id.txtCity);
            btn=(Button)v.findViewById(R.id.ok);

            txtCity.setThreshold(1);
            txtCity.setAdapter(aad);
            ad=adb.create();
            ad.show();
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String c = txtCity.getText().toString();


                        new filterplace().execute(c);

                }
            });

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        LatLng latLng = new LatLng(latitude, longitude);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.edit_profile) {
            // Handle the camera action
            startActivity(new Intent(User_dtls.this,Edit_profile.class));
        } else if (id == R.id.fdbck) {
            startActivity(new Intent(User_dtls.this,FeedBack.class));

        }  else if (id == R.id.nav_share) {
            try {
                PackageManager pm = getPackageManager();
                ApplicationInfo ai = pm.getApplicationInfo(getPackageName(), 0);
                File srcFile = new File(ai.publicSourceDir);
                Intent share = new Intent();
                share.setAction(Intent.ACTION_SEND);
                share.setType("application/vnd.android.package-archive");
                share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(srcFile));
                startActivity(Intent.createChooser(share, "Share Via"));
            } catch (Exception e) {
                Log.e("ShareApp", e.getMessage());
            }

        } else if (id == R.id.abt) {
            startActivity(new Intent(User_dtls.this,About_us.class));

        }else if (id == R.id.sgnout) {
            SharedPreferences myPrefs = getSharedPreferences("Activity",
                    MODE_PRIVATE);
            SharedPreferences.Editor editor = myPrefs.edit();
            editor.clear();
            editor.commit();
            //AppState.getSingleInstance().setLoggingOut(true);
            setLoginState(true);
            Log.e("logout", "Now log out and start the activity login");
            Intent intent = new Intent(User_dtls.this,
                    nextaftersplashscreen.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setLoginState(boolean status) {
        SharedPreferences sp = getSharedPreferences("LoginState",
                MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        ed.putBoolean("setLoggingOut", status);
        ed.commit();
    }

    class getData extends AsyncTask<String, Void, String> {
        List<String> data = new ArrayList<>();

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
            //return null;
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
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
                    config.contacts[i] = j.getString(config.TAG_IMAGE_CONTACT);
                    config.address[i]=j.getString(config.TAG_IMAGE_ADDRESS);
                    config.prices[i] = j.getString(config.TAG_IMAGE_PRICE);
                    config.desc[i]=j.getString(config.TAG_IMAGE_DESC);
                }
                JSONObject obj = new JSONObject(s);
                JSONArray jsonArray = obj.getJSONArray("supplier");
                for (int r = 0; r < jsonArray.length(); r++)
                {
                    JSONObject jobj=jsonArray.getJSONObject(r);
                    String cty=jobj.getString("City");
                    list.add(cty);
                }
                Log.e("Data",""+list);

                //adapter = new MyAdapter(nm,nm);
                //Log.e("fetch data",""+config.names+"  "+config.prices);
                recyclerView.setAdapter(new MyAdapter(config.names, config.prices, config.contacts, config.address,config.desc,User_dtls.this));
                aad=new ArrayAdapter<String>(User_dtls.this,android.R.layout.simple_list_item_1,list);
//               try {
//                   JSONObject obj = new JSONObject(s);
//                   JSONArray jsonArray = obj.getJSONArray("supplier");
//                   for (int i = 0; i < jsonArray.length(); i++)
//                   {
//                       JSONObject jobj=jsonArray.getJSONObject(i);
//                       String cty=jobj.getString("City");
//                       list.add(cty);
//                   }
//                   Log.e("Data",""+list);
//                   aad=new ArrayAdapter<String>(User_dtls.this,android.R.layout.simple_spinner_item,list);
//
//               } catch (JSONException e){
//                   e.printStackTrace();
//               }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

    }

    class filterplace extends AsyncTask<String,String,String>
    {
        String ConUrl;
        DataOutputStream dos;
        protected void onPreExecute() {
            //pDialog.show();
            ConUrl = "http://kanizfood.ml/MYAPI/filteredsuppliers";
        }
        @Override
        protected String doInBackground(String... params) {
            JSONObject jsonObject= new JSONObject();
            String city;
            city = params[0].toString();

            try {
                URL url=new URL(ConUrl);
                HttpURLConnection htp=(HttpURLConnection)url.openConnection();
                htp.setRequestMethod("POST");
                htp.setRequestProperty("Content-Type", "application/json");
                htp.setDoInput(true);
                htp.setDoOutput(true);
                htp.setUseCaches(false);
                htp.connect();
                jsonObject.put("txtCity", city);

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
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


                try {
                    JSONObject obj = new JSONObject(s.toString());
                    JSONArray user = obj.getJSONArray("supplier");
                    JSONObject jb = user.getJSONObject(0);
                    String data = obj.getString("supplier");
                    if(data.equals("no_data_found"))
                    {
                    Toast.makeText(User_dtls.this,"No providers",Toast.LENGTH_LONG).show();

                } else
                    {
                        for (int i = 0; i < user.length(); i++) {
                            JSONObject j = user.getJSONObject(i);
                            config.names[i] = j.getString(config.TAG_IMAGE_NAME);
                            config.contacts[i] = j.getString(config.TAG_IMAGE_CONTACT);
                            config.address[i] = j.getString(config.TAG_IMAGE_ADDRESS);
                            config.prices[i] = j.getString(config.TAG_IMAGE_PRICE);
                            config.desc[i] = j.getString(config.TAG_IMAGE_DESC);
                        }

                        recyclerView.setAdapter(new MyAdapter(config.names, config.prices, config.contacts, config.address, config.desc, User_dtls.this));


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

ad.dismiss();
        }

    }
}
