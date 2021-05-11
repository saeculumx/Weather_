package Model;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class ServiceGenerator {
    private static WeatherApi weatherApi;

    public static WeatherApi getWeatherApi() {
        if (weatherApi == null) {
            weatherApi = new Retrofit.Builder().
                    baseUrl("https://api.openweathermap.org/data/2.5/").
                    addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(WeatherApi.class);

        }
        return weatherApi;
    }
    public static Retrofit getRetrofit()
    {
        return new Retrofit.Builder().
                baseUrl("https://api.openweathermap.org/data/2.5/").
                addConverterFactory(GsonConverterFactory.create()).build();
    }
}
