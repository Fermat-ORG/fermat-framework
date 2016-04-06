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
import com.bitdubai.fermat_art_api.layer.identity.artist.interfaces.Artist;
import com.bitdubai.fermat_art_plugin.layer.identity.artist.developer.bitdubai.version_1.ArtistIdentityPluginRoot;
import com.bitdubai.fermat_tky_api.all_definitions.enums.ExternalPlatform;

import java.util.UUID;

/**
 * Created by Gabriel Araujo (gabe_512@hotmail.com) on 10/03/16.
 */
public class ArtistIdentityImp implements DealsWithPluginFileSystem, DealsWithPluginIdentity, Artist {

    private String alias;
    private String publicKey;
    private byte[] imageProfile;
    private UUID externalIdentityID;
    private ExternalPlatform externalPlatform;
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
    public ArtistIdentityImp(String alias, String publicKey, UUID externalIdentityID, PluginFileSystem pluginFileSystem, UUID pluginId) {
        this.alias = alias;
        this.publicKey = publicKey;
        this.externalIdentityID = externalIdentityID;
        this.pluginFileSystem = pluginFileSystem;
        this.pluginId = pluginId;
    }

    /**
     *
     * @param alias
     * @param publicKey
     * @param imageProfile
     * @param externalIdentityID
     */
    public ArtistIdentityImp(String alias, String publicKey, byte[] imageProfile, UUID externalIdentityID) {
        this.alias = alias;
        this.publicKey = publicKey;
        this.imageProfile = imageProfile;
        this.externalIdentityID = externalIdentityID;
    }

    /**
     *
     * @param alias
     * @param publicKey
     * @param imageProfile
     * @param externalIdentityID
     * @param pluginFileSystem
     * @param pluginId
     */
    public ArtistIdentityImp(String alias, String publicKey, byte[] imageProfile,UUID externalIdentityID, PluginFileSystem pluginFileSystem, UUID pluginId) {
        this.alias = alias;
        this.publicKey = publicKey;
        this.imageProfile = imageProfile;
        this.externalIdentityID = externalIdentityID;
        this.pluginFileSystem = pluginFileSystem;
        this.pluginId = pluginId;
    }

    public ArtistIdentityImp(String publicKey, byte[] imageProfile, String alias, UUID externalIdentityID, ExternalPlatform externalPlatform) {
        this.publicKey = publicKey;
        this.imageProfile = imageProfile;
        this.alias = alias;
        this.externalIdentityID = externalIdentityID;
        this.externalPlatform = externalPlatform;
    }

    /**
     * DealWithPluginFileSystem Interface implementation.
     */

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
    public ExternalPlatform getExternalPlatform() {
        return externalPlatform;
    }

    public void setExternalPlatform(ExternalPlatform externalPlatform) {
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
}
