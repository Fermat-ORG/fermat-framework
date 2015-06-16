/*
 * @#CoinapultBalance.java - 2015
 * Copyright bitDubai.com., All rights reserved.
  * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_dmp_plugin.layer.world.coinapult.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.dmp_world.coinapult.wallet.CryptoWalletBalance;

import java.sql.Timestamp;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer._11_world.coinapult.developer.bitdubai.version_1.structure.CoinapultBalance</code>
 * <p/>
 * Created by Roberto Requena - (rrequena) on 02/05/15.
 *
 * @version 1.0
 */
public class CoinapultBalance implements CryptoWalletBalance {

    /**
     * Represent the amount
     */
    private Long amount;

    /**
     * Represent the currency
     */
    private String currency;

    /**
     * Represent the isUpdate
     */
    private Boolean isUpdate;

    /**
     * Represent the lastTimestampUpdated
     */
    private Timestamp lastTimestampUpdated;

    /**
     * Constructor
     *
     * @param currency
     * @param amount
     * @param isUpdate
     */
    protected CoinapultBalance(String currency, Long amount, Boolean isUpdate) {
        super();
        this.currency = currency;
        this.amount = amount;
        this.isUpdate = isUpdate;
        this.lastTimestampUpdated = new Timestamp(System.currentTimeMillis());
    }

    @Override
    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    @Override
    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public Boolean getIsUpdate() {
        return isUpdate;
    }

    public void setIsUpdate(Boolean isUpdate) {
        this.isUpdate = isUpdate;
    }

    @Override
    public Timestamp getLastTimestampUpdated() {
        return lastTimestampUpdated;
    }

    public void setLastTimestampUpdated(Timestamp lastTimestampUpdated) {
        this.lastTimestampUpdated = lastTimestampUpdated;
    }
}
