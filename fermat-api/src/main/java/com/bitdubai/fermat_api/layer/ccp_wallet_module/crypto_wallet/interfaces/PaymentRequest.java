package com.bitdubai.fermat_api.layer.ccp_wallet_module.crypto_wallet.interfaces;

/**
 * Created by Matias Furszyfer on 2015.09.16..
 */
public interface PaymentRequest {

    public final int SEND_PAYMENT=0;
    public final int RECEIVE_PAYMENT=1;

    public String getDate();

    public String getReason();

    public long getAmount();

    public CryptoWalletWalletContact getContact();

    public int getType();

}
