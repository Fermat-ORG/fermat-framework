package com.bitdubai.fermat_bnk_plugin.layer.wallet.bank_money.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_bnk_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantGetBankMoneyWalletTransactionsException;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantGetHeldFundsException;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankMoneyTransactionRecord;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankMoneyWallet;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankMoneyWalletBalance;
import com.bitdubai.fermat_bnk_plugin.layer.wallet.bank_money.developer.bitdubai.version_1.database.BankMoneyWalletDao;
import com.bitdubai.fermat_bnk_plugin.layer.wallet.bank_money.developer.bitdubai.version_1.database.BankMoneyWalletDatabaseConstants;
import com.bitdubai.fermat_bnk_plugin.layer.wallet.bank_money.developer.bitdubai.version_1.exceptions.CantInitializeBankMoneyWalletDatabaseException;

import java.util.List;
import java.util.UUID;

/**
 * Created by memo on 23/11/15.
 */
public class BankMoneyWalletImpl implements BankMoneyWallet {

    UUID pluginId;
    Database database;
    PluginDatabaseSystem pluginDatabaseSystem;

    BankMoneyWalletDao bankMoneyWalletDao;

    public BankMoneyWalletImpl(UUID pluginId, PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginId = pluginId;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    public void initialize() throws CantInitializeBankMoneyWalletDatabaseException{
        try {
            database = this.pluginDatabaseSystem.openDatabase(pluginId, BankMoneyWalletDatabaseConstants.DATABASE_NAME);
            //bankMoneyWalletBalance = new BankMoneyWalletBalanceImpl(database);
        }catch (CantOpenDatabaseException | DatabaseNotFoundException e){
            throw new CantInitializeBankMoneyWalletDatabaseException(CantInitializeBankMoneyWalletDatabaseException.DEFAULT_MESSAGE,e,"Couldn't wallet database",null);
        }
    }

    @Override
    public BankMoneyWalletBalance getBookBalance() {
        return new BankMoneyWalletBalanceImpl(database, BalanceType.BOOK);
    }

    @Override
    public BankMoneyWalletBalance getAvailableBalance() {
        return new BankMoneyWalletBalanceImpl(database, BalanceType.AVAILABLE);
    }

    @Override
    public List<BankMoneyTransactionRecord> getTransactions(TransactionType type, int max, int offset) throws CantGetBankMoneyWalletTransactionsException {
        return null;
    }

    @Override
    public double getHeldFunds() throws CantGetHeldFundsException, CantGetHeldFundsException {
        return 0;
    }
}
