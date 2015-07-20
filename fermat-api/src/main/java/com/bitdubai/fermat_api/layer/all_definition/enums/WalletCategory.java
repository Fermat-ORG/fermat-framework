package com.bitdubai.fermat_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by eze on 2015.07.19..
 */
public enum WalletCategory {
    BRANDED_WALLET ("BRDW"),
    REFERENCE_WALLET ("REFW"),
    NICHE_WALLET ("NCHW");

    private String code;

    WalletCategory(String code){
        this.code = code;
    }

    public String getCode(){
        return this.code;
    }

    public static WalletCategory getByCode(String code) throws InvalidParameterException{
        switch (code) {
            case "BRDW": return WalletCategory.BRANDED_WALLET;
            case "REFW": return WalletCategory.REFERENCE_WALLET;
            case "NCHW": return WalletCategory.NICHE_WALLET;
            default: throw new InvalidParameterException();
        }
    }
}
