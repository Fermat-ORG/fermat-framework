package com.bitdubai.fermat_wrd_api.layer.api.tokenly.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 03/03/16.
 */
public interface TokenlyManager extends FermatManager {

    /**
     * This method returns String that contains the botUrl by botId
     * @param botId represents the bot Id in swapbot site.
     * @return
     */
    String getBotURLByBotId(String botId);

}
