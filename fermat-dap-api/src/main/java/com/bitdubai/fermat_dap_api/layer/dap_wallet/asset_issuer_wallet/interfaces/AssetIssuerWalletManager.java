package com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces;

/**
 * Created by franklin on 04/09/15.
 */
public interface AssetIssuerWalletManager {

    AssetIssuerWallet loadAssetIssuerWallet(String walletPublicKey);

    void createWalletAssetIssuer (String walletPublicKey, String assetIssuerPublicKey);
}