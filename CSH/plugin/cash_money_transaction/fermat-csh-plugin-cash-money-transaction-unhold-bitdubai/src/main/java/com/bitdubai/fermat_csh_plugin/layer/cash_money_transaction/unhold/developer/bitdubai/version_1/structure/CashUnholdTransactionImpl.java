package com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.unhold.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_csh_api.all_definition.enums.CashTransactionStatus;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.unhold.interfaces.CashUnholdTransaction;

import java.util.UUID;

/**
 * Created by Alejandro Bicelis on 11/18/2015.
 */
public class CashUnholdTransactionImpl implements CashUnholdTransaction {


    private UUID transactionId;
    private String publicKeyWallet;
    private String publicKeyActor;
    private String publicKeyPlugin;
    private float amount;
    private FiatCurrency currency;
    private String memo;
    private CashTransactionStatus transactionStatus;
    private long timestampAcknowledged;
    private long timestampConfirmedRejected;

    public CashUnholdTransactionImpl(UUID transactionId,
                                     String publicKeyWallet,
                                     String publicKeyActor,
                                     String publicKeyPlugin,
                                     float amount,
                                     FiatCurrency currency,
                                     String memo,
                                     CashTransactionStatus transactionStatus,
                                     long timestampAcknowledged,
                                     long timestampConfirmedRejected)
    {
        this.transactionId = transactionId;
        this.publicKeyWallet = publicKeyWallet;
        this.publicKeyActor = publicKeyActor;
        this.publicKeyPlugin = publicKeyPlugin;
        this.amount = amount;
        this.currency = currency;
        this.memo = memo;
        this.transactionStatus = transactionStatus;
        this.timestampAcknowledged = timestampAcknowledged;
        this.timestampConfirmedRejected = timestampConfirmedRejected;
    }


    @Override
    public CashTransactionStatus getStatus() {
        return this.transactionStatus;
    }

    @Override
    public long getTimestampAcknowledged() {
        return this.timestampAcknowledged;
    }

    @Override
    public long getTimestampConfirmedRejected() {
        return this.timestampConfirmedRejected;
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
