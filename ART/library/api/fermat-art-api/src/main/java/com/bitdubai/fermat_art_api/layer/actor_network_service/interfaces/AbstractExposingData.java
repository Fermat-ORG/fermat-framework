package com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_art_api.all_definition.enums.ArtExternalPlatform;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 10/05/16.
 */
public abstract class AbstractExposingData implements ExposingData {
    public final String publicKey;
    public final String alias;
    public final byte[] image;
    private final int IMAGE_DATA_INDEX = 0;
    public final int EXTERNAL_DATA_INDEX = 1;
    public final List data;
    private final Location location;

    public AbstractExposingData(
            final String publicKey,
            final String alias,
            String extraData,
            Location location){
        this.publicKey = publicKey;
        this.alias     = alias    ;
        //Now, we gonna get the data from extra data string
        data = getListFromExtraData(extraData);
        //Image
        image = getImageArrayByte(data.get(IMAGE_DATA_INDEX));
        //The external platform information must be implemented in this class implementations.
        this.location = location;
    }

    /**
     * This method returns an array of bytes with the image. Also checks
     * if the information is correct before the casting to the array.
     * @param data
     * @return
     */
    private byte[] getImageArrayByte(Object data){
        //If data is null I'll return an empty array.
        if(data==null){
            return new byte[0];
        }
        try{
            byte[] image = (byte[]) data;
            return image;
        } catch (ClassCastException e){
            //In this case, the data submitted is wrong
            return new byte[0];
        }
    }

    /**
     * This method returns a Hash Map with the identity external platform information. Also checks
     * if the information is correct before the casting to HashMap.
     * @param data
     * @return
     */
    public HashMap<ArtExternalPlatform, String> getExternalPlatformInformationMap(Object data){
        //If data is null I'll return an empty HashMap.
        if(data==null){
            return new HashMap<>();
        }
        try{
            HashMap<ArtExternalPlatform,String> externalPlatformInformationMap =
                    (HashMap) data;
            return externalPlatformInformationMap;
        } catch (ClassCastException e){
            //In this case, the data submitted is wrong
            return new HashMap<>();
        }
    }

    /**
     * This method returns the extra data list.
     * @param extraData
     * @return
     */
    private List getListFromExtraData(String extraData) {
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
     * @return a string representing the alias of the actor.
     */
    public final String getAlias() {
        return alias;
    }

    /**
     * @return an array of bytes with the image exposed by the Actor.
     */
    public final byte[] getImage() {
        return image;
    }

    /**
     * This method returns the Actor location
     * @return
     */
    public Location getLocation() {
        return location;
    }

    /**
     * This method returns the extra data string, this depends on artist or a Fan.
     * @return
     */
    public abstract String getExtraData();
}
