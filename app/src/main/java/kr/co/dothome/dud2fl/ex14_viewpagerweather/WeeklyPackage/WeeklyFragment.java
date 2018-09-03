package kr.co.dothome.dud2fl.ex14_viewpagerweather.WeeklyPackage;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import kr.co.dothome.dud2fl.ex14_viewpagerweather.R;
import kr.co.dothome.dud2fl.ex14_viewpagerweather.Utils.ApiService;
import kr.co.dothome.dud2fl.ex14_viewpagerweather.Utils.SetRetrofit;
import kr.co.dothome.dud2fl.ex14_viewpagerweather.WeeklyPackage.WeeklyData.WeeklyWeather;
import kr.co.dothome.dud2fl.ex14_viewpagerweather.R;
import kr.co.dothome.dud2fl.ex14_viewpagerweather.Utils.ApiService;
import kr.co.dothome.dud2fl.ex14_viewpagerweather.Utils.SetRetrofit;
import kr.co.dothome.dud2fl.ex14_viewpagerweather.WeeklyPackage.WeeklyData.WeeklyWeather;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class WeeklyFragment extends Fragment implements Callback<JsonObject>{

    private View view;
    private RecyclerView recyclerView;
    private ArrayList<WeeklyModel> weeklyModels = new ArrayList<>();
    private Gson gson = new Gson();
    private Call<JsonObject> call;
    private WeeklyWeather data;
    private WeeklyRecyclerAdapter adapter;
    private Double lat;
    private Double lon;

    public WeeklyFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_weekly, container, false);
        recyclerView = view.findViewById(R.id.recycler);
        lat = getArguments().getDouble("lat");
        lon = getArguments().getDouble("lon");
        initData();
        return view;
    }

    private void initData() {
        call = SetRetrofit.retrofit().create(ApiService.class).getWeeklyWeather(ApiService.APPID,lat,lon,ApiService.UNIT);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
        if(response.isSuccessful()) {
            data = gson.fromJson(response.body(), WeeklyWeather.class);
            for(int i=0; i<data.getCnt(); i++) {
                String icon = ApiService.ICONBASE;
                icon += data.getList().get(i).getWeather().get(0).getIcon();
                icon += ".png";
                String dt = data.getList().get(i).getDtTxt();
                String date = Integer.parseInt(dt.substring(5, 7)) + "월 ";
                date += Integer.parseInt(dt.substring(8, 10)) + "일 ";
                date += Integer.parseInt(dt.substring(11, 13)) + "시";
                String temp = data.getList().get(i).getMain().getTemp() + "도";
                String tempMax = data.getList().get(i).getMain().getTempMax() + "도";
                String tempMin = data.getList().get(i).getMain().getTempMin() + "도";
                String deg = Integer.parseInt(
                        String.valueOf(
                                Math.round(data.getList().get(i).getWind().getDeg()))) + "deg";
                String speed = data.getList().get(i).getWind().getSpeed() + "ms";
                String detail = deg + " / " + speed;
                String summary = data.getList().get(i).getWeather().get(0).getMain();
                summary += " / " + data.getList().get(i).getWeather().get(0).getDescription();
                weeklyModels.add(new WeeklyModel(icon, temp, tempMin, tempMax, date, detail, summary));
            }
            initAdapter();
        }
        else {
            Toast.makeText(getActivity(), "데이터이상", Toast.LENGTH_LONG).show();
        }
    }

    private void initAdapter() {
        adapter = new WeeklyRecyclerAdapter(getActivity(), weeklyModels);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void onFailure(Call<JsonObject> call, Throwable t) {
        Toast.makeText(getActivity(), "통신실패", Toast.LENGTH_LONG).show();
    }
}
