package Model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WeatherApi {
    @GET("onecall")
    Call<WeatherApiResponse> getWeatherData(@Query("lat") float lat, @Query("lon") float lon,@Query("exclude") String exclude,@Query("appid") String appid,@Query("units") String units,@Query("lang") String lang);
    
}
