package Acts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weather_.R;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Locale;

import Model.CurrentLocation;
import Model.LocationAcquire;
import Model.WeatherHttpHandler;
import Model.WeatherInfo;

public class TestingAct extends AppCompatActivity {
    CurrentLocation currentLocation;
    String alarmTitle;
    String alarmBody;
    String sourser;
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
    WeatherHttpHandler weatherHttpHandler = new WeatherHttpHandler();
    Button btn_start;
    private static final int REQUEST_PERMISSIONS = 100;
    boolean boolean_permission;
    TextView tv_latitude, tv_longitude, tv_weather,tv_area,tv_locality;
    SharedPreferences mPref;
    SharedPreferences.Editor medit;
    Double latitude,longitude;
    Geocoder geocoder;
    String unit;
    double rawt;
    String tail = "°C";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        geocoder = new Geocoder(this, Locale.getDefault());
        //registerReceiver(whhBroadcastReceiver,new IntentFilter(WeatherHttpHandler.str_receiver));
        registerReceiver(locationBroadcastReceiver,new IntentFilter(LocationAcquire.str_receiver));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);
        btn_start = (Button) findViewById(R.id.btn_start);
        tv_weather = (TextView) findViewById(R.id.tv_weather);
        tv_latitude = (TextView) findViewById(R.id.tv_latitude);
        tv_longitude = (TextView) findViewById(R.id.tv_longitude);
        tv_area = (TextView)findViewById(R.id.tv_area);
        tv_locality = (TextView)findViewById(R.id.tv_locality);
        LocationAcquire locationAcquire = new LocationAcquire();
        Intent intent = new Intent(getApplicationContext(),LocationAcquire.class);
        Intent whhIntent = new Intent(getApplicationContext(),WeatherHttpHandler.class);
        startService(intent);
        startService(whhIntent);
        //locationAcquire.serviceGetLocation();
        lat = locationAcquire.getLatitude();
        lon = locationAcquire.getLongitude();
        cityInfo = locationAcquire.getCurrentLocation().getCityName();
        btn_start.setOnClickListener(v->
        {
            Intent intent1 = new Intent(TestingAct.this, StartAct.class);
            startActivity(intent1);
        });
        //setWeather();
     //  tv_latitude.setText((int) lat);
     //  tv_longitude.setText((int) lon);
        //System.out.println(cityInfo+">>>");
    }
   // private final BroadcastReceiver whhBroadcastReceiver = new BroadcastReceiver() {
   //     @Override
   //     public void onReceive(Context context, Intent intent) {
   //         boolean alarmStatus = false;
   //         System.out.println("???"+intent.getStringExtra("temp"));
   //         temp = Double.parseDouble(intent.getStringExtra("temp"));
   //         felt = Double.parseDouble(intent.getStringExtra("felt"));
   //         high = Double.parseDouble(intent.getStringExtra("max"));
   //         low = Double.parseDouble(intent.getStringExtra("min"));
   //         hum = Integer.parseInt(intent.getStringExtra("hum"));
   //         pressure = Integer.parseInt(intent.getStringExtra("pre"));
   //         weatherCon = (intent.getStringExtra("wcon"));
   //         weatherConDes = (intent.getStringExtra("wcondes"));
   //         if (intent.getStringExtra("alatitle")!=null)
   //         {
   //             alarmStatus = true;
   //             alarmTitle = intent.getStringExtra("alatitle");
   //             alarmBody = intent.getStringExtra("alabody");
   //             sourser = intent.getStringExtra("sou");
   //         }
   //         tv_weather.setText(String.valueOf(temp));
   //         System.out.println("/JRT: Temp: "+temp+" Alarm = "+alarmStatus);
   //     }
   // };
    private final BroadcastReceiver locationBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
        lon = Double.parseDouble(intent.getStringExtra("lon"));
        lat = Double.parseDouble(intent.getStringExtra("lat"));
        cityInfo = intent.getStringExtra("city");
        address = intent.getStringExtra("con");
        lang = intent.getStringExtra("lang");
        tv_latitude.setText(String.valueOf(lat));
        tv_longitude.setText(String.valueOf(lon));
        tv_locality.setText(cityInfo);
        tv_area.setText(address);
        System.out.println("/JRT: LON: "+lon+" LAT: "+lat+" CityInfo "+cityInfo);
      //weatherHttpHandler.setLon(lon);
      //weatherHttpHandler.setLat(lat);
      //weatherHttpHandler.setLang(lang);
        //weatherHttpHandler.setLang("zh_cn");
        WeatherInfo weatherInfo = weatherHttpHandler.getWeatherInfoFromJson();
        unit = "C";
        if (unit.equals("C")) {
            rawt = (weatherInfo.getTemp()-273.15);
        }
        if (unit.equals("K"))
        {
            rawt = weatherInfo.getTemp();
            tail = "K";
        }
        if (unit.equals("F"))
        {
            rawt = (32 + (weatherInfo.getTemp()*1.8));
            tail = "°F";
        }
        Double temp = BigDecimal.valueOf(rawt).setScale(2, RoundingMode.HALF_UP).doubleValue();
        tv_weather.setText(temp +tail);
        System.out.println(weatherInfo.getTemp()+"MMM");
        }
    };
   // private void setWeather()
   // {
   //     weatherHttpHandler = new WeatherHttpHandler();
   //     weatherHttpHandler.setLat(lat);
   //     weatherHttpHandler.setLon(lon);
   //     System.out.println(weatherHttpHandler.getUrl()+"<<>>");
   //     String weather = weatherHttpHandler.getWeatherInfoFromJson().getTemp()+"";
   //     System.out.println("JRT:/ WER: "+weather);
   //     tv_weather.setText(weather);
   // }
    //LocationAcquire locationAcquire = new LocationAcquire();
    //@Override
    //protected void onCreate(Bundle savedInstanceState) {
       // LocationAcquire locationAcquire = new LocationAcquire();
       // super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_testing);
       // weatherHttpHandler.getWeatherInfoFromJson();
       // Intent intent = new Intent(getApplicationContext(),LocationAcquire.class);
       // startService(intent);
//
       // lat = locationAcquire.getCurrentLocation().getLat();
       // lon = locationAcquire.getCurrentLocation().getLon();
       // cityInfo = locationAcquire.getCurrentLocation().getCityName();
       // //GetLocation(this);
       // System.out.println(lat+"<<"+lon+"<<"+cityInfo);
       // weatherinfoViewModel = new ViewModelProvider(this).get(WeatherinfoViewModel.class);
       // try {
       //     weatherinfoViewModel.setWeatherRepository();
       // } catch (Exception e) {
       //     e.printStackTrace();
       // }
       // weatherinfoViewModel.getWeatherInfo().observe(this,weatherInfo -> {
       //     System.out.println("/RES :"+weatherInfo.getTemp());
       // });
    private void GetLocation(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        List<String> providers = locationManager.getProviders(true);
        String locationProvider;
        if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
            locationProvider = LocationManager.NETWORK_PROVIDER;
        } else if (providers.contains(LocationManager.GPS_PROVIDER)) {
            locationProvider = LocationManager.GPS_PROVIDER;
        } else {
            Toast.makeText(this, "No gps available", Toast.LENGTH_SHORT).show();
            return;
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(locationProvider);
        if (location != null) {
            showLocation(location);
            getAddresses(location);
        } else {
            locationManager.requestLocationUpdates(locationProvider, 0, 0, mLocationLisener);
        }
    }
    private void showLocation (Location location)
    {
        lat = location.getLatitude();
        lon = location.getLongitude();
        String address = "lat: "+location.getLatitude()+" lon: "+location.getLongitude();
        System.out.println(address);
    }
    LocationListener mLocationLisener = new LocationListener() {
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras)
        {

        }
        @Override
        public void onProviderEnabled(String provider)
        {

        }
        @Override
        public void onProviderDisabled(String provider)
        {

        }
        @Override
        public void onLocationChanged(@NonNull Location location) {
            showLocation(location);
        }
    };
    private List<Address> getAddresses(Location location)
    {
        System.out.println("???1");
        List<Address> result = null;
        try{
            if (location!=null)
            {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                System.out.println(cityInfo+"???2");
                result = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                cityInfo = result.get(0).getLocality();
                System.out.println(cityInfo+"???3");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return result;
    }
}