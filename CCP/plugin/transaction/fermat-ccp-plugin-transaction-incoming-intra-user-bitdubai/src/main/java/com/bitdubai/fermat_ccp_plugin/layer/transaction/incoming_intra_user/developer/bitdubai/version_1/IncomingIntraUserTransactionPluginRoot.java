package com.bitdubai.fermat_ccp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1;

/**
 * Created by ciencias on 2/16/15.
 */

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.CantGetFeatureForDevelopersException;
import com.bitdubai.fermat_api.layer.all_definition.common.interfaces.FeatureForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.common.utils.AddonVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.common.utils.DevelopersUtilReference;
import com.bitdubai.fermat_api.layer.all_definition.common.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.DealsWithBitcoinWallet;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.interfaces.CryptoTransmissionNetworkServiceManager;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.interfaces.DealsWithCryptoTransmissionNetworkService;
import com.bitdubai.fermat_ccp_api.layer.transaction.incoming_intra_user.IncomingIntraUserManager;
import com.bitdubai.fermat_ccp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.database.IncomingIntraUserTransactionDeveloperDatabaseFactory;
import com.bitdubai.fermat_ccp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.CantInitializeIncomingIntraUserCryptoRegistryException;
import com.bitdubai.fermat_ccp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.CantStartIncomingIntraUserEventRecorderServiceException;
import com.bitdubai.fermat_ccp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.CantStartIncomingIntraUserRelayAgentException;
import com.bitdubai.fermat_ccp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.CantStartIntraUserCryptoMonitorAgentException;
import com.bitdubai.fermat_ccp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.structure.IncomingIntraUserCryptoMonitorAgent;
import com.bitdubai.fermat_ccp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.structure.IncomingIntraUserEventRecorderService;
import com.bitdubai.fermat_ccp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.structure.IncomingIntraUserMetadataMonitorAgent;
import com.bitdubai.fermat_ccp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.structure.IncomingIntraUserRegistry;
import com.bitdubai.fermat_ccp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.structure.IncomingIntraUserRelayAgent;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.interfaces.DealsWithCryptoAddressBook;
import com.bitdubai.fermat_cry_api.layer.crypto_router.incoming_crypto.DealsWithIncomingCrypto;
import com.bitdubai.fermat_cry_api.layer.crypto_router.incoming_crypto.IncomingCryptoManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * This plugin handles Intra User transactions, meaning transactions happening between users of the platform in both ends.
 * 
 * One of the reasons for this plugin to exist is that a user can send money to another without a payment request at all.
 * In this case when the transaction is received by the payed user, someone has to decide to which wallet to send it. 
 * 
 * As this plugin is  monitoring all User to User transactions, it is the one perfect for the job of deciding where to 
 * send the payment received.
 * 
 * It can also process queries of all such transactions that happened in the past. 
 * 
 * * * * * 
 */

public class IncomingIntraUserTransactionPluginRoot extends AbstractPlugin
        implements DatabaseManagerForDevelopers,
                   DealsWithBitcoinWallet,
                   DealsWithCryptoTransmissionNetworkService,
                   DealsWithErrors,
                   DealsWithEvents,
                   DealsWithIncomingCrypto,
                   DealsWithPluginDatabaseSystem,
                   DealsWithCryptoAddressBook,
                   IncomingIntraUserManager {

    @Override
    public List<AddonVersionReference> getNeededAddonReferences() {
        return new ArrayList<>();
    }

    @Override
    public List<PluginVersionReference> getNeededPluginReferences() {
        return new ArrayList<>();
    }

    @Override
    public List<DevelopersUtilReference> getAvailableDeveloperUtils() {
        return new ArrayList<>();
    }

    @Override
    protected void validateAndAssignReferences() {

    }

    @Override
    public FeatureForDevelopers getFeatureForDevelopers(final DevelopersUtilReference developersUtilReference) throws CantGetFeatureForDevelopersException {
        return null;
    }

    /*
         * DealsWithBitcoinWallet Interface member variables
         */
    private BitcoinWalletManager bitcoinWalletManager;

    /*
     * DealsWithCryptoAddressBook Interface member variables
     */
    private CryptoAddressBookManager cryptoAddressBookManager;

    /*
     * DealsWithCryptoTransmissionNetworkService Interface member variables
     */
    private CryptoTransmissionNetworkServiceManager cryptoTransmissionNetworkServiceManager;


    /*
     * DealsWithErrors Interface member variables
     */
    private ErrorManager errorManager;

    /*
     * DealsWithEvents Interface member variables
     */
    private EventManager eventManager;

    /*
     * DealsWithIncomingCrypto member Interface variables
     */
    private IncomingCryptoManager incomingCryptoManager;

    /*
     * DealsWithPluginDatabaseSystem Interface member variables
     */
    private PluginDatabaseSystem pluginDatabaseSystem;

    /*
     * Plugin Interface member variables
     */
    private UUID pluginId;

    /*
     * Service Interface member variables.
     */
    private ServiceStatus serviceStatus = ServiceStatus.CREATED;

    /*
     * Incoming Intra User member variables
     */
    private IncomingIntraUserRegistry             registry;
    private IncomingIntraUserRelayAgent           relayAgent;
    private IncomingIntraUserCryptoMonitorAgent   cryptoMonitorAgent;
    private IncomingIntraUserMetadataMonitorAgent metadataMonitorAgent;
    private IncomingIntraUserEventRecorderService eventRecorderService;



    /*
     * DatabaseManagerForDevelopers methods implementation
     */
    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        try {
            IncomingIntraUserTransactionDeveloperDatabaseFactory databaseFactory = new IncomingIntraUserTransactionDeveloperDatabaseFactory(this.pluginDatabaseSystem,this. pluginId);
            return databaseFactory.getDatabaseList(developerObjectFactory);
        } catch (Exception e) {
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_INTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,FermatException.wrapException(e));
            return new ArrayList<>();
        }
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        try {
            IncomingIntraUserTransactionDeveloperDatabaseFactory databaseFactory = new IncomingIntraUserTransactionDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
            return databaseFactory.getDatabaseTableList(developerObjectFactory);
        } catch (Exception e) {
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_INTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,FermatException.wrapException(e));
            return new ArrayList<>();
        }
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        try {
            IncomingIntraUserTransactionDeveloperDatabaseFactory dbFactory = new IncomingIntraUserTransactionDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
            dbFactory.initializeDatabase();
            return dbFactory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_INTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(e));
            return new ArrayList<>();
        }
    }

    /*
     * DealsWithBitcoinWallet methods implementation
     */
    @Override
    public void setBitcoinWalletManager(BitcoinWalletManager bitcoinWalletManager) {
        this.bitcoinWalletManager = bitcoinWalletManager;
    }

    /*
     * DealsWithCryptoAddressBook methods implementation
     */
    @Override
    public void setCryptoAddressBookManager(CryptoAddressBookManager cryptoAddressBookManager) {
        this.cryptoAddressBookManager = cryptoAddressBookManager;
    }

    /*
     * DealsWithCryptoTransmissionNetworkService methods implementation
     */
    @Override
    public void setCryptoTransmissionNetworkService(CryptoTransmissionNetworkServiceManager cryptoTransmissionNetworkServiceManager) {
        this.cryptoTransmissionNetworkServiceManager = cryptoTransmissionNetworkServiceManager;
    }

    /*
     * DealsWithErrors methods implementation
     */
    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    /*
     * DealsWithEvents methods implementation
     */
    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    /*
     * DealsWithIncomingCrypto methods implementation
     */
    @Override
    public void setIncomingCryptoManager(IncomingCryptoManager incomungCryptoManager) {
        this.incomingCryptoManager = incomungCryptoManager;
    }

    /*
     * DealsWithPluginDatabaseSystem methods implementation
     */
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /*
     * Plugin methods implementation
     */
    @Override
    public void setId(UUID pluginId) {
        this.pluginId = pluginId;
    }

    /*
     * Service methods implementation
     */
    @Override
    public void start() throws CantStartPluginException {

        try {
            this.registry = new IncomingIntraUserRegistry(this.pluginDatabaseSystem);
            this.registry.initialize(this.pluginId);
        } catch (CantInitializeIncomingIntraUserCryptoRegistryException e) {
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_INTRA_USER_TRANSACTION,UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException("Registry could not be initialized",e,"","");
        } catch (Exception e) {
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_INTRA_USER_TRANSACTION,UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,FermatException.wrapException(e));
            throw new CantStartPluginException("An unexpected exception happened",FermatException.wrapException(e),"","");
        }

        try {
            this.eventRecorderService = new IncomingIntraUserEventRecorderService(this.eventManager, this.registry);
            eventRecorderService.start();
        } catch (CantStartIncomingIntraUserEventRecorderServiceException e) {
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_INTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException("Event Recorder Service could not be initialized",e,"","");
        } catch (Exception e) {
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_INTRA_USER_TRANSACTION,UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,FermatException.wrapException(e));
            throw new CantStartPluginException("An unexpected exception happened",FermatException.wrapException(e),"","");
        }

        try {
            this.relayAgent = new IncomingIntraUserRelayAgent(this.errorManager, this.eventManager, this.bitcoinWalletManager, this.cryptoAddressBookManager, this.registry);
            this.relayAgent.start();
        } catch (CantStartIncomingIntraUserRelayAgentException e) {
            this.eventRecorderService.stop();
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_INTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException("Relay Agent could not be initialized",e,"","");
        } catch (Exception e) {
            this.eventRecorderService.stop();
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_INTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, FermatException.wrapException(e));
            throw new CantStartPluginException("An unexpected exception happened",FermatException.wrapException(e),"","");
        }

        try {
            this.cryptoMonitorAgent = new IncomingIntraUserCryptoMonitorAgent(this.errorManager, this.incomingCryptoManager, this.registry);
            this.cryptoMonitorAgent.start();
        } catch (CantStartIntraUserCryptoMonitorAgentException e) {
            this.relayAgent.stop();
            this.eventRecorderService.stop();
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_INTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException("Crypto Monitor Agent could not be initialized",e,"","");
        } catch (Exception e) {
            this.relayAgent.stop();
            this.eventRecorderService.stop();
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_INTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, FermatException.wrapException(e));
            throw new CantStartPluginException("An unexpected exception happened",FermatException.wrapException(e),"","");
        }

        try {
            this.metadataMonitorAgent = new IncomingIntraUserMetadataMonitorAgent(this.errorManager, this.cryptoTransmissionNetworkServiceManager, this.registry);
            metadataMonitorAgent.start();
        } catch (CantStartIntraUserCryptoMonitorAgentException e) {
            this.relayAgent.stop();
            this.cryptoMonitorAgent.stop();
            this.eventRecorderService.stop();
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_INTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException("Metadata Monitor Agent could not be initialized",e,"","");
        } catch (Exception e) {
            this.relayAgent.stop();
            this.cryptoMonitorAgent.stop();
            this.eventRecorderService.stop();
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_INTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, FermatException.wrapException(e));
            throw new CantStartPluginException("An unexpected exception happened",FermatException.wrapException(e),"","");
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
        this.relayAgent.stop();
        this.cryptoMonitorAgent.stop();
        this.metadataMonitorAgent.stop();
        this.eventRecorderService.stop();
        this.serviceStatus = ServiceStatus.STOPPED;
    }

}
