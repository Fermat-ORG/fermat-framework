package com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * The enum <code>com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.enums.CryptoPaymentRequestType</code>
 * represents the type of the Crypto Payment Request.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 05/10/2015.
 */
public enum CryptoPaymentRequestType implements FermatEnum {

    OWN      ("OWN"),
    RECEIVED ("RVD");

    private String code;

    CryptoPaymentRequestType(String code) {
        this.code = code;
    }

    public static CryptoPaymentRequestType getByCode(String code) throws InvalidParameterException {

        switch (code){

            case "OWN": return OWN     ;
            case "RVD": return RECEIVED;

            default: throw new InvalidParameterException(
                    InvalidParameterException.DEFAULT_MESSAGE,
                    null,
                    "Code Received: " + code,
                    "This code is not valid for the CryptoPaymentRequestType enum."
            );
        }
    }

    @Override
    public String getCode(){
        return this.code;
    }

}
