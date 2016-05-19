package com.bitdubai.fermat_tky_plugin.layer.external_api.tokenly.developer.bitdubai.version_1.processors.music;

import com.bitdubai.fermat_tky_api.all_definitions.exceptions.WrongTokenlyUserCredentialsException;
import com.bitdubai.fermat_tky_api.all_definitions.interfaces.User;
import com.bitdubai.fermat_tky_api.layer.external_api.exceptions.CantGetUserException;
import com.bitdubai.fermat_tky_api.layer.external_api.interfaces.music.MusicUser;
import com.bitdubai.fermat_tky_plugin.layer.external_api.tokenly.developer.bitdubai.version_1.config.TokenlyConfiguration;
import com.bitdubai.fermat_tky_plugin.layer.external_api.tokenly.developer.bitdubai.version_1.processors.TokenlyAuthenticatedUserProcessor;
import com.bitdubai.fermat_tky_plugin.layer.external_api.tokenly.developer.bitdubai.version_1.records.music.MusicUserRecord;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 23/03/16.
 */
public class TokenlyMusicUserProcessor extends TokenlyAuthenticatedUserProcessor implements Serializable {

    public static MusicUser getAuthenticatedMusicUser(
            String username,
            String password) throws
            CantGetUserException,
            WrongTokenlyUserCredentialsException {
        String url = TokenlyConfiguration.URL_TOKENLY_MUSIC_AUTHENTICATION_API;
        //Get default parameters
        HashMap<String, String> parameters = TokenlyConfiguration.getMusicAuthenticationParameters();
        User user = getAuthenticatedUser(
                url,
                parameters,
                username,
                password);
        MusicUser musicUser = new MusicUserRecord(user);
        return musicUser;
    }
}
