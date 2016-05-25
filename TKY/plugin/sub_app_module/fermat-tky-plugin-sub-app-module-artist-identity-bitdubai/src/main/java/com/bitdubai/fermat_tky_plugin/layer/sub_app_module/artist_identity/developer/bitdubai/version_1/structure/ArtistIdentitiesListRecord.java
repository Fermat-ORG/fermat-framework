package com.bitdubai.fermat_tky_plugin.layer.sub_app_module.artist_identity.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_tky_api.layer.identity.artist.interfaces.Artist;
import com.bitdubai.fermat_tky_api.layer.sub_app_module.artist.interfaces.ArtistIdentitiesList;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 17/05/16.
 */
public class ArtistIdentitiesListRecord implements ArtistIdentitiesList, Serializable {

    private final List<Artist> artistList;

    public ArtistIdentitiesListRecord(List<Artist> artistList) {
        this.artistList = artistList;
    }

    @Override
    public List<Artist> getFanIdentitiesList() {
        return artistList;
    }
}
