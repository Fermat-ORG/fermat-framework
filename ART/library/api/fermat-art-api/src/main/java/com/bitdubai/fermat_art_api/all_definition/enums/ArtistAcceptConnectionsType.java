package com.bitdubai.fermat_art_api.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 09/03/16.
 */
public enum ArtistAcceptConnectionsType implements FermatEnum, Serializable {

    /**
     * Please for doing the code more readable, keep the elements of the enum ordered.
     */
    AUTOMATIC("AUT"),
    MANUAL("MAN"),
    NO_CONNECTIONS("NOC");


    String code;
    ArtistAcceptConnectionsType(String code){
        this.code=code;
    }


    public static final ArtistAcceptConnectionsType DEFAULT_ARTIST_ACCEPT_CONNECTION_TYPE = ArtistAcceptConnectionsType.AUTOMATIC;

    //PUBLIC METHODS

    public static ArtistAcceptConnectionsType getByCode(String code) throws InvalidParameterException {
        for (ArtistAcceptConnectionsType value : values()) {
            if (value.getCode().equals(code)) return value;
        }
        throw new InvalidParameterException(
                InvalidParameterException.DEFAULT_MESSAGE,
                null, "Code Received: " + code,
                "This Code Is Not Valid for the ArtistAcceptConnectionsType enum.");
    }

    @Override
    public String toString() {
        return "ArtistAcceptConnectionsType{" +
                "code='" + code + '\'' +
                '}';
    }

    //GETTER AND SETTERS
    @Override
    public String getCode() {
        return code;
    }

    public static List<String> getArrayItems(){
        List<String> platformsNames = new ArrayList<String>();
        ArtistAcceptConnectionsType[] externalPlatforms = values();
        for (ArtistAcceptConnectionsType externalPlatform : externalPlatforms) {
            platformsNames.add(externalPlatform.name());
        }
        return  platformsNames;
    }
    public static ArtistAcceptConnectionsType getArtistAcceptConnectionsTypeByLabel(String label){
        for (ArtistAcceptConnectionsType artistAcceptConnectionsType :
                values()) {
            if(artistAcceptConnectionsType.name().equals(label.toUpperCase()))
                return artistAcceptConnectionsType;
        }
        return null;
    }
}

