package com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces;

/**
 * Created by franklin on 07/09/15.
 */
public interface AssetIssuerWallet {

    String getWalletPublicKey();

    String getAssetIssuerPublicKey(); //Definir no lo tengo claro

    AssetIssuerWalletBalance getAvailableBalance();

    AssetIssuerWalletBalance getBookBalance();
}
