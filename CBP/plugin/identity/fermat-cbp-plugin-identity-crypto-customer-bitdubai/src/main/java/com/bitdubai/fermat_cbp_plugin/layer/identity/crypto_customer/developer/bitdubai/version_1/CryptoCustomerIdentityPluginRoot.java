package com.bitdubai.fermat_cbp_plugin.layer.identity.crypto_customer.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmetricCryptography;
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
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.core.PluginInfo;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.BroadcasterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_api.layer.osa_android.location_system.LocationManager;
import com.bitdubai.fermat_api.layer.osa_android.location_system.exceptions.CantGetDeviceLocationException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantCreateNewDeveloperException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_customer.exceptions.CantExposeIdentitiesException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_customer.exceptions.CantExposeIdentityException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_customer.interfaces.CryptoCustomerManager;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_customer.utils.CryptoCustomerExposingData;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.exceptions.CantUpdateCustomerIdentityException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.exceptions.CantCreateCryptoCustomerIdentityException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.exceptions.CantGetCryptoCustomerIdentityException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.exceptions.CantHideIdentityException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.exceptions.CantListCryptoCustomerIdentityException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.exceptions.CantPublishIdentityException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.exceptions.IdentityNotFoundException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.interfaces.CryptoCustomerIdentity;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.interfaces.CryptoCustomerIdentityManager;
import com.bitdubai.fermat_cbp_plugin.layer.identity.crypto_customer.developer.bitdubai.version_1.database.CryptoCustomerIdentityDatabaseDao;
import com.bitdubai.fermat_cbp_plugin.layer.identity.crypto_customer.developer.bitdubai.version_1.database.CryptoCustomerIdentityDeveloperDatabaseFactory;
import com.bitdubai.fermat_cbp_plugin.layer.identity.crypto_customer.developer.bitdubai.version_1.exceptions.CantChangeExposureLevelException;
import com.bitdubai.fermat_cbp_plugin.layer.identity.crypto_customer.developer.bitdubai.version_1.exceptions.CantExposeActorIdentitiesException;
import com.bitdubai.fermat_cbp_plugin.layer.identity.crypto_customer.developer.bitdubai.version_1.exceptions.CantExposeActorIdentityException;
import com.bitdubai.fermat_cbp_plugin.layer.identity.crypto_customer.developer.bitdubai.version_1.exceptions.CantGetIdentityException;
import com.bitdubai.fermat_cbp_plugin.layer.identity.crypto_customer.developer.bitdubai.version_1.exceptions.CantInitializeCryptoCustomerIdentityDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.identity.crypto_customer.developer.bitdubai.version_1.exceptions.CantListCryptoCustomerIdentitiesException;
import com.bitdubai.fermat_cbp_plugin.layer.identity.crypto_customer.developer.bitdubai.version_1.structure.CryptoCustomerIdentityImpl;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.enums.ProfileStatus;
import com.bitdubai.fermat_pip_api.layer.user.device_user.exceptions.CantGetLoggedInDeviceUserException;
import com.bitdubai.fermat_pip_api.layer.user.device_user.interfaces.DeviceUser;
import com.bitdubai.fermat_pip_api.layer.user.device_user.interfaces.DeviceUserManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jorge on 28-09-2015.
 * Modified by Yordin Alayn 10.09.15
 */
@PluginInfo(createdBy = "yalayn", maintainerMail = "y.alayn@gmail.com", platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.IDENTITY, plugin = Plugins.CRYPTO_CUSTOMER_IDENTITY)
public class CryptoCustomerIdentityPluginRoot extends AbstractPlugin implements CryptoCustomerIdentityManager, DatabaseManagerForDevelopers {

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.USER, addon = Addons.DEVICE_USER)
    DeviceUserManager deviceUserManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.DEVICE_LOCATION)
    LocationManager locationManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    PluginFileSystem pluginFileSystem;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.ACTOR_NETWORK_SERVICE, plugin = Plugins.CRYPTO_CUSTOMER)
    CryptoCustomerManager cryptoCustomerANSManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_BROADCASTER_SYSTEM)
    Broadcaster broadcaster;


    public CryptoCustomerIdentityPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    /*Variables.*/
    private CryptoCustomerIdentityDatabaseDao cryptoCustomerIdentityDatabaseDao;

    public static final String CRYPTO_CUSTOMER_IDENTITY_PROFILE_IMAGE_FILE_NAME = "cryptoCustomerIdentityProfileImage";

    public static final String CRYPTO_CUSTOMER_IDENTITY_PRIVATE_KEYS_FILE_NAME = "cryptoCustomerIdentityPrivateKey";

    /*CryptoCustomerIdentityManager Interface implementation.*/
    public List<CryptoCustomerIdentity> listAllCryptoCustomerFromCurrentDeviceUser() throws CantListCryptoCustomerIdentityException {
        try {
            List<CryptoCustomerIdentity> cryptoCustomerIdentityList1;
            DeviceUser loggedUser = deviceUserManager.getLoggedInDeviceUser();
            cryptoCustomerIdentityList1 = cryptoCustomerIdentityDatabaseDao.getAllCryptoCustomerIdentitiesFromCurrentDeviceUser(loggedUser);
            return cryptoCustomerIdentityList1;
        } catch (CantGetLoggedInDeviceUserException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListCryptoCustomerIdentityException("CAN'T GET CRYPTO CUSTOMER IDENTITIES", e, "Error get logged user device", "");
        } catch (CantListCryptoCustomerIdentitiesException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListCryptoCustomerIdentityException("CAN'T GET CRYPTO CUSTOMER IDENTITIES", e, "", "");
        } catch (Exception e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListCryptoCustomerIdentityException("CAN'T GET CRYPTO CUSTOMER IDENTITIES", FermatException.wrapException(e), "", "");
        }
    }

    public CryptoCustomerIdentity getCryptoCustomerIdentity(String publickey) throws CantGetCryptoCustomerIdentityException {
        try {
            return cryptoCustomerIdentityDatabaseDao.getIdentity(publickey);
        } catch (CantGetIdentityException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetCryptoCustomerIdentityException("CAN'T GET CRYPTO CUSTOMER IDENTITIES", e, "Error get Identity", "");
        } catch (IdentityNotFoundException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetCryptoCustomerIdentityException("CAN'T GET CRYPTO CUSTOMER IDENTITIES", e, "", "");
        } catch (Exception e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetCryptoCustomerIdentityException("CAN'T GET CRYPTO CUSTOMER IDENTITIES", FermatException.wrapException(e), "", "");
        }
    }

    public CryptoCustomerIdentity createCryptoCustomerIdentity(String alias, byte[] profileImage,
                                                               long accuracy,
                                                               GeoFrequency frequency) throws CantCreateCryptoCustomerIdentityException {
        try {
            DeviceUser loggedUser = deviceUserManager.getLoggedInDeviceUser();
            KeyPair keyPair = AsymmetricCryptography.generateECCKeyPair();
            // TODO BY DEFAULT THE CUSTOMER IS PUBLISHED
            CryptoCustomerIdentity cryptoCustomer = new CryptoCustomerIdentityImpl(alias, keyPair.getPrivateKey(), keyPair.getPublicKey(), profileImage, true, accuracy, frequency);
            cryptoCustomerIdentityDatabaseDao.createNewCryptoCustomerIdentity(cryptoCustomer, keyPair.getPrivateKey(), loggedUser, accuracy, frequency);
            //We will expose this identity
//            exposeIdentity(cryptoCustomer);
            broadcaster.publish(BroadcasterType.UPDATE_VIEW, "cambios_en_el_identity_customer_creado");

            return cryptoCustomer;

        } catch (CantGetLoggedInDeviceUserException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantCreateCryptoCustomerIdentityException("CAN'T CREATE NEW CRYPTO CUSTOMER IDENTITY", e, "Error getting current logged in device user", "");
        } catch (CantCreateNewDeveloperException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantCreateCryptoCustomerIdentityException("CAN'T CREATE NEW CRYPTO CUSTOMER IDENTITY", e, "Error save user on database", "");
        } catch (Exception e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantCreateCryptoCustomerIdentityException("CAN'T CREATE NEW CRYPTO CUSTOMER IDENTITY", FermatException.wrapException(e), "", "");
        }

    }

    @Override
    public void updateCryptoCustomerIdentity(String alias, String publicKey, byte[] imageProfile, long accuracy, GeoFrequency frequency) throws CantUpdateCustomerIdentityException {
        cryptoCustomerIdentityDatabaseDao.updateCryptoCustomerIdentity(alias, publicKey, imageProfile, accuracy, frequency);

        try {
            CryptoCustomerIdentity customer = cryptoCustomerIdentityDatabaseDao.getIdentity(publicKey);
            Location location = locationManager.getLocation();
            long refreshInterval = customer.getFrequency().getRefreshInterval();
            if (customer.isPublished()) {
                cryptoCustomerANSManager.updateIdentity(new CryptoCustomerExposingData(publicKey, alias, imageProfile, location, refreshInterval, customer.getAccuracy(), ProfileStatus.UNKNOWN));
            }
        } catch (CantGetIdentityException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantUpdateCustomerIdentityException("CAN'T GET CRYPTO CUSTOMER IDENTITY", FermatException.wrapException(e), "", "");

        } catch (IdentityNotFoundException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantUpdateCustomerIdentityException("CRYPTO CUSTOMER IDENTITY NOT FOUND", FermatException.wrapException(e), "", "");

        } catch (CantExposeIdentityException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantUpdateCustomerIdentityException("CAN'T EXPOSE CRYPTO CUSTOMER IDENTITY", FermatException.wrapException(e), "", "");

        } catch (CantGetDeviceLocationException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantUpdateCustomerIdentityException("CAN'T GET THE DEVICE LOCATION", FermatException.wrapException(e), "", "");
        }
    }

    @Override
    public void publishIdentity(String publicKey) throws CantPublishIdentityException, IdentityNotFoundException {

        try {

            cryptoCustomerIdentityDatabaseDao.changeExposureLevel(publicKey, true);

            exposeIdentity(cryptoCustomerIdentityDatabaseDao.getIdentity(publicKey));

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
    public void hideIdentity(String publicKey) throws CantHideIdentityException, IdentityNotFoundException {

        try {

            cryptoCustomerIdentityDatabaseDao.changeExposureLevel(publicKey, false);

            // TODO actor network service hide identity?

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

    private void exposeIdentities() throws CantExposeActorIdentitiesException {

        try {
            Location location = locationManager.getLocation();

            final List<CryptoCustomerExposingData> cryptoBrokerExposingDataList = new ArrayList<>();

            for (final CryptoCustomerIdentity identity : listAllCryptoCustomerFromCurrentDeviceUser()) {

                long refreshInterval = identity.getFrequency().getRefreshInterval();
                if (identity.isPublished()) {
                    cryptoBrokerExposingDataList.add(
                            new CryptoCustomerExposingData(
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

            cryptoCustomerANSManager.exposeIdentities(cryptoBrokerExposingDataList);

        } catch (final CantListCryptoCustomerIdentityException e) {

            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantExposeActorIdentitiesException(e, "", "Problem trying to list crypto brokers from current device user.");
        } catch (final CantExposeIdentitiesException e) {

            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantExposeActorIdentitiesException(e, "", "Problem exposing identities.");
        } catch (CantGetDeviceLocationException e) {
            throw new CantExposeActorIdentitiesException(e, "", "Problem exposing identities in Location.");
        }
    }

    private void exposeIdentity(final CryptoCustomerIdentity identity) throws CantExposeActorIdentityException {

        try {
            Location location = locationManager.getLocation();
            long refreshInterval = identity.getFrequency().getRefreshInterval();
            cryptoCustomerANSManager.exposeIdentity(new CryptoCustomerExposingData(identity.getPublicKey(), identity.getAlias(), identity.getProfileImage(), location, refreshInterval, identity.getAccuracy(), ProfileStatus.UNKNOWN));

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
        CryptoCustomerIdentityDeveloperDatabaseFactory dbFactory = new CryptoCustomerIdentityDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
        return dbFactory.getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        CryptoCustomerIdentityDeveloperDatabaseFactory dbFactory = new CryptoCustomerIdentityDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
        return dbFactory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        try {
            CryptoCustomerIdentityDeveloperDatabaseFactory dbFactory = new CryptoCustomerIdentityDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
            dbFactory.initializeDatabase();
            return dbFactory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
        } catch (CantInitializeCryptoCustomerIdentityDatabaseException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
        return new ArrayList<>();
    }

    /*Service Interface implementation.*/
    @Override
    public void start() throws CantStartPluginException {

        try {

            this.cryptoCustomerIdentityDatabaseDao = new CryptoCustomerIdentityDatabaseDao(pluginDatabaseSystem, this.pluginFileSystem, this.pluginId);
            this.cryptoCustomerIdentityDatabaseDao.initialize();

        } catch (CantInitializeCryptoCustomerIdentityDatabaseException cantInitializeExtraUserRegistryException) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantInitializeExtraUserRegistryException);
            throw new CantStartPluginException(cantInitializeExtraUserRegistryException, this.getPluginVersionReference());
        }

        try {

            /*cryptoCustomerIdentityManagerImpl = new CryptoCustomerIdentityManagerImpl(
                    cryptoCustomerIdentityDatabaseDao,
                    deviceUserManager,
                    broadcaster,
                    cryptoCustomerANSManager,
                    this
            );*/

            exposeIdentities();

        } catch (final CantExposeActorIdentitiesException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException(e, this.getPluginVersionReference());
        }

        this.serviceStatus = ServiceStatus.STARTED;
    }
}
