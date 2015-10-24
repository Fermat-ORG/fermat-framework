package com.bitdubai.fermat_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * The Class <code>com.bitdubai.fermat_api.layer.middleware.wallet_factory.enums.WalletFactoryProjectState</code>
 * enumerates type of Resources.
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 15/07/15.
 * @version 1.0
 * @since Java JDK 1.7
 */
public enum WalletFactoryProjectState implements FermatEnum {

    IN_PROGRESS("In_Progress"),//draft
    CLOSED("Closed"),//versioned
    PUBLISHED("Published"),
    DELETED("Deleted");//dismissed

    public static WalletFactoryProjectState getByCode(String key) throws InvalidParameterException {
        switch(key) {
            case"In_Progress": return IN_PROGRESS;
            case"Closed":      return CLOSED;
            case"Published":   return PUBLISHED;
            case"Deleted":     return DELETED;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + key, "This Code Is Not Valid for the Plugins enum");
        }
        //throw new InvalidParameterException(key);
        //return null;
    }

    //Modified by Manuel perez on 05/08/2015
    private String code;

    WalletFactoryProjectState(String code) {
        this.code = code;
    }

    public String value() { return this.code; }

    @Override
    public String getCode() { return this.code; }
}
