package Model;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class WeatherHttpHandler extends Service {
    int x = 0;
    Intent intent = new Intent();
    ArrayList<ForeCastWeather> forecastArrayList = new ArrayList<>();
    private MutableLiveData<Double> tempLiveData = new MutableLiveData<>();
    WeatherInfo weatherInfo = new WeatherInfo();
    WeatherAlarms weatherAlarm = new WeatherAlarms();
    public static String uul;
    static double lat;
    static double lon;
    static String exclude ="hourly,minutely";
    static String units;
    static String lang;
    boolean alarmExist = false;
    static String url = "https://api.openweathermap.org/data/2.5/onecall?lat="+lat+"&lon="+lon+"&exclude="+exclude+"&appid=520113b009452cbe00a39f3ba6582083&units="+units+"&lang="+lang+"";
    public WeatherHttpHandler(){}
    public static String str_receiverH = "Acts.TestingAct";
    //Intent intent = new Intent(str_receiverH);
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate() {
        System.out.println("WHH: ONCreate");
        super.onCreate();
        this.intent = new Intent();
        registerReceiver(locationBroadcastReceiver,new IntentFilter(LocationAcquire.str_receiver));
        getWeatherInfoFromJson();
        intent.putExtra("tempForTest",getWeatherInfoFromJson().getTemp());
        //intent = new Intent(str_receiverH);
    }
    //private class timerTask extends TimerTask
    //{
    //    @Override
    //    public void run() {
    //        getWeatherInfoFromJson();
    //    }
    //}
    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(locationBroadcastReceiver);
    }
    OkHttpClient client = new OkHttpClient();
    public static String str_receiver = "Acts.TestingAct";
    public WeatherHttpHandler(double lat, double lon, String lang, String units){
        this.lang = lang;
        this.lat = lat;
        this.lon = lon;
        this.units = units;
        setLang(lang);
        setLat(lat);
        setLon(lon);
        setUnits(units);
    }
    public MutableLiveData<Double> getCurrentTemp()
    {
        if (tempLiveData == null)
        {
            tempLiveData = new MutableLiveData<>();
        }
        return tempLiveData;
    }
  //private void updateValue(WeatherInfo weatherInfo)
  //{
  //    intent.putExtra("temp",weatherInfo.getTemp()+"");
  //    intent.putExtra("felt",weatherInfo.getFelt()+"");
  //    intent.putExtra("max",weatherInfo.getHigh()+"");
  //    intent.putExtra("min",weatherInfo.getLow()+"");
  //    intent.putExtra("hum",weatherInfo.getHum()+"");
  //    intent.putExtra("pre",weatherInfo.getPressure()+"");
  //    intent.putExtra("wcon",weatherInfo.getWeatherCon()+"");
  //    intent.putExtra("wcondes",weatherInfo.getWeatherConDes()+"");
  //    if (alarmExist) {
  //        intent.putExtra("alatitle", weatherAlarm.getAlarmTitle() + "");
  //        intent.putExtra("alabody",weatherAlarm.getAlarmBody()+"");
  //        intent.putExtra("sou",weatherAlarm.getSourser()+"");
  //    }
  //    System.out.println("WHH: BrodCast Sended "+weatherInfo.getTemp());
  //    sendBroadcast(intent);
  //}
    public WeatherInfo getWeatherInfoFromJson()
    {
        //System.out.println(uul+">>>");
        if (uul!=null) {
            Request request = new Request.Builder().url(uul).build();
            Response response = null;
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    System.out.println("WHH: Call failure, code: " + e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String resurl = request.url().toString();
                    //System.out.println(resurl);
                    String jsonData = response.body().string();
                    //System.out.println("WHH: Call Response Json: "+jsonData);
                    //System.out.println("/WER: JSON CONTENT:<<>>" + jsonData + "/n" + "<<<>>>");
                    try {
                        Decode(jsonData);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            return weatherInfo;
        }
        Request request = new Request.Builder().url(url).build();
        Response response = null;
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("WHH: Call failure, code: " + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String resurl = request.url().toString();
                //System.out.println(resurl);
                String jsonData = response.body().string();
                //System.out.println("WHH: Call Response Json: "+jsonData);
                //System.out.println("/WER: JSON CONTENT:<<>>" + jsonData + "/n" + "<<<>>>");
                try {
                    Decode(jsonData);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        return weatherInfo;
        }

    public void Decode(String content) throws JSONException {
        //current
        if (lat != 0 && lon != 0) {
            x++;
            if (x >= 2) {
                JSONObject jsonObject = new JSONObject(content);
                JSONObject current = jsonObject.getJSONObject("current");
                JSONArray jsonArraya = current.getJSONArray("weather");
                String weatherCon = jsonArraya.getJSONObject(0).getString("main");
                String weatherConDes = jsonArraya.getJSONObject(0).getString("description");
                double temp = current.getDouble("temp");
                int hum = current.getInt("humidity");
                double felt = current.getDouble("feels_like");
                int pressure = current.getInt("pressure");
                weatherInfo.setFelt(felt);
                weatherInfo.setHum(hum);
                weatherInfo.setPressure(pressure);
                weatherInfo.setTemp(temp);
                weatherInfo.setWeatherCon(weatherCon);
                weatherInfo.setWeatherConDes(weatherConDes);
                //daily
                JSONArray daily = jsonObject.getJSONArray("daily");
                JSONObject dailyInfo = daily.getJSONObject(0);
                JSONObject dailyTemp = dailyInfo.getJSONObject("temp");
                //System.out.println("WER: JSON SUB "+dailyTemp);
                double max = dailyTemp.getDouble("max");// daily.getJSONObject(0).getDouble("max");
                double min = dailyTemp.getDouble("min");//daily.getJSONObject(0).getDouble("min");
                weatherInfo.setHigh(max);
                weatherInfo.setLow(min);
                //Alarm
                if (jsonObject.has("alerts")) {
                    alarmExist = true;
                    JSONArray alarm = jsonObject.getJSONArray("alerts");
                    System.out.println("WER: JSON SUB " + alarm);
                    String sourser = alarm.getJSONObject(0).getString("sender_name");
                    String alarmTitle = alarm.getJSONObject(0).getString("event");
                    String des = alarm.getJSONObject(0).getString("description");
                    weatherAlarm = new WeatherAlarms(alarmTitle, des, sourser);
                }
                System.out.println("WHH:/ ALARM SET " + jsonObject.has("alerts"));
                //System.out.println("WER: CONT: "+alarmTitle+" "+des+" "+sourser);
                System.out.println("WHH: OKHTTP CKECK DATA: " + " / " + temp + hum + " / " + felt + max + " / " + min + " / " + weatherCon + " / " + weatherConDes + " / " + pressure);
                int mjv = daily.length();
                ArrayList<Integer> dtArray = new ArrayList<>();

                for (int i = 1; i < mjv; i++) {
                    JSONObject dailyWeather = daily.getJSONObject(i);
                    JSONObject dailyWeatherTemp = dailyWeather.getJSONObject("temp");
                    double forecastMax = dailyWeatherTemp.getDouble("max");
                    double forecastMin = dailyWeatherTemp.getDouble("min");
                    int dt = dailyWeather.getInt("dt");
                    ForeCastWeather foreCastWeather = new ForeCastWeather(forecastMax, forecastMin, dt);
                    if (forecastArrayList.size() != 0 && forecastArrayList.size() < 8) {
                        for (int o = 0; o < forecastArrayList.size(); o++) {
                            if (foreCastWeather.getDt() == forecastArrayList.get(o).getDt()) {
                                forecastArrayList.get(o).setHigh(foreCastWeather.getHigh());
                                forecastArrayList.get(o).setLow(foreCastWeather.getLow());
                                System.out.println("FAL: refresh inside + / position : " + o);
                            } else {
                                forecastArrayList.add(foreCastWeather);
                                System.out.println("FAL: add in array , position : " + forecastArrayList.size());
                            }
                        }
                    }
                    if (forecastArrayList.size() == 0) {
                        forecastArrayList.add(foreCastWeather);
                        System.out.println("FAL: init add in array");
                    } else {
                        for (int o = 0; o < forecastArrayList.size(); o++) {
                            if (foreCastWeather.getDt() == forecastArrayList.get(o).getDt()) {
                                forecastArrayList.get(o).setHigh(foreCastWeather.getHigh());
                                forecastArrayList.get(o).setLow(foreCastWeather.getLow());
                                System.out.println("FAL: refresh + / position ： " + o);
                            }
                        }
                    }
                    if (forecastArrayList.size() == 8) {
                        for (int o = 0; o < forecastArrayList.size(); o++) {
                            if (foreCastWeather.getDt() == forecastArrayList.get(o).getDt()) {
                                forecastArrayList.get(o).setHigh(foreCastWeather.getHigh());
                                forecastArrayList.get(o).setLow(foreCastWeather.getLow());
                                System.out.println("FAL: refresh inside + / position : " + o);
                            }
                        }
                    }
                }
            }
        }
    }
    public ArrayList<ForeCastWeather> getForecastArrayList()
    {
        return forecastArrayList;
    }
   // private void updateBroadcastWeather(WeatherInfo weatherInfo)
   // {
   //     intent.putExtra("weatherInfo", (Parcelable) weatherInfo);
   // }
    private static final Parcelable.Creator<WeatherInfo> CREATOR = new Parcelable.Creator<WeatherInfo>() {
        @Override
        public WeatherInfo createFromParcel(Parcel source) {
            return new WeatherInfo();

        }

        @Override
        public WeatherInfo[] newArray(int size) {
            return new WeatherInfo[size];
        }
    };
    private final BroadcastReceiver locationBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            lon = Double.parseDouble(intent.getStringExtra("lon"));
            lat = Double.parseDouble(intent.getStringExtra("lat"));
            //cityInfo = intent.getStringExtra("city");
            //address = intent.getStringExtra("con");
            lang = intent.getStringExtra("lang");
            System.out.println("WHH: Receive broadcast");
            setLon(lon);
            setLang(lang);
            setLat(lat);
            uul = ("https://api.openweathermap.org/data/2.5/onecall?lat="+lat+"&lon="+lon+"&exclude="+exclude+"&appid=520113b009452cbe00a39f3ba6582083&units="+units+"&lang="+lang+"");
            //System.out.println(uul+"<<<");
            //System.out.println("WHH: Set: Lat = " + lat);
        }
    };
    public void setLon(double lon) {
        this.lon = lon;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setExclude(String exclude) {
        this.exclude = exclude;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
