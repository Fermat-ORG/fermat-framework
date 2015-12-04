/*
 * @#MessageEncoder.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.util;

import org.jboss.logging.Logger;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.util.MessageEncoder</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 30/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class MessageEncoder implements Encoder.Text{

    /**
     * Represent the LOG
     */
    private static final Logger LOG = Logger.getLogger(MessageEncoder.class.getName());

    /**
     * (non-javadoc)
     * @see Encoder.Text#encode(Object)
     */
    @Override
    public String encode(Object object) throws EncodeException {
        LOG.debug("Execute the encode method");
        return null;
    }

    /**
     * (non-javadoc)
     * @see Encoder.Text#init(EndpointConfig)
     */
    @Override
    public void init(EndpointConfig config) {
        LOG.debug("Execute the init method");
    }

    /**
     * (non-javadoc)
     * @see Encoder.Text#destroy()
     */
    @Override
    public void destroy() {
        LOG.debug("Execute the destroy method");
    }
}
