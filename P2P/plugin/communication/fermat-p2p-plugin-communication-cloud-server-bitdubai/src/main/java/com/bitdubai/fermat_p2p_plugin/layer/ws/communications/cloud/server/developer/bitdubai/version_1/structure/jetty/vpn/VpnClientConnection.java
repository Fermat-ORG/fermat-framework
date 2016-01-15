/*
 * @#ClientConnection.java - 2016
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.vpn;

import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;

import javax.websocket.Session;


/**
 * The class <code>com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.ClientConnection</code>
 * </p>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 08/01/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class VpnClientConnection {

    private static final int MAX_MESSAGE_SIZE = 3000000;

    /**
     *  Represent the networkServiceType
     */
    private NetworkServiceType networkServiceType;

    /**
     *  Represent the vpnClientIdentity
     */
    private String vpnClientIdentity;

    /**
     *  Represent the participant
     */
    private PlatformComponentProfile participant;

    /**
     *  Represent the remoteParticipantIdentity
     */
    private String remoteParticipantIdentity;

    /**
     *  Represent the session
     */
    private Session session;

    /**
     * Represent the isApplicant
     */
    private boolean isApplicant;

    /**
     * Constructor with parameters
     *
     * @param vpnClientIdentity
     * @param participant
     * @param remoteParticipantIdentity
     * @param session
     */
    public VpnClientConnection(String vpnClientIdentity, PlatformComponentProfile participant, String remoteParticipantIdentity, Session session, NetworkServiceType networkServiceType, boolean isApplicant) {
        this.vpnClientIdentity = vpnClientIdentity;
        this.participant = participant;
        this.remoteParticipantIdentity = remoteParticipantIdentity;
        this.networkServiceType = networkServiceType;
        this.session = session;
        this.session.setMaxTextMessageBufferSize(MAX_MESSAGE_SIZE);
        this.isApplicant = isApplicant;
    }

    /**
     * Get the vpnClientIdentity value
     *
     * @return vpnClientIdentity current value
     */
    public String getVpnClientIdentity() {
        return vpnClientIdentity;
    }

    /**
     * Get the participant value
     *
     * @return participant current value
     */
    public PlatformComponentProfile getParticipant() {
        return participant;
    }

    /**
     * Get the remoteParticipantIdentity value
     *
     * @return remoteParticipantIdentity current value
     */
    public String getRemoteParticipantIdentity() {
        return remoteParticipantIdentity;
    }

    /**
     * Get the session value
     *
     * @return session current value
     */
    public Session getSession() {
        return session;
    }

    /**
     * Get the networkServiceType value
     *
     * @return networkServiceType current value
     */
    public NetworkServiceType getNetworkServiceType() {
        return networkServiceType;
    }

    /**
     * Get the isApplicant value
     *
     * @return isApplicant current value
     */
    public boolean isApplicant() {
        return isApplicant;
    }

    /**
     * Get the key
     * @return String
     */
    public String getMyKey(){
        return (participant.getIdentityPublicKey() + remoteParticipantIdentity);
    }

    /**
     * Get the remote key
     * @return String
     */
    public String getKeyForMyRemote(){
        return (remoteParticipantIdentity+participant.getIdentityPublicKey() );
    }
}
