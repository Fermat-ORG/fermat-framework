package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.DiscoveryQueryParameters;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.Profile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.util.GsonProvider;

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
     * Represent the discoveryQueryParameters
     */
    private DiscoveryQueryParameters discoveryQueryParameters;

    /**
     * Constructor with parameters
     *
     * @param status
     * @param details
     * @param profileList
     */
    public CheckInProfileListMsgRespond(STATUS status, String details, List<Profile> profileList, DiscoveryQueryParameters discoveryQueryParameters) {
        super(status, details);
        this.profileList = profileList;
        this.discoveryQueryParameters = discoveryQueryParameters;
    }

    /**
     * Gets the value of profileList and returns
     *
     * @return profileList
     */
    public List<Profile> getProfileList() {
        return profileList;
    }

    /**
     * Gets the value of discoveryQueryParameters to validate if it was NETWORK_SERVICE or Actor
     * when occurs an fails to realize the ActorTraceDiscoveryQueryRequestProcessor
     * @return discoveryQueryParameters
     */
    public DiscoveryQueryParameters getDiscoveryQueryParameters() {
        return discoveryQueryParameters;
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
    public static CheckInProfileListMsgRespond parseContent(String content) {
        return GsonProvider.getGson().fromJson(content, CheckInProfileListMsgRespond.class);
    }
}
