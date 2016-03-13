package com.bitdubai.fermat_tky_plugin.layer.external_api.tokenly.developer.bitdubai.version_1.processors;

import com.bitdubai.fermat_tky_api.layer.external_api.interfaces.swapbot.Receipt;
import com.bitdubai.fermat_tky_api.layer.external_api.interfaces.swapbot.Swap;
import com.bitdubai.fermat_tky_plugin.layer.external_api.tokenly.developer.bitdubai.version_1.config.TokenlyBotJSonAttNames;
import com.bitdubai.fermat_tky_plugin.layer.external_api.tokenly.developer.bitdubai.version_1.config.TokenlySwapJSonAttNames;
import com.bitdubai.fermat_tky_plugin.layer.external_api.tokenly.developer.bitdubai.version_1.records.SwapRecord;
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
            counter++;
        }
        return swaps;
    }

    /**
     * This method return a swap from a JsonObject
     * @param jSonObject
     * @return
     */
    private static Swap getSwapFromJsonObject(JsonObject jSonObject){

        //Swap id
        String id = getStringFromJsonObject(
                jSonObject,
                TokenlySwapJSonAttNames.ID);
        //Swap transaction id
        String txId = getStringFromJsonObject(
                jSonObject,
                TokenlySwapJSonAttNames.TX_ID);
        //Swap state
        String state  = getStringFromJsonObject(
                jSonObject,
                TokenlySwapJSonAttNames.STATE);
        //Swap Receipt
        Receipt receipt = TokenlyReceiptProcessor.getReceiptFromJsonObject(jSonObject);
        //Swap created At
        Date createdAt = getDateFromJsonObject(
                jSonObject,
                TokenlySwapJSonAttNames.CREATED_AT);
        //Swap updated at
        Date updatedAt = getDateFromJsonObject(
                jSonObject,
                TokenlySwapJSonAttNames.UPDATED_AT);
        //Swap completed at.
        Date completedAt = getDateFromJsonObject(
                jSonObject,
                TokenlySwapJSonAttNames.COMPLETED_AT);
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
