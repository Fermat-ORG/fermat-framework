package com.bitdubai.fermat_tky_plugin.layer.external_api.tokenly.developer.bitdubai.version_1.config;

import java.util.HashMap;

/**
 * This class contains the basic configurations about management
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 04/03/16.
 */
public class TokenlyConfiguration {

    /**
     * This String add the word -stage to all the URL showed in this configuration.
     * This word can chooses between tokenly stage (test) servers and production servers.
     * For use production servers, please, put this String as a empty string.
     */
    public static final String USE_STAGE_SERVERS = "-stage";

    /**
     * Represents the basic URL call for Tokenly swapbot API
     */
    public static final String URL_TOKENLY_SWAPBOT_API = "http://swapbot"+USE_STAGE_SERVERS+".tokenly.com/api/v1/public/";

    /**
     * Represents the basic URL call for Tokenly Music API
     */
    public static final String URL_TOKENLY_MUSIC_API = "https://music"+USE_STAGE_SERVERS+".tokenly.com/api/v1/music/";

    /**
     * Represents the basic URL call for Tokenly music manager authentication.
     */
    public static final String URL_TOKENLY_MUSIC_AUTHENTICATION_API = "https://music"+USE_STAGE_SERVERS+".tokenly.com/api/v1/account/login";

    /**
     * Represents the basic URL to call the protected API to get the songs by a authenticated user.
     */
    public static final String URL_TOKENLY_MUSIC_API_SONGS_BY_AUTHENTICATED_USER = "https://music"+USE_STAGE_SERVERS+".tokenly.com/api/v1/music/mysongs";

    /**
     * Represents the basic URL to call the protected API to get one song by a authenticated user.
     */
    public static final String URL_TOKENLY_MUSIC_API_ONE_SONG_BY_AUTHENTICATED_USER = "https://music"+USE_STAGE_SERVERS+".tokenly.com/api/v1/music/song/download/";

    /**
     * Represents the wrong credentials HTTP response code.
     */
    public static final int TOKENLY_WRONG_CREDENTIALS_HTTP_RESPONSE_CODE = 403;

    /**
     * Represents the basic URL to call all the albums availables.
     * In this version is used to check Music API status.
     */
    public static final String URL_TOKENLY_MUSIC_ALL_ALBUMS_AVAILABLE = "https://music"+USE_STAGE_SERVERS+".tokenly.com/api/v1/music/catalog/albums";

    /**
     * This represents the developer team username in Tokenly.
     * In this version is used to check Swapbot API status.
     */
    //public static final String URL_TOKENLY_SWAPBOT_DEVELOPER_TEAM = "https://swapbot.tokenly.com/api/v1/public/bots?username=mordorteam";
    public static final String URL_TOKENLY_SWAPBOT_DEVELOPER_TEAM = "https://swapbot"+USE_STAGE_SERVERS+".tokenly.com/api/v1/public/bots?username=mordorian";

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
