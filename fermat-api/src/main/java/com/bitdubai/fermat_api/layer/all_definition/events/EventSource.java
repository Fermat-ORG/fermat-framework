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
    ACTOR_ASSET_ISSUER("AAI"),
    ACTOR_ASSET_REDEEM_POINT("AAR"),
    ACTOR_NETWORK_SERVICE_CHAT("AC"),
    ACTOR_NETWORK_SERVICE_CRYPTO_BROKER("ACB"),
    ACTOR_NETWORK_SERVICE_CRYPTO_CUSTOMER("ACC"),
    ACTOR_NETWORK_SERVICE_ARTIST("ANSA"),
    ACTOR_NETWORK_SERVICE_FAN("ANSF"),
    ARTIST_ACTOR_CONNECTION("ARTAC"),
    BROKER_ACK_OFFLINE_PAYMENT("BAFP"),
    BROKER_ACK_ONLINE_PAYMENT("BAOP"),
    BROKER_SUBMIT_OFFLINE_MERCHANDISE("BSFM"),
    BROKER_SUBMIT_ONLINE_MERCHANDISE("BSOM"),
    BUSINESS_TRANSACTION_CLOSE_CONTRACT("BTCC"),
    BUSINESS_TRANSACTION_OPEN_CONTRACT("BTOC"),
    CHAT_ACTOR_CONNECTION("CAC"),
    CUSTOMER_ACK_OFFLINE_MERCHANDISE("CAOM"),
    CUSTOMER_ACK_ONLINE_MERCHANDISE("CAOM"),
    COMMUNICATION_CLOUD_PLUGIN("CCL"),
    CRYPTO_ADDRESS_BOOK("CAB"),
    CRYPTO_BROKER_ACTOR_CONNECTION("CBAC"),
    CRYPTO_NETWORK_BITCOIN_PLUGIN("CNB"),
    CRYPTO_NETWORK_FERMAT_PLUGIN("CNF"),
    CRYPTO_ROUTER("CCR"),
    CRYPTO_VAULT("CCV"),
    CUSTOMER_OFFLINE_PAYMENT("CFP"),
    CUSTOMER_ONLINE_PAYMENT("COP"),
    DEVICE_CONNECTIVITY("DCO"),
    DISCOUNT_WALLET_BASIC_WALLET_PLUGIN("DWB"),
    INCOMING_EXTRA_USER("IEU"),
    OUTGOING_INTRA_USER("OIU"),
    INCOMING_INTRA_USER("IIU"),
    MIDDLEWARE_APP_RUNTIME_PLUGIN("MAR"),
    MIDDLEWARE_CHAT_MANAGER("MWCM"),
    MIDDLEWARE_MONEY_REQUEST_PLUGIN("MMR"),
    MIDDLEWARE_WALLET_CONTACTS_PLUGIN("MWC"),
    MIDDLEWARE_WALLET_PLUGIN("MW0"),
    MODULE_WALLET_MANAGER_PLUGIN("MWM"),
    NEGOTIATION_TRANSACTION_NEW("NTNW"),
    NEGOTIATION_TRANSACTION_UPDATE("NTUP"),
    NEGOTIATION_TRANSACTION_CLOSE("NTCL"),
    NETWORK_SERVICE_ACTOR_ASSET_USER("NSAAU"),
    NETWORK_SERVICE_ACTOR_ASSET_ISSUER("NSAIU"),
    NETWORK_SERVICE_ACTOR_ASSET_REDEEM_POINT("NSARU"),
    NETWORK_SERVICE_ASSET_TRANSMISSION("NSAT"),
    NETWORK_SERVICE_CHAT("NSCHT"),
    NETWORK_SERVICE_CRYPTO_ADDRESSES("NSCAD"),
    NETWORK_SERVICE_CRYPTO_PAYMENT_REQUEST("NSCPR"),
    NETWORK_SERVICE_CRYPTO_TRANSMISSION("NCT"),
    NETWORK_SERVICE_INTRA_ACTOR("NSIA"),
    NETWORK_SERVICE_FERMAT_MONITOR("NSFM"),
    NETWORK_SERVICE_INTRA_USER_PLUGIN("NIU"),
    NETWORK_SERVICE_MONEY_REQUEST_PLUGIN("NMR"),
    NETWORK_SERVICE_MONEY_PLUGIN("NSM"),
    NETWORK_SERVICE_TEMPLATE_PLUGIN("NTP"),
    NETWORK_SERVICE_TRANSACTION_TRANSMISSION("NTT"),
    NETWORK_SERVICE_NEGOTIATION_TRANSMISSION("NNT"),
    NETWORK_SERVICE_WALLET_RESOURCES_PLUGIN("NWR"),
    NETWORK_SERVICE_WALLET_COMMUNITY_PLUGIN("NWC"),
    USER_DEVICE_USER_PLUGIN("UDU"),
    USER_INTRA_USER_PLUGIN("UIU"),
    WORLD_BLOCKCHAIN_INFO_PLUGIN("WBI"),
    WS_COMMUNICATION_CLOUD_CLIENT_PLUGIN("WSCCLCL"),
    USER_LEVEL_CUSTOMER_BROKER_PURCHASE_MANAGER("ULCBPM"),
    USER_LEVEL_CUSTOMER_BROKER_SALE_MANAGER("ULCBSM"),
    TIMEOUT_NOTIFIER("TON"),
    CCP_OUTGOING_DRAFT_TRANSACTION("CCPODT"),

    NETWORK_CLIENT("NETWORK_CLIENT"),;


    private final String code;

    EventSource(String Code) {
        this.code = Code;
    }


    public static EventSource getByCode(String code) throws InvalidParameterException {

        switch (code) {

            case "AOB":
                return ASSETS_OVER_BITCOIN_VAULT;
            case "AAU":
                return ACTOR_ASSET_USER;
            case "AAI":
                return ACTOR_ASSET_ISSUER;
            case "AAR":
                return ACTOR_ASSET_REDEEM_POINT;
            case "AC":
                return ACTOR_NETWORK_SERVICE_CHAT;
            case "ACB":
                return ACTOR_NETWORK_SERVICE_CRYPTO_BROKER;
            case "ACC":
                return ACTOR_NETWORK_SERVICE_CRYPTO_CUSTOMER;
            case "ANSA":
                return ACTOR_NETWORK_SERVICE_ARTIST;
            case "ANSF":
                return ACTOR_NETWORK_SERVICE_FAN;
            case "ARTAC":
                return ARTIST_ACTOR_CONNECTION;
            case "BAFP":
                return BROKER_ACK_OFFLINE_PAYMENT;
            case "BAOP":
                return BROKER_ACK_ONLINE_PAYMENT;
            case "BSFM":
                return BROKER_SUBMIT_OFFLINE_MERCHANDISE;
            case "BSOM":
                return BROKER_SUBMIT_ONLINE_MERCHANDISE;
            case "BTCC":
                return BUSINESS_TRANSACTION_CLOSE_CONTRACT;
            case "BTOC":
                return BUSINESS_TRANSACTION_OPEN_CONTRACT;
            case "NTNW":
                return NEGOTIATION_TRANSACTION_NEW;
            case "NTUP":
                return NEGOTIATION_TRANSACTION_UPDATE;
            case "NTCL":
                return NEGOTIATION_TRANSACTION_CLOSE;
            case "CAC":
                return CHAT_ACTOR_CONNECTION;
            case "CCL":
                return COMMUNICATION_CLOUD_PLUGIN;
            case "CAB":
                return CRYPTO_ADDRESS_BOOK;
            case "CBAC":
                return CRYPTO_BROKER_ACTOR_CONNECTION;
            case "CNB":
                return CRYPTO_NETWORK_BITCOIN_PLUGIN;
            case "CNF":
                return CRYPTO_NETWORK_FERMAT_PLUGIN;
            case "CCR":
                return CRYPTO_ROUTER;
            case "CCV":
                return CRYPTO_VAULT;
            case "DCO":
                return DEVICE_CONNECTIVITY;
            case "DWB":
                return DISCOUNT_WALLET_BASIC_WALLET_PLUGIN;
            case "IEU":
                return INCOMING_EXTRA_USER;
            case "IIU":
                return INCOMING_INTRA_USER;
            case "MAR":
                return MIDDLEWARE_APP_RUNTIME_PLUGIN;
            case "MWCM":
                return MIDDLEWARE_CHAT_MANAGER;
            case "MMR":
                return MIDDLEWARE_MONEY_REQUEST_PLUGIN;
            case "MWC":
                return MIDDLEWARE_WALLET_CONTACTS_PLUGIN;
            case "MW0":
                return MIDDLEWARE_WALLET_PLUGIN;
            case "MWM":
                return MODULE_WALLET_MANAGER_PLUGIN;
            case "NSAAU":
                return NETWORK_SERVICE_ACTOR_ASSET_USER;
            case "NSAIU":
                return NETWORK_SERVICE_ACTOR_ASSET_ISSUER;
            case "NSARU":
                return NETWORK_SERVICE_ACTOR_ASSET_REDEEM_POINT;
            case "NSAT":
                return NETWORK_SERVICE_ASSET_TRANSMISSION;
            case "NSCAD":
                return NETWORK_SERVICE_CRYPTO_ADDRESSES;
            case "NSCHT":
                return NETWORK_SERVICE_CHAT;
            case "NSCPR":
                return NETWORK_SERVICE_CRYPTO_PAYMENT_REQUEST;
            case "NCT":
                return NETWORK_SERVICE_CRYPTO_TRANSMISSION;
            case "NIU":
                return NETWORK_SERVICE_INTRA_USER_PLUGIN;
            case "NSIA":
                return NETWORK_SERVICE_INTRA_ACTOR;
            case "NSFM":
                return NETWORK_SERVICE_FERMAT_MONITOR;
            case "NSM":
                return NETWORK_SERVICE_MONEY_PLUGIN;
            case "NMR":
                return NETWORK_SERVICE_MONEY_REQUEST_PLUGIN;
            case "NTP":
                return NETWORK_SERVICE_TEMPLATE_PLUGIN;
            case "NTT":
                return NETWORK_SERVICE_TRANSACTION_TRANSMISSION;
            case "NNT":
                return NETWORK_SERVICE_NEGOTIATION_TRANSMISSION;
            case "NWC":
                return NETWORK_SERVICE_WALLET_COMMUNITY_PLUGIN;
            case "NWR":
                return NETWORK_SERVICE_WALLET_RESOURCES_PLUGIN;
            case "UDU":
                return USER_DEVICE_USER_PLUGIN;
            case "UIU":
                return USER_INTRA_USER_PLUGIN;
            case "WBI":
                return WORLD_BLOCKCHAIN_INFO_PLUGIN;
            case "WSCCC":
                return WS_COMMUNICATION_CLOUD_CLIENT_PLUGIN;
            case "ULCBPM":
                return USER_LEVEL_CUSTOMER_BROKER_PURCHASE_MANAGER;
            case "ULCBSM":
                return USER_LEVEL_CUSTOMER_BROKER_SALE_MANAGER;
            case "TON":
                return TIMEOUT_NOTIFIER;
            case "CCPODT":
                return CCP_OUTGOING_DRAFT_TRANSACTION;

            case "NETWORK_CLIENT":
                return NETWORK_CLIENT;

            default:
                throw new InvalidParameterException(
                        new StringBuilder().append("Code Received: ").append(code).toString(),
                        "The code received is not valid for EventSource enum."
                );

        }

    }

    @Override
    public String getCode() {
        return this.code;
    }

}
