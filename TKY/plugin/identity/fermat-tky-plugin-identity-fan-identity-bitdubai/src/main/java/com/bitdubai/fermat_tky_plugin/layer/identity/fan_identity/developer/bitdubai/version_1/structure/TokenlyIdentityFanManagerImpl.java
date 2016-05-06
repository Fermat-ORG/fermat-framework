package com.bitdubai.fermat_tky_plugin.layer.identity.fan_identity.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.user.device_user.exceptions.CantGetLoggedInDeviceUserException;
import com.bitdubai.fermat_pip_api.layer.user.device_user.interfaces.DeviceUser;
import com.bitdubai.fermat_pip_api.layer.user.device_user.interfaces.DeviceUserManager;
import com.bitdubai.fermat_tky_api.all_definitions.enums.ExternalPlatform;
import com.bitdubai.fermat_tky_api.all_definitions.interfaces.User;
import com.bitdubai.fermat_tky_api.layer.external_api.interfaces.TokenlyApiManager;
import com.bitdubai.fermat_tky_api.layer.identity.fan.exceptions.CantCreateFanIdentityException;
import com.bitdubai.fermat_tky_api.layer.identity.fan.exceptions.CantGetFanIdentityException;
import com.bitdubai.fermat_tky_api.layer.identity.fan.exceptions.CantListFanIdentitiesException;
import com.bitdubai.fermat_tky_api.layer.identity.fan.exceptions.CantUpdateFanIdentityException;
import com.bitdubai.fermat_tky_api.layer.identity.fan.interfaces.Fan;
import com.bitdubai.fermat_tky_plugin.layer.identity.fan_identity.developer.bitdubai.version_1.database.TokenlyFanIdentityDao;
import com.bitdubai.fermat_tky_plugin.layer.identity.fan_identity.developer.bitdubai.version_1.exceptions.CantInitializeTokenlyFanIdentityDatabaseException;

import java.util.List;
import java.util.UUID;

/**
 * Created by Gabriel Araujo 10/03/16.
 */
public class TokenlyIdentityFanManagerImpl implements DealsWithErrors, DealsWithLogger, DealsWithPluginDatabaseSystem, DealsWithPluginFileSystem {
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

    private TokenlyApiManager tokenlyApiManager;

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
    public TokenlyIdentityFanManagerImpl(ErrorManager errorManager, LogManager logManager, PluginDatabaseSystem pluginDatabaseSystem, PluginFileSystem pluginFileSystem, UUID pluginId, DeviceUserManager deviceUserManager, TokenlyApiManager tokenlyApiManager){
        this.errorManager = errorManager;
        this.logManager = logManager;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginFileSystem = pluginFileSystem;
        this.pluginId = pluginId;
        this.deviceUserManager = deviceUserManager;
        this.tokenlyApiManager = tokenlyApiManager;
    }

    private TokenlyFanIdentityDao getFanIdentityDao() throws CantInitializeTokenlyFanIdentityDatabaseException {
        return new TokenlyFanIdentityDao(this.pluginDatabaseSystem, this.pluginFileSystem, this.pluginId);
    }

    /**
     * This method returns the Fan identity logged in current device user
     * @return
     * @throws CantListFanIdentitiesException
     */
    public List<Fan> getIdentityFanFromCurrentDeviceUser() throws CantListFanIdentitiesException {

        try {

            List<Fan> fans;


            DeviceUser loggedUser = deviceUserManager.getLoggedInDeviceUser();
            fans = getFanIdentityDao().getIdentityFansFromCurrentDeviceUser(loggedUser);


            return fans;

        } catch (CantGetLoggedInDeviceUserException e) {
            throw new CantListFanIdentitiesException("CAN'T GET NEW ARTIST IDENTITIES", e, "Error get logged user device", "");
        } catch (Exception e) {
            throw new CantListFanIdentitiesException("CAN'T GET NEW ARTIST IDENTITIES", FermatException.wrapException(e), "", "");
        }
    }

    public Fan getIdentitFan() throws CantGetFanIdentityException {
        Fan fan = null;
        try {
            fan = getFanIdentityDao().getIdentityFan();
        } catch (CantInitializeTokenlyFanIdentityDatabaseException e) {
            e.printStackTrace();
        }
        return fan;
    }
    public Fan getIdentitFan(UUID id) throws CantGetFanIdentityException {
        Fan fan = null;
        try {
            fan = getFanIdentityDao().getIdentityFan(id);
        } catch (CantInitializeTokenlyFanIdentityDatabaseException e) {
            e.printStackTrace();
        }
        return fan;
    }

    public Fan createNewIdentityFan(User user,String password, byte[] profileImage, ExternalPlatform externalPlatform) throws CantCreateFanIdentityException {
        try {
            DeviceUser deviceUser = deviceUserManager.getLoggedInDeviceUser();

            ECCKeyPair keyPair = new ECCKeyPair();
            UUID id = UUID.randomUUID();
            String publicKey = keyPair.getPublicKey();
            String privateKey = keyPair.getPrivateKey();

            getFanIdentityDao().createNewUser(user, id, publicKey, privateKey, deviceUser, profileImage, password, externalPlatform);


            return new TokenlyFanIdentityImp(user,id,publicKey,profileImage,externalPlatform,pluginFileSystem, pluginId);
        } catch (CantGetLoggedInDeviceUserException e) {
            throw new CantCreateFanIdentityException("CAN'T CREATE NEW ARTIST IDENTITY", e, "Error getting current logged in device user", "");
        } catch (Exception e) {
            throw new CantCreateFanIdentityException("CAN'T CREATE NEW ARTIST IDENTITY", FermatException.wrapException(e), "", "");
        }
    }

    public Fan updateIdentityFan(User user, String password, UUID id, String publicKey, byte[] profileImage, ExternalPlatform externalPlatform) throws CantUpdateFanIdentityException {
        try {
            getFanIdentityDao().updateIdentityFanUser(user, password, id, publicKey, profileImage, externalPlatform);
            return getFanIdentityDao().getIdentityFan(id);
        } catch (CantInitializeTokenlyFanIdentityDatabaseException e) {
            throw new CantUpdateFanIdentityException(
                    e.getMessage(),
                    e,
                    "Can't update the fan identity",
                    "Cannot initialize database");
        } catch (CantUpdateFanIdentityException e) {
            throw new CantUpdateFanIdentityException(
                    e.getMessage(),
                    e,
                    "Can't update the fan identity",
                    "Unexpected error in database");
        } catch (CantGetFanIdentityException e) {
            throw new CantUpdateFanIdentityException(
                    e.getMessage(),
                    e,
                    "Can't get the fan identity",
                    "Unexpected error in database");
        }
    }

    /**
     * This method updates a Fan Identity.
     * @param fan
     * @throws CantUpdateFanIdentityException
     */
    public void updateIdentityFan(Fan fan) throws CantUpdateFanIdentityException{
        try{
            getFanIdentityDao().updateIdentityFanUser(fan);
        } catch (CantInitializeTokenlyFanIdentityDatabaseException e) {
            throw new CantUpdateFanIdentityException(
                    e.getMessage(),
                    e,
                    "Can't update the fan identity",
                    "Cannot initialize database");
        } catch (CantUpdateFanIdentityException e) {
            throw new CantUpdateFanIdentityException(
                    e.getMessage(),
                    e,
                    "Can't update the fan identity",
                    "Unexpected error in database");
        }
    }

}
