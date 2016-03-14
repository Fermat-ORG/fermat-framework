package com.bitdubai.fermat_tky_plugin.layer.external_api.tokenly.developer.bitdubai.version_1.processors.music;

import com.bitdubai.fermat_tky_api.all_definitions.exceptions.CantGetJSonObjectException;
import com.bitdubai.fermat_tky_api.all_definitions.interfaces.RemoteJSonProcessor;
import com.bitdubai.fermat_tky_api.layer.external_api.exceptions.CantGetSongException;
import com.bitdubai.fermat_tky_api.layer.external_api.interfaces.music.Song;
import com.bitdubai.fermat_tky_plugin.layer.external_api.tokenly.developer.bitdubai.version_1.config.TokenlyConfiguration;
import com.bitdubai.fermat_tky_plugin.layer.external_api.tokenly.developer.bitdubai.version_1.config.swapbot.TokenlySongAttNames;
import com.bitdubai.fermat_tky_plugin.layer.external_api.tokenly.developer.bitdubai.version_1.processors.AbstractTokenlyProcessor;
import com.bitdubai.fermat_tky_plugin.layer.external_api.tokenly.developer.bitdubai.version_1.records.music.SongRecord;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.sql.Date;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 14/03/16.
 */
public class TokenlySongProcessor extends AbstractTokenlyProcessor {

    private static String swabotTokenlyURL= TokenlyConfiguration.URL_TOKENLY_MUSIC_API;

    /**
     * This method returns a song from tokenly API by a request URL.
     * @param id
     * @return
     * @throws CantGetSongException
     */
    public static Song getSongById(String id) throws CantGetSongException {
        //Request URL to get a song by tokenly Id.
        String requestedURL=swabotTokenlyURL+"catalog/songs/"+id;
        try{
            JsonObject jSonObject = RemoteJSonProcessor.getJSonObject(requestedURL);
            return getSongFromJsonObject(jSonObject);
        } catch (CantGetJSonObjectException e) {
            throw new CantGetSongException(
                    e,
                    "Getting swap bot from given Id",
                    "Cannot get JSon from tokenly API using URL "+requestedURL);
        }

    }

    public static JsonArray getSongsJsonArrayByAlbumId(String albumId)
            throws CantGetJSonObjectException {
        //Request URL to get a song by tokenly Id.
        String requestedURL=swabotTokenlyURL+"catalog/songs/"+albumId;
        JsonArray jSonArray = RemoteJSonProcessor.getJSonArray(requestedURL);
        return jSonArray;
    }

    public static Song getSongFromJsonObject(
            JsonObject jsonObject) throws CantGetJSonObjectException {
        //Id
        String id = getStringFromJsonObject(jsonObject, TokenlySongAttNames.ID);
        //Name
        String name = getStringFromJsonObject(jsonObject, TokenlySongAttNames.NAME);
        //Tokens
        String[] tokens = getArrayStringFromJsonObject(jsonObject, TokenlySongAttNames.TOKENS);
        //Performers
        String performers = getStringFromJsonObject(jsonObject, TokenlySongAttNames.PERFORMERS);
        //Composers
        String composers = getStringFromJsonObject(jsonObject, TokenlySongAttNames.COMPOSERS);
        //Release date
        Date releaseDate = getDateFromJsonObject(jsonObject, TokenlySongAttNames.RELEASE_DATE);
        //Lyrics
        String lyrics = getStringFromJsonObject(jsonObject, TokenlySongAttNames.LYRICS);
        //Credits
        String credits = getStringFromJsonObject(jsonObject, TokenlySongAttNames.CREDITS);
        //Copyrights
        String copyright = getStringFromJsonObject(jsonObject, TokenlySongAttNames.COPYRIGHT);
        //OWNERSHIP
        String ownership = getStringFromJsonObject(jsonObject, TokenlySongAttNames.OWNERSHIP);
        //Usage Rights
        String usageRights = getStringFromJsonObject(jsonObject, TokenlySongAttNames.USAGE_RIGHTS);
        //Usage prohibitions
        String usageProhibitions = getStringFromJsonObject(
                jsonObject,
                TokenlySongAttNames.USAGE_PROHIBITIONS);
        //BTC address
        String bitcoinAddress = getStringFromJsonObject(jsonObject, TokenlySongAttNames.BTC_ADDRESS);
        //Other
        String other = getStringFromJsonObject(jsonObject, TokenlySongAttNames.OTHER);
        //Create record
        Song song = new SongRecord(
                id,
                name,
                tokens,
                performers,
                composers,
                releaseDate,
                lyrics,
                credits,
                copyright,
                ownership,
                usageRights,
                usageProhibitions,
                bitcoinAddress,
                other);
        return song;

    }

}
