package com.bitdubai.fermat_bnk_api.layer.bnk_wallet_module.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.modules.ModuleSettingsImpl;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;
import com.bitdubai.fermat_bnk_api.all_definition.bank_money_transaction.BankTransactionParameters;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankAccountType;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.deposit.exceptions.CantMakeDepositTransactionException;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.withdraw.exceptions.CantMakeWithdrawTransactionException;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantEditAccountException;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantLoadBankMoneyWalletException;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankAccountNumber;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankMoneyTransactionRecord;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet_module.BankMoneyWalletPreferenceSettings;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;


/**
 * Created by memo on 04/12/15.
 */
public interface BankMoneyWalletModuleManager extends ModuleManager<BankMoneyWalletPreferenceSettings, ActiveActorIdentityInformation>,
        ModuleSettingsImpl<BankMoneyWalletPreferenceSettings>, Serializable {

    List<BankAccountNumber> getAccounts() throws CantLoadBankMoneyWalletException;

    void addNewAccount(BankAccountType bankAccountType, String alias, String account, FiatCurrency fiatCurrency, String selectedImageId);

    List<BankMoneyTransactionRecord> getTransactions(String account) throws CantLoadBankMoneyWalletException;

    List<BankMoneyTransactionRecord> getPendingTransactions(String account);

    void makeDeposit(BankTransactionParameters bankTransactionParameters) throws CantMakeDepositTransactionException;

    void makeWithdraw(BankTransactionParameters bankTransactionParameters) throws CantMakeWithdrawTransactionException;

    void makeAsyncDeposit(BankTransactionParameters bankTransactionParameters);

    void makeAsyncWithdraw(BankTransactionParameters bankTransactionParameters);

    BigDecimal getBookBalance(String account);

    BigDecimal getAvailableBalance(String account);

    void createBankName(String bankName);

    String getBankName();

    void cancelAsyncBankTransaction(BankMoneyTransactionRecord transaction);

    void editAccount(String originalAccountNumber, String newAlias, String newAccountNumber, String newImageId) throws CantEditAccountException;
}
