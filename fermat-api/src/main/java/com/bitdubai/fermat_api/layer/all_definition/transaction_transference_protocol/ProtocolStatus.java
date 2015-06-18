package com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by eze on 09/06/15.
 */
public enum ProtocolStatus {
    NO_ACTION_REQUIRED("NAR"),
    TO_BE_NOTIFIED ("TBN"),
    SENDING_NOTIFIED ("SND"),
    RECEPTION_NOTIFIED ("RND");


    private final String code;

    ProtocolStatus(String Code) {
        this.code = Code;
    }

    public String getCode()   { return this.code ; }

    public static ProtocolStatus getByCode(String code) throws InvalidParameterException {

        switch (code) {
            case "NAR": return ProtocolStatus.NO_ACTION_REQUIRED;
            case "TBN": return ProtocolStatus.TO_BE_NOTIFIED;
            case "SND": return ProtocolStatus.SENDING_NOTIFIED;
            case "RND": return ProtocolStatus.RECEPTION_NOTIFIED;
        }

        /**
         * If we try to cpmvert am invalid string.
         */
        throw new InvalidParameterException(code);
    }
}
