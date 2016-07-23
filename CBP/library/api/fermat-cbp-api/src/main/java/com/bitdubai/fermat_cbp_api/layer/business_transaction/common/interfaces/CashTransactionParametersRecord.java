package com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_csh_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_csh_api.all_definition.interfaces.CashTransactionParameters;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 16/02/16.
 */
public class CashTransactionParametersRecord implements CashTransactionParameters {

    /**
     * Represents the transactionId
     */
    UUID transactionId;

    /**
     * Represents the cash wallet public Key
     */
    String publicKeyWallet;

    /**
     * Represents the actor public key
     */
    String publicKeyActor;

    /**
     * Represents the plugin public key
     */
    String publicKeyPlugin;

    /**
     * Represents the transaction amount
     */
    BigDecimal amount;

    /**
     * Represents the Fiat currency
     */
    FiatCurrency currency;

    /**
     * Represents the memo of the transaction
     */
    String memo;

    /**
     * Represents the transaction type
     */
    TransactionType transactionType;

    /**
     * Constructor with parameters
     *
     * @param publicKeyWallet
     * @param publicKeyActor
     * @param publicKeyPlugin
     * @param amount
     * @param currency
     * @param memo
     * @param transactionType
     */
    public CashTransactionParametersRecord(
            String publicKeyWallet,
            String publicKeyActor,
            String publicKeyPlugin,
            BigDecimal amount,
            FiatCurrency currency,
            String memo,
            TransactionType transactionType) {
        this.transactionId = UUID.randomUUID();
        this.publicKeyWallet = publicKeyWallet;
        this.publicKeyActor = publicKeyActor;
        this.publicKeyPlugin = publicKeyPlugin;
        this.amount = amount;
        this.currency = currency;
        this.memo = memo;
        this.transactionType = transactionType;
    }

    @Override
    public UUID getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(UUID transactionId) {
        this.transactionId = transactionId;
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
    public String getPublicKeyPlugin() {
        return publicKeyPlugin;
    }

    public void setPublicKeyPlugin(String publicKeyPlugin) {
        this.publicKeyPlugin = publicKeyPlugin;
    }

    @Override
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("CashTransactionParametersRecord{")
                .append("transactionId=").append(transactionId)
                .append(", publicKeyWallet='").append(publicKeyWallet)
                .append('\'')
                .append(", publicKeyActor='").append(publicKeyActor)
                .append('\'')
                .append(", publicKeyPlugin='").append(publicKeyPlugin)
                .append('\'')
                .append(", amount=").append(amount)
                .append(", currency=").append(currency)
                .append(", memo='").append(memo)
                .append('\'')
                .append(", transactionType=").append(transactionType)
                .append('}').toString();
    }
}
