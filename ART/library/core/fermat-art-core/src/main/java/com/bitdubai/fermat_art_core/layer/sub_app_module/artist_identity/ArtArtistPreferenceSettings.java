package com.bitdubai.fermat_art_core.layer.sub_app_module.artist_identity;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.modules.interfaces.FermatSettings;
import com.bitdubai.fermat_pip_api.all_definition.sub_app_module.settings.exceptions.CantGetDefaultLanguageException;
import com.bitdubai.fermat_pip_api.all_definition.sub_app_module.settings.exceptions.CantGetDefaultSkinException;
import com.bitdubai.fermat_pip_api.all_definition.sub_app_module.settings.exceptions.CantSetDefaultLanguageException;
import com.bitdubai.fermat_pip_api.all_definition.sub_app_module.settings.exceptions.CantSetDefaultSkinException;

import java.util.UUID;

/**
 * Created by Juan Sulbaran sulbaranja@gmail.com on 13/04/16.
 */
public class ArtArtistPreferenceSettings implements FermatSettings {

    private boolean isHomeTutorialDialogEnabled;
    private Actors localActorType;
    private String localPublicKey;
    private PlatformComponentType localPlatformComponentType;

    public ArtArtistPreferenceSettings() {
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