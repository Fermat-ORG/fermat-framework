/*
 * @#CloudWebSocketServlet.java - 2016
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.vpn;

import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

/**
 * The class <code>com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.vpn.VpnWebSocketServlet</code>
 * </p>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 09/01/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class VpnWebSocketServlet extends WebSocketServlet {

    private WebSocketVpnServerChannel vpnInstance = new WebSocketVpnServerChannel();

    @Override
    public void configure(WebSocketServletFactory factory){

        factory.setCreator(new WebSocketCreator() {
            @Override
            public Object createWebSocket(ServletUpgradeRequest req, ServletUpgradeResponse resp) {
                return vpnInstance;
            }
        });

    }

    /**
     * Get the vpnInstance value
     *
     * @return vpnInstance current value
     */
    public WebSocketVpnServerChannel getVpnInstance() {
        return vpnInstance;
    }
}
