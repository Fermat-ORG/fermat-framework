package com.bitdubai.fermat_tky_plugin.layer.song_wallet.tokenly.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_tky_api.all_definitions.enums.SongStatus;
import com.bitdubai.fermat_tky_api.all_definitions.exceptions.ObjectNotSetException;
import com.bitdubai.fermat_tky_api.all_definitions.util.ObjectChecker;
import com.bitdubai.fermat_tky_api.layer.song_wallet.exceptions.CantDeleteSongException;
import com.bitdubai.fermat_tky_api.layer.song_wallet.exceptions.CantDownloadSongException;
import com.bitdubai.fermat_tky_api.layer.song_wallet.exceptions.CantGetSongListException;
import com.bitdubai.fermat_tky_api.layer.song_wallet.exceptions.CantGetSongStatusException;
import com.bitdubai.fermat_tky_api.layer.song_wallet.exceptions.CantSynchronizeWithExternalAPIException;
import com.bitdubai.fermat_tky_api.layer.song_wallet.exceptions.CantUpdateSongStatusException;
import com.bitdubai.fermat_tky_api.layer.song_wallet.interfaces.SongWalletTokenlyManager;
import com.bitdubai.fermat_tky_api.layer.song_wallet.interfaces.WalletSong;
import com.bitdubai.fermat_tky_plugin.layer.song_wallet.tokenly.developer.bitdubai.version_1.database.TokenlySongWalletDao;
import com.bitdubai.fermat_tky_plugin.layer.song_wallet.tokenly.developer.bitdubai.version_1.exceptions.CantGetSongNameException;
import com.bitdubai.fermat_tky_plugin.layer.song_wallet.tokenly.developer.bitdubai.version_1.exceptions.CantGetSongTokenlyIdException;
import com.bitdubai.fermat_tky_api.layer.song_wallet.exceptions.CantUpdateSongDevicePathException;
import com.bitdubai.fermat_tky_plugin.layer.song_wallet.tokenly.developer.bitdubai.version_1.exceptions.CantGetStoragePathException;

import java.util.List;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 15/03/16.
 */
public class TokenlyWalletManager implements SongWalletTokenlyManager {

    /**
     * Represents the TokenlySongWalletDao.
     */
    TokenlySongWalletDao tokenlySongWalletDao;

    /**
     * Represents the TokenlyWalletSongVault
     */
    TokenlyWalletSongVault tokenlyWalletSongVault;

    /**
     * Constructor with parameters
     * @param tokenlySongWalletDao
     */
    public TokenlyWalletManager(
            TokenlySongWalletDao tokenlySongWalletDao,
            TokenlyWalletSongVault tokenlyWalletSongVault){
        this.tokenlySongWalletDao = tokenlySongWalletDao;
        this.tokenlyWalletSongVault = tokenlyWalletSongVault;
    }
    //TODO: implement this methods
    /**
     * This method returns a songs list by SongStatus enum
     * @param songStatus
     * @return
     * @throws CantGetSongListException
     */
    @Override
    public List<WalletSong> getSongsBySongStatus(SongStatus songStatus) throws
            CantGetSongListException {
        return this.tokenlySongWalletDao.getSongsBySongStatus(songStatus);
    }

    /**
     * This method returns a available songs list.
     * SongStatus: AVAILABLE
     * @return
     * @throws CantGetSongListException
     */
    @Override
    public List<WalletSong> getAvailableSongs() throws CantGetSongListException {
        return getSongsBySongStatus(SongStatus.AVAILABLE);
    }

    /**
     * This method returns a deleted songs list.
     * SongStatus: DELETED
     * @return
     * @throws CantGetSongListException
     */
    @Override
    public List<WalletSong> getDeletedSongs() throws CantGetSongListException {
        return getSongsBySongStatus(SongStatus.DELETED);
    }

    /**
     * This method returns a SongStatus by songId.
     * This Id is assigned by the Song Wallet Tokenly implementation, can be different to the
     * Tonkenly Id.
     * @param songId
     * @return
     * @throws CantGetSongStatusException
     */
    @Override
    public SongStatus getSongStatus(UUID songId) throws CantGetSongStatusException {
        return this.tokenlySongWalletDao.getSongStatus(songId);
    }

    /**
     * This method starts the synchronize songs process.
     * This checks the time passed between the method execution and the last update, if the actual
     * time - last updated is less than the default update interval, this method not synchronize
     * with external API.
     * @throws CantSynchronizeWithExternalAPIException
     */
    @Override
    public void synchronizeSongs() throws CantSynchronizeWithExternalAPIException {

    }

    /**
     * This method starts the synchronize songs process.
     * In this case, the synchronize process is started by the user.
     * This method doesn't check the last update field.
     * @throws CantSynchronizeWithExternalAPIException
     */
    @Override
    public void synchronizeSongsByUser() throws CantSynchronizeWithExternalAPIException {

    }

    /**
     * This method deletes a song from the wallet and the device storage.
     * This Id is assigned by the Song Wallet Tokenly implementation, can be different to the
     * Tonkenly Id.
     * @param songId
     * @throws CantDeleteSongException
     */
    @Override
    public void deleteSong(UUID songId) throws
            CantDeleteSongException,
            CantUpdateSongStatusException {
        try{
            //Get the Storage path
            String songName = this.tokenlySongWalletDao.getSongName(songId);
            ObjectChecker.checkArgument(songId, "The song name is null");
            //Delete from device storage
            this.tokenlyWalletSongVault.deleteSong(songName);
            //Update song status
            this.tokenlySongWalletDao.updateSongStatus(songId, SongStatus.DELETED);
        } catch (CantGetSongNameException e) {
            throw new CantDeleteSongException(
                    e,
                    "Deleting song by id:"+songId,
                    "Cannot get the tokenly id from Database");
        } catch (ObjectNotSetException e) {
            throw new CantDeleteSongException(
                    e,
                    "Deleting song by id:"+songId,
                    "The song name is probably null");
        }
    }

    /**
     * This method downloads a song to the wallet and the device storage.
     * This Id is assigned by the Song Wallet Tokenly implementation, can be different to the
     * Tokenly Id.
     * @param songId
     * @throws CantDownloadSongException
     * @throws CantUpdateSongDevicePathException
     */
    @Override
    public void downloadSong(UUID songId) throws
            CantDownloadSongException,
            CantUpdateSongDevicePathException,
            CantUpdateSongStatusException {
        try{
            //Getting tokenly Id.
            String tokenlyId = this.tokenlySongWalletDao.getSongTokenlyId(songId);
            ObjectChecker.checkArgument(tokenlyId, "The tokenly Id is null");
            //Request download song.
            String songPath = this.tokenlyWalletSongVault.downloadSong(tokenlyId);
            this.tokenlySongWalletDao.updateSongStoragePath(songId, songPath);
            //Update song status
            this.tokenlySongWalletDao.updateSongStatus(songId, SongStatus.AVAILABLE);
        } catch (CantGetSongTokenlyIdException e) {
            throw new CantDownloadSongException(
                    e,
                    "Downloading song by id:"+songId,
                    "Cannot get the tokenly id from Database");
        } catch (ObjectNotSetException e) {
            throw new CantDownloadSongException(
                    e,
                    "Downloading song by id:"+songId,
                    "The tokenly Id is null");
        }
    }
}
