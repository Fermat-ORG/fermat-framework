package com.bitdubai.fermat_csh_plugin.layer.wallet_module.cash_money.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_csh_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_csh_api.all_definition.interfaces.CashTransactionParameters;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

/**
 * Created by Alejandro Bicelis on 11/27/2015.
 */
public class CashTransactionParametersImpl implements CashTransactionParameters {

    private UUID transactionId;
    private String publicKeyWallet;
    private String publicKeyActor;
    private String publicKeyPlugin;
    private BigDecimal amount;
    FiatCurrency currency;
    private String memo;
    private TransactionType transactionType;

    public CashTransactionParametersImpl(UUID transactionId, String publicKeyWallet, String publicKeyActor,
                                         String publicKeyPlugin, BigDecimal amount, FiatCurrency currency, String memo, TransactionType transactionType)
    {
        this.transactionId = transactionId;
        this.publicKeyWallet = publicKeyWallet;
        this.publicKeyActor = publicKeyActor;
        this.publicKeyPlugin = publicKeyPlugin;
        this.amount = amount;
        this.currency = currency;
        this.memo = memo;
        this.transactionType = transactionType;
    }


    @Override
    public UUID getTransactionId() {return transactionId;}

    @Override
    public String getPublicKeyWallet() {return publicKeyWallet;}

    @Override
    public String getPublicKeyActor() {return publicKeyActor;}

    @Override
    public String getPublicKeyPlugin() { return this.publicKeyPlugin; }

    @Override
    public BigDecimal getAmount() {return amount;}

    @Override
    public FiatCurrency getCurrency() {return currency;}

    @Override
    public String getMemo() {return memo;}

    @Override
    public TransactionType getTransactionType() {return transactionType;}


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CashTransactionParameters)) return false;
        CashTransactionParameters that = (CashTransactionParameters) o;
        return Objects.equals(getTransactionId(), that.getTransactionId());
    }


}
