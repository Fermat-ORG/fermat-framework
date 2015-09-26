/*
 * @#CoinapultWallet.java - 2015
 * Copyright bitDubai.com., All rights reserved.
  * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_dmp_plugin.layer.world.coinapult.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.dmp_world.coinapult.wallet.CryptoWalletAddress;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer._11_world.coinapult.developer.bitdubai.version_1.structure.</code>
 * <p/>
 * Created by Roberto Requena - (rrequena) on 30/04/15.
 *
 * @version 1.0
 */
public class CoinapultAddress extends CryptoAddress implements CryptoWalletAddress {

    /**
     * Represent the status
     */
    private String status;

    /**
     * Constructor
     */
    public CoinapultAddress() {
        super();
    }

    /**
     * Constructor
     *
     * @param address
     * @param cryptoCurrency
     * @param status
     */
    protected CoinapultAddress(String address, CryptoCurrency cryptoCurrency, String status) {
        super();
        this.setAddress(address);
        this.setCryptoCurrency(cryptoCurrency);
        this.status = status;
    }

    @Override
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
