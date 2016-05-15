package com.example.rimi.khanasurfing;

import android.graphics.Bitmap;
import android.util.Base64;

import java.util.ArrayList;

/**
 * Created by Rimmi Biswas on 5/6/2016.
 */
public class LoginDetails {
    public static String getId() {
        return Id;
    }

    public static void setId(String id) {
        Id = id;
    }

    static String Id;
    static String Name;
    static String Mob;
    static String Email;
    static String Pass;
    static String Addr;
    static String Desc;








    public static String getFName() {

        return Name;
    }

    public static void setFName(String Name) {
        LoginDetails.Name = Name;
        //Log.e("set fname",FName);
    }


    public static String getMob() {
        return Mob;
    }

    public static void setMob(String mob) {
        Mob = mob;
    }

    public static String getEmail() {
        return Email;
    }

    public static void setEmail(String email) {
        Email = email;
    }

    public static String getPass() {
        return Pass;
    }

    public static void setPass(String pass) {
        Pass = pass;
    }

    public static String getAddrs() {
        return Addr;
    }

    public static void setAddrs(String addr) {
        Addr = addr;
    }













}
