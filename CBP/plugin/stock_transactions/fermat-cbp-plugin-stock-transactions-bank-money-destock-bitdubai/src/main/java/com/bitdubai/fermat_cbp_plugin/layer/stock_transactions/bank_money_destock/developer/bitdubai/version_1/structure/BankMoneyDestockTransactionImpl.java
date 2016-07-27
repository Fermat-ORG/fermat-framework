package com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.bank_money_destock.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_cbp_api.all_definition.business_transaction.BankMoneyTransaction;
import com.bitdubai.fermat_cbp_api.all_definition.enums.OriginTransaction;
import com.bitdubai.fermat_cbp_api.all_definition.enums.TransactionStatusRestockDestock;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

/**
 * Created by franklin on 17/11/15.
 */
public class BankMoneyDestockTransactionImpl implements BankMoneyTransaction {
    UUID transactionId;
    String actorPublicKey;
    FiatCurrency fiatCurrency;
    String cbpWalletPublicKey;
    String bnkWalletPublicKey;
    String memo;
    String concept;
    String bankAccount;
    BigDecimal amount;
    Timestamp timeStamp;
    TransactionStatusRestockDestock transactionStatus;
    BigDecimal priceReference;
    OriginTransaction originTransaction;
    String originTransactionId;

    public BankMoneyDestockTransactionImpl() {

    }

    public BankMoneyDestockTransactionImpl(UUID transactionId,
                                           String actorPublicKey,
                                           FiatCurrency fiatCurrency,
                                           String cbpWalletPublicKey,
                                           String bnkWalletPublicKey,
                                           String memo,
                                           String concept,
                                           String bankAccount,
                                           BigDecimal amount,
                                           Timestamp timeStamp,
                                           TransactionStatusRestockDestock transactionStatus,
                                           BigDecimal priceReference,
                                           OriginTransaction originTransaction,
                                           String originTransactionId) {
        this.transactionId = transactionId;
        this.actorPublicKey = actorPublicKey;
        this.fiatCurrency = fiatCurrency;
        this.cbpWalletPublicKey = cbpWalletPublicKey;
        this.bnkWalletPublicKey = bnkWalletPublicKey;
        this.memo = memo;
        this.concept = concept;
        this.bankAccount = bankAccount;
        this.amount = amount;
        this.timeStamp = timeStamp;
        this.transactionStatus = transactionStatus;
        this.priceReference = priceReference;
        this.originTransaction = originTransaction;
        this.originTransactionId = originTransactionId;
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
    public FiatCurrency getFiatCurrency() {
        return fiatCurrency;
    }

    @Override
    public void setFiatCurrency(FiatCurrency fiatCurrency) {
        this.fiatCurrency = fiatCurrency;
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
    public String getBnkWalletPublicKey() {
        return bnkWalletPublicKey;
    }

    @Override
    public void setBnkWalletPublicKey(String bnkWalletPublicKey) {
        this.bnkWalletPublicKey = bnkWalletPublicKey;
    }

    @Override
    public String getBankAccount() {
        return bankAccount;
    }

    @Override
    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
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
    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public void setAmount(BigDecimal amount) {
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
    public BigDecimal getPriceReference() {
        return priceReference;
    }

    @Override
    public void setPriceReference(BigDecimal priceReference) {
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

    /**
     * The property <code>OriginTransactionId</code>  represented the Origin Transaction
     *
     * @return the String
     */
    @Override
    public String getOriginTransactionId() {
        return originTransactionId;
    }

    @Override
    public void setOriginTransactionId(String originTransactionId) {
        this.originTransactionId = originTransactionId;
    }
}
