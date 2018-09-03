package kr.co.dothome.dud2fl.ex14_viewpagerweather;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class MainPagerAdapter extends FragmentPagerAdapter{

    private ArrayList<Fragment> fragments = new ArrayList<>();
    private ArrayList<String > tabs = new ArrayList<>();

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragment(Fragment fragment, String tabTitle){
        fragments.add(fragment);
        tabs.add(tabTitle);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabs.get(position);
    }
}
