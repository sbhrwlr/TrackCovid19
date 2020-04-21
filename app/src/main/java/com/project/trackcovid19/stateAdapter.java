package com.project.trackcovid19;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class stateAdapter extends RecyclerView.Adapter<stateAdapter.stateViewHolder> {

    private Context mCtx;
    private ArrayList<stateModel> stateList;


    public stateAdapter(Context context, ArrayList<stateModel> list){
        mCtx = context;
        stateList = list;
    }

    @NonNull
    @Override
    public stateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(mCtx).inflate(R.layout.record_layout, parent, false);
        return new stateViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull stateViewHolder holder, int position) {
        stateModel item = stateList.get(position);

        String statename = item.getStatename();
        String concases = item.getConcases();
        String reccases = item.getReccases();
        String actcases = item.getActcases();
        String deaths = item.getDeaths();

        holder.statename.setText(statename);
        holder.confirmedcases.setText(concases);
        holder.activecases.setText(actcases );
        holder.recoveredcases.setText(reccases);
        holder.deaths.setText(deaths);

    }

    @Override
    public int getItemCount() {
        return stateList.size();
    }

    public class stateViewHolder extends RecyclerView.ViewHolder{

        public TextView statename;
        public TextView confirmedcases;
        public TextView activecases;
        public TextView recoveredcases;
        public TextView deaths;

        public stateViewHolder(@NonNull View itemView) {
            super(itemView);
            statename = itemView.findViewById(R.id.state_name);
            confirmedcases = itemView.findViewById(R.id.confirmedcases);
            activecases = itemView.findViewById(R.id.activecases);
            recoveredcases = itemView.findViewById(R.id.recoveredcases);
            deaths = itemView.findViewById(R.id.deceasedcases);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String sname = statename.getText().toString();
                    String concases = confirmedcases.getText().toString();
                    String actcases = activecases.getText().toString();
                    String reccases = recoveredcases.getText().toString();
                    String deathcases = deaths.getText().toString();
                    Intent stateinfo = new Intent(mCtx, DistrictActivity.class);
                    stateinfo.putExtra("state", sname);
                    stateinfo.putExtra("confirmed", concases);
                    stateinfo.putExtra("active", actcases);
                    stateinfo.putExtra("recovered", reccases);
                    stateinfo.putExtra("deaths", deathcases);
                    mCtx.startActivity(stateinfo);

                }
            });
        }
    }

}
