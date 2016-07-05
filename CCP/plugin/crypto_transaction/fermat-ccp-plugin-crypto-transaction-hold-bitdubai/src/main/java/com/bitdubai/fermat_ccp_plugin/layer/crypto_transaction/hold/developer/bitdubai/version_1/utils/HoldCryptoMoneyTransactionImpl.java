package com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.hold.developer.bitdubai.version_1.utils;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_bch_api.layer.definition.crypto_fee.FeeOrigin;
import com.bitdubai.fermat_ccp_api.all_definition.enums.CryptoTransactionStatus;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.hold.interfaces.CryptoHoldTransaction;

import java.util.UUID;

/**
 * Created by franklin on 23/11/15.
 */
//TODO: Colocar BigDecimal
public class HoldCryptoMoneyTransactionImpl implements CryptoHoldTransaction {
    private CryptoTransactionStatus status;
    private long                    timestampAcknowledged;
    private long                    timestampConfirmedRejected;
    private UUID                    transactionId;
    private String                  publicKeyWallet;
    private String                  publicKeyActor;
    private String                  publicKeyPlugin;
    private float                   amount;
    private CryptoCurrency          currency;
    private String                  memo;
    private BlockchainNetworkType   blockchainNetworkType;
    private FeeOrigin feeOrigin;
    private long        fee;

    public HoldCryptoMoneyTransactionImpl(){

    }

    @Override
    public CryptoTransactionStatus getStatus() {
        return status;
    }

    @Override
    public void setStatus(CryptoTransactionStatus status) {
        this.status = status;
    }

    @Override
    public long getTimestampAcknowledged() {
        return timestampAcknowledged;
    }

    @Override
    public void setTimestampAcknowledged(long timestampAcknowledged) {
        this.timestampAcknowledged = timestampAcknowledged;
    }

    @Override
    public long getTimestampConfirmedRejected() {
        return timestampConfirmedRejected;
    }

    @Override
    public void setTimestampConfirmedRejected(long timestampConfirmedRejected) {
        this.timestampConfirmedRejected = timestampConfirmedRejected;
    }

    @Override
    public UUID getTransactionId() {
        return transactionId;
    }

    @Override
    public void setTransactionId(UUID transactionId) {
        this.transactionId = transactionId;
    }

    @Override
    public String getPublicKeyWallet() {
        return publicKeyWallet;
    }

    @Override
    public void setPublicKeyWallet(String publicKeyWallet) {
        this.publicKeyWallet = publicKeyWallet;
    }

    @Override
    public String getPublicKeyActor() {
        return  publicKeyActor;
    }

    @Override
    public void setPublicKeyActor(String publicKeyActor) {
        this.publicKeyActor = publicKeyActor;
    }

    @Override
    public String getPublicKeyPlugin() {
        return publicKeyPlugin;
    }

    @Override
    public void setPublicKeyPlugin(String publicKeyPlugin) {
        this.publicKeyPlugin = publicKeyPlugin;
    }

    @Override
    public float getAmount() {
        return amount;
    }

    @Override
    public void setAmount(float amount) {
        this.amount = amount;
    }

    @Override
    public CryptoCurrency getCurrency() {
        return currency;
    }

    @Override
    public void setCurrency(CryptoCurrency currency) {
        this.currency = currency;
    }

    @Override
    public String getMemo() {
        return memo;
    }

    @Override
    public void setMemo(String memo) {
        this.memo = memo;
    }

    /**
     * Returns the blockchainNetworkType of the transaction
     *
     * @return BlockchainNetworkType of the transaction
     */
    @Override
    public BlockchainNetworkType getBlockchainNetworkType() {
        return blockchainNetworkType;
    }

    @Override
    public void setBlockchainNetworkType(BlockchainNetworkType blockchainNetworkType) {
        this.blockchainNetworkType = blockchainNetworkType;
    }

    @Override
    public long getFee() {
        return fee;
    }

    @Override
    public void setFee(long fee) {
        this.fee = fee;
    }

    @Override
    public FeeOrigin getFeeOrigin() {
        return feeOrigin;
    }

    @Override
    public void setFeeOrigin(FeeOrigin feeOrigin) {
        this.feeOrigin = feeOrigin;

    }
}
