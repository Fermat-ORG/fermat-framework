package com.bitdubai.sub_app.intra_user_community.navigation_drawer;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.engine.NavigationViewPainter;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetActiveLoginIdentityException;
import com.bitdubai.sub_app.intra_user_community.adapters.AppNavigationAdapter;
import com.bitdubai.sub_app.intra_user_community.common.utils.FragmentsCommons;

/**
 * Created by mati on 2015.12.24..
 */
public class IntraUserCommunityNavigationViewPainter implements NavigationViewPainter {

    private Activity activity;

    public IntraUserCommunityNavigationViewPainter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public View addNavigationViewHeader(ActiveActorIdentityInformation intraUserLoginIdentity) {
        try {
            return FragmentsCommons.setUpHeaderScreen(activity.getLayoutInflater(), activity, intraUserLoginIdentity);
        } catch (CantGetActiveLoginIdentityException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public FermatAdapter addNavigationViewAdapter() {
        return new AppNavigationAdapter(activity, null);
    }

    @Override
    public ViewGroup addNavigationViewBodyContainer(LayoutInflater layoutInflater, ViewGroup base) {
        return null;
    }

    @Override
    public Bitmap addBodyBackground() {
        return null;
    }

    @Override
    public int addBodyBackgroundColor() {
        return 0;
    }

    @Override
    public RecyclerView.ItemDecoration addItemDecoration() {
        return null;
    }

    @Override
    public boolean hasBodyBackground() {
        return false;
    }

    @Override
    public boolean hasClickListener() {
        return false;
    }
}
