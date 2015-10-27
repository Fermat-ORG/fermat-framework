package com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_csh_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.cash_money.exceptions.CantCalculateBalanceException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.cash_money.exceptions.CantRegisterCreditException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.cash_money.exceptions.CantRegisterDebitException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.cash_money.interfaces.CashMoneyBalance;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.cash_money.interfaces.CashMoneyBalanceRecord;
import com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.database.CashMoneyWalletDao;
import com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.exceptions.CantAddCashMoneyBalance;
import com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.exceptions.CantAddCashMoneyBalanceDatabase;
import com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.exceptions.CantGetCashMoneyBalance;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.interfaces.DeviceUserManager;

import java.util.Date;
import java.util.UUID;

/**
 * Created by francisco on 14/10/15.
 */
public class ImplementCashMoneyBalance implements CashMoneyBalance{

    PluginDatabaseSystem pluginDatabaseSystem;

    private double amountDebit;
    private double amountCredit;
    private double amountBalance;
    private long time;
    private String stringTime ;
    private String stringAmount;
    private String stringBalance;
    private BalanceType balanceType;
    UUID idTransaction;

    private DeviceUserManager deviceUserManager;

    CashMoneyWalletDao cashMoneyWalletDao = new CashMoneyWalletDao(pluginDatabaseSystem);
    ImplementCashMoneyBalanceRecord implementCashMoneyBalanceRecord = new ImplementCashMoneyBalanceRecord();

    /**
     *
     * @return
     * @throws CantCalculateBalanceException
     */
    @Override
    public double getBalance() throws CantCalculateBalanceException {
        try {
            return amountBalance = cashMoneyWalletDao.getCashMoneyBalance(balanceType);
        } catch (CantGetCashMoneyBalance cantGetCashMoneyBalance) {
            throw new CantCalculateBalanceException(CantCalculateBalanceException.DEFAULT_MESSAGE,cantGetCashMoneyBalance,"Cant Get CashMoney Balance","Cant Calculate Balance Exception");
        }

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
            amountDebit = getBalance() - cashMoneyBalanceRecord.getAmount();
            Date date = new Date();
            time = date.getTime();
            stringTime= String.valueOf(time);
            stringAmount = String.valueOf(amountDebit);
            addCashMoneyBalanceDatabase(stringAmount, "0","0",stringTime);
        } catch (CantAddCashMoneyBalanceDatabase cantAddCashMoneyBalanceDatabase) {
            throw new CantRegisterDebitException(CantRegisterDebitException.DEFAULT_MESSAGE,cantAddCashMoneyBalanceDatabase,"Cant Calculate Balance Exception","Cant Add CashMoney Balance Database");

        } catch (CantCalculateBalanceException e) {
            throw new CantRegisterDebitException(CantRegisterDebitException.DEFAULT_MESSAGE,e,"Cant Calculate Balance Exception","Cant Register Debit Exception");
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
           amountCredit = getBalance() + cashMoneyBalanceRecord.getAmount();
           Date date = new Date();
           time = date.getTime();
           stringTime= String.valueOf(time);
           stringAmount = String.valueOf(amountCredit);
            addCashMoneyBalanceDatabase("0",stringAmount,"0",stringTime);
        } catch (CantAddCashMoneyBalanceDatabase cantAddCashMoneyBalanceDatabase) {
           throw new CantRegisterCreditException(CantRegisterCreditException.DEFAULT_MESSAGE,cantAddCashMoneyBalanceDatabase,"Cant Register Credit Exception","Cant Add CashMoney Balance Database");
        } catch (CantCalculateBalanceException e) {
           throw new CantRegisterCreditException(CantRegisterCreditException.DEFAULT_MESSAGE,e,"Cant Register Credit Exception","Cant Calculate Balance Exception");
       }
    }

    /**
     *
     * @param cashMoneyDebit
     * @param cashMoneyCredit
     * @param cashMoneyBalance
     * @param time
     * @throws CantAddCashMoneyBalanceDatabase
     */
    public void addCashMoneyBalanceDatabase(String cashMoneyDebit, String cashMoneyCredit, String cashMoneyBalance, String time) throws CantAddCashMoneyBalanceDatabase {
        try {
            cashMoneyWalletDao.addCashMoneyBalance(UUID.randomUUID().toString(), cashMoneyDebit, cashMoneyCredit,cashMoneyBalance,time);
        } catch (CantAddCashMoneyBalance cantAddCashMoneyBalance) {
            throw new CantAddCashMoneyBalanceDatabase(CantAddCashMoneyBalanceDatabase.DEFAULT_MESSAGE,cantAddCashMoneyBalance,"Cant Add Cash Money Balance","Cant Register Debit Exception");
        }
    }

}
