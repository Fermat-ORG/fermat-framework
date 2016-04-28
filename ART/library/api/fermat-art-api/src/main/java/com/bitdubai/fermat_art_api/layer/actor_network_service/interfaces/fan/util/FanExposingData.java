package com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.fan.util;

import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_art_api.all_definition.enums.ArtExternalPlatform;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.ExposingData;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.util.ArtistExternalPlatformInformation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * The interface <code>FanExposingData</code>
 * represents a Fan and exposes all the functionality of it.
 * <p>
 * Created by Gabriel Araujo.
 */
public final class FanExposingData implements ExposingData {

    private final String publicKey;
    private final String alias    ;
    private final byte[] image    ;
    private final FanExternalPlatformInformation fanExternalPlatformInformation;

    public FanExposingData(
            final String publicKey,
            final String alias,
            String extraData) {

        this.publicKey = publicKey;
        this.alias     = alias    ;
        List data = getListFromExtraData(extraData);
        image = (byte[]) data.get(0);
        HashMap<ArtExternalPlatform,String> externalPlatformInformationMap =
                (HashMap<ArtExternalPlatform, String>) data.get(1);
        fanExternalPlatformInformation = new FanExternalPlatformInformation(
                externalPlatformInformationMap);
    }

    /**
     * @return a string representing the public key.
     */
    public final String getPublicKey() {
        return publicKey;
    }

    /**
     * @return a string representing the alias of the crypto broker.
     */
    public final String getAlias() {
        return alias;
    }

    /**
     * @return an array of bytes with the image exposed by the Crypto Broker.
     */
    public final byte[] getImage() {
        return image;
    }

    /**
     * This method returns the FanExternalPlatformInformation.
     * @return
     */
    public final FanExternalPlatformInformation getFanExternalPlatformInformation(){
        return fanExternalPlatformInformation;
    }

    /**
     * This method returns the extra data list.
     * @param extraData
     * @return
     */
    private List getListFromExtraData(String extraData){
        List data = new ArrayList();
        data = (List) XMLParser.parseXML(extraData, data);
        return data;
    }

    /**
     * This method returns a XML String from extra data (image and artistExternalPlatformInformation)
     * @return
     */
    public final String getExtraData(){
        List data = new ArrayList();
        data.add(image);
        data.add(fanExternalPlatformInformation.getExternalPlatformInformationMap());
        return XMLParser.parseObject(data);
    }

    @Override
    public String toString() {
        return "FanExposingData{" +
                "publicKey='" + publicKey + '\'' +
                ", alias='" + alias + '\'' +
                ", image=" + Arrays.toString(image) +
                '}';
    }

}
