package com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_remote_community.settings;


import com.bitdubai.fermat_pip_api.all_definition.sub_app_module.settings.exceptions.CantGetDefaultLanguageException;
import com.bitdubai.fermat_pip_api.all_definition.sub_app_module.settings.exceptions.CantGetDefaultSkinException;
import com.bitdubai.fermat_pip_api.all_definition.sub_app_module.settings.exceptions.CantLoadSubAppSettings;
import com.bitdubai.fermat_pip_api.all_definition.sub_app_module.settings.exceptions.CantSaveSubAppSettings;
import com.bitdubai.fermat_pip_api.all_definition.sub_app_module.settings.exceptions.CantSetDefaultLanguageException;
import com.bitdubai.fermat_pip_api.all_definition.sub_app_module.settings.exceptions.CantSetDefaultSkinException;
import com.bitdubai.fermat_pip_api.all_definition.sub_app_module.settings.interfaces.SubAppSettings;

import java.util.UUID;

/**
 * Created by Eleazar (eorono@protonmail.com) on 3/04/16.
 */

public class ChatRemoteCommunitySettings implements SubAppSettings {

    private String lastSelectedIdentityPublicKey;
    private boolean presentationHelpEnabled;


    public ChatRemoteCommunitySettings() {}



    public String getLastSelectedIdentityPublicKey() { return this.lastSelectedIdentityPublicKey; }
    public void setLastSelectedIdentityPublicKey(String identityPublicKey) { this.lastSelectedIdentityPublicKey = identityPublicKey; }



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




    @Override
    public void setPreferenceSettings(String walletPreferenceSettings, String walletPublicKey) throws CantSaveSubAppSettings {}

    @Override
    public String getPreferenceSettings(String walletPublicKey) throws CantLoadSubAppSettings {return null;}

}
