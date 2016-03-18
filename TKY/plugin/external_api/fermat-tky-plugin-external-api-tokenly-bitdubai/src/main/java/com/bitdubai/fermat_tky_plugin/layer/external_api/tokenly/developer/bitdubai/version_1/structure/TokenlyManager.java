package com.bitdubai.fermat_tky_plugin.layer.external_api.tokenly.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_tky_api.layer.external_api.exceptions.CantGetAlbumException;
import com.bitdubai.fermat_tky_api.layer.external_api.exceptions.CantGetBotException;
import com.bitdubai.fermat_tky_api.layer.external_api.exceptions.CantGetSongException;
import com.bitdubai.fermat_tky_api.layer.external_api.interfaces.music.Album;
import com.bitdubai.fermat_tky_api.layer.external_api.interfaces.music.DownloadSong;
import com.bitdubai.fermat_tky_api.layer.external_api.interfaces.swapbot.Bot;
import com.bitdubai.fermat_tky_api.layer.external_api.interfaces.TokenlyApiManager;
import com.bitdubai.fermat_tky_plugin.layer.external_api.tokenly.developer.bitdubai.version_1.processors.music.TokenlyAlbumProcessor;
import com.bitdubai.fermat_tky_plugin.layer.external_api.tokenly.developer.bitdubai.version_1.processors.music.TokenlyDownloadSongProcessor;
import com.bitdubai.fermat_tky_plugin.layer.external_api.tokenly.developer.bitdubai.version_1.processors.swapbot.TokenlySwapBotProcessor;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 04/03/16.
 */
public class TokenlyManager implements TokenlyApiManager {

    /**
     * This method returns a Tokenly Bot by bot Id.
     * @param botId represents the bot Id in swapbot site.
     * @return
     * @throws CantGetBotException
     */
    @Override
    public Bot getBotByBotId(String botId) throws CantGetBotException {
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
    public Bot getBotBySwapbotUsername(String username) throws CantGetBotException {
        Bot bot = TokenlySwapBotProcessor.getBotByTokenlyUsername(username);
        return bot;
    }

    /**
     * This method returns a Tokenly Album.
     * @return
     * @throws CantGetAlbumException
     */
    @Override
    public Album[] getAlbums() throws CantGetAlbumException {
        Album[] albums = TokenlyAlbumProcessor.getAlbums();
        return albums;
    }

    @Override
    public DownloadSong getDownloadSongBySongId(String id) throws CantGetSongException {
        DownloadSong downloadSong = TokenlyDownloadSongProcessor.getDownloadSongById(id);
        return downloadSong;
    }

    /**
     * This method returns if a username and key pair si valid (temporal method)
     * @param username
     * @param userKey
     * @return
     */
    @Override
    public boolean isTokenlyAccessVaild(String username, String userKey) {
        //TODO: to implement
        return true;
    }
}
