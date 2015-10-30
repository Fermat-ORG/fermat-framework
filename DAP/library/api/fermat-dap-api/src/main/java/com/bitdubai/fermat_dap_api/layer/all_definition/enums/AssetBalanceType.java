package com.bitdubai.fermat_dap_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 02/10/15.
 */
public enum AssetBalanceType {
    AVAILABLE("AVAI"),
    BOOK("BOOK");

    String code;

    AssetBalanceType(String code){
        this.code=code;
    }

    public String getCode(){
        return this.code;
    }

    public AssetBalanceType getByCode(String code) throws InvalidParameterException {
        switch (code){
            case "BOOK":
                return AssetBalanceType.BOOK;
            case "AVAI":
                return AssetBalanceType.AVAILABLE;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the AssetCreditType enum.");
        }
    }

}
