/*
 * @#PackageEncoder.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.util;


import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.Package;
import com.google.gson.Gson;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.util.PackageEncoder</code>
 * encode the package object to json string format
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 30/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class PackageEncoder implements Encoder.Text<Package>{

    /**
     * Represent the gson instance
     */
    private Gson gson;

    /**
     * (non-javadoc)
     * @see Encoder.Text#encode(Object)
     */
    @Override
    public String encode(Package packageReceived) throws EncodeException {
        return gson.toJson(packageReceived);
    }

    /**
     * (non-javadoc)
     * @see Encoder.Text#init(EndpointConfig)
     */
    @Override
    public void init(EndpointConfig config) {
        gson = new Gson();
    }

    /**
     * (non-javadoc)
     * @see Encoder.Text#destroy()
     */
    @Override
    public void destroy() {
        gson = null;
    }
}
