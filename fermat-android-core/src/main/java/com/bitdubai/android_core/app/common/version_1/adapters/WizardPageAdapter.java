package com.bitdubai.android_core.app.common.version_1.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.bitdubai.sub_app.wallet_factory.fragment.version_3.fragment.wizard.CreateWalletFragment;

import java.util.List;

/**
 * Created by francisco on 05/08/15.
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
