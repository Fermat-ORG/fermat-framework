package com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_bnk_api.all_definition.bank_money_transaction.BankTransactionParameters;
import com.bitdubai.fermat_bnk_api.all_definition.enums.TransactionType;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 15/02/16.
 */
public class BankTransactionParametersRecord implements BankTransactionParameters {

    /**
     * Represents the transaction Id
     */
    UUID transactionId;

    /**
     * Represents the plugin public key
     */
    String publicKeyPlugin;

    /**
     * Represents the wallet public key
     */
    String publicKeyWallet;

    /**
     * Represents the actor public key
     */
    String publicKeyActor;

    /**
     * Represents the amount to deposit
     */
    BigDecimal amount;

    /**
     * Represents the bank account
     */
    String account;

    /**
     * Represents the currency to use.
     */
    FiatCurrency currency;

    /**
     * Represents the memorandum in this transaction.
     */
    String memo;
    private TransactionType transactionType;

    /**
     * Constructor with parameters
     *
     * @param publicKeyPlugin
     * @param publicKeyWallet
     * @param publicKeyActor
     * @param amount
     * @param account
     * @param currency
     * @param memo
     */
    public BankTransactionParametersRecord(
            String publicKeyPlugin,
            String publicKeyWallet,
            String publicKeyActor,
            BigDecimal amount,
            String account,
            FiatCurrency currency,
            String memo) {
        this.transactionId = UUID.randomUUID();
        this.publicKeyPlugin = publicKeyPlugin;
        this.publicKeyWallet = publicKeyWallet;
        this.publicKeyActor = publicKeyActor;
        this.amount = amount;
        this.account = account;
        this.currency = currency;
        this.memo = memo;
    }

    @Override
    public UUID getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(UUID transactionId) {
        this.transactionId = transactionId;
    }

    @Override
    public String getPublicKeyPlugin() {
        return publicKeyPlugin;
    }

    public void setPublicKeyPlugin(String publicKeyPlugin) {
        this.publicKeyPlugin = publicKeyPlugin;
    }

    @Override
    public String getPublicKeyWallet() {
        return publicKeyWallet;
    }

    public void setPublicKeyWallet(String publicKeyWallet) {
        this.publicKeyWallet = publicKeyWallet;
    }

    @Override
    public String getPublicKeyActor() {
        return publicKeyActor;
    }

    public void setPublicKeyActor(String publicKeyActor) {
        this.publicKeyActor = publicKeyActor;
    }

    @Override
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Override
    public FiatCurrency getCurrency() {
        return currency;
    }

    public void setCurrency(FiatCurrency currency) {
        this.currency = currency;
    }

    @Override
    public String getMemo() {
        return memo;
    }

    @Override
    public TransactionType getTransactionType() {
        return transactionType;
    }


    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("BankTransactionParametersRecord{")
                .append("transactionId=").append(transactionId)
                .append(", publicKeyPlugin='").append(publicKeyPlugin)
                .append('\'')
                .append(", publicKeyWallet='").append(publicKeyWallet)
                .append('\'')
                .append(", publicKeyActor='").append(publicKeyActor)
                .append('\'')
                .append(", amount=").append(amount)
                .append(", account='").append(account)
                .append('\'')
                .append(", currency=").append(currency)
                .append(", memo='").append(memo)
                .append('\'')
                .append('}').toString();
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }
}
