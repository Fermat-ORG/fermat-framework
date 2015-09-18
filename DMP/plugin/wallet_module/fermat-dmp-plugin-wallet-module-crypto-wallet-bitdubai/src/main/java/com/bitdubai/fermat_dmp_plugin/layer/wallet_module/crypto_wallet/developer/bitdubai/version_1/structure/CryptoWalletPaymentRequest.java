package com.bitdubai.fermat_dmp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.interfaces.CryptoWalletWalletContact;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.interfaces.PaymentRequest;

/**
 * Created by Matias Furszyfer on 2015.09.17..
 */
public class CryptoWalletPaymentRequest implements PaymentRequest{

    private String date;

    private String reason;

    private long amount;

    private CryptoWalletWalletContact cryptoWalletWalletContact;

    public CryptoWalletPaymentRequest(String date, String reason, long amount, CryptoWalletWalletContact cryptoWalletWalletContact) {
        this.date = date;
        this.reason = reason;
        this.amount = amount;
        this.cryptoWalletWalletContact = cryptoWalletWalletContact;
    }

    @Override
    public String getDate() {
        return null;
    }

    @Override
    public String getReason() {
        return null;
    }

    @Override
    public long getAmount() {
        return 0;
    }

    @Override
    public CryptoWalletWalletContact getContact() {
        return null;
    }
}
