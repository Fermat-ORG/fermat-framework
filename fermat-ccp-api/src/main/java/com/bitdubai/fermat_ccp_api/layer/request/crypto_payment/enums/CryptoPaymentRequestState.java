package com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * The enum <code>com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.enums.CryptoPaymentRequestState</code>
 * represents the state of the Crypto Payment Request.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 30/09/2015.
 */
public enum CryptoPaymentRequestState implements FermatEnum {

    COLLECTED                 ("COL"),
    PAID                      ("PAI"),
    PENDING_LOCALLY_RESPONSE  ("PLR"),
    PENDING_REMOTE_RESPONSE   ("PRR"),
    REFUSED_LOCALLY           ("RLO"),
    REFUSED_REMOTELY          ("RRE");

    private String code;

    CryptoPaymentRequestState(String code) {
        this.code = code;
    }

    public static CryptoPaymentRequestState getByCode(String code) throws InvalidParameterException {

        switch (code){

            case "COL": return COLLECTED                ;
            case "PAI": return PAID                     ;
            case "PLR": return PENDING_LOCALLY_RESPONSE ;
            case "PRR": return PENDING_REMOTE_RESPONSE  ;
            case "RLO": return REFUSED_LOCALLY          ;
            case "RRE": return REFUSED_REMOTELY         ;

            default: throw new InvalidParameterException(
                    InvalidParameterException.DEFAULT_MESSAGE,
                    null,
                    "Code Received: " + code,
                    "This code is not valid for the CryptoPaymentRequestState enum."
            );
        }
    }

    @Override
    public String getCode(){
        return this.code;
    }
}
