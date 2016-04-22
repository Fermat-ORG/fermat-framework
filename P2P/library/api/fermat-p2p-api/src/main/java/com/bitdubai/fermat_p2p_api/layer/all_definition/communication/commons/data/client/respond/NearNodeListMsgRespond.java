/*
 * @#NearNodeListMsgRespond.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond;

import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.NodeProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.util.InterfaceAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.NearNodeListMsgRespond</code>
 * represent the message to request the near node list
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 26/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class NearNodeListMsgRespond extends MsgRespond {

    /**
     * Represent the list of nodes
     */
    private List<NodeProfile> nodes;

    /**
     * Constructor with parameters
     *
     * @param status
     * @param details
     * @param nodes
     */
    public NearNodeListMsgRespond(STATUS status, String details, List<NodeProfile> nodes) {
        super(status, details);
        this.nodes = nodes;
    }

    /**
     * Gets the value of nodes and returns
     *
     * @return nodes
     */
    public List<NodeProfile> getNodes() {
        return nodes;
    }

    @Override
    public String toJson() {

        return getGsonInstance().toJson(this, getClass());
    }

    public static NearNodeListMsgRespond parseContent(String content) {

        return getGsonInstance().fromJson(content, NearNodeListMsgRespond.class);
    }

    private static Gson getGsonInstance() {

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Location.class, new InterfaceAdapter<Location>());
        return builder.create();
    }
}
