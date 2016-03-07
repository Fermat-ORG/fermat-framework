package com.bitdubai.fermat_wrd_plugin.layer.api.tokenly.developer.bitdubai.version_1.processors;

import com.bitdubai.fermat_wrd_api.layer.api.tokenly.interfaces.ImageDetails;
import com.bitdubai.fermat_wrd_plugin.layer.api.tokenly.developer.bitdubai.version_1.config.TokenlyImageDetailsJSonAttNames;
import com.bitdubai.fermat_wrd_plugin.layer.api.tokenly.developer.bitdubai.version_1.records.ImageDetailsRecord;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.sql.Date;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 07/03/16.
 */
public class TokenlyImageDetailsProcessor extends AbstractTokenlyProcessor{

    /**
     * This method returns a ImageDetails from a given JsonObject.
     * @param jSonObject
     * @return
     */
    public static ImageDetails getImageDetailsFromJsonObject(JsonObject jSonObject){

        Gson gSonProcessor = new Gson();
        //Image Detail Id
        String id = gSonProcessor.fromJson(
                getStringFromJsonObject(jSonObject, TokenlyImageDetailsJSonAttNames.ID),
                String.class);
        //Image Detail full url.
        String fullUrl = gSonProcessor.fromJson(
                getStringFromJsonObject(jSonObject, TokenlyImageDetailsJSonAttNames.FULL_URL),
                String.class);
        //Image Detail medium url.
        String mediumUrl = gSonProcessor.fromJson(
                getStringFromJsonObject(jSonObject, TokenlyImageDetailsJSonAttNames.MEDIUM_URL),
                String.class);
        //Image Detail Thumb url.
        String thumbUrl = gSonProcessor.fromJson(
                getStringFromJsonObject(jSonObject, TokenlyImageDetailsJSonAttNames.THUMB_URL),
                String.class);
        //Image Detail Original url.
        String originalUrl = gSonProcessor.fromJson(
                getStringFromJsonObject(jSonObject, TokenlyImageDetailsJSonAttNames.ORIGINAL_URL),
                String.class);
        //Image Detail content type
        String contentType = gSonProcessor.fromJson(
                getStringFromJsonObject(jSonObject, TokenlyImageDetailsJSonAttNames.CONTENT_TYPE),
                String.class);
        //Image Detail size
        long size = gSonProcessor.fromJson(
                getLongStringFromJsonObject(jSonObject, TokenlyImageDetailsJSonAttNames.SIZE),
                Long.class);
        //Image Detail original file name
        String originalFileName = gSonProcessor.fromJson(
                getStringFromJsonObject(jSonObject, TokenlyImageDetailsJSonAttNames.ORIGINAL_FILE_NAME),
                String.class);
        //Image Detail original file name.
        Date updatedAt = gSonProcessor.fromJson(
                getDateStringFromJsonObject(jSonObject, TokenlyImageDetailsJSonAttNames.ORIGINAL_FILE_NAME),
                Date.class);
        //Create Image Detail
        ImageDetails imageDetails = new ImageDetailsRecord(
                id,
                fullUrl,
                mediumUrl,
                thumbUrl,
                originalUrl,
                contentType,
                size,
                originalFileName,
                updatedAt);
        return imageDetails;
    }

}
