package com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.util;

import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.enums.BalanceType;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletTransactionRecord;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.enums.TransactionState;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.enums.TransactionType;

import java.util.UUID;

/**
 * Created by eze on 2015.06.25..
 */
public class TransactionWrapper implements BitcoinWalletTransactionRecord {

    /*
     * BitcoinWalletTransactionRecord Interface member variables
     */
    private String transactionHash;

    private CryptoAddress addressFrom;

    private CryptoAddress addressTo;

    private long amount;

    private TransactionType type;

    private TransactionState state;

    private long timestamp;

    private String memo;

    private UUID id;

    private BalanceType balanceType;

    /*
     * BitcoinWalletTransactionRecord Interface method implementation
     */
    @Override
    public CryptoAddress getAddressFrom() {
        return addressFrom;
    }

    @Override
    public void setAddressFrom(CryptoAddress addressFrom) {
        this.addressFrom = addressFrom;
    }

    @Override
    public CryptoAddress getAddressTo() {
        return addressTo;
    }

    @Override
    public void setAddressTo(CryptoAddress addressTo) {
        this.addressTo = addressTo;
    }

    @Override
    public long getAmount() {
        return amount;
    }

    @Override
    public void setAmount(long amount) {
        this.amount = amount;
    }

    @Override
    public TransactionType getType() {
        return type;
    }

    @Override
    public void setType(TransactionType type) {
        this.type = type;
    }


    @Override
    public UUID getIdTransaction(){
       return this.id;
    }

    @Override
    public void setIdTransaction(UUID id){
        this.id = id;
    }

    @Override
    public BalanceType getBalanceType(){
        return balanceType;
    }

    @Override
    public void setBalanceType(BalanceType type){
        this.balanceType = type;
    }

    @Override
    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String getMemo() {
        return memo;
    }

    @Override
    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public String getTramsactionHash() {
        return transactionHash;
    }

    @Override
    public void setTramsactionHash(String tramsactionHash) {
        this.transactionHash = tramsactionHash;
    }
}
