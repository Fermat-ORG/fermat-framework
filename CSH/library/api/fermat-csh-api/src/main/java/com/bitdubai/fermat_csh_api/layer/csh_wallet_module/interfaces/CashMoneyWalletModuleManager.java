package com.bitdubai.fermat_csh_api.layer.csh_wallet_module.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.modules.ModuleSettingsImpl;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;
import com.bitdubai.fermat_csh_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_csh_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_csh_api.all_definition.exceptions.CashMoneyWalletInsufficientFundsException;
import com.bitdubai.fermat_csh_api.all_definition.interfaces.CashTransactionParameters;
import com.bitdubai.fermat_csh_api.all_definition.interfaces.CashWalletBalances;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.deposit.exceptions.CantCreateDepositTransactionException;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.deposit.interfaces.CashDepositTransaction;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.withdrawal.exceptions.CantCreateWithdrawalTransactionException;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.withdrawal.interfaces.CashWithdrawalTransaction;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantCreateCashMoneyWalletException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantGetCashMoneyWalletCurrencyException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantGetCashMoneyWalletTransactionsException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.interfaces.CashMoneyWalletTransaction;
import com.bitdubai.fermat_csh_api.layer.csh_wallet_module.CashMoneyWalletPreferenceSettings;
import com.bitdubai.fermat_csh_api.layer.csh_wallet_module.exceptions.CantGetCashMoneyWalletBalancesException;

import java.util.List;
import java.util.UUID;

/**
 * Created by Alejandro Bicelis on 12/8/2015.
 */
public interface CashMoneyWalletModuleManager extends ModuleManager<CashMoneyWalletPreferenceSettings, ActiveActorIdentityInformation>,
        ModuleSettingsImpl<CashMoneyWalletPreferenceSettings> {

    CashWalletBalances getWalletBalances(String walletPublicKey) throws CantGetCashMoneyWalletBalancesException;
    FiatCurrency getWalletCurrency(String walletPublicKey) throws CantGetCashMoneyWalletCurrencyException;


    void createAsyncCashTransaction(CashTransactionParameters depositParameters);
    void cancelAsyncCashTransaction(CashMoneyWalletTransaction transaction)  throws Exception;


    CashDepositTransaction doCreateCashDepositTransaction(CashTransactionParameters depositParameters) throws CantCreateDepositTransactionException;
    CashWithdrawalTransaction doCreateCashWithdrawalTransaction(CashTransactionParameters withdrawalParameters) throws CantCreateWithdrawalTransactionException, CashMoneyWalletInsufficientFundsException;

    List<CashMoneyWalletTransaction> getPendingTransactions();
    List<CashMoneyWalletTransaction> getTransactions(String walletPublicKey, List<TransactionType> transactionTypes, List<BalanceType> balanceTypes, int max, int offset) throws CantGetCashMoneyWalletTransactionsException;
    CashMoneyWalletTransaction getTransaction(String walletPublicKey, UUID transactionId) throws CantGetCashMoneyWalletTransactionsException;

    void createCashMoneyWallet(String walletPublicKey, FiatCurrency fiatCurrency) throws CantCreateCashMoneyWalletException;
    boolean cashMoneyWalletExists(String walletPublicKey);

}
