package Model;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.weather_.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class LocationAcquire extends Service implements LocationListener {
    boolean isGPSEnable = false;
    boolean isNetworkEnable = false;
    double latitude, longitude;
    LocationManager locationManager;
    Location location;
    public static String str_receiver = "ViewModel.TestingAct";
    public static String str_receiverW = "Model.WeatherHttpHandler";
    Intent intent = new Intent(str_receiver);
    Intent intent2 = new Intent(str_receiverW);
    CurrentLocation currentLocation = new CurrentLocation();
    String cityInfo;
    String country;
    String lang;
    public LocationAcquire() {
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        serviceGetLocation();
        intent = new Intent(str_receiver);
        intent2 = new Intent(str_receiverW);
        //serviceGetLocation();
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        updateValue(location);
    }

    public void serviceGetLocation(){
        locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        isGPSEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnable = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (!isGPSEnable && !isNetworkEnable) {
            System.out.println("LOA: NO GPS");
        } else {
            if (isNetworkEnable) {
                location = null;
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
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, this);
                if (locationManager!=null)
                {
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (location!=null)
                    {
                        Geocoder geocoder = new Geocoder(this,Locale.getDefault());
                        List<Address> addresses = null;
                        try {
                            addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        lang = Locale.getDefault().getLanguage();
                        assert addresses != null;
                        cityInfo= addresses.get(0).getLocality();
                        country = addresses.get(0).getCountryName();
                        currentLocation.setCityName(cityInfo);
                        currentLocation.setDisName(country);
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                        currentLocation.setLat((float) latitude);
                        currentLocation.setLon((float)longitude);
                        updateValue(location);
                        System.out.println("/LAB: LAT: "+latitude+" LON: "+longitude+" CITY: "+cityInfo+" "+country);
                       // locationUpdate(location);
                    }
                }
            }
            if (isGPSEnable)
            {
                location = null;
                assert locationManager != null;
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,0,this);
                if (locationManager!=null)
                {
                    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (location!=null)
                    {
                        Geocoder geocoder = new Geocoder(this,Locale.getDefault());
                        List<Address> addresses = null;
                        try {
                            addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        lang = Locale.getDefault().getLanguage();
                        assert addresses != null;
                        cityInfo= addresses.get(0).getLocality();
                        country = addresses.get(0).getCountryName();
                        currentLocation.setCityName(cityInfo);
                        currentLocation.setDisName(country);
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                        currentLocation.setLat((float) latitude);
                        currentLocation.setLon((float)longitude);
                        updateValue(location);
                        System.out.println("/LAB: LAT: "+latitude+" LON: "+longitude+" CITY: "+cityInfo+" "+country);
                        //locationUpdate(location);
                    }
                }
            }
        }
    }
    private void updateValue(Location location)
    {
        intent.putExtra("lat",latitude+"");
        intent.putExtra("lon",longitude+"");
        intent.putExtra("city",cityInfo+"");
        intent.putExtra("con",country+"");
        intent.putExtra("lang",lang+"");
        intent2.putExtra("lang",lang+"");
        intent2.putExtra("lat",latitude+"");
        intent2.putExtra("lon",longitude+"");
        System.out.println("LAB:/ BrodCast Sended, Value: LA: "+latitude+" LO: "+longitude+" CITY "+cityInfo+" CON "+country+" LANG "+lang);
        sendBroadcast(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public CurrentLocation getCurrentLocation() {
        return currentLocation;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }
}