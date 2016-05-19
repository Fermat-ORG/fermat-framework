package com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.interfaces;

import com.bitdubai.fermat_art_api.layer.sub_app_module.community.ArtCommunityInformation;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Alexander Jimenez (alex_jimenez76@hotmail.com) on 4/6/16.
 */
public interface ArtistCommunityInformation extends ArtCommunityInformation, Serializable {

    /**
     * The method <code>listArtistWallets</code> returns the list of the public Artist wallets
     * @return
     */
    List listArtistWallets();

}
