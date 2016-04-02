package com.bitdubai.fermat_dap_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEventEnum;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventMonitor;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_dap_api.layer.all_definition.events.ActorAssetIssuerCompleteRegistrationNotificationEvent;
import com.bitdubai.fermat_dap_api.layer.all_definition.events.ActorAssetNetworkServicePendingNotificationEvent;
import com.bitdubai.fermat_dap_api.layer.all_definition.events.ActorAssetRedeemPointCompleteRegistrationNotificationEvent;
import com.bitdubai.fermat_dap_api.layer.all_definition.events.ActorAssetUserCompleteRegistrationNotificationEvent;
import com.bitdubai.fermat_dap_api.layer.all_definition.events.ActorAssetUserRequestListRegisteredNetworkServiceNotificationEvent;
import com.bitdubai.fermat_dap_api.layer.all_definition.events.NewRequestActorNotificationEvent;
import com.bitdubai.fermat_dap_api.layer.all_definition.events.NewReceiveMessageActorNotificationEvent;
import com.bitdubai.fermat_dap_api.layer.all_definition.events.ReceivedNewDigitalAssetMetadataNotificationEvent;
import com.bitdubai.fermat_dap_api.layer.all_definition.listeners.ActorAssetIssuerCompleteRegistrationNotificationEventListener;
import com.bitdubai.fermat_dap_api.layer.all_definition.listeners.ActorAssetNetworkServicePendingsNotificationEventListener;
import com.bitdubai.fermat_dap_api.layer.all_definition.listeners.ActorAssetRedeemPointCompleteRegistrationNotificationEventListener;
import com.bitdubai.fermat_dap_api.layer.all_definition.listeners.ActorAssetUserCompleteRegistrationNotificationEventListener;
import com.bitdubai.fermat_dap_api.layer.all_definition.listeners.ActorAssetUserRequestListRegisteredNetworksNotificationEventListener;
import com.bitdubai.fermat_dap_api.layer.all_definition.listeners.NewReceiveExtendedNotificationEventListener;
import com.bitdubai.fermat_dap_api.layer.all_definition.listeners.NewReceiveMessageActorNotificationEventListener;
import com.bitdubai.fermat_dap_api.layer.all_definition.listeners.ReceivedNewDigitalAssetMetadataNotificationEventListener;
import com.bitdubai.fermat_dap_api.layer.all_definition.listeners.RequestActorConnectionNotificationEventListener;

/**
 * Created by Nerio on 27/10/15.
 */
public enum EventType implements FermatEventEnum {

    ACTOR_ASSET_REQUEST_CONNECTIONS("AARC") {
        public FermatEventListener getNewListener(FermatEventMonitor eventMonitor) {
            return new RequestActorConnectionNotificationEventListener(this, eventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new NewRequestActorNotificationEvent(this);
        }

    },

    RECEIVE_NEW_DAP_MESSAGE("RNDAMN") {
        public FermatEventListener getNewListener(FermatEventMonitor eventMonitor) {
            return new ReceivedNewDigitalAssetMetadataNotificationEventListener(this, eventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new ReceivedNewDigitalAssetMetadataNotificationEvent(this);
        }
    },

    ACTOR_ASSET_NETWORK_SERVICE_NEW_NOTIFICATIONS("AANSNN") {
        @Override
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new ActorAssetNetworkServicePendingsNotificationEventListener(this, fermatEventMonitor);
        }

        @Override
        public FermatEvent getNewEvent() {
            return new ActorAssetNetworkServicePendingNotificationEvent(this);
        }
    },

    COMPLETE_REQUEST_LIST_ASSET_ISSUER_REGISTERED_NOTIFICATION("CL_RLAIRN") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new ActorAssetUserRequestListRegisteredNetworksNotificationEventListener(this, fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new ActorAssetUserRequestListRegisteredNetworkServiceNotificationEvent(this);
        }
    },

    COMPLETE_REQUEST_LIST_ASSET_USER_REGISTERED_NOTIFICATION("CL_RLAURN") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new ActorAssetUserRequestListRegisteredNetworksNotificationEventListener(this, fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new ActorAssetUserRequestListRegisteredNetworkServiceNotificationEvent(this);
        }
    },

    COMPLETE_REQUEST_LIST_ASSET_REDEEM_POINT_REGISTERED_NOTIFICATION("CL_RLARRN") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new ActorAssetUserRequestListRegisteredNetworksNotificationEventListener(this, fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new ActorAssetUserRequestListRegisteredNetworkServiceNotificationEvent(this);
        }
    },

    COMPLETE_ASSET_ISSUER_REGISTRATION_NOTIFICATION("CL_CAIRN") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new ActorAssetIssuerCompleteRegistrationNotificationEventListener(this, fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new ActorAssetIssuerCompleteRegistrationNotificationEvent(this);
        }
    },

    COMPLETE_ASSET_USER_REGISTRATION_NOTIFICATION("CL_CAURN") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new ActorAssetUserCompleteRegistrationNotificationEventListener(this, fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new ActorAssetUserCompleteRegistrationNotificationEvent(this);
        }
    },

    COMPLETE_ASSET_REDEEM_POINT_REGISTRATION_NOTIFICATION("CL_CARPRN") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new ActorAssetRedeemPointCompleteRegistrationNotificationEventListener(this, fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new ActorAssetRedeemPointCompleteRegistrationNotificationEvent(this);
        }
    },

    NEW_RECEIVE_MESSAGE_ACTOR("NEW_RECEIVE_MESSAGE") {
        @Override
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new NewReceiveMessageActorNotificationEventListener(this, fermatEventMonitor);
        }

        @Override
        public FermatEvent getNewEvent() {
            return new NewReceiveMessageActorNotificationEvent(this);
        }
    },
    NEW_RECEIVE_EXTENDED_KEY_ACTOR("EXTENDED_KEY") {
        @Override
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new NewReceiveExtendedNotificationEventListener(this, fermatEventMonitor);
        }

        @Override
        public FermatEvent getNewEvent() {
            return new NewRequestActorNotificationEvent(this);
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
        throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE,
                null, "Code Received: " + code, "This code isn't valid for the DAPEventType Enum");
    }

    @Override
    public Platforms getPlatform() {
        return Platforms.DIGITAL_ASSET_PLATFORM;
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
