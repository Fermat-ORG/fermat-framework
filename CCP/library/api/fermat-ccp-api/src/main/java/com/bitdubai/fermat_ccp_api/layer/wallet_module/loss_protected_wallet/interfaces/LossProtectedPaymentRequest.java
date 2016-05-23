package com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces;

import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.enums.CryptoPaymentState;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by Natalia on 2016.03.04.
 */
public interface LossProtectedPaymentRequest extends Serializable {

    int SEND_PAYMENT=0;
    int RECEIVE_PAYMENT=1;

    UUID getRequestId();

    String getDate();

    String getReason();

    long getAmount();

    LossProtectedWalletContact getContact();

    int getType();

    CryptoPaymentState getState();
}
