/*
 * @#CheckInProfileListRespondMsg.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.Profile;

import java.util.List;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.CheckInProfileListMsgRespond</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 27/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CheckInProfileListMsgRespond extends MsgRespond {

    /**
     * Represent the profile list
     */
    private List<Profile> profileList;

    /**
     * Constructor with parameters
     *
     * @param status
     * @param details
     * @param profileList
     */
    public CheckInProfileListMsgRespond(STATUS status, String details, List<Profile> profileList) {
        super(status, details);
        this.profileList = profileList;
    }

    /**
     * Gets the value of profileList and returns
     *
     * @return profileList
     */
    public List<Profile> getProfileList() {
        return profileList;
    }
}
