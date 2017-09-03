package com.example.user.hotspots;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

public class AddHotspot extends AppCompatActivity {

    EditText txtCode;
    EditText txtAddress;

    String res;
    double lat;
    double lon;
    String address;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hotspot);



        Button button = (Button) findViewById(R.id.btnBack);

        Button btnAdd = (Button) findViewById(R.id.btnSave);

        Button btnMap = (Button) findViewById(R.id.btnMap);

        txtCode = (EditText) findViewById(R.id.etCode);

        txtAddress = (EditText) findViewById(R.id.etAddress);

        TextView tvLan = (TextView) findViewById(R.id.tvLan);
        TextView tvLon = (TextView) findViewById(R.id.tvLon);

        if( getIntent().getExtras() != null)
        {
            lat = getIntent().getDoubleExtra("lat", 0);
            lon = getIntent().getDoubleExtra("lon", 0);
            tvLan.setText("Lantitude: " + lat);
            tvLon.setText("Longtitude: " + lon);
            address = getCompleteAddressString(lat, lon);
            txtAddress.setText(address);
        }





        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Hotspot hotspot =  new Hotspot(txtCode.getText() + "",txtAddress.getText() + "", lat, lon );
                new AddRequest().execute(hotspot);
                Toast.makeText(AddHotspot.this, "code: " + hotspot.getCode() + " address: " + hotspot.getAddress() + " lat: " +hotspot.getLat() + " lon: " + hotspot.getLon(), Toast.LENGTH_LONG).show();
            }
        });

        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AddHotspot.this, MapsActivity.class);
                startActivity(new Intent(intent));
            }
        });

    }

    private class AddRequest extends AsyncTask<Hotspot, Void, Void> {

        @Override
        protected Void doInBackground(Hotspot... hotspots) {

            res = HttpHandler.doPost("http://bestlab.us:8080/places", hotspots[0]);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if(!res.equals("")){
                Toast.makeText(AddHotspot.this, "Added", Toast.LENGTH_SHORT).show();
            }

        }
    }
    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0;  i <= returnedAddress.getMaxAddressLineIndex() ; i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                Log.w("Current loction address", "" + strReturnedAddress.toString());
            } else {
                Log.w("Current loction address", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("Current loction address", "Canont get Address!");
        }
        return strAdd;
    }
}
