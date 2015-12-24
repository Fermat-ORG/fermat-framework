package com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;

import java.math.BigDecimal;

/**
 * This class represents the Contract Basic information persisted in Business Transactions plugins database.
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 08/12/15.
 */
public class BusinessTransactionRecord {

    private String brokerPublicKey;

    private String cbpWalletPublicKey;

    private String contractHash;

    private ContractTransactionStatus contractTransactionStatus;

    private CryptoAddress cryptoAddress;

    private long cryptoAmount;

    private CryptoStatus cryptoStatus;

    private String customerPublicKey;

    private long timestamp;

    private String transactionHash;

    private String transactionId;

    private String externalWalletPublicKey;

    private BigDecimal priceReference;

    //Offline fields
    private FiatCurrency currencyType;

    private long paymentAmount;

    private CurrencyType paymentType;

    //Getters

    public String getBrokerPublicKey() {
        return brokerPublicKey;
    }

    public String getCBPWalletPublicKey(){
        return cbpWalletPublicKey;
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

    public FiatCurrency getCurrencyType() {
        return currencyType;
    }

    public String getCustomerPublicKey() {
        return customerPublicKey;
    }

    public long getPaymentAmount() {
        return paymentAmount;
    }

    public CurrencyType getPaymentType() {
        return paymentType;
    }

    public BigDecimal getPriceReference() {
        return priceReference;
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

    public String getExternalWalletPublicKey() {
        return externalWalletPublicKey;
    }

    //Setters

    public void setBrokerPublicKey(String brokerPublicKey) {
        this.brokerPublicKey = brokerPublicKey;
    }

    public void setCBPWalletPublicKey(String cbpWalletPublicKey){
        this.cbpWalletPublicKey = cbpWalletPublicKey;
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

    public void setCurrencyType(FiatCurrency currencyType) {
        this.currencyType = currencyType;
    }

    public void setCustomerPublicKey(String customerPublicKey) {
        this.customerPublicKey = customerPublicKey;
    }

    public void setPaymentAmount(long paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public void setPaymentType(CurrencyType paymentType) {
        this.paymentType = paymentType;
    }

    public void setPriceReference(BigDecimal priceReference) {
        this.priceReference = priceReference;
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

    public void setExternalWalletPublicKey(String externalWalletPublicKey) {
        this.externalWalletPublicKey = externalWalletPublicKey;
    }

    @Override
    public String toString() {
        return "BusinessTransactionRecord{" +
                "brokerPublicKey='" + brokerPublicKey + '\'' +
                ", cbpWalletPublicKey='" + cbpWalletPublicKey + '\'' +
                ", contractHash='" + contractHash + '\'' +
                ", contractTransactionStatus=" + contractTransactionStatus +
                ", cryptoAddress=" + cryptoAddress +
                ", cryptoAmount=" + cryptoAmount +
                ", cryptoStatus=" + cryptoStatus +
                ", customerPublicKey='" + customerPublicKey + '\'' +
                ", timestamp=" + timestamp +
                ", transactionHash='" + transactionHash + '\'' +
                ", transactionId='" + transactionId + '\'' +
                ", externalWalletPublicKey='" + externalWalletPublicKey + '\'' +
                ", priceReference=" + priceReference +
                ", currencyType=" + currencyType +
                ", paymentAmount=" + paymentAmount +
                ", paymentType=" + paymentType +
                '}';
    }
}
