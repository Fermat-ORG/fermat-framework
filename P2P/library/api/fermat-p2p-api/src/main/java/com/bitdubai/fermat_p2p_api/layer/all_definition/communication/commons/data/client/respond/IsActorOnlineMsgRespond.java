package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.base.MsgRespond;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.base.STATUS;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.enums.ProfileStatus;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.util.GsonProvider;

import java.util.UUID;

/**
 * Created by Manuel Perez P. (darkpriestrelative@gmail.com) on 17/08/16.
 */
public class IsActorOnlineMsgRespond extends MsgRespond {

    /**
     * Represents the actor profile
     */
    private String requestedProfilePublicKey;

    /**
     * Represents the profile status from the requested profile
     */
    private ProfileStatus profileStatus;

    /**
     * Represent the networkServicePublicKey
     */
    private String networkServiceType;

    /**
     * Constructor with parameters
     *
     * @param packageId
     * @param status
     * @param details
     */
    public IsActorOnlineMsgRespond(
            UUID packageId,
            STATUS status,
            String details,
            String requestedProfilePublicKey,
            ProfileStatus profileStatus,
            String networkServiceType
    ) {
        super(
                packageId,
                status,
                details);
        this.requestedProfilePublicKey = requestedProfilePublicKey;
        this.profileStatus = profileStatus;
        this.networkServiceType = networkServiceType;
    }

    /**
     * This method returns the requested profile
     * @return
     */
    public String getRequestedProfile() {
        return requestedProfilePublicKey;
    }

    /**
     * This method returns the profile status
     * @return
     */
    public ProfileStatus getProfileStatus() {
        return profileStatus;
    }

    /**
     * Generate the json representation
     * @return String
     */
    @Override
    public String toJson() {
        return GsonProvider.getGson().toJson(this, getClass());
    }

    public String getNetworkServiceType() {
        return networkServiceType;
    }

    /**
     * Get the object
     *
     * @param content
     * @return PackageContent
     */
    public static IsActorOnlineMsgRespond parseContent(String content) {
        return GsonProvider.getGson().fromJson(content, IsActorOnlineMsgRespond.class);
    }

    @Override
    public String toString() {
        return "IsActorOnlineMsgRespond{" +
                ", requestedProfilePublicKey=" + requestedProfilePublicKey +
                ", profileStatus=" + profileStatus +
                ", networkServiceType='" + networkServiceType + '\'' +
                '}';
    }
}
