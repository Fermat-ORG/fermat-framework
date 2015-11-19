package com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.bank_money_restock.developer.bitdubai.version_1.utils;

import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_bnk_api.all_definition.bank_money_transaction.BankTransactionParameters;

import java.util.UUID;

/**
 * Created by franklin on 18/11/15.
 */
public class BankTransactionParametersWrapper implements BankTransactionParameters {
    private final UUID            transactionId;
    private final FiatCurrency    fiatCurrency;
    private final String          walletPublicKey;
    private final String          publicActorKey;
    private final String          accountNumber;
    private final float           amount;
    private final String          memo;

    public BankTransactionParametersWrapper(UUID            transactionId,
                                            FiatCurrency    fiatCurrency,
                                            String          walletPublicKey,
                                            String          publicActorKey,
                                            String          accountNumber,
                                            float           amount,
                                            String          memo
    ){
        this.transactionId   = transactionId;
        this.fiatCurrency    = fiatCurrency;
        this.walletPublicKey = walletPublicKey;
        this.publicActorKey  = publicActorKey;
        this.accountNumber   = accountNumber;
        this.amount          = amount;
        this.memo            = memo;
    }
    @Override
    public UUID getTransactionId() {
        return transactionId;
    }

    @Override
    public String getPublicKeyWallet() {
        return walletPublicKey;
    }

    @Override
    public String getPublicKeyActor() {
        return publicActorKey;
    }

    @Override
    public float getAmount() {
        return amount;
    }

    //Cambiar a String
    @Override
    public int getAccountNumber() {
        return 0;
    }

    @Override
    public FiatCurrency getCurrency() {
        return fiatCurrency;
    }

    @Override
    public String getMemo() {
        return memo;
    }
}
