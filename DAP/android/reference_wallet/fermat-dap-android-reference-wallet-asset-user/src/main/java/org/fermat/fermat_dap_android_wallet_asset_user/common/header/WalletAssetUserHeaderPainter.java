package org.fermat.fermat_dap_android_wallet_asset_user.common.header;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.engine.HeaderViewPainter;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;

import org.fermat.fermat_dap_android_wallet_asset_user.sessions.AssetUserSession;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_user.interfaces.AssetUserWalletSubAppModuleManager;

import java.lang.ref.WeakReference;

import static com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT;
import static com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets.DAP_ASSET_USER_WALLET;

/**
 * Created by Nerio on 17/12/15.
 */
public class WalletAssetUserHeaderPainter implements HeaderViewPainter {

    private static final String TAG = "UserNavigationView";

    private WeakReference<Context> activity;
    private ActiveActorIdentityInformation identityAssetIssuer;
    AssetUserWalletSubAppModuleManager moduleManager;
    AssetUserSession assetUserSession;
    private ErrorManager errorManager;

    public WalletAssetUserHeaderPainter(Context activity, AssetUserSession assetUserSession) {
        this.activity = new WeakReference<>(activity);
        this.assetUserSession = assetUserSession;

        try {
            moduleManager = assetUserSession.getModuleManager();
            errorManager = assetUserSession.getErrorManager();

        } catch (Exception ex) {
            if (errorManager == null)
                Log.e(TAG, ex.getMessage(), ex);
            else
                errorManager.reportUnexpectedWalletException(DAP_ASSET_USER_WALLET,
                        DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);
        }
    }

    @Override
    public void addExpandableHeader(ViewGroup viewGroup) {
        WalletAssetUserHeaderFactory.constructHeader(viewGroup);
    }
}