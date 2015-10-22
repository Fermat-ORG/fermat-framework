package com.bitdubai.fermat_ccp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletWalletContact;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.PaymentRequest;

/**
 * Created by Matias Furszyfer on 2015.09.17..
 */
public class CryptoWalletWalletModulePaymentRequest implements PaymentRequest{

    private String date;

    private String reason;

    private long amount;

    private CryptoWalletWalletContact cryptoWalletWalletContact;

    private int type;

    private String state;

    public CryptoWalletWalletModulePaymentRequest(String date, String reason, long amount, CryptoWalletWalletContact cryptoWalletWalletContact, int type,String state) {
        this.date = date;
        this.reason = reason;
        this.amount = amount;
        this.cryptoWalletWalletContact = cryptoWalletWalletContact;
        this.type=type;
        this.state=state;
    }

    @Override
    public String getDate() {
        return date;
    }

    @Override
    public String getReason() {
        return reason;
    }

    @Override
    public long getAmount() {
        return amount;
    }

    @Override
    public CryptoWalletWalletContact getContact() {
        return cryptoWalletWalletContact;
    }

    @Override
    public int getType() {
        return type;
    }

    @Override
    public String getState() {
        return state;
    }


}
