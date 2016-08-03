package com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;

/**
 * Created by eze on 11/06/15.
 */

public class CryptoTransaction {
    private String transactionHash;
    private String blockHash;
    private BlockchainNetworkType blockchainNetworkType;
    private CryptoAddress addressFrom;
    private CryptoAddress addressTo;
    private CryptoCurrency cryptoCurrency;
    private long btcAmount;
    private long fee;
    private long cryptoAmount; // btcAmount + fee
    private CryptoStatus cryptoStatus;
    private String op_Return;
    private CryptoTransactionType cryptoTransactionType;
    private int blockDepth;


    /**
     * Overloaded constructor
     *
     * @param transactionHash
     * @param blockchainNetworkType
     * @param addressFrom
     * @param addressTo
     * @param cryptoCurrency
     * @param cryptoAmount
     * @param cryptoStatus
     */
    public CryptoTransaction(String transactionHash,
                             BlockchainNetworkType blockchainNetworkType,
                             CryptoAddress addressFrom,
                             CryptoAddress addressTo,
                             CryptoCurrency cryptoCurrency,
                             long cryptoAmount,
                             CryptoStatus cryptoStatus) {
        this.transactionHash = transactionHash;
        this.blockchainNetworkType = blockchainNetworkType;
        this.addressFrom = addressFrom;
        this.addressTo = addressTo;
        this.cryptoCurrency = cryptoCurrency;
        this.cryptoAmount = cryptoAmount;
        this.cryptoStatus = cryptoStatus;
    }


    /**
     * Default constructor.
     */
    public CryptoTransaction() {
    }

    /**
     * Getters
     */

    public String getTransactionHash() {
        return transactionHash;
    }

    public String getBlockHash() {
        return blockHash;
    }

    public BlockchainNetworkType getBlockchainNetworkType() {
        return blockchainNetworkType;
    }

    public CryptoAddress getAddressFrom() {
        return addressFrom;
    }

    public CryptoAddress getAddressTo() {
        return addressTo;
    }

    public CryptoCurrency getCryptoCurrency() {
        return cryptoCurrency;
    }

    public long getCryptoAmount() {
        return cryptoAmount;
    }

    public CryptoStatus getCryptoStatus() {
        return cryptoStatus;
    }

    public String getOp_Return() {
        return op_Return;
    }

    /**
     * setters
     */

    public void setOp_Return(String op_Return) {
        this.op_Return = op_Return;
    }

    public void setTransactionHash(String transactionHash) {
        this.transactionHash = transactionHash;
    }

    public void setBlockHash(String blockHash) {
        this.blockHash = blockHash;
    }

    public void setBlockchainNetworkType(BlockchainNetworkType blockchainNetworkType) {
        this.blockchainNetworkType = blockchainNetworkType;
    }

    public void setAddressFrom(CryptoAddress addressFrom) {
        this.addressFrom = addressFrom;
    }

    public void setAddressTo(CryptoAddress addressTo) {
        this.addressTo = addressTo;
    }

    public void setCryptoCurrency(CryptoCurrency cryptoCurrency) {
        this.cryptoCurrency = cryptoCurrency;
    }

    public void setCryptoAmount(long cryptoAmount) {
        this.cryptoAmount = cryptoAmount;
    }

    public void setCryptoStatus(CryptoStatus cryptoStatus) {
        this.cryptoStatus = cryptoStatus;
    }

    public CryptoTransactionType getCryptoTransactionType() {
        return cryptoTransactionType;
    }

    public void setCryptoTransactionType(CryptoTransactionType cryptoTransactionType) {
        this.cryptoTransactionType = cryptoTransactionType;
    }

    public int getBlockDepth() {
        return blockDepth;
    }

    public void setBlockDepth(int blockDepth) {
        this.blockDepth = blockDepth;
    }

    public long getFee() {
        return fee;
    }

    public void setFee(long fee) {
        this.fee = fee;
    }


    public long getBtcAmount() {
        return btcAmount;
    }

    public void setBtcAmount(long btcAmount) {
        this.btcAmount = btcAmount;
    }
}
