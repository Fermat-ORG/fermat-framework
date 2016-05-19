package com.bitdubai.fermat_cbp_plugin.layer.user_level_business_transaction.customer_broker_sale.developer.bitdubai.version_1.utils;

import com.bitdubai.fermat_cbp_api.layer.user_level_business_transaction.common.enums.TransactionStatus;
import com.bitdubai.fermat_cbp_api.layer.user_level_business_transaction.customer_broker_sale.interfaces.CustomerBrokerSale;

/**
 * Created by franklin on 15/12/15.
 */
public class CustomerBrokerSaleImpl implements CustomerBrokerSale {
    private String transactionId;
    private String contractTransactionId;
    private long   timestamp;
    private String purchaseStatus;
    private String contractStatus;
    private TransactionStatus transactionStatus;
    private String currencyType;
    private String transactionType;
    private String memo;

    public CustomerBrokerSaleImpl(String transactionId,
                                  String contractTransactionId,
                                  long timestamp,
                                  String purchaseStatus,
                                  String contractStatus,
                                  TransactionStatus transactionStatus,
                                  String currencyType,
                                  String transactionType,
                                  String memo){
        this.transactionId = transactionId;
        this.contractTransactionId = contractTransactionId;
        this.timestamp             = timestamp;
        this.purchaseStatus        = purchaseStatus;
        this.contractStatus        = contractStatus;
        this.transactionStatus     = transactionStatus;
        this.currencyType          = currencyType;
        this.transactionType       = transactionType;
        this.memo                  = memo;
    }

    public String getTransactionId() {
        return transactionId;
    }

    @Override
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getContractTransactionId() {
        return contractTransactionId;
    }

    @Override
    public void setContractTransactionId(String contractTransactionId) {
        this.contractTransactionId = contractTransactionId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getPurchaseStatus() {
        return purchaseStatus;
    }

    public String getContractStatus() {
        return contractStatus;
    }

    @Override
    public void setContractStatus(String contractStatus) {
        this.contractStatus = contractStatus;
    }

    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }

    @Override
    public void setTransactionStatus(TransactionStatus transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public String getCurrencyType() {
        return currencyType;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public String getMemo() {
        return memo;
    }
}
