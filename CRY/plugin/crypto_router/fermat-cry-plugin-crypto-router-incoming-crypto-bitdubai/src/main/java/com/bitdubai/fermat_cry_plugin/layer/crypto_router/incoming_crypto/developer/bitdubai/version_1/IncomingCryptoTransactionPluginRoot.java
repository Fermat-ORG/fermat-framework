package com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.TransactionProtocolManager;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
//import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.ActorAddressBookManager;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.DealsWithEvents;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.EventManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.interfaces.DealsWithActorAddressBook;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.interfaces.ActorAddressBookManager;
import com.bitdubai.fermat_cry_api.layer.crypto_router.incoming_crypto.IncomingCryptoManager;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.CryptoVaultManager;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.DealsWithCryptoVault;
import com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.exceptions.CantInitializeCryptoRegistryException;
import com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.exceptions.CantStartAgentException;
import com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.exceptions.CantStartServiceException;
import com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.interfaces.DealsWithRegistry;
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
public class IncomingCryptoTransactionPluginRoot implements IncomingCryptoManager, DealsWithActorAddressBook,DealsWithCryptoVault,DealsWithErrors, DealsWithEvents, DealsWithPluginDatabaseSystem, Plugin, Service {

    /*
     * DealsWithActorAddressBook member variables
     */
    private ActorAddressBookManager actorAddressBook;

    /*
     * DealsWithCryptoVault member variables
     */
    private CryptoVaultManager cryptoVaultManager;

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
    private PluginDatabaseSystem pluginDatabaseSystem;


    /**
     * IncomingCryptoManager Interface member variables.
     */
    private IncomingCryptoRegistry registry;

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


    /*
     * DealsWithActorAddressBook Interface implementation.
     */
    @Override
    public void setActorAddressBookManager(ActorAddressBookManager actorAddressBook) {
        this.actorAddressBook = actorAddressBook;
    }


    /**
     * DealsWithCryptoVault Interface implementation.
     */
    @Override
    public void setCryptoVaultManager(CryptoVaultManager cryptoVaultManager) {
        this.cryptoVaultManager = cryptoVaultManager;
    }

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
     */
    @Override
    public TransactionProtocolManager<CryptoTransaction> getTransactionManager(){
        return this.registry;
    }

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
            this.registry.setErrorManager(this.errorManager);
            this.registry.setPluginDatabaseSystem(this.pluginDatabaseSystem);
            this.registry.initialize(this.pluginId);
        }
        catch (CantInitializeCryptoRegistryException cantInitializeCryptoRegistryException) {

            /**
             * If I can not initialize the Registry then I can not start the service.
             */
            this.serviceStatus = ServiceStatus.STARTED;
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantInitializeCryptoRegistryException);
            throw new CantStartPluginException("Registry failed to start",cantInitializeCryptoRegistryException, Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION.getKey(),"");
        }

        /**
         * I will start the Event Recorder.
         */
        this.eventRecorder = new IncomingCryptoEventRecorderService();
        ((DealsWithEvents) this.eventRecorder).setEventManager(this.eventManager);
        ((DealsWithRegistry) this.eventRecorder).setRegistry(this.registry);

        try {
            this.eventRecorder.start();
        }
        catch (CantStartServiceException cantStartServiceException) {
           
            /**
             * I cant continue if this happens.
             */
            this.serviceStatus = ServiceStatus.STARTED;
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantStartServiceException);
            throw new CantStartPluginException("Event Recorded could not be started",cantStartServiceException, Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION.getKey(),"");
        }

        /**
         * I will start the Relay Agent.
         */
        this.relay = new IncomingCryptoRelayAgent();

        try {
            ((DealsWithActorAddressBook) this.relay).setActorAddressBookManager(this.actorAddressBook);
            ((DealsWithErrors) this.relay).setErrorManager(this.errorManager);
            ((DealsWithEvents) this.relay).setEventManager(this.eventManager);
            ((DealsWithRegistry) this.relay).setRegistry(this.registry);
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
            this.serviceStatus = ServiceStatus.STARTED;
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantStartAgentException);

            throw new CantStartPluginException("Relay Agent could not be started",cantStartAgentException, Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION.getKey(),"");
        }

        /**
         * I will start the Monitor Agent.
         */
        this.monitor = new IncomingCryptoMonitorAgent();
        try {
            ((DealsWithCryptoVault) this.monitor).setCryptoVaultManager(this.cryptoVaultManager);
            ((DealsWithErrors) this.monitor).setErrorManager(this.errorManager);
            ((DealsWithRegistry) this.monitor).setRegistry(this.registry);
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
            this.serviceStatus = ServiceStatus.STARTED;
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantStartAgentException);

            throw new CantStartPluginException("Monitor agent could not be started",cantStartAgentException, Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION.getKey(),"");
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
}
