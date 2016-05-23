package com.bitdubai.reference_niche_wallet.fermat_wallet.common.navigation_drawer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.bitdubai.android_fermat_ccp_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetActiveLoginIdentityException;

import java.lang.ref.WeakReference;

/**
 * Created by Matias Furszyfer on 2015.11.24..
 */
public class BitcoinWalletNavigationViewPainter implements com.bitdubai.fermat_android_api.engine.NavigationViewPainter {

    private final ActiveActorIdentityInformation intraUserLoginIdentity;
    private WeakReference<Context> activity;

    public BitcoinWalletNavigationViewPainter(Context activity, ActiveActorIdentityInformation intraUserLoginIdentity) {
        this.activity = new WeakReference<Context>(activity);
        this.intraUserLoginIdentity = intraUserLoginIdentity;
    }

    @Override
    public View addNavigationViewHeader(ActiveActorIdentityInformation intraUserLoginIdentity) {
        try {
            if (intraUserLoginIdentity==null) Log.e("APP","intra user null in app connection, please check this");
            return FragmentsCommons.setUpHeaderScreen((LayoutInflater) activity.get()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE), activity.get(),intraUserLoginIdentity);
        } catch (CantGetActiveLoginIdentityException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public FermatAdapter addNavigationViewAdapter() {
        try {
            NavigationViewAdapter navigationViewAdapter = new NavigationViewAdapter(activity.get());
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
                    activity.get().getResources(), R.drawable.bg_drawer_body,options);
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

    @Override
    public boolean hasClickListener() {
        return true;
    }
}
