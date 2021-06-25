package com.example.myapplicationtwo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplicationtwo.R;
import com.example.myapplicationtwo.model.UnitModel;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Locale;

public class UnitListAdapter extends RecyclerView.Adapter<UnitListAdapter.UserViewHolder> {

    private Context context;
    private ArrayList<UnitModel.Unit> unitList = null;
    private ArrayList<UnitModel.Unit> listfriOrigin;

    public UnitListAdapter(WeakReference<Context> context, ArrayList<UnitModel.Unit> unitList) {
        this.context = context.get();
        this.unitList = unitList;
        this.listfriOrigin = new ArrayList<UnitModel.Unit>();
        this.listfriOrigin.addAll(unitList);
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_unitlist, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        holder.tvUnitName.setText(unitList.get(position).getName());
        holder.tvStepName.setText(unitList.get(position).getActivities().get(position).getStepName());
        holder.tvActivityName.setText(unitList.get(position).getActivities().get(position).getName());
        holder.tvPercentage.setText(unitList.get(position).getActivities().get(position).getCompletionStatus()+"%");
        holder.tvDays.setText(unitList.get(position).getActivities().get(position).getTotalDays());
    }

    @Override
    public int getItemCount() {
        return unitList.size();
    }

    // Filter Class
    public void filters(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        unitList.clear();
        if (charText.length() == 0) {
            unitList.addAll(listfriOrigin);
        } else {
            for (UnitModel.Unit wp : listfriOrigin) {
                if (wp.getName().toLowerCase(Locale.getDefault()).contains(charText) ) {
                    unitList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

    class UserViewHolder extends RecyclerView.ViewHolder {

        AppCompatTextView tvUnitName, tvStepName, tvActivityName, tvDays, tvPercentage;

        UserViewHolder(View itemView) {
            super(itemView);
            tvUnitName = (AppCompatTextView) itemView.findViewById(R.id.tvUnitName);
            tvStepName = (AppCompatTextView) itemView.findViewById(R.id.tvStepName);
            tvActivityName = (AppCompatTextView) itemView.findViewById(R.id.tvActivityName);
            tvDays = (AppCompatTextView) itemView.findViewById(R.id.tvDays);
            tvPercentage = (AppCompatTextView) itemView.findViewById(R.id.tvPercentage);
        }
    }
}