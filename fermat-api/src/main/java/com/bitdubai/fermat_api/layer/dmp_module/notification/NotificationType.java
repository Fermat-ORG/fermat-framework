package com.bitdubai.fermat_api.layer.dmp_module.notification;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Matias Furszyfer on 2015.09.04..
 */
public enum NotificationType {

    /**
     *  Definitions types
     */
    INCOMING_MONEY   ("IM"),
    INCOMING_CONNECTION     ("IC"),
    MONEY_REQUEST ("MR"),
    CLOUD_CONNECTED_NOTIFICATION("CCN"),
    INCOMING_INTRA_ACTOR_REQUUEST_CONNECTION_NOTIFICATION("IIARCN");

    /**
     * Represent the key
     */
    private String code;

    /**
     * Constructor
     *
     * @param code
     */
    NotificationType(String code) {
        this.code = code;
    }

    /**
     * Get the code representation
     *
     * @return String
     */
    public String getCode()   { return this.code; }

    /**
     * Get the FactoryProjectType representation from code
     *
     * @param code
     * @return InformationPublishedComponentType
     * @throws InvalidParameterException
     */
    public static NotificationType getByCode(String code) throws InvalidParameterException {

        switch(code) {
            case"IM":
                return INCOMING_MONEY;
            case"IC":
                return INCOMING_CONNECTION;
            case"MR":
                return MONEY_REQUEST;
            case "CCN":
                return CLOUD_CONNECTED_NOTIFICATION;
            case "IIARCN":
                return INCOMING_INTRA_ACTOR_REQUUEST_CONNECTION_NOTIFICATION;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the FactoryProjectType enum");

        }
    }

    /**
     * (non-Javadoc)
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return getCode();
    }
}
