package com.bitdubai.fermat_api.layer.all_definition.events;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by ciencias on 24.01.15.
 * Modified by Leon Acosta (laion.cj91@gmail.com) on 07/10/2015.
 */
public enum EventSource implements FermatEnum {

    /**
     * For doing the code more readable, please keep the elements in the Enum sorted alphabetically.
     */

    ASSETS_OVER_BITCOIN_VAULT("AOB"),
    ACTOR_ASSET_USER("AAU"),
    COMMUNICATION_CLOUD_PLUGIN("CCL"),
    CRYPTO_ADDRESS_BOOK("CAB"),
    CRYPTO_NETWORK_BITCOIN_PLUGIN("CNB"),
    CRYPTO_ROUTER("CCR"),
    CRYPTO_VAULT("CCV"),
    DEVICE_CONNECTIVITY("DCO"),
    DISCOUNT_WALLET_BASIC_WALLET_PLUGIN("DWB"),
    INCOMING_EXTRA_USER("IEU"),
    INCOMING_INTRA_USER("IIU"),
    MIDDLEWARE_APP_RUNTIME_PLUGIN("MAR"),
    MIDDLEWARE_MONEY_REQUEST_PLUGIN("MMR"),
    MIDDLEWARE_WALLET_CONTACTS_PLUGIN("MWC"),
    MIDDLEWARE_WALLET_PLUGIN("MW0"),
    MODULE_WALLET_MANAGER_PLUGIN("MWM"),
    NETWORK_SERVICE_ACTOR_ASSET_USER("NSAAU"),
    NETWORK_SERVICE_ACTOR_ASSET_ISSUER("NSAIU"),
    NETWORK_SERVICE_ACTOR_ASSET_REDEEM_POINT("NSARU"),
    NETWORK_SERVICE_ASSET_TRANSMISSION("NSAT"),
    NETWORK_SERVICE_CRYPTO_ADDRESSES("NSCAD"),
    NETWORK_SERVICE_CRYPTO_PAYMENT_REQUEST("NSCPR"),
    NETWORK_SERVICE_CRYPTO_TRANSMISSION("NCT"),
    NETWORK_SERVICE_INTRA_ACTOR("NSIA"),
    NETWORK_SERVICE_INTRA_USER_PLUGIN("NIU"),
    NETWORK_SERVICE_MONEY_REQUEST_PLUGIN("NMR"),
    NETWORK_SERVICE_MONEY_PLUGIN("NSM"),
    NETWORK_SERVICE_TEMPLATE_PLUGIN("NTP"),
    NETWORK_SERVICE_WALLET_RESOURCES_PLUGIN("NWR"),
    NETWORK_SERVICE_WALLET_COMMUNITY_PLUGIN("NWC"),
    USER_DEVICE_USER_PLUGIN("UDU"),
    USER_INTRA_USER_PLUGIN("UIU"),
    WORLD_BLOCKCHAIN_INFO_PLUGIN("WBI"),
    WS_COMMUNICATION_CLOUD_CLIENT_PLUGIN("WSCCLCL"),;

    private final String code;

    EventSource(String Code) {
        this.code = Code;
    }


    public static EventSource getByCode(String code) throws InvalidParameterException {
        for (EventSource e : EventSource.values()) {
            if (e.code.equals(code)) {
                return e;
            }
        }
        throw new InvalidParameterException(
                "Code Received: " + code,
                "The code received is not valid for EventSource enum."
        );
    }

    @Override
    public String getCode() {
        return this.code;
    }

}
