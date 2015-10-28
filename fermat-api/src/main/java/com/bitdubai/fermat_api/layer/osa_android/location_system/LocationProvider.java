package com.bitdubai.fermat_api.layer.osa_android.location_system;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 *
 *  <p>The public enum <code>LocationManager</code>
 *     define the type of network location.
 *
 *
 *  @author  Natalia
 *  @version 1.0.0
 *  @since   07/05/15.
 * */

public enum LocationProvider implements FermatEnum {

    GPS("GPS"),
    NETWORK("NET"),;

    private final String code;

    LocationProvider(final String code) {

        this.code = code;
    }

    public static LocationProvider getByCode(String code) throws InvalidParameterException {

        switch (code){

            case "GPS":
                return GPS;
            case "NET":
                return NETWORK;

            default:
                throw new InvalidParameterException(
                        "Code Received: " + code,
                        "This Code Is Not Valid for the LocationProvider enum"
                );
        }
    }

    @Override
    public String getCode() {
        return this.code;
    }

}
