/*
 * @#MessageDecoder.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.util;

import org.jboss.logging.Logger;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.util.MessageDecoder</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 30/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class MessageDecoder implements Decoder.Text{

    /**
     * Represent the LOG
     */
    private static final Logger LOG = Logger.getLogger(Decoder.class.getName());

    /**
     * (non-javadoc)
     * @see Decoder.Text#decode(String)
     */
    @Override
    public Object decode(String s) throws DecodeException {
        LOG.debug("Execute the decode method");
        return null;
    }

    /**
     * (non-javadoc)
     * @see Decoder.Text#willDecode(String)
     */
    @Override
    public boolean willDecode(String s) {
        LOG.debug("Execute the willDecode method");
        return false;
    }

    /**
     * (non-javadoc)
     * @see Decoder.Text#init(EndpointConfig)
     */
    @Override
    public void init(EndpointConfig config) {
        LOG.debug("Execute the init method");
    }

    /**
     * (non-javadoc)
     * @see Decoder.Text#destroy()
     */
    @Override
    public void destroy() {
        LOG.debug("Execute the destroy method");
    }
}
