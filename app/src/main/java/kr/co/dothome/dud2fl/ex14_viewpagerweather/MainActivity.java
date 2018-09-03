package kr.co.dothome.dud2fl.ex14_viewpagerweather;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import kr.co.dothome.dud2fl.ex14_viewpagerweather.DailyPackage.DailyFragment;
import kr.co.dothome.dud2fl.ex14_viewpagerweather.Utils.PermitGps;
import kr.co.dothome.dud2fl.ex14_viewpagerweather.WeeklyPackage.WeeklyFragment;
import kr.co.dothome.dud2fl.ex14_viewpagerweather.DailyPackage.DailyFragment;
import kr.co.dothome.dud2fl.ex14_viewpagerweather.Utils.PermitGps;
import kr.co.dothome.dud2fl.ex14_viewpagerweather.WeeklyPackage.WeeklyFragment;

public class MainActivity extends AppCompatActivity implements LocationListener{

    private ViewPager viewPager;
    private MainPagerAdapter adapter;
    private TabLayout tabLayout;
    private DailyFragment dailyFragment;
    private WeeklyFragment weeklyFragment;
    private LocationManager lm;
    private double lat = 37.5;
    private double lon = 127.5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initLocation();
    }

    private void initLocation() {
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if(lm != null){
            PermitGps.check(this, lm, 1000, 5);
        }
    }

    private void initView() {
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
        adapter = new MainPagerAdapter(getSupportFragmentManager());
        dailyFragment = new DailyFragment();
        weeklyFragment = new WeeklyFragment();
        Bundle bundle = new Bundle();
        bundle.putDouble("lat", lat);
        bundle.putDouble("lon", lon);
        dailyFragment.setArguments(bundle);
        weeklyFragment.setArguments(bundle);
        adapter.addFragment(dailyFragment, "오늘의 날씨");
        adapter.addFragment(weeklyFragment, "5일간 날씨");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onLocationChanged(Location location) {
        lat = location.getLatitude();
        lon = location.getLongitude();
        initView();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
