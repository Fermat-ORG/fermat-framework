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

        //Image Detail Id
        String id = getStringFromJsonObject(
                jSonObject,
                TokenlyImageDetailsJSonAttNames.ID);
        //Image Detail full url.
        String fullUrl = getStringFromJsonObject(
                jSonObject,
                TokenlyImageDetailsJSonAttNames.FULL_URL);
        //Image Detail medium url.
        String mediumUrl = getStringFromJsonObject(
                jSonObject,
                TokenlyImageDetailsJSonAttNames.MEDIUM_URL);
        //Image Detail Thumb url.
        String thumbUrl = getStringFromJsonObject(
                jSonObject,
                TokenlyImageDetailsJSonAttNames.THUMB_URL);
        //Image Detail Original url.
        String originalUrl = getStringFromJsonObject(
                jSonObject,
                TokenlyImageDetailsJSonAttNames.ORIGINAL_URL);
        //Image Detail content type
        String contentType = getStringFromJsonObject(
                jSonObject,
                TokenlyImageDetailsJSonAttNames.CONTENT_TYPE);
        //Image Detail size
        long size = getLongFromJsonObject(
                jSonObject,
                TokenlyImageDetailsJSonAttNames.SIZE);
        //Image Detail original file name
        String originalFileName = getStringFromJsonObject(
                jSonObject,
                TokenlyImageDetailsJSonAttNames.ORIGINAL_FILE_NAME);
        //Image Detail original file name.
        Date updatedAt = getDateFromJsonObject(
                jSonObject,
                TokenlyImageDetailsJSonAttNames.UPDATED);
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
