package com.arpaul.sunshine.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.arpaul.sunshine.R;
import com.arpaul.sunshine.activity.WeatherDetailActivity;
import com.arpaul.sunshine.common.AppConstants;
import com.arpaul.sunshine.dataObjects.WeatherDataDO;
import com.arpaul.sunshine.dataObjects.WeatherDescriptionDO;
import com.arpaul.sunshine.dataObjects.WeatherTodayDO;
import com.arpaul.utilitieslib.CalendarUtils;

import java.util.ArrayList;

/**
 * Created by ARPaul on 04-04-2016.
 */
public class WeatherTodayAdapter extends RecyclerView.Adapter<WeatherTodayAdapter.ViewHolder> {

    private Context context;
    private ArrayList<WeatherTodayDO> arrWeatherDetails;

    public WeatherTodayAdapter(Context context, ArrayList<WeatherTodayDO> arrCallDetails) {
        this.context=context;
        this.arrWeatherDetails = arrCallDetails;
    }

    public void refresh(ArrayList<WeatherTodayDO> arrCallDetails) {
        this.arrWeatherDetails = arrCallDetails;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_day_timings, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final WeatherTodayDO objWeatherDO = arrWeatherDetails.get(position);


        holder.tvDayTime.setText(objWeatherDO.dayTime);
        holder.tvDayTemp.setText(objWeatherDO.temperature + "");
    }

    @Override
    public int getItemCount() {
        if(arrWeatherDetails != null)
            return arrWeatherDetails.size();

        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView tvDayTemp;
        public final TextView tvDayTime;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tvDayTemp = (TextView) view.findViewById(R.id.tvDayTemp);
            tvDayTime = (TextView) view.findViewById(R.id.tvDayTime);
        }

        @Override
        public String toString() {
            return "";
        }
    }
}
