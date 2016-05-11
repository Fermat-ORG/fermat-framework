package com.bitdubai.fermat_cbp_plugin.layer.sub_app_module.crypto_customer_community.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.ActorConnectionNotFoundException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantAcceptActorConnectionRequestException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantDenyActorConnectionRequestException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantDisconnectFromActorException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantListActorConnectionsException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.UnexpectedConnectionStateException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantPersistSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_customer.interfaces.CryptoCustomerActorConnectionManager;
import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_customer.interfaces.CryptoCustomerActorConnectionSearch;
import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_customer.utils.CryptoCustomerActorConnection;
import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_customer.utils.CryptoCustomerLinkedActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_customer.exceptions.CantListCryptoCustomersException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_customer.interfaces.CryptoCustomerManager;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.exceptions.CantListCryptoBrokerIdentitiesException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.interfaces.CryptoBrokerIdentity;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.interfaces.CryptoBrokerIdentityManager;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.interfaces.CryptoCustomerIdentityManager;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CantListIdentitiesToSelectException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.exceptions.CantGetCryptoCustomerSearchResult;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.exceptions.CantAcceptRequestException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.exceptions.CantGetCryptoCustomerListException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.exceptions.CantLoginCustomerException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.exceptions.CantStartRequestException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.exceptions.CryptoCustomerCancellingFailedException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.exceptions.CryptoCustomerDisconnectingFailedException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.interfaces.CryptoCustomerCommunityInformation;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.interfaces.CryptoCustomerCommunitySearch;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.interfaces.CryptoCustomerCommunitySelectableIdentity;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.interfaces.CryptoCustomerCommunitySubAppModuleManager;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.interfaces.LinkedCryptoCustomerIdentity;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.settings.CryptoCustomerCommunitySettings;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Alejandro Bicelis on 2/2/2016.
 */

public class CryptoCustomerCommunityManager implements CryptoCustomerCommunitySubAppModuleManager {

    private final CryptoBrokerIdentityManager          cryptoBrokerIdentityManager              ;
    private final CryptoCustomerActorConnectionManager cryptoCustomerActorConnectionManager     ;
    private final CryptoCustomerManager                cryptoCustomerActorNetworkServiceManager ;
    private final CryptoCustomerIdentityManager        cryptoCustomerIdentityManager            ;
    private final ErrorManager                         errorManager                             ;
    private final PluginFileSystem                     pluginFileSystem                         ;
    private final UUID                                 pluginId                                 ;
    private final PluginVersionReference               pluginVersionReference                   ;

    private       String                              subAppPublicKey                           ;

    public CryptoCustomerCommunityManager(final CryptoBrokerIdentityManager cryptoBrokerIdentityManager,
                                          final CryptoCustomerActorConnectionManager cryptoCustomerActorConnectionManager,
                                          final CryptoCustomerManager cryptoCustomerActorNetworkServiceManager,
                                          final CryptoCustomerIdentityManager cryptoCustomerIdentityManager,
                                          final ErrorManager errorManager,
                                          final PluginFileSystem pluginFileSystem,
                                          final UUID pluginId,
                                          final PluginVersionReference pluginVersionReference) {

        this.cryptoBrokerIdentityManager              = cryptoBrokerIdentityManager              ;
        this.cryptoCustomerActorConnectionManager     = cryptoCustomerActorConnectionManager     ;
        this.cryptoCustomerActorNetworkServiceManager = cryptoCustomerActorNetworkServiceManager ;
        this.cryptoCustomerIdentityManager            = cryptoCustomerIdentityManager            ;
        this.errorManager                             = errorManager                             ;
        this.pluginFileSystem                         = pluginFileSystem                         ;
        this.pluginId                                 = pluginId                                 ;
        this.pluginVersionReference                   = pluginVersionReference                   ;
    }




    @Override
    public List<CryptoCustomerCommunityInformation> listWorldCryptoCustomers(CryptoCustomerCommunitySelectableIdentity selectedIdentity, int max, int offset) throws CantListCryptoCustomersException {
        List<CryptoCustomerCommunityInformation> worldCustomerList;
        List<CryptoCustomerActorConnection> actorConnections;

        try{
            worldCustomerList = getCryptoCustomerSearch().getResult();
        } catch (CantGetCryptoCustomerSearchResult e) {
            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListCryptoCustomersException(e, "", "Error in listWorldCryptoCustomers trying to list world customers");
        }


        try {

            final CryptoCustomerLinkedActorIdentity linkedActorIdentity = new CryptoCustomerLinkedActorIdentity(selectedIdentity.getPublicKey(), selectedIdentity.getActorType());
            final CryptoCustomerActorConnectionSearch search = cryptoCustomerActorConnectionManager.getSearch(linkedActorIdentity);
            search.addConnectionState(ConnectionState.CONNECTED);

            actorConnections = search.getResult(Integer.MAX_VALUE, 0);

        } catch (final CantListActorConnectionsException e) {
            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListCryptoCustomersException(e, "", "Error trying to list actor connections.");
        }


        CryptoCustomerCommunityInformation worldCustomer;
        for(int i = 0; i < worldCustomerList.size(); i++)
        {
            worldCustomer = worldCustomerList.get(i);
            for(CryptoCustomerActorConnection connectedCustomer : actorConnections)
            {
                if(worldCustomer.getPublicKey().equals(connectedCustomer.getPublicKey()))
                    worldCustomerList.set(i, new CryptoCustomerCommunitySubAppModuleInformation(worldCustomer.getPublicKey(), worldCustomer.getAlias(), worldCustomer.getImage(), connectedCustomer.getConnectionState(), connectedCustomer.getConnectionId()));
            }
        }

        return worldCustomerList;
    }

    @Override
    public List<CryptoCustomerCommunitySelectableIdentity> listSelectableIdentities() throws CantListIdentitiesToSelectException {
        try {

            final List<CryptoCustomerCommunitySelectableIdentity> selectableIdentities = new ArrayList<>();

            final List<CryptoBrokerIdentity> cryptoBrokerIdentities = cryptoBrokerIdentityManager.listIdentitiesFromCurrentDeviceUser();

            for (final CryptoBrokerIdentity cbi : cryptoBrokerIdentities)
                selectableIdentities.add(new CryptoCustomerCommunitySelectableIdentityImpl(cbi));

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
    public void setSelectedActorIdentity(CryptoCustomerCommunitySelectableIdentity identity) {
        //Try to get appSettings
        CryptoCustomerCommunitySettings appSettings = null;
        try {
            appSettings = this.settingsManager.loadAndGetSettings(this.subAppPublicKey);
        }catch (Exception e){ appSettings = null; }

        //If appSettings exist, save identity
        if(appSettings != null){
            if(identity.getPublicKey() != null)
                appSettings.setLastSelectedIdentityPublicKey(identity.getPublicKey());
            try {
                this.settingsManager.persistSettings(this.subAppPublicKey, appSettings);
            }catch (CantPersistSettingsException e){
                this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            }
        }
    }


    @Override
    public List<CryptoCustomerCommunityInformation> getSuggestionsToContact(int max, int offset) throws CantGetCryptoCustomerListException {
        return null;
    }

    @Override
    public CryptoCustomerCommunitySearch getCryptoCustomerSearch() {
        return new CryptoCustomerCommunitySubAppModuleCommunitySearch(cryptoCustomerActorNetworkServiceManager);
    }

    @Override
    public void askCryptoCustomerForAcceptance(String cryptoCustomerToAddName, String cryptoCustomerToAddPublicKey, byte[] profileImage) throws CantStartRequestException {

    }

    @Override
    public void acceptCryptoCustomer(UUID connectionId) throws CantAcceptRequestException {
        try {
            System.out.println("************* im accepting in module the request: "+connectionId);
            this.cryptoCustomerActorConnectionManager.acceptConnection(connectionId);
        } catch (CantAcceptActorConnectionRequestException | ActorConnectionNotFoundException | UnexpectedConnectionStateException e)
        {
            throw new CantAcceptRequestException("", e, "", "");
        }
    }

    @Override
    public void denyConnection(UUID connectionId) throws CantDenyActorConnectionRequestException {
        try {
            this.cryptoCustomerActorConnectionManager.denyConnection(connectionId);
        } catch (CantDenyActorConnectionRequestException | ActorConnectionNotFoundException | UnexpectedConnectionStateException e)
        {
            throw new CantDenyActorConnectionRequestException("", e, "", "");
        }

    }

    @Override
    public void disconnectCryptoCustomer(final UUID requestId) throws CryptoCustomerDisconnectingFailedException {

        try {
                cryptoCustomerActorConnectionManager.disconnect(requestId);

        } catch (final CantDisconnectFromActorException | UnexpectedConnectionStateException e) {

            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CryptoCustomerDisconnectingFailedException("", e, "", "Error trying to disconnect the actor connection.");
        } catch (final ActorConnectionNotFoundException e) {

            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CryptoCustomerDisconnectingFailedException("", e, "", "Connection request not found.");
        } catch (final Exception e) {

            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CryptoCustomerDisconnectingFailedException("", e, "", "Unhandled Exception.");
        }
    }

    @Override
    public void cancelCryptoBroker(String cryptoCustomerToCancelPublicKey) throws CryptoCustomerCancellingFailedException {

    }

    @Override
    public List<CryptoCustomerCommunityInformation> getAllCryptoCustomers(int max, int offset) throws CantGetCryptoCustomerListException {
        return null;
    }


    @Override
    public List<LinkedCryptoCustomerIdentity> listCryptoCustomersPendingLocalAction(final CryptoCustomerCommunitySelectableIdentity selectedIdentity,
                                                                                      final int max,
                                                                                      final int offset) throws CantGetCryptoCustomerListException {

        try {

            final CryptoCustomerLinkedActorIdentity linkedActorIdentity = new CryptoCustomerLinkedActorIdentity(
                    selectedIdentity.getPublicKey(),
                    selectedIdentity.getActorType()
            );

            final CryptoCustomerActorConnectionSearch search = cryptoCustomerActorConnectionManager.getSearch(linkedActorIdentity);

            search.addConnectionState(ConnectionState.PENDING_LOCALLY_ACCEPTANCE);

            final List<CryptoCustomerActorConnection> actorConnections = search.getResult(max, offset);

            final List<LinkedCryptoCustomerIdentity> linkedCryptoCustomerIdentityList = new ArrayList<>();

            for (CryptoCustomerActorConnection ccac : actorConnections)
                linkedCryptoCustomerIdentityList.add(new LinkedCryptoCustomerIdentityImpl(ccac));

            return linkedCryptoCustomerIdentityList;

        } catch (final CantListActorConnectionsException e) {

            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetCryptoCustomerListException("", e, "", "Error trying to list actor connections.");
        } catch (final Exception e) {

            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetCryptoCustomerListException("", e, "", "Unhandled Exception.");
        }
    }

    @Override
    public List<CryptoCustomerCommunityInformation> listAllConnectedCryptoCustomers(CryptoCustomerCommunitySelectableIdentity selectedIdentity, int max, int offset) throws CantGetCryptoCustomerListException {

        try {

            final CryptoCustomerLinkedActorIdentity linkedActorIdentity = new CryptoCustomerLinkedActorIdentity(
                    selectedIdentity.getPublicKey(),
                    selectedIdentity.getActorType()
            );

            final CryptoCustomerActorConnectionSearch search = cryptoCustomerActorConnectionManager.getSearch(linkedActorIdentity);

            search.addConnectionState(ConnectionState.CONNECTED);

            final List<CryptoCustomerActorConnection> actorConnections = search.getResult(max, offset);

            final List<CryptoCustomerCommunityInformation> cryptoCustomerCommunityInformationList = new ArrayList<>();

            for (CryptoCustomerActorConnection ccac : actorConnections)
                cryptoCustomerCommunityInformationList.add(new CryptoCustomerCommunitySubAppModuleInformation(ccac));

            return cryptoCustomerCommunityInformationList;

        } catch (final CantListActorConnectionsException e) {

            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetCryptoCustomerListException("", e, "", "Error trying to list actor connections.");
        } catch (final Exception e) {

            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetCryptoCustomerListException("", e, "", "Unhandled Exception.");
        }
    }

    @Override
    public List<CryptoCustomerCommunityInformation> getCryptoCustomersWaitingYourAcceptance(int max, int offset) throws CantGetCryptoCustomerListException {
        return null;
    }

    @Override
    public List<CryptoCustomerCommunityInformation> getCryptoCustomersWaitingTheirAcceptance(int max, int offset) throws CantGetCryptoCustomerListException {
        return null;
    }

    @Override
    public void login(String customerPublicKey) throws CantLoginCustomerException {

    }

    private SettingsManager<CryptoCustomerCommunitySettings> settingsManager;

    @Override
    public SettingsManager<CryptoCustomerCommunitySettings> getSettingsManager() {

        if (this.settingsManager != null)
            return this.settingsManager;

        this.settingsManager = new SettingsManager<>(
                pluginFileSystem,
                pluginId
        );

        return this.settingsManager;
    }

    @Override
    public CryptoCustomerCommunitySelectableIdentity getSelectedActorIdentity() throws CantGetSelectedActorIdentityException, ActorIdentityNotSelectedException {

        //Try to get appSettings
        CryptoCustomerCommunitySettings appSettings = null;
        try {
            appSettings = this.settingsManager.loadAndGetSettings(this.subAppPublicKey);
        }catch (Exception e){ return null; }

        //Get all broker identities on local device
        List<CryptoBrokerIdentity> brokerIdentitiesInDevice = new ArrayList<>();
        try{
            brokerIdentitiesInDevice = cryptoBrokerIdentityManager.listIdentitiesFromCurrentDeviceUser();
        } catch(CantListCryptoBrokerIdentitiesException e) { /*Do nothing*/ }

        //No registered users in device
        if(brokerIdentitiesInDevice.size() == 0)
            throw new CantGetSelectedActorIdentityException("", null, "", "");


        //If appSettings exists, get its selectedActorIdentityPublicKey property
        if(appSettings != null)
        {
            String lastSelectedIdentityPublicKey = appSettings.getLastSelectedIdentityPublicKey();

            if (lastSelectedIdentityPublicKey != null){

                CryptoCustomerCommunitySelectableIdentityImpl selectedIdentity = null;

                for(CryptoBrokerIdentity i : brokerIdentitiesInDevice) {
                    if(i.getPublicKey().equals(lastSelectedIdentityPublicKey))
                        selectedIdentity = new CryptoCustomerCommunitySelectableIdentityImpl(i.getPublicKey(), Actors.CBP_CRYPTO_BROKER, i.getAlias(), i.getProfileImage());
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


        //Try to get appSettings
        CryptoCustomerCommunitySettings appSettings = null;
        try {
            appSettings = this.settingsManager.loadAndGetSettings(this.subAppPublicKey);
        }catch (Exception e){ appSettings = null; }


        //If appSettings exist
        if(appSettings != null){
            appSettings.setLastSelectedIdentityPublicKey(createdPublicKey);
            try {
                this.settingsManager.persistSettings(this.subAppPublicKey, appSettings);
            }catch (CantPersistSettingsException e){
                this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            }
        }
    }


    @Override
    public void setAppPublicKey(String publicKey) {
        this.subAppPublicKey = publicKey;
    }

    @Override
    public int[] getMenuNotifications() {
        return new int[0];
    }
}
