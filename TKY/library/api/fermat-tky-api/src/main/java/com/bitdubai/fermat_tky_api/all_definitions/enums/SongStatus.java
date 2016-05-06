package com.bitdubai.fermat_tky_api.all_definitions.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

import java.io.Serializable;

/**
 * The enum <code>com.bitdubai.fermat_tky_api.all_definitions.enums.SongStatus</code>
 * represent the different type of song status in song wallet Tokenly.<p/>
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 15/03/16.
 */
public enum SongStatus implements FermatEnum, Serializable {

    /**
     * Please for doing the code more readable, keep the elements of the enum ordered.
     */
    AVAILABLE       ("AVA", "AVAILABLE"),
    DELETED         ("DEL", "DELETED"),
    CANCELLED      ("CAN", "CANCELLED"),
    DOWNLOADING     ("DNG", "DOWNLOADING"),
    NOT_AVAILABLE   ("NAV", "NOT_AVAILABLE"),
    ;

    String code;
    String friendlyName;

    SongStatus(String code, String friendlyName){
        this.code = code;
        this.friendlyName = friendlyName;
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

    /**
     * This method returns the Platform friendly name.
     * @return
     */
    public String getFriendlyName() {
        return this.friendlyName;
    }

}


