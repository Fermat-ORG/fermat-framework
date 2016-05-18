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
import com.bitdubai.fermat_tky_api.all_definitions.exceptions.IdentityNotFoundException;
import com.bitdubai.fermat_tky_api.all_definitions.exceptions.WrongTokenlyUserCredentialsException;
import com.bitdubai.fermat_tky_api.all_definitions.interfaces.User;
import com.bitdubai.fermat_tky_api.layer.external_api.exceptions.CantGetUserException;
import com.bitdubai.fermat_tky_api.layer.external_api.interfaces.TokenlyApiManager;
import com.bitdubai.fermat_tky_api.layer.identity.fan.exceptions.CantCreateFanIdentityException;
import com.bitdubai.fermat_tky_api.layer.identity.fan.exceptions.CantGetFanIdentityException;
import com.bitdubai.fermat_tky_api.layer.identity.fan.exceptions.CantListFanIdentitiesException;
import com.bitdubai.fermat_tky_api.layer.identity.fan.exceptions.CantUpdateFanIdentityException;
import com.bitdubai.fermat_tky_api.layer.identity.fan.exceptions.FanIdentityAlreadyExistsException;
import com.bitdubai.fermat_tky_api.layer.identity.fan.interfaces.Fan;
import com.bitdubai.fermat_tky_api.layer.identity.fan.interfaces.TokenlyFanIdentityManager;
import com.bitdubai.fermat_tky_plugin.layer.identity.fan_identity.developer.bitdubai.version_1.database.TokenlyFanIdentityDao;
import com.bitdubai.fermat_tky_plugin.layer.identity.fan_identity.developer.bitdubai.version_1.exceptions.CantInitializeTokenlyFanIdentityDatabaseException;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

/**
 * Created by Gabriel Araujo 10/03/16.
 */
public class TokenlyIdentityFanManagerImpl
        implements TokenlyFanIdentityManager,
        Serializable
{
    /**
     * IdentityAssetIssuerManagerImpl member variables
     */
    UUID pluginId;

    /**
     * DealsWithErrors interface member variables
     */
    //ErrorManager errorManager;

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

    /**
     * Constructor
     *
     * @param logManager
     * @param pluginDatabaseSystem
     * @param pluginFileSystem
     */
    public TokenlyIdentityFanManagerImpl(/*ErrorManager errorManager,*/ LogManager logManager, PluginDatabaseSystem pluginDatabaseSystem, PluginFileSystem pluginFileSystem, UUID pluginId, DeviceUserManager deviceUserManager, TokenlyApiManager tokenlyApiManager){
        //this.errorManager = errorManager;
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


    public Fan getIdentitFan() throws CantGetFanIdentityException {
        Fan fan = null;
        try {
            fan = getFanIdentityDao().getIdentityFan();
        } catch (CantInitializeTokenlyFanIdentityDatabaseException e) {
            e.printStackTrace();
        }
        return fan;
    }

    /**
     * This method updates a Fan Identity.
     * @param fan
     * @throws CantUpdateFanIdentityException
     */
    public void updateIdentityFan(Fan fan) throws CantUpdateFanIdentityException{

    }

    @Override
    public List<Fan> listIdentitiesFromCurrentDeviceUser() throws CantListFanIdentitiesException {
        try {
            List<Fan> fans;

            DeviceUser loggedUser = deviceUserManager.getLoggedInDeviceUser();
            TokenlyFanIdentityDao tokenlyFanIdentityDao = getFanIdentityDao();
            fans = tokenlyFanIdentityDao.getIdentityFansFromCurrentDeviceUser(loggedUser);
            return fans;

        } catch (CantGetLoggedInDeviceUserException e) {
            throw new CantListFanIdentitiesException("CAN'T GET NEW ARTIST IDENTITIES", e, "Error get logged user device", "");
        } catch (Exception e) {
            throw new CantListFanIdentitiesException("CAN'T GET NEW ARTIST IDENTITIES", FermatException.wrapException(e), "", "");
        }
    }

    @Override
    public Fan createFanIdentity(String userName, byte[] profileImage, String externalPassword, ExternalPlatform externalPlatform) throws CantCreateFanIdentityException, FanIdentityAlreadyExistsException, WrongTokenlyUserCredentialsException {
        try {
            DeviceUser deviceUser = deviceUserManager.getLoggedInDeviceUser();

            ECCKeyPair keyPair = new ECCKeyPair();
            UUID id = UUID.randomUUID();
            String publicKey = keyPair.getPublicKey();
            String privateKey = keyPair.getPrivateKey();
            User user=null;
            try{
                if(externalPlatform == ExternalPlatform.DEFAULT_EXTERNAL_PLATFORM)
                    user = tokenlyApiManager.validateTokenlyUser(userName, externalPassword);
            } catch (CantGetUserException |InterruptedException | ExecutionException e) {
                throw new CantCreateFanIdentityException(
                        e,
                        "Validating Tokenly User",
                        "Cannot create Tokenly User");
            }
            getFanIdentityDao().createNewUser(user, id, publicKey, privateKey, deviceUser, profileImage, externalPassword, externalPlatform);


            return new TokenlyFanIdentityRecord(user,id,publicKey,profileImage,externalPlatform);
        } catch (CantGetLoggedInDeviceUserException e) {
            throw new CantCreateFanIdentityException("CAN'T CREATE NEW ARTIST IDENTITY", e, "Error getting current logged in device user", "");
        } catch (Exception e) {
            throw new CantCreateFanIdentityException("CAN'T CREATE NEW ARTIST IDENTITY", FermatException.wrapException(e), "", "");
        }
    }

    @Override
    public Fan updateFanIdentity(
            String userName,
            String password,
            UUID id,
            String publicKey,
            byte[] profileImage,
            ExternalPlatform externalPlatform) throws CantUpdateFanIdentityException, WrongTokenlyUserCredentialsException {
        try {
            User user=null;
            try{
                if(externalPlatform == ExternalPlatform.DEFAULT_EXTERNAL_PLATFORM)
                    user = tokenlyApiManager.validateTokenlyUser(userName, password);
            } catch (CantGetUserException |InterruptedException | ExecutionException  e) {
                throw new CantUpdateFanIdentityException(
                        e,
                        "Validating Tokenly User",
                        "Cannot create Tokenly User");
            }
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

    @Override
    public Fan getFanIdentity(UUID publicKey) throws CantGetFanIdentityException, IdentityNotFoundException {
        Fan fan = null;
        try {
            fan = getFanIdentityDao().getIdentityFan(publicKey);
        } catch (CantInitializeTokenlyFanIdentityDatabaseException e) {
            e.printStackTrace();
        }
        return fan;
    }

    @Override
    public void updateFanIdentity(Fan fan) throws CantUpdateFanIdentityException {
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
