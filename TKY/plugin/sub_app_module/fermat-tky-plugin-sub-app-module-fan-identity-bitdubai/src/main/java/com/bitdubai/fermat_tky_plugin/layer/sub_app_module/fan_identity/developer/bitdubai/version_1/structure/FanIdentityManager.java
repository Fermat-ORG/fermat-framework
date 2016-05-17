package com.bitdubai.fermat_tky_plugin.layer.sub_app_module.fan_identity.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.modules.ModuleManagerImpl;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
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
import com.bitdubai.fermat_tky_api.layer.sub_app_module.fan.interfaces.FanIdentitiesList;
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
        implements TokenlyFanIdentityManagerModule, Serializable {
    private final TokenlyFanIdentityManager tokenlyFanIdentityManager;
    private final TokenlyApiManager tokenlyApiManager;

    /**
     * Default constructor with parameters.
     * @param tokenlyFanIdentityManager
     * @param tokenlyApiManager
     */
    public FanIdentityManager(
            TokenlyFanIdentityManager tokenlyFanIdentityManager,
            TokenlyApiManager tokenlyApiManager,
            PluginFileSystem pluginFileSystem,
            UUID pluginId) {
        super(pluginFileSystem, pluginId);
        this.tokenlyFanIdentityManager = tokenlyFanIdentityManager;
        this.tokenlyApiManager = tokenlyApiManager;
    }

    @Override
    public FanIdentitiesList listIdentitiesFromCurrentDeviceUser() throws CantListFanIdentitiesException {
        FanIdentitiesList fanIdentitiesList = new FanIdentitiesListRecord(
                tokenlyFanIdentityManager.listIdentitiesFromCurrentDeviceUser());
        return fanIdentitiesList;
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
        try{
            List<Fan> fanaticList = tokenlyFanIdentityManager.listIdentitiesFromCurrentDeviceUser();
            ActiveActorIdentityInformation activeActorIdentityInformation;
            Fan fanatic;
            if(fanaticList!=null||!fanaticList.isEmpty()){
                fanatic = fanaticList.get(0);
                activeActorIdentityInformation = new ActiveActorIdentityInformationRecord(fanatic);
                return activeActorIdentityInformation;
            } else {
                //If there's no Identity created, in this version, I'll return an empty activeActorIdentityInformation
                activeActorIdentityInformation = new ActiveActorIdentityInformationRecord(null);
                return activeActorIdentityInformation;
            }
        } catch (CantListFanIdentitiesException e) {
            throw new CantGetSelectedActorIdentityException(
                    e,
                    "Getting the ActiveActorIdentityInformation",
                    "Cannot get the selected identity");
        }
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
