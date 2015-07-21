package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * The Class <code>com.bitdubai.fermat_api.layer.middleware.wallet_factory.enums.FactoryProjectState</code>
 * enumerates type of Resources.
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 15/07/15.
 * @version 1.0
 * @since Java JDK 1.7
 */
public enum FactoryProjectState {
    DRAFT("draft"),
    VERSIONED("versioned"),
    DISMISSED("dismissed");

    public static FactoryProjectState fromValue(String key) throws InvalidParameterException {
        switch(key) {
            case"draft":
                return DRAFT;
            case"versioned":
                return VERSIONED;
            case"dismissed":
                return DISMISSED;
        }
        throw new InvalidParameterException(key);
    }

    private final String key;

    FactoryProjectState(String key) {
        this.key = key;
    }

    public String value() { return this.key; }
}
