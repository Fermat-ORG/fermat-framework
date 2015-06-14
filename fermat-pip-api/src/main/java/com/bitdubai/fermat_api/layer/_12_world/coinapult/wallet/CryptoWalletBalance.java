/*
 * @#ICoinapultBalance.java - 2015
 * Copyright bitDubai.com., All rights reserved.
  * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_api.layer._12_world.coinapult.wallet;

import java.sql.Timestamp;

/**
 * The interface <code>com.bitdubai.fermat_api.layer._11_world.coinapult.wallet.ICoinapultBalance</code>
 * <p/>
 * Created by Roberto Requena - (rrequena) on 02/05/15.
 *
 * @version 1.0
 */
public interface CryptoWalletBalance {

    /**
     * Return the amount
     *
     * @return Long
     */
    public Long getAmount();

    /**
     * Return the currency
     *
     * @return String
     */
    public String getCurrency();

    /**
     * Return is the Balance is update
     *
     * @return Boolean
     */
    public Boolean getIsUpdate();

    /**
     * Return the Timestamp
     *
     * @return Timestamp
     */
    public Timestamp getLastTimestampUpdated();
}
