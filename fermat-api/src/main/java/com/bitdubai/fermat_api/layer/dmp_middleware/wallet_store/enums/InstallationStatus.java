package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_store.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by eze on 2015.07.13..
 */
public enum InstallationStatus {
    INSTALLED ("INSED"),
    NOT_INSTALLED ("NOINS"),
    INSTALLING ("INSING"),
    INSTALL_AVAILABLE("INSA");

    private String code;

    InstallationStatus(final String code){
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public static InstallationStatus getByCode(String code) throws InvalidParameterException{
        switch (code){
            case "INSED": return InstallationStatus.INSTALLED;
            case "NOINS": return InstallationStatus.NOT_INSTALLED;
            case "INSING": return InstallationStatus.INSTALLING;
            case "INSA": return InstallationStatus.INSTALL_AVAILABLE;
            default:
                throw new InvalidParameterException(code);
        }
    }
}
