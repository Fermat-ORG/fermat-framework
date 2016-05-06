package com.bitdubai.reference_wallet.fan_wallet.util;

import com.bitdubai.fermat_tky_api.all_definitions.enums.ExternalPlatform;
import com.bitdubai.fermat_tky_api.all_definitions.exceptions.ObjectNotSetException;
import com.bitdubai.fermat_tky_api.layer.external_api.interfaces.music.MusicUser;
import com.bitdubai.fermat_tky_api.layer.identity.fan.interfaces.Fan;

import java.util.List;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 13/04/16.
 */
public class TestFan implements Fan {
    @Override
    public List<String> getConnectedArtists() {
        return null;
    }

    @Override
    public void addNewArtistConnected(String userName) throws ObjectNotSetException {

    }

    @Override
    public String getArtistsConnectedStringList() {
        return null;
    }

    @Override
    public void addArtistConnectedList(String xmlStringList) {

    }

    @Override
    public UUID getId() {
        return null;
    }

    @Override
    public String getPublicKey() {
        return null;
    }

    @Override
    public byte[] getProfileImage() {
        return new byte[0];
    }

    @Override
    public void setNewProfileImage(byte[] imageBytes) {

    }

    @Override
    public ExternalPlatform getExternalPlatform() {
        return null;
    }

    @Override
    public MusicUser getMusicUser() {
        return new MockedMusicuser();
    }

    @Override
    public String getUserPassword() {
        return null;
    }

    @Override
    public String getTokenlyId() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public String getEmail() {
        return null;
    }

    @Override
    public String getApiToken() {
        return null;
    }

    @Override
    public String getApiSecretKey() {
        return null;
    }
}
