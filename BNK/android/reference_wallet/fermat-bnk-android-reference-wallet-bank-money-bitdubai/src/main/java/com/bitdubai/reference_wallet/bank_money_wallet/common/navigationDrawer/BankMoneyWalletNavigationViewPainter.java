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
import com.bitdubai.reference_wallet.bank_money_wallet.R;

import java.lang.ref.WeakReference;

/**
 * Created by mati on 2015.11.24..
 */
public class BankMoneyWalletNavigationViewPainter extends com.bitdubai.fermat_android_api.engine.NavigationViewPainter {

    public BankMoneyWalletNavigationViewPainter(Activity activity) {
        super(activity);
    }


    public View addNavigationViewHeader() {
        /*try {
            return FragmentsCommons.setUpHeaderScreen(activity.getLayoutInflater(), activity, actorIdentity);
        } catch (CantGetActiveLoginIdentityException e) {
            e.printStackTrace();
        }*/
        return null;
    }

    public FermatAdapter addNavigationViewAdapter() {
        /*try {
            return new NavigationViewAdapter(activity);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        return null;
    }

    public ViewGroup addNavigationViewBodyContainer(LayoutInflater layoutInflater, ViewGroup base) {
        return null;//(RelativeLayout) layoutInflater.inflate(R.layout.cbw_navigation_view_bottom, base, true);
    }

    public Bitmap addBodyBackground() {
        return BitmapFactory.decodeResource(getContext().getResources(), R.drawable.bnk_navigation_drawer_background);
    }

    public int addBodyBackgroundColor() {
        return Color.parseColor("#1375a7");
    }

    public RecyclerView.ItemDecoration addItemDecoration() {
        return null;
    }

    public boolean hasBodyBackground() {
        return false;
    }

    public boolean hasClickListener() {
        return false;
    }
}
