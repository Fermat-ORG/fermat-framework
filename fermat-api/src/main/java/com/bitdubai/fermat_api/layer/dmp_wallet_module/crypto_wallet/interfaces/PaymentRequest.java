package com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.interfaces;

/**
 * Created by Matias Furszyfer on 2015.09.16..
 */
public interface PaymentRequest {

    public String getDate();

    public String getReason();

    public long getAmount();

    public CryptoWalletWalletContact getContact();

}
