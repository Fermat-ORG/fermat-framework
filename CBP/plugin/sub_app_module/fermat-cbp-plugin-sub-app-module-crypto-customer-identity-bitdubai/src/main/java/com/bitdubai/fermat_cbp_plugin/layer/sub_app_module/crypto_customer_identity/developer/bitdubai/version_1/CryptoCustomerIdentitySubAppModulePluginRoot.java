package com.bitdubai.fermat_cbp_plugin.layer.sub_app_module.crypto_customer_identity.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractModule;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantGetModuleManagerException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.exceptions.CantUpdateCustomerIdentityException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.exceptions.CantCreateCryptoCustomerIdentityException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.exceptions.CantListCryptoCustomerIdentityException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.exceptions.CantPublishIdentityException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.exceptions.IdentityNotFoundException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.interfaces.CryptoCustomerIdentity;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.interfaces.CryptoCustomerIdentityManager;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_identity.IdentityCustomerPreferenceSettings;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_identity.exceptions.CantGetCryptoCustomerListException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_identity.exceptions.CouldNotCreateCryptoCustomerException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_identity.exceptions.CouldNotPublishCryptoCustomerException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_identity.exceptions.CouldNotUnPublishCryptoCustomerException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_identity.interfaces.CryptoCustomerIdentityInformation;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_identity.interfaces.CryptoCustomerIdentityModuleManager;
import com.bitdubai.fermat_cbp_plugin.layer.sub_app_module.crypto_customer_identity.developer.bitdubai.version_1.structure.CryptoCustomerIdentityInformationImpl;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by natalia on 16.09.15.
 */
//public class CryptoCustomerIdentitySubAppModulePluginRoot extends AbstractPlugin implements CryptoCustomerIdentityModuleManager {
public class CryptoCustomerIdentitySubAppModulePluginRoot extends AbstractModule<IdentityCustomerPreferenceSettings, ActiveActorIdentityInformation> implements
        CryptoCustomerIdentityModuleManager {

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.IDENTITY, plugin = Plugins.CRYPTO_CUSTOMER)
    private CryptoCustomerIdentityManager identityManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    private PluginFileSystem pluginFileSystem;

    public CryptoCustomerIdentitySubAppModulePluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    @Override
    public void start() throws CantStartPluginException {
        try {
            this.serviceStatus = ServiceStatus.STARTED;
        } catch (Exception exception) {
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    @Override
    public CryptoCustomerIdentityInformation createCryptoCustomerIdentity(String cryptoCustomerName, byte[] profileImage) throws CouldNotCreateCryptoCustomerException {
        try {
            CryptoCustomerIdentity identity = this.identityManager.createCryptoCustomerIdentity(cryptoCustomerName, profileImage);
            return converIdentityToInformation(identity);
        } catch (CantCreateCryptoCustomerIdentityException e) {
            throw new CouldNotCreateCryptoCustomerException(e, "", "");
        }
    }

    @Override
    public void updateCryptoCustomerIdentity(CryptoCustomerIdentityInformation cryptoBrokerIdentity) throws CantUpdateCustomerIdentityException {
        this.identityManager.updateCryptoCustomerIdentity(cryptoBrokerIdentity.getAlias(), cryptoBrokerIdentity.getPublicKey(), cryptoBrokerIdentity.getProfileImage());
    }

    @Override
    public void publishCryptoCustomerIdentity(String cryptoCustomerPublicKey) throws CouldNotPublishCryptoCustomerException {

        try {
            System.out.println("************* voy al identity manager publicar la identidad");
            this.identityManager.publishIdentity(cryptoCustomerPublicKey);
        } catch (CantPublishIdentityException | IdentityNotFoundException e) {
            throw new CouldNotPublishCryptoCustomerException(e, "", "");
        }
    }

    @Override
    public void unPublishCryptoCustomerIdentity(String cryptoCustomerPublicKey) throws CouldNotUnPublishCryptoCustomerException {

    }

    @Override
    public List<CryptoCustomerIdentityInformation> getAllCryptoCustomersIdentities(int max, int offset) throws CantGetCryptoCustomerListException {
        try {
            List<CryptoCustomerIdentityInformation> cryptoCustomers = new ArrayList<>();
            for(CryptoCustomerIdentity identity : this.identityManager.listAllCryptoCustomerFromCurrentDeviceUser()){
                cryptoCustomers.add(converIdentityToInformation(identity));
            }
            return cryptoCustomers;
        } catch (CantListCryptoCustomerIdentityException e) {
            throw new CantGetCryptoCustomerListException(CantGetCryptoCustomerListException.DEFAULT_MESSAGE, e, "","");
        }
    }

    private CryptoCustomerIdentityInformation converIdentityToInformation(final CryptoCustomerIdentity identity){
        return new CryptoCustomerIdentityInformationImpl(identity.getAlias(), identity.getPublicKey(), identity.getProfileImage(), identity.isPublished());
    }


    private SettingsManager<IdentityCustomerPreferenceSettings> settingsManager;

    @Override
    public SettingsManager<IdentityCustomerPreferenceSettings> getSettingsManager() {
        if (this.settingsManager != null)
            return this.settingsManager;

        this.settingsManager = new SettingsManager<>(
                pluginFileSystem,
                pluginId
        );

        return this.settingsManager;
    }

    @Override
    public ActiveActorIdentityInformation getSelectedActorIdentity() throws CantGetSelectedActorIdentityException, ActorIdentityNotSelectedException {
        return null;
    }

    @Override
    public void createIdentity(String name, String phrase, byte[] profile_img) throws Exception {

    }

    @Override
    public void setAppPublicKey(String publicKey) {

    }

    @Override
    public int[] getMenuNotifications() {
        return new int[0];
    }

    @Override
    public ModuleManager<IdentityCustomerPreferenceSettings, ActiveActorIdentityInformation> getModuleManager() throws CantGetModuleManagerException {
        return this;
    }
}