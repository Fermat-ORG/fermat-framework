package com.bitdubai.fermat_ccp_plugin.layer.wallet_module.loss_protected_wallet.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.enums.CryptoPaymentState;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedPaymentRequest;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWalletContact;


import java.io.Serializable;
import java.util.UUID;

/**
 * Created Natalia Cortez on 07/03/2016.
 */
public class LossProtectedWalletModulePaymentRequest implements LossProtectedPaymentRequest,Serializable {

    private String date;

    private String reason;

    private long amount;

    private LossProtectedWalletContact cryptoWalletWalletContact;

    private int type;

    private CryptoPaymentState state;

    private UUID requestId;

    public LossProtectedWalletModulePaymentRequest(UUID requestId,String date, String reason, long amount, LossProtectedWalletContact cryptoWalletWalletContact, int type,CryptoPaymentState state) {
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
    public LossProtectedWalletContact getContact() {
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
