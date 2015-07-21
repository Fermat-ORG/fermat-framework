package com.bitdubai.fermat_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by eze on 2015.07.20..
 */
public enum BrandedWallet {
    UNKNOWN_WALLET ("UNKW");

    private String code;

    BrandedWallet (String code){
        this.code = code;
    }

    public String getCode(){
        return this.code;
    }

    public NicheWallet getByCode(String code) throws InvalidParameterException {
        switch (code) {
            case "UNKW" : return NicheWallet.UNKNOWN_WALLET;
            default: throw new InvalidParameterException();
        }
    }
}
