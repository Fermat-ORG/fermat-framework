/*
 * @#EventType.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_wpd_api.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEventEnum;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventMonitor;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_wpd_api.all_definition.events.BegunWalletInstallationEvent;
import com.bitdubai.fermat_wpd_api.all_definition.events.FinishedWalletInstallationEvent;
import com.bitdubai.fermat_wpd_api.all_definition.events.WalletClosedEvent;
import com.bitdubai.fermat_wpd_api.all_definition.events.WalletCreatedEvent;
import com.bitdubai.fermat_wpd_api.all_definition.events.WalletInstalledEvent;
import com.bitdubai.fermat_wpd_api.all_definition.events.WalletNavigationStructureDownloadedEvent;
import com.bitdubai.fermat_wpd_api.all_definition.events.WalletOpenedEvent;
import com.bitdubai.fermat_wpd_api.all_definition.events.WalletResourcesInstalledEvent;
import com.bitdubai.fermat_wpd_api.all_definition.events.WalletUninstalledEvent;
import com.bitdubai.fermat_wpd_api.all_definition.events.WalletWentOnlineEvent;
import com.bitdubai.fermat_wpd_api.all_definition.listeners.BegunWalletInstallationEventListener;
import com.bitdubai.fermat_wpd_api.all_definition.listeners.FinishedWalletInstallationEventListener;
import com.bitdubai.fermat_wpd_api.all_definition.listeners.WalletClosedEventListener;
import com.bitdubai.fermat_wpd_api.all_definition.listeners.WalletCreatedEventListener;
import com.bitdubai.fermat_wpd_api.all_definition.listeners.WalletInstalledEventListener;
import com.bitdubai.fermat_wpd_api.all_definition.listeners.WalletNavigationStructureDownloadedEventListener;
import com.bitdubai.fermat_wpd_api.all_definition.listeners.WalletOpenedEventListener;
import com.bitdubai.fermat_wpd_api.all_definition.listeners.WalletResourcesInstalledEventListener;
import com.bitdubai.fermat_wpd_api.all_definition.listeners.WalletUninstalledEventListener;
import com.bitdubai.fermat_wpd_api.all_definition.listeners.WalletWentOnlineEventListener;

/**
 * The enum <code>EventType</code>
 * represent the different type for the events<p/>
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public enum EventType implements FermatEventEnum {

    BEGUN_WALLET_INSTALLATION("BWI") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new BegunWalletInstallationEventListener(this, fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new BegunWalletInstallationEvent(this);
        }
    },

    FINISHED_WALLET_INSTALLATION("FWI") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new FinishedWalletInstallationEventListener(this, fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new FinishedWalletInstallationEvent(this);
        }
    },

    WALLET_CLOSED("WC2") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new WalletClosedEventListener(this, fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new WalletClosedEvent(this);
        }
    },

    WALLET_CREATED("WC1") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new WalletCreatedEventListener(this, fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new WalletCreatedEvent(this);
        }
    },

    WALLET_INSTALLED("WI1") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new WalletInstalledEventListener(this, fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new WalletInstalledEvent(this);
        }
    },

    WALLET_OPENED("WO1") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new WalletOpenedEventListener(this, fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new WalletOpenedEvent(this);
        }
    },

    WALLET_RESOURCES_INSTALLED("WRI") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new WalletResourcesInstalledEventListener(this, fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new WalletResourcesInstalledEvent(this);
        }
    },

    WALLET_RESOURCES_NAVIGATION_STRUCTURE_DOWNLOADED("WRNSD") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new WalletNavigationStructureDownloadedEventListener(this, fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new WalletNavigationStructureDownloadedEvent(this);
        }
    },

    WALLET_UNINSTALLED("WU1") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new WalletUninstalledEventListener(this, fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new WalletUninstalledEvent(this);
        }
    },

    WALLET_WENT_ONLINE("WWO") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new WalletWentOnlineEventListener(this, fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new WalletWentOnlineEvent(this);
        }
    };


    /**
     * Represent the code of the message status
     */
    private final String code;

    /**
     * Constructor whit parameter
     *
     * @param code the valid code
     */
    EventType(String code) {
        this.code = code;
    }


    public abstract FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor);

    public abstract FermatEvent getNewEvent();

    /**
     * Return the enum by the code
     *
     * @param code the valid code
     * @return EventType enum
     * @throws InvalidParameterException error with is no a valid code
     */
    public static EventType getByCode(String code) throws InvalidParameterException {
        for (EventType eventType : EventType.values()) {
            if (eventType.code.equals(code)) {
                return eventType;
            }
        }
        throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This code isn't valid for the EventType Enum");
    }

    @Override
    public Platforms getPlatform() {
        return null;
    }

    @Override
    public String getCode() {
        return this.code;
    }


    @Override
    public String toString() {
        return getCode();
    }
}
