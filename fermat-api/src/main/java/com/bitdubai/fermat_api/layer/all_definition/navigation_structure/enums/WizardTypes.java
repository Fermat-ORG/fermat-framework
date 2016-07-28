package com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * WizardTypes enumerable
 *
 * @author Francisco Vásquez
 * @version 1.0
 */
//todo:  Esta clase tiene que volar al igual que la de fragments y la de activities
public enum WizardTypes {

    CWP_WALLET_FACTORY_CREATE_NEW_PROJECT("CWFCNP"),
    CWP_WALLET_PUBLISHER_PUBLISH_PROJECT("CWPPP"),
    CCP_WALLET_BITCOIN_START_WIZARD("CCPWBSW"),
    CBP_WALLET_CRYPTO_BROKER_START_WIZARD("CBPWCBSW"),
    CBP_WALLET_CRYPTO_CUSTOMER_START_WIZARD("CBPWCCSW"),

    DESKTOP_WELCOME_WIZARD("DWW");


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

    public static WizardTypes getValueFromString(String code) throws InvalidParameterException {
        switch (code) {
            case "CWFCNP":
                return WizardTypes.CWP_WALLET_FACTORY_CREATE_NEW_PROJECT;
            case "CWPPP":
                return WizardTypes.CWP_WALLET_PUBLISHER_PUBLISH_PROJECT;
            case "CCPWBSW":
                return CCP_WALLET_BITCOIN_START_WIZARD;
            case "CBPWCBSW":
                return CBP_WALLET_CRYPTO_BROKER_START_WIZARD;
            case "CBPWCCSW":
                return CBP_WALLET_CRYPTO_CUSTOMER_START_WIZARD;
            case "DWW":
                return DESKTOP_WELCOME_WIZARD;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, new StringBuilder().append("Code Received: ").append(code).toString(), "This Code Is Not Valid for the Plugins enum");
        }
        //return null;
    }
}
