package org.fermat.fermat_dap_android_sub_app_redeem_point_community.navigation_drawer;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.engine.NavigationViewPainter;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;

import org.fermat.fermat_dap_api.layer.all_definition.exceptions.CantGetIdentityAssetUserException;

import java.lang.ref.WeakReference;

/**
 * Created by Nerio on 24/12/15.
 */
public class RedeemPointCommunityNavigationViewPainter implements NavigationViewPainter {

    private WeakReference<Context> activity;
    private ActiveActorIdentityInformation activeIdentity;

    public RedeemPointCommunityNavigationViewPainter(Context activity, ActiveActorIdentityInformation activeIdentity) {
        this.activity = new WeakReference<Context>(activity);
        this.activeIdentity = activeIdentity;
    }

    @Override
    public View addNavigationViewHeader(ActiveActorIdentityInformation identityAssetIssuer) {
        try {
            return RedeemPointCommunityFragmentsCommons.setUpHeaderScreen((LayoutInflater) activity.get()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE), activity.get(), identityAssetIssuer);
        } catch (CantGetIdentityAssetUserException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public FermatAdapter addNavigationViewAdapter() {
        return new RedeemPointCommunityNavigationAdapter(activity.get(), null);
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
