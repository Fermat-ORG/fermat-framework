package org.fermat.fermat_dap_api.layer.all_definition.enums;


import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

import java.io.Serializable;

/**
 * The enum <code>com.bitdubai.fermat_dap_api.layer.all_definition.enums.ConnectionState</code>
 * enumerates the states of connection of a common Fermat Actor Asset.
 * <p/>
 * Created by Nerio on 10/11/15.
 */
public enum DAPConnectionState implements FermatEnum, Serializable {

    /**
     * For doing the code more readable, please keep the elements in the Enum sorted alphabetically.
     */
    BLOCKED_LOCALLY("BKDLY"),//LOCAL ACTOR BLOCKED LOCALLY IN FERMAT
    BLOCKED_REMOTELY("BKDRY"),//ACTOR BLOCKED REMOTELY IN FERMAT
    CANCELLED_LOCALLY("CLDLY"),//LOCAL ACTOR CANCEL REQUEST LOCALLY IN FERMAT
    CANCELLED_REMOTELY("CLDRY"),//ACTOR CANCEL REQUEST REMOTELY IN FERMAT
    CONNECTED_ONLINE("CTDON"),//ACTOR WITH CRYPTO ADDRESS OR KEY EXTENDED AND ON-LINE IN FERMAT
    CONNECTED_OFFLINE("CTDOF"),//ACTOR WITH CRYPTO ADDRESS OR KEY EXTENDED AND OFFLINE IN FERMAT
    CONNECTING("CTING"),//PROCESS CONNECTING ACTORS EQUAL OR DIFFERENT IN FERMAT
    CONNECTED("CNTED"),//PROCESS CONNECTED SUCCESSFULLY COMPLETE BETWEEN ACTORS IN FERMAT
    DENIED_LOCALLY("DIELY"),//LOCAL ACTOR DENIED REQUEST LOCALLY IN FERMAT
    DENIED_REMOTELY("DIERY"),//ACTOR DENIED REQUEST REMOTELY IN FERMAT
    DISCONNECTED_LOCALLY("DEDLY"),//LOCAL ACTOR DISCONNECTED ON PROBLEM
    DISCONNECTED_REMOTELY("DEDRY"),//ACTOR DISCONNECTED ON PROBLEM IN NS FERMAT
    ERROR_UNKNOWN("ERROR"),//ERROR WITH ACTOR UNKNOWN
    PENDING_LOCALLY("PNGLY"),//LOCAL ACTOR SENDING REQUEST TO ANOTHER ACTOR ACROSS NS IN FERMAT
    PENDING_REMOTELY("PNGRY"),//ACTOR WAITING REPLY TO ANOTHER ACTOR ACROSS NS IN FERMAT
    REGISTERED_LOCALLY("RGDLY"),//LOCAL ACTOR CREATED SUCCESSFULLY
    REGISTERED_REMOTELY("RGDRY"),//LOCAL ACTOR REGISTERED REMOTELY IN FERMAT
    REGISTERED_ONLINE("RGDON"),//LOCAL ACTOR REGISTERED AND ACTIVE IN FERMAT
    REGISTERED_OFFLINE("RGDOF"),//ACTOR REGISTERED BUT OFFLINE IN FERMAT
    ;

    private final String code;

    DAPConnectionState(final String code) {
        this.code = code;
    }

    @Override
    public final String getCode() {
        return this.code;
    }

    public static DAPConnectionState getByCode(final String code) throws InvalidParameterException {

        switch (code) {

            case "BKDLY":
                return BLOCKED_LOCALLY;
            case "BKDRY":
                return BLOCKED_REMOTELY;
            case "CLDLY":
                return CANCELLED_LOCALLY;
            case "CLDRY":
                return CANCELLED_REMOTELY;
            case "CTDON":
                return CONNECTED_ONLINE;
            case "CTDOF":
                return CONNECTED_OFFLINE;
            case "CTING":
                return CONNECTING;
            case "CNTED":
                return CONNECTED;
            case "DIELY":
                return DENIED_LOCALLY;
            case "DIERY":
                return DENIED_REMOTELY;
            case "DEDLY":
                return DISCONNECTED_LOCALLY;
            case "DEDRY":
                return DISCONNECTED_REMOTELY;
            case "ERROR":
                return ERROR_UNKNOWN;
            case "PNGLY":
                return PENDING_LOCALLY;
            case "PNGRY":
                return PENDING_REMOTELY;
            case "RGDLY":
                return REGISTERED_LOCALLY;
            case "RGDRY":
                return REGISTERED_REMOTELY;
            case "RGDON":
                return REGISTERED_ONLINE;
            case "RGDOF":
                return REGISTERED_OFFLINE;

            default:
                throw new InvalidParameterException(
                        "Code Received: " + code,
                        "The code received is not valid for the DAPConnectionState Enum"
                );
        }
    }
}
