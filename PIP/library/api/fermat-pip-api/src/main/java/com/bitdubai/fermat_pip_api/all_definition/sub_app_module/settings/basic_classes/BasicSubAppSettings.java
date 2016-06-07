package com.bitdubai.fermat_pip_api.all_definition.sub_app_module.settings.basic_classes;

import com.bitdubai.fermat_pip_api.all_definition.sub_app_module.settings.exceptions.CantLoadSubAppSettings;
import com.bitdubai.fermat_pip_api.all_definition.sub_app_module.settings.exceptions.CantSaveSubAppSettings;
import com.bitdubai.fermat_pip_api.all_definition.sub_app_module.settings.interfaces.SubAppSettings;

import java.io.Serializable;
import java.util.UUID;

/**
 * The class <code>com.bitdubai.fermat_pip_api.all_definition.sub_app_module.settings.examples.BasicSubAppSettings</code>
 * contains all the basic functionality of a sub app settings in fermat. If you want to add more functionality maybe you can
 * extend this class.
 * <p/>
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 18/12/2015.
 *
 * @author lnacosta
 * @version 1.0.0
 */
public class BasicSubAppSettings implements SubAppSettings, Serializable {

    @Override
    public UUID getDefaultLanguage() throws com.bitdubai.fermat_pip_api.all_definition.sub_app_module.settings.exceptions.CantGetDefaultLanguageException {
        return null;
    }

    @Override
    public UUID getDefaultSkin() throws com.bitdubai.fermat_pip_api.all_definition.sub_app_module.settings.exceptions.CantGetDefaultSkinException {
        return null;
    }

    @Override
    public void setDefaultLanguage(UUID languageId) throws com.bitdubai.fermat_pip_api.all_definition.sub_app_module.settings.exceptions.CantSetDefaultLanguageException {

    }

    @Override
    public void setDefaultSkin(UUID skinId) throws com.bitdubai.fermat_pip_api.all_definition.sub_app_module.settings.exceptions.CantSetDefaultSkinException {

    }

    @Override
    public void setPreferenceSettings(String walletPreferenceSettings, String walletPublicKey) throws CantSaveSubAppSettings {
        // todo methods unused
    }

    @Override
    public String getPreferenceSettings(String walletPublicKey) throws CantLoadSubAppSettings {
        // todo methods unused
        return null;
    }

    @Override
    public void setIsPresentationHelpEnabled(boolean b) {

    }
}
