package org.fermat.fermat_dap_android_wallet_asset_issuer.common.header;

import android.content.Context;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.engine.HeaderViewPainter;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;

import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_issuer.interfaces.AssetIssuerWalletSupAppModuleManager;

import java.lang.ref.WeakReference;

/**
 * Created by Nerio on 17/12/15.
 */
public class WalletAssetIssuerHeaderPainter implements HeaderViewPainter {

    private static final String TAG = "IssuerNavigationView";

    private WeakReference<Context> activity;
    ReferenceAppFermatSession<AssetIssuerWalletSupAppModuleManager> assetIssuerSession;

    public WalletAssetIssuerHeaderPainter(Context activity,
                                          ReferenceAppFermatSession<AssetIssuerWalletSupAppModuleManager> assetIssuerSession) {

        this.activity = new WeakReference<>(activity);
        this.assetIssuerSession = assetIssuerSession;
    }

    @Override
    public void addExpandableHeader(ViewGroup viewGroup) {
        WalletAssetIssuerHeaderFactory.constructHeader(viewGroup);
    }
}
