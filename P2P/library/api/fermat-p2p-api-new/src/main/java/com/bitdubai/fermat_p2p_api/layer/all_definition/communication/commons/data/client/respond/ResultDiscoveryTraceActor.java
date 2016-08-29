/*
* @#ResultDiscoveryTraceActor.java - 2016
* Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
* BITDUBAI/CONFIDENTIAL
*/
package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.ActorProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.NodeProfile;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.ResultDiscoveryTraceActor</code>
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 29/04/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class ResultDiscoveryTraceActor {

    /*
     * Represent the nodeProfile
     */
    private NodeProfile nodeProfile;

    /*
     * Represent the actorProfile
     */
    private ActorProfile actorProfile;

    public ResultDiscoveryTraceActor(NodeProfile nodeProfile, ActorProfile actorProfile){
        this.nodeProfile  = nodeProfile;
        this.actorProfile = actorProfile;
    }

    public NodeProfile getNodeProfile() {
        return nodeProfile;
    }

    public ActorProfile getActorProfile() {
        return actorProfile;
    }
}
