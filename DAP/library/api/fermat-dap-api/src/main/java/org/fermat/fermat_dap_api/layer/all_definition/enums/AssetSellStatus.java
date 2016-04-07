package org.fermat.fermat_dap_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 9/02/16.
 */
public enum AssetSellStatus implements FermatEnum {

    //ENUM DECLARATION
    WAITING_CONFIRMATION("WACO"),
    NEGOTIATION_CONFIRMED("NECO"),
    NEGOTIATION_REJECTED("NERE"),
    NEGOTIATION_CANCELLED("NECA"),
    NEGOTIATION_FINISHED("NEFI"),
    DEAL_CHANGED("CH"),
    ADDING_INPUTS("ADIN"),
    INPUTS_ADDED("INAD"),
    WAITING_FIRST_SIGNATURE("WAFS"),
    PARTIALLY_SIGNED("PASI"),
    WAITING_COMPLETE_SIGNATURE("WACS"),
    SIGNATURE_REJECTED("SIRE"),
    COMPLETE_SIGNATURE("COSI"),
    WALLET_DEBITED("WLDB"),
    BROADCASTING("BCT"),
    SELL_FINISHED("SEFI"),
    NO_ACTION_REQUIRED("NO");

    //VARIABLE DECLARATION

    private String code;

    //CONSTRUCTORS

    AssetSellStatus(String code) {
        this.code = code;
    }

    //PUBLIC METHODS

    public static AssetSellStatus getByCode(String code) throws InvalidParameterException {
        for (AssetSellStatus fenum : values()) {
            if (fenum.getCode().equals(code)) return fenum;
        }
        throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the AssetSellStatus enum.");
    }

    //PRIVATE METHODS

    //GETTER AND SETTERS

    @Override
    public String getCode() {
        return code;
    }
}
