package com.bitdubai.fermat_tky_api.layer.song_wallet.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_tky_api.all_definitions.enums.SongStatus;
import com.bitdubai.fermat_tky_api.layer.song_wallet.exceptions.CantDeleteSongException;
import com.bitdubai.fermat_tky_api.layer.song_wallet.exceptions.CantDownloadSongException;
import com.bitdubai.fermat_tky_api.layer.song_wallet.exceptions.CantGetSongListException;
import com.bitdubai.fermat_tky_api.layer.song_wallet.exceptions.CantGetSongStatusException;
import com.bitdubai.fermat_tky_api.layer.song_wallet.exceptions.CantSynchronizeWithExternalAPIException;
import com.bitdubai.fermat_tky_api.layer.song_wallet.exceptions.CantUpdateSongDevicePathException;
import com.bitdubai.fermat_tky_api.layer.song_wallet.exceptions.CantUpdateSongStatusException;

import java.util.List;
import java.util.UUID;

/**
 * This public interface contains all the methods to implement in Song Wallet Tokenly.
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 15/03/16.
 */
public interface SongWalletTokenlyManager extends FermatManager{

    /**
     * This method returns a songs list by SongStatus enum
     * @param songStatus
     * @return
     * @throws CantGetSongListException
     */
    List<WalletSong> getSongsBySongStatus(SongStatus songStatus) throws CantGetSongListException;

    /**
     * This method returns a available songs list.
     * SongStatus: AVAILABLE
     * @return
     * @throws CantGetSongListException
     */
    List<WalletSong> getAvailableSongs() throws CantGetSongListException;

    /**
     * This method returns a deleted songs list.
     * SongStatus: DELETED
     * @return
     * @throws CantGetSongListException
     */
    List<WalletSong> getDeletedSongs() throws CantGetSongListException;

    /**
     * This method returns a SongStatus by songId.
     * This Id is assigned by the Song Wallet Tokenly implementation, can be different to the
     * Tonkenly Id.
     * @param songId
     * @return
     * @throws CantGetSongStatusException
     */
    SongStatus getSongStatus(UUID songId) throws CantGetSongStatusException;

    /**
     * This method starts the synchronize songs process.
     * This checks the time passed between the method execution and the last update, if the actual
     * time - last updated is less than the default update interval, this method not synchronize
     * with external API.
     * @throws CantSynchronizeWithExternalAPIException
     */
    void synchronizeSongs() throws CantSynchronizeWithExternalAPIException;

    /**
     * This method starts the synchronize songs process.
     * In this case, the synchronize process is started by the user.
     * This method doesn't check the last update field.
     * @throws CantSynchronizeWithExternalAPIException
     */
    void synchronizeSongsByUser() throws CantSynchronizeWithExternalAPIException;

    /**
     * This method deletes a song from the wallet and the device storage.
     * This Id is assigned by the Song Wallet Tokenly implementation, can be different to the
     * Tonkenly Id.
     * @param songId
     * @throws CantDeleteSongException
     */
    void deleteSong(UUID songId) throws
            CantDeleteSongException,
            CantUpdateSongStatusException;

    /**
     * This method downloads a song to the wallet and the device storage.
     * This Id is assigned by the Song Wallet Tokenly implementation, can be different to the
     * Tonkenly Id.
     * @param songId
     * @throws CantDownloadSongException
     */
    void downloadSong(UUID songId) throws
            CantDownloadSongException,
            CantUpdateSongDevicePathException,
            CantUpdateSongStatusException;

}
