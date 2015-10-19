package com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * The enum <code>com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.enums.CryptoPaymentType</code>
 * represents the type of the Crypto Payment Request.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 30/09/2015.
 */
public enum CryptoPaymentType implements FermatEnum {

    SENT     ("SENT"),
    RECEIVED ("RVD" );

    private String code;

    CryptoPaymentType(String code) {
        this.code = code;
    }

    public static CryptoPaymentType getByCode(String code) throws InvalidParameterException {

        switch (code){

            case "SENT": return SENT    ;
            case "RVD":  return RECEIVED;

            default: throw new InvalidParameterException(
                    InvalidParameterException.DEFAULT_MESSAGE,
                    null,
                    "Code Received: " + code,
                    "This code is not valid for the CryptoPaymentType enum."
            );
        }
    }

    @Override
    public String getCode(){
        return this.code;
    }

}
