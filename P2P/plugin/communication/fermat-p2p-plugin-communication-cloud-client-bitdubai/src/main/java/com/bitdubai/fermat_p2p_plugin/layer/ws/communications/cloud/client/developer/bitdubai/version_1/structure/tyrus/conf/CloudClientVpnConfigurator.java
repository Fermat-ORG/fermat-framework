/*
 * @#CLoudClientConfigurator.java - 2016
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.tyrus.conf;

import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.JsonAttNamesConstants;
import com.google.gson.JsonObject;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.websocket.ClientEndpointConfig;
import javax.websocket.HandshakeResponse;

/**
 * The class <code>CLoudClientConfigurator</code>
 * </p>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 17/01/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CloudClientVpnConfigurator extends ClientEndpointConfig.Configurator {

    /**
     * Represent the vpnClientIdentity
     */
    private ECCKeyPair vpnClientIdentity;

    /**
     * Represent the remoteParticipantNetworkService
     */
    private PlatformComponentProfile remoteParticipantNetworkService;

    /**
     * Represent the registerParticipant
     */
    private PlatformComponentProfile registerParticipant;

    /**
     * Represent the remotePlatformComponentProfile
     */
    private  PlatformComponentProfile remotePlatformComponentProfile;

    /**
     * Constructor with parameters
     *
     * @param vpnClientIdentity
     * @param remoteParticipantNetworkService
     * @param registerParticipant
     * @param remotePlatformComponentProfile
     */
    public CloudClientVpnConfigurator(ECCKeyPair vpnClientIdentity, PlatformComponentProfile remoteParticipantNetworkService, PlatformComponentProfile registerParticipant, PlatformComponentProfile remotePlatformComponentProfile) {
        this.vpnClientIdentity = vpnClientIdentity;
        this.remoteParticipantNetworkService = remoteParticipantNetworkService;
        this.registerParticipant = registerParticipant;
        this.remotePlatformComponentProfile = remotePlatformComponentProfile;
    }

    @Override
    public void beforeRequest(Map<String, List<String>> headers) {

        /*
         * Get json representation
         */
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(JsonAttNamesConstants.NETWORK_SERVICE_TYPE, remoteParticipantNetworkService.getNetworkServiceType().toString());
        jsonObject.addProperty(JsonAttNamesConstants.CLIENT_IDENTITY_VPN, vpnClientIdentity.getPublicKey());
        jsonObject.addProperty(JsonAttNamesConstants.APPLICANT_PARTICIPANT_VPN, registerParticipant.toJson());
        jsonObject.addProperty(JsonAttNamesConstants.REMOTE_PARTICIPANT_VPN, remotePlatformComponentProfile.getIdentityPublicKey());

        /*
         * Add the att to the header
         */
        headers.put(JsonAttNamesConstants.HEADER_ATT_NAME_TI, Arrays.asList(jsonObject.toString()));
        //headers.put("Origin", Arrays.asList("myOrigin"));
    }

    @Override
    public void afterResponse(HandshakeResponse hr) {
    }
}
