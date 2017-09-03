package com.example.user.hotspots;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    String res = "";
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);


        Button btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddHotspot.class);
                startActivity(new Intent(intent));
            }
        });

        new LoadHotspot().execute();

    }
    protected void onResume(){
        super.onResume();
        new LoadHotspot().execute();
    }



    private class LoadHotspot extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {

                res = HttpHandler.getJSONFromUrl("http://bestlab.us:8080/places");

                return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Gson gson = new Gson();

            Type listType = new TypeToken<List<Hotspot>>(){}.getType();
            List<Hotspot> list =  gson.fromJson( res , listType);
            List<Map<String, String>> data = new ArrayList<Map<String, String>>();
            for (Hotspot hotspot: list) {
                Map<String, String> datum = new HashMap<String, String>(2);

                datum.put("code", hotspot.getCode());
                datum.put("address", hotspot.getAddress());
                data.add(datum);
            }


            SimpleAdapter adapter = new SimpleAdapter(MainActivity.this, data,
                    android.R.layout.simple_expandable_list_item_2, new String[]{"code", "address"},  new int[] {android.R.id.text1,
                    android.R.id.text2});
            listView.setAdapter(adapter);

        }
    }
}
