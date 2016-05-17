package com.bitdubai.fermat_tky_plugin.layer.identity.fan_identity.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.DeviceDirectory;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginBinaryFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_tky_api.all_definitions.enums.ExternalPlatform;
import com.bitdubai.fermat_tky_api.all_definitions.exceptions.ObjectNotSetException;
import com.bitdubai.fermat_tky_api.all_definitions.interfaces.User;
import com.bitdubai.fermat_tky_api.all_definitions.util.ObjectChecker;
import com.bitdubai.fermat_tky_api.layer.external_api.interfaces.music.MusicUser;
import com.bitdubai.fermat_tky_api.layer.identity.fan.interfaces.Fan;
import com.bitdubai.fermat_tky_plugin.layer.identity.fan_identity.developer.bitdubai.version_1.TokenlyFanIdentityPluginRoot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Gabriel Araujo (gabe_512@hotmail.com) on 10/03/16.
 */
public class TokenlyFanIdentityImp implements
        Fan, Serializable {

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
    public TokenlyFanIdentityImp(
            User user,
            UUID id,
            String publicKey,
            byte[] imageProfile,
            ExternalPlatform externalPlatform,
            PluginFileSystem pluginFileSystem,
            UUID pluginId) {
        this.id = id;
        this.tokenlyID = user.getTokenlyId();
        this.publicKey = publicKey;
        this.imageProfile = imageProfile;
        this.externalUserName = user.getUsername();
        this.externalAccessToken = user.getApiToken();
        this.apiSecretKey = user.getApiSecretKey();
        this.email = user.getEmail();
        this.externalPlatform = externalPlatform;
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
    public TokenlyFanIdentityImp(
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
    public TokenlyFanIdentityImp(
            UUID id,
            String tokenlyID,
            String publicKey,
            byte[] imageProfile,
            String externalUserName,
            String externalAccessToken,
            String apiSecretKey,
            ExternalPlatform externalPlatform,
            String email,
            PluginFileSystem pluginFileSystem,
            UUID pluginId) {
        this.id = id;
        this.tokenlyID = tokenlyID;
        this.publicKey = publicKey;
        this.imageProfile = imageProfile;
        this.externalUserName = externalUserName;
        this.externalAccessToken = externalAccessToken;
        this.apiSecretKey = apiSecretKey;
        this.externalPlatform = externalPlatform;
        this.email = email;
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
                    TokenlyFanIdentityPluginRoot.TOKENLY_FAN_IDENTITY_PROFILE_IMAGE + "_" + publicKey,
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
    public String getUsername() {
        return externalUserName;
    }

    @Override
    public String getApiToken() {
        return externalAccessToken;
    }

    public void setId(UUID id) {
        this.id = id;
    }


    public void setUserName(String externalUserName) {
        this.externalUserName = externalUserName;
    }


    public void setApiToken(String externalAccessToken) {
        this.externalAccessToken = externalAccessToken;
    }

    @Override
    public ExternalPlatform getExternalPlatform() {
        return externalPlatform;
    }

    @Override
    public MusicUser getMusicUser() {
        /**
         * TODO: harcoded User. I'll use this for testing, please, Gabriel, remove this when this
         * method is full implemented.
         */
        //TODO: Hardoced User
        /*MusicUser hardocedUser = new TokenlyUserImp(
                "18873727-da0f-4b50-a213-cc40c6b4562d",
                "pereznator",
                "darkpriestrelative@gmail.com",
                "Tvn1yFjTsisMHnlI",
                "K0fW5UfvrrEVQJQnK27FbLgtjtWHjsTsq3kQFB6Y");
        return hardocedUser;*/
        //TODO: this is the real implementation
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
        return externalPassword;
    }

    public void setExternalPlatform(ExternalPlatform externalPlatform) {
        this.externalPlatform = externalPlatform;
    }

    @Override
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getExternalPassword() {
        return externalPassword;
    }

    public void setExternalPassword(String externalPassword) {
        this.externalPassword = externalPassword;
    }

    @Override
    public String getApiSecretKey() {
        return apiSecretKey;
    }

    public void setApiSecretKey(String apiSecretKey) {
        this.apiSecretKey = apiSecretKey;
    }

    @Override
    public String getTokenlyId() {
        return tokenlyID;
    }

    public void setTokenlyID(String tokenlyID) {
        this.tokenlyID = tokenlyID;
    }

    /**
     * This method returns a list with the username from the artists connected.
     * @return
     */
    @Override
    public List<String> getConnectedArtists() {
        //TODO: to implement and remove this hardcode
        /*List<String> hardcodedList=new ArrayList<>();
        hardcodedList.add("TatianaMoroz");
        hardcodedList.add("adam");
        hardcodedList.add("mordorteam");
        return hardcodedList;*/
        //TODO: this is the real implementation.
        if(this.artistsConnectedList==null || this.artistsConnectedList.isEmpty()){
            return new ArrayList<>();
        } else{
            return this.artistsConnectedList;
        }
    }

    /**
     * This method persist the username in the fan identity.
     * @param userName
     */
    public void addNewArtistConnected(String userName) throws ObjectNotSetException {
        ObjectChecker.checkArgument(userName, "The user name is null");
        if(this.artistsConnectedList==null){
            this.artistsConnectedList = new ArrayList<>();
        }
        this.artistsConnectedList.add(userName);
    }

    /**
     * This method sets the artist connected list from a XML String.
     * @param xmlStringList
     */
    public void addArtistConnectedList(String xmlStringList){
        if(!(xmlStringList==null || xmlStringList.isEmpty())){
            List<String> proposedList = new ArrayList<>();
            proposedList = (List<String>) XMLParser.parseXML(xmlStringList, proposedList);
            this.artistsConnectedList = proposedList;
        }
    }

    /**
     * This method returns the XML String representation from the Artist Connected List.
     * @return
     */
    public String getArtistsConnectedStringList(){
        if(this.artistsConnectedList==null || this.artistsConnectedList.isEmpty()){
            return "";
        } else{
            return XMLParser.parseObject(this.artistsConnectedList);
        }
    }
}
