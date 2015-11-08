package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEventEnum;
import com.bitdubai.fermat_api.layer.all_definition.events.common.GenericEventListener;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventMonitor;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.CompleteClientComponentRegistrationNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.CompleteComponentConnectionRequestNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.CompleteComponentRegistrationNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.CompleteRequestListComponentRegisteredNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.FailureComponentConnectionRequestNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.FailureComponentRegistrationNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.FailureRequestedListNotAvailableNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.NewNetworkServiceMessageReceivedNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.NewNetworkServiceMessageSentNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.listeners.CompleteClientComponentRegistrationNotificationEventListener;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.listeners.CompleteComponentConnectionRequestNotificationEventListener;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.listeners.CompleteComponentRegistrationNotificationEventListener;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.listeners.CompleteRequestListComponentRegisteredNotificationEventListener;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.listeners.FailureComponentConnectionRequestNotificationEventListener;

/**
 * The enum <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType</code>
 * represent the different type for the events of cry api.<p/>
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 16/09/2015.
 * Update by Roberto Requena (rart3001@gmail.com) on 17/09/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public enum P2pEventType implements FermatEventEnum {

    /**
     * Declaration of the Web Socket Communication Layer Events
     */
    COMPLETE_COMPONENT_REGISTRATION_NOTIFICATION("CL_CRCN") {
        public FermatEventListener getNewListener(FermatEventMonitor eventMonitor) {
            return new CompleteComponentRegistrationNotificationEventListener(this, eventMonitor);
        }
        public FermatEvent getNewEvent() {
            return new CompleteComponentRegistrationNotificationEvent(this);
        }
    },

    FAILURE_COMPONENT_REGISTRATION_REQUEST_NOTIFICATION("FCRRN"){
        public FermatEvent getNewEvent() {
            return new FailureComponentRegistrationNotificationEvent(this);
        }
    },

    COMPLETE_CLIENT_COMPONENT_REGISTRATION_NOTIFICATION("CL_CCRCN") {
        public FermatEventListener getNewListener(FermatEventMonitor eventMonitor) {
            return new CompleteClientComponentRegistrationNotificationEventListener(this, eventMonitor);
        }
        public FermatEvent getNewEvent() {
            return new CompleteClientComponentRegistrationNotificationEvent(this);
        }
    },

    COMPLETE_REQUEST_LIST_COMPONENT_REGISTERED_NOTIFICATION("CL_CRLCRN") {
        public FermatEventListener getNewListener(FermatEventMonitor eventMonitor) {
            return new CompleteRequestListComponentRegisteredNotificationEventListener(this, eventMonitor);
        }
        public FermatEvent getNewEvent() {
            return new CompleteRequestListComponentRegisteredNotificationEvent(this);
        }
    },

    FAILURE_REQUESTED_LIST_NOT_AVAILABLE_NOTIFICATION("FRLNAN"){
        public FermatEvent getNewEvent() {
            return new FailureRequestedListNotAvailableNotificationEvent(this);
        }
    },

    COMPLETE_COMPONENT_CONNECTION_REQUEST_NOTIFICATION("CL_CCCRN") {
        public FermatEventListener getNewListener(FermatEventMonitor eventMonitor) {
            return new CompleteComponentConnectionRequestNotificationEventListener(this, eventMonitor);
        }
        public FermatEvent getNewEvent() {
            return new CompleteComponentConnectionRequestNotificationEvent(this);
        }
    },

    FAILURE_COMPONENT_CONNECTION_REQUEST_NOTIFICATION("F_CCCRN") {
        public FermatEventListener getNewListener(FermatEventMonitor eventMonitor) {
            return new FailureComponentConnectionRequestNotificationEventListener(this, eventMonitor);
        }
        public FermatEvent getNewEvent() {
            return new FailureComponentConnectionRequestNotificationEvent(this);
        }
    },

    NEW_NETWORK_SERVICE_MESSAGE_RECEIVE_NOTIFICATION("NNSMRN") {
        public FermatEvent getNewEvent() {
            return new NewNetworkServiceMessageReceivedNotificationEvent(this);
        }
    },

    NEW_NETWORK_SERVICE_MESSAGE_SENT_NOTIFICATION("NNSMSN") {
        public FermatEvent getNewEvent() {
            return new NewNetworkServiceMessageSentNotificationEvent(this);
        }
    },

    CLIENT_CONNECTION_CLOSE("CCC"){
        public FermatEvent getNewEvent() {
            return new NewNetworkServiceMessageSentNotificationEvent(this);
        }
    },

    VPN_CONNECTION_CLOSE("VCC"){
        public FermatEvent getNewEvent() {
            return new NewNetworkServiceMessageSentNotificationEvent(this);
        }
    }
    ;

    /**
     * Represent the code of the message status
     */
    private final String code;

    /**
     * Constructor whit parameter
     *
     * @param code the valid code
     */
    P2pEventType(String code) {
        this.code = code;
    }

    public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
        return new GenericEventListener(this, fermatEventMonitor);
    }

    public abstract FermatEvent getNewEvent();

    /**
     * Return the enum by the code
     *
     * @param code the valid code
     * @return P2pEventType enum
     * @throws InvalidParameterException error with is no a valid code
     */
    public static P2pEventType getByCode(String code) throws InvalidParameterException {
        for (P2pEventType p2pEventType : P2pEventType.values()) {
            if (p2pEventType.code.equals(code)) {
                return p2pEventType;
            }
        }
        throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This code isn't valid for the P2pEventType Enum");
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
