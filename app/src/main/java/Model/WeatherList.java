package Model;

import java.util.ArrayList;

public class WeatherList {
    private ArrayList<WeatherInfo> weatherInfoArrayList;

    public void addWeather(WeatherInfo weatherInfo) {
        weatherInfoArrayList.add(weatherInfo);
    }

    public ArrayList<WeatherInfo> getWeatherList() {
        return weatherInfoArrayList;
    }

  //  public WeatherInfo getWeatherByID(String id) {
  //      for (int i = 0; i < weatherInfoArrayList.size(); i++) {
  //          if (weatherInfoArrayList.get(i).getId().equals(id))
  //          {
  //              return weatherInfoArrayList.get(i);
  //          }
  //          else
  //          {
  //              System.out.println("/WEL:Checking:... Loop: "+i);
  //          }
  //      }
  //      System.out.println("/WEL: No Match Found");
  //      return null;
  //  }
}
