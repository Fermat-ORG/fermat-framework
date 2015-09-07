package com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces;

import com.bitdubai.fermat_dap_api.exceptions.CantCalculateBalanceException;

/**
 * Created by franklin on 04/09/15.
 */
public interface AssetIssuerWallet {

    //Documentar y excepciones
    String getWalletPublicKey();

    String getDigitalAssetPublicKey();

    long getBalance()  throws CantCalculateBalanceException;

    void debit(); //Debemos de definir la estructura de la transaccion

    void credit(); //Debemos de definir la estructura de la transaccion

    //Debemos de crear un metodo que liste las transacciones

    //Debemos de crear un metodo que modifique un valor de la transacccion y colocarle una descripion

}
