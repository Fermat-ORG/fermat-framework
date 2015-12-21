package com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.navigation_drawer;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.bitdubai.android_fermat_ccp_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_api.layer.all_definition.identities.ActiveIdentity;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetActiveLoginIdentityException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserLoginIdentity;

/**
 * Created by Matias Furszyfer on 2015.11.24..
 */
public class BitcoinWalletNavigationViewPainter implements com.bitdubai.fermat_android_api.engine.NavigationViewPainter {

    private final ActiveIdentity intraUserLoginIdentity;
    private Activity activity;

    public BitcoinWalletNavigationViewPainter(Activity activity, ActiveIdentity intraUserLoginIdentity) {
        this.activity = activity;
        this.intraUserLoginIdentity = intraUserLoginIdentity;
    }

    @Override
    public View addNavigationViewHeader(ActiveIdentity intraUserLoginIdentity) {
        try {
            return FragmentsCommons.setUpHeaderScreen(activity.getLayoutInflater(), activity,intraUserLoginIdentity);
        } catch (CantGetActiveLoginIdentityException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public FermatAdapter addNavigationViewAdapter() {
        try {
            NavigationViewAdapter navigationViewAdapter = new NavigationViewAdapter(activity);
            return navigationViewAdapter;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ViewGroup addNavigationViewBodyContainer(LayoutInflater layoutInflater,ViewGroup base) {
        RelativeLayout relativeLayout = (RelativeLayout) layoutInflater.inflate(R.layout.navigation_view_bottom,base,true);
        //base.setLayoutParams(new RelativeLayout.LayoutParams(activity,));
        return relativeLayout;
    }

    @Override
    public Bitmap addBodyBackground() {
        Bitmap drawable = null;
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inScaled = true;
            options.inSampleSize = 5;
            drawable = BitmapFactory.decodeResource(
                    activity.getResources(), R.drawable.bg_drawer_body,options);
            //drawable = ContextCompat.getDrawable(activity.getApplicationContext(), R.drawable.bg_drawer_body);
        }catch (OutOfMemoryError error){
            error.printStackTrace();
        }
        return drawable;
    }

    @Override
    public int addBodyBackgroundColor() {
        return 0;
    }

    @Override
    public RecyclerView.ItemDecoration addItemDecoration(){
        return null;
    }

    @Override
    public boolean hasBodyBackground() {
        return true;
    }
}
