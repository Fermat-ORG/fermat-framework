/*
 * @#ICoinapultTransaction.java - 2015
 * Copyright bitDubai.com., All rights reserved.
  * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_api.layer._12_world.coinapult.wallet;

import java.sql.Timestamp;

/**
 * The interface <code>com.bitdubai.fermat_api.layer._11_world.coinapult.wallet.ICoinapultTransaction</code>
 * <p/>
 * Created by Roberto Requena - (rrequena) on 02/05/15.
 *
 * @version 1.0
 */
public interface CryptoWalletTransaction {

    /**
     * Return the address
     *
     * @return String
     */
    public String getAddress();

    /**
     * Return the timestamp
     *
     * @return Timestamp
     */
    public Timestamp getCompleteTime();

    /**
     * Return the timestamp
     *
     * @return Timestamp
     */
    public Timestamp getExpiration();

    /**
     * Return the in amount
     *
     * @return Long
     */
    public Long getInAmount();

    /**
     * Return the in currency
     *
     * @return String
     */
    public String getInCurrency();

    /**
     * Return the in expected
     *
     * @return Long
     */
    public Long getInExpected();


    /**
     * Return the out amount
     *
     * @return Long
     */
    public Long getOutAmount();

    /**
     * Return the out currency
     *
     * @return String
     */
    public String getOutCurrency();

    /**
     * Return the out expected
     *
     * @return Long
     */
    public Long getOutExpected();

    /**
     * Return the quote ask
     *
     * @return Long
     */
    public Long getQuoteAsk();

    /**
     * Return the quote bid
     *
     * @return Long
     */
    public Long getQuoteBid();

    /**
     * Return the estate
     *
     * @return String
     */
    public String getState();

    /**
     * Return the timestamp
     *
     * @return Timestamp
     */
    public Timestamp getTimestamp();

    /**
     * Return the transaction id
     *
     * @return String
     */
    public String getTransactionId();

    /**
     * Return the type
     *
     * @return String
     */
    public String getType();
}
