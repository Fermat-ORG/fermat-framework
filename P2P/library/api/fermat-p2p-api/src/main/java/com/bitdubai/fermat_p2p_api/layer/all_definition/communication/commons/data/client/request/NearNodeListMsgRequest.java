/*
 * @#NearNodeListMsgRequest.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.request;

import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.PackageContent;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.request.NearNodeListMsgRequest</code>
 * represent the message to request the near node list
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 26/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class NearNodeListMsgRequest extends PackageContent {

    /**
     * Represent the actual client location
     */
    private Location clientLocation;

    /**
     * Constructor with parameter
     *
     * @param clientLocation
     */
    public NearNodeListMsgRequest(Location clientLocation) {
        this.clientLocation = clientLocation;
    }

    /**
     * Gets the value of clientLocation and returns
     *
     * @return clientLocation
     */
    public Location getClientLocation() {
        return clientLocation;
    }


}
