package com.bitdubai.reference_wallet.fan_wallet.util;

import com.bitdubai.fermat_tky_api.layer.external_api.interfaces.music.MusicUser;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 13/04/16.
 */
public class MockedMusicuser implements MusicUser {


    @Override
    public String getTokenlyId() {
        return null;
    }

    @Override
    public String getUsername() {
        return "pereznator";
    }

    @Override
    public String getEmail() {
        return "darkpriestrelative@gmail.com";
    }

    @Override
    public String getApiToken() {
        return "Tvn1yFjTsisMHnlI";
    }

    @Override
    public String getApiSecretKey() {
        return "K0fW5UfvrrEVQJQnK27FbLgtjtWHjsTsq3kQFB6Y";
    }
}
