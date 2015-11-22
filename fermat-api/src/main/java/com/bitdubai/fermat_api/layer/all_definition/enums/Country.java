package com.bitdubai.fermat_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by ciencias on 20.12.14.
 */
public enum Country implements FermatEnum {

    ARGENTINA("AR", "Argentina"),
    AUSTRALIA("AU", "Australia"),
    BRAZIL("BR", "Brazil"),
    GREAT_BRITAIN("GB", "Great Britain"),
    CANADA("CA", "Canada"),
    CHILE("CL", "Chile"),
    CHINA("CN", "China"),
    COLOMBIA("CO", "Colombia"),
    EUROZONE("EU", "Eurozone"),
    JAPAN("JP", "Japan"),
    MEXICO("MX", "Mexico"),
    NEW_ZEALAND("NZ", "New Zealand"),
    SWITZERLAND("CH", "Switzerland"),
    UNITED_STATES_OF_AMERICA("US", "United States of America"),
    VENEZUELA("VE", "Venezuela"),
    ;

    private String code;
    private String mDisplayName;

    Country(String code, String DisplayName) {
        this.code = code;
        this.mDisplayName = DisplayName;
    }

    @Override
    public String getCode() {
        return code;
    }

    public static Country getByCode(String code) throws InvalidParameterException {

        switch (code) {
            case "AR": return Country.ARGENTINA;
            case "AU": return Country.AUSTRALIA;
            case "BR": return Country.BRAZIL;
            case "GB": return Country.GREAT_BRITAIN;
            case "CA": return Country.CANADA;
            case "CL": return Country.CHILE;
            case "CN": return Country.CHINA;
            case "CO": return Country.COLOMBIA;
            case "EU": return Country.EUROZONE;
            case "JP": return Country.JAPAN;
            case "MX": return Country.MEXICO;
            case "NZ": return Country.NEW_ZEALAND;
            case "CH": return Country.SWITZERLAND;
            case "US": return Country.UNITED_STATES_OF_AMERICA;
            case "VE": return Country.VENEZUELA;
            //Modified by Manuel Perez on 03/08/2015
            //Modified by Alejandro Bicelis on 20/11/2015
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the Country enum");
        }
    }

    public String getCountry() {
        return mDisplayName;
    }
}