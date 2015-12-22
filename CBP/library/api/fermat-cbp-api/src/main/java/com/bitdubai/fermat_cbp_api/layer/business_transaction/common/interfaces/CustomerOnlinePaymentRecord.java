package com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 08/12/15.
 */
public class CustomerOnlinePaymentRecord {

    private String brokerPublicKey;

    private String contractHash;

    private ContractTransactionStatus contractTransactionStatus;

    private CryptoAddress cryptoAddress;

    private long cryptoAmount;

    private CryptoStatus cryptoStatus;

    private String customerPublicKey;

    private long timestamp;

    private String transactionHash;

    private String transactionId;

    private String walletPublicKey;

    //Offline fields
    private long paymentAmount;

    private CurrencyType paymentType;

    private FiatCurrency currencyType;

    public long getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(long paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public CurrencyType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(CurrencyType paymentType) {
        this.paymentType = paymentType;
    }

    public FiatCurrency getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(FiatCurrency currencyType) {
        this.currencyType = currencyType;
    }

    public String getBrokerPublicKey() {
        return brokerPublicKey;
    }

    public String getContractHash() {
        return contractHash;
    }

    public ContractTransactionStatus getContractTransactionStatus() {
        return contractTransactionStatus;
    }

    public CryptoAddress getCryptoAddress() {
        return cryptoAddress;
    }

    public long getCryptoAmount() {
        return cryptoAmount;
    }

    public CryptoStatus getCryptoStatus() {
        return cryptoStatus;
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

    public String getWalletPublicKey() {
        return walletPublicKey;
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

    public void setCryptoAddress(CryptoAddress cryptoAddress) {
        this.cryptoAddress = cryptoAddress;
    }

    public void setCryptoAmount(long cryptoAmount) {
        this.cryptoAmount = cryptoAmount;
    }

    public void setCryptoStatus(CryptoStatus cryptoStatus) {
        this.cryptoStatus = cryptoStatus;
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

    public void setWalletPublicKey(String walletPublicKey) {
        this.walletPublicKey = walletPublicKey;
    }

    @Override
    public String toString() {
        return "CustomerOnlinePaymentRecord{" +
                "brokerPublicKey='" + brokerPublicKey + '\'' +
                ", contractHash='" + contractHash + '\'' +
                ", contractTransactionStatus=" + contractTransactionStatus +
                ", cryptoAddress=" + cryptoAddress +
                ", cryptoAmount=" + cryptoAmount +
                ", cryptoStatus=" + cryptoStatus +
                ", customerPublicKey='" + customerPublicKey + '\'' +
                ", timestamp=" + timestamp +
                ", transactionHash='" + transactionHash + '\'' +
                ", transactionId='" + transactionId + '\'' +
                ", walletPublicKey='" + walletPublicKey + '\'' +
                ", paymentAmount=" + paymentAmount +
                ", paymentType=" + paymentType +
                ", currencyType=" + currencyType +
                '}';
    }
}
