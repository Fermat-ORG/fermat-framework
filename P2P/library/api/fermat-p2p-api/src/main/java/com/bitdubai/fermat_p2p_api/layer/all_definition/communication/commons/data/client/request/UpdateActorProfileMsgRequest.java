package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.request;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.PackageContent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.Profile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.util.GsonProvider;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.request.CheckInProfileMsgRequest</code>
 * represent the message to request to update a profile with the node<p/>
 *
 * Created by Roberto Requena - (rart3001@gmail.com) on 20/06/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class UpdateActorProfileMsgRequest extends PackageContent {

    /**
     * Represent the profile to Update
     */
    private Profile profileToUpdate;

    /**
     * Constructor with parameters
     *
     * @param profileToUpdate
     */
    public UpdateActorProfileMsgRequest(Profile profileToUpdate) {
        this.profileToUpdate = profileToUpdate;
    }

    /**
     * Gets the value of profileToUpdate and returns
     *
     * @return profileToUpdate
     */
    public Profile getProfileToUpdate() {
        return profileToUpdate;
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
    public static UpdateActorProfileMsgRequest parseContent(String content) {
        return GsonProvider.getGson().fromJson(content, UpdateActorProfileMsgRequest.class);
    }
}
