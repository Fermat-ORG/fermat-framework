package com.bitdubai.fermat_tky_plugin.layer.wallet_module.fan.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_tky_api.all_definitions.enums.SongStatus;
import com.bitdubai.fermat_tky_api.layer.song_wallet.exceptions.CantDeleteSongException;
import com.bitdubai.fermat_tky_api.layer.song_wallet.exceptions.CantDownloadSongException;
import com.bitdubai.fermat_tky_api.layer.song_wallet.exceptions.CantGetSongListException;
import com.bitdubai.fermat_tky_api.layer.song_wallet.exceptions.CantGetSongStatusException;
import com.bitdubai.fermat_tky_api.layer.song_wallet.exceptions.CantSynchronizeWithExternalAPIException;
import com.bitdubai.fermat_tky_api.layer.song_wallet.interfaces.SongWalletTokenlyManager;
import com.bitdubai.fermat_tky_api.layer.song_wallet.interfaces.WalletSong;

import java.util.List;
import java.util.UUID;

/**
 * Created by Alexander Jimenez (alex_jimenez76@hotmail.com) on 3/16/16.
 */
public class FanWalletModuleManager implements SongWalletTokenlyManager {
    @Override
    public List<WalletSong> getSongsBySongStatus(SongStatus songStatus) throws CantGetSongListException {
        return null;
    }

    @Override
    public List<WalletSong> getAvailableSongs() throws CantGetSongListException {
        return null;
    }

    @Override
    public List<WalletSong> getDeletedSongs() throws CantGetSongListException {
        return null;
    }

    @Override
    public SongStatus getSongStatus(UUID songId) throws CantGetSongStatusException {
        return null;
    }

    @Override
    public void synchronizeSongs() throws CantSynchronizeWithExternalAPIException {

    }

    @Override
    public void synchronizeSongsByUser() throws CantSynchronizeWithExternalAPIException {

    }

    @Override
    public void deleteSong(UUID songId) throws CantDeleteSongException {

    }

    @Override
    public void downloadSong(UUID songId) throws CantDownloadSongException {

    }
}
