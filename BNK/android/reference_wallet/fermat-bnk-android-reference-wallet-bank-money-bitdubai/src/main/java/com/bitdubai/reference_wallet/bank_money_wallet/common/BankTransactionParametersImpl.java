package com.bitdubai.reference_wallet.bank_money_wallet.common;

import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_bnk_api.all_definition.bank_money_transaction.BankTransactionParameters;
import com.bitdubai.fermat_bnk_api.all_definition.enums.TransactionType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * Created by guillermo on 26/01/16.
 */
public class BankTransactionParametersImpl implements BankTransactionParameters, Serializable {


    private UUID transactionId;
    private String publicKeyPlugin;
    private String publicKeyWallet;
    private String publicKeyActor;
    private BigDecimal amount;
    private String account;
    private FiatCurrency currency;
    private String memo;
    private TransactionType transactionType;

    public BankTransactionParametersImpl(UUID transactionId, String publicKeyPlugin, String publicKeyWallet, String publicKeyActor, BigDecimal amount, String account, FiatCurrency currency, String memo, TransactionType transactionType) {
        this.transactionId = transactionId;
        this.publicKeyPlugin = publicKeyPlugin;
        this.publicKeyWallet = publicKeyWallet;
        this.publicKeyActor = publicKeyActor;
        this.amount = amount;
        this.account = account;
        this.currency = currency;
        this.memo = memo;
        this.transactionType = transactionType;
    }

    @Override
    public UUID getTransactionId() {
        return transactionId;
    }

    @Override
    public String getPublicKeyPlugin() {
        return publicKeyPlugin;
    }

    @Override
    public String getPublicKeyWallet() {
        return publicKeyWallet;
    }

    @Override
    public String getPublicKeyActor() {
        return publicKeyActor;
    }

    @Override
    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public String getAccount() {
        return account;
    }

    @Override
    public FiatCurrency getCurrency() {
        return currency;
    }

    @Override
    public String getMemo() {
        return memo;
    }

    @Override
    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }
}
