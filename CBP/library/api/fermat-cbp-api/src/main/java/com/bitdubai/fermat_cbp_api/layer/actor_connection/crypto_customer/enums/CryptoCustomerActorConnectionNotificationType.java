package com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_customer.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * The enum class <code>com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_broker.enums.CryptoCustomerActorConnectionNotificationType</code>
 * enumerates all the notifications of the crypto customer actor connection plug-in.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 29/02/2016.
 */
public enum CryptoCustomerActorConnectionNotificationType implements FermatEnum {


    /**
     * To do make code more readable, please keep the elements in the Enum sorted alphabetically.
     */
    CONNECTION_REQUEST_RECEIVED  ("CBP_CRYCUS_CRR"),

    ;

    private final String code;

    CryptoCustomerActorConnectionNotificationType(final String code) {
        this.code = code;
    }

    public static CryptoCustomerActorConnectionNotificationType getByCode(String code) throws InvalidParameterException {

        switch (code) {

            case "CBP_CRYCUS_CRR":  return CONNECTION_REQUEST_RECEIVED;

            default:
                throw new InvalidParameterException(
                        "Code Received: " + code,
                        "The received code is not valid for the CryptoCustomerActorConnectionNotificationType enum"
                );
        }
    }

    @Override
    public String getCode() {
        return this.code;
    }

}