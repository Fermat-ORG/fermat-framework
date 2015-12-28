package com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.settings;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CantSelectIdentityException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.interfaces.CryptoBrokerCommunitySelectableIdentity;
import com.bitdubai.fermat_pip_api.all_definition.sub_app_module.settings.exceptions.CantGetDefaultLanguageException;
import com.bitdubai.fermat_pip_api.all_definition.sub_app_module.settings.exceptions.CantGetDefaultSkinException;
import com.bitdubai.fermat_pip_api.all_definition.sub_app_module.settings.exceptions.CantLoadSubAppSettings;
import com.bitdubai.fermat_pip_api.all_definition.sub_app_module.settings.exceptions.CantSaveSubAppSettings;
import com.bitdubai.fermat_pip_api.all_definition.sub_app_module.settings.exceptions.CantSetDefaultLanguageException;
import com.bitdubai.fermat_pip_api.all_definition.sub_app_module.settings.exceptions.CantSetDefaultSkinException;
import com.bitdubai.fermat_pip_api.all_definition.sub_app_module.settings.interfaces.SubAppSettings;

import java.util.UUID;

/**
 * The class <code>com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.settings.CryptoBrokerCommunitySettings</code>
 * bla bla bla.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 23/12/2015.
 */
public class CryptoBrokerCommunitySettings implements SubAppSettings {

    private CryptoBrokerCommunitySelectableIdentity lastSelectedIdentity;

    public CryptoBrokerCommunitySettings() {
        this.lastSelectedIdentity = new CryptoBrokerCommunitySelectableIdentity() {
            @Override
            public void select() throws CantSelectIdentityException {

            }

            @Override
            public String getPublicKey() {
                return "testingdata";
            }

            @Override
            public Actors getActorType() {
                return Actors.CBP_CRYPTO_BROKER;
            }

            @Override
            public String getAlias() {
                return "testingdata";
            }

            @Override
            public byte[] getImage() {
                return new byte[0];
            }
        };
    }

    public CryptoBrokerCommunitySelectableIdentity getLastSelectedIdentity() {
        return lastSelectedIdentity;
    }

    public void setLastSelectedIdentity(CryptoBrokerCommunitySelectableIdentity lastSelectedIdentity) {
        this.lastSelectedIdentity = lastSelectedIdentity;
    }

    @Override
    public UUID getDefaultLanguage() throws CantGetDefaultLanguageException {
        return null;
    }

    @Override
    public UUID getDefaultSkin() throws CantGetDefaultSkinException {
        return null;
    }

    @Override
    public void setDefaultLanguage(UUID languageId) throws CantSetDefaultLanguageException {

    }

    @Override
    public void setDefaultSkin(UUID skinId) throws CantSetDefaultSkinException {

    }

    @Override
    public void setPreferenceSettings(String walletPreferenceSettings, String walletPublicKey) throws CantSaveSubAppSettings {

    }

    @Override
    public String getPreferenceSettings(String walletPublicKey) throws CantLoadSubAppSettings {
        return null;
    }

}
