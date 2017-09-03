package com.example.user.hotspots;

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
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by user on 15/7/2017.
 */

public class HttpHandler {
    public static String doGet(String urlStr){

        try {
            URL url = new URL(urlStr);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */ );
            urlConnection.setConnectTimeout(15000 /* milliseconds */ );
            urlConnection.setDoOutput(true);
            urlConnection.connect();

            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String line;
            StringBuilder builder = new StringBuilder();
            while((line = reader.readLine())!=null){
                builder.append(line);
            }
            reader.close();
            return builder.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
    public static String doPost(String urlStr, Hotspot hotspot){

        try {

            URL url = new URL(urlStr);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //Connect
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.connect();

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", hotspot.getCode());
            jsonObject.put("address", hotspot.getAddress());
            jsonObject.put("lat", hotspot.getLat());
            jsonObject.put("lon", hotspot.getLon());
            jsonObject.put("datetime", hotspot.getCurrentDatetime());


            //Write
            OutputStream outputStream = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            writer.write(jsonObject.toString());
            writer.close();
            outputStream.close();

            int status = conn.getResponseCode();
            InputStream inputStream;
            //Read
            if (status >= 400 && status <= 499){
               throw new Exception("Bad authentication status: "+ status);
            }else {
                inputStream = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                StringBuilder builder = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
                return builder.toString();
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";

    }

    public static String getJSONFromUrl(String url)  {

        HttpURLConnection httpURLConnection ;
        BufferedReader bufferedReader = null;
        StringBuilder stringBuilder;
        String line;
        String jsonString = null;
        try {
            URL u = new URL(url);
            httpURLConnection = (HttpURLConnection) u.openConnection();
            httpURLConnection.setRequestMethod("GET");
            bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            stringBuilder = new StringBuilder();

            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line + '\n');
            }
            jsonString = stringBuilder.toString();
            httpURLConnection.disconnect();
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonString;
    }



}
