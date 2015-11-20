package com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces;

import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantCreateBankMoneyException;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantTransactionBankMoneyException;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantLoadBankMoneyException;

import java.util.List;

/**
 * Created by Yordin Alayn on 26.09.15.
 */
public interface BankMoneyManager {

    List<BankMoney> getTransactionsBankMoney() throws CantTransactionBankMoneyException;

    BankMoney registerBankMoney(
         final String bankTransactionId
        ,final String publicKeyActorFrom
        ,final String publicKeyActorTo
        ,final String balanceType
        ,final String transactionType
        ,final float  amount
        ,final String bankCurrencyType
        ,final String bankOperationType
        ,final String bankDocumentReference
        ,final String bankName
        ,final String bankAccountNumber
        ,final String bankAccountType
        ,final long runningBookBalance
        ,final long runningAvailableBalance
        ,final long timestamp
        ,final String getMemo
    ) throws CantCreateBankMoneyException;

    BankMoney loadCashMoneyWallet(String walletPublicKey) throws CantLoadBankMoneyException;

    void createCashMoney (String walletPublicKey) throws CantCreateBankMoneyException;
}
