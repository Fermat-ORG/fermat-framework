package com.bitdubai.fermat_ccp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.enums.CryptoPaymentState;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletWalletContact;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.PaymentRequest;

import java.util.UUID;

/**
 * Created by Matias Furszyfer on 2015.09.17..
 */
public class CryptoWalletWalletModulePaymentRequest implements PaymentRequest{

    private String date;

    private String reason;

    private long amount;

    private CryptoWalletWalletContact cryptoWalletWalletContact;

    private int type;

    private CryptoPaymentState state;

    private UUID requestId;

    public CryptoWalletWalletModulePaymentRequest(UUID requestId,String date, String reason, long amount, CryptoWalletWalletContact cryptoWalletWalletContact, int type,CryptoPaymentState state) {
        this.requestId = requestId;

        this.date = date;
        this.reason = reason;
        this.amount = amount;
        this.cryptoWalletWalletContact = cryptoWalletWalletContact;
        this.type=type;
        this.state=state;
    }

    @Override
    public UUID getRequestId()
    {
        return this.requestId;
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
    public CryptoPaymentState getState() {
        return state;
    }


}
