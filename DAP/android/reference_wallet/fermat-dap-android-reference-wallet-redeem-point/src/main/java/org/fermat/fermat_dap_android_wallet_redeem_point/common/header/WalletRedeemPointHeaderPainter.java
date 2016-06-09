package org.fermat.fermat_dap_android_wallet_redeem_point.common.header;

import android.content.Context;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.engine.HeaderViewPainter;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;

import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_redeem_point.interfaces.AssetRedeemPointWalletSubAppModule;

import java.lang.ref.WeakReference;

/**
 * Created by Nerio on 17/12/15.
 */
public class WalletRedeemPointHeaderPainter implements HeaderViewPainter {

    private static final String TAG = "RedeemNavigationView";

    private WeakReference<Context> activity;
    ReferenceAppFermatSession<AssetRedeemPointWalletSubAppModule> redeemPointSession;

    public WalletRedeemPointHeaderPainter(Context activity,
                                          ReferenceAppFermatSession<AssetRedeemPointWalletSubAppModule> redeemPointSession) {

        this.activity = new WeakReference<>(activity);
        this.redeemPointSession = redeemPointSession;
    }

    @Override
    public void addExpandableHeader(ViewGroup viewGroup) {
        WalletAssetUserHeaderFactory.constructHeader(viewGroup);
    }
}