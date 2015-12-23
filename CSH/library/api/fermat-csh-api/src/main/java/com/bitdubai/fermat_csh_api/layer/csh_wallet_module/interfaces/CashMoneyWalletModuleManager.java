package com.bitdubai.fermat_csh_api.layer.csh_wallet_module.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.interfaces.FermatSettings;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;
import com.bitdubai.fermat_csh_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_csh_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_csh_api.all_definition.interfaces.CashWalletBalances;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.deposit.exceptions.CantCreateDepositTransactionException;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.deposit.interfaces.CashDepositTransaction;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.deposit.interfaces.CashDepositTransactionParameters;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.withdrawal.exceptions.CantCreateWithdrawalTransactionException;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.withdrawal.interfaces.CashWithdrawalTransaction;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.withdrawal.interfaces.CashWithdrawalTransactionParameters;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantCreateCashMoneyWalletException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantGetCashMoneyWalletCurrencyException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantGetCashMoneyWalletTransactionsException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.interfaces.CashMoneyWalletTransaction;
import com.bitdubai.fermat_csh_api.layer.csh_wallet_module.exceptions.CantGetCashMoneyWalletBalancesException;

import java.util.List;

/**
 * Created by Alejandro Bicelis on 12/8/2015.
 */
public interface CashMoneyWalletModuleManager extends ModuleManager<FermatSettings, ActiveActorIdentityInformation> {

    CashWalletBalances getWalletBalances(String walletPublicKey) throws CantGetCashMoneyWalletBalancesException;
    FiatCurrency getWalletCurrency(String walletPublicKey) throws CantGetCashMoneyWalletCurrencyException;

    CashDepositTransaction createCashDepositTransaction(CashDepositTransactionParameters depositParameters) throws CantCreateDepositTransactionException;
    CashWithdrawalTransaction createCashWithdrawalTransaction(CashWithdrawalTransactionParameters withdrawalParameters) throws CantCreateWithdrawalTransactionException;

    List<CashMoneyWalletTransaction> getTransactions(String walletPublicKey, List<TransactionType> transactionTypes, List<BalanceType> balanceTypes, int max, int offset) throws CantGetCashMoneyWalletTransactionsException;

    boolean cashMoneyWalletExists(String walletPublicKey);

    void createCashMoneyWallet(String walletPublicKey, FiatCurrency fiatCurrency) throws CantCreateCashMoneyWalletException;

}
