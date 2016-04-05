/*
 * @#GetNodeCatalogMsjRequest.java - 2016
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.node.respond;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.MsgRespond;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.node.respond.GetNodeCatalogMsjRequest</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 05/04/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class GetNodeCatalogMsjRequest extends MsgRespond {

    /**
     * Constructor with parameters
     *
     * @param status
     * @param details
     */
    public GetNodeCatalogMsjRequest(STATUS status, String details) {
        super(status, details);
    }
}
