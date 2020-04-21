package com.project.trackcovid19;

import android.Manifest;
import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_LOCATION = 1;
    private TextView tcases, acases, rcases, dcases, yourloc;
    private RequestQueue mRequestQueue, nRequestQueue;
    private LottieAnimationView load, loadlist;
    private RecyclerView recyclerView;
    private stateAdapter mstateAdapter;
    private ArrayList<stateModel> stateList;
    TextView tvAddress;
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        tcases = findViewById(R.id.total_Cases);
        acases = findViewById(R.id.active_cases);
        rcases = findViewById(R.id.recovered_cases);
        dcases = findViewById(R.id.deceased_cases);
        load = findViewById(R.id.load);
        tvAddress = findViewById(R.id.location);


        loadlist = findViewById(R.id.loadlist);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        stateList = new ArrayList<>();


        mRequestQueue = Volley.newRequestQueue(this);
        nRequestQueue = Volley.newRequestQueue(this);
        parseJSON();
        stateJSON();

    }

    private void stateJSON() {

        loadlist.setVisibility(View.VISIBLE);
        String url = "https://api.rootnet.in/covid19-in/unofficial/covid19india.org/statewise";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject data = response.getJSONObject("data");
                            JSONArray jsonArray = data.getJSONArray("statewise");

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject statewise = jsonArray.getJSONObject(i);
                                String statename = statewise.getString("state");
                                String confirmed = statewise.getString("confirmed");
                                String recovered = statewise.getString("deaths");
                                String active = statewise.getString("active");
                                String deaths = statewise.getString("deaths");

                                stateList.add(new stateModel(statename, confirmed, active, recovered, deaths));

                                loadlist.setVisibility(View.GONE);
                            }

                            mstateAdapter = new stateAdapter(MainActivity.this, stateList);
                            recyclerView.setAdapter(mstateAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        nRequestQueue.add(request);
    }

    private void parseJSON() {
        load.setVisibility(View.VISIBLE);
        String url = "https://api.rootnet.in/covid19-in/unofficial/covid19india.org/statewise";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String jsonResponse;
                        try {
                            // Parsing json object response
                            // response will be a json object
                            JSONObject data = response.getJSONObject("data");
                            JSONObject total = data.getJSONObject("total");
                            String confirmed = total.getString("confirmed");
                            String active = total.getString("active");
                            String deaths = total.getString("deaths");
                            String recovered = total.getString("recovered");

                            tcases.setText(confirmed);
                            acases.setText(active);
                            dcases.setText(deaths);
                            rcases.setText(recovered);
                            load.setVisibility(View.GONE);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        MyApplication.getInstance().addToRequestQueue(request);
    }

}