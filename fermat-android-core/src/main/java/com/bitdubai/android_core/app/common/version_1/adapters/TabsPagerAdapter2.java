package com.bitdubai.android_core.app.common.version_1.adapters;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Parcelable;
import android.support.v13.app.FragmentStatePagerAdapter;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragmentInterface;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Matias Furszyfer on 2016.06.08..
 */
public class TabsPagerAdapter2<F extends Fragment & AbstractFermatFragmentInterface> extends FragmentStatePagerAdapter implements FermatUIAdapter<F>{

    private String[] titles;
    private F[] fragments;

    public TabsPagerAdapter2(FragmentManager fragmentManager,String[] tabsTitles,F[] fragments){
        super(fragmentManager);
        titles = tabsTitles;
        this.fragments = fragments;

    }

    public void destroyItem(android.view.ViewGroup container, int position, Object object) {
        FragmentManager manager = ((Fragment) object).getFragmentManager();
        if(manager != null) {
            FragmentTransaction trans = manager.beginTransaction();
            trans.detach((Fragment) object);
            trans.remove((Fragment) object);
            trans.commit();
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        if(titles.length>0) {
            title = titles[position];
        }
        return title;
    }

    @Override
    public int getCount() {
        return fragments.length;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }


    @Override
    public Parcelable saveState() {
        return null;
    }


    public List<F> getLstCurrentFragments() {
        return Arrays.asList(fragments);
    }

    public void destroyCurrentFragments(){
        for(AbstractFermatFragmentInterface fragment : fragments){
            Fragment fragment1 =(Fragment)fragment;
            FragmentManager manager = fragment1.getFragmentManager();
            if(manager != null) {
                FragmentTransaction trans = manager.beginTransaction();
                trans.detach(fragment1);
                trans.remove(fragment1);
                trans.commit();
            }
        }
        fragments = null;
    }
}