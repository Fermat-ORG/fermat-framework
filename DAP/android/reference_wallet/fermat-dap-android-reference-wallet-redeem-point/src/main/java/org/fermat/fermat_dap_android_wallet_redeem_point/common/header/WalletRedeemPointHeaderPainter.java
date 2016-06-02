package org.fermat.fermat_dap_android_wallet_redeem_point.common.header;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.engine.HeaderViewPainter;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;

import org.fermat.fermat_dap_android_wallet_redeem_point.sessions.RedeemPointSession;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_redeem_point.interfaces.AssetRedeemPointWalletSubAppModule;

import java.lang.ref.WeakReference;

import static com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT;
import static com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets.DAP_REDEEM_POINT_WALLET;

/**
 * Created by Nerio on 17/12/15.
 */
public class WalletRedeemPointHeaderPainter implements HeaderViewPainter {

    private static final String TAG = "RedeemNavigationView";

    private WeakReference<Context> activity;
    private ActiveActorIdentityInformation identityAssetIssuer;
    AssetRedeemPointWalletSubAppModule moduleManager;
    RedeemPointSession redeemPointSession;
    private ErrorManager errorManager;

    public WalletRedeemPointHeaderPainter(Context activity, RedeemPointSession redeemPointSession) {
        this.activity = new WeakReference<>(activity);
        this.redeemPointSession = redeemPointSession;

        try {
            moduleManager = redeemPointSession.getModuleManager();
            errorManager = redeemPointSession.getErrorManager();

        } catch (Exception ex) {
            if (errorManager == null)
                Log.e(TAG, ex.getMessage(), ex);
            else
                errorManager.reportUnexpectedWalletException(DAP_REDEEM_POINT_WALLET,
                        DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);
        }
    }

    @Override
    public void addExpandableHeader(ViewGroup viewGroup) {
        WalletAssetUserHeaderFactory.constructHeader(viewGroup);
    }
}