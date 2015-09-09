package com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces;

import com.bitdubai.fermat_dap_api.exceptions.CantCalculateBalanceException;
import com.bitdubai.fermat_dap_api.exceptions.CantRegisterCreditException;
import com.bitdubai.fermat_dap_api.exceptions.CantRegisterDebitException;

/**
 * Created by franklin on 04/09/15.
 */
public interface AssetIssuerWalletBalance {

    //TODO: Documentar y excepciones
    String getWalletPublicKey(); //TODO: Revisar esto

    String getDigitalAssetPublicKey(); //TODO: Revisar esto

    long getBalance()  throws CantCalculateBalanceException;

    void debit(AssetIssuerWalletTransactionRecord assetIssuerWalletTransactionRecord) throws CantRegisterDebitException; //TODO: Debemos de definir la estructura de la transaccion

    void credit(AssetIssuerWalletTransactionRecord assetIssuerWalletTransactionRecord)  throws CantRegisterCreditException; //TODO: Debemos de definir la estructura de la transaccion
}
