package com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.util;

import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_art_api.all_definition.enums.ArtExternalPlatform;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.ExposingData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * The interface <code>ArtistExposingData</code>
 * represents an ART Artist and exposes all the functionality of it.
 * <p>
 * Created by Gabriel Araujo.
 */
public final class ArtistExposingData implements ExposingData {

    private final String publicKey;
    private final String alias;
    private final byte[] image;
    private final ArtistExternalPlatformInformation artistExternalPlatformInformation;

    public ArtistExposingData(
            final String publicKey,
            final String alias,
            String extraData) {
        this.publicKey = publicKey;
        this.alias     = alias    ;
        List data = getListFromExtraData(extraData);
        image = (byte[]) data.get(0);
        HashMap<ArtExternalPlatform,String> externalPlatformInformationMap =
                (HashMap<ArtExternalPlatform, String>) data.get(1);
        artistExternalPlatformInformation = new ArtistExternalPlatformInformation(
                externalPlatformInformationMap);
    }

    private List getListFromExtraData(String extraData){
        List data = new ArrayList();
        data = (List) XMLParser.parseXML(extraData, data);
        return data;
    }

    /**
     * @return a string representing the public key.
     */
    public final String getPublicKey() {
        return publicKey;
    }

    /**
     * @return a string representing the alias of the artist.
     */
    public final String getAlias() {
        return alias;
    }

    /**
     * @return an array of bytes with the image exposed by the artist.
     */
    public final byte[] getImage() {
        return image;
    }

    public final ArtistExternalPlatformInformation getArtistExternalPlatformInformation(){
        return artistExternalPlatformInformation;
    }

    /**
     * This method returns a XML String from extra data (image and artistExternalPlatformInformation)
     * @return
     */
    public final String getExtraData(){
        List data = new ArrayList();
        data.add(image);
        data.add(artistExternalPlatformInformation.getExternalPlatformInformationMap());
        return XMLParser.parseObject(data);
    }

    @Override
    public String toString() {
        return "ArtistExposingData{" +
                "publicKey='" + publicKey + '\'' +
                ", alias='" + alias + '\'' +
                ", image=" + Arrays.toString(image) +
                '}';
    }

}
