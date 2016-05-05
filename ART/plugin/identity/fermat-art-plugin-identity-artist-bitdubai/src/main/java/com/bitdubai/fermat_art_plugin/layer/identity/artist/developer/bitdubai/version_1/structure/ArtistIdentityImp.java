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
import com.bitdubai.fermat_art_api.all_definition.enums.ArtExternalPlatform;
import com.bitdubai.fermat_art_api.all_definition.enums.ArtistAcceptConnectionsType;
import com.bitdubai.fermat_art_api.all_definition.enums.ExposureLevel;
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
    private UUID externalIdentityID;
    private ArtExternalPlatform externalPlatform;
    private String externalUsername;
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
     * @param externalIdentityID
     * @param pluginFileSystem
     * @param pluginId
     */
    public ArtistIdentityImp(
            String alias,
            String publicKey,
            UUID externalIdentityID,
            PluginFileSystem pluginFileSystem,
            UUID pluginId,
            ArtExternalPlatform artExternalPlatform,
            String externalUsername) {
        this.alias = alias;
        this.publicKey = publicKey;
        this.externalIdentityID = externalIdentityID;
        this.pluginFileSystem = pluginFileSystem;
        this.pluginId = pluginId;
        this.externalPlatform = artExternalPlatform;
        this.externalUsername = externalUsername;
    }

    /**
     * @param alias
     * @param publicKey
     * @param imageProfile
     * @param externalIdentityID
     * @param pluginFileSystem
     * @param pluginId
     * @param artExternalPlatform
     * @param exposureLevel
     * @param acceptConnectionsType
     * @param externalUsername
     */
    public ArtistIdentityImp(
            String alias,
            String publicKey,
            byte[] imageProfile,
            UUID externalIdentityID,
            PluginFileSystem pluginFileSystem,
            UUID pluginId,
            ArtExternalPlatform artExternalPlatform, ExposureLevel exposureLevel, ArtistAcceptConnectionsType acceptConnectionsType, String externalUsername) {
        this.alias = alias;
        this.publicKey = publicKey;
        this.imageProfile = imageProfile;
        this.externalIdentityID = externalIdentityID;
        this.pluginFileSystem = pluginFileSystem;
        this.pluginId = pluginId;
        this.externalPlatform = artExternalPlatform;
        this.externalUsername = externalUsername;
        this.exposureLevel = exposureLevel;
        this.artistAcceptConnectionsType = acceptConnectionsType;
    }

    public ArtistIdentityImp(
            String publicKey,
            byte[] imageProfile,
            String alias,
            UUID externalIdentityID,
            ArtExternalPlatform externalPlatform,
            String externalUsername) {
        this.publicKey = publicKey;
        this.imageProfile = imageProfile;
        this.alias = alias;
        this.externalIdentityID = externalIdentityID;
        this.externalPlatform = externalPlatform;
        this.externalUsername = externalUsername;
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

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Override
    public String getAlias() {
        return this.alias;
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
    public ArtExternalPlatform getExternalPlatform() {
        return externalPlatform;
    }

    public void setExternalPlatform(ArtExternalPlatform externalPlatform) {
        this.externalPlatform = externalPlatform;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    @Override
    public UUID getExternalIdentityID() {
        return externalIdentityID;
    }

    public void setExternalIdentityID(UUID externalIdentityID) {
        this.externalIdentityID = externalIdentityID;
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
    public String getExternalUsername() {
        return externalUsername;
    }
}
