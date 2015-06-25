package com.bitdubai.fermat_dmp_plugin.layer.basic_wallet.bitcoin_wallet.developer.bitdubai.version_1.structure;



import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;

import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.BitcoinTransaction;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.enums.TransactionType;

import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CabtStoreMemoException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantCalculateBalanceException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantFindTransactionException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantGetTransactionsException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantRegisterCreditException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantRegisterDebitDebitException;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOperator;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilterGroup;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;


import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemory;

import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecord;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;

import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * Created by eze on 2015.06.23..
 */
public class BitcoinWalletBasicWalletDao {

    /**
     * CryptoAddressBook Interface member variables.
     */
    private Database database;

    /**
     * DealsWithErrors Interface member variables.
     */
    ErrorManager errorManager;

    /**
     * DealsWithDatabaseSystem Interface member variables.
     */
    PluginDatabaseSystem pluginDatabaseSystem;



    /**
     * Constructor.
     */
    public BitcoinWalletBasicWalletDao(ErrorManager errorManager, PluginDatabaseSystem pluginDatabaseSystem, Database database){
        /**
         * The only one who can set the pluginId is the Plugin Root.
         */
        this.errorManager = errorManager;       ;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.database = database;

    }

    /**
     * BitcoinWalletBasicWalletDao Interface implementation.
     */


    /*
     * balance must iterate over the ValueChunks table and sum the
     * fiat amount of money of the records with Status UNSPENT
     */
    long getBalance() throws CantCalculateBalanceException {
        long debit = 0;
        long credits = 0;
        long balance = 0;

        DatabaseTable bitcoinwalletTable = database.getTable(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_NAME);

        // We set the filter to get the Send Transactions
        bitcoinwalletTable.setStringFilter(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_TYPE_COLUMN_NAME, TransactionType.DEBIT.getCode(), DatabaseFilterType.EQUAL);

        // now we apply the filter
        try {
            bitcoinwalletTable.loadToMemory();
        }
        catch (CantLoadTableToMemory cantLoadTableToMemory) {
            /**
             * I can not solve this situation.
             */
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_WALLET_BASIC_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantLoadTableToMemory);

            throw new CantCalculateBalanceException();
        }

        // and finally we calculate the balance
        for(DatabaseTableRecord record : bitcoinwalletTable.getRecords())
            debit += record.getLongValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_AMOUNT_COLUMN_NAME);

        // We set the filter to get the Received Transactions

        bitcoinwalletTable.clearAllFilters();

        bitcoinwalletTable.setStringFilter(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_TYPE_COLUMN_NAME, TransactionType.CREDIT.getCode(), DatabaseFilterType.EQUAL);

        // now we apply the filter
        try {
            bitcoinwalletTable.loadToMemory();
        }
        catch (CantLoadTableToMemory cantLoadTableToMemory) {
            /**
             * I can not solve this situation.
             */
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_WALLET_BASIC_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantLoadTableToMemory);

            throw new CantCalculateBalanceException();
        }

        // and finally we calculate the balance
        for(DatabaseTableRecord record : bitcoinwalletTable.getRecords())
            credits += record.getLongValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_AMOUNT_COLUMN_NAME);


        return balance;

    }



    /*
     * Add a new debit transaction.
     */
    public void addDebit(BitcoinTransaction cryptoTransaction) throws CantRegisterDebitDebitException {

        // create the database objects
        DatabaseTable bitcoinwalletTable = database.getTable(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_NAME);
        DatabaseTableRecord debitRecord;

        //First check if the trasacction exist

        /**
         *  I will load the information of table into a memory structure, filter by transaction hash .
         */
        bitcoinwalletTable.setStringFilter(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_TRANSACTION_HASH_COLUMN_NAME, cryptoTransaction.getTramsactionHash(), DatabaseFilterType.EQUAL);


        try {
            bitcoinwalletTable.loadToMemory();
        } catch (CantLoadTableToMemory cantLoadTableToMemory) {
            /**
             * I can not solve this situation.
             */
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_WALLET_BASIC_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantLoadTableToMemory);

            throw new CantRegisterDebitDebitException();

        }

        if (bitcoinwalletTable.getRecords().size() == 0) {
            // Now we complete the debit new record
            debitRecord = bitcoinwalletTable.getEmptyRecord();

            UUID debitRecordId = UUID.randomUUID();

            debitRecord.setUUIDValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_ID_COLUMN_NAME, debitRecordId);
            debitRecord.setStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_TYPE_COLUMN_NAME, cryptoTransaction.getType().getCode());
            debitRecord.setLongValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_AMOUNT_COLUMN_NAME, cryptoTransaction.getAmount());
            debitRecord.setLongValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_STATE_COLUMN_NAME, cryptoTransaction.getTimestamp());
            debitRecord.setStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_MEMO_COLUMN_NAME, cryptoTransaction.getMemo());
            debitRecord.setLongValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_TIME_STAMP_COLUMN_NAME, cryptoTransaction.getTimestamp());
            debitRecord.setStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_TRANSACTION_HASH_COLUMN_NAME, cryptoTransaction.getTramsactionHash());
            debitRecord.setStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_ADDRESS_FROM_COLUMN_NAME, cryptoTransaction.getAddressFrom().getAddress());
            debitRecord.setStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_ADDRESS_TO_COLUMN_NAME, cryptoTransaction.getAddressTo().getAddress());

            // Now I insert debit record.
            try {
                bitcoinwalletTable.insertRecord(debitRecord);
            } catch (CantInsertRecordException cantInsertRecordException) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_WALLET_BASIC_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantInsertRecordException);

                throw new CantRegisterDebitDebitException();
            }
        }

    }

         /*
     * Add a new Credit transaction.
     */
    public void addCredit(BitcoinTransaction cryptoTransaction) throws CantRegisterCreditException {

        // create the database objects
        DatabaseTable bitcoinwalletTable = database.getTable(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_NAME);
        DatabaseTableRecord debitRecord;

        //First check if the trasacction exist

        /**
         *  I will load the information of table into a memory structure, filter by transaction hash .
         */
        bitcoinwalletTable.setStringFilter(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_TRANSACTION_HASH_COLUMN_NAME, cryptoTransaction.getTramsactionHash(), DatabaseFilterType.EQUAL);
        try {
            bitcoinwalletTable.loadToMemory();
        }
        catch (CantLoadTableToMemory cantLoadTableToMemory) {
            /**
             * I can not solve this situation.
             */
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_WALLET_BASIC_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantLoadTableToMemory);

            throw new CantRegisterCreditException();

        }

        if(bitcoinwalletTable.getRecords().size() == 0)
        {
            // Now we complete the debit new record
            debitRecord = bitcoinwalletTable.getEmptyRecord();

            UUID debitRecordId = UUID.randomUUID();

            debitRecord.setUUIDValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_ID_COLUMN_NAME , debitRecordId);
            debitRecord.setStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_TYPE_COLUMN_NAME, cryptoTransaction.getType().getCode());
            debitRecord.setLongValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_AMOUNT_COLUMN_NAME, cryptoTransaction.getAmount());
            debitRecord.setLongValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_STATE_COLUMN_NAME,  cryptoTransaction.getTimestamp());
            debitRecord.setStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_MEMO_COLUMN_NAME, cryptoTransaction.getMemo());
            debitRecord.setLongValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_TIME_STAMP_COLUMN_NAME,  cryptoTransaction.getTimestamp());
            debitRecord.setStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_TRANSACTION_HASH_COLUMN_NAME, cryptoTransaction.getTramsactionHash());
            debitRecord.setStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_ADDRESS_FROM_COLUMN_NAME, cryptoTransaction.getAddressFrom().getAddress());
            debitRecord.setStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_ADDRESS_TO_COLUMN_NAME, cryptoTransaction.getAddressTo().getAddress());

            // Now I insert debit record.
            try{
                bitcoinwalletTable.insertRecord(debitRecord);
            }
            catch(CantInsertRecordException cantInsertRecordException)
            {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_WALLET_BASIC_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantInsertRecordException);

                throw new CantRegisterCreditException();
            }
        }



    }


    public List<BitcoinTransaction> getTransactions(int max, int offset) throws CantGetTransactionsException {

        // create the database objects
        DatabaseTable bitcoinwalletTable = database.getTable(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_NAME);


        /**
         *  I will load the information of table into a memory structure, filter for count of records and page
         */
        bitcoinwalletTable.setFilterTop(String.valueOf(max));
        bitcoinwalletTable.setFilterOffSet(String.valueOf(offset));

        try {
            bitcoinwalletTable.loadToMemory();
        } catch (CantLoadTableToMemory cantLoadTableToMemory) {
            /**
             * I can not solve this situation.
             */
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_WALLET_BASIC_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantLoadTableToMemory);

            throw new CantGetTransactionsException();

        }

        List<BitcoinTransaction> bitcoinTransactionList = new ArrayList<BitcoinTransaction>();


            // Read record data and create transactions list

            for(DatabaseTableRecord record : bitcoinwalletTable.getRecords())
            {
                record.getLongValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_AMOUNT_COLUMN_NAME);

                BitcoinTransaction bitcoinTransaction = new BitcoinTransaction();

                CryptoAddress crypoAddressTo = new CryptoAddress();


                crypoAddressTo.setAddress(record.getStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_ADDRESS_TO_COLUMN_NAME));
                crypoAddressTo.setCryptoCurrency(CryptoCurrency.BITCOIN);
                bitcoinTransaction.setAddressFrom(crypoAddressTo);

                CryptoAddress crypoAddressFrom = new CryptoAddress();
                crypoAddressFrom.setAddress(record.getStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_ADDRESS_FROM_COLUMN_NAME));

                bitcoinTransaction.setAddressTo(crypoAddressFrom);
                bitcoinTransaction.setAmount(record.getLongValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_AMOUNT_COLUMN_NAME));
                bitcoinTransaction.setMemo(record.getStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_MEMO_COLUMN_NAME));
                bitcoinTransaction.setState(null);
                bitcoinTransaction.setTimestamp(record.getLongValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_TIME_STAMP_COLUMN_NAME));
                bitcoinTransaction.setTramsactionHash(record.getStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_TRANSACTION_HASH_COLUMN_NAME));
                bitcoinTransaction.setType(TransactionType.getByCode(record.getStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_TYPE_COLUMN_NAME)));

                bitcoinTransactionList.add(bitcoinTransaction);
            }







    // Now we return BitcoinTransaction list
        return  bitcoinTransactionList;
    }


    public void updateMemoFiled(UUID transactionID, String memo) throws CabtStoreMemoException, CantFindTransactionException {

        // create the database objects
        DatabaseTable bitcoinwalletTable = database.getTable(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_NAME);


        /**
         *  I will load the information of table into a memory structure, filter for transaction id
         */
        bitcoinwalletTable.setStringFilter(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_ID_COLUMN_NAME, transactionID.toString(), DatabaseFilterType.EQUAL);


        try {
            bitcoinwalletTable.loadToMemory();

        } catch (CantLoadTableToMemory cantLoadTableToMemory) {
            /**
             * I can not solve this situation.
             */
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_WALLET_BASIC_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantLoadTableToMemory);

            throw new CantFindTransactionException();

        }



        // Read record data and create transactions list

        for(DatabaseTableRecord record : bitcoinwalletTable.getRecords())
        {
            record.setStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_MEMO_COLUMN_NAME,memo);

            try {

                bitcoinwalletTable.updateRecord(record);

            } catch (CantUpdateRecord cantUpdateRecord) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_WALLET_BASIC_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantUpdateRecord);

                throw new CabtStoreMemoException();
            }

        }


    }

}