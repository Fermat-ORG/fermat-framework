/*
 * @#GetNodeCatalogMsjRequest.java - 2016
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.data.node.respond;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.MsgRespond;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.NodesCatalog;

import java.util.List;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.node.respond.GetNodeCatalogMsjRespond</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 05/04/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class GetNodeCatalogMsjRespond extends MsgRespond {

    /**
     * Represent the nodeProfileList
     */
    private List<NodesCatalog> nodesCatalogList;

    /**
     * Represent the count
     */
    private Integer count;

    /**
     * Constructor with parameters
     *
     * @param status
     * @param details
     */
    public GetNodeCatalogMsjRespond(STATUS status, String details, List<NodesCatalog> nodesCatalogList, Integer count) {
        super(status, details);
        this.nodesCatalogList = nodesCatalogList;
        this.count = count;
    }

    /**
     * Get the nodesCatalogList
     * @return List<NodeProfile>
     */
    public List<NodesCatalog> getNodesCatalogList() {
        return nodesCatalogList;
    }

    /**
     * Get the Count
     * @return Integer
     */
    public Integer getCount() {
        return count;
    }
}
