package com.bitdubai.fermat_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Matias Furszyfer  on 2015.07.22..
 */
public enum UISource implements FermatEnum {

    //Modified by Manuel Perez on 03/08/2015
    ACTIVITY("ACTIV"),
    ADAPTER("ADAPT"),
    TASK("TASK"),
    VIEW("VIEW");

    private String code;

    UISource(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    public static UISource getByCode(String code) throws InvalidParameterException {

        switch (code) {
            case "ACTIV": return UISource.ACTIVITY;
            case "ADAPT": return UISource.ADAPTER;
            case "TASK":  return UISource.TASK;
            case "VIEW":  return UISource.VIEW;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the UISource enum");
        }
    }
}
