package com.bitdubai.fermat_tky_api.layer.wallet_module.interfaces;

import com.bitdubai.fermat_api.layer.modules.ModuleSettingsImpl;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;
import com.bitdubai.fermat_tky_api.all_definitions.enums.SongStatus;
import com.bitdubai.fermat_tky_api.all_definitions.enums.TokenlyAPIStatus;
import com.bitdubai.fermat_tky_api.all_definitions.exceptions.CantConnectWithTokenlyException;
import com.bitdubai.fermat_tky_api.all_definitions.exceptions.TokenlyAPINotAvailableException;
import com.bitdubai.fermat_tky_api.all_definitions.exceptions.WrongTokenlyUserCredentialsException;
import com.bitdubai.fermat_tky_api.all_definitions.interfaces.User;
import com.bitdubai.fermat_tky_api.layer.external_api.exceptions.CantGetAlbumException;
import com.bitdubai.fermat_tky_api.layer.external_api.exceptions.CantGetBotException;
import com.bitdubai.fermat_tky_api.layer.external_api.exceptions.CantGetSongException;
import com.bitdubai.fermat_tky_api.layer.external_api.exceptions.CantGetUserException;
import com.bitdubai.fermat_tky_api.layer.external_api.interfaces.music.Album;
import com.bitdubai.fermat_tky_api.layer.external_api.interfaces.music.DownloadSong;
import com.bitdubai.fermat_tky_api.layer.external_api.interfaces.music.MusicUser;
import com.bitdubai.fermat_tky_api.layer.external_api.interfaces.music.Song;
import com.bitdubai.fermat_tky_api.layer.external_api.interfaces.swapbot.Bot;
import com.bitdubai.fermat_tky_api.layer.identity.fan.exceptions.CantListFanIdentitiesException;
import com.bitdubai.fermat_tky_api.layer.identity.fan.interfaces.Fan;
import com.bitdubai.fermat_tky_api.layer.song_wallet.exceptions.CantDeleteSongException;
import com.bitdubai.fermat_tky_api.layer.song_wallet.exceptions.CantDownloadSongException;
import com.bitdubai.fermat_tky_api.layer.song_wallet.exceptions.CantGetSongListException;
import com.bitdubai.fermat_tky_api.layer.song_wallet.exceptions.CantGetSongStatusException;
import com.bitdubai.fermat_tky_api.layer.song_wallet.exceptions.CantSynchronizeWithExternalAPIException;
import com.bitdubai.fermat_tky_api.layer.song_wallet.exceptions.CantUpdateSongDevicePathException;
import com.bitdubai.fermat_tky_api.layer.song_wallet.exceptions.CantUpdateSongStatusException;
import com.bitdubai.fermat_tky_api.layer.song_wallet.interfaces.WalletSong;
import com.bitdubai.fermat_tky_api.layer.wallet_module.FanWalletPreferenceSettings;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

/**
 * Created by Alexander Jimenez (alex_jimenez76@hotmail.com) on 3/17/16.
 * Edited by Miguel Payarez on 30/03/16.
 */
public interface FanWalletModule extends
        ModuleManager<
                FanWalletPreferenceSettings,
                ActiveActorIdentityInformation>,
        ModuleSettingsImpl<FanWalletPreferenceSettings>, Serializable {

    //Song Wallet
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

    /**
     * This method reports to wallet manager that the ser wants to download a song
     */
    void cancelDownload();

    //Fan Identity

    /**
     * Through the method <code>listIdentitiesFromCurrentDeviceUser</code> we can get all the fan
     * identities linked to the current logged device user.
     * @return
     * @throws CantListFanIdentitiesException
     */
    List<Fan> listIdentitiesFromCurrentDeviceUser() throws CantListFanIdentitiesException;

    //External API
    /**
     * This method returns String that contains a swap bot by botId
     * @param botId represents the bot Id in swapbot site.
     * @return
     */
    Bot getBotByBotId(String botId) throws CantGetBotException, CantConnectWithTokenlyException;

    /**
     * This method returns String that contains a swap bot by tokenly username
     * @param username
     * @return
     * @throws CantGetBotException
     */
    Bot getBotBySwapbotUsername(String username) throws CantGetBotException, CantConnectWithTokenlyException;

    /**
     * This method returns a Tokenly Album.
     * @return
     * @throws CantGetAlbumException
     */
    Album[] getAlbums() throws CantGetAlbumException, CantConnectWithTokenlyException;

    /**
     * This method returns a download song by song Id.
     * @param id
     * @return
     */
    DownloadSong getDownloadSongBySongId(String id) throws CantGetSongException, CantConnectWithTokenlyException;

    /**
     * This method returns a User object by a username and key pair
     * @param username Tokenly username.
     * @param userKey user password
     * @return
     * @throws CantGetUserException
     * @throws ExecutionException
     * @throws InterruptedException
     * @throws WrongTokenlyUserCredentialsException
     */
    User validateTokenlyUser(String username, String userKey) throws
            CantGetUserException,
            ExecutionException,
            InterruptedException,
            WrongTokenlyUserCredentialsException;

    /**
     * This method returns a song array. This songs are provided by the Tokenly protected API, only
     * authenticated users can get the songs.
     * @param musicUser
     * @return
     */
    Song[] getSongsByAuthenticatedUser(MusicUser musicUser) throws CantGetAlbumException;

    /**
     * This method returns a song. This song is provided by the Tokenly protected API, only
     * authenticated users can get the song.
     * @param musicUser
     * @param tokenlySongId
     * @return
     * @throws CantGetSongException
     */
    Song getSongByAuthenticatedUser(MusicUser musicUser, String tokenlySongId)
            throws CantGetSongException;

    /**
     * This method checks if the Tokenly Music API is available.
     * @return
     * @throws TokenlyAPINotAvailableException
     */
    TokenlyAPIStatus getMusicAPIStatus() throws TokenlyAPINotAvailableException;

    /**
     * This method checks if the Tokenly Swapbot API is available.
     * @return
     * @throws TokenlyAPINotAvailableException
     */
    TokenlyAPIStatus getSwapBotAPIStatus() throws TokenlyAPINotAvailableException;

}
