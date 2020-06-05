package com.modularity.mvvm.viewModel;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.widget.Toast;

import com.modularity.mvvm.bean.WeatherData;
import com.modularity.perfectionRetrofit.PerfectionCallBack;
import com.modularity.perfectionRetrofit.PerfectionRetrofit;
import com.modularity.perfectionRetrofit.exception.PerfectionThrowable;

import java.util.HashMap;

public class QueryWeatherViewModel {

    private static final String TAG = "QueryWeatherViewModel";

    public final ObservableBoolean loading = new ObservableBoolean(false);

    public final ObservableBoolean loadingSuccess = new ObservableBoolean(false);

    public final ObservableBoolean loadingFailure = new ObservableBoolean(false);

    public final ObservableField<String> city = new ObservableField<>();

    public final ObservableField<String> cityId = new ObservableField<>();

    public final ObservableField<String> temp1 = new ObservableField<>();

    public final ObservableField<String> temp2 = new ObservableField<>();

    public final ObservableField<String> weather = new ObservableField<>();

    public final ObservableField<String> time = new ObservableField<>();

    private Context mContext;

    public QueryWeatherViewModel(Context context) {
        this.mContext = context;
    }

    public void queryWeather() {
        PerfectionRetrofit retrofit = new PerfectionRetrofit.Builder()
                .baseUrl("http://www.weather.com.cn/")
                .build();
        retrofit.requestGet("data/cityinfo/101210101.html", new HashMap<>(), new PerfectionCallBack<WeatherData>() {
            @Override
            public void onStart() {
                loading.set(true);
                loadingSuccess.set(false);
                loadingFailure.set(false);
            }

            @Override
            public void onComplete() {
                loading.set(false);
            }

            @Override
            public void onSuccess(WeatherData data) {
                WeatherData.WeatherinfoBean weatherInfo = data.getWeatherinfo();
                city.set(weatherInfo.getCity());
                cityId.set(weatherInfo.getCityid());
                temp1.set(weatherInfo.getTemp1());
                temp2.set(weatherInfo.getTemp2());
                weather.set(weatherInfo.getWeather());
                time.set(weatherInfo.getPtime());
                loadingSuccess.set(true);
            }

            @Override
            public void onError(PerfectionThrowable e) {
                Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                loadingSuccess.set(false);
                loadingFailure.set(true);
            }
        });

    }
}

