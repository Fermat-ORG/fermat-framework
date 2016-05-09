package com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.settings;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_pip_api.all_definition.sub_app_module.settings.exceptions.CantGetDefaultLanguageException;
import com.bitdubai.fermat_pip_api.all_definition.sub_app_module.settings.exceptions.CantGetDefaultSkinException;
import com.bitdubai.fermat_pip_api.all_definition.sub_app_module.settings.exceptions.CantLoadSubAppSettings;
import com.bitdubai.fermat_pip_api.all_definition.sub_app_module.settings.exceptions.CantSaveSubAppSettings;
import com.bitdubai.fermat_pip_api.all_definition.sub_app_module.settings.exceptions.CantSetDefaultLanguageException;
import com.bitdubai.fermat_pip_api.all_definition.sub_app_module.settings.exceptions.CantSetDefaultSkinException;
import com.bitdubai.fermat_pip_api.all_definition.sub_app_module.settings.interfaces.SubAppSettings;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 05/04/16.
 */
public class FanCommunitySettings implements SubAppSettings, Serializable {

    private String lastSelectedIdentityPublicKey;
    private Actors lastSelectedActorType;
    private boolean presentationHelpEnabled;

    /**
     * Default constructor.
     */
    public FanCommunitySettings() {}

    public String getLastSelectedIdentityPublicKey() {
        return this.lastSelectedIdentityPublicKey;
    }

    public void setLastSelectedIdentityPublicKey(
            String identityPublicKey) {
        this.lastSelectedIdentityPublicKey = identityPublicKey;
    }

    public Actors getLastSelectedActorType() {
        return lastSelectedActorType;
    }

    public void setLastSelectedActorType(Actors lastSelectedActorType) {
        this.lastSelectedActorType = lastSelectedActorType;
    }

    public void setPresentationHelpEnabled(boolean presentationHelpEnabled) {
        this.presentationHelpEnabled = presentationHelpEnabled;
    }

    @Override
    public void setIsPresentationHelpEnabled(boolean b) {
        this.presentationHelpEnabled = b;
    }

    public boolean isPresentationHelpEnabled() {
        return this.presentationHelpEnabled;
    }

    @Override
    public void setDefaultLanguage(UUID languageId) throws CantSetDefaultLanguageException {}

    @Override
    public UUID getDefaultLanguage() throws CantGetDefaultLanguageException {return null;}

    @Override
    public void setDefaultSkin(UUID skinId) throws CantSetDefaultSkinException {}

    @Override
    public UUID getDefaultSkin() throws CantGetDefaultSkinException {return null;}

    //TODO: Deprecated?
    @Override
    public void setPreferenceSettings(
            String walletPreferenceSettings,
            String walletPublicKey) throws CantSaveSubAppSettings {}

    @Override
    public String getPreferenceSettings(
            String walletPublicKey) throws CantLoadSubAppSettings {
        return null;
    }

}

