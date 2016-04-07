package org.fermat.fermat_dap_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEventEnum;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventMonitor;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

import org.fermat.fermat_dap_api.layer.all_definition.listeners.ActorAssetIssuerCompleteRegistrationNotificationEventListener;

/**
 * Created by Nerio on 27/10/15.
 */
public enum EventType implements FermatEventEnum {

    ACTOR_ASSET_REQUEST_CONNECTIONS("AARC") {
        public FermatEventListener getNewListener(FermatEventMonitor eventMonitor) {
            return new org.fermat.fermat_dap_api.layer.all_definition.listeners.RequestActorConnectionNotificationEventListener(this, eventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new org.fermat.fermat_dap_api.layer.all_definition.events.NewRequestActorNotificationEvent(this);
        }

    },

    RECEIVE_NEW_DAP_MESSAGE("RNDAMN") {
        public FermatEventListener getNewListener(FermatEventMonitor eventMonitor) {
            return new org.fermat.fermat_dap_api.layer.all_definition.listeners.ReceivedNewDigitalAssetMetadataNotificationEventListener(this, eventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new org.fermat.fermat_dap_api.layer.all_definition.events.ReceivedNewDigitalAssetMetadataNotificationEvent(this);
        }
    },

    ACTOR_ASSET_NETWORK_SERVICE_NEW_NOTIFICATIONS("AANSNN") {
        @Override
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new org.fermat.fermat_dap_api.layer.all_definition.listeners.ActorAssetNetworkServicePendingsNotificationEventListener(this, fermatEventMonitor);
        }

        @Override
        public FermatEvent getNewEvent() {
            return new org.fermat.fermat_dap_api.layer.all_definition.events.ActorAssetNetworkServicePendingNotificationEvent(this);
        }
    },

    COMPLETE_REQUEST_LIST_ASSET_ISSUER_REGISTERED_NOTIFICATION("CL_RLAIRN") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new org.fermat.fermat_dap_api.layer.all_definition.listeners.ActorAssetUserRequestListRegisteredNetworksNotificationEventListener(this, fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new org.fermat.fermat_dap_api.layer.all_definition.events.ActorAssetUserRequestListRegisteredNetworkServiceNotificationEvent(this);
        }
    },

    COMPLETE_REQUEST_LIST_ASSET_USER_REGISTERED_NOTIFICATION("CL_RLAURN") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new org.fermat.fermat_dap_api.layer.all_definition.listeners.ActorAssetUserRequestListRegisteredNetworksNotificationEventListener(this, fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new org.fermat.fermat_dap_api.layer.all_definition.events.ActorAssetUserRequestListRegisteredNetworkServiceNotificationEvent(this);
        }
    },

    COMPLETE_REQUEST_LIST_ASSET_REDEEM_POINT_REGISTERED_NOTIFICATION("CL_RLARRN") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new org.fermat.fermat_dap_api.layer.all_definition.listeners.ActorAssetUserRequestListRegisteredNetworksNotificationEventListener(this, fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new org.fermat.fermat_dap_api.layer.all_definition.events.ActorAssetUserRequestListRegisteredNetworkServiceNotificationEvent(this);
        }
    },

    COMPLETE_ASSET_ISSUER_REGISTRATION_NOTIFICATION("CL_CAIRN") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new ActorAssetIssuerCompleteRegistrationNotificationEventListener(this, fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new org.fermat.fermat_dap_api.layer.all_definition.events.ActorAssetIssuerCompleteRegistrationNotificationEvent(this);
        }
    },

    COMPLETE_ASSET_USER_REGISTRATION_NOTIFICATION("CL_CAURN") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new org.fermat.fermat_dap_api.layer.all_definition.listeners.ActorAssetUserCompleteRegistrationNotificationEventListener(this, fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new org.fermat.fermat_dap_api.layer.all_definition.events.ActorAssetUserCompleteRegistrationNotificationEvent(this);
        }
    },

    COMPLETE_ASSET_REDEEM_POINT_REGISTRATION_NOTIFICATION("CL_CARPRN") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new org.fermat.fermat_dap_api.layer.all_definition.listeners.ActorAssetRedeemPointCompleteRegistrationNotificationEventListener(this, fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new org.fermat.fermat_dap_api.layer.all_definition.events.ActorAssetRedeemPointCompleteRegistrationNotificationEvent(this);
        }
    },

    NEW_RECEIVE_MESSAGE_ACTOR("NEW_RECEIVE_MESSAGE") {
        @Override
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new org.fermat.fermat_dap_api.layer.all_definition.listeners.NewReceiveMessageActorNotificationEventListener(this, fermatEventMonitor);
        }

        @Override
        public FermatEvent getNewEvent() {
            return new org.fermat.fermat_dap_api.layer.all_definition.events.NewReceiveMessageActorNotificationEvent(this);
        }
    },
    NEW_RECEIVE_EXTENDED_KEY_ACTOR("EXTENDED_KEY") {
        @Override
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new org.fermat.fermat_dap_api.layer.all_definition.listeners.NewReceiveExtendedNotificationEventListener(this, fermatEventMonitor);
        }

        @Override
        public FermatEvent getNewEvent() {
            return new org.fermat.fermat_dap_api.layer.all_definition.events.NewRequestActorNotificationEvent(this);
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
