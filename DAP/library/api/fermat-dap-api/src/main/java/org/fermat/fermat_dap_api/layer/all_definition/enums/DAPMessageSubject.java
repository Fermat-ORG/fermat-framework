package org.fermat.fermat_dap_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

import java.io.Serializable;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 16/02/16.
 */
public enum DAPMessageSubject implements FermatEnum, Serializable {

    //ENUM DECLARATION
    /**
     * This one will be consider as the default option
     * from which that message was created, and all the
     * plugins that are listening to that option will respond
     * to that message.
     */
    DEFAULT("DEFAULT"),
    ASSET_APPROPRIATED("ASAP"),
    ASSET_DISTRIBUTION("ASDI"),
    ASSET_TRANSFER("ASTR"),
    ASSET_MOVEMENT("ASMO"),
    NEW_SELL_STARTED("NSS"),
    TRANSACTION_SIGNED("TXSI"),
    NEGOTIATION_ANSWER("ASSE"),
    NEGOTIATION_CANCELLED("NECA"),
    NEW_NEGOTIATION_STARTED("NNS"),
    SIGNATURE_REJECTED("SIRE"),
    USER_REDEMPTION("USRE"),
    REDEEM_POINT_REDEMPTION("RPR"),
    ASSET_RECEPTION("ASRE");

    //VARIABLE DECLARATION

    private String code;

    //CONSTRUCTORS

    DAPMessageSubject(String code) {
        this.code = code;
    }

    //PUBLIC METHODS

    public static DAPMessageSubject getByCode(String code) throws InvalidParameterException {
        for (DAPMessageSubject fenum : values()) {
            if (fenum.getCode().equals(code)) return fenum;
        }
        throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the DAPMessageSubject enum.");
    }

    //PRIVATE METHODS

    //GETTER AND SETTERS

    @Override
    public String getCode() {
        return code;
    }
}
