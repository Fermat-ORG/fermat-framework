package org.fermat.fermat_dap_android_wallet_asset_user.common.header;

import android.content.Context;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.engine.HeaderViewPainter;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;

import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_user.interfaces.AssetUserWalletSubAppModuleManager;

import java.lang.ref.WeakReference;

/**
 * Created by Nerio on 17/12/15.
 */
public class WalletAssetUserHeaderPainter implements HeaderViewPainter {

    private static final String TAG = "UserNavigationView";

    private WeakReference<Context> activity;
    ReferenceAppFermatSession<AssetUserWalletSubAppModuleManager> assetUserSession;

    public WalletAssetUserHeaderPainter(Context activity,
                                        ReferenceAppFermatSession<AssetUserWalletSubAppModuleManager> assetUserSession) {

        this.activity = new WeakReference<>(activity);
        this.assetUserSession = assetUserSession;
    }

    @Override
    public void addExpandableHeader(ViewGroup viewGroup) {
        WalletAssetUserHeaderFactory.constructHeader(viewGroup);
    }
}