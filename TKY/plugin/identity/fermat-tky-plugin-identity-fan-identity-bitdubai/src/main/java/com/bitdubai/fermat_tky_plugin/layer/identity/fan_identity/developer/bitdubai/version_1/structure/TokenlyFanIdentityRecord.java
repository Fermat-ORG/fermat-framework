package com.bitdubai.fermat_tky_plugin.layer.identity.fan_identity.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_tky_api.all_definitions.enums.ExternalPlatform;
import com.bitdubai.fermat_tky_api.all_definitions.exceptions.ObjectNotSetException;
import com.bitdubai.fermat_tky_api.all_definitions.interfaces.User;
import com.bitdubai.fermat_tky_api.all_definitions.util.ObjectChecker;
import com.bitdubai.fermat_tky_api.layer.external_api.interfaces.music.MusicUser;
import com.bitdubai.fermat_tky_api.layer.identity.fan.interfaces.Fan;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 17/05/16.
 */
public class TokenlyFanIdentityRecord implements Fan, Serializable{

    private UUID id;
    private String tokenlyID;
    private String publicKey;
    private byte[] imageProfile;
    private String externalUserName;
    private String externalAccessToken;
    private String apiSecretKey;
    private String externalPassword;
    private ExternalPlatform externalPlatform;
    private String email;
    private List<String> artistsConnectedList;

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
    public TokenlyFanIdentityRecord(
            UUID id,
            String tokenlyID,
            String publicKey,
            byte[] imageProfile,
            String externalUserName,
            String externalAccessToken,
            String apiSecretKey,
            String externalPassword,
            ExternalPlatform externalPlatform,
            String email) {
        this.id = id;
        this.tokenlyID = tokenlyID;
        this.publicKey = publicKey;
        this.imageProfile = imageProfile;
        this.externalUserName = externalUserName;
        this.externalAccessToken = externalAccessToken;
        this.apiSecretKey = apiSecretKey;
        this.externalPassword = externalPassword;
        this.externalPlatform = externalPlatform;
        this.email = email;
    }

    /**
     *
     * @param user
     * @param id
     * @param publicKey
     * @param imageProfile
     * @param externalPlatform
     */
    public TokenlyFanIdentityRecord(
            User user,
            UUID id,
            String publicKey,
            byte[] imageProfile,
            ExternalPlatform externalPlatform) {
        this.id = id;
        this.tokenlyID = user.getTokenlyId();
        this.publicKey = publicKey;
        this.imageProfile = imageProfile;
        this.externalUserName = user.getUsername();
        this.externalAccessToken = user.getApiToken();
        this.apiSecretKey = user.getApiSecretKey();
        this.email = user.getEmail();
        this.externalPlatform = externalPlatform;
    }

    @Override
    public List<String> getConnectedArtists() {
        return this.artistsConnectedList;
    }

    @Override
    public void addNewArtistConnected(String userName) throws ObjectNotSetException {
        ObjectChecker.checkArgument(userName, "The user name is null");
        if(this.artistsConnectedList==null){
            this.artistsConnectedList = new ArrayList<>();
        }
        this.artistsConnectedList.add(userName);
    }

    @Override
    public String getArtistsConnectedStringList() {
        if(this.artistsConnectedList==null || this.artistsConnectedList.isEmpty()){
            return "";
        } else{
            return XMLParser.parseObject(this.artistsConnectedList);
        }
    }

    @Override
    public void addArtistConnectedList(String xmlStringList) {
        if(!(xmlStringList==null || xmlStringList.isEmpty())){
            List<String> proposedList = new ArrayList<>();
            proposedList = (List<String>) XMLParser.parseXML(xmlStringList, proposedList);
            this.artistsConnectedList = proposedList;
        }
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
