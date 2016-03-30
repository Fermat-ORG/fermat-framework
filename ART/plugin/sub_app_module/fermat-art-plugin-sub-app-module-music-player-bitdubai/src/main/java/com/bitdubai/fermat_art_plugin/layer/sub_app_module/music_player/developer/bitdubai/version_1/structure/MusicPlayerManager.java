package com.bitdubai.fermat_art_plugin.layer.sub_app_module.music_player.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_art_api.layer.sub_app_module.music_player.MusicPlayerModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_tky_api.all_definitions.enums.SongStatus;
import com.bitdubai.fermat_tky_api.layer.identity.fan.interfaces.Fan;
import com.bitdubai.fermat_tky_api.layer.song_wallet.exceptions.CantDeleteSongException;
import com.bitdubai.fermat_tky_api.layer.song_wallet.exceptions.CantDownloadSongException;
import com.bitdubai.fermat_tky_api.layer.song_wallet.exceptions.CantGetSongListException;
import com.bitdubai.fermat_tky_api.layer.song_wallet.exceptions.CantGetSongStatusException;
import com.bitdubai.fermat_tky_api.layer.song_wallet.exceptions.CantSynchronizeWithExternalAPIException;
import com.bitdubai.fermat_tky_api.layer.song_wallet.exceptions.CantUpdateSongDevicePathException;
import com.bitdubai.fermat_tky_api.layer.song_wallet.exceptions.CantUpdateSongStatusException;
import com.bitdubai.fermat_tky_api.layer.song_wallet.interfaces.WalletSong;

import java.util.List;
import java.util.UUID;

/**
 * Created by Alexander Jimenez (alex_jimenez76@hotmail.com) on 3/29/16.
 */
public class MusicPlayerManager implements MusicPlayerModuleManager {
    private final ErrorManager errorManager;
    private final MusicPlayerModuleManager musicPlayerModuleManager;

    public MusicPlayerManager(ErrorManager errorManager, MusicPlayerModuleManager musicPlayerModuleManager) {
        this.errorManager = errorManager;
        this.musicPlayerModuleManager = musicPlayerModuleManager;
    }

    @Override
    public List<WalletSong> getSongsBySongStatus(SongStatus songStatus) throws CantGetSongListException {
        return musicPlayerModuleManager.getSongsBySongStatus(songStatus);
    }

    @Override
    public List<WalletSong> getAvailableSongs() throws CantGetSongListException {
        return musicPlayerModuleManager.getAvailableSongs();
    }

    @Override
    public List<WalletSong> getDeletedSongs() throws CantGetSongListException {
        return musicPlayerModuleManager.getDeletedSongs();
    }

    @Override
    public SongStatus getSongStatus(UUID songId) throws CantGetSongStatusException {
        return musicPlayerModuleManager.getSongStatus(songId);
    }

    @Override
    public void synchronizeSongs(Fan fanIdentity) throws CantSynchronizeWithExternalAPIException {
        musicPlayerModuleManager.synchronizeSongs(fanIdentity);
    }

    @Override
    public void synchronizeSongsByUser(Fan fanIdentity) throws CantSynchronizeWithExternalAPIException {
        musicPlayerModuleManager.synchronizeSongsByUser(fanIdentity);
    }

    @Override
    public void deleteSong(UUID songId) throws CantDeleteSongException, CantUpdateSongStatusException {
        musicPlayerModuleManager.deleteSong(songId);
    }

    @Override
    public void downloadSong(UUID songId) throws CantDownloadSongException, CantUpdateSongDevicePathException, CantUpdateSongStatusException {
        musicPlayerModuleManager.downloadSong(songId);
    }
}
