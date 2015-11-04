package com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_store.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by eze on 2015.07.13..
 */
public enum InstallationStatus {
    INSTALLED ("INSED"),
    NOT_INSTALLED ("NOINS"),
    INSTALLING ("INSING"),
    UNINSTALLING("UNING"),
    UNINSTALLED("UNED"),
    NOT_UNINSTALLED("NUNED"),
    INSTALL_AVAILABLE("INSA"),
    UPGRADE_AVAILABLE("UPGA"),
    UPGRADING("UPGING");

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
            case "UNING": return InstallationStatus.UNINSTALLING;
            case "UNED": return InstallationStatus.UNINSTALLED;
            case "NUNED": return InstallationStatus.NOT_UNINSTALLED;
            case "INSA": return InstallationStatus.INSTALL_AVAILABLE;
            case "UPGA": return InstallationStatus.UPGRADE_AVAILABLE;
            case "UPGING": return InstallationStatus.UPGRADING;
            //Modified by Manuel Perez 05/08/2015
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the InstalationStatus enum");

        }
    }
}
