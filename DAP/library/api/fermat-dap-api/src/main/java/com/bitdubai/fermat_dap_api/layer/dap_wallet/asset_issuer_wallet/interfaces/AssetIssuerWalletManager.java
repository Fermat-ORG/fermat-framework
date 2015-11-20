package com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces;

import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantCreateWalletException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;

/**
 * Created by franklin on 04/09/15.
 */
public interface AssetIssuerWalletManager {

    AssetIssuerWallet loadAssetIssuerWallet(String walletPublicKey) throws CantLoadWalletException;

    void createWalletAssetIssuer (String walletPublicKey) throws CantCreateWalletException;
}