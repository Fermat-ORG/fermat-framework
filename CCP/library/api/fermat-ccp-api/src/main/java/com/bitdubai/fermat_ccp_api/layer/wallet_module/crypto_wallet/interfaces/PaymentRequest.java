package com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces;

/**
 * Created by Matias Furszyfer on 2015.09.16..
 */
public interface PaymentRequest {

    int SEND_PAYMENT=0;
    int RECEIVE_PAYMENT=1;

    String getDate();

    String getReason();

    long getAmount();

    CryptoWalletWalletContact getContact();

    int getType();

    String getState();
}
