package com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.events;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.dmp_world.Agent;
import com.bitdubai.fermat_api.layer.dmp_world.wallet.exceptions.CantInitializeMonitorAgentException;
import com.bitdubai.fermat_api.layer.dmp_world.wallet.exceptions.CantStartAgentException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.CryptoVaultDatabaseActions;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.CryptoVaultDatabaseFactory;

import java.util.UUID;

/**
 * Created by rodrigo on 2015.06.18..
 */
public class TransactionNotificationAgent implements Agent, DealsWithErrors, DealsWithPluginDatabaseSystem, DealsWithPluginIdentity {

        /**
     * TransactionNotificationAgent variables
     */
    Database database;
    UUID walletId;

    /**
     * Agent interface member variables
     */
    Thread agentThread;
    MonitorAgent monitorAgent;

    /**
     * DealsWithErrors interface member variables
     */
    ErrorManager errorManager;

    /**
     * DealsWithPluginDatabaseSystem interface member variable
     */
    PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * DealsWithPluginIdentity interfaz member variable
     */
    UUID pluginId;


    /**
     * Constructor
     */
    public void TransactionNotificationAgent(PluginDatabaseSystem pluginDatabaseSystem, ErrorManager errorManager, UUID pluginId, UUID walletId){
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.errorManager = errorManager;
        this.pluginId = pluginId;
        this.walletId = walletId;
    }

    /**
     * Agent interface implementation
     * @throws CantStartAgentException
     */
    @Override
    public void start() throws CantStartAgentException {
        monitorAgent = new MonitorAgent();

        ((DealsWithPluginDatabaseSystem) this.monitorAgent).setPluginDatabaseSystem(this.pluginDatabaseSystem);
        ((DealsWithErrors) this.monitorAgent).setErrorManager(this.errorManager);

        try {
            ((MonitorAgent) this.monitorAgent).Initialize();
        }
        catch (CantInitializeMonitorAgentException cantInitializeCryptoRegistryException) {
            /**
             * I cant continue if this happens.
             */
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_CRYPTO_VAULT, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantInitializeCryptoRegistryException);
            throw new CantStartAgentException();
        }

        this.agentThread = new Thread(monitorAgent);
        this.agentThread.start();
    }

    @Override
    public void stop() {

    }

    /**
     * DealsWithPluginDatabaseSystem interfaz implementation
     */
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /**
     * DealsWithErrors interface implementation
     */
     @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    /**
     * DealsWithPlugInIdentity interface implementation
     * @param pluginId
     */
    @Override
    public void setPluginId(UUID pluginId) {
        this.pluginId = pluginId;
    }

    /**
     * Private class which implements runnable and is started by the Agent
     */
    private class MonitorAgent implements DealsWithPluginDatabaseSystem, DealsWithErrors, Runnable{

        /**
         * how often I will search for transactions to notify
         */
        public final int SLEEP_TIME = 10000;

        /**
         * PluginDatabaseSystem interfaz member variables
         */
        PluginDatabaseSystem pluginDatabaseSystem;

        /**
         * DealsWithErrors interfaz member variables
         */
        ErrorManager errorManager;

        /**
         * DealsWithErrors interface implementation
         * @param errorManager
         */
        @Override
        public void setErrorManager(ErrorManager errorManager) {
            this.errorManager = errorManager;
        }

        @Override
        public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
            this.pluginDatabaseSystem = pluginDatabaseSystem;
        }

        public void Initialize() throws CantInitializeMonitorAgentException {
            /**
             * I open the database
             */
            try {

                database = this.pluginDatabaseSystem.openDatabase(pluginId, walletId.toString());
            }
            catch (DatabaseNotFoundException databaseNotFoundException) {
                /**
                 * if the database doesnt exists, I will create it.
                 */
                CryptoVaultDatabaseFactory databaseFactory = new CryptoVaultDatabaseFactory();
                databaseFactory.setPluginDatabaseSystem(this.pluginDatabaseSystem);

                /**
                 * I will create the database where I am going to store the information of this wallet.
                 */
                try {

                    database =  databaseFactory.createDatabase(pluginId, walletId);

                }
                catch (CantCreateDatabaseException cantCreateDatabaseException){

                    /**
                     * The database cannot be created. I can not handle this situation.
                     */
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_CRYPTO_VAULT, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantCreateDatabaseException);
                    throw new CantInitializeMonitorAgentException();
                }
            }
            catch (CantOpenDatabaseException cantOpenDatabaseException){

                /**
                 * The database exists but cannot be open. I can not handle this situation.
                 */
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_CRYPTO_VAULT, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantOpenDatabaseException);
                throw new CantInitializeMonitorAgentException();
            }

        }

        @Override
        public void run() {
            /**
             * this will run in an infinite loop
             */
            while (true)
            {
                try {
                    Thread.sleep(SLEEP_TIME);
                } catch (InterruptedException interruptedException) {
                    return;
                }

                /**
                 * now I will check if there are pending transactions to raise the event
                 */
                doTheMainTask();
            }

        }

        /**
         * Implements the agent
         */
        private void doTheMainTask() {
            /**
             * I search for transactions not yet notified. If I found something, Ill raise an event
             */
            if (isTransactionToBeNotified()){
                //raiseevent
            }

        }

        private boolean isTransactionToBeNotified() {
            CryptoVaultDatabaseActions db = new CryptoVaultDatabaseActions(database);
            
            return false;
        }
    }



}
