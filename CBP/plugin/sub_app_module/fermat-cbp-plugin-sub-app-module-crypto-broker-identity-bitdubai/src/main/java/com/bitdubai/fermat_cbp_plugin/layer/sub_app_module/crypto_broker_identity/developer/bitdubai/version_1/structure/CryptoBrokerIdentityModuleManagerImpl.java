package com.bitdubai.fermat_cbp_plugin.layer.sub_app_module.crypto_broker_identity.developer.bitdubai.version_1.structure;

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
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.exceptions.CantCreateCryptoBrokerIdentityException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.exceptions.CantHideIdentityException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.exceptions.CantListCryptoBrokerIdentitiesException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.exceptions.CantPublishIdentityException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.exceptions.CantUnHideIdentityException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.exceptions.CantUpdateBrokerIdentityException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.exceptions.CryptoBrokerIdentityAlreadyExistsException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.exceptions.IdentityNotFoundException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.interfaces.CryptoBrokerIdentity;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.interfaces.CryptoBrokerIdentityManager;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_identity.IdentityBrokerPreferenceSettings;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_identity.exceptions.CantCreateCryptoBrokerException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_identity.exceptions.CantHideCryptoBrokerException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_identity.exceptions.CantListCryptoBrokersException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_identity.exceptions.CantPublishCryptoBrokerException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_identity.exceptions.CantUnHideCryptoBrokerException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_identity.exceptions.CryptoBrokerNotFoundException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_identity.interfaces.CryptoBrokerIdentityInformation;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_identity.interfaces.CryptoBrokerIdentityModuleManager;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_identity.utils.CryptoBrokerIdentityInformationImpl;
import com.bitdubai.fermat_cbp_plugin.layer.sub_app_module.crypto_broker_identity.developer.bitdubai.version_1.CryptoBrokerIdentitySubAppModulePluginRoot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Yordin Alayn on 19.04.16.
 */
public class CryptoBrokerIdentityModuleManagerImpl
        extends ModuleManagerImpl<IdentityBrokerPreferenceSettings> implements CryptoBrokerIdentityModuleManager, Serializable {

    private CryptoBrokerIdentityManager identityManager;
    private CryptoBrokerIdentitySubAppModulePluginRoot pluginRoot;
    private final LocationManager locationManager;

    public CryptoBrokerIdentityModuleManagerImpl(
            CryptoBrokerIdentityManager identityManager,
            PluginFileSystem pluginFileSystem,
            UUID pluginId,
            CryptoBrokerIdentitySubAppModulePluginRoot pluginRoot,
            LocationManager locationManager) {
        super(pluginFileSystem, pluginId);
        this.identityManager = identityManager;
        this.pluginRoot = pluginRoot;
        this.locationManager = locationManager;
    }

    @Override
    public CryptoBrokerIdentityInformation createCryptoBrokerIdentity(String alias, byte[] image, long accuracy, GeoFrequency frequency) throws CantCreateCryptoBrokerException {

        try {

            CryptoBrokerIdentity identity = this.identityManager.createCryptoBrokerIdentity(alias, image, accuracy, frequency);
            return converIdentityToInformation(identity);

        } catch (CantCreateCryptoBrokerIdentityException e) {
            throw new CantCreateCryptoBrokerException(e, "", "There was an error trying to create an identity.");
        } catch (CryptoBrokerIdentityAlreadyExistsException e) {
            throw new CantCreateCryptoBrokerException(e, "", "A similar alias already exists.");
        }
    }

    @Override
    public void updateCryptoBrokerIdentity(CryptoBrokerIdentityInformation cryptoBrokerIdentity) throws CantUpdateBrokerIdentityException {
        this.identityManager.updateCryptoBrokerIdentity(cryptoBrokerIdentity.getAlias(), cryptoBrokerIdentity.getPublicKey(), cryptoBrokerIdentity.getProfileImage(), cryptoBrokerIdentity.getAccuracy(), cryptoBrokerIdentity.getFrequency());
    }

    @Override
    public void unHideIdentity(String publicKey) throws CantUnHideCryptoBrokerException, CryptoBrokerNotFoundException {

        try {
            this.identityManager.unHideIdentity(publicKey);
        } catch (CantUnHideIdentityException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantUnHideCryptoBrokerException(e, "", "Problem UNHIDE the identity.");
        } catch (IdentityNotFoundException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantUnHideCryptoBrokerException(e, "", "Cannot find the identity.");
        } catch (Exception e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantUnHideCryptoBrokerException(e, "", "Unhandled Exception.");
        }
    }

    @Override
    public void publishIdentity(String publicKey) throws CantPublishCryptoBrokerException, CryptoBrokerNotFoundException {

        try {
            this.identityManager.publishIdentity(publicKey);
        } catch (CantPublishIdentityException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantPublishCryptoBrokerException(e, "", "Problem publishing the identity.");
        } catch (IdentityNotFoundException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantPublishCryptoBrokerException(e, "", "Cannot find the identity.");
        } catch (Exception e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantPublishCryptoBrokerException(e, "", "Unhandled Exception.");
        }
    }

    @Override
    public void hideIdentity(String publicKey) throws CantHideCryptoBrokerException, CryptoBrokerNotFoundException {

        try {

            this.identityManager.hideIdentity(publicKey);

        } catch (CantHideIdentityException e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantHideCryptoBrokerException(e, "", "Problem hiding the identity.");
        } catch (IdentityNotFoundException e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantHideCryptoBrokerException(e, "", "Cannot find the identity.");
        } catch (Exception e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantHideCryptoBrokerException(e, "", "Unhandled Exception.");
        }
    }

    @Override
    public List<CryptoBrokerIdentityInformation> listIdentities(int max, int offset) throws CantListCryptoBrokersException {
        try {
            List<CryptoBrokerIdentityInformation> cryptoBrokers = new ArrayList<>();
            for (CryptoBrokerIdentity identity : this.identityManager.listIdentitiesFromCurrentDeviceUser()) {
                cryptoBrokers.add(converIdentityToInformation(identity));
            }
            return cryptoBrokers;
        } catch (CantListCryptoBrokerIdentitiesException e) {
            throw new CantListCryptoBrokersException(CantListCryptoBrokersException.DEFAULT_MESSAGE, e, "", "");
        }
    }

    private CryptoBrokerIdentityInformation converIdentityToInformation(final CryptoBrokerIdentity identity) {
        return new CryptoBrokerIdentityInformationImpl(identity.getAlias(), identity.getPublicKey(), identity.getProfileImage(), identity.getExposureLevel(), identity.getAccuracy(), identity.getFrequency());
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

    /**
     * Through the method <code>getLocation</code> we can get the location coordinates of user.
     *
     * @return a new instance of the settings manager for the specified fermat settings object.
     */
    @Override
    public Location getLocation() throws CantGetDeviceLocationException {
        return locationManager.getLocation();
    }

    @Override
    public Boolean itHasAssociatedWallet(String brokerPublicKey) {
        return null;
    }
}