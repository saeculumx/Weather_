package Model;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import javax.xml.transform.Result;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okio.BufferedSource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.internal.EverythingIsNonNull;

public class WeatherRepository {
    private String url;
    private static WeatherRepository instance;
    private float lat = 54;
    private float lon = 8;
    private String exclude = "hourly,minutely";
    private String APP_ID = "70742a4ee46a7faef64cb6003f3b8f4f";
    private String units = "standard";
    private String lang = "en_uk";
    private final MutableLiveData<WeatherInfo> weatherInfoMutableLiveData;

    public WeatherRepository() {
        weatherInfoMutableLiveData = new MutableLiveData<>();
    }
    public static synchronized WeatherRepository getWeatherRSPInstance()
    {
        if (instance!=null)
        {
            instance = new WeatherRepository();
        }
        return instance;
    }
    public LiveData<WeatherInfo> getWeatherInfo()
    {
        return weatherInfoMutableLiveData;
    }
    public void setQuery(float lat, float lon, String exclude, String units, String lang)
    {
        this.exclude = exclude;
        this.lang = lang;
        this.lat = lat;
        this.lon = lon;
        this.units = units;
        //
        WeatherRepository instance;
        lat = 54;
        lon = 8;
        exclude = "hourly,minutely";
        APP_ID = "70742a4ee46a7faef64cb6003f3b8f4f";
        units = "standard";
        lang = "en_uk";
        //
        WeatherApi weatherApi = ServiceGenerator.getWeatherApi();
        Retrofit retrofit = ServiceGenerator.getRetrofit();
        System.out.println("<<<BK1>>>");
        Call<WeatherApiResponse> call = weatherApi.getWeatherData(lat,lon,exclude,APP_ID,units,lang);
        call.enqueue(new Callback<WeatherApiResponse>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<WeatherApiResponse> call, Response<WeatherApiResponse> response) {
                if (response.code() == 201||response.code() == 200)
                {
                    Log.i("/WER","HTTP OK, CODE:"+response.code()+" /TEST url: "+ call.request().url());
                    //try {
                    //    getFromOkHttp(call.request().url().toString());
                    //} catch (Exception e) {
                    //    e.printStackTrace();
                    //}
                    WeatherApiResponse weatherApiResponse = response.body();
                    weatherInfoMutableLiveData.setValue(weatherApiResponse.getWeatherInfo());
                }
                else
                {
                    Log.i("/WER","ERROR IN HTTP,CODE NOT 201/200, CODE: "+response.code()+" BODY: "+response.body());
                }
            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<WeatherApiResponse> call, Throwable t) {
                Log.i("/WER","ERROR IN HTTP, FAILURE");
            }
        });
    }
    public WeatherApiResponse getFromOkHttp(String url) throws Exception {
        WeatherApiResponse weatherApiResponse = new WeatherApiResponse();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        okhttp3.Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String jsonData = response.body().string();
        System.out.println("/WER: JSON CONTENT:<<>>"+jsonData+"/n"+"<<<>>>");
        JSONObject jsonObject = new JSONObject(jsonData);
        JSONObject d = jsonObject.getJSONObject("current");
        JSONArray jsonArraya = d.getJSONArray("weather");
        String weatherCon = jsonArraya.getJSONObject(0).getString("main");
        String weatherConDes = jsonArraya.getJSONObject(0).getString("description");
        double temp = d.getDouble("temp");
        int hum = d.getInt("humidity");
        double felt = d.getDouble("feels_like");
        int pressure = d.getInt("pressure");
        JSONArray jsonArrayb = d.getJSONArray("daily");
        double max = jsonArrayb.getJSONObject(0).getDouble("max");
        double min = jsonArrayb.getJSONObject(0).getDouble("min");
        System.out.println("/WER: OKHTTP CKECK DATA: "+" / "+temp+hum+" / "+felt+max+" / "+min+" / "+weatherCon+" / "+weatherConDes+" / "+pressure);
        WeatherInfo weatherInfo = new WeatherInfo(temp,hum,felt,max,min,weatherCon,weatherConDes,pressure);
        weatherApiResponse.setWeatherInfo(weatherInfo);
        return weatherApiResponse;
    }
}
