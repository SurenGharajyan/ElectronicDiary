package com.me.reactiveapp.fragmentsDayAdd;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by USER on 27.07.2017.
 */

public class FragmentAdapterDay extends FragmentPagerAdapter {
    public FragmentAdapterDay(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case  0:
                fragment=new RecycleViewFragment();
                break;
            case 1:
//                fragment=new TextWithImageFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
