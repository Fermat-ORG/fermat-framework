package com.bitdubai.fermat_ccp_api.layer.wallet_module.fermat_wallet.interfaces;

import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.enums.CryptoPaymentState;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by Matias Furszyfer on 2015.09.16..
 */
public interface PaymentRequest extends Serializable {

    int SEND_PAYMENT=0;
    int RECEIVE_PAYMENT=1;

    UUID getRequestId();

    String getDate();

    String getReason();

    long getAmount();

    FermatWalletWalletContact getContact();

    int getType();

    CryptoPaymentState getState();
}
