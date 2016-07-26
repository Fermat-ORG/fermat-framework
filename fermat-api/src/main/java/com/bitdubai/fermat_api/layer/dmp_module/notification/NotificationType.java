package com.bitdubai.fermat_api.layer.dmp_module.notification;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Matias Furszyfer on 2015.09.04..
 */
public enum NotificationType {

    /**
     * Definitions types
     */
    INCOMING_MONEY("IM"),
    INCOMING_CONNECTION("IC"),
    MONEY_REQUEST("MR"),
    CLOUD_CONNECTED_NOTIFICATION("CCN"),
    INCOMING_INTRA_ACTOR_REQUEST_CONNECTION_NOTIFICATION("IIARCN"),
    RECEIVE_REQUEST_PAYMENT_NOTIFICATION("RRPN"),
    DENIED_REQUEST_PAYMENT_NOTIFICATION("DRPN"),
    OUTGOING_INTRA_ACTOR_ROLLBACK_TRANSACTION_NOTIFICATION("OIARTN"),
    CLOUD_CLIENT_CONNECTED("CCCONEC"),
    CLOUD_CLIENT_CLOSED("CCCLOSE"),
    CLOUD_CLIENT_CONNECTION_LOOSE("CCCL"),
    REVIEW_NOTIFICATION("RN");

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
    public String getCode() {
        return this.code;
    }

    /**
     * Get the FactoryProjectType representation from code
     *
     * @param code
     * @return InformationPublishedComponentType
     * @throws InvalidParameterException
     */
    public static NotificationType getByCode(String code) throws InvalidParameterException {

        switch (code) {
            case "IM":
                return INCOMING_MONEY;
            case "IC":
                return INCOMING_CONNECTION;
            case "MR":
                return MONEY_REQUEST;
            case "CCN":
                return CLOUD_CONNECTED_NOTIFICATION;
            case "IIARCN":
                return INCOMING_INTRA_ACTOR_REQUEST_CONNECTION_NOTIFICATION;
            case "OIARTN":
                return OUTGOING_INTRA_ACTOR_ROLLBACK_TRANSACTION_NOTIFICATION;
            case "RRPN":
                return RECEIVE_REQUEST_PAYMENT_NOTIFICATION;
            case "DRPN":
                return DENIED_REQUEST_PAYMENT_NOTIFICATION;
            case "CCCONEC":
                return CLOUD_CLIENT_CONNECTED;
            case "CCCLOSE":
                return CLOUD_CLIENT_CLOSED;
            case "CCCL":
                return CLOUD_CLIENT_CONNECTION_LOOSE;
            case "RN":
                return REVIEW_NOTIFICATION;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, new StringBuilder().append("Code Received: ").append(code).toString(), "This Code Is Not Valid for the FactoryProjectType enum");

        }
    }

    /**
     * (non-Javadoc)
     *
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return getCode();
    }
}
