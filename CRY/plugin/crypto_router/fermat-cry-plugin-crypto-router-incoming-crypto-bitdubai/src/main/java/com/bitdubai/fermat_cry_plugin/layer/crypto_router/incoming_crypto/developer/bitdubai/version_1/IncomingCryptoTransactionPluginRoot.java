package com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer._1_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer._1_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.DealsWithEvents;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.EventManager;
import com.bitdubai.fermat_api.layer.cry_crypto_module.actor_address_book.ActorAddressBook;
import com.bitdubai.fermat_api.layer.cry_crypto_module.actor_address_book.DealsWithActorAddressBook;
import com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.exceptions.CantInitializeCryptoRegistryException;
import com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.exceptions.CantStartAgentException;
import com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.exceptions.CantStartServiceException;
import com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.interfaces.TransactionAgent;
import com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.interfaces.TransactionService;
import com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.structure.IncomingCryptoEventRecorderService;
import com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.structure.IncomingCryptoMonitorAgent;
import com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.structure.IncomingCryptoRegistry;
import com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.structure.IncomingCryptoRelayAgent;

import java.util.UUID;

/**
 * Created by loui on 18/03/15.
 * Modified by Arturo Vallone 25/04/2015
 */
public class IncomingCryptoTransactionPluginRoot implements DealsWithErrors, DealsWithEvents, DealsWithPluginDatabaseSystem,DealsWithActorAddressBook, Plugin, Service {

    /**
     * DealsWithErrors Interface member variables.
     */
    private ErrorManager errorManager;

    /**
     * DealsWithEvents Interface member variables.
     */
    private EventManager eventManager;

    /**
     * DealsWithPluginDatabaseSystem Interface member variables.
     */
    PluginDatabaseSystem pluginDatabaseSystem;


    /**
     * IncomingCryptoManager Interface member variables.
     */
    private IncomingCryptoRegistry registry;

    /*
     * DealsWithUserAddressBook member variables
     */
     private ActorAddressBook actorAddressBook;


    /**
     * Plugin Interface member variables.
     */
    private UUID pluginId;

    /**
     * Service Interface member variables.
     */
    private ServiceStatus serviceStatus = ServiceStatus.CREATED;
    private TransactionAgent monitor;
    private TransactionAgent relay;
    private TransactionService eventRecorder;


    /**
     *DealsWithErrors Interface implementation.
     */
    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }


    /**
     * DealWithEvents Interface implementation.
     */
    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }


    /**
     * DealsWithPluginDatabaseSystem interface implementation.
     */
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /**
     * IncomingCryptoManager interface implementation.

    @Override
    public Registry getRegistry() {
        return this.registry;
    }


    @Override
    public TransactionManager getTransactionManager() {
        return (TransactionManager) this.registry;
    }
     */

    /**
     * Plugin interface implementation.
     */
    @Override
    public void setId(UUID pluginId) {
        this.pluginId = pluginId;
    }


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
            this.registry.initialize(this.pluginId);
            this.registry.setErrorManager(this.errorManager);
            this.registry.setPluginDatabaseSystem(this.pluginDatabaseSystem);
        }
        catch (CantInitializeCryptoRegistryException cantInitializeCryptoRegistryException) {

            /**
             * If I can not initialize the Registry then I can not start the service.
             */
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantInitializeCryptoRegistryException);
            
            throw new CantStartPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION);
        }

        /**
         * I will start the Event Recorder.
         */
        this.eventRecorder = new IncomingCryptoEventRecorderService(this.eventManager,this.registry);

        try {
            this.eventRecorder.start();
        }
        catch (CantStartServiceException cantStartServiceException) {
           
            /**
             * I cant continue if this happens.
             */
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantStartServiceException);
            throw new CantStartPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION);
        }

        /**
         * I will start the Relay Agent.
         */
        this.relay = new IncomingCryptoRelayAgent();

        try {
            //((IncomingCryptoRelayAgent) this.relay).setPluginId(this.pluginId);
            ((IncomingCryptoRelayAgent) this.relay).setRegistry(this.registry);
            ((DealsWithErrors) this.relay).setErrorManager(this.errorManager);
            ((DealsWithActorAddressBook) this.relay).setUserAddressBookManager(this.actorAddressBook);
            this.relay.start();
        }
        catch (CantStartAgentException cantStartAgentException) {

            /**
             * Note that I stop previously started services and agents.
             */
            this.eventRecorder.stop();

            /**
             * I cant continue if this happens.
             */
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantStartAgentException);

            throw new CantStartPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION);
        }

        /**
         * I will start the Monitor Agent.
         */
        this.monitor = new IncomingCryptoMonitorAgent();
        try {
            //((IncomingCryptoMonitorAgent) this.monitor).setPluginId(this.pluginId);
            ((IncomingCryptoMonitorAgent) this.monitor).setRegistry(this.registry);
            ((DealsWithErrors) this.monitor).setErrorManager(this.errorManager);
            // TODO: give this class the reference for the CryptoVault
            this.monitor.start();
        }
        catch (CantStartAgentException cantStartAgentException) {

            /**
             * Note that I stop previously started services and agents.
             */
            this.eventRecorder.stop();
            this.relay.stop();

            /**
             * I cant continue if this happens.
             */
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantStartAgentException);

            throw new CantStartPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION);
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

    @Override
    public void setUserAddressBookManager(ActorAddressBook actorAddressBook) {
        this.actorAddressBook = actorAddressBook;
    }


    /**
     * TransactionManager interface implementation.

    @Override
    public UUID getNextPendingTransactionByDestination (UUID destinationId) throws CantSearchForTransactionsException {
        return ((TransactionManager) this.registry).getNextPendingTransactionByDestination(destinationId);
    }
    public boolean releaseTransaction (UUID trxID) throws CantReleaseTransactionException {
        return ((TransactionManager) this.registry).releaseTransaction(trxID);
    }
     */

}
