package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.request;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.PackageContent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.Profile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.util.GsonProvider;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.request.CheckOutProfileMsgRequest</code>
 * represent the message to request to unregister a profile with the node<p/>
 *
 * Created by Roberto Requena - (rart3001@gmail.com) on 29/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CheckOutProfileMsgRequest extends PackageContent {

    /**
     * Represent the identityPublicKey of the profile
     */
    private String identityPublicKey;

    /**
     * Constructor with parameters
     *
     * @param profileToUnRegister
     */
    public CheckOutProfileMsgRequest(Profile profileToUnRegister) {
        this.identityPublicKey = profileToUnRegister.getIdentityPublicKey();
    }

    /**
     * Gets the value of identityPublicKey and returns
     *
     * @return identityPublicKey
     */
    public String getIdentityPublicKey() {
        return identityPublicKey;
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
    public static CheckOutProfileMsgRequest parseContent(String content) {
        return GsonProvider.getGson().fromJson(content, CheckOutProfileMsgRequest.class);
    }
}
