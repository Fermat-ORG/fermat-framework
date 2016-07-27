package com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting;

import java.io.Serializable;

/**
 * Created by Miguel Payarez (miguel_payarez@hotmail.com) on 7/5/16.
 */
public interface CryptoBrokerWalletSettingFee extends Serializable {


    /**
     * The method <code>feeOrigin</code> returns one of this  (AMOUNT, FUNDS)  of the CryptoBrokerWalletSettingFee
     *
     * @return an String of the miner fee taking origin in this case (wallet or transaction) of CryptoBrokerWalletSettingFee
     */
    String getFeeOrigin();

    /**
     * The method <code>setFeeOrigin</code> sets the feeOrigin of the CryptoBrokerWalletSettingFee
     *
     * @param feeOrigin
     */
    void setFeeOrigin(String feeOrigin);

    /**
     * The method <code>getBitcoinFee</code> returns one of this (2500,4500,8000) of the fee of the CryptoBrokerWalletSettingFee
     *
     * @return an long of Amount of fee  public key
     */
    long getBitcoinFee();

    /**
     * The method <code>bitcoinFee</code> sets the fee amount of the CryptoBrokerWalletSettingFee
     *
     * @param bitcoinFee
     */
    void setBitcoinFee(long bitcoinFee);

}
