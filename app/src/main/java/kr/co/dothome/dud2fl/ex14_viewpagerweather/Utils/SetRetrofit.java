package kr.co.dothome.dud2fl.ex14_viewpagerweather.Utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SetRetrofit {
    public static Retrofit retrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
}
