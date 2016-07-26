package com.bitdubai.android_core.app.common.version_1.navigation_view;

import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.bitdubai.android_core.app.FermatActivity;
import com.bitdubai.android_core.app.common.version_1.adapters.FermatUIAdapter;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragmentInterface;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by mati on 2016.03.28..
 */
public class FermatActionBarDrawerEventListener extends ActionBarDrawerToggle {

    final WeakReference<FermatActivity> activityWeakReference;


    public FermatActionBarDrawerEventListener(FermatActivity activity, DrawerLayout drawerLayout, int openDrawerContentDescRes, int closeDrawerContentDescRes) {
        super(activity, drawerLayout, openDrawerContentDescRes, closeDrawerContentDescRes);
        this.activityWeakReference = new WeakReference<FermatActivity>(activity);
    }

    public FermatActionBarDrawerEventListener(FermatActivity activity, DrawerLayout drawerLayout, Toolbar toolbar, int openDrawerContentDescRes, int closeDrawerContentDescRes) {
        super(activity, drawerLayout, toolbar, openDrawerContentDescRes, closeDrawerContentDescRes);
        this.activityWeakReference = new WeakReference<FermatActivity>(activity);
    }

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {
        super.onDrawerSlide(drawerView, slideOffset);
        InputMethodManager imm =
                (InputMethodManager) activityWeakReference.get().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (activityWeakReference.get().getCurrentFocus() != null && imm != null && imm.isActive()) {
            imm.hideSoftInputFromWindow(activityWeakReference.get().getCurrentFocus().getWindowToken(), 0);
        }
        super.onDrawerSlide(drawerView, slideOffset);
        //float moveFactor = (navigationView.getWidth() * slideOffset);
        //findViewById(R.id.content).setTranslationX(moveFactor);
                            /*if(adapter!=null){
                                if(!adapter.getLstCurrentFragments().isEmpty()){
                                    for (AbstractFermatFragment abstractFermatFragment : adapter.getLstCurrentFragments()) {
                                        abstractFermatFragment.onDrawerSlide(drawerView, slideOffset);
                                    }
                                }
                            }*/
    }

    @Override
    public void onDrawerOpened(View drawerView) {
        super.onDrawerOpened(drawerView);
        FermatUIAdapter fermatUIAdapter = activityWeakReference.get().getAdapter();
        if (fermatUIAdapter != null) {
            if (!fermatUIAdapter.getLstCurrentFragments().isEmpty()) {
                List<AbstractFermatFragmentInterface> list = fermatUIAdapter.getLstCurrentFragments();
                for (AbstractFermatFragmentInterface abstractFermatFragment : list) {
                    if (abstractFermatFragment instanceof AbstractFermatFragment) {
                        ((AbstractFermatFragment) abstractFermatFragment).onDrawerOpen();
                    }
                }
            }
        }
    }

    @Override
    public void onDrawerClosed(View drawerView) {
        super.onDrawerClosed(drawerView);
        FermatUIAdapter fermatUIAdapter = activityWeakReference.get().getAdapter();
        if (fermatUIAdapter != null) {
            if (!fermatUIAdapter.getLstCurrentFragments().isEmpty()) {
                List<AbstractFermatFragmentInterface> list = fermatUIAdapter.getLstCurrentFragments();
                for (AbstractFermatFragmentInterface abstractFermatFragment : list) {
                    if (abstractFermatFragment instanceof AbstractFermatFragment) {
                        ((AbstractFermatFragment) abstractFermatFragment).onDrawerClose();
                    }
                }
            }
        }
    }

    @Override
    public void onDrawerStateChanged(int newState) {
        super.onDrawerStateChanged(newState);
    }
}
