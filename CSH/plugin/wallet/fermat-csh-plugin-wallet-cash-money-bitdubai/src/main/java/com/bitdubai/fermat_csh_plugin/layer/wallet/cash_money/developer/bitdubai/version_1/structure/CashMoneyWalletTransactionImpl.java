package com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_csh_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.interfaces.CashMoneyWalletTransaction;

import java.util.UUID;

/**
 * Created by Alejandro Bicelis on 11/24/2015.
 */
public class CashMoneyWalletTransactionImpl implements CashMoneyWalletTransaction {

    UUID transactionId;
    String publicKeyWallet;
    String publicKeyActor;
    String publicKeyPlugin;
    TransactionType transactionType;
    float amount;
    String memo;
    long timestamp;


    public CashMoneyWalletTransactionImpl(UUID transactionId, String publicKeyWallet, String publicKeyActor, String publicKeyPlugin,
                                          TransactionType transactionType, float amount, String memo, long timestamp) {
        this.transactionId = transactionId;
        this.publicKeyWallet = publicKeyWallet;
        this.publicKeyActor = publicKeyActor;
        this.publicKeyPlugin = publicKeyPlugin;
        this.transactionType = transactionType;
        this.amount = amount;
        this.memo = memo;
        this.timestamp = timestamp;
    }


    @Override
    public TransactionType getTransactionType() {
        return transactionType;
    }

    @Override
    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public UUID getTransactionId() {
        return transactionId;
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
    public String getPublicKeyPlugin() {
        return publicKeyPlugin;
    }

    @Override
    public float getAmount() {
        return amount;
    }

    @Override
    public FiatCurrency getCurrency() {
        return null;
    }

    @Override
    public String getMemo() {
        return memo;
    }
}
