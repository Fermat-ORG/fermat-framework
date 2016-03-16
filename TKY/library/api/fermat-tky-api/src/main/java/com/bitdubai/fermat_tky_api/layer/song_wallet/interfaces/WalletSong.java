package com.bitdubai.fermat_tky_api.layer.song_wallet.interfaces;

import com.bitdubai.fermat_tky_api.all_definitions.enums.SongStatus;
import com.bitdubai.fermat_tky_api.layer.external_api.interfaces.music.Song;

/**
 * This interface represents a Song interface implementation for Song Wallet: Tokenly.
 * This contains the SongStatus registered in Song Wallet database.
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 15/03/16.
 */
public interface WalletSong extends Song {

    /**
     * This method returns the WalletSong SongStatus
     * @return
     */
    SongStatus getSongStatus();

}
