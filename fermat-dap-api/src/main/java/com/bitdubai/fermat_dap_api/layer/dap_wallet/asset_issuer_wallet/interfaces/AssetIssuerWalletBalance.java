package com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces;

import com.bitdubai.fermat_dap_api.exceptions.CantCalculateBalanceException;

/**
 * Created by franklin on 04/09/15.
 */
public interface AssetIssuerWalletBalance {

    //Documentar y excepciones
    String getWalletPublicKey();

    String getDigitalAssetPublicKey();

    long getBalance()  throws CantCalculateBalanceException;

    void debit(AssetIssuerWalletTransactionRecord assetIssuerWalletTransactionRecord); //Debemos de definir la estructura de la transaccion

    void credit(AssetIssuerWalletTransactionRecord assetIssuerWalletTransactionRecord); //Debemos de definir la estructura de la transaccion
}
