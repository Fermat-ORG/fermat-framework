package com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.navigation_drawer;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.engine.NavigationViewPainter;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantGetIdentityAssetUserException;

/**
 * Created by Nerio on 24/12/15.
 */
public class UserCommunityNavigationViewPainter implements NavigationViewPainter {

    private Activity activity;
    private ActiveActorIdentityInformation activeIdentity;

    public UserCommunityNavigationViewPainter(Activity activity) {
        this.activity = activity;
    }

    public UserCommunityNavigationViewPainter(Activity activity, ActiveActorIdentityInformation activeIdentity) {
        this.activity = activity;
        this.activeIdentity = activeIdentity;

    }

    @Override
    public View addNavigationViewHeader(ActiveActorIdentityInformation identityAssetUser) {
        try {
            return UserCommunityFragmentsCommons.setUpHeaderScreen(activity.getLayoutInflater(), activity, identityAssetUser);
        } catch (CantGetIdentityAssetUserException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public FermatAdapter addNavigationViewAdapter() {
        return new UserCommunityNavigationAdapter(activity, null);
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
