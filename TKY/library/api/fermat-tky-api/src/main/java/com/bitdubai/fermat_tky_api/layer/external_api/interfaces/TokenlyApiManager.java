package com.bitdubai.fermat_tky_api.layer.external_api.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
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

import java.util.concurrent.ExecutionException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 11/03/16.
 */
public interface TokenlyApiManager extends FermatManager {

    /**
     * This method returns String that contains a swap bot by botId
     * @param botId represents the bot Id in swapbot site.
     * @return
     */
    Bot getBotByBotId(String botId) throws CantGetBotException;

    /**
     * This method returns String that contains a swap bot by tokenly username
     * @param username
     * @return
     * @throws CantGetBotException
     */
    Bot getBotBySwapbotUsername(String username) throws CantGetBotException;

    /**
     * This method returns a Tokenly Album.
     * @return
     * @throws CantGetAlbumException
     */
    Album[] getAlbums() throws CantGetAlbumException;

    /**
     * This method returns a download song by song Id.
     * @param id
     * @return
     */
    DownloadSong getDownloadSongBySongId(String id) throws CantGetSongException;

    /**
     * This method returns a User object by a username and key pair.
     * @param username
     * @param userKey
     * @return
     */
    User validateTokenlyUser(String username, String userKey) throws CantGetUserException, ExecutionException, InterruptedException;

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
    Song getSongByAuthenticatedUser(MusicUser musicUser, String tokenlySongId) throws CantGetSongException;

}