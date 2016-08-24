package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond;

import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.ActorProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.util.GsonProvider;

import java.util.List;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.ActorListMsgRespond</code>
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 24/06/2016.
 *
 * @author  lnacosta
 * @version 1.0
 * @since   Java JDK 1.7
 */
public class ActorListMsgRespond extends MsgRespond {

    /**
     * Represent the profile list
     */
    private List<ActorProfile> profileList;

    /**
     * Represent the networkServicePublicKey
     */
    private String networkServiceType;

    /**
     * Constructor with parameters
     *
     * @param status
     * @param details
     * @param profileList
     * @param networkServiceType
     */
    public ActorListMsgRespond(
            UUID packageId,final STATUS                  status                 ,
            final String                  details                ,
            final List<ActorProfile> profileList            ,
            final String                  networkServiceType) {

        super(packageId,
                status ,
                details
        );

        this.profileList             = profileList            ;
        this.networkServiceType = networkServiceType;
    }

    /**
     * Gets the value of profileList and returns
     *
     * @return profileList
     */
    public List<ActorProfile> getActors() {
        return profileList;
    }

    /**
     * Gets the value of networkServiceType and returns
     * @return networkServiceType
     */
    public String getNetworkServiceType() {
        return networkServiceType;
    }


    /**
     * Generate the json representation
     * @return String
     */
    @Override
    public String toJson() {
        return GsonProvider.getGson().toJson(this, getClass());
    }

    /**
     * Get the object
     *
     * @param content
     * @return PackageContent
     */
    public static ActorListMsgRespond parseContent(String content) {
        return GsonProvider.getGson().fromJson(content, ActorListMsgRespond.class);
    }

    @Override
    public String toString() {
        return "ActorListMsgRespond{" +
                ", profileList=" + profileList +
                ", NetworkServiceType='" + networkServiceType + '\'' +
                '}';
    }
}
