package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_store.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by eze on 2015.07.13..
 */
public enum WalletInstallationStatus {
    INSTALLED ("INSED"),
    NOT_INSTALLED ("NOINS"),
    INSTALLING ("INSING");

    private String code;

    WalletInstallationStatus(final String code){
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public static WalletInstallationStatus getByCode(String code) throws InvalidParameterException{
        switch (code){
            case "INSED": return WalletInstallationStatus.INSTALLED;
            case "NOINS": return WalletInstallationStatus.NOT_INSTALLED;
            case "INSING": return WalletInstallationStatus.INSTALLING;
            default:
                throw new InvalidParameterException(code);
        }
    }
}
