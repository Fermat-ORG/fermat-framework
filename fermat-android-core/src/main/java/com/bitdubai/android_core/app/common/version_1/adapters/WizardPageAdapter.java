package com.bitdubai.android_core.app.common.version_1.adapters;


import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * WizardPageAdapter
 *
 * @author Francisco Vasquez
 * @version 1.0
 */
public class WizardPageAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> fragments;

    public WizardPageAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
