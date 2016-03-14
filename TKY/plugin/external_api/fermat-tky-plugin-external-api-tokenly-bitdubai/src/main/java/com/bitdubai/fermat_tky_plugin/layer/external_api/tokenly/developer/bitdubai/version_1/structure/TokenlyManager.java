package com.bitdubai.fermat_tky_plugin.layer.external_api.tokenly.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_tky_api.layer.external_api.exceptions.CantGetBotException;
import com.bitdubai.fermat_tky_api.layer.external_api.interfaces.swapbot.Bot;
import com.bitdubai.fermat_tky_api.layer.external_api.interfaces.TokenlyApiManager;
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
}
