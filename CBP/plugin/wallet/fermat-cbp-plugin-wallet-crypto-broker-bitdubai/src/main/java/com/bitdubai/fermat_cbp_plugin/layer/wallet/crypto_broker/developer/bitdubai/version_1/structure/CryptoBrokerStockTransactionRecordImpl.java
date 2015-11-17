package com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.interfaces.KeyPair;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_cbp_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet.crypto_broker.interfaces.CryptoBrokerStockTransactionRecord;

import java.util.UUID;

/**
 * Created by Yordin Alayn on 19.10.15.
 */
public class CryptoBrokerStockTransactionRecordImpl implements CryptoBrokerStockTransactionRecord {

    private static final int HASH_PRIME_NUMBER_PRODUCT = 3061;
    private static final int HASH_PRIME_NUMBER_ADD = 7213;

    private UUID transactionId;
    private KeyPair walletKeyPair;
    private String ownerPublicKey;
    private BalanceType balanceType;
    private TransactionType transactionType;
    private float amount;
    private CurrencyType currencyType;
    private FermatEnum merchandise;
    private float runningBookBalance;
    private float runningAvailableBalance;
    private long timeStamp;
    private String memo;

    public CryptoBrokerStockTransactionRecordImpl(
            UUID transactionId,
            KeyPair walletKeyPair,
            String ownerPublicKey,
            BalanceType balanceType,
            TransactionType transactionType,
            CurrencyType currencyType,
            FermatEnum merchandise,
            float amount,
            float runningBookBalance,
            float runningAvailableBalance,
            long timeStamp,
            String memo
    ){
        this.transactionId = transactionId;
        this.walletKeyPair = walletKeyPair;
        this.ownerPublicKey = ownerPublicKey;
        this.balanceType = balanceType;
        this.transactionType = transactionType;
        this.amount = amount;
        this.currencyType = currencyType;
        this.runningBookBalance = runningBookBalance;
        this.runningAvailableBalance = runningAvailableBalance;
        this.timeStamp = timeStamp;
        this.memo = memo;
    }

    public UUID getTransactionId() { return this.transactionId; }

    public BalanceType getBalanceType() { return this.balanceType; }

    public TransactionType getTransactionType() { return this.transactionType; }

    @Override
    public String getWalletPublicKey() { return this.walletKeyPair.getPublicKey(); }

    @Override
    public String getOwnerPublicKey() { return this.ownerPublicKey; }

    public CurrencyType getCurrencyType() { return this.currencyType; }

    public FermatEnum getMerchandise() { return this.merchandise; }

    public float getAmount() { return this.amount; }

    public float getRunningBookBalance() { return this.runningBookBalance; }

    public float getRunningAvailableBalance() { return this.runningAvailableBalance; }

    public long getTimestamp() { return this.timeStamp; }

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