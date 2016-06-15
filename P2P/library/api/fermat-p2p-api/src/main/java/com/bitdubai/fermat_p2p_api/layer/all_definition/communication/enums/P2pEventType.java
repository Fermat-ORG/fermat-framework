package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEventEnum;
import com.bitdubai.fermat_api.layer.all_definition.events.common.GenericEventListener;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventMonitor;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.events.NetworkClientActorFoundEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.events.NetworkClientActorUnreachableEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.events.NetworkClientCallConnectedEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.events.NetworkClientConnectionClosedEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.events.NetworkClientConnectionLostEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.events.NetworkClientConnectionSuccessEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.events.NetworkClientNewMessageDeliveredEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.events.NetworkClientNewMessageTransmitEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.events.NetworkClientProfileRegisteredEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.events.NetworkClientRegisteredEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.ClientConnectionCloseNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.ClientConnectionLooseNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.ClientSuccessReconnectNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.CompleteClientComponentRegistrationNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.CompleteComponentConnectionRequestNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.CompleteComponentRegistrationNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.CompleteRequestListComponentRegisteredNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.CompleteUpdateActorNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.FailureComponentConnectionRequestNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.FailureComponentRegistrationNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.FailureRequestedListNotAvailableNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.FailureUpdateActorNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.RegisterServerRequestNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.VPNConnectionCloseNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.VPNConnectionLooseNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.VpnSuccessReconnectNotificationEvent;

/**
 * The enum <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType</code>
 * represent the different type for the events of cry api.<p/>
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 16/09/2015.
 * Update by Roberto Requena (rart3001@gmail.com) on 17/09/2015.
 *
 * @author  lnacosta, Rart3001
 * @version 1.0
 * @since   Java JDK 1.7
 */
public enum P2pEventType implements FermatEventEnum {

    /**
     * Declaration of the Web Socket Communication Layer Events
     */
    COMPLETE_COMPONENT_REGISTRATION_NOTIFICATION("CL_CRCN") {
        public CompleteComponentRegistrationNotificationEvent getNewEvent() {
            return new CompleteComponentRegistrationNotificationEvent(this);
        }
    },

    FAILURE_COMPONENT_REGISTRATION_REQUEST_NOTIFICATION("FCRRN"){
        public FailureComponentRegistrationNotificationEvent getNewEvent() {
            return new FailureComponentRegistrationNotificationEvent(this);
        }
    },

    FAILURE_UPDATE_ACTOR_REQUEST_NOTIFICATION("FUPDACTRN"){
        public FailureUpdateActorNotificationEvent getNewEvent() {
            return new FailureUpdateActorNotificationEvent(this);
        }
    },

    COMPLETE_CLIENT_COMPONENT_REGISTRATION_NOTIFICATION("CL_CCRCN") {
        public CompleteClientComponentRegistrationNotificationEvent getNewEvent() {
            return new CompleteClientComponentRegistrationNotificationEvent(this);
        }
    },

    REGISTER_SERVER_REQUEST_NOTIFICATION("REG_SER_REN"){
        public RegisterServerRequestNotificationEvent getNewEvent() {
            return new RegisterServerRequestNotificationEvent(this);
        }
    },

    COMPLETE_REQUEST_LIST_COMPONENT_REGISTERED_NOTIFICATION("CL_CRLCRN") {
        public CompleteRequestListComponentRegisteredNotificationEvent getNewEvent() {
            return new CompleteRequestListComponentRegisteredNotificationEvent(this);
        }
    },

    FAILURE_REQUESTED_LIST_NOT_AVAILABLE_NOTIFICATION("FRLNAN"){
        public FailureRequestedListNotAvailableNotificationEvent getNewEvent() {
            return new FailureRequestedListNotAvailableNotificationEvent(this);
        }
    },

    COMPLETE_COMPONENT_CONNECTION_REQUEST_NOTIFICATION("CL_CCCRN") {
        public CompleteComponentConnectionRequestNotificationEvent getNewEvent() {
            return new CompleteComponentConnectionRequestNotificationEvent(this);
        }
    },

    COMPLETE_UPDATE_ACTOR_NOTIFICATION("CL_UPDACT") {
        public CompleteUpdateActorNotificationEvent getNewEvent() {
            return new CompleteUpdateActorNotificationEvent(this);
        }
    },

    FAILURE_COMPONENT_CONNECTION_REQUEST_NOTIFICATION("F_CCCRN") {
        public FailureComponentConnectionRequestNotificationEvent getNewEvent() {
            return new FailureComponentConnectionRequestNotificationEvent(this);
        }
    },

    CLIENT_CONNECTION_CLOSE("CCC"){
        public ClientConnectionCloseNotificationEvent getNewEvent() {
            return new ClientConnectionCloseNotificationEvent(this);
        }
    },

    CLIENT_CONNECTION_LOOSE("CCL"){
        public ClientConnectionLooseNotificationEvent getNewEvent() {
            return new ClientConnectionLooseNotificationEvent(this);
        }
    },

    CLIENT_SUCCESS_RECONNECT("CSC"){
        public ClientSuccessReconnectNotificationEvent getNewEvent() {
            return new ClientSuccessReconnectNotificationEvent(this);
        }
    },

    VPN_CONNECTION_CLOSE("VCC"){
        public VPNConnectionCloseNotificationEvent getNewEvent() {
            return new VPNConnectionCloseNotificationEvent(this);
        }
    },

    VPN_CONNECTION_LOOSE("VCL"){
        public VPNConnectionLooseNotificationEvent getNewEvent() {
            return new VPNConnectionLooseNotificationEvent(this);
        }
    },

    VPN_SUCCESS_RECONNECT("VSR"){
        public VpnSuccessReconnectNotificationEvent getNewEvent() {
            return new VpnSuccessReconnectNotificationEvent(this);
        }
    },


    /**
     * INIT NETWORK NODE-CLIENT TEMPLATE EVENTS
     */

    NETWORK_CLIENT_ACTOR_PROFILE_REGISTERED("NCAPR"){
        public NetworkClientProfileRegisteredEvent getNewEvent() { return new NetworkClientProfileRegisteredEvent(this); }
    },
    NETWORK_CLIENT_CONNECTION_SUCCESS("NCCSU"){
        public NetworkClientConnectionSuccessEvent getNewEvent() { return new NetworkClientConnectionSuccessEvent(this); }
    },
    NETWORK_CLIENT_CONNECTION_LOST("NCCL"){
        public NetworkClientConnectionLostEvent getNewEvent() { return new NetworkClientConnectionLostEvent(this); }
    },
    NETWORK_CLIENT_CONNECTION_CLOSED("NCCC"){
        public NetworkClientConnectionClosedEvent getNewEvent() { return new NetworkClientConnectionClosedEvent(this); }
    },
    NETWORK_CLIENT_NETWORK_SERVICE_PROFILE_REGISTERED("NCNSPR"){
        public NetworkClientProfileRegisteredEvent getNewEvent() { return new NetworkClientProfileRegisteredEvent(this); }
    },
    NETWORK_CLIENT_REGISTERED("NCR"){
        public NetworkClientRegisteredEvent getNewEvent() { return new NetworkClientRegisteredEvent(this); }
    },
    NETWORK_CLIENT_ACTOR_FOUND("NCAF"){
        public NetworkClientActorFoundEvent getNewEvent() { return new NetworkClientActorFoundEvent(this); }
    },
    NETWORK_CLIENT_ACTOR_UNREACHABLE("NCAD"){
        public NetworkClientActorUnreachableEvent getNewEvent() { return new NetworkClientActorUnreachableEvent(this); }
    },
    NETWORK_CLIENT_NEW_MESSAGE_TRANSMIT("NCNWT"){
        public NetworkClientNewMessageTransmitEvent getNewEvent() { return new NetworkClientNewMessageTransmitEvent(this); }
    },
    NETWORK_CLIENT_SENT_MESSAGE_DELIVERED("NCSENTMD"){
        public NetworkClientNewMessageDeliveredEvent getNewEvent() { return new NetworkClientNewMessageDeliveredEvent(this); }
    },
    NETWORK_CLIENT_CALL_CONNECTED("NCACC"){
        public NetworkClientCallConnectedEvent getNewEvent() { return new NetworkClientCallConnectedEvent(this); }
    },

    /**
     * END  NETWORK NODE-CLIENT TEMPLATE EVENTS
     */

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
        return new GenericEventListener<>(this, fermatEventMonitor);
    }

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
