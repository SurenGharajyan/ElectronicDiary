package com.me.reactiveapp.fragmentsImageSlide;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 14.07.2017.
 */

public class FragmentAdapter extends FragmentPagerAdapter {
    List<FragmentModel> fragmentList;
    public FragmentAdapter(FragmentManager fm) {
        super(fm);
        fragmentList=new ArrayList<>();
    }

    public void setItems(List<FragmentModel> fragmentList){
        this.fragmentList=fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return new FragmentInterfaceScreen(fragmentList.get(position));
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
