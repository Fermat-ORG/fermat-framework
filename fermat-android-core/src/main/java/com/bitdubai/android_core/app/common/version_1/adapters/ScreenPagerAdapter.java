package com.bitdubai.android_core.app.common.version_1.adapters;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.util.Log;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragmentInterface;


/**
 * Created by Matias Furszyfer on 23/07/2015.
 */

/**
 * ScreenPagerAdapter to add new subApp
 */
public class ScreenPagerAdapter<F extends Fragment & AbstractFermatFragmentInterface> extends FermatScreenAdapter<F> implements FermatUIAdapter<F> {


    private static final String TAG = "ScreenPagerAdapter";

    /**
     * @param fm
     * @param fragments
     */

    public ScreenPagerAdapter(FragmentManager fm, F[] fragments) {
        super(fm, fragments);
    }
    /*
     * Return the Fragment associated with a specified position.
     */
    @Override
    public Fragment getItem(int position) {
        if(fragments.length<1){
            Log.e(TAG,"fragmentos en desktop nulo");
        }
        if(position>fragments.length){
            Log.e(TAG,"position mayor a cantidad de fragmentos en desktop");
        }
        return this.fragments[position];
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
        return this.fragments.length;
    }


    public void changeData(F[] fragments) {
        removeAllFragments();
        this.fragments = null;
        this.fragments = fragments;
        notifyDataSetChanged();
    }

    public void removeAllFragments(){
        for (Fragment fragment : this.fragments) {
            FragmentManager manager = fragment.getFragmentManager();
            FragmentTransaction trans = manager.beginTransaction();
            trans.remove(fragment);
            trans.commit();
        }
        fragments = null;
    }


}