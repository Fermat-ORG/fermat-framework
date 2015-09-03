package com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums;

/**
 * WizardTypes enumerable
 *
 * @author Francisco Vásquez
 * @version 1.0
 */
public enum WizardTypes {

    CWP_WALLET_FACTORY_CREATE_NEW_PROJECT("CWFCNP"),
    CWP_WALLET_PUBLISHER_PUBLISH_PROJECT("CWPPP");


    private String code;

    WizardTypes(String code) {
        this.code = code;
    }

    public String getKey() {
        return this.code;
    }


    public String toString() {
        return code;
    }

    public static WizardTypes getValueFromString(String code) {
        switch (code) {
            case "CWFCNP":
                return WizardTypes.CWP_WALLET_FACTORY_CREATE_NEW_PROJECT;
            case "CWPPP":
                return WizardTypes.CWP_WALLET_PUBLISHER_PUBLISH_PROJECT;
            default:
                break;
        }
        return null;
    }
}
