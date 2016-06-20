package com.bitdubai.android_core.app.common.version_1.adapters;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Parcelable;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragmentInterface;

/**
 * Created by Matias Furszyfer on 2016.06.08..
 */
public class TabsPagerAdapter2<F extends Fragment & AbstractFermatFragmentInterface> extends FermatScreenAdapter<F> implements FermatUIAdapter<F>{

    public TabsPagerAdapter2(FragmentManager fragmentManager,String[] tabsTitles,F[] fragments){
        super(fragmentManager,fragments);
        titles = tabsTitles;
    }

    public TabsPagerAdapter2(FragmentManager fm, F[] fragments) {
        super(fm, fragments);
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
    public Parcelable saveState() {
        return null;
    }

}