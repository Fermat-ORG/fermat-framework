package com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.settings;

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
 * The class <code>com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.settings.CryptoBrokerCommunitySettings</code>
 * bla bla bla.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 23/12/2015.
 */
public class CryptoCustomerCommunitySettings implements SubAppSettings, Serializable {

    private String lastSelectedIdentityPublicKey;
    private boolean presentationHelpEnabled;


    public CryptoCustomerCommunitySettings() {}



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



    //TODO: Deprecated?
    @Override
    public void setPreferenceSettings(String walletPreferenceSettings, String walletPublicKey) throws CantSaveSubAppSettings {}

    @Override
    public String getPreferenceSettings(String walletPublicKey) throws CantLoadSubAppSettings {return null;}

}
