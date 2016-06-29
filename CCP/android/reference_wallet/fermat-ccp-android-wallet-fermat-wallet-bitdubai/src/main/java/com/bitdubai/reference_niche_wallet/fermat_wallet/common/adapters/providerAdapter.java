package com.bitdubai.reference_niche_wallet.fermat_wallet.common.adapters;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

/**
 * Created by root on 26/06/16.
 */
public class providerAdapter extends FragmentPagerAdapter {

    public providerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
