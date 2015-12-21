package com.bitdubai.fermat_android_api.engine;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_api.layer.all_definition.identities.ActiveIdentity;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.interfaces.IntraWalletUserIdentity;

/**
 * Created by mati on 2015.11.24..
 */
public interface NavigationViewPainter {

    View addNavigationViewHeader(ActiveIdentity intraUserLoginIdentity);

    FermatAdapter addNavigationViewAdapter();

    ViewGroup addNavigationViewBodyContainer(LayoutInflater layoutInflater,ViewGroup base);

    Bitmap addBodyBackground();

    int addBodyBackgroundColor();

    RecyclerView.ItemDecoration addItemDecoration();

    boolean hasBodyBackground();
}
