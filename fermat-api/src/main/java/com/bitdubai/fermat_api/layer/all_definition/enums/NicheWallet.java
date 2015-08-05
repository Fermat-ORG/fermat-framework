package com.bitdubai.fermat_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.CallToGetByCodeOnNONEException;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by eze on 2015.07.19..
 */
public enum NicheWallet {
    /**
     * The value NONE is used to store the information of no wallet
     */
    NONE("NONE",null),
    UNKNOWN_WALLET ("UNKW", ReferenceWallet.BASIC_WALLET_BITCOIN_WALLET);

    private String code;
    private ReferenceWallet referenceWallet;

    NicheWallet (String code,ReferenceWallet referenceWallet){
        this.code = code;
        this.referenceWallet = referenceWallet;
    }

    public String getCode(){
        return this.code;
    }

    public ReferenceWallet getReferenceWallet(){
        return this.referenceWallet;
    }

    public NicheWallet getByCode(String code) throws InvalidParameterException, CallToGetByCodeOnNONEException{
        switch (code) {
            case "NONE" : throw  new CallToGetByCodeOnNONEException("method getByCode called by a NONE wallet",null,"","");
            case "UNKW" : return NicheWallet.UNKNOWN_WALLET;
            //Modified by Manuel Perez on 03/08/2015
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the NicheWallet enum");

        }
    }
}
