package com.arpaul.sunshine.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arpaul.sunshine.adapter.WeatherAdapter;
import com.arpaul.sunshine.dataObjects.WeatherDataDO;
import com.arpaul.sunshine.R;
import com.arpaul.sunshine.webServices.WeatherLoader;

import java.util.ArrayList;

/**
 * Created by ARPaul on 04-04-2016.
 */
public class ForecastFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<WeatherDataDO>>{
    private RecyclerView rvWeather;
    private WeatherAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        initializeControls(rootView);

        getActivity().getSupportLoaderManager().initLoader(1, null, this).forceLoad();
        return rootView;
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return new WeatherLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<WeatherDataDO>> loader, ArrayList<WeatherDataDO> data) {
        adapter.refresh(data);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<WeatherDataDO>> loader) {
        //adapter.refresh(new ArrayList<WeatherDataDO>());
    }

    private void initializeControls(View rootView){
        rvWeather = (RecyclerView) rootView.findViewById(R.id.rvWeather);

        adapter = new WeatherAdapter(getActivity(),new ArrayList<WeatherDataDO>());
        rvWeather.setAdapter(adapter);
    }
}
