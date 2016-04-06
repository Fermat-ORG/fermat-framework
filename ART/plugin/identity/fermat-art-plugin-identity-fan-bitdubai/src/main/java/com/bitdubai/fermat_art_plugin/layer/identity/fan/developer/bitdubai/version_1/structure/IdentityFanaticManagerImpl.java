package com.bitdubai.fermat_art_plugin.layer.identity.fan.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_art_api.all_definition.exceptions.CantPublishIdentityException;
import com.bitdubai.fermat_art_api.all_definition.exceptions.IdentityNotFoundException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantExposeIdentityException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.fan.FanManager;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.fan.util.FanExposingData;
import com.bitdubai.fermat_art_api.layer.identity.fan.exceptions.CantCreateFanIdentityException;
import com.bitdubai.fermat_art_api.layer.identity.fan.exceptions.CantGetFanIdentityException;
import com.bitdubai.fermat_art_api.layer.identity.fan.exceptions.CantListFanIdentitiesException;
import com.bitdubai.fermat_art_api.layer.identity.fan.exceptions.CantUpdateFanIdentityException;
import com.bitdubai.fermat_art_api.layer.identity.fan.interfaces.Fanatic;
import com.bitdubai.fermat_art_plugin.layer.identity.fan.developer.bitdubai.version_1.database.FanaticIdentityDao;
import com.bitdubai.fermat_art_plugin.layer.identity.fan.developer.bitdubai.version_1.exceptions.CantInitializeFanaticIdentityDatabaseException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.user.device_user.exceptions.CantGetLoggedInDeviceUserException;
import com.bitdubai.fermat_pip_api.layer.user.device_user.interfaces.DeviceUser;
import com.bitdubai.fermat_pip_api.layer.user.device_user.interfaces.DeviceUserManager;

import java.util.List;
import java.util.UUID;

/**
 * Created by Gabriel Araujo 10/03/16.
 */
public class IdentityFanaticManagerImpl implements DealsWithErrors, DealsWithLogger, DealsWithPluginDatabaseSystem, DealsWithPluginFileSystem {
    /**
     * IdentityAssetIssuerManagerImpl member variables
     */
    UUID pluginId;

    /**
     * DealsWithErrors interface member variables
     */
    ErrorManager errorManager;

    /**
     * DealsWithLogger interface mmeber variables
     */
    LogManager logManager;

    /**
     * DealsWithPluginDatabaseSystem interface member variables
     */
    PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * DealsWithPluginFileSystem interface member variables
     */
    PluginFileSystem pluginFileSystem;


    /**
     * DealsWithDeviceUsers Interface member variables.
     */
    private DeviceUserManager deviceUserManager;

    private FanManager fanManager;

    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    @Override
    public void setLogManager(LogManager logManager) {
        this.logManager = logManager;
    }

    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
        this.pluginFileSystem = pluginFileSystem;
    }

    /**
     * Constructor
     *
     * @param errorManager
     * @param logManager
     * @param pluginDatabaseSystem
     * @param pluginFileSystem
     */
    public IdentityFanaticManagerImpl(ErrorManager errorManager, LogManager logManager, PluginDatabaseSystem pluginDatabaseSystem, PluginFileSystem pluginFileSystem, UUID pluginId, DeviceUserManager deviceUserManager, FanManager fanManager){
        this.errorManager = errorManager;
        this.logManager = logManager;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginFileSystem = pluginFileSystem;
        this.pluginId = pluginId;
        this.deviceUserManager = deviceUserManager;
        this.fanManager = fanManager;
    }

    private FanaticIdentityDao getFanaticIdentityDao() throws CantInitializeFanaticIdentityDatabaseException {
        return new FanaticIdentityDao(this.pluginDatabaseSystem, this.pluginFileSystem, this.pluginId);
    }

    public List<Fanatic> getIdentityArtistFromCurrentDeviceUser() throws CantListFanIdentitiesException {

        try {

            List<Fanatic> artists;


            DeviceUser loggedUser = deviceUserManager.getLoggedInDeviceUser();
            artists = getFanaticIdentityDao().getIdentityFanaticsFromCurrentDeviceUser(loggedUser);


            return artists;

        } catch (CantGetLoggedInDeviceUserException e) {
            throw new CantListFanIdentitiesException("CAN'T GET ASSET NEW Fanatic IDENTITIES", e, "Error get logged user device", "");
        } catch (Exception e) {
            throw new CantListFanIdentitiesException("CAN'T GET ASSET NEW Fanatic IDENTITIES", FermatException.wrapException(e), "", "");
        }
    }

    public Fanatic getIdentitFanatic() throws CantGetFanIdentityException {
        Fanatic Fanatic = null;
        try {
            Fanatic = getFanaticIdentityDao().getIdentityFanatic();
        } catch (CantInitializeFanaticIdentityDatabaseException e) {
            e.printStackTrace();
        }
        return Fanatic;
    }
    public Fanatic getIdentitFanatic(String publicKey) throws CantGetFanIdentityException {
        Fanatic Fanatic = null;
        try {
            Fanatic = getFanaticIdentityDao().getIdentityFanatic(publicKey);
        } catch (CantInitializeFanaticIdentityDatabaseException e) {
            e.printStackTrace();
        }
        return Fanatic;
    }
    public Fanatic createNewIdentityArtist(String alias, byte[] profileImage, UUID externalIdentityID) throws CantCreateFanIdentityException {
        try {
            DeviceUser loggedUser = deviceUserManager.getLoggedInDeviceUser();

            ECCKeyPair keyPair = new ECCKeyPair();
            String publicKey = keyPair.getPublicKey();
            String privateKey = keyPair.getPrivateKey();

            getFanaticIdentityDao().createNewUser(alias, publicKey, privateKey, loggedUser, profileImage, externalIdentityID);

            return new FanaticIdentityImp(alias, publicKey, profileImage,externalIdentityID, pluginFileSystem, pluginId);
        } catch (CantGetLoggedInDeviceUserException e) {
            throw new CantCreateFanIdentityException("CAN'T CREATE NEW Fanatic IDENTITY", e, "Error getting current logged in device user", "");
        } catch (Exception e) {
            throw new CantCreateFanIdentityException("CAN'T CREATE NEW Fanatic IDENTITY", FermatException.wrapException(e), "", "");
        }
    }

    public void updateIdentityArtist(String alias,String publicKey, byte[] profileImage, UUID externalIdentityID) throws CantUpdateFanIdentityException {
        try {
            getFanaticIdentityDao().updateIdentityFanaticUser(publicKey, alias, profileImage, externalIdentityID);

        } catch (CantInitializeFanaticIdentityDatabaseException e) {
            e.printStackTrace();
        }
    }

    public void registerIdentitiesANS(String publicKey) throws CantPublishIdentityException, IdentityNotFoundException {
        try {
            Fanatic Fanatic = getIdentitFanatic(publicKey);
            FanExposingData fanExposingData = new FanExposingData(Fanatic.getPublicKey(),Fanatic.getAlias(),Fanatic.getProfileImage());
            fanManager.exposeIdentity(fanExposingData);
        } catch (CantGetFanIdentityException | CantExposeIdentityException e) {
            e.printStackTrace();
        }
    }


}
