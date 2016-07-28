package com.bitdubai.fermat_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * The enum <code>com.bitdubai.fermat_api.layer.all_definition.enums.Country</code>
 * Contains the different Countries eligible on Fermat.
 * <p/>
 * Created by ciencias on 20.12.14. *
 * Modified by Manuel Perez on 03/08/2015
 * Modified by Alejandro Bicelis on 20/11/2015
 * Modified by pmgesualdi - (pmgesualdi@hotmail.com) on 30/11/2015.
 */
public enum Country implements FermatEnum {

    /**
     * To make the code more readable, please keep the elements in the Enum sorted alphabetically.
     */
    ARGENTINA("AR", "Argentina"),
    AUSTRALIA("AU", "Australia"),
    BRAZIL("BR", "Brazil"),
    CANADA("CA", "Canada"),
    CHILE("CL", "Chile"),
    CHINA("CN", "China"),
    COLOMBIA("CO", "Colombia"),
    EUROZONE("EU", "Eurozone"),
    GREAT_BRITAIN("GB", "Great Britain"),
    JAPAN("JP", "Japan"),
    MEXICO("MX", "Mexico"),
    NEW_ZEALAND("NZ", "New Zealand"),
    SWITZERLAND("CH", "Switzerland"),
    UNITED_STATES_OF_AMERICA("US", "United States of America"),
    VENEZUELA("VE", "Venezuela"),
    NONE("NONE", "NONE"),;

    private final String code;
    private final String mDisplayName;

    Country(final String code, final String DisplayName) {
        this.code = code;
        this.mDisplayName = DisplayName;
    }

    public static Country getByCode(String code) throws InvalidParameterException {

        switch (code) {
            case "AR":
                return Country.ARGENTINA;
            case "AU":
                return Country.AUSTRALIA;
            case "BR":
                return Country.BRAZIL;
            case "CA":
                return Country.CANADA;
            case "CL":
                return Country.CHILE;
            case "CN":
                return Country.CHINA;
            case "CO":
                return Country.COLOMBIA;
            case "EU":
                return Country.EUROZONE;
            case "GB":
                return Country.GREAT_BRITAIN;
            case "JP":
                return Country.JAPAN;
            case "MX":
                return Country.MEXICO;
            case "NZ":
                return Country.NEW_ZEALAND;
            case "CH":
                return Country.SWITZERLAND;
            case "US":
                return Country.UNITED_STATES_OF_AMERICA;
            case "VE":
                return Country.VENEZUELA;
            case "NONE":
                return Country.NONE;
            default:
                throw new InvalidParameterException(
                        new StringBuilder().append("Code Received: ").append(code).toString(),
                        "This Code Is Not Valid for the Country enum"
                );
        }
    }

    public String getCountry() {
        return mDisplayName;
    }

    @Override
    public String getCode() {
        return code;
    }
}