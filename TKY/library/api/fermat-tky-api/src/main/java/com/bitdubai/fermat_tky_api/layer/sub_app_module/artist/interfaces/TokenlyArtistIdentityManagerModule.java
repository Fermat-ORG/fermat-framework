package com.bitdubai.fermat_tky_api.layer.sub_app_module.artist.interfaces;

import com.bitdubai.fermat_api.layer.modules.ModuleSettingsImpl;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;
import com.bitdubai.fermat_tky_api.all_definitions.enums.ArtistAcceptConnectionsType;
import com.bitdubai.fermat_tky_api.all_definitions.enums.ExposureLevel;
import com.bitdubai.fermat_tky_api.all_definitions.enums.ExternalPlatform;
import com.bitdubai.fermat_tky_api.all_definitions.enums.TokenlyAPIStatus;
import com.bitdubai.fermat_tky_api.all_definitions.exceptions.IdentityNotFoundException;
import com.bitdubai.fermat_tky_api.all_definitions.exceptions.TokenlyAPINotAvailableException;
import com.bitdubai.fermat_tky_api.all_definitions.exceptions.WrongTokenlyUserCredentialsException;
import com.bitdubai.fermat_tky_api.layer.identity.artist.exceptions.ArtistIdentityAlreadyExistsException;
import com.bitdubai.fermat_tky_api.layer.identity.artist.exceptions.CantCreateArtistIdentityException;
import com.bitdubai.fermat_tky_api.layer.identity.artist.exceptions.CantGetArtistIdentityException;
import com.bitdubai.fermat_tky_api.layer.identity.artist.exceptions.CantListArtistIdentitiesException;
import com.bitdubai.fermat_tky_api.layer.identity.artist.exceptions.CantUpdateArtistIdentityException;
import com.bitdubai.fermat_tky_api.layer.identity.artist.interfaces.Artist;


import java.util.UUID;

/**
 * Created by Alexander Jimenez (alex_jimenez76@hotmail.com) on 3/17/16.
 */
public interface TokenlyArtistIdentityManagerModule extends
        ModuleManager<
                TokenlyArtistPreferenceSettings,
                ActiveActorIdentityInformation>,
        ModuleSettingsImpl<TokenlyArtistPreferenceSettings> {
    /**
     * Through the method <code>listIdentitiesFromCurrentDeviceUser</code> we can get all the artist
     * identities linked to the current logged device user.
     * @return
     * @throws CantListArtistIdentitiesException
     */
    ArtistIdentitiesList listIdentitiesFromCurrentDeviceUser() throws CantListArtistIdentitiesException;

    /**
     *
     * @param userName
     * @param profileImage
     * @param password
     * @param externalPlatform
     * @param exposureLevel
     * @param artistAcceptConnectionsType
     * @return
     * @throws CantCreateArtistIdentityException
     * @throws ArtistIdentityAlreadyExistsException
     */
    Artist createArtistIdentity(
            String userName,
            byte[] profileImage,
            String password,
            ExternalPlatform externalPlatform,
            ExposureLevel exposureLevel,
            ArtistAcceptConnectionsType artistAcceptConnectionsType) throws
            CantCreateArtistIdentityException,
            ArtistIdentityAlreadyExistsException, WrongTokenlyUserCredentialsException;

    /**
     *
     * @param username
     * @param password
     * @param id
     * @param publicKey
     * @param profileImage
     * @param externalPlatform
     * @param exposureLevel
     * @param artistAcceptConnectionsType
     * @throws CantUpdateArtistIdentityException
     */
    Artist updateArtistIdentity(
            String username,
            String password,
            UUID id,
            String publicKey,
            byte[] profileImage,
            ExternalPlatform externalPlatform,
            ExposureLevel exposureLevel,
            ArtistAcceptConnectionsType artistAcceptConnectionsType) throws
            CantUpdateArtistIdentityException, WrongTokenlyUserCredentialsException;

    /**
     * This method returns a Artist identity
     * @param publicKey
     * @return
     * @throws CantGetArtistIdentityException
     * @throws IdentityNotFoundException
     */
    Artist getArtistIdentity(UUID publicKey) throws
            CantGetArtistIdentityException,
            IdentityNotFoundException;

    /**
     * This method checks if the Tokenly Music API is available.
     * @return
     * @throws TokenlyAPINotAvailableException
     */
    TokenlyAPIStatus getMusicAPIStatus() throws TokenlyAPINotAvailableException;

    /**
     * Through the method <code>getSettingsManager</code> we can get a settings manager for the specified
     * settings class parametrized.
     *
     * @return a new instance of the settings manager for the specified fermat settings object.
     */
    @Override
    SettingsManager<TokenlyArtistPreferenceSettings> getSettingsManager();

}
