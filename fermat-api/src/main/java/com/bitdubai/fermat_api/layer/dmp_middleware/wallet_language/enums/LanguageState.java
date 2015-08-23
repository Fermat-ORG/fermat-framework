package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_language.enums;

/**
 * The Class <code>com.bitdubai.fermat_api.layer.middleware.wallet_factory.enums.WalletFactoryProjectState</code>
 * enumerates type of Resources.
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 30/07/15.
 * @version 1.0
 * @since Java JDK 1.7
 */
public enum LanguageState {

    DRAFT("draft"),
    VERSIONED("versioned"),
    DISMISSED("dismissed"),
    CLOSED("closed");

    public static LanguageState getByCode(String key) {
        switch(key) {
            case"draft":
                return DRAFT;
            case"versioned":
                return VERSIONED;
            case"dismissed":
                return DISMISSED;
            case"closed":
                return CLOSED;
        }
        return null;
    }

    private final String code;

    LanguageState(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

}
