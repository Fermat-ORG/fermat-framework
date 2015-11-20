package com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_csh_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.cash_money.exceptions.CantCalculateBalanceException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.cash_money.exceptions.CantRegisterCreditException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.cash_money.exceptions.CantRegisterDebitException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.cash_money.exceptions.CantTransactionCashMoneyException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.cash_money.exceptions.CantTransactionSummaryCashMoneyException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.cash_money.interfaces.CashMoney;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.cash_money.interfaces.CashMoneyBalance;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.cash_money.interfaces.CashMoneyBalanceRecord;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.cash_money.interfaces.CashMoneyTransaction;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.cash_money.interfaces.CashMoneyTransactionSummary;
import com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.database.CashMoneyWalletDao;
import com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.exceptions.CantAddCreditException;
import com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.exceptions.CantAddDebitException;
import com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.exceptions.CantGetBalancesRecord;
import com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.exceptions.CantGetCashMoneyListException;
import com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.exceptions.CantGetCurrentBalanceException;

import java.util.List;

/**
 * Created by francisco on 21/10/15.
 */
public class ImplementCashMoney implements CashMoney, CashMoneyBalance {

    private PluginDatabaseSystem pluginDatabaseSystem;
    private Database database;
    CashMoneyWalletDao cashMoneyWalletDao = new CashMoneyWalletDao(pluginDatabaseSystem);

    /**
     *
     * @param balanceType
     * @return
     * @throws CantTransactionCashMoneyException
     */
    @Override
    public double getBookBalance(BalanceType balanceType) throws CantTransactionCashMoneyException {
        try {
            return getCurrentBalance(balanceType);
        } catch (CantGetCurrentBalanceException e) {
           throw new CantTransactionCashMoneyException(CantTransactionCashMoneyException.DEFAULT_MESSAGE,e,"Cant TransactionCashMoney CashMoneyConstructor Exception","Cant Get CurrentBalance Exception");
        }
    }

    /**
     *
     * @param balanceType
     * @return
     * @throws CantTransactionCashMoneyException
     */
    @Override
    public double getAvailableBalance(BalanceType balanceType) throws CantTransactionCashMoneyException {
        try {
            return getCurrentBalance(balanceType);
        } catch (CantGetCurrentBalanceException e) {
           throw new CantTransactionCashMoneyException(CantTransactionCashMoneyException.DEFAULT_MESSAGE,e,"Cant TransactionCashMoney CashMoneyConstructor Exception","Cant Get Current Balance Exception");
        }
    }

    /**
     *
     * @param balanceType
     * @param max
     * @param offset
     * @return
     * @throws CantTransactionCashMoneyException
     */
    @Override
    public List<CashMoneyTransaction> getTransactions(BalanceType balanceType, int max, int offset) throws CantTransactionCashMoneyException {
        return cashMoneyWalletDao.getTransactions(balanceType,max,offset);
    }

    @Override
    public CashMoneyTransactionSummary getBrokerTransactionSummary(BalanceType balanceType) throws CantTransactionSummaryCashMoneyException {
        return null;
    }

    /**
     *
     * @return
     * @throws CantCalculateBalanceException
     */
    @Override
    public double getBalance() throws CantCalculateBalanceException {
        double balanceAmount = 0;
        try {
            balanceAmount=cashMoneyWalletDao.getAmaunt();
        } catch (CantGetCashMoneyListException e) {
            throw new CantCalculateBalanceException(CantCalculateBalanceException.DEFAULT_MESSAGE,e,"Cant Calculate Balance Exception","Cant Get CashMoneyConstructor List Exception");
        }
        return balanceAmount;
    }

    /**
     *
     * @param cashMoneyBalanceRecord
     * @param balanceType
     * @throws CantRegisterDebitException
     */
    @Override
    public void debit(CashMoneyBalanceRecord cashMoneyBalanceRecord, BalanceType balanceType) throws CantRegisterDebitException {
        try {
            cashMoneyWalletDao.addDebit(cashMoneyBalanceRecord,balanceType);
        } catch (CantAddDebitException e) {
            throw new CantRegisterDebitException(CantAddDebitException.DEFAULT_MESSAGE,e,"Cant Register Debit Exception","Cant Add Debit Exception");
        }
    }

    /**
     *
     * @param cashMoneyBalanceRecord
     * @param balanceType
     * @throws CantRegisterCreditException
     */
    @Override
    public void credit(CashMoneyBalanceRecord cashMoneyBalanceRecord, BalanceType balanceType) throws CantRegisterCreditException {
        try {
            cashMoneyWalletDao.addCredit(cashMoneyBalanceRecord,balanceType);
        } catch (CantAddCreditException e) {
           throw new CantRegisterCreditException(CantRegisterCreditException.DEFAULT_MESSAGE,e,"Cant Register Credit Exception","Cant Add Credit Exception");
        }
    }

    /**
     *
     * @param balanceType
     * @return
     * @throws CantGetCurrentBalanceException
     */
    private double getCurrentBalance(BalanceType balanceType) throws CantGetCurrentBalanceException{

        try {
            return cashMoneyWalletDao.getCurrentBalance(balanceType);
        } catch (CantGetCurrentBalanceException e) {
            throw new CantGetCurrentBalanceException(CantGetCurrentBalanceException.DEFAULT_MESSAGE,e,"Cant Get Current Balance Exception","Cant Get Current Balance Exception");
        } catch (CantGetBalancesRecord cantGetBalancesRecord) {
            throw new CantGetCurrentBalanceException(CantGetCurrentBalanceException.DEFAULT_MESSAGE,cantGetBalancesRecord,"","");
        }
    }
}
