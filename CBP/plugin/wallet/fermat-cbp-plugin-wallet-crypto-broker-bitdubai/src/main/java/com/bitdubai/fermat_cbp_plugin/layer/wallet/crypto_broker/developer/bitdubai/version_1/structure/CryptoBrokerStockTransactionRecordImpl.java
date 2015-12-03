package com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.interfaces.KeyPair;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_cbp_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerStockTransactionRecord;

import java.util.UUID;

/**
 * Created by Yordin Alayn on 19.10.15.
 */
public class CryptoBrokerStockTransactionRecordImpl implements CryptoBrokerStockTransactionRecord {

    private static final int HASH_PRIME_NUMBER_PRODUCT = 3061;
    private static final int HASH_PRIME_NUMBER_ADD = 7213;

    private final UUID transactionId;
//    private UUID contractId;
    private final KeyPair walletKeyPair;
    private final String ownerPublicKey;
    private final BalanceType balanceType;
    private final TransactionType transactionType;
    private final float amount;
    private final CurrencyType currencyType;
    private final FermatEnum merchandise;
    private final float runningBookBalance;
    private final float runningAvailableBalance;
    private final long timeStamp;
    private final String memo;

    public CryptoBrokerStockTransactionRecordImpl(
            final UUID transactionId,
//            UUID contractId,
            final KeyPair walletKeyPair,
            final String ownerPublicKey,
            final BalanceType balanceType,
            final TransactionType transactionType,
            final CurrencyType currencyType,
            final FermatEnum merchandise,
            final float amount,
            final float runningBookBalance,
            final float runningAvailableBalance,
            final long timeStamp,
            final String memo
    ){
        this.transactionId = transactionId;
//        this.contractId      = contractId;
        this.walletKeyPair = walletKeyPair;
        this.ownerPublicKey = ownerPublicKey;
        this.balanceType = balanceType;
        this.transactionType = transactionType;
        this.amount = amount;
        this.currencyType = currencyType;
        this.merchandise = merchandise;
        this.runningBookBalance = runningBookBalance;
        this.runningAvailableBalance = runningAvailableBalance;
        this.timeStamp = timeStamp;
        this.memo = memo;
    }

    @Override
    public UUID getTransactionId() { return this.transactionId; }

//    @Override
//    public UUID getContractId() {
//        return contractId;
//    }

    @Override
    public BalanceType getBalanceType() { return this.balanceType; }

    @Override
    public TransactionType getTransactionType() { return this.transactionType; }

    @Override
    public String getWalletPublicKey() { return this.walletKeyPair.getPublicKey(); }

    @Override
    public String getOwnerPublicKey() { return this.ownerPublicKey; }

    @Override
    public CurrencyType getCurrencyType() { return this.currencyType; }

    @Override
    public FermatEnum getMerchandise() { return this.merchandise; }

    @Override
    public float getAmount() { return this.amount; }

    @Override
    public float getRunningBookBalance() { return this.runningBookBalance; }

    @Override
    public float getRunningAvailableBalance() { return this.runningAvailableBalance; }

    @Override
    public long getTimestamp() { return this.timeStamp; }

    @Override
    public String getMemo() { return this.memo; }

    public boolean equals(Object o){
        if(!(o instanceof CryptoBrokerStockTransactionRecord))
            return false;
        CryptoBrokerStockTransactionRecord compare = (CryptoBrokerStockTransactionRecord) o;
        return ownerPublicKey.equals(compare.getOwnerPublicKey()) && walletKeyPair.getPublicKey().equals(compare.getWalletPublicKey());
    }

    @Override
    public int hashCode(){
        int c = 0;
        c += ownerPublicKey.hashCode();
        c += walletKeyPair.hashCode();
        return 	HASH_PRIME_NUMBER_PRODUCT * HASH_PRIME_NUMBER_ADD + c;
    }
}