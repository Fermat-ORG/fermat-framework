package com.bitdubai.fermat_tky_plugin.layer.identity.artist_identity.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.layer.all_definition.enums.DeviceDirectory;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginBinaryFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_tky_api.all_definitions.enums.ArtistAcceptConnectionsType;
import com.bitdubai.fermat_tky_api.all_definitions.enums.ExposureLevel;
import com.bitdubai.fermat_tky_api.all_definitions.enums.ExternalPlatform;
import com.bitdubai.fermat_tky_api.all_definitions.exceptions.ObjectNotSetException;
import com.bitdubai.fermat_tky_api.all_definitions.interfaces.User;
import com.bitdubai.fermat_tky_api.all_definitions.util.ObjectChecker;
import com.bitdubai.fermat_tky_api.layer.external_api.interfaces.music.MusicUser;
import com.bitdubai.fermat_tky_api.layer.identity.artist.interfaces.Artist;
import com.bitdubai.fermat_tky_plugin.layer.identity.artist_identity.developer.bitdubai.version_1.TokenlyArtistIdentityPluginRoot;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by Gabriel Araujo (gabe_512@hotmail.com) on 10/03/16.
 */
public class TokenlyArtistIdentityImp implements DealsWithPluginFileSystem, DealsWithPluginIdentity, Artist, Serializable {

    private UUID id;
    private String tokenlyID;
    private String publicKey;
    private byte[] imageProfile;
    private String externalUserName;
    private String externalAccessToken;
    private String apiSecretKey;
    private String externalPassword;
    private String email;
    private ExternalPlatform externalPlatform;
    private ExposureLevel exposureLevel;
    private ArtistAcceptConnectionsType artistAcceptConnectionsType;
    /**
     * DealsWithPluginFileSystem Interface member variables.
     */
    private PluginFileSystem pluginFileSystem;

    /**
     * DealsWithPluginIdentity Interface member variables.
     */
    private UUID pluginId;

    /**
     *
     * @param user
     * @param id
     * @param publicKey
     * @param imageProfile
     * @param externalPlatform
     */
    public TokenlyArtistIdentityImp(User user, UUID id, String publicKey, byte[] imageProfile, ExternalPlatform externalPlatform, ExposureLevel exposureLevel, ArtistAcceptConnectionsType artistAcceptConnectionsType,PluginFileSystem pluginFileSystem, UUID pluginId) {
        this.id = id;
        this.tokenlyID = user.getTokenlyId();
        this.publicKey = publicKey;
        this.imageProfile = imageProfile;
        this.externalUserName = user.getUsername();
        this.externalAccessToken = user.getApiToken();
        this.apiSecretKey = user.getApiSecretKey();
        this.email = user.getEmail();
        this.externalPlatform = externalPlatform;
        this.exposureLevel = exposureLevel;
        this.artistAcceptConnectionsType = artistAcceptConnectionsType;
        this.pluginFileSystem = pluginFileSystem;
        this.pluginId = pluginId;
    }


    /**
     * Constructor
     * @param id
     * @param tokenlyID
     * @param publicKey
     * @param imageProfile
     * @param externalUserName
     * @param externalAccessToken
     * @param apiSecretKey
     * @param externalPassword
     * @param externalPlatform
     * @param email
     */
    public TokenlyArtistIdentityImp(UUID id, String tokenlyID, String publicKey, byte[] imageProfile, String externalUserName, String externalAccessToken, String apiSecretKey, String externalPassword, ExternalPlatform externalPlatform, ExposureLevel exposureLevel, ArtistAcceptConnectionsType artistAcceptConnectionsType,String email) {
        this.id = id;
        this.tokenlyID = tokenlyID;
        this.publicKey = publicKey;
        this.imageProfile = imageProfile;
        this.externalUserName = externalUserName;
        this.externalAccessToken = externalAccessToken;
        this.apiSecretKey = apiSecretKey;
        this.externalPassword = externalPassword;
        this.externalPlatform = externalPlatform;
        this.exposureLevel = exposureLevel;
        this.artistAcceptConnectionsType = artistAcceptConnectionsType;
        this.email = email;
    }
    /**
     * Constructor
     * @param id
     * @param tokenlyID
     * @param publicKey
     * @param imageProfile
     * @param externalUserName
     * @param externalAccessToken
     * @param apiSecretKey
     * @param externalPlatform
     * @param email
     * @param pluginFileSystem
     * @param pluginId
     */
    public TokenlyArtistIdentityImp(UUID id, String tokenlyID, String publicKey, byte[] imageProfile, String externalUserName, String externalAccessToken, String apiSecretKey, ExternalPlatform externalPlatform, String email,ExposureLevel exposureLevel, ArtistAcceptConnectionsType artistAcceptConnectionsType, PluginFileSystem pluginFileSystem, UUID pluginId) {
        this.id = id;
        this.tokenlyID = tokenlyID;
        this.publicKey = publicKey;
        this.imageProfile = imageProfile;
        this.externalUserName = externalUserName;
        this.externalAccessToken = externalAccessToken;
        this.apiSecretKey = apiSecretKey;
        this.externalPlatform = externalPlatform;
        this.email = email;
        this.exposureLevel = exposureLevel;
        this.artistAcceptConnectionsType = artistAcceptConnectionsType;
        this.pluginFileSystem = pluginFileSystem;
        this.pluginId = pluginId;
    }

    @Override
    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public byte[] getProfileImage() {
        return imageProfile;
    }

    @Override
    public void setNewProfileImage(byte[] imageBytes) {
        try {
            PluginBinaryFile file = this.pluginFileSystem.createBinaryFile(pluginId,
                    DeviceDirectory.LOCAL_USERS.getName(),
                    TokenlyArtistIdentityPluginRoot.TOKENLY_ARTIST_IDENTITY_PROFILE_IMAGE + "_" + publicKey,
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );

            file.setContent(imageBytes);


            file.persistToMedia();
        } catch (CantPersistFileException e) {
            e.printStackTrace();
        } catch (CantCreateFileException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        this.imageProfile = imageBytes;
    }

    public void setId(UUID id) {
        this.id = id;
    }


    public void setExternalUserName(String externalUserName) {
        this.externalUserName = externalUserName;
    }


    public void setExternalAccessToken(String externalAccessToken) {
        this.externalAccessToken = externalAccessToken;
    }

    @Override
    public ExternalPlatform getExternalPlatform() {
        return externalPlatform;
    }

    @Override
    public MusicUser getMusicUser() {
        //We're going to check if all Music parameters are set
        Object[] objects = new Object[]{this.tokenlyID,
                this.externalUserName,
                this.email,
                this.externalAccessToken,
                this.apiSecretKey};
        try{
            ObjectChecker.checkArguments(objects);
        } catch (ObjectNotSetException e) {
            //In theory, this cannot be to happen, I'll return null
            return null;
        }
        MusicUser musicUser = new TokenlyUserImp(
                this.tokenlyID,
                this.externalUserName,
                this.email,
                this.externalAccessToken,
                this.apiSecretKey);
        return musicUser;
    }

    @Override
    public String getUserPassword() {
        return this.externalPassword;
    }

    public void setExternalPlatform(ExternalPlatform externalPlatform) {
        this.externalPlatform = externalPlatform;
    }

    @Override
    public ExposureLevel getExposureLevel() {
        return exposureLevel;
    }

    public void setExposureLevel(ExposureLevel exposureLevel) {
        this.exposureLevel = exposureLevel;
    }

    @Override
    public ArtistAcceptConnectionsType getArtistAcceptConnectionsType() {
        return artistAcceptConnectionsType;
    }

    public void setArtistAcceptConnectionsType(ArtistAcceptConnectionsType artistAcceptConnectionsType) {
        this.artistAcceptConnectionsType = artistAcceptConnectionsType;
    }

    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
        this.pluginFileSystem = pluginFileSystem;
    }

    @Override
    public void setPluginId(UUID pluginId) {
        this.pluginId = pluginId;
    }

    @Override
    public String getTokenlyId() {
        return this.tokenlyID;
    }

    @Override
    public String getUsername() {
        return this.externalUserName;
    }

    @Override
    public String getEmail() {
        return this.email;
    }

    @Override
    public String getApiToken() {
        return this.externalAccessToken;
    }

    @Override
    public String getApiSecretKey() {
        return this.apiSecretKey;
    }
}
