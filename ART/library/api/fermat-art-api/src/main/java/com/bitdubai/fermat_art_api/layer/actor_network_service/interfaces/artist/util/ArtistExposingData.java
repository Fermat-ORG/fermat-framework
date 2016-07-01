package com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.util;

import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_art_api.all_definition.enums.ArtExternalPlatform;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.AbstractExposingData;

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
public final class ArtistExposingData extends AbstractExposingData {

    private final ArtistExternalPlatformInformation artistExternalPlatformInformation;

    public ArtistExposingData(
            final String publicKey,
            final String alias,
            String extraData,
            Location location) {
        super(publicKey,alias,extraData,location);
        //External platform information.
        HashMap<ArtExternalPlatform, String> externalPlatformInformationMap=
                getExternalPlatformInformationMap(data.get(EXTERNAL_DATA_INDEX));
        artistExternalPlatformInformation = new ArtistExternalPlatformInformation(
                externalPlatformInformationMap);
    }

    /**
     * This method returns the FanExternalPlatformInformation.
     * @return
     */
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
                ", externalPlatform=" + artistExternalPlatformInformation +
                '}';
    }

}
