package com.bitdubai.fermat_dmp_plugin.layer.world.blockchain_info.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.dmp_world.Agent;
import com.bitdubai.fermat_api.layer.dmp_world.wallet.exceptions.CantInitializeMonitorAgentException;
import com.bitdubai.fermat_api.layer.dmp_world.wallet.exceptions.CantStartAgentException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOrder;
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
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.EventManager;
import com.bitdubai.fermat_dmp_plugin.layer.world.blockchain_info.developer.bitdubai.version_1.exceptions.CantReadAnnouncedTransactionException;

import java.util.List;
import java.util.UUID;

/**
 * Created by ciencias on 3/19/15.
 */
public class BlockchainInfoIncomingCryptoAnnouncerAgent implements Agent, DealsWithEvents,DealsWithErrors,DealsWithPluginDatabaseSystem {

    /**
     * DealsWithEvents Interface member variables.
     */
    ErrorManager errorManager;

    /**
     * DealWithEvents Interface member variables.
     */
    EventManager eventManager;

    /**
     * DealsWithPluginDatabaseSystem Interface member variables.
     */
    PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * Agent Member Variables.
     */
    Thread agentThread;
    MonitorAgent monitorAgent;

    Database database;
    DatabaseTable table;
    UUID pluginId;
    UUID walletId;

    /**
     * Constructor
     */

    public BlockchainInfoIncomingCryptoAnnouncerAgent(ErrorManager errorManager, PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId, UUID walletId){
        this.errorManager = errorManager;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
        this.walletId = walletId;
    }

    /**
     * Agent interface implementation.
     */

    @Override
    public void start()throws  CantStartAgentException{
        this.monitorAgent = new MonitorAgent ();

        ((DealsWithPluginDatabaseSystem) this.monitorAgent).setPluginDatabaseSystem(this.pluginDatabaseSystem);
        ((DealsWithErrors) this.monitorAgent).setErrorManager(this.errorManager);

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
     * DealWithEvents Interface implementation.
     */

    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
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
        ErrorManager errorManager;

        /**
         * DealsWithPluginDatabaseSystem Interface member variables.
         */
        PluginDatabaseSystem pluginDatabaseSystem;


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
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BLOCKCHAIN_INFO_WORLD, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantCreateDatabaseException);
                    throw new CantInitializeMonitorAgentException();
                }
            }
            catch (CantOpenDatabaseException cantOpenDatabaseException){

                /**
                 * The database exists but cannot be open. I can not handle this situation.
                 */
                //errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BLOCKCHAIN_INFO_WORLD, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantOpenDatabaseException);
                throw new CantInitializeMonitorAgentException();
            }

            readEvents();
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
         * Runnable Interface implementation.
         */
        @Override
        public void run() {


            /**
             * Infinite loop.
             */
            while (true == true) {

                /**
                 * Sleep for a while.
                 */
                try {
                    Thread.sleep(SLEEP_TIME);
                } catch (InterruptedException interruptedException) {
                    cleanResources();
                    return;
                }

                /**
                 * Now I do the main task.
                 */
                try
                {
                    doTheMainTask();
                }
                catch ( CantReadAnnouncedTransactionException cantReadAnnouncedTransactionException)
                {
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BLOCKCHAIN_INFO_WORLD, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantReadAnnouncedTransactionException);

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

        private void doTheMainTask() throws CantReadAnnouncedTransactionException {

            boolean existsNewTransaction = true;

            while (existsNewTransaction){
                /**
                 * Read de older transaction with status JUST_RECEIVED
                 */
                table = database.getTable(BlockchainInfoDatabaseConstants.INCOMING_CRYPTO_TABLE_NAME);
                table.setStringFilter(BlockchainInfoDatabaseConstants.INCOMING_CRYPTO_TABLE_STATUS_COLUMN_NAME,"JUST_RECEIVED", DatabaseFilterType.EQUAL);
                table.setFilterOrder(BlockchainInfoDatabaseConstants.INCOMING_CRYPTO_TABLE_TRX_HASH_COLUMN_NAME, DatabaseFilterOrder.ASCENDING);
                table.setFilterTop("1");
                try {
                    table.loadToMemory();
                }
                catch (CantLoadTableToMemory cantLoadTableToMemory) {
                    /**
                     * I can not solve this situation.
                     */
                   // errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BLOCKCHAIN_INFO_WORLD, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantLoadTableToMemory);
                    throw new CantReadAnnouncedTransactionException();
                }
                //table.clearAllFilters();

                /**
                 * Get Transaction record
                 */

                List<DatabaseTableRecord> records = table.getRecords();

                if(records.size() > 0) {
                    /**
                     * Update status to TO_BE_ANNOUNCED
                     */
                    String trxHash = records.get(0).getStringValue(BlockchainInfoDatabaseConstants.INCOMING_CRYPTO_TABLE_TRX_HASH_COLUMN_NAME);
                    records.get(0).setStringValue(BlockchainInfoDatabaseConstants.INCOMING_CRYPTO_TABLE_STATUS_COLUMN_NAME, "TO_BE_ANNOUNCED");
                    table.setStringFilter(BlockchainInfoDatabaseConstants.INCOMING_CRYPTO_TABLE_TRX_HASH_COLUMN_NAME, trxHash, DatabaseFilterType.EQUAL);
                    try {
                        table.updateRecord(records.get(0));

                    } catch (CantUpdateRecord cantUpdateRecord) {
                        /**
                         * I can not solve this situation.
                         */

                        //errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BLOCKCHAIN_INFO_WORLD, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,cantUpdateRecord);
                        throw new CantReadAnnouncedTransactionException();
                    }

                    table.clearAllFilters();
                    /**
                     * Then fires the event announcing the rest of the plugins platform that the transaction was received .
                     */

                //TODO comentado por error, eventManager is null
                   // PlatformEvent platformEvent = eventManager.getNewEvent(EventType.INCOMING_CRYPTO_RECEIVED);
                    //((IncomingCryptoReceivedEvent) platformEvent).setSource(EventSource.WORLD_BLOCKCHAIN_INFO_PLUGIN);
                   // platformEvent.setSource(EventSource.WORLD_BLOCKCHAIN_INFO_PLUGIN);
                   // eventManager.raiseEvent(platformEvent);

                    /**
                     * If the firing event was successful , updates the status to ANNOUNCED .
                     */

                    records.get(0).setStringValue(BlockchainInfoDatabaseConstants.INCOMING_CRYPTO_TABLE_STATUS_COLUMN_NAME, "ANNOUNCED");
                    table.setStringFilter(BlockchainInfoDatabaseConstants.INCOMING_CRYPTO_TABLE_TRX_HASH_COLUMN_NAME, trxHash, DatabaseFilterType.EQUAL);

                    try {
                        table.updateRecord(records.get(0));

                    } catch (CantUpdateRecord cantUpdateRecord) {
                        /**
                         * I can not solve this situation.
                         */

                        //errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BLOCKCHAIN_INFO_WORLD, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,cantUpdateRecord);
                        throw new CantReadAnnouncedTransactionException();
                    }
                } else {
                    existsNewTransaction = false;
                }
            }

            /**
             * Read the EventsRecorded table to see if there are new Events.
             */
            readEvents();

            /**
             * If there are, I bring the transactions to this plug-in.
             */

        }

        private void readEvents() {

        }

        private void cleanResources() {

            /**
             * Disconnect from database and explicitly set all references to null.
             */
            database = null;
            table = null;
        }
    }
}
