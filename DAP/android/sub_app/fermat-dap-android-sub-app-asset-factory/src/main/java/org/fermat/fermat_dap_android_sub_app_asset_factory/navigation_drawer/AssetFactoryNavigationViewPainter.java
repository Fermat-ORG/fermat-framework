package org.fermat.fermat_dap_android_sub_app_asset_factory.navigation_drawer;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.engine.NavigationViewPainter;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;

import org.fermat.fermat_dap_api.layer.all_definition.exceptions.CantGetIdentityAssetIssuerException;

import java.lang.ref.WeakReference;

/**
 * Created by frank on 12/9/15.
 */
public class AssetFactoryNavigationViewPainter implements NavigationViewPainter {

    private WeakReference<Context> activity;
    private final ActiveActorIdentityInformation identityAssetIssuer;

    public AssetFactoryNavigationViewPainter(Context activity, ActiveActorIdentityInformation identityAssetIssuer) {
        this.activity = new WeakReference<Context>(activity);
        this.identityAssetIssuer = identityAssetIssuer;
    }

    @Override
    public View addNavigationViewHeader(ActiveActorIdentityInformation identityAssetIssuer) {
        try {
            return FragmentsCommons.setUpHeaderScreen((LayoutInflater) activity.get()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE), activity.get(), identityAssetIssuer);
        } catch (CantGetIdentityAssetIssuerException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public FermatAdapter addNavigationViewAdapter() {
        try {
            return new AssetFactoryNavigationViewAdapter(activity.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ViewGroup addNavigationViewBodyContainer(LayoutInflater layoutInflater, ViewGroup base) {
//        return (RelativeLayout) layoutInflater.inflate(R.layout.dap_navigation_drawer_issuer_wallet_bottom, base, true);
        return null;
    }

//    @Override
//    public Bitmap addBodyBackground() {
//        Bitmap drawable = null;
//        try {
//            BitmapFactory.Options options = new BitmapFactory.Options();
//            options.inScaled = true;
//            options.inSampleSize = 5;
//            drawable = BitmapFactory.decodeResource(
//                    activity.getResources(), R.drawable.cbw_navigation_drawer_background, options);
//        } catch (OutOfMemoryError error) {
//            error.printStackTrace();
//        }
//        return drawable;
//    }

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
