package com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.receive_offline_bank_transfer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_bnk_api.all_definition.bank_money_transaction.BankMoneyTransaction;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankAccountType;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankCurrencyType;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankOperationType;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankTransactionStatus;
import com.bitdubai.fermat_bnk_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.receive_offline_bank_transfer.interfaces.ReceiveOfflineBankTransfer;

import java.util.UUID;

/**
 * Created by Yordin Alayn on 29.09.15.
 */
public class ReceiveOfflineBankTransferBankMoneyTransactionImpl implements BankMoneyTransaction, ReceiveOfflineBankTransfer{

    private UUID bankTransactionId;
    private String publicKeyActorTo;
    private String publicKeyActorFrom;
    private BalanceType balanceType;
    private TransactionType transactionType;
    private float amount;
    private BankCurrencyType bankCurrencyType;
    private BankOperationType bankOperationType;
    private String bankDocumentReference;
    private String bankToName;
    private String bankToAccountNumber;
    private BankAccountType bankToAccountType;
    private String bankFromName;
    private String bankFromAccountNumber;
    private BankAccountType bankFromAccountType;
    private BankTransactionStatus transactionStatus;
    private long timeStamp;

    public ReceiveOfflineBankTransferBankMoneyTransactionImpl(
            UUID bankTransactionId
            ,String publicKeyActorTo
            ,String publicKeyActorFrom
            ,BalanceType balanceType
            ,TransactionType transactionType
            ,float amount
            ,BankCurrencyType bankCurrencyType
            ,BankOperationType bankOperationType
            ,String bankDocumentReference
            ,String bankToName
            ,String bankToAccountNumber
            ,BankAccountType bankToAccountType
            ,String bankFromName
            ,String bankFromAccountNumber
            ,BankAccountType bankFromAccountType
            ,BankTransactionStatus transactionStatus
            ,long timeStamp
    ){
        this.bankTransactionId = bankTransactionId;
        this.publicKeyActorTo = publicKeyActorTo;
        this.publicKeyActorFrom = publicKeyActorFrom;
        this.balanceType = balanceType;
        this.transactionType = transactionType;
        this.amount = amount;
        this.bankCurrencyType = bankCurrencyType;
        this.bankOperationType = bankOperationType;
        this.bankDocumentReference = bankDocumentReference;
        this.bankToName = bankToName;
        this.bankToAccountNumber = bankToAccountNumber;
        this.bankToAccountType = bankToAccountType;
        this.bankFromName = bankFromName;
        this.bankFromAccountNumber = bankFromAccountNumber;
        this.bankFromAccountType = bankFromAccountType;
        this.transactionStatus = transactionStatus;
        this.timeStamp = timeStamp;
    }


    @Override
    public UUID getBankTransactionId(){ return this.bankTransactionId; }
    public void setBankTransactionId(UUID id) { this.bankTransactionId = id; }

    @Override
    public String getPublicKeyActorTo(){ return this.publicKeyActorTo; }
    public void setPublicKeyActorTo(String actor) { this.publicKeyActorTo = actor; }

    @Override
    public String getPublicKeyActorFrom(){ return this.publicKeyActorFrom; }
    public void setPublicKeyActorFrom(String actor) { this.publicKeyActorFrom = actor; }

    @Override
    public BankTransactionStatus getStatus(){ return this.transactionStatus; }
    public void setStatus(BankTransactionStatus status) { this.transactionStatus = status; }

    @Override
    public BalanceType getBalanceType(){ return this.balanceType; }
    public void setBalanceType(BalanceType balance) { this.balanceType = balance; }

    @Override
    public TransactionType getTransactionType(){ return this.transactionType; }
    public void setTransactionType(TransactionType transType) { this.transactionType = transType; }

    @Override
    public float getAmount(){ return this.amount; }
    public void setAmount(float amount) { this.amount = amount; }

    @Override
    public BankCurrencyType getBankCurrencyType(){ return this.bankCurrencyType; }
    public void setBankCurrencyType(BankCurrencyType currency) { this.bankCurrencyType = currency; }

    @Override
    public BankOperationType getBankOperationType(){ return this.bankOperationType; }
    public void setBankOperationType(BankOperationType operation) { this.bankOperationType = operation; }

    @Override
    public String getBankDocumentReference(){ return this.bankDocumentReference; }
    public void setBankDocumentReference(String document) { this.bankDocumentReference = document; }

    @Override
    public String getBankToName(){ return this.bankToName; }
    public void setBankToName(String name) { this.bankToName = name; }

    @Override
    public String getBankToAccountNumber(){ return this.bankToAccountNumber; }
    public void setBankToAccountNumber(String number) { this.bankToAccountNumber = number; }

    @Override
    public BankAccountType getBankToAccountType(){ return this.bankToAccountType; }
    public void setBankToAccountType(BankAccountType aType) { this.bankToAccountType = aType; }

    @Override
    public String getBankFromName(){ return this.bankFromName; }
    public void setBankFromName(String name) { this.bankFromName = name; }

    @Override
    public String getBankFromAccountNumber(){ return this.bankFromAccountNumber; }
    public void setBankFromAccountNumber(String number) { this.bankFromAccountNumber = number; }

    @Override
    public BankAccountType getBankFromAccountType(){ return this.bankFromAccountType; }
    public void setBankFromAccountType(BankAccountType aType) { this.bankFromAccountType = aType; }

    @Override
    public long getTimestamp(){ return timeStamp; }
    public void setTimestamp(long timeT) { this.timeStamp = timeT; }

}
