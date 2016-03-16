package com.bitdubai.fermat_tky_plugin.layer.sub_app_module.artist_identity.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_tky_api.layer.identity.Artist.interfaces.Artist;

import java.util.List;

/**
 * Created by Alexander Jimenez (alex_jimenez76@hotmail.com) on 3/15/16.
 */
public class ArtistIdentityManager implements com.bitdubai.fermat_tky_api.layer.identity.Artist.interfaces.ArtistIdentityManager{
    @Override
    public List<Artist> listIdentitiesFromCurrentDeviceUser() {
        return null;
    }

    @Override
    public Artist createArtistIdentity(String alias, byte[] imageBytes) {
        return null;
    }

    @Override
    public Artist getArtistIdentity(String publicKey) {
        return null;
    }

    @Override
    public void publishIdentity(String publicKey) {

    }

    @Override
    public void hideIdentity(String publicKey) {

    }
}
