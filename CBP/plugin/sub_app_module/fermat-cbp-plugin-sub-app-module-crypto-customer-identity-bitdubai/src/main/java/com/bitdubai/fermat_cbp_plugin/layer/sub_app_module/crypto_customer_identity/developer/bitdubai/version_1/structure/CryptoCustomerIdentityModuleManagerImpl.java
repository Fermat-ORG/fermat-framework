package com.bitdubai.fermat_cbp_plugin.layer.sub_app_module.crypto_customer_identity.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.GeoFrequency;
import com.bitdubai.fermat_api.layer.modules.ModuleManagerImpl;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_api.layer.osa_android.location_system.LocationManager;
import com.bitdubai.fermat_api.layer.osa_android.location_system.exceptions.CantGetDeviceLocationException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.exceptions.CantUpdateCustomerIdentityException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.exceptions.CantCreateCryptoCustomerIdentityException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.exceptions.CantListCryptoCustomerIdentityException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.exceptions.CantPublishIdentityException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.exceptions.IdentityNotFoundException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.interfaces.CryptoCustomerIdentity;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.interfaces.CryptoCustomerIdentityManager;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer.exceptions.CantGetCryptoCustomerListException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_identity.IdentityCustomerPreferenceSettings;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_identity.exceptions.CouldNotCreateCryptoCustomerException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_identity.exceptions.CouldNotPublishCryptoCustomerException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_identity.interfaces.CryptoCustomerIdentityInformation;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_identity.interfaces.CryptoCustomerIdentityModuleManager;
import com.bitdubai.fermat_cbp_plugin.layer.sub_app_module.crypto_customer_identity.developer.bitdubai.version_1.CryptoCustomerIdentitySubAppModulePluginRoot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * Created by Yordin Alayn on 19.04.16.
 */
public class CryptoCustomerIdentityModuleManagerImpl extends ModuleManagerImpl<IdentityCustomerPreferenceSettings>
        implements CryptoCustomerIdentityModuleManager, Serializable {

    private CryptoCustomerIdentityManager identityManager;
    private CryptoCustomerIdentitySubAppModulePluginRoot pluginRoot;
    private final LocationManager locationManager;


    public CryptoCustomerIdentityModuleManagerImpl(CryptoCustomerIdentityManager identityManager,
                                                   PluginFileSystem pluginFileSystem,
                                                   UUID pluginId,
                                                   CryptoCustomerIdentitySubAppModulePluginRoot pluginRoot,
                                                   LocationManager locationManager) {
        super(pluginFileSystem, pluginId);

        this.identityManager = identityManager;
        this.pluginRoot = pluginRoot;
        this.locationManager = locationManager;
    }

    @Override
    public CryptoCustomerIdentityInformation createCryptoCustomerIdentity(String cryptoCustomerName, byte[] profileImage, long accuracy, GeoFrequency frequency) throws CouldNotCreateCryptoCustomerException {
        try {
            CryptoCustomerIdentity identity = this.identityManager.createCryptoCustomerIdentity(cryptoCustomerName, profileImage, accuracy, frequency);
            return converIdentityToInformation(identity);
        } catch (CantCreateCryptoCustomerIdentityException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CouldNotCreateCryptoCustomerException(e, "Crypto Customer Identity Module Manager", "Cant Create Crypto Customer Identity");
        }
    }

    @Override
    public void updateCryptoCustomerIdentity(CryptoCustomerIdentityInformation cryptoBrokerIdentity) throws CantUpdateCustomerIdentityException {
        try {
            this.identityManager.updateCryptoCustomerIdentity(cryptoBrokerIdentity.getAlias(), cryptoBrokerIdentity.getPublicKey(), cryptoBrokerIdentity.getProfileImage(), cryptoBrokerIdentity.getAccuracy(), cryptoBrokerIdentity.getFrequency());
        } catch (CantUpdateCustomerIdentityException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantUpdateCustomerIdentityException(e, "Crypto Customer Identity Module Manager", "Cant Update Crypto Customer Identity");
        }
    }

    @Override
    public void publishCryptoCustomerIdentity(String cryptoCustomerPublicKey) throws CouldNotPublishCryptoCustomerException {

        try {
            System.out.println("************* voy al identity manager publicar la identidad");
            this.identityManager.publishIdentity(cryptoCustomerPublicKey);
        } catch (CantPublishIdentityException | IdentityNotFoundException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CouldNotPublishCryptoCustomerException(e, "Crypto Customer Identity Module Manager", "Cant Publish Crypto Customer Identity");
        }
    }

    @Override
    public void unPublishCryptoCustomerIdentity(String cryptoCustomerPublicKey) throws com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_identity.exceptions.CouldNotUnPublishCryptoCustomerException {

    }

    @Override
    public List<CryptoCustomerIdentityInformation> getAllCryptoCustomersIdentities(int max, int offset) throws com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_identity.exceptions.CantGetCryptoCustomerListException {
        try {
            List<CryptoCustomerIdentityInformation> cryptoCustomers = new ArrayList<>();
            for (CryptoCustomerIdentity identity : this.identityManager.listAllCryptoCustomerFromCurrentDeviceUser()) {
                cryptoCustomers.add(converIdentityToInformation(identity));
            }
            return cryptoCustomers;
        } catch (CantListCryptoCustomerIdentityException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_identity.exceptions.CantGetCryptoCustomerListException(CantGetCryptoCustomerListException.DEFAULT_MESSAGE, e, "Crypto Customer Identity Module Manager", "Cant Get List All Crypto Customer Identity");
        }
    }

    private CryptoCustomerIdentityInformation converIdentityToInformation(final CryptoCustomerIdentity identity) {
        return new CryptoCustomerIdentityInformationImpl(identity.getAlias(), identity.getPublicKey(), identity.getProfileImage(), identity.isPublished(), identity.getAccuracy(), identity.getFrequency());
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
    public Location getLocation() throws CantGetDeviceLocationException {
        return locationManager.getLocation();
    }
}
