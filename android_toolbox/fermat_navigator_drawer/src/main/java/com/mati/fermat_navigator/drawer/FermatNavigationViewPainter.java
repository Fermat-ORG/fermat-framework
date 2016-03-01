package com.mati.fermat_navigator.drawer;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.RelativeLayout;


import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;

import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;

import com.mati.fermat_navigator_drawer.R;



/**
 * Created by natalia on 26/02/16.
 */
public abstract class FermatNavigationViewPainter implements com.bitdubai.fermat_android_api.engine.NavigationViewPainter {

    private final ActiveActorIdentityInformation intraUserLoginIdentity;
    private Activity activity;
    public FermatNavigationViewPainter(Activity activity, ActiveActorIdentityInformation intraUserLoginIdentity) {

        this.activity = activity;
        this.intraUserLoginIdentity = intraUserLoginIdentity;

    }
    @Override
    public View addNavigationViewHeader(ActiveActorIdentityInformation intraUserLoginIdentity) {

            return this.setUpHeaderScreen(activity.getLayoutInflater(), activity, intraUserLoginIdentity);
    }

    @Override
    public FermatAdapter addNavigationViewAdapter() {

        return getNavigationAdapter();
    }

    @Override
    public ViewGroup addNavigationViewBodyContainer(LayoutInflater layoutInflater,ViewGroup base) {
        RelativeLayout relativeLayout = (RelativeLayout) layoutInflater.inflate(R.layout.navigation_view_bottom,base,true);

        return relativeLayout;
    }

    @Override
    public Bitmap addBodyBackground() {
        return getbackgroundDrawable();
    }

    @Override
    public int addBodyBackgroundColor() {
        return 0;
    }
    protected abstract Bitmap getbackgroundDrawable();

    @Override
    public RecyclerView.ItemDecoration addItemDecoration(){
        return null;
    }

    @Override
    public boolean hasBodyBackground() {
        return true;
    }

    @Override
    public boolean hasClickListener() {
        return true;
    }


    protected abstract View setUpHeaderScreen(LayoutInflater inflater,Activity activity,ActiveActorIdentityInformation intraUserLoginIdentity) ;


    protected abstract FermatAdapter getNavigationAdapter();


}
