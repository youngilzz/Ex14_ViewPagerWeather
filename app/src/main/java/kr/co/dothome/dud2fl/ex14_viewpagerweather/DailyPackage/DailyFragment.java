package kr.co.dothome.dud2fl.ex14_viewpagerweather.DailyPackage;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import kr.co.dothome.dud2fl.ex14_viewpagerweather.DailyPackage.DailyData.DailyWeather;
import kr.co.dothome.dud2fl.ex14_viewpagerweather.R;
import kr.co.dothome.dud2fl.ex14_viewpagerweather.Utils.ApiService;
import kr.co.dothome.dud2fl.ex14_viewpagerweather.Utils.SetRetrofit;
import kr.co.dothome.dud2fl.ex14_viewpagerweather.DailyPackage.DailyData.DailyWeather;
import kr.co.dothome.dud2fl.ex14_viewpagerweather.R;
import kr.co.dothome.dud2fl.ex14_viewpagerweather.Utils.ApiService;
import kr.co.dothome.dud2fl.ex14_viewpagerweather.Utils.SetRetrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DailyFragment extends Fragment implements Callback<JsonObject>{

    private double lat;
    private double lon;
    private ImageView ivIcon;
    private TextView tvLat;
    private TextView tvLon;
    private TextView tvCity;
    private TextView tvTemp;
    private TextView tvComment;
    private View view;
    private Gson gson = new Gson();
    private Call<JsonObject> call;
    private DailyWeather daily;


    public DailyFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      view = inflater.inflate(R.layout.fragment_daily, container, false);
        initView();
        initData();
        return view;
    }


    public void initView(){
        lat = getArguments().getDouble("lat");
        lon = getArguments().getDouble("lon");
        ivIcon = view.findViewById(R.id.iv_icon);
        tvLat = view.findViewById(R.id.tv_lat);
        tvLon = view.findViewById(R.id.tv_lon);
        tvCity = view.findViewById(R.id.tv_city);
        tvTemp = view.findViewById(R.id.tv_temp);
        tvComment = view.findViewById(R.id.tv_comment);
    }

    private void initData() {
        tvLat.setText("위도: "+lat);
        tvLon.setText("경도: "+lon);
        call = SetRetrofit.retrofit().create(ApiService.class).getDailyWeather(ApiService.APPID, lat, lon, ApiService.UNIT);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
        if(response.isSuccessful()){
            daily = gson.fromJson(response.body(), DailyWeather.class);
            if(daily != null){
                tvCity.setText("지역: " + daily.getName());
                String iconUrl = ApiService.ICONBASE + daily.getWeather().get(0).getIcon() + ".png";
                Picasso.get().load(iconUrl).into(ivIcon);
                String tempStr = daily.getMain().getTemp() + " / ";
                tempStr += daily.getMain().getTempMax()+"도[최고], ";
                tempStr += daily.getMain().getTempMin()+"도[최저], ";
                tvTemp.setText(tempStr);
                String cmtStr = daily.getWeather().get(0).getMain();
                cmtStr += "["+daily.getWeather().get(0).getDescription()+"]";
                tvComment.setText(cmtStr);
            }
        }
        else {
            Toast.makeText(getActivity(), "데이터 이상", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onFailure(Call<JsonObject> call, Throwable t) {
        Toast.makeText(getActivity(), "통신실패", Toast.LENGTH_LONG).show();
    }
}
