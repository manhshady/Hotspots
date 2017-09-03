package com.example.user.hotspots;

import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by user on 15/7/2017.
 */

public class Hotspot {
    @SerializedName("datetime")
    private String datetime ;

    @SerializedName("code")
    private String code;

    @SerializedName("lon")
    private double lon;

    @SerializedName("lat")
    private double lat;


    @SerializedName("address")
    private String address;

    public Hotspot(String code, String address, double lat, double lon) {
        this.datetime = getCurrentDatetime();
        this.code = code;
        this.address = address;
        this.lat = lat;
        this.lon = lon;
    }

    public String getCurrentDatetime() {
        Date currentDateTime = new Date();
        currentDateTime.getTime();
        return currentDateTime.toString();
    }


    public String getDatetime() {
        return datetime;
    }

    public String getCode() {
        return code;
    }

    public String getAddress() {
        return address;
    }

    public double getLon() {
        return lon;
    }

    public double getLat() {
        return lat;
    }


}

