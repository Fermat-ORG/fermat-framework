package com.bitdubai.reference_niche_wallet.bitcoin_wallet.common;

import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.interfaces.CryptoWalletTransaction;

import java.util.Date;

/**
 * Created by mati on 2015.07.15..
 */
public class TransactionItem {

    public CryptoWalletTransaction cryptoWalletTransaction;

    public Date date;

    public TransactionItem(CryptoWalletTransaction cryptoWalletTransaction, Date date) {
        this.cryptoWalletTransaction = cryptoWalletTransaction;
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TransactionItem that = (TransactionItem) o;

        return date.equals(that.date);

    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }
}
