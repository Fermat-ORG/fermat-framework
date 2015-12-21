package com.bitdubai.fermat_cbp_api.layer.user_level_business_transaction.customer_broker_purchase.interfaces;

import com.bitdubai.fermat_cbp_api.layer.user_level_business_transaction.common.enums.TransactionStatus;

/**
 * Created by franklin on 13/12/15.
 */
public interface CustomerBrokerPurchase {
    //TODO: Documentar
    String getTransactionId();
    void setTransactionId(String transactionId);
    String getContractTransactionId();
    void setContractTransactionId(String contractTransactionId);
    long   getTimestamp();
    String getPurchaseStatus();
    String getContractStatus();
    TransactionStatus getTransactionStatus();
    void setTransactionStatus(TransactionStatus transactionStatus);
    String getCurrencyType();
    String getTransactionType();
    String getMemo();
}
