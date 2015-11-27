package com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.deposit.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_bnk_api.all_definition.bank_money_transaction.BankTransaction;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankOperationType;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankTransactionStatus;
import com.bitdubai.fermat_bnk_api.all_definition.enums.TransactionType;

import java.util.UUID;

/**
 * Created by memo on 25/11/15.
 */
public class BankTransactionImpl implements BankTransaction {




    @Override
    public UUID getTransactionId() {
        return null;
    }

    @Override
    public String getPublicKeyPlugin() {
        return null;
    }

    @Override
    public String getPublicKeyWallet() {
        return null;
    }

    @Override
    public String getPublicKeyActor() {
        return null;
    }

    @Override
    public float getAmount() {
        return 0;
    }

    @Override
    public String getAccountNumber() {
        return null;
    }

    @Override
    public FiatCurrency getCurrency() {
        return null;
    }

    @Override
    public String getMemo() {
        return null;
    }

    @Override
    public BankOperationType getBankOperationType() {
        return null;
    }

    @Override
    public TransactionType getTransactionType() {
        return null;
    }

    @Override
    public long getTimestamp() {
        return 0;
    }

    @Override
    public BankTransactionStatus getBankTransactionStatus() {
        return null;
    }
}
