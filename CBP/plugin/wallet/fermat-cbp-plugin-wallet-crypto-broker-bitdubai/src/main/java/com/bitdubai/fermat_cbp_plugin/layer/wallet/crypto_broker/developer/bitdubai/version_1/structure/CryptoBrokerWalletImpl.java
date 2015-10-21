package com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.interfaces.KeyPair;
import com.bitdubai.fermat_cbp_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet.crypto_broker.interfaces.CryptoBrokerTransactionRecord;

import java.util.UUID;

/**
 * Created by Yordin Alayn on 19.10.15.
 */
public class CryptoBrokerWalletImpl implements CryptoBrokerTransactionRecord {
    private static final int HASH_PRIME_NUMBER_PRODUCT = 7681;
    private static final int HASH_PRIME_NUMBER_ADD = 3581;

    private UUID transactionId;
    private KeyPair keyPairWallet;
    private KeyPair keyPairBroker;
    private BalanceType balanceType;
    private TransactionType transactionType;
    private float amount;
    private CurrencyType currencyType;
    private float runningBookBalance;
    private float runningAvailableBalance;
    private long timeStamp;
    private String memo;

    public CryptoBrokerWalletImpl(
            UUID transactionId,
            KeyPair keyPairWallet,
            KeyPair keyPairBroker,
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
        this.balanceType = balanceType;
        this.transactionType = transactionType;
        this.amount = amount;
        this.currencyType = currencyType;
        this.runningBookBalance = runningBookBalance;
        this.runningAvailableBalance = runningAvailableBalance;
        this.timeStamp = timeStamp;
        this.memo = memo;
    }

    @Override
    public UUID getTransactionId() { return this.transactionId; }
    public void setTransactionId(UUID id) { this.transactionId = id; }

    @Override
    public BalanceType getBalanceType() { return this.balanceType; }
    public void setBalanceType(BalanceType balance) { this.balanceType = balance; }

    @Override
    public TransactionType getTransactionType() { return this.transactionType; }
    public void setBalanceType(TransactionType transaction) { this.transactionType = transaction; }

    @Override
    public CurrencyType getCurrencyType() { return this.currencyType; }
    public void setCurrencyType(CurrencyType currency) { this.currencyType = currency; }

    @Override
    public String getPublicKeyWallet() { return this.keyPairWallet.getPublicKey(); }
    public void setPublicKeyWallet(String publicKey) { this.keyPairWallet = keyPairWallet; }

    @Override
    public String getPublicKeyBroker() { return this.keyPairBroker.getPublicKey(); }
    public void setPublicKeyBroker(String publicKey) { this.keyPairBroker = keyPairBroker; }

    @Override
    public float getAmount() { return this.amount; }
    public void setAmount(float amount) { this.amount = amount; }

    @Override
    public float getRunningBookBalance() { return this.runningBookBalance; }
    public void setRunningBookBalance(float bookBalance) { this.runningBookBalance = bookBalance; }

    @Override
    public float getRunningAvailableBalance() { return this.runningAvailableBalance; }
    public void setRunningAvailableBalance(float availableBalance) { this.runningAvailableBalance = availableBalance; }

    @Override
    public long getTimestamp() { return this.timeStamp; }
    public void setTimestamp(long time) { this.timeStamp = time; }

    @Override
    public String getMemo() { return this.memo; }
    public void setMemo(String memo) { this.memo = memo; }

    /*public boolean equals(Object o){
        if(!(o instanceof CryptoBrokerIdentity))
            return false;
        CryptoBrokerIdentity compare = (CryptoBrokerIdentity) o;
        return alias.equals(compare.getAlias()) && keyPair.getPublicKey().equals(compare.getPublicKey());
    }

    @Override
    public int hashCode(){
        int c = 0;
        c += alias.hashCode();
        c += keyPair.hashCode();
        return 	HASH_PRIME_NUMBER_PRODUCT * HASH_PRIME_NUMBER_ADD + c;
    }*/
}