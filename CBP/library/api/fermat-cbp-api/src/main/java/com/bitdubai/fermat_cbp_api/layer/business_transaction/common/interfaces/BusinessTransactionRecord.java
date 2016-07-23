package com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_bch_api.layer.definition.crypto_fee.BitcoinFee;
import com.bitdubai.fermat_bch_api.layer.definition.crypto_fee.FeeOrigin;
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

    private CryptoCurrency cryptoCurrency;

    private String customerPublicKey;

    private long timestamp;

    private String transactionHash;

    private String transactionId;

    private String externalWalletPublicKey;

    private BigDecimal priceReference;

    private BlockchainNetworkType blockchainNetworkType;

    private UUID externalTransactionId;

    private String customerAlias;

    private long fee;

    private FeeOrigin feeOrigin;

    //Offline fields
    private FiatCurrency fiatCurrency;

    private long paymentAmount;

    private MoneyType paymentType;

    private String actorPublicKey;

    //Getters

    /**
     * @return the broker public key.
     */
    public String getBrokerPublicKey() {
        return brokerPublicKey;
    }

    /**
     * @return the CBP Wallet Public Key.
     */
    public String getCBPWalletPublicKey() {
        return cbpWalletPublicKey;
    }

    /**
     * @return the contract hash/Id.
     */
    public String getContractHash() {
        return contractHash;
    }

    /**
     * @return the ContractTransactionStatus.
     */
    public ContractTransactionStatus getContractTransactionStatus() {
        return contractTransactionStatus;
    }

    /**
     * @return the Crypto Address.
     */
    public CryptoAddress getCryptoAddress() {
        return cryptoAddress;
    }

    /**
     * @return the crypto amount.
     */
    public long getCryptoAmount() {
        return cryptoAmount;
    }

    /**
     * @return the CryptoStatus.
     */
    public CryptoStatus getCryptoStatus() {
        return cryptoStatus;
    }

    /**
     * @return the FiatCurrency.
     */
    public FiatCurrency getFiatCurrency() {
        return fiatCurrency;
    }

    /**
     * @return the customer public key.
     */
    public String getCustomerPublicKey() {
        return customerPublicKey;
    }

    /**
     * @return the payment amount
     */
    public long getPaymentAmount() {
        return paymentAmount;
    }

    /**
     * @return the MoneyType.
     */
    public MoneyType getPaymentType() {
        return paymentType;
    }

    /**
     * @return the reference price.
     */
    public BigDecimal getPriceReference() {
        return priceReference;
    }

    /**
     * @return the creation timestamp.
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * @return the transaction hash
     */
    public String getTransactionHash() {
        return transactionHash;
    }

    /**
     * @return the transaction id.
     */
    public String getTransactionId() {
        return transactionId;
    }

    /**
     * @return the external wallet public key. We can define external as a wallet from another platform different from CBP.
     */
    public String getExternalWalletPublicKey() {
        return externalWalletPublicKey;
    }

    /**
     * @return the external transaction id. We can define external as a Transaction Id from another platform different from CBP.
     */
    public UUID getExternalTransactionId() {
        return externalTransactionId;
    }

    //Setters

    /**
     * This method sets the broker public key.
     *
     * @param brokerPublicKey the broker public key.
     */
    public void setBrokerPublicKey(String brokerPublicKey) {
        this.brokerPublicKey = brokerPublicKey;
    }

    /**
     * This method sets the cbp wallet public key.
     *
     * @param cbpWalletPublicKey the cbp wallet public key.
     */
    public void setCBPWalletPublicKey(String cbpWalletPublicKey) {
        this.cbpWalletPublicKey = cbpWalletPublicKey;
    }

    /**
     * This method sets the contract hash/Id.
     *
     * @param contractHash the contract hash/Id.
     */
    public void setContractHash(String contractHash) {
        this.contractHash = contractHash;
    }

    /**
     * This method sets the ContractTransactionStatus.
     *
     * @param contractTransactionStatus the ContractTransactionStatus.
     */
    public void setContractTransactionStatus(ContractTransactionStatus contractTransactionStatus) {
        this.contractTransactionStatus = contractTransactionStatus;
    }

    /**
     * This method sets the CryptoAddress.
     *
     * @param cryptoAddress the CryptoAddress.
     */
    public void setCryptoAddress(CryptoAddress cryptoAddress) {
        this.cryptoAddress = cryptoAddress;
    }

    /**
     * This method sets the crypto amount.
     *
     * @param cryptoAmount the crypto amount.
     */
    public void setCryptoAmount(long cryptoAmount) {
        this.cryptoAmount = cryptoAmount;
    }

    /**
     * This method sets the CryptoStatus.
     *
     * @param cryptoStatus the CryptoStatus.
     */
    public void setCryptoStatus(CryptoStatus cryptoStatus) {
        this.cryptoStatus = cryptoStatus;
    }

    /**
     * This method sets the currency type.
     *
     * @param currencyType the currency type.
     */
    public void setFiatCurrency(FiatCurrency currencyType) {
        this.fiatCurrency = currencyType;
    }

    /**
     * This method sets the customer public key.
     *
     * @param customerPublicKey the customer public key.
     */
    public void setCustomerPublicKey(String customerPublicKey) {
        this.customerPublicKey = customerPublicKey;
    }

    /**
     * This method sets the payment amount.
     *
     * @param paymentAmount the payment amount.
     */
    public void setPaymentAmount(long paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    /**
     * This method sets the payment type.
     *
     * @param paymentType the payment type.
     */
    public void setPaymentType(MoneyType paymentType) {
        this.paymentType = paymentType;
    }

    /**
     * This method sets the reference price.
     *
     * @param priceReference the reference price.
     */
    public void setPriceReference(BigDecimal priceReference) {
        this.priceReference = priceReference;
    }

    /**
     * This method sets the transaction timestamp.
     *
     * @param timestamp the transaction timestamp.
     */
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * This method sets the transaction hash
     *
     * @param transactionHash the transaction hash
     */
    public void setTransactionHash(String transactionHash) {
        this.transactionHash = transactionHash;
    }

    /**
     * This method set the transaction Id.
     *
     * @param transactionId transaction Id.
     */
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    /**
     * This method sets the external wallet public key. We can define external as a wallet from another platform different from CBP.
     *
     * @param externalWalletPublicKey the external wallet public key.
     */
    public void setExternalWalletPublicKey(String externalWalletPublicKey) {
        this.externalWalletPublicKey = externalWalletPublicKey;
    }

    /**
     * This method sets the external transaction id. We can define external as a Transaction Id from another platform different from CBP.
     *
     * @param externalTransactionId the external transaction id.
     */
    public void setExternalTransactionId(UUID externalTransactionId) {
        this.externalTransactionId = externalTransactionId;
    }

    /**
     * @return the actor public key.
     */
    public String getActorPublicKey() {
        return actorPublicKey;
    }

    /**
     * This method sets the actor public key.
     *
     * @param actorPublicKey the actor public key.
     */
    public void setActorPublicKey(String actorPublicKey) {
        this.actorPublicKey = actorPublicKey;
    }

    /**
     * @return the BlockchainNetworkType.
     */
    public BlockchainNetworkType getBlockchainNetworkType() {
        return blockchainNetworkType;
    }

    /**
     * This method sets the BlockchainNetworkType.
     *
     * @param blockchainNetworkType the Blockchain Network Type.
     */
    public void setBlockchainNetworkType(BlockchainNetworkType blockchainNetworkType) {
        this.blockchainNetworkType = blockchainNetworkType;
    }

    /**
     * @return returns the Customer Alias.
     */
    public String getCustomerAlias() {
        return customerAlias;
    }

    /**
     * This method sets the customer Alias.
     *
     * @param customerAlias the customer Alias.
     */
    public void setCustomerAlias(String customerAlias) {
        this.customerAlias = customerAlias;
    }

    /**
     * This method returns the transaction fee
     *
     * @return
     */
    public long getFee() {
        long minimalFee = BitcoinFee.SLOW.getFee();
        if (this.fee < minimalFee) {
            return minimalFee;
        }
        return fee;
    }

    /**
     * This method sets the transaction fee
     *
     * @param fee
     */
    public void setFee(long fee) {
        this.fee = fee;
    }

    /**
     * This method returns the transaction origin fee
     *
     * @return
     */
    public FeeOrigin getFeeOrigin() {
        if (this.feeOrigin == null) {
            return FeeOrigin.SUBSTRACT_FEE_FROM_AMOUNT;
        }
        return this.feeOrigin;
    }

    /**
     * This method sets the transaction
     *
     * @param feeOrigin
     */
    public void setFeeOrigin(FeeOrigin feeOrigin) {
        this.feeOrigin = feeOrigin;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("BusinessTransactionRecord{")
                .append("brokerPublicKey='").append(brokerPublicKey)
                .append('\'')
                .append(", cbpWalletPublicKey='").append(cbpWalletPublicKey)
                .append('\'')
                .append(", contractHash='").append(contractHash)
                .append('\'')
                .append(", contractTransactionStatus=").append(contractTransactionStatus)
                .append(", cryptoAddress=").append(cryptoAddress)
                .append(", cryptoAmount=").append(cryptoAmount)
                .append(", cryptoStatus=").append(cryptoStatus)
                .append(", cryptoCurrency=").append(cryptoCurrency)
                .append(", customerPublicKey='").append(customerPublicKey)
                .append('\'')
                .append(", timestamp=").append(timestamp)
                .append(", transactionHash='").append(transactionHash)
                .append('\'')
                .append(", transactionId='").append(transactionId)
                .append('\'')
                .append(", externalWalletPublicKey='").append(externalWalletPublicKey)
                .append('\'')
                .append(", priceReference=").append(priceReference)
                .append(", blockchainNetworkType=").append(blockchainNetworkType)
                .append(", externalTransactionId=").append(externalTransactionId)
                .append(", customerAlias='").append(customerAlias)
                .append('\'')
                .append(", fee=").append(fee)
                .append(", feeOrigin=").append(feeOrigin)
                .append(", fiatCurrency=").append(fiatCurrency)
                .append(", paymentAmount=").append(paymentAmount)
                .append(", paymentType=").append(paymentType)
                .append(", actorPublicKey='").append(actorPublicKey)
                .append('\'')
                .append('}').toString();
    }

    public CryptoCurrency getCryptoCurrency() {
        return cryptoCurrency;
    }

    public void setCryptoCurrency(CryptoCurrency cryptoCurrency) {
        this.cryptoCurrency = cryptoCurrency;
    }
}
