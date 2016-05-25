package com.bitdubai.fermat_tky_api.layer.sub_app_module.fan.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.modules.interfaces.FermatSettings;
import com.bitdubai.fermat_tky_api.all_definitions.exceptions.CantGetDefaultLanguageException;
import com.bitdubai.fermat_tky_api.all_definitions.exceptions.CantGetDefaultSkinException;
import com.bitdubai.fermat_tky_api.all_definitions.exceptions.CantSetDefaultLanguageException;
import com.bitdubai.fermat_tky_api.all_definitions.exceptions.CantSetDefaultSkinException;

import java.io.Serializable;
import java.util.UUID;

/**
 * TokenlyFanPreferenceSettings
 *
 * @author Gabriel Araujo 18/03/16.
 * @version 1.0
 */
public class TokenlyFanPreferenceSettings implements FermatSettings, Serializable {
    private boolean isHomeTutorialDialogEnabled;
    private Actors localActorType;
    private String localPublicKey;
    private PlatformComponentType localPlatformComponentType;

    public TokenlyFanPreferenceSettings() {
    }

    public boolean isHomeTutorialDialogEnabled() {
        return isHomeTutorialDialogEnabled;
    }

    public Actors getLocalActorType() {
        return localActorType;
    }

    public PlatformComponentType getLocalPlatformComponentType() {
        return localPlatformComponentType;
    }

    public String getLocalPublicKey() {
        return localPublicKey;
    }

    public void setProfileSelected(String publicKey, PlatformComponentType localPlatformComponentType) {
        this.localPlatformComponentType=localPlatformComponentType;
        this.localPublicKey=publicKey;
    }

    public void setIsHomeTutorialDialogEnabled(boolean isHomeTutorialDialogEnabled) {
        this.isHomeTutorialDialogEnabled = isHomeTutorialDialogEnabled;
    }
    /**
     * This method let us know the default language of a wallet
     *
     * @return the identifier of the default language of the wallet
     * @throws CantGetDefaultLanguageException
     */
    //@Override
    public UUID getDefaultLanguage() throws CantGetDefaultLanguageException {
        return null;
    }

    /**
     * This method let us know the default skin of a wallet
     *
     * @return the identifier of the default skin of the wallet
     * @throws CantGetDefaultSkinException
     */
    //@Override
    public UUID getDefaultSkin() throws CantGetDefaultSkinException {
        return null;
    }

    /**
     * This method let us set the default language for a wallet
     *
     * @param languageId the identifier of the language to set as default
     * @throws CantSetDefaultLanguageException
     */
    //@Override
    public void setDefaultLanguage(UUID languageId) throws CantSetDefaultLanguageException {

    }

    /**
     * This method let us set the default skin for a wallet
     *
     * @param skinId the identifier of the skin to set as default
     * @throws CantSetDefaultSkinException
     */
    //@Override
    public void setDefaultSkin(UUID skinId) throws CantSetDefaultSkinException {

    }

    @Override
    public void setIsPresentationHelpEnabled(boolean b) {
        isHomeTutorialDialogEnabled=b;
    }


}
