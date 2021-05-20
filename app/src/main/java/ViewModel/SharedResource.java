package ViewModel;

import java.util.ArrayList;

import Model.ForeCastWeather;

public class SharedResource {
    ArrayList<ForeCastWeather> foreCastWeatherArrayList;
    public SharedResource()
    {
        foreCastWeatherArrayList = new ArrayList<>();
    }
    public ArrayList<ForeCastWeather> getForeCastWeatherArrayList() {
        return foreCastWeatherArrayList;
    }

    public void setForeCastWeatherArrayList(ArrayList<ForeCastWeather> foreCastWeatherArrayList) {
        this.foreCastWeatherArrayList = foreCastWeatherArrayList;
    }

}
