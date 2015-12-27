package com.bitdubai.reference_wallet.bank_money_wallet.common.navigationDrawer;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.reference_wallet.bank_money_wallet.R;

/**
 * Created by mati on 2015.11.24..
 */
public class BankMoneyWalletNavigationViewPainter implements com.bitdubai.fermat_android_api.engine.NavigationViewPainter {

    private Activity activity;

    public BankMoneyWalletNavigationViewPainter(Activity activity) {
        this.activity = activity;
    }


    @Override
    public View addNavigationViewHeader(ActiveActorIdentityInformation intraUserLoginIdentity) {
        /*try {
            return FragmentsCommons.setUpHeaderScreen(activity.getLayoutInflater(), activity, actorIdentity);
        } catch (CantGetActiveLoginIdentityException e) {
            e.printStackTrace();
        }*/
        return null;
    }

    @Override
    public FermatAdapter addNavigationViewAdapter() {
        /*try {
            return new NavigationViewAdapter(activity);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        return null;
    }

    @Override
    public ViewGroup addNavigationViewBodyContainer(LayoutInflater layoutInflater, ViewGroup base) {
        return null;//(RelativeLayout) layoutInflater.inflate(R.layout.cbw_navigation_view_bottom, base, true);
    }

    @Override
    public Bitmap addBodyBackground() {
        return BitmapFactory.decodeResource(activity.getResources(), R.drawable.bnk_navigation_drawer_background);
    }

    @Override
    public int addBodyBackgroundColor() {
        return Color.parseColor("#1375a7");
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
