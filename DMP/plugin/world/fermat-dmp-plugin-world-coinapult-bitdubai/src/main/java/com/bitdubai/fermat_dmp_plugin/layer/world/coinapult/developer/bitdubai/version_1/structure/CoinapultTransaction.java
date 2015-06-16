/*
 * @#CoinapultTransaction.java - 2015
 * Copyright bitDubai.com., All rights reserved.
  * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_dmp_plugin.layer.world.coinapult.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.dmp_world.coinapult.wallet.CryptoWalletTransaction;
import com.bitdubai.fermat_dmp_plugin.layer.world.coinapult.developer.bitdubai.version_1.coinapult_http_client.Transaction;
import com.bitdubai.fermat_dmp_plugin.layer.world.coinapult.developer.bitdubai.version_1.enums.Types;


import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer._11_world.coinapult.developer.bitdubai.version_1.structure.CoinapultTransaction</code>
 * <p/>
 * Created by Roberto Requena - (rrequena) on 02/05/15.
 *
 * @version 1.0
 */
public class CoinapultTransaction implements CryptoWalletTransaction {

    /**
     * Represent the address
     */
    private String address;

    /**
     * Represent the completeTime
     */
    private Timestamp completeTime;

    /**
     * Represent the expiration
     */
    private Timestamp expiration;

    /**
     * Represent the inAmount
     */
    private Long inAmount;

    /**
     * Represent the inCurrency
     */
    private String inCurrency;

    /**
     * Represent the inExpected
     */
    private Long inExpected;

    /**
     * Represent the outAmount
     */
    private Long outAmount;

    /**
     * Represent the outCurrency
     */
    private String outCurrency;

    /**
     * Represent the outExpected
     */
    private Long outExpected;

    /**
     * Represent the quoteAsk
     */
    private Long quoteAsk;

    /**
     * Represent the quoteBid
     */
    private Long quoteBid;

    /**
     * Represent the state
     */
    private String state;

    /**
     * Represent the timestamp
     */
    private Timestamp timestamp;

    /**
     * Represent the transactionId
     */
    private String transactionId;

    /**
     * Represent the type
     */
    private Types type;

    /**
     * Construct
     *
     * @param transaction
     */
    protected CoinapultTransaction(Transaction.Json transaction) {

        this.address = transaction.address;
        this.completeTime = transaction.getCompleteTime();
        this.expiration = transaction.getExpiration();

        this.inCurrency = transaction.in.currency;

        /*
         * Convert the amount to Satoshis multiply for 1000
         */
        this.inAmount = transaction.in.amount.multiply(new BigDecimal(1000)).longValue();
        this.inExpected = transaction.in.expected.multiply(new BigDecimal(1000)).longValue();
        this.outAmount = transaction.out.amount.multiply(new BigDecimal(1000)).longValue();

        this.outCurrency = transaction.out.currency;

        /*
         * Convert the amount to Satoshis multiply for 1000
         */
        this.outExpected = transaction.out.expected.multiply(new BigDecimal(1000)).longValue();
        this.quoteAsk = transaction.quote.ask.multiply(new BigDecimal(1000)).longValue();
        this.quoteBid = transaction.quote.bid.multiply(new BigDecimal(1000)).longValue();

        this.state = transaction.state;
        this.timestamp = transaction.getTimestamp();
        this.transactionId = transaction.tid;
        this.type = Types.getType(transaction.type);

    }

    @Override
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public Timestamp getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(Timestamp completeTime) {
        this.completeTime = completeTime;
    }

    @Override
    public Timestamp getExpiration() {
        return expiration;
    }

    public void setExpiration(Timestamp expiration) {
        this.expiration = expiration;
    }

    @Override
    public Long getInAmount() {
        return inAmount;
    }

    public void setInAmount(Long inAmount) {
        this.inAmount = inAmount;
    }

    @Override
    public String getInCurrency() {
        return inCurrency;
    }

    public void setInCurrency(String inCurrency) {
        this.inCurrency = inCurrency;
    }

    @Override
    public Long getInExpected() {
        return inExpected;
    }

    public void setInExpected(Long inExpected) {
        this.inExpected = inExpected;
    }

    @Override
    public Long getOutAmount() {
        return outAmount;
    }

    public void setOutAmount(Long outAmount) {
        this.outAmount = outAmount;
    }

    @Override
    public String getOutCurrency() {
        return outCurrency;
    }

    public void setOutCurrency(String outCurrency) {
        this.outCurrency = outCurrency;
    }

    @Override
    public Long getOutExpected() {
        return outExpected;
    }

    public void setOutExpected(Long outExpected) {
        this.outExpected = outExpected;
    }

    @Override
    public Long getQuoteAsk() {
        return quoteAsk;
    }

    public void setQuoteAsk(Long quoteAsk) {
        this.quoteAsk = quoteAsk;
    }

    @Override
    public Long getQuoteBid() {
        return quoteBid;
    }

    public void setQuoteBid(Long quoteBid) {
        this.quoteBid = quoteBid;
    }

    @Override
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    @Override
    public String getType() {
        return type.getValue();
    }

    public void setType(Types type) {
        this.type = type;
    }
}
