package com.bitdubai.fermat_api.layer.dmp_world.wallet;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by ciencias on 3/20/15.
 */
public enum IncomingCryptoStatus {

    JUST_RECEIVED("JRX"),
    TO_BE_ANNOUNCED("TBA"),
    ANNOUNCED("ANN"),
    ANNOUNCING_FAILED("ANNFAIL");

    private String code;

    IncomingCryptoStatus(String code){

        this.code=code;

    }

    public String getCode(){

        return this.code;

    }

    public static IncomingCryptoStatus getByCode(String code)throws InvalidParameterException{

        switch (code){

            case "JRX":
                return IncomingCryptoStatus.JUST_RECEIVED;
            case "TBA":
                return IncomingCryptoStatus.TO_BE_ANNOUNCED;
            case "ANN":
                return IncomingCryptoStatus.ANNOUNCED;
            case "ANNFAIL":
                return IncomingCryptoStatus.ANNOUNCING_FAILED;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the IncomingCryptoStatus enum");


        }

    }
    
}
