package com.bitdubai.fermat_art_plugin.layer.identity.artist.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.layer.all_definition.enums.DeviceDirectory;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginBinaryFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_art_api.all_definition.enums.ArtistAcceptConnectionsType;
import com.bitdubai.fermat_art_api.all_definition.enums.ExposureLevel;
import com.bitdubai.fermat_art_api.all_definition.enums.ExternalPlatform;
import com.bitdubai.fermat_art_api.layer.identity.artist.interfaces.Artist;
import com.bitdubai.fermat_art_plugin.layer.identity.artist.developer.bitdubai.version_1.ArtistIdentityPluginRoot;

import java.util.UUID;

/**
 * Created by Gabriel Araujo (gabe_512@hotmail.com) on 10/03/16.
 */
public class ArtistIdentityImp implements DealsWithPluginFileSystem, DealsWithPluginIdentity, Artist {

    private String alias;
    private String publicKey;
    private byte[] imageProfile;
    private String externalUserName;
    private String externalAccessToken;
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
     * @param alias
     * @param publicKey
     * @param externalUserName
     * @param externalAccessToken
     * @param externalPlatform
     * @param exposureLevel
     * @param artistAcceptConnectionsType
     * @param pluginFileSystem
     * @param pluginId
     */
    public ArtistIdentityImp(String alias, String publicKey, String externalUserName, String externalAccessToken, ExternalPlatform externalPlatform, ExposureLevel exposureLevel, ArtistAcceptConnectionsType artistAcceptConnectionsType, PluginFileSystem pluginFileSystem, UUID pluginId) {
        this.alias = alias;
        this.publicKey = publicKey;
        this.externalUserName = externalUserName;
        this.externalAccessToken = externalAccessToken;
        this.externalPlatform = externalPlatform;
        this.exposureLevel = exposureLevel;
        this.artistAcceptConnectionsType = artistAcceptConnectionsType;
        this.pluginFileSystem = pluginFileSystem;
        this.pluginId = pluginId;
    }

    /**
     *
     * @param alias
     * @param publicKey
     * @param imageProfile
     * @param externalUserName
     * @param externalAccessToken
     * @param externalPlatform
     * @param exposureLevel
     * @param artistAcceptConnectionsType
     */
    public ArtistIdentityImp(String alias, String publicKey, byte[] imageProfile, String externalUserName, String externalAccessToken, ExternalPlatform externalPlatform, ExposureLevel exposureLevel, ArtistAcceptConnectionsType artistAcceptConnectionsType) {
        this.alias = alias;
        this.publicKey = publicKey;
        this.imageProfile = imageProfile;
        this.externalUserName = externalUserName;
        this.externalAccessToken = externalAccessToken;
        this.externalPlatform = externalPlatform;
        this.exposureLevel = exposureLevel;
        this.artistAcceptConnectionsType = artistAcceptConnectionsType;
    }

    /**
     *
     * @param alias
     * @param publicKey
     * @param imageProfile
     * @param externalUserName
     * @param externalAccessToken
     * @param externalPlatform
     * @param exposureLevel
     * @param artistAcceptConnectionsType
     * @param pluginFileSystem
     * @param pluginId
     */
    public ArtistIdentityImp(String alias, String publicKey, byte[] imageProfile, String externalUserName, String externalAccessToken, ExternalPlatform externalPlatform, ExposureLevel exposureLevel, ArtistAcceptConnectionsType artistAcceptConnectionsType, PluginFileSystem pluginFileSystem, UUID pluginId) {
        this.alias = alias;
        this.publicKey = publicKey;
        this.imageProfile = imageProfile;
        this.externalUserName = externalUserName;
        this.externalAccessToken = externalAccessToken;
        this.externalPlatform = externalPlatform;
        this.exposureLevel = exposureLevel;
        this.artistAcceptConnectionsType = artistAcceptConnectionsType;
        this.pluginFileSystem = pluginFileSystem;
        this.pluginId = pluginId;
    }

    /**
     *
     * @param alias
     * @param publicKey
     * @param imageProfile
     * @param pluginFileSystem
     * @param pluginId
     */
    public ArtistIdentityImp(String alias, String publicKey, byte[] imageProfile, PluginFileSystem pluginFileSystem, UUID pluginId) {
        this.alias = alias;
        this.publicKey = publicKey;
        this.imageProfile = imageProfile;
        this.pluginFileSystem = pluginFileSystem;
        this.pluginId = pluginId;
    }

    /**
     * DealWithPluginFileSystem Interface implementation.
     */

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Override
    public String getPublicKey() {
        return publicKey;
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
                    ArtistIdentityPluginRoot.ARTIST_PROFILE_IMAGE_FILE_NAME + "_" + publicKey,
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

    @Override
    public String getExternalUsername() {
        return externalUserName;
    }

    @Override
    public String getExternalAccesToken() {
        return externalAccessToken;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
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
}
