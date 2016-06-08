package com.bitdubai.sub_app.wallet_manager.adapter;


import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;

import java.util.List;

/**
 * WizardPageAdapter
 *
 * @author Matias Furszyfer
 * @version 1.0
 */
public class WizardPageAdapter extends FragmentStatePagerAdapter{

    private List<AbstractFermatFragment> fragments;

    public WizardPageAdapter(FragmentManager fm, List<AbstractFermatFragment> fragments) {
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

    public List<AbstractFermatFragment> getLstCurrentFragments() {
        return fragments;
    }
}
