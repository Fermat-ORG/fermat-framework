package com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.conf;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.NetworkServiceProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.Profile;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.JsonAttNamesConstants;
import com.google.gson.JsonObject;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.websocket.ClientEndpointConfig;
import javax.websocket.HandshakeResponse;

/**
 * The class <code>NetworkCallChannelConfigurator</code>
 * </p>
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/04/2016.
 *
 * @author  lnacosta
 * @version 1.0
 * @since   Java JDK 1.7
 */
public class NetworkCallChannelConfigurator extends ClientEndpointConfig.Configurator {

    /**
     * Represent the networkCallIdentity
     */
    private ECCKeyPair networkCallIdentity;

    /**
     * Represent the remoteParticipantNetworkService
     */
    private NetworkServiceProfile remoteParticipantNetworkService;

    /**
     * Represent the registerParticipant
     */
    private Profile registerParticipant;

    /**
     * Represent the remotePlatformComponentProfile
     */
    private Profile remotePlatformComponentProfile;

    /**
     * Constructor with parameters
     *
     * @param networkCallIdentity
     * @param remoteParticipantNetworkService
     * @param registerParticipant
     * @param remotePlatformComponentProfile
     */
    public NetworkCallChannelConfigurator(final ECCKeyPair            networkCallIdentity            ,
                                          final NetworkServiceProfile remoteParticipantNetworkService,
                                          final Profile               registerParticipant            ,
                                          final Profile               remotePlatformComponentProfile ) {

        this.networkCallIdentity             = networkCallIdentity            ;
        this.remoteParticipantNetworkService = remoteParticipantNetworkService;
        this.registerParticipant             = registerParticipant            ;
        this.remotePlatformComponentProfile  = remotePlatformComponentProfile ;
    }

    @Override
    public void beforeRequest(Map<String, List<String>> headers) {

        /*
         * Get json representation
         */
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(JsonAttNamesConstants.NETWORK_SERVICE_TYPE     , remoteParticipantNetworkService.getNetworkServiceType().toString());
        jsonObject.addProperty(JsonAttNamesConstants.CLIENT_IDENTITY_VPN      , networkCallIdentity.getPublicKey());
        jsonObject.addProperty(JsonAttNamesConstants.APPLICANT_PARTICIPANT_VPN, registerParticipant.toJson());
        jsonObject.addProperty(JsonAttNamesConstants.REMOTE_PARTICIPANT_VPN   , remotePlatformComponentProfile.getIdentityPublicKey());

        /*
         * Add the att to the header
         */
        headers.put(JsonAttNamesConstants.HEADER_ATT_NAME_TI, Arrays.asList(jsonObject.toString()));
    }

    @Override
    public void afterResponse(HandshakeResponse hr) {
    }
}
