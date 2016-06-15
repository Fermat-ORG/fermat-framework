package org.fermat.fermat_dap_android_wallet_asset_user.navigation_drawer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.bitdubai.fermat_android_api.engine.FermatApplicationCaller;
import com.bitdubai.fermat_android_api.engine.NavigationViewPainter;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.R;

import org.fermat.fermat_dap_api.layer.all_definition.exceptions.CantGetIdentityAssetUserException;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_user.interfaces.AssetUserWalletSubAppModuleManager;

import java.lang.ref.WeakReference;

/**
 * Created by frank on 12/9/15.
 */
public class UserWalletNavigationViewPainter implements NavigationViewPainter {

    private static final String TAG = "UserNavigationView";

    private WeakReference<Context> activity;
    private WeakReference<FermatApplicationCaller> applicationsHelper;
    ReferenceAppFermatSession<AssetUserWalletSubAppModuleManager> assetUserSession;

    public UserWalletNavigationViewPainter(Context activity,
                                           ReferenceAppFermatSession<AssetUserWalletSubAppModuleManager> assetUserSession,
                                           FermatApplicationCaller applicationsHelper) {

        this.activity = new WeakReference<>(activity);
        this.assetUserSession = assetUserSession;
        this.applicationsHelper = new WeakReference<>(applicationsHelper);
    }

    @Override
    public View addNavigationViewHeader() {
        try {
            return FragmentsCommons.setUpHeaderScreen((LayoutInflater) activity.get()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE), activity.get(), assetUserSession, applicationsHelper.get());
        } catch (CantGetIdentityAssetUserException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public FermatAdapter addNavigationViewAdapter() {
        try {
            return new UserWalletNavigationViewAdapter(activity.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ViewGroup addNavigationViewBodyContainer(LayoutInflater layoutInflater, ViewGroup base) {
        //Dap v3
        return (RelativeLayout) layoutInflater.inflate(R.layout.dap_v3_navigation_drawer_user_wallet_bottom, base, true);

        //dap v2
        //return null;
    }

    //DAP V3
    @Override
    public Bitmap addBodyBackground() {
        Bitmap drawable = null;
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inScaled = true;
            //options.inSampleSize = 5;
            drawable = BitmapFactory.decodeResource(
                    activity.get().getResources(), R.drawable.element_background, options);
        } catch (OutOfMemoryError error) {
            error.printStackTrace();
        }
        return drawable;
    }

    //DAP V2
    //@Override
    //public Bitmap addBodyBackground() {
    //return null;
    //}

    //DAP V2
    //@Override
    //public int addBodyBackgroundColor() {
    //return 0;
    //}

    //DAP V3
    @Override
    public int addBodyBackgroundColor() {
        return R.drawable.dap_v3_navigation_drawer_user_wallet_gradient_background;
    }

    @Override
    public RecyclerView.ItemDecoration addItemDecoration() {
        return null;
    }

    @Override
    public boolean hasBodyBackground() {

        //dap v2
        //return false;

        //dap v3
        return true;
    }

    @Override
    public boolean hasClickListener() {
        return false;
    }
}
