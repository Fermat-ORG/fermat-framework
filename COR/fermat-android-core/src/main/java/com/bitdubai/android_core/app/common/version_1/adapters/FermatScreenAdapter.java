package com.bitdubai.android_core.app.common.version_1.adapters;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v13.app.FragmentStatePagerAdapter;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragmentInterface;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Matias Furszyfer on 2016.06.09..
 */
public class FermatScreenAdapter<F extends Fragment & AbstractFermatFragmentInterface> extends FragmentStatePagerAdapter implements FermatUIAdapter<F> {

    protected F[] fragments;
    protected String[] titles;
    private int lastPosition;

    public FermatScreenAdapter(FragmentManager fm, F[] fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public List<F> getLstCurrentFragments() {
        return Arrays.asList(fragments);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }

    public void destroyItem(android.view.ViewGroup container, int position, Object object) {
//        FragmentManager manager = ((Fragment) object).getFragmentManager();
//        if(manager != null) {
//            FragmentTransaction trans = manager.beginTransaction();
//            trans.detach((Fragment) object);
//            trans.remove((Fragment) object);
//            trans.commit();
//        }
    }

    public void destroyCurrentFragments() {
        for (AbstractFermatFragmentInterface fragment : fragments) {
            Fragment fragment1 = (Fragment) fragment;
            FragmentManager manager = fragment1.getFragmentManager();
            if (manager != null) {
                FragmentTransaction trans = manager.beginTransaction();
                trans.detach(fragment1);
                trans.remove(fragment1);
                trans.commit();
            }
        }
    }

    public void onFragmentFocus(int position) {
        if (fragments != null) {
            fragments[lastPosition].setFragmentFocus(false);
            lastPosition = position;
            fragments[position].setFragmentFocus(true);
        }
    }

    public void setTitles(String[] titles) {
        this.titles = titles;
    }

    public void setStartFragmentPosition(int startFragmentPosition) {
        this.lastPosition = startFragmentPosition;
        if (fragments != null)
            fragments[lastPosition].setFragmentFocus(true);
    }
}
