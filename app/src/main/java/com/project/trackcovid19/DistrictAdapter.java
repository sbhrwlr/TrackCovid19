package com.project.trackcovid19;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DistrictAdapter extends RecyclerView.Adapter<DistrictAdapter.districtViewHolder> {
    private Context mCtx;
    private ArrayList<districtModel> dList;


    public DistrictAdapter(Context context, ArrayList<districtModel> list){
        mCtx = context;
        dList = list;
    }

    @NonNull
    @Override
    public districtViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(mCtx).inflate(R.layout.districtlayout, parent, false);
        return new districtViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull districtViewHolder holder, int position) {
        districtModel model = dList.get(position);
        String districtname = model.getDistrictname();
        String cCases = model.getcCases();


        holder.distname.setText(districtname);
        holder.confirmedcases.setText(cCases);
    }

    @Override
    public int getItemCount() {
        return dList.size();
    }


    public class districtViewHolder extends RecyclerView.ViewHolder{

        public TextView distname;
        public TextView confirmedcases;
        public districtViewHolder(@NonNull View itemView) {
            super(itemView);
            distname = itemView.findViewById(R.id.district_name);
            confirmedcases = itemView.findViewById(R.id.concases);
        }
    }
}
