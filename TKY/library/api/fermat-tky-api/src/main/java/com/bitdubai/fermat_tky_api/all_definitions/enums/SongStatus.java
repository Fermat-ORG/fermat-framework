package com.bitdubai.fermat_tky_api.all_definitions.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * The enum <code>com.bitdubai.fermat_tky_api.all_definitions.enums.SongStatus</code>
 * represent the different type of song status in song wallet Tokenly.<p/>
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 15/03/16.
 */
public enum SongStatus implements FermatEnum {

    /**
     * Please for doing the code more readable, keep the elements of the enum ordered.
     */
    AVAILABLE       ("AVA"),
    DELETED         ("DEL"),
    DOWNLOADED      ("DED"),
    DOWNLOADING     ("DNG"),
    NOT_AVAILABLE   ("NAV"),
    AVA             ("AVAILABLE"),
    DEL             ("DELETED"),
    DED             ("DOWNLOADED"),
    DNG             ("DOWNLOADING"),
    NAV             ("NOT_AVAILABLE");

    String code;
    SongStatus(String code){
        this.code=code;
    }

    //PUBLIC METHODS
    public static SongStatus getByCode(String code) throws InvalidParameterException {
        for (SongStatus value : values()) {
            if (value.getCode().equals(code)) return value;
        }
        throw new InvalidParameterException(
                InvalidParameterException.DEFAULT_MESSAGE,
                null, "Code Received: " + code,
                "This Code Is Not Valid for the SongStatus enum.");
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



}


