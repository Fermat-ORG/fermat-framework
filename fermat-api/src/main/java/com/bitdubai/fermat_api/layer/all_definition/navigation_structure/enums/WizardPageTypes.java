package com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums;

/**
 * Wizard Pages Enumerable
 *
 * @author Francisco Vásquez
 * @version 1.0
 */
public enum WizardPageTypes {

    CWP_WALLET_FACTORY_CREATE_STEP_1("CWFCS1"),
    CWP_WALLET_FACTORY_CREATE_STEP_2("CWFCS2"),

    CWP_WALLET_PUBLISHER_PUBLISH_STEP_1("CWPPS1"),
    CWP_WALLET_PUBLISHER_PUBLISH_STEP_2("CWPPS2");

    private String code;

    WizardPageTypes(String code) {
        this.code = code;
    }

    public String getKey() {
        return this.code;
    }

    public String toString() {
        return code;
    }

    public static WizardPageTypes getValueFromString(String code) {
        switch (code) {
            case "CWFCS1":
                return WizardPageTypes.CWP_WALLET_FACTORY_CREATE_STEP_1;
            case "CWFCS2":
                return WizardPageTypes.CWP_WALLET_FACTORY_CREATE_STEP_2;
            case "CWPPS1":
                return WizardPageTypes.CWP_WALLET_PUBLISHER_PUBLISH_STEP_1;
            case "CWPPS2":
                return WizardPageTypes.CWP_WALLET_PUBLISHER_PUBLISH_STEP_2;
            default:
                break;
        }
        return null;
    }
}
