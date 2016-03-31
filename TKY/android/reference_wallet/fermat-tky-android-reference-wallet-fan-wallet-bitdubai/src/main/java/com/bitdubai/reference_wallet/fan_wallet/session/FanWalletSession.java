package com.bitdubai.reference_wallet.fan_wallet.session;

import com.bitdubai.fermat_android_api.layer.definition.wallet.abstracts.AbstractFermatSession;

/**
 * Created by Miguel Payarez on 14/03/16.
 */
public class FanWalletSession extends AbstractFermatSession {

    public static final String CATALOG="artist_catalog";
    public static final String MY_SONG="song_list";

    //TODO wait to define method in module
/*    public List<catalog> getArtistCatalog() {
        Object data = getData(CATALOG);
        return (data != null) ? (List<catalog>) data : null;
    }

    public List<songs> getSongList() {
        Object data = getData(MY_SONG);
        return (data != null) ? (List<song>) data : null;
    }*/


}
