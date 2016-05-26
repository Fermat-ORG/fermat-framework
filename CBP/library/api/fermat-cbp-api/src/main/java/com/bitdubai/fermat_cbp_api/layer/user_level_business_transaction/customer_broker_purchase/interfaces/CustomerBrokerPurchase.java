package com.bitdubai.fermat_cbp_api.layer.user_level_business_transaction.customer_broker_purchase.interfaces;

import com.bitdubai.fermat_cbp_api.layer.user_level_business_transaction.common.enums.TransactionStatus;


/**
 * Created by franklin on 13/12/15.
 */
public interface CustomerBrokerPurchase {
    /**
     * The method <code>getTransactionId</code> returns the transaction id of the customer broker purchase
     *
     * @return an String of the transaction id
     */
    String getTransactionId();

    /**
     * The method <code>setTransactionId</code> sets the transaction id of the customer broker purchase
     *
     * @param transactionId
     */
    void setTransactionId(String transactionId);

    /**
     * The method <code>getContractTransactionId</code> returns the contract transaction id of the customer broker purchase
     *
     * @return an String of the contract transaction id
     */
    String getContractTransactionId();

    /**
     * The method <code>setContractTransactionId</code> sets the contract transaction id of the customer broker purchase
     *
     * @param contractTransactionId
     */
    void setContractTransactionId(String contractTransactionId);

    /**
     * The method <code>getTimestamp</code> returns the contract timestamp of the customer broker purchase
     *
     * @return a long of the timestamp
     */
    long getTimestamp();

    /**
     * The method <code>getPurchaseStatus</code> returns the purchase status of the customer broker purchase
     *
     * @return an String of the purchase status
     */
    String getPurchaseStatus();

    /**
     * The method <code>getContractStatus</code> returns the contract status of the customer broker purchase
     *
     * @return an String of the contract status
     */
    String getContractStatus();

    /**
     * Set the contract status
     *
     * @param contractStatus the contract status code
     */
    void setContractStatus(String contractStatus);

    /**
     * The method <code>getTransactionStatus</code> returns the transaction status of the customer broker purchase
     *
     * @return a TransactionStatus
     */
    TransactionStatus getTransactionStatus();

    /**
     * The method <code>setTransactionStatus</code> sets the transaction status of the customer broker purchase
     *
     * @param transactionStatus
     */
    void setTransactionStatus(TransactionStatus transactionStatus);

    /**
     * The method <code>getMoneyType</code> returns the currency type of the customer broker purchase
     *
     * @return an String of the currency type
     */
    String getCurrencyType();

    /**
     * The method <code>getTransactionType</code> returns the transaction type of the customer broker purchase
     *
     * @return an String of the transaction type
     */
    String getTransactionType();

    /**
     * The method <code>getMemo</code> returns the memo of the customer broker purchase
     *
     * @return an String of the memo
     */
    String getMemo();
}
