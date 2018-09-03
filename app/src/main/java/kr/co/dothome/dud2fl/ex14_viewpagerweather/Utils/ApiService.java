package kr.co.dothome.dud2fl.ex14_viewpagerweather.Utils;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    static final String BASEURL = "https://api.openweathermap.org/data/2.5/";
    static final String ICONBASE = "http://motive.co.kr/images/weather/";
    static final String APPID = "21370b063ee72752f5195eaf78e332a8";
    static final String UNIT = "metric";

    @GET("weather")
    Call<JsonObject> getDailyWeather(
    @Query("appid") String appid,
    @Query("lat") double lat,
    @Query("lon") double lon,
    @Query("units") String units);

    @GET("forecast")
    Call<JsonObject> getWeeklyWeather(
    @Query("appid") String appid,
    @Query("lat") double lat,
    @Query("lon") double lon,
    @Query("units") String units);
}//https://api.openweathermap.org/data/2.5/forecast?lat=37.5&lon=126.8&id=524901&appid=21370b063ee72752f5195eaf78e332a8&units=metric
