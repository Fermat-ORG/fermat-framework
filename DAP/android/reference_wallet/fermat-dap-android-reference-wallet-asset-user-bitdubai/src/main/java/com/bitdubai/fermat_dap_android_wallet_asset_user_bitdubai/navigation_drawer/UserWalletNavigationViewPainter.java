package com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.navigation_drawer;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.bitdubai.fermat_android_api.engine.NavigationViewPainter;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.R;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_user.exceptions.CantGetIdentityAssetUserException;

/**
 * Created by frank on 12/9/15.
 */
public class UserWalletNavigationViewPainter implements NavigationViewPainter {

    private Activity activity;
    private final ActiveActorIdentityInformation identityAssetUser;

    public UserWalletNavigationViewPainter(Activity activity, ActiveActorIdentityInformation identityAssetUser) {
        this.activity = activity;
        this.identityAssetUser = identityAssetUser;
    }

    @Override
    public View addNavigationViewHeader(ActiveActorIdentityInformation identityAssetUser) {
        try {
            return FragmentsCommons.setUpHeaderScreen(activity.getLayoutInflater(), activity, identityAssetUser);
        } catch (CantGetIdentityAssetUserException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public FermatAdapter addNavigationViewAdapter() {
        try {
            return new UserWalletNavigationViewAdapter(activity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ViewGroup addNavigationViewBodyContainer(LayoutInflater layoutInflater, ViewGroup base) {
        return (RelativeLayout) layoutInflater.inflate(R.layout.dap_navigation_drawer_user_wallet_bottom, base, true);
    }

    @Override
    public Bitmap addBodyBackground() {
        Bitmap drawable = null;
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inScaled = true;
            options.inSampleSize = 5;
            drawable = BitmapFactory.decodeResource(
                    activity.getResources(), R.drawable.cbw_navigation_drawer_background, options);
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
