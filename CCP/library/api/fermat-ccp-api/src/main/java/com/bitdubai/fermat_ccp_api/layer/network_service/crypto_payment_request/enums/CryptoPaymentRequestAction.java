package com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * The enum <code>com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.enums.CryptoPaymentRequestAction</code>
 * represents the different actions that you can inform throw the network service for a Crypto Payment Request.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 05/10/2015.
 */
public enum CryptoPaymentRequestAction implements FermatEnum {

    INFORM_APPROVAL  ("INAP"),
    INFORM_DENIAL    ("INDE"),
    INFORM_RECEPTION ("INRC"),
    INFORM_REFUSAL   ("INRF");

    private String code;

    CryptoPaymentRequestAction(String code) {
        this.code = code;
    }

    public static CryptoPaymentRequestAction getByCode(String code) throws InvalidParameterException {

        switch (code){

            case "INAP": return INFORM_APPROVAL ;
            case "INDE": return INFORM_DENIAL   ;
            case "INRC": return INFORM_RECEPTION;
            case "INRF": return INFORM_REFUSAL  ;

            default: throw new InvalidParameterException(
                    InvalidParameterException.DEFAULT_MESSAGE,
                    null,
                    "Code Received: " + code,
                    "This code is not valid for the CryptoPaymentRequestAction enum."
            );
        }
    }

    @Override
    public String getCode(){
        return this.code;
    }

}
