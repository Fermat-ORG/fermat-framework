package com.bitdubai.fermat_tky_plugin.layer.sub_app_module.fan_identity.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.modules.ModuleManagerImpl;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_tky_api.all_definitions.enums.ExternalPlatform;
import com.bitdubai.fermat_tky_api.all_definitions.enums.TokenlyAPIStatus;
import com.bitdubai.fermat_tky_api.all_definitions.exceptions.IdentityNotFoundException;
import com.bitdubai.fermat_tky_api.all_definitions.exceptions.TokenlyAPINotAvailableException;
import com.bitdubai.fermat_tky_api.all_definitions.exceptions.WrongTokenlyUserCredentialsException;
import com.bitdubai.fermat_tky_api.layer.external_api.interfaces.TokenlyApiManager;
import com.bitdubai.fermat_tky_api.layer.identity.fan.exceptions.CantCreateFanIdentityException;
import com.bitdubai.fermat_tky_api.layer.identity.fan.exceptions.CantGetFanIdentityException;
import com.bitdubai.fermat_tky_api.layer.identity.fan.exceptions.CantListFanIdentitiesException;
import com.bitdubai.fermat_tky_api.layer.identity.fan.exceptions.CantUpdateFanIdentityException;
import com.bitdubai.fermat_tky_api.layer.identity.fan.exceptions.FanIdentityAlreadyExistsException;
import com.bitdubai.fermat_tky_api.layer.identity.fan.interfaces.Fan;
import com.bitdubai.fermat_tky_api.layer.identity.fan.interfaces.TokenlyFanIdentityManager;
import com.bitdubai.fermat_tky_api.layer.sub_app_module.fan.interfaces.TokenlyFanIdentityManagerModule;
import com.bitdubai.fermat_tky_api.layer.sub_app_module.fan.interfaces.TokenlyFanPreferenceSettings;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * Created by Alexander Jimenez (alex_jimenez76@hotmail.com) on 3/15/16.
 */
public class FanIdentityManager
        extends ModuleManagerImpl<TokenlyFanPreferenceSettings>
        implements TokenlyFanIdentityManagerModule,Serializable {
    private final ErrorManager errorManager;
    private final TokenlyFanIdentityManager tokenlyFanIdentityManager;
    private final TokenlyApiManager tokenlyApiManager;

    /**
     * Default constructor with parameters.
     * @param errorManager
     * @param tokenlyFanIdentityManager
     * @param tokenlyApiManager
     */
    public FanIdentityManager(
            ErrorManager errorManager,
            TokenlyFanIdentityManager tokenlyFanIdentityManager,
            TokenlyApiManager tokenlyApiManager,
            PluginFileSystem pluginFileSystem,
            UUID pluginId) {
        super(pluginFileSystem, pluginId);
        this.errorManager = errorManager;
        this.tokenlyFanIdentityManager = tokenlyFanIdentityManager;
        this.tokenlyApiManager = tokenlyApiManager;
    }

    @Override
    public List<Fan> listIdentitiesFromCurrentDeviceUser() throws CantListFanIdentitiesException {
        return tokenlyFanIdentityManager.listIdentitiesFromCurrentDeviceUser();
    }

    @Override
    public Fan createFanIdentity(
            String userName,
            byte[] profileImage,
            String userPassword,
            ExternalPlatform externalPlatform) throws
            CantCreateFanIdentityException,
            FanIdentityAlreadyExistsException,
            WrongTokenlyUserCredentialsException {
        return tokenlyFanIdentityManager.createFanIdentity(
                userName,
                profileImage,
                userPassword,
                externalPlatform);
    }

    @Override
    public Fan updateFanIdentity(
            String userName,
            String password,
            UUID id,
            String publicKey,
            byte[] profileImage,
            ExternalPlatform externalPlatform) throws
            CantUpdateFanIdentityException,
            WrongTokenlyUserCredentialsException {
        return tokenlyFanIdentityManager.updateFanIdentity(
                userName,
                password,
                id,
                publicKey,
                profileImage,
                externalPlatform);
    }

    @Override
    public Fan getFanIdentity(UUID publicKey) throws CantGetFanIdentityException, IdentityNotFoundException {
        return tokenlyFanIdentityManager.getFanIdentity(publicKey);
    }

    /**
     * This method checks if the Tokenly Music API is available.
     * @return
     * @throws TokenlyAPINotAvailableException
     */
    @Override
    public TokenlyAPIStatus getMusicAPIStatus() throws TokenlyAPINotAvailableException {
        return tokenlyApiManager.getMusicAPIStatus();
    }

    /*@Override
    public SettingsManager<TokenlyFanPreferenceSettings> getSettingsManager() {
        return null;
    }*/

    @Override
    public ActiveActorIdentityInformation getSelectedActorIdentity() throws
            CantGetSelectedActorIdentityException,
            ActorIdentityNotSelectedException {
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
}
