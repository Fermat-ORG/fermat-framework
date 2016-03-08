package com.bitdubai.fermat_wrd_plugin.layer.api.tokenly.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_wrd_api.layer.api.tokenly.exceptions.CantGetBotException;
import com.bitdubai.fermat_wrd_api.layer.api.tokenly.interfaces.Bot;
import com.bitdubai.fermat_wrd_api.layer.api.tokenly.interfaces.TokenlyApiManager;
import com.bitdubai.fermat_wrd_plugin.layer.api.tokenly.developer.bitdubai.version_1.processors.TokenlySwapBotProcessor;

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
    public Bot getBotURLByBotId(String botId) throws CantGetBotException {
        Bot bot = TokenlySwapBotProcessor.getBotURLByBotId(botId);
        return bot;
    }
}
