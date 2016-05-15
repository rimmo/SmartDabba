package com.example.rimi.khanasurfing;

import android.graphics.Bitmap;

/**
 * Created by Rimmi Biswas on 3/20/2016.
 */
public class config {
    public static String[] names;
    public static String[] prices;
    public static String[] contacts;
    public static String[] address;
    public static String[] desc;
    //public static Bitmap[] bitmaps;

    //public static final String GET_URL = "http://kanizfood.ml/MYAPI/insertsupplierdtls";
    public static final String TAG_IMAGE_PRICE = "price";
    public static final String TAG_IMAGE_NAME = "Name";
    public static final String TAG_IMAGE_CONTACT = "Mob";
    public static final String TAG_IMAGE_ADDRESS = "City";
    public static final String TAG_IMAGE_DESC= "Description";
    public config(int i){
        names = new String[i];
        prices = new String[i];
        contacts= new String[i];
        address=new String[i];
        desc=new String[i];
       // bitmaps = new Bitmap[i];
    }
}
