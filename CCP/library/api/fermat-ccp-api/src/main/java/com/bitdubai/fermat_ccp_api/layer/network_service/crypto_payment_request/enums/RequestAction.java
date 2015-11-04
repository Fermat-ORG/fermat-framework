package com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * The enum <code>com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.enums.RequestAction</code>
 * represents the different actions that you can inform throw the network service for a Crypto Payment Request.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 05/10/2015.
 */
public enum RequestAction implements FermatEnum {

    /**
     * For doing the code more readable, please keep the elements in the Enum sorted alphabetically.
     */

    INFORM_APPROVAL  ("INAP"),
    INFORM_DENIAL    ("INDE"),
    INFORM_RECEPTION ("INRC"),
    INFORM_REFUSAL   ("INRF"),
    NONE             ("NONE"),
    REQUEST          ("REQU"),

    ;

    private String code;

    RequestAction(String code) {
        this.code = code;
    }

    public static RequestAction getByCode(String code) throws InvalidParameterException {

        switch (code){

            case "INAP": return INFORM_APPROVAL ;
            case "INDE": return INFORM_DENIAL   ;
            case "INRC": return INFORM_RECEPTION;
            case "INRF": return INFORM_REFUSAL  ;
            case "NONE": return NONE            ;
            case "REQU": return REQUEST         ;

            default: throw new InvalidParameterException(
                    "Code Received: " + code,
                    "This code is not valid for the RequestAction enum."
            );
        }
    }

    @Override
    public String getCode(){
        return this.code;
    }

}
