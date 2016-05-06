package com.bitdubai.fermat_art_plugin.layer.sub_app_module.fan_identity.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_art_api.all_definition.enums.ArtExternalPlatform;
import com.bitdubai.fermat_art_api.all_definition.exceptions.CantPublishIdentityException;
import com.bitdubai.fermat_art_api.all_definition.exceptions.IdentityNotFoundException;
import com.bitdubai.fermat_art_api.all_definition.interfaces.ArtIdentity;
import com.bitdubai.fermat_art_api.layer.identity.fan.exceptions.CantCreateFanIdentityException;
import com.bitdubai.fermat_art_api.layer.identity.fan.exceptions.CantGetFanIdentityException;
import com.bitdubai.fermat_art_api.layer.identity.fan.exceptions.CantListFanIdentitiesException;
import com.bitdubai.fermat_art_api.layer.identity.fan.exceptions.CantUpdateFanIdentityException;
import com.bitdubai.fermat_art_api.layer.identity.fan.exceptions.FanIdentityAlreadyExistsException;
import com.bitdubai.fermat_art_api.layer.identity.fan.interfaces.Fanatic;
import com.bitdubai.fermat_art_api.layer.identity.fan.interfaces.FanaticIdentityManager;
import com.bitdubai.fermat_art_api.layer.sub_app_module.identity.Fan.FanIdentityManagerModule;
import com.bitdubai.fermat_art_api.layer.sub_app_module.identity.Fan.FanIdentitySettings;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Created by alexander on 3/15/16.
 */
public class ModuleFanIdentityManager implements FanIdentityManagerModule,Serializable {
    private final ErrorManager errorManager;
    private final FanaticIdentityManager fanaticIdentityManager;

    public ModuleFanIdentityManager(ErrorManager errorManager, FanaticIdentityManager fanaticIdentityManager) {
        this.errorManager = errorManager;
        this.fanaticIdentityManager = fanaticIdentityManager;
    }

    @Override
    public List<Fanatic> listIdentitiesFromCurrentDeviceUser() throws CantListFanIdentitiesException {
        return fanaticIdentityManager.listIdentitiesFromCurrentDeviceUser();
    }

    @Override
    public HashMap<ArtExternalPlatform, HashMap<UUID, String>> listExternalIdentitiesFromCurrentDeviceUser() throws CantListFanIdentitiesException {
        return fanaticIdentityManager.listExternalIdentitiesFromCurrentDeviceUser();
    }

    @Override
    public ArtIdentity getLinkedIdentity(String publicKey) {
        return fanaticIdentityManager.getLinkedIdentity(publicKey);
    }

    @Override
    public Fanatic createFanaticIdentity(
            String alias,
            byte[] imageBytes,
            UUID externalIdentityID,
            ArtExternalPlatform artExternalPlatform,
            String externalUsername) throws
            CantCreateFanIdentityException, FanIdentityAlreadyExistsException {
        return fanaticIdentityManager.createFanaticIdentity(
                alias,
                imageBytes,
                externalIdentityID,
                artExternalPlatform,
                externalUsername);
    }

    @Override
    public void updateFanIdentity(
            String alias,
            String publicKey,
            byte[] imageProfile,
            UUID externalIdentityID,
            ArtExternalPlatform artExternalPlatform,
            String externalUsername) throws CantUpdateFanIdentityException {
        fanaticIdentityManager.updateFanIdentity(
                alias,
                publicKey,
                imageProfile,
                externalIdentityID,
                artExternalPlatform,
                externalUsername);
    }

    @Override
    public Fanatic getFanIdentity(String publicKey) throws CantGetFanIdentityException, IdentityNotFoundException {
        return fanaticIdentityManager.getFanIdentity(publicKey);
    }

    @Override
    public void publishIdentity(String publicKey) throws CantPublishIdentityException, IdentityNotFoundException {
        fanaticIdentityManager.publishIdentity(publicKey);
    }

    @Override
    public SettingsManager<FanIdentitySettings> getSettingsManager() {
        return null;
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
}
