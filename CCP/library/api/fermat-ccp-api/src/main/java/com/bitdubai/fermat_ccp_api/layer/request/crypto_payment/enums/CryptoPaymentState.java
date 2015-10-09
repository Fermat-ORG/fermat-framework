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

    APPROVED                       ("APR"),
    DENIED_BY_INCOMPATIBILITY      ("DBI"),
    ERROR                          ("ERR"),
    IN_APPROVING_PROCESS           ("IAP"),
    NOT_SENT_YET                   ("NSY"),
    PAID                           ("PAI"),
    PAYMENT_PROCESS_STARTED        ("PPS"),
    PENDING_RESPONSE               ("PEN"),
    REFUSED                        ("REF"),
    WAITING_RECEPTION_CONFIRMATION ("WRC"),

    ;

    private String code;

    CryptoPaymentState(String code) {
        this.code = code;
    }

    public static CryptoPaymentState getByCode(String code) throws InvalidParameterException {

        switch (code){

            case "APR": return APPROVED                      ;
            case "DBI": return DENIED_BY_INCOMPATIBILITY     ;
            case "ERR": return ERROR                         ;
            case "IAP": return IN_APPROVING_PROCESS          ;
            case "NSY": return NOT_SENT_YET                  ;
            case "PAI": return PAID                          ;
            case "PPS": return PAYMENT_PROCESS_STARTED       ;
            case "PEN": return PENDING_RESPONSE              ;
            case "REF": return REFUSED                       ;
            case "WRC": return WAITING_RECEPTION_CONFIRMATION;

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
