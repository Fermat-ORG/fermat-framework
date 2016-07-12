package com.bitdubai.sub_app.wallet_manager.adapter;


import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_wpd.wallet_manager.R;

import java.util.List;

/**
 * WizardPageAdapter
 *
 * @author Matias Furszyfer
 * @version 1.0
 */
public class WizardPageAdapter extends FragmentStatePagerAdapter{

    private List<AbstractFermatFragment> fragments;
    private FragmentManager mFragmentManager;

    public WizardPageAdapter(FragmentManager fm, List<AbstractFermatFragment> fragments) {
        super(fm);
        this.mFragmentManager = fm;
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

//    @Override
//    public void destroyItem(ViewGroup container, int position, Object object) {
////        super.destroyItem(container, position, object);
//    }

    private String getFragmentTag(int position) {
        return "android:switcher:" + R.id.view_pager_welcome + ":" + position;
    }

    public AbstractFermatFragment getActiveFragment(ViewPager container, int position) {
        String name = makeFragmentName(container.getId(), position);
        return (AbstractFermatFragment) mFragmentManager.findFragmentByTag(name);
    }

    private static String makeFragmentName(int viewId, int index) {
        return "android:switcher:" + viewId + ":" + index;
    }



}
