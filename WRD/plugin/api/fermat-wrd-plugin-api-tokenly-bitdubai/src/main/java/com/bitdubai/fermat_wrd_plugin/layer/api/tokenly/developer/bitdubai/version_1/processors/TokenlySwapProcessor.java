package com.bitdubai.fermat_wrd_plugin.layer.api.tokenly.developer.bitdubai.version_1.processors;

import com.bitdubai.fermat_wrd_api.layer.api.tokenly.interfaces.Receipt;
import com.bitdubai.fermat_wrd_api.layer.api.tokenly.interfaces.Swap;
import com.bitdubai.fermat_wrd_plugin.layer.api.tokenly.developer.bitdubai.version_1.config.TokenlyBotJSonAttNames;
import com.bitdubai.fermat_wrd_plugin.layer.api.tokenly.developer.bitdubai.version_1.config.TokenlySwapJSonAttNames;
import com.bitdubai.fermat_wrd_plugin.layer.api.tokenly.developer.bitdubai.version_1.records.SwapRecord;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.sql.Date;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 07/03/16.
 */
public class TokenlySwapProcessor extends AbstractTokenlyProcessor {

    /**
     * This method returns a swap array from a given JsonObject.
     * @param jSonObject
     * @return
     */
    public static Swap[] getSwapArrayFromJsonObject(JsonObject jSonObject){
        JsonElement jsonElement = jSonObject.get(TokenlyBotJSonAttNames.SWAPS);
        JsonArray jsonArray = jsonElement.getAsJsonArray().getAsJsonArray();
        Swap[] swaps = new Swap[jsonArray.size()];
        int counter = 0;
        for(JsonElement jSonElementArray : jsonArray){
            JsonObject jsonObjectFromArray = jSonElementArray.getAsJsonObject();
            swaps[counter] = getSwapFromJsonObject(jsonObjectFromArray);
        }
        return swaps;
    }

    /**
     * This method return a swap from a JsonObject
     * @param jSonObject
     * @return
     */
    private static Swap getSwapFromJsonObject(JsonObject jSonObject){

        Gson gSonProcessor = new Gson();
        //Swap id
        String id = gSonProcessor.fromJson(
                getStringFromJsonObject(jSonObject, TokenlySwapJSonAttNames.ID),
                String.class);
        //Swap transaction id
        String txId = gSonProcessor.fromJson(
                getStringFromJsonObject(jSonObject, TokenlySwapJSonAttNames.TX_ID),
                String.class);
        //Swap state
        String state  = gSonProcessor.fromJson(
                getStringFromJsonObject(jSonObject, TokenlySwapJSonAttNames.STATE),
                String.class);
        //Swap Receipt
        Receipt receipt = TokenlyReceiptProcessor.getReceiptFromJsonObject(jSonObject);
        //Swap created At
        Date createdAt = gSonProcessor.fromJson(
                getDateStringFromJsonObject(jSonObject, TokenlySwapJSonAttNames.CREATED_AT),
                Date.class);
        //Swap updated at
        Date updatedAt = gSonProcessor.fromJson(
                getDateStringFromJsonObject(jSonObject, TokenlySwapJSonAttNames.UPDATED_AT),
                Date.class);
        //Swap completed at.
        Date completedAt = gSonProcessor.fromJson(
                getDateStringFromJsonObject(jSonObject, TokenlySwapJSonAttNames.COMPLETED_AT),
                Date.class);
        //Created swap
        Swap swap = new SwapRecord(
                id,
                txId,
                state,
                receipt,
                createdAt,
                updatedAt,
                completedAt);
        return swap;
    }

}
