package com.bitdubai.fermat_tky_plugin.layer.external_api.tokenly.developer.bitdubai.version_1.processors.music;

import com.bitdubai.fermat_tky_api.all_definitions.interfaces.User;
import com.bitdubai.fermat_tky_api.layer.external_api.exceptions.CantGetUserException;
import com.bitdubai.fermat_tky_api.layer.external_api.interfaces.music.MusicUser;
import com.bitdubai.fermat_tky_plugin.layer.external_api.tokenly.developer.bitdubai.version_1.config.TokenlyConfiguration;
import com.bitdubai.fermat_tky_plugin.layer.external_api.tokenly.developer.bitdubai.version_1.processors.TokenlyAuthenticatedUserProcessor;
import com.bitdubai.fermat_tky_plugin.layer.external_api.tokenly.developer.bitdubai.version_1.records.music.MusicUserRecord;

import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 23/03/16.
 */
public class TokenlyMusicUserProcessor extends TokenlyAuthenticatedUserProcessor {


    private static ExecutorService executorService;

    public static MusicUser getAuthenticatedMusicUser(
            final String username,
            final String password) throws CantGetUserException, ExecutionException, InterruptedException {
        final String url = TokenlyConfiguration.URL_TOKENLY_MUSIC_AUTHENTICATION_API;
        //Get default parameters
        final HashMap<String, String> parameters = TokenlyConfiguration.getMusicAuthenticationParameters();
        executorService = Executors.newSingleThreadExecutor();
        final User[] user = new User[1];
        //Ejecuta el thread con una función callable que devuelve el objeto computado por el thread.
        Future<MusicUser> result = executorService.submit(new Callable<MusicUser>() {
            public MusicUser call() throws Exception {
                user[0] = getAuthenticatedUser(
                        url,
                        parameters,
                        username,
                        password);
                return new MusicUserRecord(user[0]);
            }
        });

        return result.get();
    }
}
