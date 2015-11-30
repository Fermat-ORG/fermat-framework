package com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.hold.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_bnk_api.all_definition.enums.*;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankMoneyTransactionRecord;

import java.util.UUID;

/**
 * Created by memo on 23/11/15.
 */
public class BankMoneyTransactionRecordImpl implements BankMoneyTransactionRecord {

    //TODO: add atributtes.


    UUID bankTransactionId;

    BankTransactionStatus status;

    BalanceType balanceType;

    TransactionType transactionType;

    String publicKey;

    @Override
    public UUID getBankTransactionId() {
        return null;
    }

    @Override
    public BankTransactionStatus getStatus() {
        return null;
    }

    @Override
    public BalanceType getBalanceType() {
        return null;
    }

    @Override
    public TransactionType getTransactionType() {
        return null;
    }

    @Override
    public String getPublicKeyActorFrom() {
        return null;
    }

    @Override
    public String getPublicKeyActorTo() {
        return null;
    }

    @Override
    public float getAmount() {
        return 0;
    }

    @Override
    public BankCurrencyType getBankCurrencyType() {
        return null;
    }

    @Override
    public BankOperationType getBankOperationType() {
        return null;
    }

    @Override
    public String getBankDocumentReference() {
        return null;
    }

    @Override
    public String getBankName() {
        return null;
    }

    @Override
    public String getBankAccountNumber() {
        return null;
    }

    @Override
    public BankAccountType getBankAccountType() {
        return null;
    }

    @Override
    public long getTimestamp() {
        return 0;
    }

    @Override
    public long getRunningBookBalance() {
        return 0;
    }

    @Override
    public long getRunningAvailableBalance() {
        return 0;
    }

    @Override
    public String getMemo() {
        return null;
    }
}
