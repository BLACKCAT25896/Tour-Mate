package com.example.tourmate;


import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.tourmate.adapter.WeatherForecastAdapter;
import com.example.tourmate.common.Common;
import com.example.tourmate.databinding.FragmentForecastBinding;
import com.example.tourmate.model.WeatherForecastResult;
import com.example.tourmate.retrofit.IOpenWeatherMap;
import com.example.tourmate.retrofit.RetrofitClient;

import java.util.function.Consumer;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;



public class ForecastFragment extends Fragment {
    private FragmentForecastBinding binding;
    CompositeDisposable compositeDisposable;
    IOpenWeatherMap weatherMap;
    static ForecastFragment instanse;

    public static ForecastFragment getInstanse() {

        if(instanse == null)
            instanse = new ForecastFragment();

        return instanse;
    }

    public ForecastFragment() {
        compositeDisposable = new CompositeDisposable();
        Retrofit retrofit = RetrofitClient.getInstance();
        weatherMap = retrofit.create(IOpenWeatherMap.class);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_forecast, container, false);

        binding.forecastRecyclerView.setHasFixedSize(true);
        binding.forecastRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        getForecastWeatherInfo();




        View view= binding.getRoot();

        return view;
    }

    private void getForecastWeatherInfo() {


    }

    @Override
    public void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    @Override
    public void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }



    private void displayForecastWeather(WeatherForecastResult weatherForecastResult) {

        binding.txtCityName.setText(new StringBuilder(weatherForecastResult.city.name));
        binding.txtGeoCoord.setText(new StringBuilder(weatherForecastResult.city.coord.toString()));

        WeatherForecastAdapter adapter = new WeatherForecastAdapter(getContext(),weatherForecastResult);
        binding.forecastRecyclerView.setAdapter(adapter);
    }

}
