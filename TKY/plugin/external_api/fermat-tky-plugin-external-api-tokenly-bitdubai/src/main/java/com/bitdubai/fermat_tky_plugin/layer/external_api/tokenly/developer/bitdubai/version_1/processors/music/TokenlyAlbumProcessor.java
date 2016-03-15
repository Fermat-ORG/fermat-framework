package com.bitdubai.fermat_tky_plugin.layer.external_api.tokenly.developer.bitdubai.version_1.processors.music;

import com.bitdubai.fermat_tky_api.all_definitions.exceptions.CantGetJSonObjectException;
import com.bitdubai.fermat_tky_api.all_definitions.interfaces.RemoteJSonProcessor;
import com.bitdubai.fermat_tky_api.layer.external_api.exceptions.CantGetAlbumException;
import com.bitdubai.fermat_tky_api.layer.external_api.interfaces.music.Album;
import com.bitdubai.fermat_tky_api.layer.external_api.interfaces.music.Song;
import com.bitdubai.fermat_tky_plugin.layer.external_api.tokenly.developer.bitdubai.version_1.config.TokenlyConfiguration;
import com.bitdubai.fermat_tky_plugin.layer.external_api.tokenly.developer.bitdubai.version_1.config.swapbot.TokenlyAlbumAttNames;
import com.bitdubai.fermat_tky_plugin.layer.external_api.tokenly.developer.bitdubai.version_1.processors.AbstractTokenlyProcessor;
import com.bitdubai.fermat_tky_plugin.layer.external_api.tokenly.developer.bitdubai.version_1.records.music.AlbumRecord;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 14/03/16.
 */
public class TokenlyAlbumProcessor extends AbstractTokenlyProcessor {

    private static String swabotTokenlyURL= TokenlyConfiguration.URL_TOKENLY_MUSIC_API;

    /**
     * This method returns a song from tokenly API by a request URL.
     * @param id
     * @return
     * @throws CantGetAlbumException
     */
    public static Album getSongById(String id) throws CantGetAlbumException {
        //Request URL to get a song by tokenly Id.
        String requestedURL=swabotTokenlyURL+"catalog/songs/"+id;
        try{
            JsonObject jSonObject = RemoteJSonProcessor.getJSonObject(requestedURL);
            return getAlbumFromJsonObject(jSonObject);
        } catch (CantGetJSonObjectException e) {
            throw new CantGetAlbumException(
                    e,
                    "Getting album from given Id",
                    "Cannot get JSon from tokenly API using URL "+requestedURL);
        }

    }

    private static Album getAlbumFromJsonObject(JsonObject jsonObject)
            throws CantGetJSonObjectException {
        //Id
        String id = getStringFromJsonObject(jsonObject, TokenlyAlbumAttNames.ID);
        //Name
        String name = getStringFromJsonObject(jsonObject, TokenlyAlbumAttNames.NAME);
        //Description
        String description = getStringFromJsonObject(jsonObject, TokenlyAlbumAttNames.DESCRIPTION);
        //Songs count.
        int songCount = (int) getLongFromJsonObject(jsonObject, TokenlyAlbumAttNames.SONG_COUNT);
        //Songs
        JsonArray jsonArraySongs = TokenlySongProcessor.getSongsJsonArrayByAlbumId(id);
        int jsonArraySize = jsonArraySongs.size();
        Song[] songs = new Song[jsonArraySize];
        Song song;
        //loop counter
        int counter=0;
        for(JsonElement jsonElement : jsonArraySongs){
            song = TokenlySongProcessor.getSongFromJsonObject(jsonElement.getAsJsonObject());
            songs[counter] = song;
            counter++;
        }
        //Create record
        Album album = new AlbumRecord(
                id,
                name,
                description,
                songCount,
                songs);
        return album;
    }

}
