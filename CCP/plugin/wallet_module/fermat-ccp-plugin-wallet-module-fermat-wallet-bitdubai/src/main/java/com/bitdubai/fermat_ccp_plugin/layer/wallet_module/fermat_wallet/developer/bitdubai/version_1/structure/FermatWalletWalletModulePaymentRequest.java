package com.bitdubai.fermat_ccp_plugin.layer.wallet_module.fermat_wallet.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.enums.CryptoPaymentState;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletWalletContact;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.fermat_wallet.interfaces.FermatWalletWalletContact;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.fermat_wallet.interfaces.PaymentRequest;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by Matias Furszyfer on 2015.09.17..
 */
public class FermatWalletWalletModulePaymentRequest implements PaymentRequest,Serializable {

    private String date;

    private String reason;

    private long amount;

    private FermatWalletWalletContact fermatWalletWalletContact;

    private int type;

    private CryptoPaymentState state;

    private UUID requestId;

    public FermatWalletWalletModulePaymentRequest(UUID requestId, String date, String reason, long amount, FermatWalletWalletContact cryptoWalletWalletContact, int type, CryptoPaymentState state) {
        this.requestId = requestId;

        this.date = date;
        this.reason = reason;
        this.amount = amount;
        this.fermatWalletWalletContact = cryptoWalletWalletContact;
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
    public FermatWalletWalletContact getContact() {
        return fermatWalletWalletContact;
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
