package com.bitdubai.fermat_art_plugin.layer.identity.artist.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_art_api.all_definition.enums.ArtExternalPlatform;
import com.bitdubai.fermat_art_api.all_definition.enums.ArtistAcceptConnectionsType;
import com.bitdubai.fermat_art_api.all_definition.enums.ExposureLevel;
import com.bitdubai.fermat_art_api.layer.identity.artist.interfaces.Artist;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 18/05/16.
 */
public class ArtistRecord implements Artist, Serializable {

    private String alias;
    private String publicKey;
    private byte[] imageProfile;
    private UUID externalIdentityID;
    private ArtExternalPlatform externalPlatform;
    private String externalUsername;
    private ExposureLevel exposureLevel;
    private ArtistAcceptConnectionsType artistAcceptConnectionsType;

    /**
     * Constructor with parameters
     * @param alias
     * @param publicKey
     * @param imageProfile
     * @param externalIdentityID
     * @param externalPlatform
     * @param externalUsername
     * @param exposureLevel
     * @param artistAcceptConnectionsType
     */
    public ArtistRecord(
            String alias,
            String publicKey,
            byte[] imageProfile,
            UUID externalIdentityID,
            ArtExternalPlatform externalPlatform,
            String externalUsername,
            ExposureLevel exposureLevel,
            ArtistAcceptConnectionsType artistAcceptConnectionsType) {
        this.alias = alias;
        this.publicKey = publicKey;
        this.imageProfile = imageProfile;
        this.externalIdentityID = externalIdentityID;
        this.externalPlatform = externalPlatform;
        this.externalUsername = externalUsername;
        this.exposureLevel = exposureLevel;
        this.artistAcceptConnectionsType = artistAcceptConnectionsType;
    }

    @Override
    public String getExternalUsername() {
        return this.externalUsername;
    }

    @Override
    public ExposureLevel getExposureLevel() {
        return this.exposureLevel;
    }

    @Override
    public ArtistAcceptConnectionsType getArtistAcceptConnectionsType() {
        return this.artistAcceptConnectionsType;
    }

    @Override
    public String getAlias() {
        return this.alias;
    }

    @Override
    public String getPublicKey() {
        return this.publicKey;
    }

    @Override
    public byte[] getProfileImage() {
        return this.imageProfile;
    }

    @Override
    public void setNewProfileImage(byte[] imageBytes) {
        this.imageProfile = imageBytes;
    }

    @Override
    public UUID getExternalIdentityID() {
        return this.externalIdentityID;
    }

    @Override
    public ArtExternalPlatform getExternalPlatform() {
        return this.externalPlatform;
    }
}
