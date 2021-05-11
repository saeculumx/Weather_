package ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import Model.WeatherApiResponse;
import Model.WeatherInfo;
import Model.WeatherRepository;

public class WeatherinfoViewModel extends ViewModel {
    WeatherRepository weatherRepository;
    public WeatherinfoViewModel()
    {
        weatherRepository = WeatherRepository.getWeatherRSPInstance();
    }
    LiveData<WeatherInfo> getWeatherInfo()
    {
        return weatherRepository.getWeatherInfo();
    }
    public void setWeatherRepository() throws Exception {
        System.out.println("<<<BK3>>>");
        weatherRepository = new WeatherRepository();
        System.out.println("<<<BK2>>>");
        weatherRepository.setQuery(1,1,"hourly,minutely","standard","en_uk");
    }
}

