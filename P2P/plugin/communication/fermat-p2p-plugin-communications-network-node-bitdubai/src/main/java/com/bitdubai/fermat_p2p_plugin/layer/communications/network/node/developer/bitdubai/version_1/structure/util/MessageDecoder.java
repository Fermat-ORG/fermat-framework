/*
 * @#MessageDecoder.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.util;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.Message;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import org.jboss.logging.Logger;

import java.io.StringReader;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.util.MessageDecoder</code>
 * decode the json string to a message object
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 30/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class MessageDecoder implements Decoder.Text<Message>{

    /**
     * Represent the LOG
     */
    private static final Logger LOG = Logger.getLogger(Decoder.class.getName());

    /**
     * Represent the gson instance
     */
    private Gson gson;

    /**
     * Represent the parser instance
     */
    private JsonParser parser;

    /**
     * (non-javadoc)
     * @see Decoder.Text#decode(String)
     */
    @Override
    public Message decode(String s) throws DecodeException {
        LOG.debug("Execute the decode method");
        return gson.fromJson(s, Message.class);
    }

    /**
     * (non-javadoc)
     * @see Decoder.Text#willDecode(String)
     */
    @Override
    public boolean willDecode(String s) {
        LOG.debug("Execute the willDecode method");

        try{

            parser.parse(s);
            return true;

        }catch (Exception ex){

            LOG.error("Invalid json string");
            return false;
        }
    }

    /**
     * (non-javadoc)
     * @see Decoder.Text#init(EndpointConfig)
     */
    @Override
    public void init(EndpointConfig config) {
        LOG.debug("Execute the init method");
        gson = new Gson();
        parser = new JsonParser();
    }

    /**
     * (non-javadoc)
     * @see Decoder.Text#destroy()
     */
    @Override
    public void destroy() {
        LOG.debug("Execute the destroy method");
        gson = null;
        parser = null;
    }
}
