package com.bitdubai.fermat_cbp_plugin.layer.identity.crypto_broker.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.interfaces.KeyPair;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.GeoFrequency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.enums.SubAppsPublicKeys;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.core.PluginInfo;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.BroadcasterType;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.FermatBundle;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_api.layer.osa_android.location_system.LocationManager;
import com.bitdubai.fermat_api.layer.osa_android.location_system.exceptions.CantGetDeviceLocationException;
import com.bitdubai.fermat_cbp_api.all_definition.constants.CBPBroadcasterConstants;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantCreateNewDeveloperException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.CantExposeIdentitiesException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.CantExposeIdentityException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.interfaces.CryptoBrokerManager;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.utils.CryptoBrokerExposingData;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.ExposureLevel;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.exceptions.CantCreateCryptoBrokerIdentityException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.exceptions.CantGetCryptoBrokerIdentityException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.exceptions.CantHideIdentityException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.exceptions.CantListCryptoBrokerIdentitiesException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.exceptions.CantPublishIdentityException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.exceptions.CantUnHideIdentityException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.exceptions.CantUpdateBrokerIdentityException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.exceptions.CryptoBrokerIdentityAlreadyExistsException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.exceptions.IdentityNotFoundException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.interfaces.CryptoBrokerIdentity;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.interfaces.CryptoBrokerIdentityManager;
import com.bitdubai.fermat_cbp_plugin.layer.identity.crypto_broker.developer.bitdubai.version_1.database.CryptoBrokerIdentityDatabaseDao;
import com.bitdubai.fermat_cbp_plugin.layer.identity.crypto_broker.developer.bitdubai.version_1.database.CryptoBrokerIdentityDeveloperDatabaseFactory;
import com.bitdubai.fermat_cbp_plugin.layer.identity.crypto_broker.developer.bitdubai.version_1.exceptions.CantChangeExposureLevelException;
import com.bitdubai.fermat_cbp_plugin.layer.identity.crypto_broker.developer.bitdubai.version_1.exceptions.CantExposeActorIdentitiesException;
import com.bitdubai.fermat_cbp_plugin.layer.identity.crypto_broker.developer.bitdubai.version_1.exceptions.CantExposeActorIdentityException;
import com.bitdubai.fermat_cbp_plugin.layer.identity.crypto_broker.developer.bitdubai.version_1.exceptions.CantGetIdentityException;
import com.bitdubai.fermat_cbp_plugin.layer.identity.crypto_broker.developer.bitdubai.version_1.exceptions.CantInitializeCryptoBrokerIdentityDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.identity.crypto_broker.developer.bitdubai.version_1.structure.CryptoBrokerIdentityImpl;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.enums.ProfileStatus;
import com.bitdubai.fermat_pip_api.layer.user.device_user.exceptions.CantGetLoggedInDeviceUserException;
import com.bitdubai.fermat_pip_api.layer.user.device_user.interfaces.DeviceUser;
import com.bitdubai.fermat_pip_api.layer.user.device_user.interfaces.DeviceUserManager;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by jorge on 28-09-2015.
 * Modified by Yordin Alayn 10.09.15
 * Updated by lnacosta (laion.cj91@gmail.com) on 24/11/2015.
 */
@PluginInfo(createdBy = "yalayn", maintainerMail = "y.alayn@gmail.com", platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.IDENTITY, plugin = Plugins.CRYPTO_BROKER_IDENTITY)
public class CryptoBrokerIdentityPluginRoot extends AbstractPlugin implements CryptoBrokerIdentityManager, DatabaseManagerForDevelopers {

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.USER, addon = Addons.DEVICE_USER)
    DeviceUserManager deviceUserManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    PluginFileSystem pluginFileSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.DEVICE_LOCATION)
    LocationManager locationManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.ACTOR_NETWORK_SERVICE, plugin = Plugins.CRYPTO_BROKER)
    CryptoBrokerManager cryptoBrokerANSManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_BROADCASTER_SYSTEM)
    Broadcaster broadcaster;

    public static final String CRYPTO_BROKER_IDENTITY_PROFILE_IMAGE_FILE_NAME = "cryptoBrokerIdentityProfileImage";
    public static final String CRYPTO_BROKER_IDENTITY_PRIVATE_KEYS_FILE_NAME = "cryptoBrokerIdentityPrivateKey";

    private CryptoBrokerIdentityDatabaseDao cryptoBrokerIdentityDatabaseDao;


    public CryptoBrokerIdentityPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    /*CryptoBrokerIdentityManager Interface implementation.*/
    public final List<CryptoBrokerIdentity> listIdentitiesFromCurrentDeviceUser() throws CantListCryptoBrokerIdentitiesException {
        try {
            DeviceUser loggedUser = deviceUserManager.getLoggedInDeviceUser();
            return cryptoBrokerIdentityDatabaseDao.listIdentitiesFromDeviceUser(loggedUser);
        } catch (CantGetLoggedInDeviceUserException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListCryptoBrokerIdentitiesException("CAN'T GET CRYPTO BROKER IDENTITIES", e, "Error get logged user device", "");
        } catch (com.bitdubai.fermat_cbp_plugin.layer.identity.crypto_broker.developer.bitdubai.version_1.exceptions.CantListCryptoBrokerIdentitiesException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListCryptoBrokerIdentitiesException("CAN'T GET CRYPTO BROKER IDENTITIES", e, "", "");
        } catch (Exception e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListCryptoBrokerIdentitiesException("CAN'T GET CRYPTO BROKER IDENTITIES", FermatException.wrapException(e), "", "");
        }
    }

    public final CryptoBrokerIdentity createCryptoBrokerIdentity(String alias, byte[] image, long accuracy, GeoFrequency frecuency)
            throws CantCreateCryptoBrokerIdentityException, CryptoBrokerIdentityAlreadyExistsException {

        try {
            DeviceUser loggedUser = deviceUserManager.getLoggedInDeviceUser();
            KeyPair keyPair = new ECCKeyPair();

            CryptoBrokerIdentity cryptoBroker = new CryptoBrokerIdentityImpl(alias, keyPair, image, ExposureLevel.HIDE, accuracy, frecuency/*,cryptoBrokerIdentityExtraData*/);
            cryptoBrokerIdentityDatabaseDao.createNewCryptoBrokerIdentity(cryptoBroker, keyPair.getPrivateKey(), loggedUser);

            FermatBundle fermatBundle = new FermatBundle();
            fermatBundle.put(Broadcaster.PUBLISH_ID, SubAppsPublicKeys.CBP_BROKER_IDENTITY.getCode());
            fermatBundle.put(Broadcaster.NOTIFICATION_TYPE, CBPBroadcasterConstants.CBI_BROKER_IDENTITY_CREATED);
            broadcaster.publish(BroadcasterType.UPDATE_VIEW, fermatBundle);

            return cryptoBroker;
        } catch (CantGetLoggedInDeviceUserException e) {

            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantCreateCryptoBrokerIdentityException("CAN'T CREATE NEW CRYPTO BROKER IDENTITY", e, "Error getting current logged in device user", "");
        } catch (CantCreateNewDeveloperException e) {

            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantCreateCryptoBrokerIdentityException("CAN'T CREATE NEW CRYPTO BROKER IDENTITY", e, "Error save user on database", "");
        } catch (Exception e) {

            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantCreateCryptoBrokerIdentityException("CAN'T CREATE NEW CRYPTO BROKER IDENTITY", FermatException.wrapException(e), "", "");
        }

    }

    @Override
    public void updateCryptoBrokerIdentity(String alias, String publicKey, byte[] imageProfile, long accuracy, GeoFrequency frequency) throws CantUpdateBrokerIdentityException {
        this.cryptoBrokerIdentityDatabaseDao.updateCryptoBrokerIdentity(alias, publicKey, imageProfile, accuracy, frequency);

        try {
            publishUpdatedIdentity(publicKey);
        } catch (CantGetIdentityException e) {

            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantUpdateBrokerIdentityException("CAN'T GET CRYPTO BROKER IDENTITY", FermatException.wrapException(e), "", "");
        } catch (IdentityNotFoundException e) {

            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantUpdateBrokerIdentityException("CRYPTO BROKER IDENTITY NOT FOUND", FermatException.wrapException(e), "", "");
        } catch (CantExposeIdentityException e) {

            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantUpdateBrokerIdentityException("CAN'T EXPOSE CRYPTO BROKER IDENTITY", FermatException.wrapException(e), "", "");
        } catch (CantGetDeviceLocationException e) {
            throw new CantUpdateBrokerIdentityException(e, "", "Problem exposing identities in Location.");
        }
    }

    /**
     * This method publish an updated identity.
     *
     * @param cryptoBrokerIdentityPublicKey the broker identity public key
     * @throws CantGetDeviceLocationException
     * @throws IdentityNotFoundException
     * @throws CantGetIdentityException
     * @throws CantExposeIdentityException
     */
    private void publishUpdatedIdentity(String cryptoBrokerIdentityPublicKey) throws CantGetDeviceLocationException, IdentityNotFoundException, CantGetIdentityException, CantExposeIdentityException {
        Location location = locationManager.getLocation();
        CryptoBrokerIdentity cryptoBrokerIdentity = cryptoBrokerIdentityDatabaseDao.getIdentity(
                cryptoBrokerIdentityPublicKey);
        if (cryptoBrokerIdentity.isPublished()) {
            cryptoBrokerANSManager.updateIdentity(
                    new CryptoBrokerExposingData(
                            cryptoBrokerIdentity,
                            location,
                            ProfileStatus.UNKNOWN));

            FermatBundle fermatBundle = new FermatBundle();
            fermatBundle.put(Broadcaster.PUBLISH_ID, SubAppsPublicKeys.CBP_BROKER_IDENTITY.getCode());
            fermatBundle.put(Broadcaster.NOTIFICATION_TYPE, CBPBroadcasterConstants.CBI_BROKER_IDENTITY_EDITED);
            broadcaster.publish(BroadcasterType.UPDATE_VIEW, fermatBundle);
        }
    }

    /**
     * This method updates a crypto broker identity
     *
     * @param cryptoBrokerIdentity the broker identity
     * @throws CantUpdateBrokerIdentityException
     */
    public void updateCryptoBrokerIdentity(CryptoBrokerIdentity cryptoBrokerIdentity)
            throws CantUpdateBrokerIdentityException {
        this.cryptoBrokerIdentityDatabaseDao.updateCryptoBrokerIdentity(cryptoBrokerIdentity);
        try {
            publishUpdatedIdentity(cryptoBrokerIdentity.getPublicKey());
        } catch (CantGetIdentityException e) {

            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantUpdateBrokerIdentityException("CAN'T GET CRYPTO BROKER IDENTITY", FermatException.wrapException(e), "", "");
        } catch (IdentityNotFoundException e) {

            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantUpdateBrokerIdentityException("CRYPTO BROKER IDENTITY NOT FOUND", FermatException.wrapException(e), "", "");
        } catch (CantExposeIdentityException e) {

            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantUpdateBrokerIdentityException("CAN'T EXPOSE CRYPTO BROKER IDENTITY", FermatException.wrapException(e), "", "");
        } catch (CantGetDeviceLocationException e) {
            throw new CantUpdateBrokerIdentityException(e, "", "Problem exposing identities in Location.");
        }
    }

    @Override
    public CryptoBrokerIdentity getCryptoBrokerIdentity(final String publicKey) throws CantGetCryptoBrokerIdentityException, IdentityNotFoundException {
        try {
            return cryptoBrokerIdentityDatabaseDao.getIdentity(publicKey);
        } catch (final IdentityNotFoundException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (final CantGetIdentityException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetCryptoBrokerIdentityException(e, "", "There was a problem trying to get the identity.");
        } catch (final Exception e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetCryptoBrokerIdentityException(e, "", "Unhandled Exception.");
        }
    }

    @Override
    public void publishIdentity(String publicKey) throws CantPublishIdentityException, IdentityNotFoundException {
        try {
            cryptoBrokerIdentityDatabaseDao.changeExposureLevel(publicKey, ExposureLevel.PUBLISH);
            exposeIdentity(cryptoBrokerIdentityDatabaseDao.getIdentity(publicKey));

            FermatBundle fermatBundle = new FermatBundle();
            fermatBundle.put(Broadcaster.PUBLISH_ID, SubAppsPublicKeys.CBP_BROKER_IDENTITY.getCode());
            fermatBundle.put(Broadcaster.NOTIFICATION_TYPE, CBPBroadcasterConstants.CBI_BROKER_IDENTITY_EDITED);
            broadcaster.publish(BroadcasterType.UPDATE_VIEW, fermatBundle);

        } catch (final CantExposeActorIdentityException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantPublishIdentityException(e, "", "Cant expose actor identity.");
        } catch (final CantChangeExposureLevelException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantPublishIdentityException(e, "", "There was a problem trying to change the exposure level.");
        } catch (final IdentityNotFoundException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (final CantGetIdentityException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantPublishIdentityException(e, "", "There was a problem trying to get the identity.");
        } catch (final Exception e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantPublishIdentityException(e, "", "Unhandled Exception.");
        }
    }

    @Override
    public void unHideIdentity(String publicKey) throws CantUnHideIdentityException, IdentityNotFoundException {
        try {
            cryptoBrokerIdentityDatabaseDao.changeExposureLevel(publicKey, ExposureLevel.PUBLISH);
        } catch (final CantChangeExposureLevelException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantUnHideIdentityException(e, "", "There was a problem trying to change the exposure level.");
        } catch (final IdentityNotFoundException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (final Exception e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantUnHideIdentityException(e, "", "Unhandled Exception.");
        }

    }

    @Override
    public void hideIdentity(String publicKey) throws CantHideIdentityException, IdentityNotFoundException {
        try {
            cryptoBrokerIdentityDatabaseDao.changeExposureLevel(publicKey, ExposureLevel.HIDE);
        } catch (final CantChangeExposureLevelException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantHideIdentityException(e, "", "There was a problem trying to change the exposure level.");
        } catch (final IdentityNotFoundException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (final Exception e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantHideIdentityException(e, "", "Unhandled Exception.");
        }
    }

    @Override
    public final void start() throws CantStartPluginException {
        try {
            this.cryptoBrokerIdentityDatabaseDao = new CryptoBrokerIdentityDatabaseDao(pluginDatabaseSystem, this.pluginFileSystem, this.pluginId);
            this.cryptoBrokerIdentityDatabaseDao.initialize();
        } catch (CantInitializeCryptoBrokerIdentityDatabaseException cantInitializeExtraUserRegistryException) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantInitializeExtraUserRegistryException);
            throw new CantStartPluginException(cantInitializeExtraUserRegistryException, this.getPluginVersionReference());
        }
        try {
            exposeIdentities();
        } catch (final CantExposeActorIdentitiesException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
        }
        this.serviceStatus = ServiceStatus.STARTED;
    }

    private void exposeIdentities() throws CantExposeActorIdentitiesException {
        try {
            Location location = locationManager.getLocation();

            final List<CryptoBrokerExposingData> cryptoBrokerExposingDataList = new ArrayList<>();
            for (final CryptoBrokerIdentity identity : listIdentitiesFromCurrentDeviceUser()) {
                int refreshInterval = identity.getFrequency().getRefreshInterval();
                if (identity.isPublished()) {
                    cryptoBrokerExposingDataList.add(
                            new CryptoBrokerExposingData(
                                    identity.getPublicKey(),
                                    identity.getAlias(),
                                    identity.getProfileImage(),
                                    location,
                                    refreshInterval,
                                    identity.getAccuracy(),
                                    ProfileStatus.UNKNOWN
                            )
                    );
                }
            }
            cryptoBrokerANSManager.exposeIdentities(cryptoBrokerExposingDataList);
        } catch (final CantListCryptoBrokerIdentitiesException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantExposeActorIdentitiesException(e, "", "Problem trying to list crypto brokers from current device user.");
        } catch (final CantExposeIdentitiesException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantExposeActorIdentitiesException(e, "", "Problem exposing identities.");
        } catch (CantGetDeviceLocationException e) {
            throw new CantExposeActorIdentitiesException(e, "", "Problem exposing identities in Location.");
        }
    }

    private void exposeIdentity(final CryptoBrokerIdentity identity) throws CantExposeActorIdentityException {
        try {
            Location location = locationManager.getLocation();
            //long refreshInterval = 0;
            //refreshInterval = identity.getFrequency().getRefreshInterval();
            cryptoBrokerANSManager.exposeIdentity(new CryptoBrokerExposingData(identity, location, ProfileStatus.UNKNOWN));
        } catch (final CantExposeIdentityException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantExposeActorIdentityException(e, "", "Problem exposing identity.");
        } catch (CantGetDeviceLocationException e) {
            throw new CantExposeActorIdentityException(e, "", "Problem exposing identities in Location.");
        }
    }

    /*DatabaseManagerForDevelopers Interface implementation.*/
    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        CryptoBrokerIdentityDeveloperDatabaseFactory dbFactory = new CryptoBrokerIdentityDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
        return dbFactory.getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        CryptoBrokerIdentityDeveloperDatabaseFactory dbFactory = new CryptoBrokerIdentityDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
        return dbFactory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        try {
            CryptoBrokerIdentityDeveloperDatabaseFactory dbFactory = new CryptoBrokerIdentityDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
            dbFactory.initializeDatabase();
            return dbFactory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
        } catch (CantInitializeCryptoBrokerIdentityDatabaseException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
        return new ArrayList<>();
    }
}
