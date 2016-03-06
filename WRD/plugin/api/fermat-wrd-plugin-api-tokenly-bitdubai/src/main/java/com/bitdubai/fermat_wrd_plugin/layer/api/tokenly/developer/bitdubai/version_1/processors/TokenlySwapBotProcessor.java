package com.bitdubai.fermat_wrd_plugin.layer.api.tokenly.developer.bitdubai.version_1.processors;

import com.bitdubai.fermat_wrd_api.all_definitions.interfaces.RemoteJSonProcessor;
import com.bitdubai.fermat_wrd_api.layer.api.tokenly.exceptions.CantGetBotException;
import com.bitdubai.fermat_wrd_api.layer.api.tokenly.exceptions.CantGetJSonObjectException;
import com.bitdubai.fermat_wrd_api.layer.api.tokenly.interfaces.Bot;
import com.google.gson.JsonElement;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 03/03/16.
 */
public class TokenlySwapBotProcessor {

    private static String swabotTokenlyURL= com.bitdubai.fermat_wrd_plugin.layer.api.tokenly.developer.bitdubai.version_1.config.TokenlyConfiguration.URL_TOKENLY_SWAPBOT_API;

    public static Bot getBotURLByBotId(String botId) throws CantGetBotException {
        String requestedURL=swabotTokenlyURL+"bot/"+botId;
        try{
            JsonElement jSonBot = RemoteJSonProcessor.getJSonObject(requestedURL);
            //TODO: get bot data from JSon
            return null;
        } catch (CantGetJSonObjectException e) {
            throw new CantGetBotException(
                    e,
                    "Getting swap bot from given Id",
                    "Cannot get JSon from tokenly API using URL "+requestedURL);
        }

    }

}
