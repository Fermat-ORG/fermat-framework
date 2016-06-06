package org.fermat.fermat_dap_android_sub_app_asset_issuer_community.navigation_drawer;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.engine.FermatApplicationCaller;
import com.bitdubai.fermat_android_api.engine.NavigationViewPainter;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;

import org.fermat.fermat_dap_api.layer.all_definition.exceptions.CantGetIdentityAssetIssuerException;
import org.fermat.fermat_dap_api.layer.dap_sub_app_module.asset_issuer_community.interfaces.AssetIssuerCommunitySubAppModuleManager;

import java.lang.ref.WeakReference;

/**
 * Created by Nerio on 24/12/15.
 */
public class IssuerCommunityNavigationViewPainter implements NavigationViewPainter {

    private static final String TAG = "Iss-ComunNavigationView";

    private WeakReference<Context> activity;
    private WeakReference<FermatApplicationCaller> applicationsHelper;
    ReferenceAppFermatSession<AssetIssuerCommunitySubAppModuleManager> assetIssuerCommunitySubAppSession;

    public IssuerCommunityNavigationViewPainter(Context activity,
                                                ReferenceAppFermatSession<AssetIssuerCommunitySubAppModuleManager> assetIssuerCommunitySubAppSession,
                                                FermatApplicationCaller applicationsHelper) {

        this.activity = new WeakReference<>(activity);
        this.assetIssuerCommunitySubAppSession = assetIssuerCommunitySubAppSession;
        this.applicationsHelper = new WeakReference<>(applicationsHelper);
    }

    @Override
    public View addNavigationViewHeader() {
        try {
            return IssuerCommunityFragmentsCommons.setUpHeaderScreen((LayoutInflater) activity.get()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE), activity.get(), assetIssuerCommunitySubAppSession, applicationsHelper.get());
        } catch (CantGetIdentityAssetIssuerException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public FermatAdapter addNavigationViewAdapter() {
        return new IssuerCommunityNavigationAdapter(activity.get(), null);
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
        return true;
    }
}
