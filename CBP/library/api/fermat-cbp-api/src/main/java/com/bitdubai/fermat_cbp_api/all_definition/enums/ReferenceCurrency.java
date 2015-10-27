package com.bitdubai.fermat_cbp_api.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.InvalidParameterException;

/**
 * Created by angel on 18/9/15.
 */
public enum ReferenceCurrency implements FermatEnum {
    DOLAR("DOL"),
    EURO("EUR");

    private String code;

    ReferenceCurrency(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    public static ReferenceCurrency getByCode(String code) throws InvalidParameterException {
        switch (code) {
            case "DOL": return ReferenceCurrency.DOLAR;
            case "EUR": return ReferenceCurrency.EURO;
            default: throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the ContactState enum");
        }
    }
}
