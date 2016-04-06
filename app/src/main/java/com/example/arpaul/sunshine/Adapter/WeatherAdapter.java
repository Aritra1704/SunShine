package com.example.arpaul.sunshine.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.arpaul.sunshine.DataObjects.WeatherDataDO;
import com.example.arpaul.sunshine.R;

import java.util.ArrayList;

/**
 * Created by ARPaul on 04-04-2016.
 */
public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {

    private Context context;
    private ArrayList<WeatherDataDO> arrWeatherDetails;

    public WeatherAdapter(Context context, ArrayList<WeatherDataDO> arrCallDetails) {
        this.context=context;
        this.arrWeatherDetails = arrCallDetails;
    }

    public void refresh(ArrayList<WeatherDataDO> arrCallDetails) {
        this.arrWeatherDetails = arrCallDetails;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final WeatherDataDO objWeatherDO = arrWeatherDetails.get(position);
        holder.tvWeatherCondition.setText(objWeatherDO.getTemperatureDay());

    }

    @Override
    public int getItemCount() {
        if(arrWeatherDetails != null)
            return arrWeatherDetails.size();

        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView ivWeather;
        public final TextView tvWeatherCondition;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            ivWeather = (ImageView) view.findViewById(R.id.ivWeather);
            tvWeatherCondition = (TextView) view.findViewById(R.id.tvWeatherCondition);
        }

        @Override
        public String toString() {
            return "";
        }
    }
}
