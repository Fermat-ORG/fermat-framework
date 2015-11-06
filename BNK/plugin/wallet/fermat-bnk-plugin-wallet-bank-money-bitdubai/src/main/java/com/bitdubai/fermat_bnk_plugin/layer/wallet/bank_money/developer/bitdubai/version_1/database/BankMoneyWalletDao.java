package com.bitdubai.fermat_bnk_plugin.layer.wallet.bank_money.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantRegisterDebitException;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankMoneyBalanceRecord;
import com.bitdubai.fermat_bnk_plugin.layer.wallet.bank_money.developer.bitdubai.version_1.exceptions.CantAddBankMoneyException;
import com.bitdubai.fermat_bnk_plugin.layer.wallet.bank_money.developer.bitdubai.version_1.exceptions.CantAddCreditException;
import com.bitdubai.fermat_bnk_plugin.layer.wallet.bank_money.developer.bitdubai.version_1.exceptions.CantGetBankMoneyTotalBalanceException;
import com.bitdubai.fermat_bnk_plugin.layer.wallet.bank_money.developer.bitdubai.version_1.exceptions.CantGetCurrentBalanceException;
import com.bitdubai.fermat_bnk_plugin.layer.wallet.bank_money.developer.bitdubai.version_1.exceptions.CantInitializeBankMoneyWalletDatabaseException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.TransactionType;

import java.util.UUID;

/**
 * Created by yordin on 01/10/15.
 */
public class BankMoneyWalletDao {

    /**
     *  Represent the Plugin Database.
     */
    private PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * Database connection
     */
    Database database;

    public BankMoneyWalletDao (PluginDatabaseSystem pluginDatabaseSystem){
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /**
     *
     * @param ownerId
     * @throws CantInitializeBankMoneyWalletDatabaseException
     */
    public void initializeDatabase(UUID ownerId) throws CantInitializeBankMoneyWalletDatabaseException {
        try {
             /*
              * Open new database connection
              */
            database = this.pluginDatabaseSystem.openDatabase(ownerId, BankMoneyWalletDatabaseConstants.DATABASE_NAME);
            database.closeDatabase();
        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
            throw new CantInitializeBankMoneyWalletDatabaseException(
                    CantInitializeBankMoneyWalletDatabaseException.DEFAULT_MESSAGE,
                    cantOpenDatabaseException,
                    "initializeDatabase",
                    "Cant Initialize CashMoneyConstructor WalletDatabase Exception - Cant Open Database Exception");
        } catch (DatabaseNotFoundException e) {
             /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */
            BankMoneyWalletDatabaseFactory cashMoneyWalletDatabaseFactory = new BankMoneyWalletDatabaseFactory(pluginDatabaseSystem);
            try {
                  /*
                   * We create the new database
                   */
                database = cashMoneyWalletDatabaseFactory.createDatabase(ownerId, BankMoneyWalletDatabaseConstants.DATABASE_NAME);
                database.closeDatabase();
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                throw new CantInitializeBankMoneyWalletDatabaseException(
                        CantInitializeBankMoneyWalletDatabaseException.DEFAULT_MESSAGE,
                        cantCreateDatabaseException,
                        "initializeDatabase",
                        "Cant Initialize BankMoney WalletDatabase Exception - Cant Create Database Exception");
            }
        }
    }
    public void addBankMoney(BankMoneyBalanceRecord bankMoneyBalanceRecord,
                             BalanceType balanceType,
                             TransactionType transactionType,
                             long runingBookBalance,
                             long runingAvalibleBalance,
                             String publicKeyBroker,
                             String publicKeyCustomer) throws CantAddBankMoneyException {
        try {
        DatabaseTable table = this.database.getTable(BankMoneyWalletDatabaseConstants.BANK_MONEY_TABLE_NAME);
        DatabaseTableRecord record = table.getEmptyRecord();

        record.setUUIDValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_BANK_TRANSACTION_ID_COLUMN_NAME, bankMoneyBalanceRecord.getBankTransactionId());
        record.setStringValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_PUBLIC_KEY_CUSTOMER_COLUMN_NAME, publicKeyCustomer);
        record.setStringValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_PUBLIC_KEY_BROKER_COLUMN_NAME, publicKeyBroker);
        record.setStringValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_BALANCE_TYPE_COLUMN_NAME, balanceType.getCode());
        record.setStringValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_TRANSACTION_TYPE_COLUMN_NAME, transactionType.getCode());
        record.setDoubleValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_AMOUNT_COLUMN_NAME, bankMoneyBalanceRecord.getAmount());
        record.setStringValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_BANK_CURRENCY_TYPE_COLUMN_NAME, bankMoneyBalanceRecord.getBankCurrencyType().getCode());
        record.setStringValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_BANK_OPERATION_TYPE_COLUMN_NAME, bankMoneyBalanceRecord.getBankOperationType().getCode());
        record.setStringValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_BANK_DOCUMENT_REFERENCE_COLUMN_NAME, bankMoneyBalanceRecord.getBankDocumentReference());
        record.setStringValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_BANK_NAME_COLUMN_NAME, bankMoneyBalanceRecord.getBankName());
        record.setStringValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_BANK_ACCOUNT_NUMBER_COLUMN_NAME, bankMoneyBalanceRecord.getBankAccountNumber());
        record.setStringValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_BANK_ACCOUNT_TYPE_COLUMN_NAME, bankMoneyBalanceRecord.getBankAccountType().getCode());
        record.setLongValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_RUNNING_BOOK_BALANCE_COLUMN_NAME, runingBookBalance);
        record.setLongValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_RUNNING_AVAILABLE_BALANCE_COLUMN_NAME, runingAvalibleBalance);
        record.setLongValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_TIMESTAMP_COLUMN_NAME, bankMoneyBalanceRecord.getTimestamp());
        record.setStringValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_MEMO_COLUMN_NAME, bankMoneyBalanceRecord.getMemo());
        record.setStringValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_STATUS_COLUMN_NAME, bankMoneyBalanceRecord.getStatus().getCode());

            table.insertRecord(record);
            database.closeDatabase();
        } catch (CantInsertRecordException e) {
            throw new CantAddBankMoneyException(CantAddBankMoneyException.DEFAULT_MESSAGE,e,"Cant Add Bank Money Exception","Cant Insert Record Exception");
        }
    }
    void addDebit(BankMoneyBalanceRecord BankMoneyBalanceRecord, BalanceType balanceType) throws CantRegisterDebitException{
        try {
            double availableAmount;
            double bookAmount;
            long runningAvailableBalance = 0;
            long runningBookBalance = 0;

            String publicKeyBroker = null;
            String publicKeyCustomer = null;

            if (balanceType == BalanceType.AVAILABLE){
                availableAmount  = BankMoneyBalanceRecord.getAmount();
                runningAvailableBalance = (long) (getCurrentBalance(BalanceType.AVAILABLE) + (-availableAmount));
                addBankMoney(BankMoneyBalanceRecord, balanceType, TransactionType.DEBIT, runningBookBalance, runningAvailableBalance, publicKeyBroker, publicKeyCustomer);

            }
            if (balanceType == BalanceType.BOOK) {
                bookAmount = BankMoneyBalanceRecord.getAmount();

                runningBookBalance = (long) (getCurrentBalance(BalanceType.BOOK) + (-bookAmount));
                addBankMoney(BankMoneyBalanceRecord, balanceType, TransactionType.DEBIT, runningBookBalance, runningAvailableBalance, publicKeyBroker, publicKeyCustomer);
            }
                } catch (CantGetCurrentBalanceException e) {
            throw new CantRegisterDebitException(CantRegisterDebitException.DEFAULT_MESSAGE,e,"Cant Register Debit Exception","Cant Get Current Balance Exception");
                } catch (CantGetBankMoneyTotalBalanceException e) {
                    e.printStackTrace();
                } catch (CantAddBankMoneyException e) {
            throw new CantRegisterDebitException(CantRegisterDebitException.DEFAULT_MESSAGE,e,"Cant Register Debit Exception","Cant Add Bank Money Exception");
        }
    }
    void addCredit(BankMoneyBalanceRecord BankMoneyBalanceRecord, BalanceType balanceType) throws CantAddCreditException {
        try {
            double availableAmount;
            double bookAmount;
            long runningAvailableBalance = 0;
            long runningBookBalance = 0;

            String publicKeyBroker = null;
            String publicKeyCustomer = null;

            if (balanceType == BalanceType.AVAILABLE){
                availableAmount  = BankMoneyBalanceRecord.getAmount();
                runningAvailableBalance = (long) (getCurrentBalance(BalanceType.AVAILABLE) + (-availableAmount));
                addBankMoney(BankMoneyBalanceRecord, balanceType, TransactionType.CREDIT, runningBookBalance, runningAvailableBalance, publicKeyBroker, publicKeyCustomer);

            }
            if (balanceType == BalanceType.BOOK) {
                bookAmount = BankMoneyBalanceRecord.getAmount();

                runningBookBalance = (long) (getCurrentBalance(BalanceType.BOOK) + (-bookAmount));
                addBankMoney(BankMoneyBalanceRecord, balanceType, TransactionType.CREDIT, runningBookBalance, runningAvailableBalance, publicKeyBroker, publicKeyCustomer);
            }
        } catch (CantGetCurrentBalanceException e) {
            throw new CantAddCreditException(CantAddCreditException.DEFAULT_MESSAGE,e,"Cant Add Credit Exception","Cant Get Current Balance Exception");
        } catch (CantGetBankMoneyTotalBalanceException e) {
            e.printStackTrace();
        } catch (CantAddBankMoneyException e) {
            throw new CantAddCreditException(CantAddCreditException.DEFAULT_MESSAGE,e,"Cant Add Credit Exception","Cant Add Bank Money Exception");
        }
    }
    /**
     *
      * @param balanceType
     * @return
     * @throws CantGetCurrentBalanceException
     * @throws CantGetBankMoneyTotalBalanceException
     */
    public double getCurrentBalance(BalanceType balanceType) throws CantGetCurrentBalanceException, CantGetBankMoneyTotalBalanceException {
        if (balanceType == balanceType.AVAILABLE)
            return Double.valueOf(getBankMoneyTotalBalance().getStringValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_TOTAL_BALANCES_AVAILABLE_BALANCE_COLUMN_NAME));
        else
            return Double.valueOf(getBankMoneyTotalBalance().getStringValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_TOTAL_BALANCES_BOOK_BALANCE_COLUMN_NAME));
    }

    /**
     *
     * @return
     * @throws CantGetBankMoneyTotalBalanceException
     */
    private DatabaseTableRecord getBankMoneyTotalBalance() throws CantGetBankMoneyTotalBalanceException {
        try {
            DatabaseTable balancesTable = database.getTable(BankMoneyWalletDatabaseConstants.BANK_MONEY_TOTAL_BALANCES_TABLE_NAME);
            balancesTable.loadToMemory();
            return balancesTable.getRecords().get(0);
        } catch (CantLoadTableToMemoryException e) {
           throw new CantGetBankMoneyTotalBalanceException(CantGetBankMoneyTotalBalanceException.DEFAULT_MESSAGE,e,"Cant Get Bank Money Total Balance Exception ","Cant Load Table To Memory Exception");
        }
    }
}
