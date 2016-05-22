package com.bitdubai.fermat_art_plugin.layer.identity.fan.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_art_api.all_definition.enums.ArtExternalPlatform;
import com.bitdubai.fermat_art_api.layer.identity.fan.interfaces.Fanatic;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 18/05/16.
 */
public class FanRecord implements Fanatic, Serializable {

    /**
     * Definitions
     */
    private final String alias;
    private final String publicKey;
    private byte[] imageProfile;
    private final UUID externalIdentityID;
    private final ArtExternalPlatform externalPlatform;
    private final String externalUsername;

    public FanRecord(
            String alias,
            String publicKey,
            byte[] imageProfile,
            UUID externalIdentityID,
            ArtExternalPlatform externalPlatform,
            String externalUsername){
        this.alias = alias;
        this.publicKey = publicKey;
        this.imageProfile = imageProfile;
        this.externalIdentityID = externalIdentityID;
        this.externalPlatform = externalPlatform;
        this.externalUsername = externalUsername;
    }

    @Override
    public String getExternalUsername() {
        return this.externalUsername;
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
