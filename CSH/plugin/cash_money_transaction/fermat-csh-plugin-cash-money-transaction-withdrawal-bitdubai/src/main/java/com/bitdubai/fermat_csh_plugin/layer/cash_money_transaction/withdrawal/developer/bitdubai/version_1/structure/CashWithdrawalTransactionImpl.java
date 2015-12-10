package com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.withdrawal.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.withdrawal.interfaces.CashWithdrawalTransaction;

import java.util.UUID;

/**
 * Created by Alejandro Bicelis on 11/27/2015.
 */
public class CashWithdrawalTransactionImpl implements CashWithdrawalTransaction {


    private UUID transactionId;
    private String publicKeyWallet;
    private String publicKeyActor;
    private String publicKeyPlugin;
    private float amount;
    private FiatCurrency currency;
    private String memo;
    private long timestamp;

    public CashWithdrawalTransactionImpl(UUID transactionId,
                                         String publicKeyWallet,
                                         String publicKeyActor,
                                         String publicKeyPlugin,
                                         float amount,
                                         FiatCurrency currency,
                                         String memo,
                                         long timestamp)
    {
        this.transactionId = transactionId;
        this.publicKeyWallet = publicKeyWallet;
        this.publicKeyActor = publicKeyActor;
        this.publicKeyPlugin = publicKeyPlugin;
        this.amount = amount;
        this.currency = currency;
        this.memo = memo;
        this.timestamp = timestamp;
    }



    @Override
    public long getTimestamp() {
        return this.timestamp;
    }

    @Override
    public UUID getTransactionId() {
        return this.transactionId;
    }

    @Override
    public String getPublicKeyWallet() {
        return this.publicKeyWallet;
    }

    @Override
    public String getPublicKeyActor() {
        return this.publicKeyActor;
    }

    @Override
    public String getPublicKeyPlugin() { return this.publicKeyPlugin; }

    @Override
    public float getAmount() {
        return this.amount;
    }

    @Override
    public FiatCurrency getCurrency() {
        return this.currency;
    }

    @Override
    public String getMemo() {
        return this.memo;
    }
}
