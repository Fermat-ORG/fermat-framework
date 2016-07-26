package com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Wizard Pages Enumerable
 *
 * @author Francisco Vásquez
 * @version 1.0
 */
@SuppressWarnings("SpellCheckingInspection")
public enum WizardPageTypes {

    CWP_WALLET_FACTORY_CREATE_STEP_1("CWFCS1"),
    CWP_WALLET_FACTORY_CREATE_STEP_2("CWFCS2"),

    CWP_WALLET_PUBLISHER_PUBLISH_STEP_1("CWPPS1"),
    CWP_WALLET_PUBLISHER_PUBLISH_STEP_2("CWPPS2"),
    CWP_WALLET_PUBLISHER_PUBLISH_STEP_3("CWPPS3");

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

    public static WizardPageTypes getValueFromString(String code) throws InvalidParameterException {
        switch (code) {
            case "CWFCS1":
                return WizardPageTypes.CWP_WALLET_FACTORY_CREATE_STEP_1;
            case "CWFCS2":
                return WizardPageTypes.CWP_WALLET_FACTORY_CREATE_STEP_2;
            case "CWPPS1":
                return WizardPageTypes.CWP_WALLET_PUBLISHER_PUBLISH_STEP_1;
            case "CWPPS2":
                return WizardPageTypes.CWP_WALLET_PUBLISHER_PUBLISH_STEP_2;
            case "CWPPS3":
                return WizardPageTypes.CWP_WALLET_PUBLISHER_PUBLISH_STEP_3;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, new StringBuilder().append("Code Received: ").append(code).toString(), "This Code Is Not Valid for the Plugins enum");

        }
        //return null;
    }
}
