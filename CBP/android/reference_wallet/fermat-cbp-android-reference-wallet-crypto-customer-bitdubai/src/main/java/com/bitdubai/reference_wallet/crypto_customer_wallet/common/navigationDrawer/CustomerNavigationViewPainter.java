package com.bitdubai.reference_wallet.crypto_customer_wallet.common.navigationDrawer;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.bitdubai.fermat_android_api.engine.NavigationViewPainter;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetActiveLoginIdentityException;
import com.bitdubai.reference_wallet.crypto_customer_wallet.R;
import com.bitdubai.reference_wallet.crypto_customer_wallet.util.FragmentsCommons;

/**
 * Created by mati on 2015.11.24..
 */
public class CustomerNavigationViewPainter implements NavigationViewPainter {

    private final ActorIdentity actorIdentity;
    private Activity activity;

    public CustomerNavigationViewPainter(Activity activity, ActorIdentity actorIdentity) {
        this.activity = activity;
        this.actorIdentity = actorIdentity;
    }

    @Override
    public View addNavigationViewHeader() {
        try {
            return FragmentsCommons.setUpHeaderScreen(activity.getLayoutInflater(), activity, actorIdentity);
        } catch (CantGetActiveLoginIdentityException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public FermatAdapter addNavigationViewAdapter() {
        try {
            return new NavigationViewAdapter(activity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ViewGroup addNavigationViewBodyContainer(LayoutInflater layoutInflater, ViewGroup base) {
        return (RelativeLayout) layoutInflater.inflate(R.layout.ccw_navigation_view_bottom, base, true);
    }

    @Override
    public Drawable addBodyBackground() {
        return ContextCompat.getDrawable(activity.getApplicationContext(), R.drawable.ccw_navigation_drawer_background);
    }

    @Override
    public int addBodyBackgroundColor() {
        return Color.WHITE;
    }

    @Override
    public RecyclerView.ItemDecoration addItemDecoration() {
        return null;
    }
}
