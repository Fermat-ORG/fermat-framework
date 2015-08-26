package com.bitdubai.android_core.app.common.version_1.adapters;
import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import java.util.List;


/**
 * Created by Matias on 23/07/2015.
 */

/**
 * ScreenPagerAdapter to add new subApp
 */
public class ScreenPagerAdapter extends FragmentPagerAdapter {


    private List<Fragment> fragments;

    /**
     * @param fm
     * @param fragments
     */

    public ScreenPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }
    /*
     * Return the Fragment associated with a specified position.
     */
    @Override
    public Fragment getItem(int position) {
        return this.fragments.get(position);
    }

    /*
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return this.fragments.size();
    }
}