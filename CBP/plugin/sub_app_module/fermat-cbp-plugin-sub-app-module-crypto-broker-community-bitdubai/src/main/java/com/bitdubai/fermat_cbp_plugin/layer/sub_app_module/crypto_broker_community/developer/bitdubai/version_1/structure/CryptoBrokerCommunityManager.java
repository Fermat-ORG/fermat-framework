package com.bitdubai.fermat_cbp_plugin.layer.sub_app_module.crypto_broker_community.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.ActorConnectionNotFoundException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantAcceptActorConnectionRequestException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantCancelActorConnectionRequestException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantDenyActorConnectionRequestException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantDisconnectFromActorException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantListActorConnectionsException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantRequestActorConnectionException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.ConnectionAlreadyRequestedException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.UnexpectedConnectionStateException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.UnsupportedActorTypeException;
import com.bitdubai.fermat_api.layer.actor_connection.common.structure_common_classes.ActorIdentityInformation;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantPersistSettingsException;
import com.bitdubai.fermat_api.layer.modules.ModuleManagerImpl;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_broker.interfaces.CryptoBrokerActorConnectionManager;
import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_broker.interfaces.CryptoBrokerActorConnectionSearch;
import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_broker.utils.CryptoBrokerActorConnection;
import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_broker.utils.CryptoBrokerLinkedActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.interfaces.CryptoBrokerManager;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.exceptions.CantListCryptoBrokerIdentitiesException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.interfaces.CryptoBrokerIdentity;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.interfaces.CryptoBrokerIdentityManager;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.exceptions.CantListCryptoCustomerIdentityException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.exceptions.CantPublishIdentityException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.exceptions.IdentityNotFoundException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.interfaces.CryptoCustomerIdentity;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.interfaces.CryptoCustomerIdentityManager;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.ActorConnectionAlreadyRequestedException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.ActorTypeNotSupportedException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CantAcceptRequestException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CantGetCryptoBrokerSearchResult;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CantListCryptoBrokersException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CantListIdentitiesToSelectException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CantRequestConnectionException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CantValidateConnectionStateException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.ConnectionRequestNotFoundException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CryptoBrokerCancellingFailedException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CryptoBrokerConnectionDenialFailedException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CryptoBrokerDisconnectingFailedException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.interfaces.CryptoBrokerCommunityInformation;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.interfaces.CryptoBrokerCommunitySearch;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.interfaces.CryptoBrokerCommunitySelectableIdentity;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.interfaces.CryptoBrokerCommunitySubAppModuleManager;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.settings.CryptoBrokerCommunitySettings;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The class <code>com.bitdubai.fermat_cbp_plugin.layer.sub_app_module.crypto_broker_community.developer.bitdubai.version_1.structure.CryptoBrokerCommunityManager</code>
 * bla bla bla.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 21/12/2015.
 */
public class CryptoBrokerCommunityManager
        extends ModuleManagerImpl<CryptoBrokerCommunitySettings>
        implements CryptoBrokerCommunitySubAppModuleManager, Serializable {

    private final CryptoBrokerIdentityManager        cryptoBrokerIdentityManager           ;
    private final CryptoBrokerActorConnectionManager cryptoBrokerActorConnectionManager    ;
    private final CryptoBrokerManager                cryptoBrokerActorNetworkServiceManager;
    private final CryptoCustomerIdentityManager      cryptoCustomerIdentityManager         ;
    private final ErrorManager                       errorManager                          ;
    private final PluginVersionReference             pluginVersionReference                ;

    private       String                             subAppPublicKey                       ;

    public CryptoBrokerCommunityManager(final CryptoBrokerIdentityManager        cryptoBrokerIdentityManager           ,
                                        final CryptoBrokerActorConnectionManager cryptoBrokerActorConnectionManager    ,
                                        final CryptoBrokerManager                cryptoBrokerActorNetworkServiceManager,
                                        final CryptoCustomerIdentityManager      cryptoCustomerIdentityManager         ,
                                        final ErrorManager                       errorManager                          ,
                                        final PluginFileSystem                   pluginFileSystem                      ,
                                        final UUID                               pluginId                              ,
                                        final PluginVersionReference             pluginVersionReference                ) {

        super(pluginFileSystem, pluginId);

        this.cryptoBrokerIdentityManager            = cryptoBrokerIdentityManager           ;
        this.cryptoBrokerActorConnectionManager     = cryptoBrokerActorConnectionManager    ;
        this.cryptoBrokerActorNetworkServiceManager = cryptoBrokerActorNetworkServiceManager;
        this.cryptoCustomerIdentityManager          = cryptoCustomerIdentityManager         ;
        this.errorManager                           = errorManager                          ;
        this.pluginVersionReference                 = pluginVersionReference                ;
    }



    @Override
    public List<CryptoBrokerCommunityInformation> listWorldCryptoBrokers(CryptoBrokerCommunitySelectableIdentity selectedIdentity, int max, int offset) throws CantListCryptoBrokersException {

        List<CryptoBrokerCommunityInformation> worldBrokerList;
        List<CryptoBrokerActorConnection> actorConnections;

        try{
            worldBrokerList = getCryptoBrokerSearch().getResult();
        } catch (CantGetCryptoBrokerSearchResult e) {
            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListCryptoBrokersException(e, "", "Error in listWorldCryptoBrokers trying to list world brokers");
        }


        try {

            final CryptoBrokerLinkedActorIdentity linkedActorIdentity = new CryptoBrokerLinkedActorIdentity(selectedIdentity.getPublicKey(), selectedIdentity.getActorType());
            final CryptoBrokerActorConnectionSearch search = cryptoBrokerActorConnectionManager.getSearch(linkedActorIdentity);
            //search.addConnectionState(ConnectionState.CONNECTED);
            //search.addConnectionState(ConnectionState.PENDING_REMOTELY_ACCEPTANCE);

            actorConnections = search.getResult(Integer.MAX_VALUE, 0);

        } catch (final CantListActorConnectionsException e) {
            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListCryptoBrokersException(e, "", "Error trying to list actor connections.");
        }

        CryptoBrokerCommunityInformation worldBroker;
        for(int i = 0; i < worldBrokerList.size(); i++)
        {
            worldBroker = worldBrokerList.get(i);
            for(CryptoBrokerActorConnection connectedBroker : actorConnections)
            {
                if(worldBroker.getPublicKey().equals(connectedBroker.getPublicKey()))
                    worldBrokerList.set(i, new CryptoBrokerCommunitySubAppModuleInformation(worldBroker.getPublicKey(), worldBroker.getAlias(), worldBroker.getImage(), connectedBroker.getConnectionState(), connectedBroker.getConnectionId()));
            }
        }
        return worldBrokerList;
    }

    /**
     * We are listing here all crypto brokers and all crypto customer identities found in the device.
     */
    @Override
    public List<CryptoBrokerCommunitySelectableIdentity> listSelectableIdentities() throws CantListIdentitiesToSelectException {

        try {

            final List<CryptoBrokerCommunitySelectableIdentity> selectableIdentities = new ArrayList<>();

            final List<CryptoBrokerIdentity> cryptoBrokerIdentities = cryptoBrokerIdentityManager.listIdentitiesFromCurrentDeviceUser();

            for (final CryptoBrokerIdentity cbi : cryptoBrokerIdentities)
                selectableIdentities.add(new CryptoBrokerCommunitySelectableIdentityImpl(cbi));

            final List<CryptoCustomerIdentity> cryptoCustomerIdentities = cryptoCustomerIdentityManager.listAllCryptoCustomerFromCurrentDeviceUser();

            for (final CryptoCustomerIdentity cci : cryptoCustomerIdentities)
                selectableIdentities.add(new CryptoBrokerCommunitySelectableIdentityImpl(cci));

            return selectableIdentities;

        } catch (final CantListCryptoBrokerIdentitiesException e) {

            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListIdentitiesToSelectException(e, "", "Error in DAO trying to list identities.");
        } catch (final Exception e) {

            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListIdentitiesToSelectException(e, "", "Unhandled Exception.");
        }
    }

    @Override
    public void setSelectedActorIdentity(CryptoBrokerCommunitySelectableIdentity identity) {

        //Try to get appSettings
        CryptoBrokerCommunitySettings appSettings;
        try {
            appSettings = loadAndGetSettings(this.subAppPublicKey);
        }catch (Exception e){ appSettings = null; }

        //If appSettings exist, save identity
        if(appSettings != null){
            if(identity.getPublicKey() != null)
                appSettings.setLastSelectedIdentityPublicKey(identity.getPublicKey());
            if(identity.getActorType() != null)
                appSettings.setLastSelectedActorType(identity.getActorType());
            try {
                persistSettings(this.subAppPublicKey, appSettings);
            }catch (CantPersistSettingsException e){
                this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            }
        }
    }

    @Override
    public CryptoBrokerCommunitySearch getCryptoBrokerSearch() {
        return new CryptoBrokerCommunitySubAppModuleCommunitySearch(cryptoBrokerActorNetworkServiceManager);
    }

    @Override
    public CryptoBrokerCommunitySearch searchConnectedCryptoBroker(CryptoBrokerCommunitySelectableIdentity selectedIdentity) {
        return null;
    }

    @Override
    public void requestConnectionToCryptoBroker(final CryptoBrokerCommunitySelectableIdentity selectedIdentity     ,
                                                final CryptoBrokerCommunityInformation        cryptoBrokerToContact) throws CantRequestConnectionException          ,
            ActorConnectionAlreadyRequestedException,
            ActorTypeNotSupportedException          {

        try {

            final ActorIdentityInformation actorSending = new ActorIdentityInformation(
                    selectedIdentity.getPublicKey()   ,
                    selectedIdentity.getActorType()   ,
                    selectedIdentity.getAlias()       ,
                    selectedIdentity.getImage()
            );

            final ActorIdentityInformation actorReceiving = new ActorIdentityInformation(
                    cryptoBrokerToContact.getPublicKey()   ,
                    Actors.CBP_CRYPTO_BROKER               ,
                    cryptoBrokerToContact.getAlias()       ,
                    cryptoBrokerToContact.getImage()
            );

            cryptoBrokerActorConnectionManager.requestConnection(
                    actorSending,
                    actorReceiving
            );

        } catch (final CantRequestActorConnectionException e) {

            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantRequestConnectionException(e, "", "Error trying to request the actor connection.");
        } catch (final UnsupportedActorTypeException e) {

            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new ActorTypeNotSupportedException(e, "", "Actor type is not supported.");
        } catch (final ConnectionAlreadyRequestedException e) {

            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new ActorConnectionAlreadyRequestedException(e, "", "Connection already requested.");
        } catch (final Exception e) {

            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantRequestConnectionException(e, "", "Unhandled Exception.");
        }
    }

    @Override
    public void acceptCryptoBroker(final UUID requestId) throws CantAcceptRequestException, ConnectionRequestNotFoundException {

        try {

            cryptoBrokerActorConnectionManager.acceptConnection(requestId);

        } catch (final CantAcceptActorConnectionRequestException |
                UnexpectedConnectionStateException        e) {

            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantAcceptRequestException(e, "", "Error trying to accept the actor connection.");
        } catch (final ActorConnectionNotFoundException e) {

            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new ConnectionRequestNotFoundException(e, "", "Connection already requested.");
        } catch (final Exception e) {

            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantAcceptRequestException(e, "", "Unhandled Exception.");
        }
    }

    @Override
    public void denyConnection(final UUID requestId) throws CryptoBrokerConnectionDenialFailedException,
            ConnectionRequestNotFoundException         {

        try {

            cryptoBrokerActorConnectionManager.denyConnection(requestId);

        } catch (final CantDenyActorConnectionRequestException |
                UnexpectedConnectionStateException      e) {

            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CryptoBrokerConnectionDenialFailedException(e, "", "Error trying to deny the actor connection.");
        } catch (final ActorConnectionNotFoundException e) {

            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new ConnectionRequestNotFoundException(e, "", "Connection request not found.");
        } catch (final Exception e) {

            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CryptoBrokerConnectionDenialFailedException(e, "", "Unhandled Exception.");
        }
    }

    @Override
    public void disconnectCryptoBroker(final UUID requestId) throws CryptoBrokerDisconnectingFailedException,
            ConnectionRequestNotFoundException      {

        try {

            cryptoBrokerActorConnectionManager.disconnect(requestId);

        } catch (final CantDisconnectFromActorException   |
                UnexpectedConnectionStateException e) {

            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CryptoBrokerDisconnectingFailedException(e, "", "Error trying to disconnect the actor connection.");
        } catch (final ActorConnectionNotFoundException e) {

            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new ConnectionRequestNotFoundException(e, "", "Connection request not found.");
        } catch (final Exception e) {

            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CryptoBrokerDisconnectingFailedException(e, "", "Unhandled Exception.");
        }
    }

    @Override
    public void cancelCryptoBroker(final UUID requestId) throws CryptoBrokerCancellingFailedException, ConnectionRequestNotFoundException {

        try {

            cryptoBrokerActorConnectionManager.cancelConnection(requestId);

        } catch (final CantCancelActorConnectionRequestException |
                UnexpectedConnectionStateException e) {

            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CryptoBrokerCancellingFailedException(e, "", "Error trying to disconnect the actor connection.");
        } catch (final ActorConnectionNotFoundException e) {

            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new ConnectionRequestNotFoundException(e, "", "Connection request not found.");
        } catch (final Exception e) {

            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CryptoBrokerCancellingFailedException(e, "", "Unhandled Exception.");
        }
    }

    @Override
    public List<CryptoBrokerCommunityInformation> listAllConnectedCryptoBrokers(final CryptoBrokerCommunitySelectableIdentity selectedIdentity,
                                                                                final int                                     max             ,
                                                                                final int                                     offset          ) throws CantListCryptoBrokersException {

        try {

            final CryptoBrokerLinkedActorIdentity linkedActorIdentity = new CryptoBrokerLinkedActorIdentity(
                    selectedIdentity.getPublicKey(),
                    selectedIdentity.getActorType()
            );

            final CryptoBrokerActorConnectionSearch search = cryptoBrokerActorConnectionManager.getSearch(linkedActorIdentity);

            search.addConnectionState(ConnectionState.CONNECTED);

            final List<CryptoBrokerActorConnection> actorConnections = search.getResult(max, offset);

            final List<CryptoBrokerCommunityInformation> cryptoBrokerCommunityInformationList = new ArrayList<>();

            for (CryptoBrokerActorConnection cbac : actorConnections)
                cryptoBrokerCommunityInformationList.add(new CryptoBrokerCommunitySubAppModuleInformation(cbac));

            return cryptoBrokerCommunityInformationList;

        } catch (final CantListActorConnectionsException e) {

            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListCryptoBrokersException(e, "", "Error trying to list actor connections.");
        } catch (final Exception e) {

            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListCryptoBrokersException(e, "", "Unhandled Exception.");
        }
    }

    @Override
    public List<CryptoBrokerCommunityInformation> listCryptoBrokersPendingLocalAction(final CryptoBrokerCommunitySelectableIdentity selectedIdentity,
                                                                                      final int max,
                                                                                      final int offset) throws CantListCryptoBrokersException {

        try {

            final CryptoBrokerLinkedActorIdentity linkedActorIdentity = new CryptoBrokerLinkedActorIdentity(
                    selectedIdentity.getPublicKey(),
                    selectedIdentity.getActorType()
            );

            final CryptoBrokerActorConnectionSearch search = cryptoBrokerActorConnectionManager.getSearch(linkedActorIdentity);

            search.addConnectionState(ConnectionState.PENDING_LOCALLY_ACCEPTANCE);

            final List<CryptoBrokerActorConnection> actorConnections = search.getResult(max, offset);

            final List<CryptoBrokerCommunityInformation> cryptoBrokerCommunityInformationList = new ArrayList<>();

            for (CryptoBrokerActorConnection cbac : actorConnections)
                cryptoBrokerCommunityInformationList.add(new CryptoBrokerCommunitySubAppModuleInformation(cbac));

            return cryptoBrokerCommunityInformationList;

        } catch (final CantListActorConnectionsException e) {

            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListCryptoBrokersException(e, "", "Error trying to list actor connections.");
        } catch (final Exception e) {

            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListCryptoBrokersException(e, "", "Unhandled Exception.");
        }
    }

    @Override
    public List<CryptoBrokerCommunityInformation> listCryptoBrokersPendingRemoteAction(final CryptoBrokerCommunitySelectableIdentity selectedIdentity,
                                                                                       final int max,
                                                                                       final int offset) throws CantListCryptoBrokersException {
        try {

            final CryptoBrokerLinkedActorIdentity linkedActorIdentity = new CryptoBrokerLinkedActorIdentity(
                    selectedIdentity.getPublicKey(),
                    selectedIdentity.getActorType()
            );

            final CryptoBrokerActorConnectionSearch search = cryptoBrokerActorConnectionManager.getSearch(linkedActorIdentity);

            search.addConnectionState(ConnectionState.PENDING_REMOTELY_ACCEPTANCE);

            final List<CryptoBrokerActorConnection> actorConnections = search.getResult(max, offset);

            final List<CryptoBrokerCommunityInformation> cryptoBrokerCommunityInformationList = new ArrayList<>();

            for (CryptoBrokerActorConnection cbac : actorConnections)
                cryptoBrokerCommunityInformationList.add(new CryptoBrokerCommunitySubAppModuleInformation(cbac));

            return cryptoBrokerCommunityInformationList;

        } catch (final CantListActorConnectionsException e) {

            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListCryptoBrokersException(e, "", "Error trying to list actor connections.");
        } catch (final Exception e) {

            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListCryptoBrokersException(e, "", "Unhandled Exception.");
        }
    }

    @Override
    public int getCryptoBrokersWaitingYourAcceptanceCount() {
        return 0;
    }

    @Override
    public ConnectionState getActorConnectionState(String publicKey) throws CantValidateConnectionStateException {

        try {
            CryptoBrokerCommunitySelectableIdentity selectedIdentity  =  getSelectedActorIdentity();
            final CryptoBrokerLinkedActorIdentity linkedActorIdentity = new CryptoBrokerLinkedActorIdentity(selectedIdentity.getPublicKey(), selectedIdentity.getActorType());
            final CryptoBrokerActorConnectionSearch search = cryptoBrokerActorConnectionManager.getSearch(linkedActorIdentity);
            final List<CryptoBrokerActorConnection> actorConnections = search.getResult(Integer.MAX_VALUE, 0);

            for(CryptoBrokerActorConnection connection : actorConnections){
                if(publicKey.equals(connection.getPublicKey()))
                    return connection.getConnectionState();
            }

        } catch (final CantListActorConnectionsException e) {
            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantValidateConnectionStateException(e, "", "Error trying to list actor connections.");
        } catch (Exception e) {}

        return ConnectionState.DISCONNECTED_LOCALLY;
    }




    @Override
    public CryptoBrokerCommunitySelectableIdentity getSelectedActorIdentity() throws CantGetSelectedActorIdentityException, ActorIdentityNotSelectedException {

        //Try to get appSettings
        CryptoBrokerCommunitySettings appSettings;
        try {
            appSettings = loadAndGetSettings(this.subAppPublicKey);
        }catch (Exception e){ return null; }


        //Get all customer identities on local device
        List<CryptoCustomerIdentity> customerIdentitiesInDevice = new ArrayList<>();
        try{
            customerIdentitiesInDevice = cryptoCustomerIdentityManager.listAllCryptoCustomerFromCurrentDeviceUser();
        } catch(CantListCryptoCustomerIdentityException e) { /*Do nothing*/ }


        //Get all broker identities on local device
        List<CryptoBrokerIdentity> brokerIdentitiesInDevice = new ArrayList<>();
        try{
            brokerIdentitiesInDevice = cryptoBrokerIdentityManager.listIdentitiesFromCurrentDeviceUser();
        } catch(CantListCryptoBrokerIdentitiesException e) { /*Do nothing*/ }

        //No registered users in device
        if(customerIdentitiesInDevice.size() + brokerIdentitiesInDevice.size() == 0)
            throw new CantGetSelectedActorIdentityException("", null, "", "");



        //If appSettings exists, get its selectedActorIdentityPublicKey property
        if(appSettings != null)
        {
            String lastSelectedIdentityPublicKey = appSettings.getLastSelectedIdentityPublicKey();
            Actors lastSelectedActorType = appSettings.getLastSelectedActorType();

            if (lastSelectedIdentityPublicKey != null && lastSelectedActorType != null) {

                CryptoBrokerCommunitySelectableIdentityImpl selectedIdentity = null;

                if(lastSelectedActorType == Actors.CBP_CRYPTO_BROKER)
                {
                    for(CryptoBrokerIdentity i : brokerIdentitiesInDevice) {
                        if(i.getPublicKey().equals(lastSelectedIdentityPublicKey))
                            selectedIdentity = new CryptoBrokerCommunitySelectableIdentityImpl(i.getPublicKey(), Actors.CBP_CRYPTO_BROKER, i.getAlias(), i.getProfileImage());
                    }
                }
                else if( lastSelectedActorType == Actors.CBP_CRYPTO_CUSTOMER)
                {
                    for(CryptoCustomerIdentity i : customerIdentitiesInDevice) {
                        if(i.getPublicKey().equals(lastSelectedIdentityPublicKey))
                            selectedIdentity = new CryptoBrokerCommunitySelectableIdentityImpl(i.getPublicKey(), Actors.CBP_CRYPTO_CUSTOMER, i.getAlias(), i.getProfileImage());
                    }
                }


                if(selectedIdentity == null)
                    throw new ActorIdentityNotSelectedException("", null, "", "");

                return selectedIdentity;
            }
            else
                throw new ActorIdentityNotSelectedException("", null, "", "");
        }

        return null;
    }

    @Override
    public void createIdentity(String name, String phrase, byte[] profile_img) throws Exception {

        String createdPublicKey = null;

        if(name.equals("Customer"))
        {
            try{
                final CryptoCustomerIdentity createdIdentity = cryptoCustomerIdentityManager.createCryptoCustomerIdentity(name, profile_img);
                createdPublicKey = createdIdentity.getPublicKey();
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            cryptoCustomerIdentityManager.publishIdentity(createdIdentity.getPublicKey());
                        } catch(CantPublishIdentityException | IdentityNotFoundException e) {
                            errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
                        }
                    }
                }.start();
            }catch(Exception e) {
                this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
                return;
            }
        }
        else if( name.equals("Broker"))
        {
            try{
                final CryptoBrokerIdentity createdIdentity = cryptoBrokerIdentityManager.createCryptoBrokerIdentity(name, profile_img);
                createdPublicKey = createdIdentity.getPublicKey();

                new Thread() {
                    @Override
                    public void run() {
                        try {
                            cryptoBrokerIdentityManager.publishIdentity(createdIdentity.getPublicKey());
                        } catch(Exception e) {
                            errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
                        }
                    }
                }.start();
            }catch(Exception e) {
                this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
                return;
            }
        }



        //Try to get appSettings
        CryptoBrokerCommunitySettings appSettings;
        try {
            appSettings = loadAndGetSettings(this.subAppPublicKey);
        }catch (Exception e){ appSettings = null; }


        //If appSettings exist
        if(appSettings != null){
            if(createdPublicKey != null)
                appSettings.setLastSelectedIdentityPublicKey(createdPublicKey);
            if(name.equals("Customer"))
                appSettings.setLastSelectedActorType(Actors.CBP_CRYPTO_CUSTOMER);
            else if(name.equals("Broker"))
                appSettings.setLastSelectedActorType(Actors.CBP_CRYPTO_BROKER);

            try {
                persistSettings(this.subAppPublicKey, appSettings);
            }catch (CantPersistSettingsException e){
                this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            }
        }
    }

    @Override
    public void setAppPublicKey(final String publicKey) {
        this.subAppPublicKey = publicKey;
    }

    @Override
    public int[] getMenuNotifications() {
        return new int[0];
    }
}
