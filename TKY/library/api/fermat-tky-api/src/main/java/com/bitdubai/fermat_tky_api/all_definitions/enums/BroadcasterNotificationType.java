package com.bitdubai.fermat_tky_api.all_definitions.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 01/04/16.
 */
public enum BroadcasterNotificationType implements FermatEnum {

    /**
     * Please for doing the code more readable, keep the elements of the enum ordered.
     */
    FAN_WALLET_BROADCAST_NOTIFICATION("FAN_WBN"),
    DOWNLOAD_EXCEPTION("DWE"),
    DOWNLOAD_PERCENTAGE("DWP"),
    SONG_ID("SID"),
    SONG_INFO("SGI"),
    SONG_CANCEL("SCA"),
    ;

    String code;
    BroadcasterNotificationType(String code){
        this.code=code;
    }

    //PUBLIC METHODS

    public static BroadcasterNotificationType getByCode(String code) throws InvalidParameterException {
        for (BroadcasterNotificationType value : values()) {
            if (value.getCode().equals(code)) return value;
        }
        throw new InvalidParameterException(
                InvalidParameterException.DEFAULT_MESSAGE,
                null, "Code Received: " + code,
                "This Code Is Not Valid for the BroadcasterNotificationType enum.");
    }

    @Override
    public String toString() {
        return "BroadcasterNotificationType{" +
                "code='" + code + '\'' +
                '}';
    }

    //GETTER AND SETTERS
    @Override
    public String getCode() {
        return code;
    }

}
