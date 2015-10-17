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
    UUID idTransaction;

    CashMoneyWalletDao cashMoneyWalletDao = new CashMoneyWalletDao(pluginDatabaseSystem);
    @Override
    public double getBalance() throws CantCalculateBalanceException {
        return 0;
    }

    /**
     *
     * @param cashMoneyBalanceRecord
     * @param balanceType
     * @throws CantRegisterDebitException
     */
    @Override
    public void debit(CashMoneyBalanceRecord cashMoneyBalanceRecord, BalanceType balanceType) throws CantRegisterDebitException {
        amountDebit = cashMoneyBalanceRecord.getAmount();
        amountDebit = amountDebit - BalanceCalculate();
        Date date = new Date();
        time = date.getTime();
        stringTime= String.valueOf(time);
        stringAmount = String.valueOf(amountDebit);
        try {
            addCashMoneyBalanceDatabase(stringAmount, "0","0",stringTime);
        } catch (CantAddCashMoneyBalanceDatabase cantAddCashMoneyBalanceDatabase) {
            cantAddCashMoneyBalanceDatabase.printStackTrace();
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
        amountCredit = cashMoneyBalanceRecord.getAmount();
        amountCredit= amountCredit+BalanceCalculate();
        Date date = new Date();
        time = date.getTime();
        stringTime= String.valueOf(time);
        stringAmount = String.valueOf(amountCredit);


        try {
            addCashMoneyBalanceDatabase("0",stringAmount,"0",stringTime);
        } catch (CantAddCashMoneyBalanceDatabase cantAddCashMoneyBalanceDatabase) {
            cantAddCashMoneyBalanceDatabase.printStackTrace();
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
    public double BalanceCalculate(){
        try {
            if (cashMoneyWalletDao.getCashMoneyBalance().size() !=0 ){
                int lastIndex = cashMoneyWalletDao.getCashMoneyBalance().size()-1;
                amountBalance = Double.parseDouble(cashMoneyWalletDao.getCashMoneyBalance().get(lastIndex).toString());
            }
            else  {
                amountBalance=0;
            }
        } catch (CantGetCashMoneyBalance cantGetCashMoneyBalance) {
            cantGetCashMoneyBalance.printStackTrace();
        }

        return amountBalance;
    }
}
