/*
 * @#ICoinapultAddress.java - 2015
 * Copyright bitDubai.com., All rights reserved.
  * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_api.layer._12_world.coinapult.wallet;

import com.bitdubai.fermat_api.layer._1_definition.enums.CryptoCurrency;

/**
 * The interface <code>com.bitdubai.fermat_api.layer._11_world.coinapult.wallet.ICoinapultAddress</code>
 * <p/>
 * Created by Roberto Requena - (rrequena) on 02/05/15.
 *
 * @version 1.0
 */
public interface CryptoWalletAddress {

    /**
     * Return the status of the address
     *
     * @return String
     */
    public String getStatus();

    /**
     * Return the address
     * @return String
     */
    public String getAddress();

    /**
     * Return the CryptoCurrency
     * @return CryptoCurrency
     */
    public CryptoCurrency getCryptoCurrency();

}
