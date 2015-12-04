package com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.crypto_money_restock.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_cbp_api.all_definition.business_transaction.CryptoMoneyTransaction;
import com.bitdubai.fermat_cbp_api.all_definition.enums.OriginTransaction;
import com.bitdubai.fermat_cbp_api.all_definition.enums.TransactionStatusRestockDestock;

import java.sql.Timestamp;
import java.util.UUID;

/**
 * Created by franklin on 17/11/15.
 */
public class CryptoMoneyRestockTransactionImpl implements CryptoMoneyTransaction {
    UUID                            transactionId;
    String                          actorPublicKey;
    CryptoCurrency                  cryptoCurrency;
    String                          cbpWalletPublicKey;
    String                          cryWalletPublicKey;
    String                          memo;
    String                          concept;
    float                           amount;
    Timestamp                       timeStamp;
    TransactionStatusRestockDestock transactionStatus;
    float                           priceReference;
    OriginTransaction originTransaction;

    public CryptoMoneyRestockTransactionImpl(){

    };

    public CryptoMoneyRestockTransactionImpl(UUID transactionId,
                                             String actorPublicKey,
                                             CryptoCurrency cryptoCurrency,
                                             String cbpWalletPublicKey,
                                             String cryWalletPublicKey,
                                             String memo,
                                             String concept,
                                             float amount,
                                             Timestamp timeStamp,
                                             TransactionStatusRestockDestock transactionStatus,
                                             float priceReference,
                                             OriginTransaction originTransaction){
        this.transactionId      = transactionId;
        this.actorPublicKey     = actorPublicKey;
        this.cryptoCurrency     = cryptoCurrency;
        this.cbpWalletPublicKey = cbpWalletPublicKey;
        this.cryWalletPublicKey = cryWalletPublicKey;
        this.memo               = memo;
        this.concept            = concept;
        this.amount             = amount;
        this.timeStamp          = timeStamp;
        this.transactionStatus  = transactionStatus;
        this.priceReference     = priceReference;
        this.originTransaction  = originTransaction;
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
    public String getActorPublicKey() {
        return actorPublicKey;
    }

    @Override
    public void setActorPublicKey(String actorPublicKey) {
        this.actorPublicKey = actorPublicKey;
    }

    @Override
    public CryptoCurrency getCryptoCurrency() {
        return cryptoCurrency;
    }

    @Override
    public void setCryptoCurrency(CryptoCurrency cryptoCurrency) {

    }

    @Override
    public String getCbpWalletPublicKey() {
        return cbpWalletPublicKey;
    }

    @Override
    public void setCbpWalletPublicKey(String cbpWalletPublicKey) {
        this.cbpWalletPublicKey = cbpWalletPublicKey;
    }

    @Override
    public String getCryWalletPublicKey() {
        return this.cryWalletPublicKey;
    }

    @Override
    public void setCryWalletPublicKey(String cryWalletPublicKey) {
        this.cryWalletPublicKey = cryWalletPublicKey;
    }

    @Override
    public String getConcept() {
        return concept;
    }

    @Override
    public void setConcept(String concept) {
        this.concept = concept;
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
    public Timestamp getTimeStamp() {
        return timeStamp;
    }

    @Override
    public void setTimeStamp(Timestamp timeStamp) {
        this.timeStamp = timeStamp;
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
    public TransactionStatusRestockDestock getTransactionStatus() {
        return transactionStatus;
    }

    @Override
    public void setTransactionStatus(TransactionStatusRestockDestock transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    @Override
    public float getPriceReference() {
        return priceReference;
    }

    @Override
    public void setPriceReference(float priceReference) {
        this.priceReference = priceReference;
    }

    @Override
    public OriginTransaction getOriginTransaction() {
        return originTransaction;
    }

    @Override
    public void setOriginTransaction(OriginTransaction originTransaction) {
        this.originTransaction = originTransaction;
    }
}
