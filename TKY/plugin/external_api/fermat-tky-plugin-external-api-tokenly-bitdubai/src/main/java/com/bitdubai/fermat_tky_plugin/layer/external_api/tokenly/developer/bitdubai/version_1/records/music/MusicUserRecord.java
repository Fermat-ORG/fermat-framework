package com.bitdubai.fermat_tky_plugin.layer.external_api.tokenly.developer.bitdubai.version_1.records.music;

import com.bitdubai.fermat_tky_api.all_definitions.interfaces.User;
import com.bitdubai.fermat_tky_api.layer.external_api.interfaces.music.MusicUser;

import java.io.Serializable;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 23/03/16.
 */
public class MusicUserRecord implements MusicUser, Serializable {

    String id;
    String username;
    String email;
    String apiToken;
    String apiSecretKey;

    /**
     * Constructor with parameters.
     * @param id
     * @param username
     * @param email
     * @param apiToken
     * @param apiSecretKey
     */
    public MusicUserRecord(
            String id,
            String username,
            String email,
            String apiToken,
            String apiSecretKey) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.apiToken = apiToken;
        this.apiSecretKey = apiSecretKey;
    }

    /**
     * Constructor with User object parameter.
     * @param user
     */
    public MusicUserRecord(
            User user){
        this.id = user.getTokenlyId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.apiToken = user.getApiToken();
        this.apiSecretKey = user.getApiSecretKey();
    }

    /**
     * This method returns the tokenly id.
     * @return
     */
    @Override
    public String getTokenlyId() {
        return this.id;
    }

    /**
     * This method returns the tokenly username.
     * @return
     */
    @Override
    public String getUsername() {
        return this.username;
    }

    /**
     * This method returns the email registered in tokenly account.
     * @return
     */
    @Override
    public String getEmail() {
        return this.email;
    }

    /**
     * This method returns the user Api Token from Tokenly public API.
     * @return
     */
    @Override
    public String getApiToken() {
        return this.apiToken;
    }

    /**
     * This method returns the user Api Token from Tokenly public API.
     * @return
     */
    @Override
    public String getApiSecretKey() {
        return this.apiSecretKey;
    }

    @Override
    public String toString() {
        return "MusicUserRecord{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", apiToken='" + apiToken + '\'' +
                ", apiSecretKey='" + apiSecretKey + '\'' +
                '}';
    }
}
