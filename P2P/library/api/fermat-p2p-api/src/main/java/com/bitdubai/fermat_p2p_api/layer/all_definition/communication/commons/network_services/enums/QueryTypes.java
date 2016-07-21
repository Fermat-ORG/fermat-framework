package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;

/**
 * The enum class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.enums.QueryTypes</code>
 * Enums all the queries that can be done in a basic network service.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 28/06/2016.
 *
 * @author  lnacosta
 * @version 1.0
 * @since   Java JDK 1.7
 */
public enum QueryTypes implements FermatEnum {

    /**
     * To make the code more readable, please keep the elements in the Enum sorted alphabetically.
     */
    ACTOR_LIST           ("AC"),

    ;

    private final String code;

    QueryTypes(final String code) {

        this.code     = code    ;
    }

    public static QueryTypes getByCode(final String code) {

        for (QueryTypes type : QueryTypes.values()) {
            if(type.getCode().equals(code))
                return type;
        }

        throw new java.security.InvalidParameterException("Code Received: " + code+" - The received code is not valid for the QueryTypes enum");
    }

    @Override
    public String getCode() {
        return this.code;
    }

}