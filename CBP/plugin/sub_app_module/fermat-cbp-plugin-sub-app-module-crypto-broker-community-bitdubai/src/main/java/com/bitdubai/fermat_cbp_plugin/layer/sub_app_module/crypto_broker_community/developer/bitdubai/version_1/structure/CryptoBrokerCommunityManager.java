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
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_broker.interfaces.CryptoBrokerActorConnectionManager;
import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_broker.interfaces.CryptoBrokerActorConnectionSearch;
import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_broker.utils.CryptoBrokerActorConnection;
import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_broker.utils.CryptoBrokerLinkedActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.interfaces.CryptoBrokerManager;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.exceptions.CantCreateCryptoBrokerIdentityException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.exceptions.CantGetCryptoBrokerIdentityException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.exceptions.CantListCryptoBrokerIdentitiesException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.exceptions.CantPublishIdentityException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.exceptions.CryptoBrokerIdentityAlreadyExistsException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.exceptions.IdentityNotFoundException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.interfaces.CryptoBrokerIdentity;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.interfaces.CryptoBrokerIdentityManager;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.interfaces.CryptoCustomerIdentity;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.interfaces.CryptoCustomerIdentityManager;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.ActorConnectionAlreadyRequestedException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.ActorTypeNotSupportedException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CantAcceptRequestException;
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
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The class <code>com.bitdubai.fermat_cbp_plugin.layer.sub_app_module.crypto_broker_community.developer.bitdubai.version_1.structure.CryptoBrokerCommunityManager</code>
 * bla bla bla.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 21/12/2015.
 */
public class CryptoBrokerCommunityManager implements CryptoBrokerCommunitySubAppModuleManager {

    private final CryptoBrokerIdentityManager        cryptoBrokerIdentityManager           ;
    private final CryptoBrokerActorConnectionManager cryptoBrokerActorConnectionManager    ;
    private final CryptoBrokerManager                cryptoBrokerActorNetworkServiceManager;
    private final CryptoCustomerIdentityManager      cryptoCustomerIdentityManager         ;
    private final ErrorManager                       errorManager                          ;
    private final PluginFileSystem                   pluginFileSystem                      ;
    private final UUID                               pluginId                              ;
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
        System.out.println("CBC CryptoBrokerCommunityManager initttttt!!!!!");

        this.cryptoBrokerIdentityManager            = cryptoBrokerIdentityManager           ;
        this.cryptoBrokerActorConnectionManager     = cryptoBrokerActorConnectionManager    ;
        this.cryptoBrokerActorNetworkServiceManager = cryptoBrokerActorNetworkServiceManager;
        this.cryptoCustomerIdentityManager          = cryptoCustomerIdentityManager         ;
        this.errorManager                           = errorManager                          ;
        this.pluginFileSystem                       = pluginFileSystem                      ;
        this.pluginId                               = pluginId                              ;
        this.pluginVersionReference                 = pluginVersionReference                ;
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
                selectableIdentities.add(new SelectableIdentity(cbi));

            final List<CryptoCustomerIdentity> cryptoCustomerIdentities = cryptoCustomerIdentityManager.getAllCryptoCustomerFromCurrentDeviceUser();

            for (final CryptoCustomerIdentity cci : cryptoCustomerIdentities)
                selectableIdentities.add(new SelectableIdentity(cci));

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
    public CryptoBrokerCommunitySearch searchNewCryptoBroker(final CryptoBrokerCommunitySelectableIdentity selectedIdentity) {

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
    public boolean isActorConnected(String publicKey) throws CantValidateConnectionStateException {

        try {
            final CryptoBrokerLinkedActorIdentity linkedActorIdentity = new CryptoBrokerLinkedActorIdentity(publicKey, Actors.CBP_CRYPTO_BROKER);
            final CryptoBrokerActorConnectionSearch search = cryptoBrokerActorConnectionManager.getSearch(linkedActorIdentity);
            search.addConnectionState(ConnectionState.CONNECTED);

            final List<CryptoBrokerActorConnection> actorConnections = search.getResult(Integer.MAX_VALUE, 0);

            for(CryptoBrokerActorConnection connection : actorConnections){
                if(publicKey.equals(connection.getPublicKey()))
                    return true;
            }

        } catch (final CantListActorConnectionsException e) {
            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantValidateConnectionStateException(e, "", "Error trying to list actor connections.");
        }
        return false;
    }

    private SettingsManager<CryptoBrokerCommunitySettings> settingsManager;

    @Override
    public SettingsManager<CryptoBrokerCommunitySettings> getSettingsManager() {

        if (this.settingsManager != null)
            return this.settingsManager;

        this.settingsManager = new SettingsManager<>(
                pluginFileSystem,
                pluginId
        );

        return this.settingsManager;
    }

    @Override
    public CryptoBrokerCommunitySelectableIdentity getSelectedActorIdentity() throws CantGetSelectedActorIdentityException, ActorIdentityNotSelectedException {
        System.out.print("CBC getSelectedActorIdentity.. (PK=" + this.subAppPublicKey + ") ");

        //Try to get appSettings
        CryptoBrokerCommunitySettings appSettings = null;
        try {
            appSettings = this.settingsManager.loadAndGetSettings(this.subAppPublicKey);
        }catch (Exception e){appSettings = null;}


        //If appSettings exists, get selectedActorIdentityPublicKey
        if(appSettings != null)
        {
            String lastSelectedIdentityPublicKey = appSettings.getLastSelectedIdentityPublicKey();

            if (lastSelectedIdentityPublicKey != null){

                CryptoBrokerIdentity brokerIdentity = null;
                try{
                    brokerIdentity = cryptoBrokerIdentityManager.getCryptoBrokerIdentity(lastSelectedIdentityPublicKey);
                } catch(IdentityNotFoundException | CantGetCryptoBrokerIdentityException e) {
                    System.out.println("Nope.. ");

                    return null;

                }
                System.out.println("Good");
                return new CryptoBrokerCommunitySelectableIdentityImpl(brokerIdentity.getPublicKey(), Actors.CBP_CRYPTO_BROKER,
                                                                       brokerIdentity.getAlias(), brokerIdentity.getProfileImage());
            }
            //Check if at least one local broker identity has been created
            else
            {
                List<CryptoBrokerIdentity> identitiesInDevice;
                try{
                    identitiesInDevice = cryptoBrokerIdentityManager.listIdentitiesFromCurrentDeviceUser();
                    if(identitiesInDevice.size() > 0)
                    {
                        CryptoBrokerIdentity firstIdentity = identitiesInDevice.get(0);
                        appSettings.setLastSelectedIdentityPublicKey(firstIdentity.getPublicKey());

                        try {
                            this.settingsManager.persistSettings(this.subAppPublicKey, appSettings);
                        }catch (CantPersistSettingsException e){
                            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                        }

                        System.out.println("Good");
                        return new CryptoBrokerCommunitySelectableIdentityImpl(firstIdentity.getPublicKey(), Actors.CBP_CRYPTO_BROKER,
                                firstIdentity.getAlias(), firstIdentity.getProfileImage());
                    }


                }catch (CantListCryptoBrokerIdentitiesException e){
                    //Do nothing
                }
            }

        }
        System.out.println("Nope.. ");

        return null;



//
//        List<CryptoBrokerIdentity> deviceBrokerIdentities = null;
//        try{
//            deviceBrokerIdentities = this.cryptoBrokerIdentityManager.listIdentitiesFromCurrentDeviceUser();
//        } catch( CantListCryptoBrokerIdentitiesException e) {
//            e.printStackTrace();
//        }
//        final CryptoBrokerIdentity foo = deviceBrokerIdentities.get(0);
//
//        return new CryptoBrokerCommunitySelectableIdentityImpl(foo.getPublicKey(), Actors.CBP_CRYPTO_BROKER, foo.getAlias(), foo.getProfileImage());
//
//
//
//        try {
//
//            CryptoBrokerCommunitySelectableIdentity lastSelectedIdentity = getSettingsManager().loadAndGetSettings(this.subAppPublicKey).getLastSelectedIdentity();
//
//            if (lastSelectedIdentity != null)
//                return lastSelectedIdentity;
//            else
//                throw new Exception();
//
//        } catch (Exception e) {
//
//            try {
//
//                getSettingsManager().persistSettings(this.subAppPublicKey, new CryptoBrokerCommunitySettings());
//
//                CryptoBrokerCommunitySelectableIdentity lastSelectedIdentity = getSettingsManager().loadAndGetSettings(this.subAppPublicKey).getLastSelectedIdentity();
//
//                if (lastSelectedIdentity != null)
//                    return lastSelectedIdentity;
//                else
//                    throw new ActorIdentityNotSelectedException("", "There's no an identity selected");
//
//            }catch (final CantPersistSettingsException exception) {
//
//                throw new CantGetSelectedActorIdentityException(exception, "", "Error trying to persist the settings.");
//            } catch (final Exception exception) {
//
//                this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
//                throw new CantGetSelectedActorIdentityException(exception, "", "Unhandled Error.");
//            }
//        } catch (final ActorIdentityNotSelectedException exception) {
//
//            throw exception;
//        } catch (final Exception exception) {
//
//            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
//            throw new CantGetSelectedActorIdentityException(exception, "", "Unhandled Error.");
//        }
    }

    @Override
    public void createIdentity(String name, String phrase, byte[] profile_img) throws Exception {
        CryptoBrokerIdentity createdIdentity = null;
        try{
            createdIdentity = cryptoBrokerIdentityManager.createCryptoBrokerIdentity(name, profile_img);
            cryptoBrokerIdentityManager.publishIdentity(createdIdentity.getPublicKey());
        }catch(CantCreateCryptoBrokerIdentityException | CryptoBrokerIdentityAlreadyExistsException e ) {
            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            return;
        }catch(CantPublishIdentityException | IdentityNotFoundException e) {
            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            return;
        }


        //Try to get appSettings
        CryptoBrokerCommunitySettings appSettings = null;
        try {
            appSettings = this.settingsManager.loadAndGetSettings(this.subAppPublicKey);
        }catch (Exception e){ appSettings = null; }


        //If appSettings exist
        if(appSettings != null){
            appSettings.setLastSelectedIdentityPublicKey(createdIdentity.getPublicKey());

            try {
                this.settingsManager.persistSettings(this.subAppPublicKey, appSettings);
            }catch (CantPersistSettingsException e){
                this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            }
        }
    }

    @Override
    public void setAppPublicKey(final String publicKey) {
        System.out.println("CBC setAppPublicKey a....." + publicKey);

        this.subAppPublicKey = publicKey;
    }

    @Override
    public int[] getMenuNotifications() {
        return new int[0];
    }
}
