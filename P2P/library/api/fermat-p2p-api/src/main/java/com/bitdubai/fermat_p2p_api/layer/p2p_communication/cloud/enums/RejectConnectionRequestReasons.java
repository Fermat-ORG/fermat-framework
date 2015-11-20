package com.bitdubai.fermat_p2p_api.layer.p2p_communication.cloud.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by ciencias on 2/23/15.
 */
public enum RejectConnectionRequestReasons {
    
    I_AM_TOO_BUSY_NOW("IATBN"),
    I_DO_NOT_TALK_TO_STRANGERS("IDNTTS"),
    YOU_HAVE_BAD_REPUTATION("YHBR");

    private final String code;

    RejectConnectionRequestReasons(final String code){
        this.code = code;
    }

    public String getCode(){
        return code;
    }

    public static RejectConnectionRequestReasons getByCode(final String code) throws InvalidParameterException{
        switch (code){
            case "IATBN": return RejectConnectionRequestReasons.I_AM_TOO_BUSY_NOW;
            case "IDNTTS": return RejectConnectionRequestReasons.I_DO_NOT_TALK_TO_STRANGERS;
            case "YHBR": return RejectConnectionRequestReasons.YOU_HAVE_BAD_REPUTATION;
            default: throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code: " + code, null);
        }
    }
    
}
