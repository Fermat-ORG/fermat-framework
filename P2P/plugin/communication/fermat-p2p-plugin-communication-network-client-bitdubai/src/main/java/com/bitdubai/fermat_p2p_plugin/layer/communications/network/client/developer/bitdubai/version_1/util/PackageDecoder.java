/*
* @#PackageDecoder.java - 2016
* Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
* BITDUBAI/CONFIDENTIAL
*/
package com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.util;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.Package;
import com.google.gson.Gson;
import com.google.gson.JsonParser;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.util.PackageDecoder</code>
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 20/05/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class PackageDecoder implements Decoder.Text<Package> {

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
    public Package decode(String s) throws DecodeException {
        return gson.fromJson(s, Package.class);
    }

    /**
     * (non-javadoc)
     * @see Decoder.Text#willDecode(String)
     */
    @Override
    public boolean willDecode(String s) {
        try{

            parser.parse(s);
            return true;

        }catch (Exception ex){
            return false;
        }
    }

    /**
     * (non-javadoc)
     * @see Decoder.Text#init(EndpointConfig)
     */
    @Override
    public void init(EndpointConfig config) {
        gson = new Gson();
        parser = new JsonParser();
    }

    /**
     * (non-javadoc)
     * @see Decoder.Text#destroy()
     */
    @Override
    public void destroy() {
        gson = null;
        parser = null;
    }

}
