package com.bitdubai.fermat_dap_api.layer.all_definition.enums;


import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * The enum <code>com.bitdubai.fermat_dap_api.layer.all_definition.enums.ConnectionState</code>
 * enumerates the states of connection of a common Fermat Actor Asset.
 *
 * Created by Nerio on 10/11/15.
 */
public enum DAPConnectionState implements FermatEnum {

    /**
     * For doing the code more readable, please keep the elements in the Enum sorted alphabetically.
     */
    BLOCKED_LOCALLY               ("BKDLY"),//LOCAL ACTOR BLOCKED LOCALLY IN FERMAT
    BLOCKED_REMOTELY              ("BKDRY"),//ACTOR BLOCKED REMOTELY IN FERMAT
    CANCELLED_LOCALLY             ("CLDLY"),//LOCAL ACTOR CANCEL REQUEST LOCALLY IN FERMAT
    CANCELLED_REMOTELY            ("CLDRY"),//ACTOR CANCEL REQUEST REMOTELY IN FERMAT
    CONNECTED_ONLINE              ("CTDON"),//ACTOR WITH CRYPTO ADDRESS OR KEY EXTENDED AND ON-LINE IN FERMAT
    CONNECTED_OFFLINE             ("CTDOF"),//ACTOR WITH CRYPTO ADDRESS OR KEY EXTENDED AND OFFLINE IN FERMAT
    CONNECTING                    ("CTING"),//PROCESS CONNECTING ACTORS EQUAL OR DIFFERENT IN FERMAT
    CONNECTED                     ("CNTED"),//PROCESS CONNECTED SUCCESSFULLY COMPLETE BETWEEN ACTORS IN FERMAT
    DENIED_LOCALLY                ("DEDLY"),//LOCAL ACTOR DENIED REQUEST LOCALLY IN FERMAT
    DENIED_REMOTELY               ("DEDRY"),//ACTOR DENIED REQUEST REMOTELY IN FERMAT
    DISCONNECTED_LOCALLY          ("DEDLY"),//LOCAL ACTOR DISCONNECTED ON PROBLEM
    DISCONNECTED_REMOTELY         ("DEDRY"),//ACTOR DISCONNECTED ON PROBLEM IN NS FERMAT
    ERROR_UNKNOWN                 ("ERROR"),//ERROR WITH ACTOR UNKNOWN
    PENDING_LOCALLY               ("PNGLY"),//LOCAL ACTOR SENDING REQUEST TO ANOTHER ACTOR ACROSS NS IN FERMAT
    PENDING_REMOTELY              ("PNGRY"),//ACTOR WAITING REPLY TO ANOTHER ACTOR ACROSS NS IN FERMAT
    REGISTERED_LOCALLY            ("RGDLY"),//LOCAL ACTOR CREATED SUCCESSFULLY
    REGISTERED_REMOTELY           ("RGDRY"),//LOCAL ACTOR REGISTERED REMOTELY IN FERMAT
    REGISTERED_ONLINE             ("RGDON"),//LOCAL ACTOR REGISTERED AND ACTIVE IN FERMAT
    REGISTERED_OFFLINE            ("RGDOF"),//ACTOR REGISTERED BUT OFFLINE IN FERMAT
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

            case "CTDON": return CONNECTED_ONLINE               ;
            case "CTDOF": return CONNECTED_OFFLINE              ;
            case "CTING": return CONNECTING                     ;
            case "RGDLY": return REGISTERED_LOCALLY             ;
            case "RGDON": return REGISTERED_ONLINE              ;
            case "RGDOF": return REGISTERED_OFFLINE             ;

            default: throw new InvalidParameterException(
                    "Code Received: " + code,
                    "The code received is not valid for the DAPConnectionState Enum"
            );
        }
    }
}
