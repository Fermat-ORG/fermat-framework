package com.bitdubai.fermat_tky_plugin.layer.identity.artist_identity.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_tky_api.all_definitions.enums.ArtistAcceptConnectionsType;
import com.bitdubai.fermat_tky_api.all_definitions.enums.ExposureLevel;
import com.bitdubai.fermat_tky_api.all_definitions.enums.ExternalPlatform;
import com.bitdubai.fermat_tky_api.all_definitions.exceptions.ObjectNotSetException;
import com.bitdubai.fermat_tky_api.all_definitions.interfaces.User;
import com.bitdubai.fermat_tky_api.all_definitions.util.ObjectChecker;
import com.bitdubai.fermat_tky_api.layer.external_api.interfaces.music.MusicUser;
import com.bitdubai.fermat_tky_api.layer.identity.artist.interfaces.Artist;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 17/05/16.
 */
public class TokenlyArtistIdentityRecord implements Artist, Serializable{

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

    public TokenlyArtistIdentityRecord(
            UUID id,
            String tokenlyID,
            String publicKey,
            byte[] imageProfile,
            String externalUserName,
            String externalAccessToken,
            String apiSecretKey,
            String externalPassword,
            String email,
            ExternalPlatform externalPlatform,
            ExposureLevel exposureLevel,
            ArtistAcceptConnectionsType artistAcceptConnectionsType) {
        this.id = id;
        this.tokenlyID = tokenlyID;
        this.publicKey = publicKey;
        this.imageProfile = imageProfile;
        this.externalUserName = externalUserName;
        this.externalAccessToken = externalAccessToken;
        this.apiSecretKey = apiSecretKey;
        this.externalPassword = externalPassword;
        this.email = email;
        this.externalPlatform = externalPlatform;
        this.exposureLevel = exposureLevel;
        this.artistAcceptConnectionsType = artistAcceptConnectionsType;
    }

    public TokenlyArtistIdentityRecord(
            User user,
            UUID id,
            String publicKey,
            byte[] imageProfile,
            ExternalPlatform externalPlatform,
            ExposureLevel exposureLevel,
            ArtistAcceptConnectionsType artistAcceptConnectionsType) {
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
    public UUID getId() {
        return this.id;
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
    public ExternalPlatform getExternalPlatform() {
        return this.externalPlatform;
    }

    @Override
    public MusicUser getMusicUser() {
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
