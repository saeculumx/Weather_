package View;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.location.Geocoder;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.weather_.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import Model.ForeCastAdapter;
import Model.ForeCastWeather;
import Model.LocationAcquire;
import Model.WeatherAlarms;
import Model.WeatherHttpHandler;
import Model.WeatherInfo;


public class HomePageFragment extends Fragment implements ForeCastAdapter.OnListItemClickListener {
    int l = 0;
    ForeCastAdapter foreCastAdapter;
    ArrayList<ForeCastWeather> foreCastWeathers = new ArrayList<>();
    private static Dialog dialog;
    double temp;
    int hum;
    double felt;
    double high;
    double low;
    String weatherCon;
    String weatherConDes;
    int pressure;
    double lat;
    String lang;
    double lon;
    String address;
    String cityInfo;
    String unit;
    double rawt;
    String tail = "°C";
    TextView textView;
    TextView leftView;
    TextView middleView;
    TextView rightView;
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    NestedScrollView nestedScrollView;
    //VideoView videoView;
    int Resid = R.raw.yu;
    Uri uri;
    MutableLiveData<Integer> videoFeed = new MutableLiveData<>();
    //
    private WeatherHttpHandler weatherHttpHandler = new WeatherHttpHandler();
    private View view;
    private WeatherInfo weatherInfo = new WeatherInfo();
    private WeatherAlarms weatherAlarms = new WeatherAlarms();

    public HomePageFragment() {
        // Required empty public constructor
    }

    public static HomePageFragment getInstance() {
        return new HomePageFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home_page, container, false);
        nestedScrollView = view.findViewById(R.id.NestedScroll);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        textView = view.findViewById(R.id.cardTextView);
        leftView = view.findViewById(R.id.LeftTextView);
        middleView = view.findViewById(R.id.MiddleTextView);
        rightView = view.findViewById(R.id.RightTextView);
        recyclerView = view.findViewById(R.id.weatherRecycleView);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        Intent geoIntent = new Intent(getActivity().getApplicationContext(), LocationAcquire.class);
        Intent whhIntent = new Intent(getActivity().getApplicationContext(), WeatherHttpHandler.class);
        getActivity().startService(geoIntent);
        getActivity().startService(whhIntent);
        System.out.println("STA: Start Done");
        foreCastAdapter = new ForeCastAdapter(foreCastWeathers, HomePageFragment.this);
        recyclerView.setAdapter(foreCastAdapter);
        getActivity().registerReceiver(locationBroadcastReceiver, new IntentFilter(LocationAcquire.str_receiver));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                foreCastAdapter = new ForeCastAdapter(foreCastWeathers, HomePageFragment.this);
                recyclerView.setAdapter(foreCastAdapter);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        showProgress(getActivity());
        return view;
    }
    public static void showProgress(Context context)
    {
        if (dialog==null) initDia(context);
        dialog.show();
    }
    public static void dismissProgress()
    {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }
    public static void initDia(Context context)
    {
        dialog = new Dialog(context);
        Window window = dialog.getWindow();
        if (window!=null) window.setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(R.layout.progress_bar);
        dialog.setCancelable(false);
    }
    private final BroadcastReceiver locationBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            lon = Double.parseDouble(intent.getStringExtra("lon"));
            lat = Double.parseDouble(intent.getStringExtra("lat"));
            cityInfo = intent.getStringExtra("city");
            address = intent.getStringExtra("con");
            lang = intent.getStringExtra("lang");
            //tv_latitude.setText(String.valueOf(lat));
            //tv_longitude.setText(String.valueOf(lon));
            //tv_locality.setText(cityInfo);
            //tv_area.setText(address);
            System.out.println("/JRT: LON: "+lon+" LAT: "+lat+" CityInfo "+cityInfo);
            //weatherHttpHandler.setLon(lon);
            //weatherHttpHandler.setLat(lat);
            //weatherHttpHandler.setLang(lang);
            //weatherHttpHandler.setLang("zh_cn");
            weatherInfo = weatherHttpHandler.getWeatherInfoFromJson();
            double datat1 = weatherInfo.getTemp();
            double datat2 = weatherInfo.getFelt();
            pressure = weatherInfo.getPressure();
            middleView.setText(pressure+"mpa");
            weatherCon = weatherInfo.getWeatherCon();
            if (weatherCon!=null) {
                if (weatherCon.equals("Clouds") || weatherCon.equals("Rain") || weatherCon.equals("Snow") || weatherCon.equals("Thunderstorm") || weatherCon.equals("Drizzle") || weatherCon.equals("Clear")) {
                    if (lon == 0 && lat == 0)
                    {
                        System.out.println("Not Ready");
                    }
                    else {
                        System.out.println("Ready: "+lon+"  /  "+lat);
                        if (l>2) {
                            dialog.dismiss();
                        }
                        else l++;
                    }
                }
            }
            leftView.setText(weatherCon);
            Double temp = BigDecimal.valueOf(covert(datat1)).setScale(2, RoundingMode.HALF_UP).doubleValue();
            textView.setText(temp +tail);
            Double felt = BigDecimal.valueOf(covert(datat2)).setScale(2, RoundingMode.HALF_UP).doubleValue();
            rightView.setText(felt +tail);
            foreCastWeathers = weatherHttpHandler.getForecastArrayList();
            System.out.println("! FoR size: "+foreCastWeathers.size());

            foreCastAdapter = new ForeCastAdapter(foreCastWeathers, HomePageFragment.this);
            recyclerView.setAdapter(foreCastAdapter);

            if (weatherCon!=null) {
                switch (weatherCon) {
                    case "Clouds":
                        if (Resid != R.raw.yun) {
                            Resid = R.raw.yun;
                            videoFeed.setValue(Resid);
                            System.out.println("! set Cloud");
                        }
                        break;
                    case "Rain":
                        if (Resid != R.raw.yu) {
                            Resid = R.raw.yu;
                            videoFeed.setValue(Resid);
                            System.out.println("! set Rain");
                        }
                        break;
                    case "Snow":
                        if (Resid!=R.raw.xuesen)
                        {
                            Resid = R.raw.xuesen;
                            videoFeed.setValue(Resid);
                            System.out.println("! set snow");
                        }
                        break;
                    case "Thunderstorm":
                        if (Resid!=R.raw.baoyu)
                        {
                            Resid = R.raw.baoyu;
                            videoFeed.setValue(Resid);
                            System.out.println("! set thunder");
                        }
                        break;
                    case "Drizzle":
                        if (Resid!=R.raw.dayu)
                        {
                            Resid = R.raw.dayu;
                            videoFeed.setValue(Resid);
                            System.out.println("! set smallRain");
                        }
                        break;
                    case "Clear":
                        if (Resid!=R.raw.clear)
                        {
                            Resid = R.raw.clear;
                            videoFeed.setValue(Resid);
                            System.out.println("! set clear");
                        }
                        break;
                    default:
                        if (Resid != R.raw.huanghun) {
                            Resid = R.raw.huanghun;
                            videoFeed.setValue(Resid);
                            System.out.println("! set Defult");
                        }
                        break;
                }
            }
            //System.out.println(weatherInfo.getTemp()+"<<>>");
        }
    };
    public double covert(Double data)
    {
        unit = "C";
        if (unit.equals("C")) {
            rawt = (data-273.15);
            return rawt;
        }
        if (unit.equals("K"))
        {
            rawt = data;
            tail = "K";
            return rawt;
        }
        if (unit.equals("F"))
        {
            rawt = (32 + (data*1.8));
            tail = "°F";
            return rawt;
        }
        return 0;
    }
    public int getResid()
    {
        return Resid;
    }
    public LiveData<Integer> getVideoFeed()
    {
        return videoFeed;
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        int serNum = clickedItemIndex;
    }
}