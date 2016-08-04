package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_offline_payment.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 08/12/15.
 */
public class CustomerOfflinePaymentRecord {

    private String brokerPublicKey;

    private String contractHash;

    private ContractTransactionStatus contractTransactionStatus;

    private String customerPublicKey;

    private long timestamp;

    private String transactionHash;

    private String transactionId;


    public String getBrokerPublicKey() {
        return brokerPublicKey;
    }

    public String getContractHash() {
        return contractHash;
    }

    public ContractTransactionStatus getContractTransactionStatus() {
        return contractTransactionStatus;
    }

    public String getCustomerPublicKey() {
        return customerPublicKey;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getTransactionHash() {
        return transactionHash;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setBrokerPublicKey(String brokerPublicKey) {
        this.brokerPublicKey = brokerPublicKey;
    }

    public void setContractHash(String contractHash) {
        this.contractHash = contractHash;
    }

    public void setContractTransactionStatus(ContractTransactionStatus contractTransactionStatus) {
        this.contractTransactionStatus = contractTransactionStatus;
    }

    public void setCustomerPublicKey(String customerPublicKey) {
        this.customerPublicKey = customerPublicKey;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setTransactionHash(String transactionHash) {
        this.transactionHash = transactionHash;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    @Override
    public String toString() {
        return "CustomerOfflinePaymentRecord{" + "brokerPublicKey='" + brokerPublicKey + '\'' + ", contractHash='" + contractHash + '\'' + ", contractTransactionStatus=" + contractTransactionStatus + ", customerPublicKey='" + customerPublicKey + '\'' + ", timestamp=" + timestamp + ", transactionHash='" + transactionHash + '\'' + ", transactionId='" + transactionId + '\'' + '}';
    }
}
