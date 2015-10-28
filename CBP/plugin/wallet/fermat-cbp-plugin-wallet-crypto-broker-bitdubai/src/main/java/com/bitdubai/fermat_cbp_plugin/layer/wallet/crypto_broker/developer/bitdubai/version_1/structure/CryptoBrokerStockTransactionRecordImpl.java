package com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.interfaces.KeyPair;
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
    private KeyPair keyPairWallet;
    private KeyPair keyPairBroker;
    private KeyPair keyPairCustomer;
    private BalanceType balanceType;
    private TransactionType transactionType;
    private float amount;
    private CurrencyType currencyType;
    private float runningBookBalance;
    private float runningAvailableBalance;
    private long timeStamp;
    private String memo;

    public CryptoBrokerStockTransactionRecordImpl(
            UUID transactionId,
            KeyPair keyPairWallet,
            KeyPair keyPairBroker,
            KeyPair keyPairCustomer,
            BalanceType balanceType,
            TransactionType transactionType,
            CurrencyType currencyType,
            float amount,
            float runningBookBalance,
            float runningAvailableBalance,
            long timeStamp,
            String memo
    ){
        this.transactionId = transactionId;
        this.keyPairWallet = keyPairWallet;
        this.keyPairBroker = keyPairBroker;
        this.keyPairCustomer = keyPairCustomer;
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
    public String getWalletPublicKey() { return null; }

    @Override
    public String getOwnerPublicKey() { return null; }

    public void setBalanceType(TransactionType transaction) { this.transactionType = transaction; }

    public CurrencyType getCurrencyType() { return this.currencyType; }

    public String getPublicKeyWallet() { return this.keyPairWallet.getPublicKey(); }

    public String getPublicKeyBroker() { return this.keyPairBroker.getPublicKey(); }

    public String getPublicKeyCustomer() { return this.keyPairCustomer.getPublicKey(); }

    public float getAmount() { return this.amount; }

    public float getRunningBookBalance() { return this.runningBookBalance; }

    public float getRunningAvailableBalance() { return this.runningAvailableBalance; }

    public long getTimestamp() { return this.timeStamp; }

    public String getMemo() { return this.memo; }

    public boolean equals(Object o){
        if(!(o instanceof CryptoBrokerStockTransactionRecord))
            return false;
        CryptoBrokerStockTransactionRecord compare = (CryptoBrokerStockTransactionRecord) o;
        return keyPairBroker.getPublicKey().equals(compare.getOwnerPublicKey()) && keyPairWallet.getPublicKey().equals(compare.getWalletPublicKey());
    }

    @Override
    public int hashCode(){
        int c = 0;
        c += keyPairBroker.hashCode();
        c += keyPairWallet.hashCode();
        return 	HASH_PRIME_NUMBER_PRODUCT * HASH_PRIME_NUMBER_ADD + c;
    }
}