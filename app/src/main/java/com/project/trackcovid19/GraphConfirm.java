package com.project.trackcovid19;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.MarkerType;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GraphConfirm extends AppCompatActivity {
    private AnyChartView chartView1, chartView2, chartView3;
    RequestQueue nRequestQueue;
    ArrayList<DataEntry> entries = new ArrayList<>();
    ArrayList<DataEntry> entries2 = new ArrayList<>();
    ArrayList<DataEntry> entries3 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_confirm);

        addData();
    }

    private void addData(){
        String url = "https://api.covid19india.org/data.json";
        nRequestQueue  = Volley.newRequestQueue(this);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("cases_time_series");
                            int len = jsonArray.length();
                            System.out.println(len);
                            for (int i = len-31; i < len; i++) {
                                JSONObject casetime = jsonArray.getJSONObject(i);
                                String date = casetime.getString("date");
                                String dcon = casetime.getString("dailyconfirmed");
                                int dc = Integer.parseInt(dcon);
                                entries.add(new DataStructure(date, dc));
                            }
                            Visualize(entries);
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

    public void Visualize(ArrayList<DataEntry> c){
        chartView3 = findViewById(R.id.chartdec);
        chartView3.setProgressBar(findViewById(R.id.progress_bar3));
        Cartesian cartesian = AnyChart.line();
        cartesian.animation(true);
        cartesian.padding(10d, 20d, 5d, 20d);
        cartesian.crosshair().enabled(true);
        cartesian.crosshair()
                .yLabel(true)
                .yStroke((Stroke) null, null, null, (String) null, (String) null);
        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.title("Deceased Per Day from last one month");
        cartesian.yAxis(0).title("Number of Food Sold");
        cartesian.xAxis(0).labels().padding(5d, 5d, 5d, 5d);
        Toast.makeText(this, "Viz three", Toast.LENGTH_SHORT).show();
        Set set = Set.instantiate();
        set.data(c);
        Mapping secondgraph = set.mapAs("{ date: 'date', dailydeceased: 'dailydeceased' }");

        Line second = cartesian.line(secondgraph);
        second.name("Deceased");
        second.hovered().markers().enabled(true);
        second.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        second.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);
        cartesian.legend().enabled(true);
        cartesian.legend().fontSize(13d);
        cartesian.legend().padding(0d, 0d, 10d, 0d);
        chartView3.setChart(cartesian);

        c.clear();
    }
    public void Visualize2(ArrayList<DataEntry> c){
        chartView3 = findViewById(R.id.chartdec);
        chartView3.setProgressBar(findViewById(R.id.progress_bar3));
        Cartesian cartesian = AnyChart.line();
        cartesian.animation(true);
        cartesian.padding(10d, 20d, 5d, 20d);
        cartesian.crosshair().enabled(true);
        cartesian.crosshair()
                .yLabel(true)
                .yStroke((Stroke) null, null, null, (String) null, (String) null);
        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.title("Deceased Per Day from last one month");
        cartesian.yAxis(0).title("Number of Food Sold");
        cartesian.xAxis(0).labels().padding(5d, 5d, 5d, 5d);
        Toast.makeText(this, "Viz three", Toast.LENGTH_SHORT).show();
        Set set = Set.instantiate();
        set.data(c);
        Mapping secondgraph = set.mapAs("{ date: 'date', dailydeceased: 'dailydeceased' }");

        Line second = cartesian.line(secondgraph);
        second.name("Deceased");
        second.hovered().markers().enabled(true);
        second.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        second.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);
        cartesian.legend().enabled(true);
        cartesian.legend().fontSize(13d);
        cartesian.legend().padding(0d, 0d, 10d, 0d);
        chartView3.setChart(cartesian);

        c.clear();
    }

    public static class DataStructure extends ValueDataEntry{

        DataStructure(String date, int dailyconfirm) {
            super(date,dailyconfirm);
            setValue("dailyconfirm", dailyconfirm);
        }
    }
    private class DataStructure2 extends ValueDataEntry{

        DataStructure2(String date, int dailyrecover) {
            super(date,dailyrecover);
            setValue("dailyrecover", dailyrecover);
        }
    }
    private class DataStructure3 extends ValueDataEntry{

        DataStructure3(String date, int dailydeceased) {
            super(date,dailydeceased);
            setValue("dailydeceased", dailydeceased);

        }
    }
}