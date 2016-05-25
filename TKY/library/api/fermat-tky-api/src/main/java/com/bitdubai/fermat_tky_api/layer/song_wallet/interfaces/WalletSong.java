package com.bitdubai.fermat_tky_api.layer.song_wallet.interfaces;

import com.bitdubai.fermat_tky_api.all_definitions.enums.SongStatus;
import com.bitdubai.fermat_tky_api.layer.external_api.interfaces.music.Song;

import java.io.Serializable;
import java.util.UUID;

/**
 * This interface represents a Song interface implementation for Song Wallet: Tokenly.
 * This contains the SongStatus registered in Song Wallet database.
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 15/03/16.
 */
public interface WalletSong extends Song, Serializable {

    /**
     * This method returns the WalletSong SongStatus
     * @return
     */
    SongStatus getSongStatus();

    /**
     * This method returns the WalletSong Id.
     * This Id is assigned by the Song Wallet Tokenly implementation, can be different to the
     * Tonkenly Id.
     * @return
     */
    UUID getSongId();

    /**
     * This method returns a XML String with the String[] associated to this object
     * @return
     */
    String getTokensXML();

    /**
     * This method returns a byte array that represents the song ready to be played.
     * @return
     */
    byte[] getSongBytes();

}
