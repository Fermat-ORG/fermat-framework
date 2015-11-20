package com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces;

import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantCreateWalletException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;

/**
 * Created by franklin on 14/10/15.
 */
public interface AssetRedeemPointWalletManager {
    AssetRedeemPointWallet loadAssetRedeemPointWallet(String walletPublicKey) throws CantLoadWalletException;

    void createWalletAssetRedeemPoint (String walletPublicKey) throws CantCreateWalletException;

}
