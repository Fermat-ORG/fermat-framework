package com.bitdubai.fermat_tky_plugin.layer.external_api.tokenly.developer.bitdubai.version_1.processors.swapbot;

import com.bitdubai.fermat_tky_api.all_definitions.exceptions.CantConnectWithTokenlyException;
import com.bitdubai.fermat_tky_api.all_definitions.exceptions.CantGetJSonObjectException;
import com.bitdubai.fermat_tky_api.all_definitions.interfaces.RemoteJSonProcessor;
import com.bitdubai.fermat_tky_api.layer.external_api.exceptions.CantGetBotException;
import com.bitdubai.fermat_tky_api.layer.external_api.interfaces.swapbot.Bot;
import com.bitdubai.fermat_tky_api.layer.external_api.interfaces.swapbot.ImageDetails;
import com.bitdubai.fermat_tky_api.layer.external_api.interfaces.swapbot.Swap;
import com.bitdubai.fermat_tky_api.layer.external_api.interfaces.swapbot.TokenlyBalance;
import com.bitdubai.fermat_tky_plugin.layer.external_api.tokenly.developer.bitdubai.version_1.config.music.TokenlyBotJSonAttNames;
import com.bitdubai.fermat_tky_plugin.layer.external_api.tokenly.developer.bitdubai.version_1.config.TokenlyConfiguration;
import com.bitdubai.fermat_tky_plugin.layer.external_api.tokenly.developer.bitdubai.version_1.config.music.TokenlySwapJSonAttNames;
import com.bitdubai.fermat_tky_plugin.layer.external_api.tokenly.developer.bitdubai.version_1.processors.*;
import com.bitdubai.fermat_tky_plugin.layer.external_api.tokenly.developer.bitdubai.version_1.records.swapbot.ImageDetailsRecord;
import com.bitdubai.fermat_tky_plugin.layer.external_api.tokenly.developer.bitdubai.version_1.records.swapbot.SwapBotRecord;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.sql.Date;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 03/03/16.
 */
public class TokenlySwapBotProcessor extends AbstractTokenlyProcessor {

    private static String swabotTokenlyURL= TokenlyConfiguration.URL_TOKENLY_SWAPBOT_API;
    private static String defaultURLLogo = "https://redeem.tokenly.com/images/tokenly-logo.png";

    /**
     * This method returns a bot from tokenly API by a request URL.
     * @param botId
     * @return
     * @throws CantGetBotException
     */
    public static Bot getBotByBotId(String botId) throws
            CantGetBotException,
            CantConnectWithTokenlyException {
        //Request URL to get a bot by tokenly Id.
        String requestedURL=swabotTokenlyURL+"bot/"+botId;
        try{
            JsonObject jSonObject = RemoteJSonProcessor.getJSonObject(requestedURL);
            return getBotFromJsonObject(jSonObject);
        } catch (CantGetJSonObjectException e) {
            throw new CantGetBotException(
                    e,
                    "Getting swap bot from given Id",
                    "Cannot get JSon from tokenly API using URL "+requestedURL);
        }

    }

    /**
     * This method returns a bot from tokenly API by a request URL.
     * @param username
     * @return
     * @throws CantGetBotException
     */
    public static Bot getBotByTokenlyUsername(String username) throws
            CantGetBotException,
            CantConnectWithTokenlyException {
        //Request URL to get a bot by tokenly Id.
        String requestedURL=swabotTokenlyURL+"bots?username="+username;
        String id;
        String botUrl;
        try{
            JsonArray jSonArray = RemoteJSonProcessor.getJSonArray(requestedURL);
            if(jSonArray==null){
                return null;
            }
            /**
             * TODO:
             * The following line is only for testing, right now, 18/04/2016 we created a testing swapbot in
             * swapbot.tokenly.com site, but our bot is not showing in public TOKENLY API (we believe this
             * happening because we don't pay the subscription) but this bot can be get from this URL
             * https://swapbot.tokenly.com/api/v1/public/bot/2115b238-70d4-4619-a9f5-41e2c82473c0
             * So, for now, we gonna hardcode the bot id and bot url for user mordorteam, our bot,
             * we will remove this logic when the public API shows our bot.
             */
            if(!username.equals("mordorteam")){
                JsonObject jSonObject = jSonArray.
                        get(0).
                        getAsJsonObject();
                id = getStringFromJsonObject(jSonObject, TokenlyBotJSonAttNames.ID);
                botUrl = getStringFromJsonObject(jSonObject, TokenlyBotJSonAttNames.BOT_URL);
            }else{
                //Mordor team swapbot Id
                id="2115b238-70d4-4619-a9f5-41e2c82473c0";
                //Mordor team swapbot URL
                botUrl = "https://swapbot.tokenly.com/bot/mordorteam/mordortest";
            }

            Bot bot = getBotByBotId(id);
            bot.setBotUrl(botUrl);
            return bot;
        } catch (CantGetJSonObjectException e) {
            throw new CantGetBotException(
                    e,
                    "Getting swap bot from given Id",
                    "Cannot get JSon from tokenly API using URL "+requestedURL);
        }

    }

    /**
     * This method returns a bot from a JsonObject.
     * @param jSonObject
     * @return
     */
    private static Bot getBotFromJsonObject(JsonObject jSonObject){

        //Bot Id
        String id = getStringFromJsonObject(jSonObject, TokenlyBotJSonAttNames.ID);
        //Bot name
        String name = getStringFromJsonObject(jSonObject, TokenlyBotJSonAttNames.NAME);
        //Bot address
        String address = getStringFromJsonObject(jSonObject, TokenlyBotJSonAttNames.ADDRESS);
        //Bot username
        String username = getStringFromJsonObject(jSonObject, TokenlyBotJSonAttNames.USERNAME);
        //Bot description
        String description = getStringFromJsonObject(jSonObject, TokenlyBotJSonAttNames.DESCRIPTION);
        //Bot description HTML
        String descriptionHtml = getStringFromJsonObject(jSonObject, TokenlyBotJSonAttNames.DESCRIPTION_HTML);
        //Bot Background image details
        ImageDetails backgroundImageDetails;
        try{
            backgroundImageDetails =
                    TokenlyImageDetailsProcessor.getImageDetailsFromJsonObject(
                            jSonObject.getAsJsonObject(TokenlyBotJSonAttNames.BACKGROUND_DETAILS));
        } catch(ClassCastException e){
            //Empty ImageDetails
            backgroundImageDetails = new ImageDetailsRecord(
                    "emptyId",
                    defaultURLLogo,
                    defaultURLLogo,
                    defaultURLLogo,
                    defaultURLLogo,
                    "",
                    10,
                    "TokenlyLogo",
                    null);
        }
        //Bot logo image details
        ImageDetails logoImageDetails;
        try{
            logoImageDetails =
                    TokenlyImageDetailsProcessor.getImageDetailsFromJsonObject(
                            jSonObject.getAsJsonObject(TokenlyBotJSonAttNames.LOGO_DETAILS));
        } catch(ClassCastException e){
            //Empty ImageDetails
            logoImageDetails = new ImageDetailsRecord(
                    "emptyId",
                    defaultURLLogo,
                    defaultURLLogo,
                    defaultURLLogo,
                    defaultURLLogo,
                    "",
                    10,
                    "TokenlyLogo",
                    null);
        }
        //Bot background overlay setting
        String[] backgroudOverlaySettings;
        try{
            backgroudOverlaySettings = getArrayStringFromJsonObject(
                    jSonObject,
                    TokenlyBotJSonAttNames.BACKGROUND_OVERLAY_SETTINGS);
        } catch(Exception e){
            backgroudOverlaySettings = new String[]{""};
        }
        //Bot swaps
        Swap[] swaps = TokenlySwapProcessor.getSwapArrayFromJsonObject(jSonObject);
        //Bot balances
        TokenlyBalance[] tokenlyBalances;
        try {
            tokenlyBalances = TokenlyBalanceProcessor.
                    getTokenlyBalancesFromJsonObject(
                            jSonObject.getAsJsonObject(TokenlyBotJSonAttNames.BALANCES));
        }catch(ClassCastException e){
            //Empty ImageDetails
            tokenlyBalances = new TokenlyBalance[0];
        }
        //Bot all balances by type
        TokenlyBalance[][] allTokenlyBalancesByType;
        try{
            allTokenlyBalancesByType = TokenlyBalanceProcessor.
                    getTokenlyBalancesByType(
                            jSonObject.getAsJsonObject(TokenlyBotJSonAttNames.ALL_BALANCES_BY_TYPE));
        }catch(ClassCastException | NullPointerException e){
            //Empty ImageDetails
            allTokenlyBalancesByType = new TokenlyBalance[0][0];
        }
        //Bot return fee.
        float returnFee = (float) getDoubleFromJsonObject(jSonObject, TokenlyBotJSonAttNames.RETURN_FEE);
        //Bot state.
        String state = getStringFromJsonObject(jSonObject, TokenlyBotJSonAttNames.STATE);
        //Bot return fee.
        long confirmationsRequired =
                getLongFromJsonObject(jSonObject, TokenlyBotJSonAttNames.RETURN_FEE);
        //Bot refund after blocks.
        long refundsAfterBlocks = getLongFromJsonObject(jSonObject, TokenlyBotJSonAttNames.REFUND_AFTER_BLOCKS);
        //Bot created date
        Date createdAt = getDateFromJsonObject(jSonObject, TokenlySwapJSonAttNames.CREATED_AT);
        //Bot hash
        String hash = getStringFromJsonObject(jSonObject, TokenlyBotJSonAttNames.HASH);
        //Create bot
        Bot bot = new SwapBotRecord(
                id,
                name,
                address,
                username,
                description,
                descriptionHtml,
                backgroundImageDetails,
                logoImageDetails,
                backgroudOverlaySettings,
                swaps,
                tokenlyBalances,
                allTokenlyBalancesByType,
                returnFee,
                state,
                confirmationsRequired,
                refundsAfterBlocks,
                createdAt,
                hash);

        return bot;
    }

}
