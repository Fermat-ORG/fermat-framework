package com.bitdubai.fermat_tky_plugin.layer.external_api.tokenly.developer.bitdubai.version_1.config;

import java.util.HashMap;

/**
 * This class contains the basic configurations about management
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 04/03/16.
 */
public class TokenlyConfiguration {

    /**
     * Represents the basic URL call for Tokenly swapbot API
     */
    public static final String URL_TOKENLY_SWAPBOT_API = "http://swapbot.tokenly.com/api/v1/public/";

    /**
     * Represents the basic URL call for Tokenly Music API
     */
    public static final String URL_TOKENLY_MUSIC_API = "https://music-stage.tokenly.com/api/v1/music/";

    /**
     * Represents the basic URL call for Tokenly music manager authentication.
     */
    public static final String URL_TOKENLY_MUSIC_AUTHENTICATION_API = "https://music-stage.tokenly.com/api/v1/account/login";

    /**
     * Represents the basic URL to call the protected API to get the songs by a authenticated user.
     */
    public static final String URL_TOKENLY_MUSIC_API_SONGS_BY_AUTHENTICATED_USER = "https://music-stage.tokenly.com/api/v1/music/mysongs";

    /**
     * Represents the basic URL to call the protected API to get one song by a authenticated user.
     */
    public static final String URL_TOKENLY_MUSIC_API_ONE_SONG_BY_AUTHENTICATED_USER = "https://music-stage.tokenly.com/api/v1/music/song/download/";

    /**
     * This method returns a hashMap with the parameters required by the protected Tokenly API to
     * authenticate a Tokenly user.
     * @return
     */
    public static final HashMap<String, String> getMusicAuthenticationParameters(){
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("curl", "-X");
        parameters.put("Content-Type", "application/x-www-form-urlencoded");
        parameters.put("Accept", "application/json");
        return parameters;
    }

}
