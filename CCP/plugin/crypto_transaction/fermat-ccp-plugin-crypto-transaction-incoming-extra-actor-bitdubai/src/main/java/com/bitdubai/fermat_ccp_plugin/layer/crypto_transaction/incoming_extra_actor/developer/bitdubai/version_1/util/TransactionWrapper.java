package com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.util;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_bch_api.layer.definition.crypto_fee.FeeOrigin;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.crypto_wallet.interfaces.CryptoWalletTransactionRecord;

import java.util.UUID;

/**
 * Created by eze on 2015.06.25..
 */
public class TransactionWrapper implements CryptoWalletTransactionRecord {

    /*
     * CryptoWalletTransactionRecord Interface member variables
     */
    private UUID transactionId;

    private UUID requestId;

    private String actorFromPublicKey;

    private String actorToPublicKey;

    private Actors actorFromType;

    private Actors actorToType;

    private String transactionHash;

    private CryptoAddress addressFrom;

    private CryptoAddress addressTo;

    private long amount;

    private long timestamp;

    private String memo;

    private FeeOrigin feeOrigin;

    private long        fee;

    private BlockchainNetworkType blockchainNetworkType;

    private CryptoCurrency cryptoCurrency;

    private  long Total;

    @Override
    public CryptoAddress getAddressFrom() {
        return addressFrom;
    }

    public void setAddressFrom(CryptoAddress addressFrom) {
        this.addressFrom = addressFrom;
    }

    @Override
    public UUID getTransactionId() {
        return this.transactionId;
    }

    @Override
    public UUID getRequestId() {
        return requestId;
    }


    public void setIdTransaction(UUID id) {
        this.transactionId = id;
    }

    public void setIdRequest(UUID id) {
        this.requestId = id;
    }

    @Override
    public CryptoAddress getAddressTo() {
        return addressTo;
    }

    public void setAddressTo(CryptoAddress addressTo) {
        this.addressTo = addressTo;
    }

    @Override
    public long getAmount() {
        return amount;
    }

    @Override
    public long getTotal() {
        return this.Total;
    }

    public void setTotal(long Total) {
        this.Total = Total;
    }


    public void setAmount(long amount) {
        this.amount = amount;
    }

    @Override
    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public String getTransactionHash() {
        return transactionHash;
    }

    @Override
    public BlockchainNetworkType getBlockchainNetworkType() {return blockchainNetworkType;}

    public void setCryptoCurrency(CryptoCurrency cryptoCurrency) {
        this.cryptoCurrency = cryptoCurrency;
    }

    @Override
    public CryptoCurrency getCryptoCurrency() {
        return this.cryptoCurrency;
    }

    @Override
    public FeeOrigin getFeeOrigin() {
        return this.feeOrigin;
    }

    @Override
    public long getFee() {
        return this.fee;
    }

    public void  setFee(long fee) {
         this.fee = fee;
    }

    public void  setFeeOrigin(FeeOrigin feeOrigin) {
        this.feeOrigin = feeOrigin;
    }

    public void setBlockchainNetworkType(BlockchainNetworkType blockchainNetworkType) {
        this.blockchainNetworkType = blockchainNetworkType;
    }

    public String getActorFromPublicKey() {
        return actorFromPublicKey;
    }

    public void setActorFromPublicKey(String actorFromPublicKey) {
        this.actorFromPublicKey = actorFromPublicKey;
    }

    public String getActorToPublicKey() {
        return actorToPublicKey;
    }

    public void setActorToPublicKey(String actorToPublicKey) {
        this.actorToPublicKey = actorToPublicKey;
    }

    @Override
    public Actors getActorToType() {return this.actorToType; }

    public void setActorToType(Actors actorToType) {this.actorToType = actorToType;}

    @Override
    public Actors getActorFromType() { return this.actorFromType;}

    public void setActorFromType(Actors actorFromType){ this.actorFromType = actorFromType; }

    public void setTransactionHash(String tramsactionHash) {
        this.transactionHash = tramsactionHash;
    }
}
