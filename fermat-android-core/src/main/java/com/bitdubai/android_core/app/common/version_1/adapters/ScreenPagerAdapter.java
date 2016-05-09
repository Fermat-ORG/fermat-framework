package com.bitdubai.android_core.app.common.version_1.adapters;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v13.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;

import java.util.List;


/**
 * Created by Matias Furszyfer on 23/07/2015.
 */

/**
 * ScreenPagerAdapter to add new subApp
 */
public class ScreenPagerAdapter extends FragmentPagerAdapter implements FermatUIAdapter {


    private static final String TAG = "ScreenPagerAdapter";
    private List<AbstractFermatFragment> fragments;

    /**
     * @param fm
     * @param fragments
     */

    public ScreenPagerAdapter(FragmentManager fm, List<AbstractFermatFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }
    /*
     * Return the Fragment associated with a specified position.
     */
    @Override
    public Fragment getItem(int position) {
        if(fragments.isEmpty()){
            Log.e(TAG,"fragmentos en desktop nulo");
        }
        if(position>fragments.size()){
            Log.e(TAG,"position mayor a cantidad de fragmentos en desktop");
        }
        return this.fragments.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //if (position >= getCount()) {
//            FragmentManager manager = ((Fragment) object).getFragmentManager();
//            FragmentTransaction trans = manager.beginTransaction();
//            trans.remove((Fragment) object);
//            trans.commit();
       // }
    }



    /*
             * Return the number of views available.
             */
    @Override
    public int getCount() {
        return this.fragments.size();
    }

    public List<AbstractFermatFragment> getLstCurrentFragments() {
        return fragments;
    }

    public void changeData(List<AbstractFermatFragment> fragments) {
        removeAllFragments();
        this.fragments.clear();
        this.fragments = fragments;
        notifyDataSetChanged();
    }

    public void removeAllFragments(){
        for (AbstractFermatFragment fragment : this.fragments) {
            FragmentManager manager = fragment.getFragmentManager();
            FragmentTransaction trans = manager.beginTransaction();
            trans.remove(fragment);
            trans.commit();
        }
    }


}