/*
 * @#RespondNearNodeListMsg.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.PackageContent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.NodeProfile;

import java.util.List;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.RespondNearNodeListMsg</code>
 * represent the message to request the near node list
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 26/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class RespondNearNodeListMsg extends PackageContent {

    /**
     * Represent the list of nodes
     */
    private List<NodeProfile> nodes;

    /**
     * Constructor
     */
    public RespondNearNodeListMsg(){
        super();
    }

    /**
     * Constructor with parameter
     * @param nodes
     */
    public RespondNearNodeListMsg(List<NodeProfile> nodes) {
        super();
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
}
