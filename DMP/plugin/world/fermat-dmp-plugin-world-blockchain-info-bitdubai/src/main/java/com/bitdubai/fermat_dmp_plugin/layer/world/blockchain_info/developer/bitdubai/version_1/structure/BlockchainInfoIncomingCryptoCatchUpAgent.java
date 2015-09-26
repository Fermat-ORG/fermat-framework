package com.bitdubai.fermat_dmp_plugin.layer.world.blockchain_info.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.dmp_world.Agent;
import com.bitdubai.fermat_api.layer.dmp_world.wallet.exceptions.CantInitializeMonitorAgentException;
import com.bitdubai.fermat_api.layer.dmp_world.wallet.exceptions.CantStartAgentException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_dmp_plugin.layer.world.blockchain_info.developer.bitdubai.version_1.exceptions.CantReadWalletTransactionsException;
import com.bitdubai.fermat_dmp_plugin.layer.world.blockchain_info.developer.bitdubai.version_1.structure.api_v_1.APIException;
import com.bitdubai.fermat_dmp_plugin.layer.world.blockchain_info.developer.bitdubai.version_1.structure.api_v_1.blockexplorer.BlockExplorer;
import com.bitdubai.fermat_dmp_plugin.layer.world.blockchain_info.developer.bitdubai.version_1.structure.api_v_1.blockexplorer.Input;
import com.bitdubai.fermat_dmp_plugin.layer.world.blockchain_info.developer.bitdubai.version_1.structure.api_v_1.blockexplorer.Transaction;
import com.bitdubai.fermat_dmp_plugin.layer.world.blockchain_info.developer.bitdubai.version_1.structure.api_v_1.blockexplorer.Address;
import com.bitdubai.fermat_dmp_plugin.layer.world.blockchain_info.developer.bitdubai.version_1.structure.api_v_1.wallet.Wallet;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * Created by ciencias on 3/19/15.
 */
public class BlockchainInfoIncomingCryptoCatchUpAgent implements Agent, DealsWithErrors, DealsWithPluginDatabaseSystem {

    /**
     * DealsWithErrors Interface member variables.
     */
    ErrorManager errorManager;

    /**
     * DealsWithPluginDatabaseSystem Interface member variables.
     */
    PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * Agent Member Variables.
     */
    Thread agentThread;
    MonitorAgent monitorAgent;

    String apiKey;
    String[] addresses;
    Database database;
    UUID pluginId;
    Wallet refWallet;
    UUID walletId;

    /**
     * constructor
     */

    public BlockchainInfoIncomingCryptoCatchUpAgent(String apiKey, ErrorManager errorManager, PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId, Wallet refWallet, UUID walletId){
        this.apiKey = apiKey;
        this.errorManager = errorManager;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
        this.refWallet = refWallet;
        this.walletId = walletId;
    }

    /**
     * Agent interface implementation.
     */

    @Override
    public void start()throws CantStartAgentException {
        this.monitorAgent = new MonitorAgent();

        ((DealsWithPluginDatabaseSystem) this.monitorAgent).setPluginDatabaseSystem(this.pluginDatabaseSystem);

        ((DealsWithErrors) this.monitorAgent).setErrorManager(this.errorManager);
        this.monitorAgent.setRefWallet(this.refWallet);
        this.monitorAgent.setApiKey(this.apiKey);

        try {
            ((MonitorAgent) this.monitorAgent).Initialize();
        }
        catch (CantInitializeMonitorAgentException cantInitializeCryptoRegistryException) {
            /**
             * I cant continue if this happens.
             */
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BLOCKCHAIN_INFO_WORLD, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantInitializeCryptoRegistryException);

            throw new CantStartAgentException();
        }

        this.agentThread = new Thread(new MonitorAgent());

        this.agentThread.start();
    }

    @Override
    public void stop() {
        this.agentThread.interrupt();
    }

    /**
     *DealsWithErrors Interface implementation.
     */
    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    /**
     * DealsWithPluginDatabaseSystem interface implementation.
     */

    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }


    private class MonitorAgent implements DealsWithErrors,DealsWithPluginDatabaseSystem, Runnable  {

        private final int SLEEP_TIME = 5000;

        /**
         * DealsWithEvents Interface member variables.
         */
        ErrorManager errorManager1;

        /**
         * DealsWithPluginDatabaseSystem Interface member variables.
         */
        PluginDatabaseSystem pluginDatabaseSystem;

        Wallet refWallet;
        String apiKey;

        /**
         * MonitorAgent interface implementation.
         */
        private void Initialize () throws CantInitializeMonitorAgentException {
            /**
             * Here I open the database read the event table and load it to memory.
             */

            /**
             * I will try to open the transactions' database..
             */
            try {


                database = this.pluginDatabaseSystem.openDatabase(pluginId, walletId.toString());
            }
            catch (DatabaseNotFoundException databaseNotFoundException) {

                BlockchainInfoDatabaseFactory databaseFactory = new BlockchainInfoDatabaseFactory();
                databaseFactory.setPluginDatabaseSystem(this.pluginDatabaseSystem);

                /**
                 * I will create the database where I am going to store the information of this wallet.
                 */
                try {

                    database =  databaseFactory.createDatabase(pluginId, pluginId);

                }
                catch (CantCreateDatabaseException cantCreateDatabaseException){

                    /**
                     * The database cannot be created. I can not handle this situation.
                     */
                    this.errorManager1.reportUnexpectedPluginException(Plugins.BITDUBAI_BLOCKCHAIN_INFO_WORLD, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantCreateDatabaseException);
                    throw new CantInitializeMonitorAgentException();
                }
            }
            catch (CantOpenDatabaseException cantOpenDatabaseException){

                /**
                 * The database exists but cannot be open. I can not handle this situation.
                 */
                this.errorManager1.reportUnexpectedPluginException(Plugins.BITDUBAI_BLOCKCHAIN_INFO_WORLD, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantOpenDatabaseException);
                throw new CantInitializeMonitorAgentException();
            }



        }
        /*
                String getLastAnnouncedTrxHashByAddress(String address) throws CantGetLastAnnouncedTrxHashException {
                     //  I will load the information of table into a memory structure, and read the last transaction in status ANNOUNCED for the parametrized address

                    DatabaseTable table = database.getTable(BlockchainInfoDatabaseConstants.INCOMING_CRYPTO_TABLE_NAME);
                    table.setStringFilter(BlockchainInfoDatabaseConstants.INCOMING_CRYPTO_TABLE_STATUS_COLUMN_NAME,"ANNOUNCED", DatabaseFilterType.EQUAL);
                    table.setStringFilter(BlockchainInfoDatabaseConstants.INCOMING_CRYPTO_TABLE_CRYPTO_ADDRESS_FROM_COLUMN_NAME, address, DatabaseFilterType.EQUAL);
                    table.setFilterOrder(BlockchainInfoDatabaseConstants.INCOMING_CRYPTO_TABLE_TRX_HASH_COLUMN_NAME, DatabaseFilterOrder.DESCENDING);
                    table.setFilterTop("1");

                    try {
                        table.loadToMemory();
                    }
                    catch (CantLoadTableToMemory cantLoadTableToMemory) {
                         //I can not solve this situation.
                        this.errorManager1.reportUnexpectedPluginException(Plugins.BITDUBAI_BLOCKCHAIN_INFO_WORLD, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantLoadTableToMemory);
                        throw new CantGetLastAnnouncedTrxHashException();
                    }

                     // Get transactions data
                    List<DatabaseTableRecord> records = table.getRecords();

                    if(records.size() > 0){
                        return records.get(0).getStringValue(BlockchainInfoDatabaseConstants.INCOMING_CRYPTO_TABLE_TRX_HASH_COLUMN_NAME);
                    } else {
                        return null;
                    }
                }
        */
        String[] getAddresses() throws CantReadWalletTransactionsException {
            String[] addresses;
            try {
                // list all active addresses from your wallet
                List<com.bitdubai.fermat_dmp_plugin.layer.world.blockchain_info.developer.bitdubai.version_1.structure.api_v_1.wallet.Address> listAddresses = refWallet.listAddresses(0);
                addresses = new String[listAddresses.size()];
                for (int i = 0; i < listAddresses.size(); i++) {
                    String address = listAddresses.get(i).getAddress();
                    addresses[i] = address;
                }
            } catch (IOException|APIException e)
            {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BLOCKCHAIN_INFO_WORLD, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantReadWalletTransactionsException();
            }
            return addresses;
        }

        public void loadTransactionsByAddress(String addressString) throws CantReadWalletTransactionsException{
            /**
             * Using the API blockchain info see the list of transactions for this wallet.
             */
            // instantiate a block explorer
            BlockExplorer blockExplorer = new BlockExplorer(apiKey);
            // get a transaction by hash and list the value of all its inputs
            try {
                //tesnet wallet
                Address address = blockExplorer.getAddress(addressString);

                List<Transaction> transactions = address.getTransactions();

                // while exists new transactions i load into table.
                // when i find an existent transaction i break the while
                int z = 0;
                boolean existsNewTransactions = true;
                while (z < transactions.size() && existsNewTransactions) {
                    Transaction tx = transactions.get(z);
                    for (Input i : tx.getInputs()) {
                        /**
                         * Insert transactions on status JUST_RECEIVED .
                         * Check the transactions not exists
                         */
                        DatabaseTable table = database.getTable(BlockchainInfoDatabaseConstants.INCOMING_CRYPTO_TABLE_NAME);
                        table.setStringFilter(BlockchainInfoDatabaseConstants.INCOMING_CRYPTO_TABLE_TRX_HASH_COLUMN_NAME, tx.getHash(), DatabaseFilterType.EQUAL);
                        try {
                            table.loadToMemory();
                        } catch (CantLoadTableToMemory cantLoadTableToMemory) {
                            /**
                             * I can not solve this situation.
                             */
                            throw new CantReadWalletTransactionsException();
                        }

                        /**
                         * If transaction exists i break the while
                         */
                        if (table.getRecords().size() == 0) {
                            DatabaseTable incomingCryptoTable = database.getTable(BlockchainInfoDatabaseConstants.INCOMING_CRYPTO_TABLE_NAME);
                            DatabaseTableRecord incomingCryptoRecord = incomingCryptoTable.getEmptyRecord();

                            incomingCryptoRecord.setUUIDValue(BlockchainInfoDatabaseConstants.INCOMING_CRYPTO_TABLE_TRX_HASH_COLUMN_NAME, UUID.fromString(tx.getHash()));
                            incomingCryptoRecord.setLongValue(BlockchainInfoDatabaseConstants.INCOMING_CRYPTO_TABLE_AMOUNT_COLUMN_NAME, i.getPreviousOutput().getValue());
                            incomingCryptoRecord.setStringValue(BlockchainInfoDatabaseConstants.INCOMING_CRYPTO_TABLE_CRYPTO_ADDRESS_TO_COLUMN_NAME, i.getPreviousOutput().getAddress());
                            incomingCryptoRecord.setStringValue(BlockchainInfoDatabaseConstants.INCOMING_CRYPTO_TABLE_CRYPTO_ADDRESS_FROM_COLUMN_NAME, addressString);
                            incomingCryptoRecord.setStringValue(BlockchainInfoDatabaseConstants.INCOMING_CRYPTO_TABLE_STATUS_COLUMN_NAME, "JUST_RECEIVED");
                            incomingCryptoRecord.setLongValue(BlockchainInfoDatabaseConstants.INCOMING_CRYPTO_TABLE_CURRENT_CONFIRMATIONS_COLUMN_NAME, tx.getOutputs().size());
                            incomingCryptoRecord.setLongValue(BlockchainInfoDatabaseConstants.INCOMING_CRYPTO_TABLE_PREVIOUS_CONFIRMATIONS_COLUMN_NAME, tx.getInputs().size());

                            try {
                                incomingCryptoTable.insertRecord(incomingCryptoRecord);
                            } catch (CantInsertRecordException cantInsertRecord) {
                                /**
                                 * I can not solve this situation.
                                 */
                                throw new CantReadWalletTransactionsException();
                            }
                        } else {
                            existsNewTransactions = false;
                        }
                    }
                    z++;
                }
            }
            catch(APIException|IOException ioException)
            {
                /**
                 * I can not solve this situation.
                 */
                throw new CantReadWalletTransactionsException();
            }
        }

        /**
         *DealsWithErrors Interface implementation.
         */
        @Override
        public void setErrorManager(ErrorManager errorManager) {
            this.errorManager1 = errorManager;
        }

        /**
         * DealsWithPluginDatabaseSystem interface implementationsssaf.
         */
        @Override
        public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {

            this.pluginDatabaseSystem = pluginDatabaseSystem;
        }

        public void setApiKey(String apiKey){
            this.apiKey = apiKey;
        }

        public void setRefWallet(Wallet refWallet) {
            this.refWallet = refWallet;
        }

        /**
         * Runnable Interface implementation.
         */
        @Override
        public void run() {
            /**
             * Now I do the main task.
             */
            try{
                doTheMainTask();
            }
            catch(CantReadWalletTransactionsException cantReadWalletTransactionsException) {
                //this.errorManager1.reportUnexpectedPluginException(Plugins.BITDUBAI_BLOCKCHAIN_INFO_WORLD, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantReadWalletTransactionsException);
                // throw new CantInitializeMonitorAgentException();
            }

            /**
             * Check if I have been Interrupted.
             */
            if (Thread.currentThread().isInterrupted()) {
                cleanResources();
                return;
            }
        }


        private void doTheMainTask() throws CantReadWalletTransactionsException {
            //TODO: comentado- el metodo getAddress da error en la clase HTTPClient metodo openURL no puede encodear los parametros que se le pasan
            //  String[] addresses = this.getAddresses();

            //for(String address : addresses){
            // loadTransactionsByAddress(address);
            //}
        }

        private void cleanResources() {
            /**
             * Disconnect from database and explicitly set all references to null.
             */
            database = null;

        }
    }
}
