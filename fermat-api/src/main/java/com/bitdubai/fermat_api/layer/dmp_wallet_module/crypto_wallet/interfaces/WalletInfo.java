package com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.interfaces;

/**
 * Created by mati on 2015.09.17..
 */
public interface WalletInfo {

    // este te trae la cantidad de transacciones
    public int getTotalTransactions();

    // este te trae el total de las transacciones
    public long getTotalAmountOfTransactions();

}
