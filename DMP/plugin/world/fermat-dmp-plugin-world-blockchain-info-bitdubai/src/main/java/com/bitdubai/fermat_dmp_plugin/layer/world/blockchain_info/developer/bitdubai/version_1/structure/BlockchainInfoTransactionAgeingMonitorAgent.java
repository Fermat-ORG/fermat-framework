package com.bitdubai.fermat_dmp_plugin.layer.world.blockchain_info.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.layer.dmp_world.Agent;
import com.bitdubai.fermat_api.layer.dmp_world.DealsWithWalletIdentity;
import com.bitdubai.fermat_dmp_plugin.layer.world.blockchain_info.developer.bitdubai.version_1.interfaces.DealsWithBlockchainInfoApi;
import com.bitdubai.fermat_dmp_plugin.layer.world.blockchain_info.developer.bitdubai.version_1.exceptions.CantGetConfirmationsNumberException;
import com.bitdubai.fermat_api.layer.dmp_world.wallet.exceptions.CantInitializeMonitorAgentException;
import com.bitdubai.fermat_api.layer.dmp_world.wallet.exceptions.CantStartAgentException;
import com.bitdubai.fermat_dmp_plugin.layer.world.blockchain_info.developer.bitdubai.version_1.exceptions.CantUpdateTransactionConfirmationsException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_dmp_plugin.layer.world.blockchain_info.developer.bitdubai.version_1.structure.api_v_1.APIException;
import com.bitdubai.fermat_dmp_plugin.layer.world.blockchain_info.developer.bitdubai.version_1.structure.api_v_1.blockexplorer.BlockExplorer;
import com.bitdubai.fermat_dmp_plugin.layer.world.blockchain_info.developer.bitdubai.version_1.structure.api_v_1.blockexplorer.Transaction;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * Created by leon on 5/8/15.
 */
public class BlockchainInfoTransactionAgeingMonitorAgent implements Agent, DealsWithBlockchainInfoApi, DealsWithErrors, DealsWithPluginDatabaseSystem, DealsWithPluginIdentity, DealsWithWalletIdentity {


    /**
     * DealsWithBlockchainInfoApi Interface member variables..
     */
    String apiKey;

    /**
     * DealsWithErrors Interface member variables.
     */
    ErrorManager errorManager;

    /**
     * DealsWithPluginDatabaseSystem Interface member variables.
     */
    PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * DealsWithPluginIdentity Interface member variables.
     */
    UUID pluginId;

    /**
     * DealsWithWalletIdentity Interface member variables.
     */
    UUID walletId;

    /**
     * Agent Member Variables.
     */
    Thread agentThread;
    MonitorAgent monitorAgent;

    /**
     * Constructor
     */

    public BlockchainInfoTransactionAgeingMonitorAgent(String apiKey, ErrorManager errorManager, PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId, UUID walletId){
        this.apiKey = apiKey;
        this.errorManager = errorManager;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
        this.walletId = walletId;
    }

    /**
     * Agent Interface implementation.
     */
    @Override
    public void start() throws CantStartAgentException {

        this.monitorAgent = new MonitorAgent();

        this.monitorAgent.setApiKey(this.apiKey);
        this.monitorAgent.setErrorManager(this.errorManager);
        this.monitorAgent.setPluginDatabaseSystem(this.pluginDatabaseSystem);
        this.monitorAgent.setPluginId(this.pluginId);
        this.monitorAgent.setWalletId(this.walletId);

        try {
            this.monitorAgent.Initialize();

            this.agentThread = new Thread(this.monitorAgent);
            this.agentThread.start();
        }
        catch (Exception exception) {
            System.out.println("Exception: " + exception.getMessage());
            exception.printStackTrace();
            throw new CantStartAgentException();
        }

    }

    @Override
    public void stop() {

        this.agentThread.interrupt();

    }

    /**
     * DealsWithBlockchainInfoApi Interface implementation.
     */
    @Override
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
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

    /**
     * DealsWithPluginIdentity interface implementation.
     */
    @Override
    public void setPluginId(UUID pluginId){
        this.pluginId = pluginId;
    }

    /**
     * DealsWithWalletIdentity interface implementation.
     */
    @Override
    public void setWalletId (UUID walletId){
        this.walletId = walletId;
    }












    private class MonitorAgent implements DealsWithBlockchainInfoApi, DealsWithErrors, DealsWithPluginDatabaseSystem, DealsWithPluginIdentity, DealsWithWalletIdentity, Runnable  {

        /*
         *  it runs every 7 minutes
         */
        private final int SLEEP_TIME = 420000;

        /**
         *  Minimum confirmations number
         **/
        private final String MINIMUM_CONFIRMATIONS = "7";


        /**
         * DealsWithBlockchainInfoApi Interface member variables..
         */
        String apiKey;

        /**
         * DealsWithErrors Interface member variables.
         */
        ErrorManager errorManager;

        /**
         * DealsWithPluginDatabaseSystem Interface member variables.
         */
        PluginDatabaseSystem pluginDatabaseSystem;

        /**
         * DealsWithPluginIdentity Interface member variables.
         */
        UUID pluginId;

        /**
         * DealsWithWalletIdentity Interface member variables.
         */
        UUID walletId;


        /**
         * Initialize variables
         */
        private Database database;

        /**
         * MonitorAgent interface implementation.
         */
        private void Initialize () throws CantInitializeMonitorAgentException{
            /**
             * I will try to open the wallet's database..
             */

            try {
                this.database = this.pluginDatabaseSystem.openDatabase(this.pluginId, this.walletId.toString());
            }
            catch (DatabaseNotFoundException databaseNotFoundException) {

                BlockchainInfoDatabaseFactory databaseFactory = new BlockchainInfoDatabaseFactory();
                databaseFactory.setPluginDatabaseSystem(this.pluginDatabaseSystem);

                /**
                 * I will create the database where I am going to store the information of this wallet.
                 */
                try {
                    this.database =  databaseFactory.createDatabase(this.pluginId, this.walletId);
                }
                catch (CantCreateDatabaseException cantCreateDatabaseException){

                    /**
                     * The database cannot be created. I can not handle this situation.
                     */
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BLOCKCHAIN_INFO_WORLD, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantCreateDatabaseException);
                    throw new CantInitializeMonitorAgentException();
                }
            }
            catch (CantOpenDatabaseException cantOpenDatabaseException){

                /**
                 * The database exists but cannot be open. I can not handle this situation.
                 */
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BLOCKCHAIN_INFO_WORLD, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantOpenDatabaseException);
                throw new CantInitializeMonitorAgentException();
            }
        }

        /**
         * DealsWithBlockchainInfoApi Interface implementation.
         */
        @Override
        public void setApiKey(String apiKey) {
            this.apiKey = apiKey;
        }

        /**
         * DealsWithPluginDatabaseSystem interface implementation.
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

        /**
         * DealsWithPluginIdentity interface implementation.
         */
        @Override
        public void setPluginId(UUID pluginId){
            this.pluginId = pluginId;
        }

        /**
         * DealsWithWalletIdentity interface implementation.
         */
        @Override
        public void setWalletId (UUID walletId){
            this.walletId = walletId;
        }



        /**
         * Runnable Interface implementation.
         */
        @Override
        public void run() {

            /**
             * Infinite loop.
             */
            while (true) {

                /**
                 * Now I do the main task.
                 */
                doTheMainTask();

                /**
                 * Then i sleep for a while.
                 */
                try {
                    Thread.sleep(SLEEP_TIME);
                } catch (InterruptedException interruptedException) {
                    cleanResources();
                    return;
                }

                /**
                 * Check if I have been Interrupted.
                 */
                if (Thread.currentThread().isInterrupted()) {
                    cleanResources();
                    return;
                }
            }
        }

        private void doTheMainTask() {

            /**
             * Read the recorded transactions of the wallet and update the number of confirmations.
             */
            try {
                updateTransactions();
            } catch (CantUpdateTransactionConfirmationsException e){
                //TODO comentado por error null point
                //errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BLOCKCHAIN_INFO_WORLD, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            }
        }

        private void updateTransactions() throws CantUpdateTransactionConfirmationsException {

            // api blockexplorer to bring the transactions
            BlockExplorer blockExplorer = new BlockExplorer(this.apiKey);

            /**
             * Read the transactions with less or equal than 6 current confirmations
             */
            DatabaseTable table = database.getTable(BlockchainInfoDatabaseConstants.INCOMING_CRYPTO_TABLE_NAME);
            table.setStringFilter(BlockchainInfoDatabaseConstants.INCOMING_CRYPTO_TABLE_CURRENT_CONFIRMATIONS_COLUMN_NAME, MINIMUM_CONFIRMATIONS, DatabaseFilterType.LESS_THAN);
            try {
                table.loadToMemory();
            }
            catch (CantLoadTableToMemory cantLoadTableToMemory) {
                /**
                 * I can not solve this situation.
                 */
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BLOCKCHAIN_INFO_WORLD, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantLoadTableToMemory);
                throw new CantUpdateTransactionConfirmationsException();
            }

            /**
             * for each record with less than 6 confirmations i will bring the transaction
             * to find the updated number of transactions
             * if the number of current confirmations persisted is lesser than the new one
             * i save the new value in the field current_confirmations
             * and the old_value in the field previous confirmations
             * if the number of current confirmations persisted is equals to the new one
             * and the number of persisted previous confirmations is different to current confirmations
             * i update previous confirmations to the actual value
             * current < new ----> current = new && previous = current
             * current = new && current <> previous ----> previous = current
             */
            List<DatabaseTableRecord> records = table.getRecords();

            for(DatabaseTableRecord record : records){
                String trxHash = record.getStringValue(BlockchainInfoDatabaseConstants.INCOMING_CRYPTO_TABLE_TRX_HASH_COLUMN_NAME);
                long currentConfirmations = record.getLongValue(BlockchainInfoDatabaseConstants.INCOMING_CRYPTO_TABLE_CURRENT_CONFIRMATIONS_COLUMN_NAME);
                long previousConfirmations = record.getLongValue(BlockchainInfoDatabaseConstants.INCOMING_CRYPTO_TABLE_CURRENT_CONFIRMATIONS_COLUMN_NAME);
                try {
                    int lastConfirmationsNumber = getConfirmationsNumber(blockExplorer, trxHash);
                    if (currentConfirmations < lastConfirmationsNumber) {
                        record.setLongValue(BlockchainInfoDatabaseConstants.INCOMING_CRYPTO_TABLE_CURRENT_CONFIRMATIONS_COLUMN_NAME, lastConfirmationsNumber);
                        record.setLongValue(BlockchainInfoDatabaseConstants.INCOMING_CRYPTO_TABLE_PREVIOUS_CONFIRMATIONS_COLUMN_NAME, currentConfirmations);

                        try {
                            table.updateRecord(record);

                        } catch (CantUpdateRecord cantUpdateRecord) {
                            /**
                             * I can not solve this situation.
                             */

                            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BLOCKCHAIN_INFO_WORLD, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantUpdateRecord);
                            throw new CantUpdateTransactionConfirmationsException();
                        }
                    } else if (currentConfirmations == lastConfirmationsNumber &&
                               currentConfirmations != previousConfirmations){

                        record.setLongValue(BlockchainInfoDatabaseConstants.INCOMING_CRYPTO_TABLE_PREVIOUS_CONFIRMATIONS_COLUMN_NAME, currentConfirmations);

                        try {
                            table.updateRecord(record);

                        } catch (CantUpdateRecord cantUpdateRecord) {
                            /**
                             * I can not solve this situation.
                             */
                            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BLOCKCHAIN_INFO_WORLD, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantUpdateRecord);
                            throw new CantUpdateTransactionConfirmationsException();
                        }
                    }
                } catch (CantGetConfirmationsNumberException e){
                    //TODO comentado por error null point
                    //errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BLOCKCHAIN_INFO_WORLD, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
                    //throw new CantUpdateTransactionConfirmationsException();
                }
            }
        }

        private int getConfirmationsNumber(BlockExplorer blockExplorer, String trxHash) throws CantGetConfirmationsNumberException{
            int confirmations = 0;
            try {
                if(trxHash != "") {
                    Transaction transaction = blockExplorer.getTransaction(trxHash);
                    confirmations = transaction.getOutputs().size();
                }
            } catch (APIException|IOException e){
                //TODO Comentado por error null point
                //errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BLOCKCHAIN_INFO_WORLD, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
                throw new CantGetConfirmationsNumberException();
            }
            return confirmations;
        }

        private void cleanResources() {

            /**
             * Disconnect from database and explicitly set all references to null.
             */
            this.database = null;

        }
    }
}
