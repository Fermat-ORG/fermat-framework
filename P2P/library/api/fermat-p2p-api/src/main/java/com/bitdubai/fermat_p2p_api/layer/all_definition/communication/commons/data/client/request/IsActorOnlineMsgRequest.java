package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.request;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.PackageContent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.Profile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.util.GsonProvider;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.MessageContentType;

/**
 * This class represents the the message to request if an actor is online.
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 16/08/16.
 */
public class IsActorOnlineMsgRequest extends PackageContent {

    /**
     * Represents the requested profile
     */
    private String requestedProfilePublicKey;

    /**
     * Default constructor with parameters
     * @param requestedProfile
     */
    public IsActorOnlineMsgRequest(
            Profile requestedProfile) {

        super(MessageContentType.JSON);
        this.requestedProfilePublicKey = requestedProfile.getIdentityPublicKey();
    }

    /**
     * This method returns the requested profile
     *
     * @return requestedProfile
     */
    public String getRequestedProfilePublicKey() {

        return requestedProfilePublicKey;
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
    public static IsActorOnlineMsgRequest parseContent(String content) {
        return GsonProvider.getGson().fromJson(content, IsActorOnlineMsgRequest.class);
    }

    @Override
    public String toString() {
        return "IsActorOnlineMsgRequest{" +
                ", requestedProfilePublicKey=" + requestedProfilePublicKey +
                '}';
    }
}
