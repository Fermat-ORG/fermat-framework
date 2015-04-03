package com.bitdubai.fermat_core.layer._13_transaction.incoming_crypto.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer._13_transaction.incoming_crypto.IncomingCryptoManager;
import com.bitdubai.fermat_api.layer._13_transaction.incoming_crypto.Registry;
import com.bitdubai.fermat_api.layer._1_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer._1_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer._2_os.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer._2_os.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer._2_os.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer._2_os.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer._3_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer._3_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.DealsWithEvents;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventManager;
import com.bitdubai.fermat_core.layer._13_transaction.incoming_crypto.developer.bitdubai.version_1.exceptions.CantInitializeCryptoRegistryException;
import com.bitdubai.fermat_core.layer._13_transaction.incoming_crypto.developer.bitdubai.version_1.exceptions.CantStartAgentException;
import com.bitdubai.fermat_core.layer._13_transaction.incoming_crypto.developer.bitdubai.version_1.exceptions.CantStartServiceException;
import com.bitdubai.fermat_core.layer._13_transaction.incoming_crypto.developer.bitdubai.version_1.interfaces.TransactionAgent;
import com.bitdubai.fermat_core.layer._13_transaction.incoming_crypto.developer.bitdubai.version_1.interfaces.TransactionService;
import com.bitdubai.fermat_core.layer._13_transaction.incoming_crypto.developer.bitdubai.version_1.structure.IncomingCryptoEventRecorderService;
import com.bitdubai.fermat_core.layer._13_transaction.incoming_crypto.developer.bitdubai.version_1.structure.IncomingCryptoMonitorAgent;
import com.bitdubai.fermat_core.layer._13_transaction.incoming_crypto.developer.bitdubai.version_1.structure.IncomingCryptoRegistry;
import com.bitdubai.fermat_core.layer._13_transaction.incoming_crypto.developer.bitdubai.version_1.structure.IncomingCryptoRelayAgent;

import java.util.UUID;

/**
 * Created by loui on 18/03/15.
 */
public class IncomingCryptoTransactionPluginRoot implements Service, IncomingCryptoManager, DealsWithPluginDatabaseSystem, DealsWithEvents, DealsWithErrors, DealsWithPluginFileSystem, Plugin {

    /**
     * Service Interface member variables.
     */
    ServiceStatus serviceStatus = ServiceStatus.CREATED;
    TransactionAgent monitor;
    TransactionAgent relay;
    TransactionService eventRecorder;

    /**
     * IncomingCryptoManager Interface member variables.
     */
    Registry registry;

    /**
     * DealsWithPluginDatabaseSystem Interface member variables.
     */
    PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * UsesFileSystem Interface member variables.
     */
    PluginFileSystem pluginFileSystem;

    /**
     * DealsWithEvents Interface member variables.
     */
    EventManager eventManager;

    /**
     * Plugin Interface member variables.
     */
    UUID pluginId;

    /**
     * Service Interface implementation.
     */
    @Override
    public void start()  throws CantStartPluginException {

        /**
         * I will initialize the Registry, which in turn will create the database if necessary.
         */
        this.registry = new IncomingCryptoRegistry();

        try {
            ((IncomingCryptoRegistry) this.registry).Initialize();
        }
        catch (CantInitializeCryptoRegistryException cantInitializeCryptoRegistryException) {
            /**
             * If I can not initialize the Registry then I can not start the service.
             * * * * 
             */
            System.err.println("CantInitializeException: " + cantInitializeCryptoRegistryException.getMessage());
            cantInitializeCryptoRegistryException.printStackTrace();
            throw new CantStartPluginException(Plugins.INCOMING_CRYPTO_TRANSACTION );
        }

        /**
         * I will start the Event Recorder.
         */
        this.eventRecorder = new IncomingCryptoEventRecorderService(this.eventManager);
        try {

            this.eventRecorder.start();
        }
        catch (CantStartServiceException cantStartServiceException) {
            /**
             * I cant continue if this happens.
             */
            System.err.println("CantStartServiceException: " + cantStartServiceException.getMessage());
            cantStartServiceException.printStackTrace();
            throw new CantStartPluginException(Plugins.INCOMING_CRYPTO_TRANSACTION );
        }

        /**
         * I will start the Relay Agent.
         */
        this.relay = new IncomingCryptoRelayAgent();

        try {
            this.relay.start();
        }
        catch (CantStartAgentException cantStartAgentException) {
            /**
             * I cant continue if this happens.
             */
            System.err.println("CantStartAgentException: " + cantStartAgentException.getMessage());
            cantStartAgentException.printStackTrace();

            /**
             * Note that I stop previously started services and agents.
             */
            this.eventRecorder.stop();
            
            throw new CantStartPluginException(Plugins.INCOMING_CRYPTO_TRANSACTION );
        }

        /**
         * I will start the Monitor Agent.
         */
        this.monitor = new IncomingCryptoMonitorAgent();

        try {
            this.monitor.start();
            ((DealsWithPluginDatabaseSystem) this.monitor).setPluginDatabaseSystem(this.pluginDatabaseSystem);
        }
        catch (CantStartAgentException cantStartAgentException) {
            /**
             * I cant continue if this happens.
             */
            System.err.println("CantStartAgentException: " + cantStartAgentException.getMessage());
            cantStartAgentException.printStackTrace();

            /**
             * Note that I stop previously started services and agents.
             */
            this.eventRecorder.stop();
            this.relay.stop();

            throw new CantStartPluginException(Plugins.INCOMING_CRYPTO_TRANSACTION );
        }
        
        this.serviceStatus = ServiceStatus.STARTED;
        
    }


    @Override
    public void pause() {

        this.serviceStatus = ServiceStatus.PAUSED;

    }

    @Override
    public void resume() {

        this.serviceStatus = ServiceStatus.STARTED;

    }

    @Override
    public void stop() {

        this.eventRecorder.stop();
        this.relay.stop();
        this.monitor.stop();
        
        this.serviceStatus = ServiceStatus.STOPPED;
    }

    @Override
    public ServiceStatus getStatus() {
        return this.serviceStatus;
    }


    /**
     * IncomingCryptoManager interface implementation.
     */
    @Override
    public Registry getRegistry() {
        return this.registry;
    }
    

    /**
     * DealsWithPluginFileSystem Interface implementation.
     */
    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
        this.pluginFileSystem = pluginFileSystem;
    }


    /**
     * DealsWithPluginDatabaseSystem interface implementation.
     */
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }


    /**
     * DealWithEvents Interface implementation.
     */
    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }


    /**
     *DealWithErrors Interface implementation.
     */

    @Override
    public void setErrorManager(ErrorManager errorManager) {

    }


    /**
     * DealsWithPluginIdentity methods implementation.
     */

    @Override
    public void setId(UUID pluginId) {
        this.pluginId = pluginId;
    }



}
