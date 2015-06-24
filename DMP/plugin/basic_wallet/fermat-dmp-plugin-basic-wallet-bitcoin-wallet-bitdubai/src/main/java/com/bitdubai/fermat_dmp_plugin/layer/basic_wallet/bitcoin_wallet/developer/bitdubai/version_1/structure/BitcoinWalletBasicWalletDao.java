package com.bitdubai.fermat_dmp_plugin.layer.basic_wallet.bitcoin_wallet.developer.bitdubai.version_1.structure;



import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;

import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.BitcoinTransaction;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.enums.TransactionType;

import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantCalculateBalanceException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantRegisterCreditException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantRegisterDebitDebitException;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;


import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemory;

import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;

import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;


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
    public BitcoinWalletBasicWalletDao(ErrorManager errorManager, PluginDatabaseSystem pluginDatabaseSystem){
        /**
         * The only one who can set the pluginId is the Plugin Root.
         */
        this.errorManager = errorManager;       ;
        this.pluginDatabaseSystem = pluginDatabaseSystem;

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



}