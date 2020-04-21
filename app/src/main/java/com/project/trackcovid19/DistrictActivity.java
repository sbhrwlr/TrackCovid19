package com.project.trackcovid19;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DistrictActivity extends AppCompatActivity {

    private TextView statename, concases, actcases, reccases, deaths;
    private RecyclerView recycler_view;
    private ArrayList<districtModel> districtList;
    private DistrictAdapter mDistrictAdapter;
    private RequestQueue nRequestQueue;
    private LottieAnimationView loadlist;
    private String jsonString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_district);

        statename = findViewById(R.id.sname);
        concases = findViewById(R.id.total_Cases);
        actcases = findViewById(R.id.active_cases);
        reccases = findViewById(R.id.recovered_cases);
        deaths = findViewById(R.id.deceased_cases);
        loadlist = findViewById(R.id.loadlist);
        recycler_view = findViewById(R.id.rv);
        recycler_view.setHasFixedSize(true);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));

        districtList = new ArrayList<>();

        Intent getinfo = getIntent();
        String state = getinfo.getExtras().getString("state");
        String confirmed = getinfo.getExtras().getString("confirmed");
        String active = getinfo.getExtras().getString("active");
        String recovered = getinfo.getExtras().getString("recovered");
        String deceased = getinfo.getExtras().getString("deaths");

        statename.setText(state);
        concases.setText(confirmed);
        actcases.setText(active);
        reccases.setText(recovered);
        deaths.setText(deceased);

        nRequestQueue = Volley.newRequestQueue(this);
        districtJSON();
    }

    private void districtJSON() {
        final String sname = statename.getText().toString();
        Toast.makeText(DistrictActivity.this, sname, Toast.LENGTH_SHORT).show();
        loadlist.setVisibility(View.VISIBLE);
        String url = "https://api.covid19india.org/v2/state_district_wise.json";


        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try{
                            for(int i=0;i<response.length();i++){
                                // Get current json object
                                JSONObject statewise = response.getJSONObject(i);
                                String state = statewise.getString("state");
                                JSONArray districtData = statewise.getJSONArray("districtData");

                                if (state.equals(sname)){
                                    for (int j=0;j<districtData.length();j++){
                                        JSONObject districtwise = districtData.getJSONObject(j);
                                        String district = districtwise.getString("district");
                                        String confirmed = districtwise.getString("confirmed");
                                        String active = districtwise.getString("active");
                                        String recovered = districtwise.getString("recovered");
                                        String deceased = districtwise.getString("deceased");

                                        districtList.add(new districtModel(district, confirmed, active, recovered, deceased));
                                    }
                                }
                                        loadlist.setVisibility(View.GONE);

                                }
                            mDistrictAdapter = new DistrictAdapter(DistrictActivity.this, districtList);
                            recycler_view.setAdapter(mDistrictAdapter);
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DistrictActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

        nRequestQueue.add(request);
    }
}