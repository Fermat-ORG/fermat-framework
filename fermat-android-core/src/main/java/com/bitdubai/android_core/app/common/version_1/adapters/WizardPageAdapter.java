package com.bitdubai.android_core.app.common.version_1.adapters;


import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragmentInterface;

import java.util.List;

/**
 * WizardPageAdapter
 *
 * @author Francisco Vasquez
 * @author Matias Furszyfer
 * @version 1.0
 */
public class WizardPageAdapter<F extends Fragment & AbstractFermatFragmentInterface> extends FragmentStatePagerAdapter implements FermatUIAdapter<F>{

    private List<F> fragments;

    public WizardPageAdapter(FragmentManager fm, List<F> fragments) {
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


    @Override
    public List<F> getLstCurrentFragments() {
        return fragments;
    }
}
