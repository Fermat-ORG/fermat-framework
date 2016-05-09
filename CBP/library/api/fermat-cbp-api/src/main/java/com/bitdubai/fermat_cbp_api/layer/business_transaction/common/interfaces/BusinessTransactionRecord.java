package com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType;

import java.math.BigDecimal;
import java.util.UUID;

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

    private BlockchainNetworkType blockchainNetworkType;

    private UUID externalTransactionId;

    private String customerAlias;

    //Offline fields
    private FiatCurrency fiatCurrency;

    private long paymentAmount;

    private MoneyType paymentType;

    private String actorPublicKey;

    //Getters

    /**
     * This method returns the broker public key.
     * @return
     */
    public String getBrokerPublicKey() {
        return brokerPublicKey;
    }

    /**
     * This method returns the CBP Wallet Public Key.
     * @return
     */
    public String getCBPWalletPublicKey(){
        return cbpWalletPublicKey;
    }

    /**
     * This method returns the contract hash/Id.
     * @return
     */
    public String getContractHash() {
        return contractHash;
    }

    /**
     * This method returns the ContractTransactionStatus.
     * @return
     */
    public ContractTransactionStatus getContractTransactionStatus() {
        return contractTransactionStatus;
    }

    /**
     * This method returns the Crypto Address.
     * @return
     */
    public CryptoAddress getCryptoAddress() {
        return cryptoAddress;
    }

    /**
     * This method returns the crypto amount.
     * @return
     */
    public long getCryptoAmount() {
        return cryptoAmount;
    }

    /**
     * This method return the CryptoStatus.
     * @return
     */
    public CryptoStatus getCryptoStatus() {
        return cryptoStatus;
    }

    /**
     * This method returns the FiatCurrency.
     * @return
     */
    public FiatCurrency getFiatCurrency() {
        return fiatCurrency;
    }

    /**
     * This method returns the customer public key.
     * @return
     */
    public String getCustomerPublicKey() {
        return customerPublicKey;
    }

    /**
     * This method returns the payment amount
     * @return
     */
    public long getPaymentAmount() {
        return paymentAmount;
    }

    /**
     * This method returns the MoneyType.
     * @return
     */
    public MoneyType getPaymentType() {
        return paymentType;
    }

    /**
     * This method returns the reference price.
     * @return
     */
    public BigDecimal getPriceReference() {
        return priceReference;
    }

    /**
     * This method returns the creation timestamp.
     * @return
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * This method set the transaction hash
     * @return
     */
    public String getTransactionHash() {
        return transactionHash;
    }

    /**
     * This method returns the transaction id.
     * @return
     */
    public String getTransactionId() {
        return transactionId;
    }

    /**
     * This method returns the external wallet public key.
     * We can define external as a wallet from another platform different from CBP.
     * @return
     */
    public String getExternalWalletPublicKey() {
        return externalWalletPublicKey;
    }

    /**
     * This method returns the external transaction id.
     * We can define external as a Transaction Id from another platform different from CBP.
     * @return
     */
    public UUID getExternalTransactionId() {
        return externalTransactionId;
    }

    //Setters

    /**
     * This method sets the broker public key.
     * @param brokerPublicKey
     */
    public void setBrokerPublicKey(String brokerPublicKey) {
        this.brokerPublicKey = brokerPublicKey;
    }

    /**
     * This method sets the cbp wallet public key.
     * @param cbpWalletPublicKey
     */
    public void setCBPWalletPublicKey(String cbpWalletPublicKey){
        this.cbpWalletPublicKey = cbpWalletPublicKey;
    }

    /**
     * This method sets the contract hash/Id.
     * @param contractHash
     */
    public void setContractHash(String contractHash) {
        this.contractHash = contractHash;
    }

    /**
     * This method sets the ContractTransactionStatus.
     * @param contractTransactionStatus
     */
    public void setContractTransactionStatus(ContractTransactionStatus contractTransactionStatus) {
        this.contractTransactionStatus = contractTransactionStatus;
    }

    /**
     * This method sets the CryptoAddress.
     * @param cryptoAddress
     */
    public void setCryptoAddress(CryptoAddress cryptoAddress) {
        this.cryptoAddress = cryptoAddress;
    }

    /**
     * This method sets the crypto amount.
     * @param cryptoAmount
     */
    public void setCryptoAmount(long cryptoAmount) {
        this.cryptoAmount = cryptoAmount;
    }

    /**
     * This method sets the CryptoStatus.
     * @param cryptoStatus
     */
    public void setCryptoStatus(CryptoStatus cryptoStatus) {
        this.cryptoStatus = cryptoStatus;
    }

    /**
     * This method sets the currency type.
     * @param currencyType
     */
    public void setFiatCurrency(FiatCurrency currencyType) {
        this.fiatCurrency = currencyType;
    }

    /**
     * This method sets the customer public key.
     * @param customerPublicKey
     */
    public void setCustomerPublicKey(String customerPublicKey) {
        this.customerPublicKey = customerPublicKey;
    }

    /**
     * This method sets the payment amount.
     * @param paymentAmount
     */
    public void setPaymentAmount(long paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    /**
     * This method sets the payment type.
     * @param paymentType
     */
    public void setPaymentType(MoneyType paymentType) {
        this.paymentType = paymentType;
    }

    /**
     * This method sets the reference price.
     * @param priceReference
     */
    public void setPriceReference(BigDecimal priceReference) {
        this.priceReference = priceReference;
    }

    /**
     * This method sets the transaction timestamp.
     * @param timestamp
     */
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * This method sets the transaction hash
     * @param transactionHash
     */
    public void setTransactionHash(String transactionHash) {
        this.transactionHash = transactionHash;
    }

    /**
     * This method set the transaction Id.
     * @param transactionId
     */
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    /**
     * This method sets the external wallet public key.
     * We can define external as a wallet from another platform different from CBP.
     * @param externalWalletPublicKey
     */
    public void setExternalWalletPublicKey(String externalWalletPublicKey) {
        this.externalWalletPublicKey = externalWalletPublicKey;
    }

    /**
     * This method sets the external transaction id.
     * We can define external as a Transaction Id from another platform different from CBP.
     * @param externalTransactionId
     */
    public void setExternalTransactionId(UUID externalTransactionId) {
        this.externalTransactionId = externalTransactionId;
    }

    /**
     * This method returns the actor public key.
     * @return
     */
    public String getActorPublicKey() {
        return actorPublicKey;
    }

    /**
     * This method sets the actor public key.
     * @param actorPublicKey
     */
    public void setActorPublicKey(String actorPublicKey) {
        this.actorPublicKey = actorPublicKey;
    }

    /**
     * This method returns the BlockchainNetworkType.
     * @return
     */
    public BlockchainNetworkType getBlockchainNetworkType() {
        return blockchainNetworkType;
    }

    /**
     * This method sets the BlockchainNetworkType.
     * @param blockchainNetworkType
     */
    public void setBlockchainNetworkType(BlockchainNetworkType blockchainNetworkType) {
        this.blockchainNetworkType = blockchainNetworkType;
    }
    /**
     * This method returns the customerAlias.
     * @param
     */
    public String getCustomerAlias() {
        return customerAlias;
    }
    /**
     * This method sets the customerAlias.
     * @param customerAlias
     */
    public void setCustomerAlias(String customerAlias) {
        this.customerAlias = customerAlias;
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
                ", blockchainNetworkType=" + blockchainNetworkType +
                ", externalTransactionId=" + externalTransactionId +
                ", customerAlias=" + customerAlias +
                ", fiatCurrency=" + fiatCurrency +
                ", paymentAmount=" + paymentAmount +
                ", paymentType=" + paymentType +
                ", actorPublicKey='" + actorPublicKey + '\'' +
                '}';
    }
}
