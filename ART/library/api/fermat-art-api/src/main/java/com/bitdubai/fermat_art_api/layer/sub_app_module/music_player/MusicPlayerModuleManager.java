package com.bitdubai.fermat_art_api.layer.sub_app_module.music_player;

import com.bitdubai.fermat_api.layer.modules.ModuleSettingsImpl;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;
import com.bitdubai.fermat_tky_api.all_definitions.enums.SongStatus;
import com.bitdubai.fermat_tky_api.layer.external_api.exceptions.CantGetSongException;
import com.bitdubai.fermat_tky_api.layer.external_api.interfaces.music.MusicUser;
import com.bitdubai.fermat_tky_api.layer.identity.fan.interfaces.Fan;
import com.bitdubai.fermat_tky_api.layer.song_wallet.exceptions.CantDeleteSongException;
import com.bitdubai.fermat_tky_api.layer.song_wallet.exceptions.CantDownloadSongException;
import com.bitdubai.fermat_tky_api.layer.song_wallet.exceptions.CantGetSongListException;
import com.bitdubai.fermat_tky_api.layer.song_wallet.exceptions.CantGetSongStatusException;
import com.bitdubai.fermat_tky_api.layer.song_wallet.exceptions.CantSynchronizeWithExternalAPIException;
import com.bitdubai.fermat_tky_api.layer.song_wallet.exceptions.CantUpdateSongDevicePathException;
import com.bitdubai.fermat_tky_api.layer.song_wallet.exceptions.CantUpdateSongStatusException;
import com.bitdubai.fermat_tky_api.layer.song_wallet.interfaces.WalletSong;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * Created by Alexander Jimenez (alex_jimenez76@hotmail.com) on 3/29/16.
 */
public interface MusicPlayerModuleManager
        extends ModuleManager<MusicPlayerPreferenceSettings,ActiveActorIdentityInformation>,
        ModuleSettingsImpl<MusicPlayerPreferenceSettings>, Serializable {
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
     * @param fanIdentity
     * @throws CantSynchronizeWithExternalAPIException
     */
    void synchronizeSongs(Fan fanIdentity) throws CantSynchronizeWithExternalAPIException;

    /**
     * This method starts the synchronize songs process.
     * In this case, the synchronize process is started by the user.
     * This method doesn't check the last update field.
     * @param fanIdentity
     * @throws CantSynchronizeWithExternalAPIException
     */
    void synchronizeSongsByUser(Fan fanIdentity) throws CantSynchronizeWithExternalAPIException;

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
     * @param musicUser
     * @throws CantDownloadSongException
     */
    void downloadSong(UUID songId, MusicUser musicUser) throws
            CantDownloadSongException,
            CantUpdateSongDevicePathException,
            CantUpdateSongStatusException;

    /**
     * This method returns a WalletSong object that includes a byte array that represents the song
     * ready to be played.
     * @param songId
     * @return
     * @throws CantGetSongException
     */
    WalletSong getSongWithBytes(UUID songId) throws CantGetSongException;
}
