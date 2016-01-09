/*
 * @#ClientConnection.java - 2016
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.vpn;

import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;

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

    private static final int MAX_MESSAGE_SIZE = 1000000;

    static final String PROFILE_ATT_NAME = "p";

    static final String VPN_CLIENT_IDENTITY_ATT_NAME = "VCI";

    static final String PENDING_PONG_MSG_ATT_NAME = "PMG";

    private Session session;

    public VpnClientConnection(Session session) {
        this.session = session;
        this.session.setMaxTextMessageBufferSize(MAX_MESSAGE_SIZE);
    }

    public PlatformComponentProfile getPlatformComponentProfile(){
        return (PlatformComponentProfile) session.getUserProperties().get(PROFILE_ATT_NAME);
    }

    public void setPlatformComponentProfile(PlatformComponentProfile platformComponentProfile){
        session.getUserProperties().put(PROFILE_ATT_NAME, platformComponentProfile);
    }

    public String getVpnClientIdentity(){
        return (String) session.getUserProperties().get(VPN_CLIENT_IDENTITY_ATT_NAME);
    }

    public void setVpnClientIdentity(String clientIdentity){
        session.getUserProperties().put(VPN_CLIENT_IDENTITY_ATT_NAME, clientIdentity);
    }

    public Boolean getPendingPongMsg(){
        return (Boolean) session.getUserProperties().get(PENDING_PONG_MSG_ATT_NAME);
    }

    public void setPendingPongMsg(Boolean pending){
        session.getUserProperties().put(PENDING_PONG_MSG_ATT_NAME, pending);
    }

    /**
     * Get the session value
     *
     * @return session current value
     */
    public Session getSession() {
        return session;
    }
}
