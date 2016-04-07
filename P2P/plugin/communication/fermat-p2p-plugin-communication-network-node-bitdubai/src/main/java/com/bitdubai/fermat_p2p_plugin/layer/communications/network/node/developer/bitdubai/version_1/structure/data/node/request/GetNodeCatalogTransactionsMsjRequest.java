/*
 * @#NodeCatalogTransactionsMsjRequest.java - 2016
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.data.node.request;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.PackageContent;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.node.request.GetNodeCatalogTransactionsMsjRespond</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 05/04/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class GetNodeCatalogTransactionsMsjRequest extends PackageContent {

    /**
     * Represent the offset
     */
    private Integer offset;

    /**
     * Represent the max
     */
    private Integer max;

    /**
     * Constructor
     */
    public GetNodeCatalogTransactionsMsjRequest(){
        super();
    }

    /**
     * Constructor with parameters
     *
     * @param offset
     * @param max
     */
    public GetNodeCatalogTransactionsMsjRequest(Integer offset, Integer max){
        super();
        this.offset = offset;
        this.max = max;
    }

    /**
     * Get the first record to make pagination
     *
     * @return int
     */
    public Integer getOffset() {
        return offset;
    }

    /**
     * Get the number of max the record to return
     *
     * @return int
     */
    public Integer getMax() {
        return max;
    }
}
