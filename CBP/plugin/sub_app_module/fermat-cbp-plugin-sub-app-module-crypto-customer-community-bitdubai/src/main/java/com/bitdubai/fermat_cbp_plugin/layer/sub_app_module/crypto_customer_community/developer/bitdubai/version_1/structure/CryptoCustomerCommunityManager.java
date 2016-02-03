package com.bitdubai.fermat_cbp_plugin.layer.sub_app_module.crypto_customer_community.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantPersistSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_customer.interfaces.CryptoCustomerActorConnectionManager;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_customer.interfaces.CryptoCustomerManager;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.exceptions.CantCreateCryptoBrokerIdentityException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.exceptions.CantGetCryptoBrokerIdentityException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.exceptions.CantListCryptoBrokerIdentitiesException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.exceptions.IdentityNotFoundException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.interfaces.CryptoBrokerIdentity;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.interfaces.CryptoBrokerIdentityManager;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.exceptions.CantCreateCryptoCustomerIdentityException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.exceptions.CantGetCryptoCustomerIdentityException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.interfaces.CryptoCustomerIdentity;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.interfaces.CryptoCustomerIdentityManager;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.settings.CryptoBrokerCommunitySettings;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.exceptions.CantGetCryptoCustomerListException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.exceptions.CantLoginCustomerException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.exceptions.CantStartRequestException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.exceptions.CryptoCustomerCancellingFailedException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.exceptions.CryptoCustomerConnectionRejectionFailedException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.exceptions.CryptoCustomerDisconnectingFailedException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.interfaces.CryptoCustomerCommunityInformation;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.interfaces.CryptoCustomerCommunitySearch;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.interfaces.CryptoCustomerCommunitySelectableIdentity;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.interfaces.CryptoCustomerCommunitySubAppModuleManager;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.settings.CryptoCustomerCommunitySettings;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

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
    public List<CryptoCustomerCommunityInformation> getSuggestionsToContact(int max, int offset) throws CantGetCryptoCustomerListException {
        return null;
    }

    @Override
    public CryptoCustomerCommunitySearch searchNewCryptoCustomer(CryptoCustomerCommunitySelectableIdentity cryptoCustomerCommunitySelectableIdentity) {
        return new CryptoCustomerCommunitySubAppModuleCommunitySearch(cryptoCustomerActorNetworkServiceManager);
    }

    @Override
    public void askCryptoCustomerForAcceptance(String cryptoCustomerToAddName, String cryptoCustomerToAddPublicKey, byte[] profileImage) throws CantStartRequestException {

    }

    @Override
    public void acceptCryptoCustomer(String cryptoCustomerToAddName, String cryptoCustomerToAddPublicKey, byte[] profileImage) throws com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.exceptions.CantAcceptRequestException {

    }

    @Override
    public void denyConnection(String cryptoCustomerToRejectPublicKey) throws CryptoCustomerConnectionRejectionFailedException {

    }

    @Override
    public void disconnectCryptoCustomer(String cryptoCustomerToDisconnectPublicKey) throws CryptoCustomerDisconnectingFailedException {

    }

    @Override
    public void cancelCryptoBroker(String cryptoCustomerToCancelPublicKey) throws CryptoCustomerCancellingFailedException {

    }

    @Override
    public List<CryptoCustomerCommunityInformation> getAllCryptoCustomers(int max, int offset) throws CantGetCryptoCustomerListException {
        return null;
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
        }catch (Exception e){appSettings = null;}


        //Get all broker identities on local device
        List<CryptoBrokerIdentity> identitiesInDevice = null;
        try{
            identitiesInDevice = cryptoBrokerIdentityManager.listIdentitiesFromCurrentDeviceUser();
        } catch(CantListCryptoBrokerIdentitiesException e) { /*Do nothing*/ }


        //If appSettings exists, get its selectedActorIdentityPublicKey property
        if(appSettings != null)
        {
            String lastSelectedIdentityPublicKey = appSettings.getLastSelectedIdentityPublicKey();

            if (lastSelectedIdentityPublicKey != null){

                CryptoCustomerCommunitySelectableIdentityImpl selectedIdentity = null;

                for(CryptoBrokerIdentity i : identitiesInDevice) {
                    if(i.getPublicKey().equals(lastSelectedIdentityPublicKey))
                        selectedIdentity = new CryptoCustomerCommunitySelectableIdentityImpl(i.getPublicKey(), Actors.CBP_CRYPTO_BROKER, i.getAlias(), i.getProfileImage());
                }

                if(selectedIdentity == null)
                    return null;

                //System.out.println("Good");
                return selectedIdentity;
            }
            //Check if at least one local broker identity has been created
            else
            {

                if(identitiesInDevice.size() > 0)
                {
                    CryptoCustomerCommunitySelectableIdentityImpl selectedIdentity
                            = new CryptoCustomerCommunitySelectableIdentityImpl(identitiesInDevice.get(0).getPublicKey(),
                                                                              Actors.CBP_CRYPTO_BROKER,
                                                                              identitiesInDevice.get(0).getAlias(),
                                                                              identitiesInDevice.get(0).getProfileImage());

                    appSettings.setLastSelectedIdentityPublicKey(selectedIdentity.getPublicKey());

                    try {
                        this.settingsManager.persistSettings(this.subAppPublicKey, appSettings);
                    }catch (CantPersistSettingsException e){
                        this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                    }

                    //System.out.println("Good");
                    return selectedIdentity;
                }

            }

        }

        //System.out.println("Nope.. ");
        return null;

    }

    @Override
    public void createIdentity(String name, String phrase, byte[] profile_img) throws Exception {


        //TODO: Por ahora se crea un John / Jane brokers.. pues interesa en el flujo actual, en el futuro el dialogo deberia preguntar si la persona quiere es crear un
        // broker o un customer.. y crear aqui lo que se necesite crear.
        CryptoBrokerIdentity createdIdentity = null;
        try{
            createdIdentity = cryptoBrokerIdentityManager.createCryptoBrokerIdentity(name, profile_img);
            cryptoBrokerIdentityManager.publishIdentity(createdIdentity.getPublicKey());
        }catch(CantCreateCryptoBrokerIdentityException e) {
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
            appSettings.setLastSelectedIdentityPublicKey(createdIdentity.getPublicKey());

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
