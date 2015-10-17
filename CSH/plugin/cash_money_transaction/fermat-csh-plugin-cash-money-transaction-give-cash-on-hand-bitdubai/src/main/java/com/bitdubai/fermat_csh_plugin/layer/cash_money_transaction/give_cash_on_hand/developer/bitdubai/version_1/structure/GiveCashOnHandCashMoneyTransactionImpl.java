package com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.give_cash_on_hand.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_csh_api.all_definition.cash_money_transaction.CashMoneyTransaction;
import com.bitdubai.fermat_csh_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_csh_api.all_definition.enums.CashCurrencyType;
import com.bitdubai.fermat_csh_api.all_definition.enums.CashTransactionStatus;
import com.bitdubai.fermat_csh_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.give_cash_on_hand.interfaces.GiveCashOnHand;

import java.util.UUID;

/**
 * Created by Yordin Alayn on 29.09.15.
 */
public class GiveCashOnHandCashMoneyTransactionImpl implements CashMoneyTransaction, GiveCashOnHand{

    private UUID bankTransactionId;
    private String publicKeyActorTo;
    private String publicKeyActorFrom;
    private BalanceType balanceType;
    private TransactionType transactionType;
    private float amount;
    private CashCurrencyType cashCurrencyType;
    private String cashReference;
    private long timeStamp;
    private CashTransactionStatus transactionStatus;
    
    public GiveCashOnHandCashMoneyTransactionImpl(
             UUID bankTransactionId
            ,String publicKeyActorTo
            ,String publicKeyActorFrom
            ,BalanceType balanceType
            ,TransactionType transactionType
            ,float amount
            ,CashCurrencyType cashCurrencyType
            ,String cashReference
            ,long timeStamp
            ,CashTransactionStatus transactionStatus
    ){
        this.bankTransactionId = bankTransactionId;
        this.publicKeyActorTo = publicKeyActorTo;
        this.publicKeyActorFrom = publicKeyActorFrom;
        this.balanceType = balanceType;
        this.transactionType = transactionType;
        this.amount = amount;
        this.cashCurrencyType = cashCurrencyType;
        this.cashReference = cashReference;
        this.timeStamp = timeStamp;
        this.transactionStatus = transactionStatus;
    }


    @Override
    public UUID getCashTransactionId(){ return this.bankTransactionId; }
    public void setCashTransactionId(UUID id) { this.bankTransactionId = id; }

    @Override
    public String getPublicKeyActorTo(){ return this.publicKeyActorTo; }
    public void setPublicKeyActorTo(String actor) { this.publicKeyActorTo = actor; }

    @Override
    public String getPublicKeyActorFrom(){ return this.publicKeyActorFrom; }
    public void setPublicKeyActorFrom(String actor) { this.publicKeyActorFrom = actor; }

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
    public CashCurrencyType getCashCurrencyType(){ return this.cashCurrencyType; }
    public void setCashCurrencyType(CashCurrencyType currency) { this.cashCurrencyType = currency; }

    @Override
    public String getCashReference(){ return this.cashReference; }
    public void setCashReference(String reference) { this.cashReference = reference; }

    @Override
    public long getTimestamp(){ return timeStamp; }
    public void setTimestamp(long timeT) { this.timeStamp = timeT; }

    @Override
    public CashTransactionStatus getStatus(){ return this.transactionStatus; }
    public void setStatus(CashTransactionStatus status) { this.transactionStatus = status; }

}
