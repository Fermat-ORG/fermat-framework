package com.bitdubai.sub_app.intra_user_community.navigation_drawer;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.engine.FermatApplicationCaller;
import com.bitdubai.fermat_android_api.engine.NavigationViewPainter;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetActiveLoginIdentityException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserModuleManager;
import com.bitdubai.sub_app.intra_user_community.adapters.AppNavigationAdapter;
import com.bitdubai.sub_app.intra_user_community.common.utils.FragmentsCommons;


import java.lang.ref.WeakReference;

/**
 * Created by mati on 2015.12.24..
 */
public class IntraUserCommunityNavigationViewPainter implements NavigationViewPainter {

    private WeakReference<Context> activity;
   // private final ActiveActorIdentityInformation intraUserLoginIdentity;
    private WeakReference<FermatApplicationCaller> applicationsHelper;
    private ReferenceAppFermatSession<IntraUserModuleManager> intraUserSubAppSession;

    public IntraUserCommunityNavigationViewPainter(Context activity,ReferenceAppFermatSession intraUserSubAppSession,FermatApplicationCaller applicationsHelper) {
        this.activity = new WeakReference(activity);
        this.intraUserSubAppSession = intraUserSubAppSession;
        this.applicationsHelper = new WeakReference<FermatApplicationCaller>(applicationsHelper);
    }

    @Override
    public View addNavigationViewHeader() {
        try {

            return FragmentsCommons.setUpHeaderScreen((LayoutInflater) activity.get()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE), activity.get(), intraUserSubAppSession,applicationsHelper.get());
        } catch (CantGetActiveLoginIdentityException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public FermatAdapter addNavigationViewAdapter() {
        return new AppNavigationAdapter(activity.get(), null);
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
        return true;
    }
}
