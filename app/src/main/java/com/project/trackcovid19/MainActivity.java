package com.project.trackcovid19;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.Button;
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
    private TextView tcases, acases, rcases, dcases, yourloc, dttotal, dtrecover, dtdecease;
    private RequestQueue mRequestQueue, nRequestQueue;
    private LottieAnimationView load, loadlist;
    private RecyclerView recyclerView;
    private stateAdapter mstateAdapter;
    private ArrayList<stateModel> stateList;
    TextView tvAddress;
    LocationManager locationManager;
    private Button vizbtn;
    Button btn1, btn2 , btn3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        dttotal = findViewById(R.id.deltatotal);
        dtrecover = findViewById(R.id.deltarecover);
        dtdecease = findViewById(R.id.deltadeceased);
        tcases = findViewById(R.id.total_cases);
        acases = findViewById(R.id.active_cases);
        rcases = findViewById(R.id.recovered_cases);
        dcases = findViewById(R.id.deceased_cases);
        load = findViewById(R.id.load);
        tvAddress = findViewById(R.id.location);

        btn1 = findViewById(R.id.confirm);
        btn2 = findViewById(R.id.recover);
        btn3 = findViewById(R.id.decease);



        loadlist = findViewById(R.id.loadlist);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        stateList = new ArrayList<>();


        mRequestQueue = Volley.newRequestQueue(this);

        stateJSON();

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GraphConfirm.class);
                startActivity(intent);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GraphRecovered.class);
                startActivity(intent);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GraphDeceased.class);
                startActivity(intent);
            }
        });

    }

    private void stateJSON() {

        loadlist.setVisibility(View.VISIBLE);
        String url = "https://api.covid19india.org/data.json";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("statewise");
                            JSONObject total = jsonArray.getJSONObject(0);
                            String deltatotal = "(+" + total.getString("deltaconfirmed") + ")";
                            String deltarecovered = "(+" + total.getString("deltarecovered") + ")";
                            String deltadeaths = "(+" + total.getString("deltadeaths") + ")";
                            String statename = total.getString("state");
                            String confirmed = total.getString("confirmed");
                            String recovered = total.getString("recovered");
                            String active = total.getString("active");
                            String deaths = total.getString("deaths");
                            tcases.setText(confirmed);
                            dttotal.setText(deltatotal);
                            dtrecover.setText(deltarecovered);
                            dtdecease.setText(deltadeaths);
                            acases.setText(active);
                            dcases.setText(deaths);
                            rcases.setText(recovered);
                            load.setVisibility(View.GONE);
                            for (int i = 1; i < jsonArray.length(); i++) {
                                JSONObject statewise = jsonArray.getJSONObject(i);
                                String sname = statewise.getString("state");
                                String deltaconfirm = "(+"+statewise.getString("deltaconfirmed")+")";
                                String deltarecover = "(+"+statewise.getString("deltarecovered")+")";
                                String deltadeceased = "(+"+statewise.getString("deltadeaths")+")";
                                if(deltaconfirm=="(+0)"){ deltaconfirm = null; }
                                if(deltarecover=="(+0)"){ deltarecover = null; }
                                if(deltadeceased=="(+0)"){ deltadeceased = null; }
                                String confirm=deltaconfirm+" "+ statewise.getString("confirmed");
                                String recover =deltarecover+" "+statewise.getString("recovered");
                                String activ = statewise.getString("active");
                                String death = deltadeceased+" "+statewise.getString("deaths");

                                    stateList.add(new stateModel(sname, confirm, activ, recover, death));
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
        mRequestQueue  = Volley.newRequestQueue(this);
        mRequestQueue.add(request);
    }

}