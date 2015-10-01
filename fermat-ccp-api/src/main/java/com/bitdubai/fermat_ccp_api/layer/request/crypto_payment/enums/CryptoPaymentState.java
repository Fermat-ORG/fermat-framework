package com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * The enum <code>com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.enums.CryptoPaymentState</code>
 * represents the state of the Crypto Payment Request.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 30/09/2015.
 */
public enum CryptoPaymentState implements FermatEnum {

    DENIED_BY_INCOMPATIBILITY ("DBI"),
    PAID                      ("PAI"),
    PENDING_RESPONSE          ("PEN"),
    REFUSED                   ("REF");

    private String code;

    CryptoPaymentState(String code) {
        this.code = code;
    }

    public static CryptoPaymentState getByCode(String code) throws InvalidParameterException {

        switch (code){

            case "DBI": return DENIED_BY_INCOMPATIBILITY;
            case "PAI": return PAID                     ;
            case "PEN": return PENDING_RESPONSE         ;
            case "REF": return REFUSED                  ;

            default: throw new InvalidParameterException(
                    InvalidParameterException.DEFAULT_MESSAGE,
                    null,
                    "Code Received: " + code,
                    "This code is not valid for the CryptoPaymentState enum."
            );
        }
    }

    @Override
    public String getCode(){
        return this.code;
    }

}
