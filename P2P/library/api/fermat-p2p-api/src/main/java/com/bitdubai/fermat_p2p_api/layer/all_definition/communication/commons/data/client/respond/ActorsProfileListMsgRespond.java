/*
 * @#ActorsProfileListMsgRespond.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.ActorProfile;

import java.util.List;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.ActorsProfileListMsgRespond</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 27/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class ActorsProfileListMsgRespond extends MsgRespond {

    /**
     * Represent the profile list
     */
    private List<ActorProfile> profileList;

    /**
     * Constructor with parameters
     *
     * @param status
     * @param details
     * @param profileList
     */
    public ActorsProfileListMsgRespond(STATUS status, String details, List<ActorProfile> profileList) {
        super(status, details);
        this.profileList = profileList;
    }

    /**
     * Gets the value of profileList and returns
     *
     * @return profileList
     */
    public List<ActorProfile> getProfileList() {
        return profileList;
    }
}
