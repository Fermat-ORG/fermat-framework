package com.bitdubai.fermat_tky_plugin.layer.external_api.tokenly.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_tky_api.all_definitions.enums.TokenlyAPIStatus;
import com.bitdubai.fermat_tky_api.all_definitions.exceptions.CantConnectWithTokenlyException;
import com.bitdubai.fermat_tky_api.all_definitions.exceptions.ObjectNotSetException;
import com.bitdubai.fermat_tky_api.all_definitions.exceptions.TokenlyAPINotAvailableException;
import com.bitdubai.fermat_tky_api.all_definitions.exceptions.WrongTokenlyUserCredentialsException;
import com.bitdubai.fermat_tky_api.all_definitions.interfaces.User;
import com.bitdubai.fermat_tky_api.all_definitions.util.ObjectChecker;
import com.bitdubai.fermat_tky_api.layer.external_api.exceptions.CantGetAlbumException;
import com.bitdubai.fermat_tky_api.layer.external_api.exceptions.CantGetBotException;
import com.bitdubai.fermat_tky_api.layer.external_api.exceptions.CantGetSongException;
import com.bitdubai.fermat_tky_api.layer.external_api.exceptions.CantGetUserException;
import com.bitdubai.fermat_tky_api.layer.external_api.interfaces.music.Album;
import com.bitdubai.fermat_tky_api.layer.external_api.interfaces.music.DownloadSong;
import com.bitdubai.fermat_tky_api.layer.external_api.interfaces.music.MusicUser;
import com.bitdubai.fermat_tky_api.layer.external_api.interfaces.music.Song;
import com.bitdubai.fermat_tky_api.layer.external_api.interfaces.swapbot.Bot;
import com.bitdubai.fermat_tky_api.layer.external_api.interfaces.TokenlyApiManager;
import com.bitdubai.fermat_tky_plugin.layer.external_api.tokenly.developer.bitdubai.version_1.processors.TokenlyAPIStatusProcessor;
import com.bitdubai.fermat_tky_plugin.layer.external_api.tokenly.developer.bitdubai.version_1.processors.music.TokenlyAlbumProcessor;
import com.bitdubai.fermat_tky_plugin.layer.external_api.tokenly.developer.bitdubai.version_1.processors.music.TokenlyDownloadSongProcessor;
import com.bitdubai.fermat_tky_plugin.layer.external_api.tokenly.developer.bitdubai.version_1.processors.music.TokenlyMusicUserProcessor;
import com.bitdubai.fermat_tky_plugin.layer.external_api.tokenly.developer.bitdubai.version_1.processors.music.TokenlySongProcessor;
import com.bitdubai.fermat_tky_plugin.layer.external_api.tokenly.developer.bitdubai.version_1.processors.swapbot.TokenlySwapBotProcessor;

import java.io.Serializable;
import java.util.concurrent.ExecutionException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 04/03/16.
 */
public class TokenlyManager implements TokenlyApiManager, Serializable {

    /**
     * This method returns a Tokenly Bot by bot Id.
     * @param botId represents the bot Id in swapbot site.
     * @return
     * @throws CantGetBotException
     */
    @Override
    public Bot getBotByBotId(String botId) throws
            CantGetBotException,
            CantConnectWithTokenlyException {
        Bot bot = TokenlySwapBotProcessor.getBotByBotId(botId);
        return bot;
    }

    /**
     * This method returns a Tokenly Bot by bot username.
     * @param username represents the bot Id in swapbot site.
     * @return
     * @throws CantGetBotException
     */
    @Override
    public Bot getBotBySwapbotUsername(String username) throws
            CantGetBotException,
            CantConnectWithTokenlyException {
        Bot bot = TokenlySwapBotProcessor.getBotByTokenlyUsername(username);
        return bot;
    }

    /**
     * This method returns a Tokenly Album.
     * @return
     * @throws CantGetAlbumException
     */
    @Override
    public Album[] getAlbums() throws CantGetAlbumException, CantConnectWithTokenlyException {
        Album[] albums = TokenlyAlbumProcessor.getAlbums();
        return albums;
    }

    @Override
    public DownloadSong getDownloadSongBySongId(String id) throws
            CantGetSongException,
            CantConnectWithTokenlyException {
        DownloadSong downloadSong = TokenlyDownloadSongProcessor.getDownloadSongById(id);
        return downloadSong;
    }

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
    @Override
    public User validateTokenlyUser(String username, String userKey) throws
            CantGetUserException,
            ExecutionException,
            InterruptedException,
            WrongTokenlyUserCredentialsException {
        User user = TokenlyMusicUserProcessor.getAuthenticatedMusicUser(username, userKey);
        return user;
    }

    /**
     * This method returns a song array. This songs are provided by the Tokenly protected API, only
     * authenticated users can get the songs.
     * @param musicUser
     * @return
     */
    @Override
    public Song[] getSongsByAuthenticatedUser(MusicUser musicUser) throws CantGetAlbumException{
        try{
            //Validate if all the important musicUser fields are not null.
            ObjectChecker.checkArguments(
                    new String[]{
                            musicUser.getUsername(),
                            musicUser.getApiToken(),
                            musicUser.getApiSecretKey()});
            //Get songs from Tokenly protected API
            Song[] songs = TokenlySongProcessor.getSongsByAuthenticatedUser(musicUser);
            return songs;
        } catch (ObjectNotSetException e) {
            throw new CantGetAlbumException("Any MusicUser argument is null");
        }
    }

    /**
     * This method returns a song. This song is provided by the Tokenly protected API, only
     * authenticated users can get the song.
     * @param musicUser
     * @param tokenlySongId
     * @return
     * @throws CantGetSongException
     */
    @Override
    public Song getSongByAuthenticatedUser(
            MusicUser musicUser,
            String tokenlySongId) throws CantGetSongException{
        try{
            //Validate if all the important musicUser fields are not null.
            ObjectChecker.checkArguments(
                    new String[]{
                            musicUser.getUsername(),
                            musicUser.getApiToken(),
                            musicUser.getApiSecretKey()});
            //Get songs from Tokenly protected API
            Song song = TokenlySongProcessor.getSongByAuthenticatedUser(
                    musicUser,
                    tokenlySongId);
            return song;
        } catch (ObjectNotSetException e) {
            throw new CantGetSongException("Any MusicUser argument is null");
        }
    }

    /**
     * This method checks if the Tokenly Music API is available.
     * @return
     * @throws TokenlyAPINotAvailableException
     */
    @Override
    public TokenlyAPIStatus getMusicAPIStatus() throws TokenlyAPINotAvailableException {
        return TokenlyAPIStatusProcessor.getMusicAPIStatus();
    }

    /**
     * This method checks if the Tokenly Swapbot API is available.
     * @return
     * @throws TokenlyAPINotAvailableException
     */
    @Override
    public TokenlyAPIStatus getSwapBotAPIStatus() throws TokenlyAPINotAvailableException {
        return TokenlyAPIStatusProcessor.getSwapBotAPIStatus();
    }
}
