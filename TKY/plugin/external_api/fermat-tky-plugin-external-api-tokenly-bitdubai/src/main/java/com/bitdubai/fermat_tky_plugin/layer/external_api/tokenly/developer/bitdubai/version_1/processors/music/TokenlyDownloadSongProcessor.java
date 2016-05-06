package com.bitdubai.fermat_tky_plugin.layer.external_api.tokenly.developer.bitdubai.version_1.processors.music;

import com.bitdubai.fermat_tky_api.all_definitions.exceptions.CantConnectWithTokenlyException;
import com.bitdubai.fermat_tky_api.all_definitions.exceptions.CantGetJSonObjectException;
import com.bitdubai.fermat_tky_api.all_definitions.interfaces.RemoteJSonProcessor;
import com.bitdubai.fermat_tky_api.layer.external_api.exceptions.CantGetSongException;
import com.bitdubai.fermat_tky_api.layer.external_api.interfaces.music.DownloadSong;
import com.bitdubai.fermat_tky_plugin.layer.external_api.tokenly.developer.bitdubai.version_1.config.TokenlyConfiguration;
import com.bitdubai.fermat_tky_plugin.layer.external_api.tokenly.developer.bitdubai.version_1.config.swapbot.TokenlyDownloadSongAttNames;
import com.bitdubai.fermat_tky_plugin.layer.external_api.tokenly.developer.bitdubai.version_1.processors.AbstractTokenlyProcessor;
import com.bitdubai.fermat_tky_plugin.layer.external_api.tokenly.developer.bitdubai.version_1.records.music.DownloadSongRecord;
import com.google.gson.JsonObject;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 14/03/16.
 */
public class TokenlyDownloadSongProcessor extends AbstractTokenlyProcessor {

    private static String swabotTokenlyURL= TokenlyConfiguration.URL_TOKENLY_MUSIC_API;

    /**
     * This method returns a download song from tokenly API by a request URL.
     * @param id
     * @return
     * @throws CantGetSongException
     */
    public static DownloadSong getDownloadSongById(String id) throws
            CantGetSongException,
            CantConnectWithTokenlyException {
        //Request URL to get a download song by tokenly Id.
        String requestedURL=swabotTokenlyURL+"song/download/"+id;
        try{
            JsonObject jSonObject = RemoteJSonProcessor.getJSonObject(requestedURL);
            return getDownloadSongFromJsonObject(jSonObject);
        } catch (CantGetJSonObjectException e) {
            throw new CantGetSongException(
                    e,
                    "Getting swap bot from given Id",
                    "Cannot get JSon from tokenly API using URL "+requestedURL);
        }

    }

    /**
     * This method returns a DownloadSong object from a JsonObject.
     * @param jSonObject
     * @return
     * @throws CantGetJSonObjectException
     */
    private static DownloadSong getDownloadSongFromJsonObject(
            JsonObject jSonObject) throws CantGetJSonObjectException{
        //Id
        String id = getStringFromJsonObject(jSonObject, TokenlyDownloadSongAttNames.ID);
        //Name
        String name = getStringFromJsonObject(jSonObject, TokenlyDownloadSongAttNames.NAME);
        //Description
        String description = getStringFromJsonObject(
                jSonObject,
                TokenlyDownloadSongAttNames.DESCRIPTION);
        //Download URL
        String downloadURL = getStringFromJsonObject(
                jSonObject,
                TokenlyDownloadSongAttNames.DOWNLOAD_URL);
        //Download Size
        long downloadSize = getLongFromJsonObject(
                jSonObject,
                TokenlyDownloadSongAttNames.DOWNLOAD_SIZE);
        DownloadSong downloadSong = new DownloadSongRecord(
                id,
                name,
                description,
                downloadURL,
                downloadSize);
        return downloadSong;
    }

}
