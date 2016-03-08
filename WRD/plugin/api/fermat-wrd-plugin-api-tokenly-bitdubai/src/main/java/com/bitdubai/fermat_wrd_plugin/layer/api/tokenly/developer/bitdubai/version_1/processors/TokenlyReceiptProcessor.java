package com.bitdubai.fermat_wrd_plugin.layer.api.tokenly.developer.bitdubai.version_1.processors;

import com.bitdubai.fermat_wrd_api.layer.api.tokenly.interfaces.Receipt;
import com.bitdubai.fermat_wrd_plugin.layer.api.tokenly.developer.bitdubai.version_1.config.TokenlyReceiptJSonAttNames;
import com.bitdubai.fermat_wrd_plugin.layer.api.tokenly.developer.bitdubai.version_1.records.ReceiptRecord;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.sql.Date;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 07/03/16.
 */
public class TokenlyReceiptProcessor extends AbstractTokenlyProcessor {

    /**
     * This method returns a Receipt from a JsonObject.
     * @param jsonObject
     * @return
     */
    public static Receipt getReceiptFromJsonObject(JsonObject jsonObject){

        Gson gSonProcessor = new Gson();
        //Receipt quantity in.
        double quantityIn = getLongFromJsonObject(
                jsonObject,
                TokenlyReceiptJSonAttNames.QUANTITY_IN);
        //Receipt asset in
        String assetIn = getStringFromJsonObject(
                jsonObject,
                TokenlyReceiptJSonAttNames.ASSET_IN);
        //Receipt quantity out
        double quantityOut = getLongFromJsonObject(
                jsonObject,
                TokenlyReceiptJSonAttNames.QUANTITY_OUT);
        //Receipt asset out
        String assetOut = getStringFromJsonObject(
                jsonObject,
                TokenlyReceiptJSonAttNames.ASSET_OUT);
        //Receipt type
        String type = getStringFromJsonObject(
                jsonObject,
                TokenlyReceiptJSonAttNames.TYPE);
        //Receipt destination
        String destination = getStringFromJsonObject(
                jsonObject,
                TokenlyReceiptJSonAttNames.DESTINATION);
        //Receipt tx in.
        String txIn = getStringFromJsonObject(
                jsonObject,
                TokenlyReceiptJSonAttNames.TX_ID_IN);
        //Receipt tx out.
        String txOut= getStringFromJsonObject(
                jsonObject,
                TokenlyReceiptJSonAttNames.TX_ID_OUT);
        //Receipt confirmations
        int confirmations = (int) getLongFromJsonObject(
                jsonObject,
                TokenlyReceiptJSonAttNames.CONFIRMATIONS);
        //Receipt confirmations out.
        int confirmationsOut = (int) getLongFromJsonObject(
                jsonObject,
                TokenlyReceiptJSonAttNames.CONFIRMATIONS);
        //Receipt timestamp
        long timestamp = getLongFromJsonObject(
                jsonObject,
                TokenlyReceiptJSonAttNames.TIMESTAMP);
        //Receipt completed at
        Date completedAt = getDateFromJsonObject(
                jsonObject,
                TokenlyReceiptJSonAttNames.COMPLETED_AT);
        //Create receipt record.
        Receipt receipt = new ReceiptRecord(
                quantityIn,
                assetIn,
                quantityOut,
                assetOut,
                type,
                destination,
                txIn,
                txOut,
                confirmations,
                confirmationsOut,
                timestamp,
                completedAt);
        return receipt;

    }

}
