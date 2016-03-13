package com.bitdubai.fermat_tky_api.layer.external_api.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_tky_api.layer.external_api.exceptions.CantGetBotException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 11/03/16.
 */
public interface TokenlyApiManager extends FermatManager {

    /**
     * This method returns String that contains a swap bot by botId
     * @param botId represents the bot Id in swapbot site.
     * @return
     */
    com.bitdubai.fermat_tky_api.layer.external_api.interfaces.swapbot.Bot getBotByBotId(String botId) throws CantGetBotException;

    /**
     * This method returns String that contains a swap bot by tokenly username
     * @param username
     * @return
     * @throws CantGetBotException
     */
    com.bitdubai.fermat_tky_api.layer.external_api.interfaces.swapbot.Bot getBotBySwapbotUsername(String username) throws CantGetBotException;

}