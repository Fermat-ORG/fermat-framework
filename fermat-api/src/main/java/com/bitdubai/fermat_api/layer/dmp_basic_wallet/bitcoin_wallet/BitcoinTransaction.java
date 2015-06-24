package com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet;

import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.enums.TransactionState;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.enums.TransactionType;

import java.util.UUID;

/**
 * Created by eze on 2015.06.17..
 */
public class BitcoinTransaction {

    private String transactionHash;

    private CryptoAddress addressFrom;

    private CryptoAddress addressTo;

    private long amount;

    private TransactionType type;

    private TransactionState state;

    private long timestamp;

    private String memo;

    public CryptoAddress getAddressFrom() {
        return addressFrom;
    }

    public void setAddressFrom(CryptoAddress addressFrom) {
        this.addressFrom = addressFrom;
    }

    public CryptoAddress getAddressTo() {
        return addressTo;
    }

    public void setAddressTo(CryptoAddress addressTo) {
        this.addressTo = addressTo;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public TransactionState getState() {
        return state;
    }

    public void setState(TransactionState state) {
        this.state = state;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getTramsactionHash() {
        return transactionHash;
    }

    public void setTramsactionHash(String tramsactionHash) {
        this.transactionHash = tramsactionHash;
    }
}
