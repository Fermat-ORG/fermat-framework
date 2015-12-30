package com.bitdubai.reference_wallet.crypto_customer_wallet.common.navigationDrawer;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.bitdubai.fermat_android_api.engine.NavigationViewPainter;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
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
    public View addNavigationViewHeader(ActiveActorIdentityInformation intraUserLoginIdentity) {
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
            return new CryptoCustomerWalletNavigationViewAdapter(activity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ViewGroup addNavigationViewBodyContainer(LayoutInflater layoutInflater, ViewGroup base) {
        RelativeLayout layout = (RelativeLayout) layoutInflater.inflate(R.layout.ccw_navigation_view_bottom, base, true);
        FermatTextView bitcoinBalance = (FermatTextView) layout.findViewById(R.id.ccw_navigation_view_bitcoin_balance);
        bitcoinBalance.setText("0.3521 BTC");

        return layout;
    }

    @Override
    public Bitmap addBodyBackground() {
        Bitmap drawable = null;
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inScaled = true;
            options.inSampleSize = 5;
            drawable = BitmapFactory.decodeResource(
                    activity.getResources(), R.drawable.ccw_navigation_drawer_background, options);
        } catch (OutOfMemoryError error) {
            error.printStackTrace();
        }
        return drawable;
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
        return true;
    }

    @Override
    public boolean hasClickListener() {
        return false;
    }
}
