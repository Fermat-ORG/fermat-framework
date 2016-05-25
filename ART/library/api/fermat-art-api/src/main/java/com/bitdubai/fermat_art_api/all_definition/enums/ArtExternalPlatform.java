package com.bitdubai.fermat_art_api.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 08/04/16.
 */
public enum ArtExternalPlatform implements FermatEnum, Serializable {

    TOKENLY("TKY","Tokenly"),
    UNDEFINED("UNDEF","Undefined"),
    ;

    String code;
    String friendlyName;

    /**
     * Sets the default external platform.
     */
    private static final ArtExternalPlatform DEFAULT_EXTERNAL_PLATFORM = ArtExternalPlatform.TOKENLY;

    ArtExternalPlatform(final String code, final String friendlyName){
        this.code = code;
        this. friendlyName = friendlyName;
    }

    //PUBLIC METHODS
    public static ArtExternalPlatform getByCode(String code) throws InvalidParameterException {
        for (ArtExternalPlatform value : values()) {
            if (value.getCode().equals(code)) return value;
        }
        throw new InvalidParameterException(
                InvalidParameterException.DEFAULT_MESSAGE,
                null, "Code Received: " + code,
                "This Code Is Not Valid for the ArtExternalPlatform enum.");
    }

    @Override
    public String toString() {
        return "SongStatus{" +
                "code='" + code + '\'' +
                '}';
    }

    //GETTER AND SETTERS
    @Override
    public String getCode() {
        return code;
    }

    /**
     * This method returns the Platform friendly name.
     * @return
     */
    public String getFriendlyName() {
        return this.friendlyName;
    }

    /**
     * Gets the default external platform.
     * @return the default ArtExternalPlatform
     */
    public static ArtExternalPlatform getDefaultExternalPlatform(){
        return DEFAULT_EXTERNAL_PLATFORM;
    }

    /**
     * Returns an Array with all the platforms.
     * @return
     */
    public static List<String> getArrayItems(){
        List<String> platformsNames = new ArrayList<String>();
        ArtExternalPlatform[] externalPlatforms = values();
        for (ArtExternalPlatform externalPlatform : externalPlatforms) {
            platformsNames.add(externalPlatform.name());
        }
        return  platformsNames;
    }

    /**
     * This method returns the ART external platform.
     * @param label
     * @return
     */
    public static ArtExternalPlatform getArtExternalPlatformByLabel(String label){
        for (ArtExternalPlatform externalPlatform :
                values()) {
            if(externalPlatform.name().equals(label.toUpperCase()))
                return externalPlatform;
        }
        return null;
    }

}
