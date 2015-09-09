package com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces;

import com.bitdubai.fermat_dap_api.exceptions.CantGetTransactionsException;

import java.util.List;

/**
 * Created by franklin on 07/09/15.
 */
public interface AssetIssuerWallet {

    //TODO:Documentat y manejo de expcepciones
    String getWalletPublicKey();

    String getAssetIssuerPublicKey(); //TODO: Definir no lo tengo claro

    AssetIssuerWalletBalance getAvailableBalance();

    AssetIssuerWalletBalance getBookBalance();

    List<AssetIssuerTransaction> getTransacctions(int max, int offset) throws CantGetTransactionsException;
}
