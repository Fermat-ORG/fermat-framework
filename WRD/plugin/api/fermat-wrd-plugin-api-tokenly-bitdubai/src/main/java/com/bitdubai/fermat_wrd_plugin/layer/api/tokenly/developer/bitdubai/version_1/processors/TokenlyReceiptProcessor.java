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
        double quantityIn = gSonProcessor.fromJson(
                getLongStringFromJsonObject(jsonObject, TokenlyReceiptJSonAttNames.QUANTITY_IN),
                Long.class);
        //Receipt asset in
        String assetIn = gSonProcessor.fromJson(
                getStringFromJsonObject(jsonObject, TokenlyReceiptJSonAttNames.ASSET_IN),
                String.class);
        //Receipt quantity out
        double quantityOut = gSonProcessor.fromJson(
                getLongStringFromJsonObject(jsonObject, TokenlyReceiptJSonAttNames.QUANTITY_OUT),
                Long.class);
        //Receipt asset out
        String assetOut = gSonProcessor.fromJson(
                getStringFromJsonObject(jsonObject, TokenlyReceiptJSonAttNames.ASSET_OUT),
                String.class);
        //Receipt type
        String type = gSonProcessor.fromJson(
                getStringFromJsonObject(jsonObject, TokenlyReceiptJSonAttNames.TYPE),
                String.class);
        //Receipt destination
        String destination = gSonProcessor.fromJson(
                getStringFromJsonObject(jsonObject, TokenlyReceiptJSonAttNames.DESTINATION),
                String.class);
        //Receipt tx in.
        String txIn = gSonProcessor.fromJson(
                getStringFromJsonObject(jsonObject, TokenlyReceiptJSonAttNames.TX_ID_IN),
                String.class);
        //Receipt tx out.
        String txOut= gSonProcessor.fromJson(
                getStringFromJsonObject(jsonObject, TokenlyReceiptJSonAttNames.TX_ID_OUT),
                String.class);
        //Receipt confirmations
        int confirmations = gSonProcessor.fromJson(
                getLongStringFromJsonObject(jsonObject, TokenlyReceiptJSonAttNames.CONFIRMATIONS),
                Integer.class);
        //Receipt confirmations out.
        int confirmationsOut = gSonProcessor.fromJson(
                getLongStringFromJsonObject(jsonObject, TokenlyReceiptJSonAttNames.CONFIRMATIONS),
                Integer.class);
        //Receipt timestamp
        long timestamp = gSonProcessor.fromJson(
                getLongStringFromJsonObject(jsonObject, TokenlyReceiptJSonAttNames.TIMESTAMP),
                Integer.class);
        //Receipt completed at
        Date completedAt = gSonProcessor.fromJson(
                getDateStringFromJsonObject(jsonObject, TokenlyReceiptJSonAttNames.COMPLETED_AT),
                Date.class);
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
