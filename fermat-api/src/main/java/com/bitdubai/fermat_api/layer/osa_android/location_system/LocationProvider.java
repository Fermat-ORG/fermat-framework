package com.bitdubai.fermat_api.layer.osa_android.location_system;

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

public enum LocationProvider {

    //Modified by Manuel Perez on 05/08/2015
    GPS("GPS"),
    NETWORK("NETWORK");

    private String code;

    LocationProvider(String code){

        this.code=code;

    }

    public String getCode(){

        return this.code;

    }

    public static LocationProvider getByCode(String code)throws InvalidParameterException{

        switch (code){

            case "GPS":
                return LocationProvider.GPS;
            case "NETWORK":
                return LocationProvider.NETWORK;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the LocationProvider enum");


        }

    }

}
