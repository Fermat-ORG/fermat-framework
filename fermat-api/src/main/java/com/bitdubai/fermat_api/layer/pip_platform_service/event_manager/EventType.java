/*
 * @#EventType.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_api.layer.pip_platform_service.event_manager;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * The enum <code>com.bitdubai.fermat_dmp_plugin.layer._11_network_service.intra_user.developer.bitdubai.version_1.structure.IntraUserNetworkServiceMessage</code>
 * represent the different type for the events<p/>
 *
 * Created by ciencias on 24/01/15.
 * Update by Roberto Requena - (rart3001@gmail.com) on 24/06/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public enum EventType {

    /**
     * The enum values
     */
    DEVICE_USER_CREATED("DUC"),
    DEVICE_USER_LOGGED_IN ("DLI"),
    DEVICE_USER_LOGGED_OUT ("DLO"),
    INTRA_USER_LOGGED_IN ("ILI"),
    INTRA_USER_LOGGED_OUT ("ILO"),
    INTRA_USER_CONTACT_CREATED ("ICC"),
    INCOMING_NETWORK_SERVICE_CONNECTION_REQUEST ("INSCR"),
    ESTABLISHED_NETWORK_SERVICE_CONNECTION("SNSC"),
    NEW_NETWORK_SERVICE_MESSAGE_RECEIVE("NNSMR"),

    WALLET_CREATED ("WC1"),
    WALLET_WENT_ONLINE ("WWO"),
    WALLET_INSTALLED ("WI1"),
    BEGUN_WALLET_INSTALLATION ("BWI"),
    WALLET_UNINSTALLED ("WU1"),
    WALLET_CLOSED ("WC2"),
    WALLET_OPENED ("WO1"),
    WALLET_RESOURCES_INSTALLED ("WRI"),
    NAVIGATION_STRUCTURE_UPDATED ("NSU"),
    MONEY_RECEIVED ("MR1"),
    FINISHED_WALLET_INSTALLATION ("FWI"),

    INCOMING_CRYPTO_TRANSACTIONS_WAITING_TRANSFERENCE ("TWT"),
    INCOMING_CRYPTO_TRANSACTIONS_WAITING_TRANSFERENCE_EXTRA_USER ("TWE"),


    INCOMING_CRYPTO_RECEIVED ("ICR"),
    INCOMING_CRYPTO_RECEPTION_CONFIRMED ("IIRC"),
    INCOMING_CRYPTO_REVERSED ("ICREV"),
    INCOMING_CRYPTO_IDENTIFIED ("ICI"),

    /**
     * New incoming crypto events
     */
    INCOMING_CRYPTO_ON_CRYPTO_NETWORK("ICOCN"),
    INCOMING_CRYPTO_ON_BLOCKCHAIN("ICOBC"),
    INCOMING_CRYPTO_IRREVERSIBLE("ICIRR"),
    INCOMING_CRYPTO_REVERSED_ON_CRYPTO_NETWORK("ICROCN"),
    INCOMING_CRYPTO_REVERSED_ON_BLOCKCHAIN("ICROBC"),


    INCOMING_CRYPTO_IDENTIFIED_FROM_EXTRA_USER ("ICIEU"),
    INCOMING_CRYPTO_IDENTIFIED_FROM_INTRA_USER ("ICIIU"),
    INCOMING_CRYPTO_IDENTIFIED_FROM_DEVICE_USER ("ICIDU"),
    INCOMING_CRYPTO_RECEIVED_FROM_EXTRA_USER ("ICREU"),
    INCOMING_CRYPTO_RECEIVED_FROM_INTRA_USER ("ICRIU"),
    INCOMING_CRYPTO_RECEIVED_FROM_DEVICE_USER ("ICRDU"),
    INCOMING_CRYPTO_RECEPTION_CONFIRMED_FROM_EXTRA_USER ("ICCEU"),
    INCOMING_CRYPTO_RECEPTION_CONFIRMED_FROM_INTRA_USER ("ICCIU"),
    INCOMING_CRYPTO_RECEPTION_CONFIRMED_FROM_DEVICE_USER ("ICCDU"),
    INCOMING_CRYPTO_REVERSED_FROM_EXTRA_USER ("REVEU"),
    INCOMING_CRYPTO_REVERSED_FROM_INTRA_USER ("REVIU"),
    INCOMING_CRYPTO_REVERSED_FROM_DEVICE_USER ("REVDU"),

    INCOMING_MONEY_REQUEST_RECEIVED ("IMRR"),
    OUTGOING_MONEY_REQUEST_DELIVERED ("OMRD"),
    OUTGOING_MONEY_REQUEST_APPROVED ("OMRA"),
    OUTGOING_MONEY_REQUEST_REJECTED ("OMRRJ"),
    INCOMING_MONEY_REQUEST_APPROVED ("IMRA"),
    INCOMING_MONEY_REQUEST_REJECTED ("IMRRJ"),

    DEVICE_CONNECTIVITY_NETWORK_CHANGE ("DCNC"),

    BITCOIN_NEW_PEER_CONNECTED ("BNPC"),
    BITCOIN_NEW_CRYPTO_WALLET_CREATED ("BNCWC"),
    BITCOIN_CRYPTO_WALLET_LOADED ("BCWL"),
    BITCOIN_BLOCKCHAIN_DOWNLOADED ("BBD"),
    BITCOIN_NEW_PEER_CONNECTED_INTRA_USER ("BNPCIU"),

    /**
     * Issue #543
     */
    INCOMING_CRYPTO_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_EXTRA_USER("ICOCNWTEU"),
    INCOMING_CRYPTO_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_EXTRA_USER("ICOBWTEU"),
    INCOMING_CRYPTO_REVERSED_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_EXTRA_USER("ICROCNWTEU"),
    INCOMING_CRYPTO_REVERSED_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_EXTRA_USER("ICROBWTEU");

    /**
     * Represent the code of the message status
     */
    private final String code;

    /**
     * Constructor whit parameter
     *
     * @param code the valid code
     */
    EventType (String code) {
        this.code = code;
    }

    /**
     * Return a string code
     *
     * @return String that represent of the message status
     */
    public String getCode()   { return this.code ; }


    /**
     * Return the enum by the code
     *
     * @param code the valid code
     * @return EventType enum
     * @throws InvalidParameterException error with is no a valid code
     */
    public static EventType getByCode(String code) throws InvalidParameterException {

        switch (code) {
            case "DUC":
                return EventType.DEVICE_USER_CREATED;
            case "DLI":
                return EventType.DEVICE_USER_LOGGED_IN;
            case "DLO":
                return EventType.DEVICE_USER_LOGGED_OUT;
            case "ILI":
                return EventType.INTRA_USER_LOGGED_IN;
            case "ILO":
                return EventType.INTRA_USER_LOGGED_OUT;
            case "ICC":
                return EventType.INTRA_USER_CONTACT_CREATED;
            case "INSCR":
                return EventType.INCOMING_NETWORK_SERVICE_CONNECTION_REQUEST;
            case "SNSC":
                return EventType.ESTABLISHED_NETWORK_SERVICE_CONNECTION;
            case "NNSMR":
                return EventType.NEW_NETWORK_SERVICE_MESSAGE_RECEIVE;
            /**
             * Bitcoin incoming crypto events
             */
            case "ICOCN":
                return EventType.INCOMING_CRYPTO_ON_CRYPTO_NETWORK;
            case "ICOBC":
                return EventType.INCOMING_CRYPTO_ON_BLOCKCHAIN;
            case "ICIRR":
                return EventType.INCOMING_CRYPTO_IRREVERSIBLE;
            case "ICROCN":
                return EventType.INCOMING_CRYPTO_REVERSED_ON_CRYPTO_NETWORK;
            case "ICROBC":
                return EventType.INCOMING_CRYPTO_REVERSED_ON_BLOCKCHAIN;

            case "WC1":
                return EventType.WALLET_CREATED;
            case "WWO":
                return EventType.WALLET_WENT_ONLINE;
            case "WI1":
                return EventType.WALLET_INSTALLED;
            case "BWI":
                return EventType.BEGUN_WALLET_INSTALLATION;
            case "WU1":
                return EventType.WALLET_UNINSTALLED;
            case "WC2":
                return EventType.WALLET_CLOSED;
            case "WO1":
                return EventType.WALLET_OPENED;
            case "WRI":
                return EventType.WALLET_RESOURCES_INSTALLED;
            case "NSU":
                return EventType.NAVIGATION_STRUCTURE_UPDATED;
            case "MR1":
                return EventType.MONEY_RECEIVED;
            case "FWI":
                return EventType.FINISHED_WALLET_INSTALLATION;

            case "TWT":
                return EventType.INCOMING_CRYPTO_TRANSACTIONS_WAITING_TRANSFERENCE;
            case "TWE":
                return EventType.INCOMING_CRYPTO_TRANSACTIONS_WAITING_TRANSFERENCE_EXTRA_USER;


            case "ICR":
                return EventType.INCOMING_CRYPTO_RECEIVED;
            case "IIRC":
                return EventType.INCOMING_CRYPTO_RECEPTION_CONFIRMED;
            case "ICREV":
                return EventType.INCOMING_CRYPTO_REVERSED;
            case "ICI":
                return EventType.INCOMING_CRYPTO_IDENTIFIED;
            case "ICIEU":
                return EventType.INCOMING_CRYPTO_IDENTIFIED_FROM_EXTRA_USER;
            case "ICIIU":
                return EventType.INCOMING_CRYPTO_IDENTIFIED_FROM_INTRA_USER;
            case "ICIDU":
                return EventType.INCOMING_CRYPTO_IDENTIFIED_FROM_DEVICE_USER;
            case "ICREU":
                return EventType.INCOMING_CRYPTO_RECEIVED_FROM_EXTRA_USER;
            case "ICRIU":
                return EventType.INCOMING_CRYPTO_RECEIVED_FROM_INTRA_USER;
            case "ICRDU":
                return EventType.INCOMING_CRYPTO_RECEIVED_FROM_DEVICE_USER;
            case "ICCEU":
                return EventType.INCOMING_CRYPTO_RECEPTION_CONFIRMED_FROM_EXTRA_USER;
            case "ICCIU":
                return EventType.INCOMING_CRYPTO_RECEPTION_CONFIRMED_FROM_INTRA_USER;
            case "ICCDU":
                return EventType.INCOMING_CRYPTO_RECEPTION_CONFIRMED_FROM_DEVICE_USER;
            case "REVEU":
                return EventType.INCOMING_CRYPTO_REVERSED_FROM_EXTRA_USER;
            case "REVIU":
                return EventType.INCOMING_CRYPTO_REVERSED_FROM_INTRA_USER;
            case "REVDU":
                return EventType.INCOMING_CRYPTO_REVERSED_FROM_DEVICE_USER;

            case "IMRR":
                return EventType.INCOMING_MONEY_REQUEST_RECEIVED;
            case "OMRD":
                return EventType.OUTGOING_MONEY_REQUEST_DELIVERED;
            case "OMRA":
                return EventType.OUTGOING_MONEY_REQUEST_APPROVED;
            case "OMRRJ":
                return EventType.OUTGOING_MONEY_REQUEST_REJECTED;
            case "IMRA":
                return EventType.INCOMING_MONEY_REQUEST_APPROVED;
            case "IMRRJ":
                return EventType.INCOMING_MONEY_REQUEST_REJECTED;

            case "DCNC":
                return EventType.DEVICE_CONNECTIVITY_NETWORK_CHANGE;

            case "BNPC":
                return EventType.BITCOIN_NEW_PEER_CONNECTED;
            case "BNCWC":
                return EventType.BITCOIN_NEW_CRYPTO_WALLET_CREATED;
            case "BCWL":
                return EventType.BITCOIN_CRYPTO_WALLET_LOADED;
            case "BBD":
                return EventType.BITCOIN_BLOCKCHAIN_DOWNLOADED;
            case "BNPCIU":
                return EventType.BITCOIN_NEW_PEER_CONNECTED_INTRA_USER;

            /**
             * Issue #543
             */
            case "ICOCNWTEU":
                return EventType.INCOMING_CRYPTO_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_EXTRA_USER;
            case"ICOBWTEU":
                return EventType.INCOMING_CRYPTO_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_EXTRA_USER;
            case"ICROCNWTEU":
                return EventType.INCOMING_CRYPTO_REVERSED_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_EXTRA_USER;
            case"ICROBWTEU":
                return EventType.INCOMING_CRYPTO_REVERSED_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_EXTRA_USER;
        }

        /**
         * If we try to cpmvert am invalid string.
         */
        throw new InvalidParameterException(code);
    };

    /**
     * (non-Javadoc)
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return getCode();
    }
}
