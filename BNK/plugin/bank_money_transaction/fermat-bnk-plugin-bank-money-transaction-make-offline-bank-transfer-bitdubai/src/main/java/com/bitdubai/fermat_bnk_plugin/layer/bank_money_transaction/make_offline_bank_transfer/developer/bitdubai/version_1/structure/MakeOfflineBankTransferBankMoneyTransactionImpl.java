package com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.make_offline_bank_transfer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_bnk_api.all_definition.bank_money_transaction.BankMoneyTransaction;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankAccountType;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankCurrencyType;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankOperationType;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankTransactionStatus;
import com.bitdubai.fermat_bnk_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.make_offline_bank_transfer.interfaces.MakeOfflineBankTransfer;

import java.util.UUID;

/**
 * Created by Yordin Alayn on 29.09.15.
 */
public class MakeOfflineBankTransferBankMoneyTransactionImpl implements BankMoneyTransaction, MakeOfflineBankTransfer{

    public UUID getBankTransactionId(){ return null; }

    public String getPublicKeyBroker(){ return null; }

    public String getPublicKeyCustomer(){ return null; }

    public BankTransactionStatus getStatus(){ return null; }

    public BalanceType getBalanceType(){ return null; }

    public TransactionType getTransactionType(){ return null; }

    public float getAmount(){ return 0; }

    public  BankCurrencyType getBankCurrencyType(){ return null; }

    public  BankOperationType getBankOperationType(){ return null; }

    public String getBankDocumentReference(){ return null; }

    public String getBankToName(){ return null; }

    public String getBankToAccountNumber(){ return null; }

    public BankAccountType getBankToAccountType(){ return null; }

    public String getBankFromName(){ return null; }

    public String getBankFromAccountNumber(){ return null; }

    public BankAccountType getBankFromAccountType(){ return null; }

    public long getTimestamp(){ return 0; }

}
