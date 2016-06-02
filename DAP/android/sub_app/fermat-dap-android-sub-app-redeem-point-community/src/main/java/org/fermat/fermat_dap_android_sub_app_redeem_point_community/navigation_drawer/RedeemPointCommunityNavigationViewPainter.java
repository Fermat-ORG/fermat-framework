package org.fermat.fermat_dap_android_sub_app_redeem_point_community.navigation_drawer;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.engine.NavigationViewPainter;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;

import org.fermat.fermat_dap_android_sub_app_redeem_point_community.sessions.AssetRedeemPointCommunitySubAppSession;
import org.fermat.fermat_dap_api.layer.all_definition.exceptions.CantGetIdentityRedeemPointException;
import org.fermat.fermat_dap_api.layer.dap_sub_app_module.redeem_point_community.interfaces.RedeemPointCommunitySubAppModuleManager;

import java.lang.ref.WeakReference;

/**
 * Created by Nerio on 24/12/15.
 */
public class RedeemPointCommunityNavigationViewPainter implements NavigationViewPainter {

    private static final String TAG = "Red-ComunNavigationView";

    private WeakReference<Context> activity;
    private ActiveActorIdentityInformation activeIdentity;
    AssetRedeemPointCommunitySubAppSession assetRedeemPointCommunitySubAppSession;
    RedeemPointCommunitySubAppModuleManager moduleManager;
    private ErrorManager errorManager;

    public RedeemPointCommunityNavigationViewPainter(Context activity, AssetRedeemPointCommunitySubAppSession assetRedeemPointCommunitySubAppSession) {
        this.activity = new WeakReference<Context>(activity);
        this.assetRedeemPointCommunitySubAppSession = assetRedeemPointCommunitySubAppSession;

        errorManager = assetRedeemPointCommunitySubAppSession.getErrorManager();

        try {
            moduleManager = assetRedeemPointCommunitySubAppSession.getModuleManager();
            activeIdentity = this.moduleManager.getActiveAssetRedeemPointIdentity();

        } catch (FermatException ex) {
            if (errorManager == null)
                Log.e(TAG, ex.getMessage(), ex);
            else
                Log.e(TAG, ex.getMessage(), ex);
        }
    }

    @Override
    public View addNavigationViewHeader() {
        //TODO: el actorIdentityInformation lo podes obtener del module en un hilo en background y hacer un lindo loader mientras tanto
        try {
            return RedeemPointCommunityFragmentsCommons.setUpHeaderScreen((LayoutInflater) activity.get()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE), activity.get(), activeIdentity);
        } catch (CantGetIdentityRedeemPointException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public FermatAdapter addNavigationViewAdapter() {
        return new RedeemPointCommunityNavigationAdapter(activity.get(), null);
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
