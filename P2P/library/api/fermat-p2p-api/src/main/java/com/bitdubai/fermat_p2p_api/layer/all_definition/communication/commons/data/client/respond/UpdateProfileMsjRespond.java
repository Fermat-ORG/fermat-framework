package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.util.GsonProvider;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.UpdateProfileMsjRespond</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 20/06/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class UpdateProfileMsjRespond extends MsgRespond {

    /**
     * Represent the identityPublicKey of the profile
     */
    private String identityPublicKey;

    /**
     * Constructor with parameters
     *
     * @param status
     * @param details
     * @param identityPublicKey
     */
    public UpdateProfileMsjRespond(STATUS status, String details, String identityPublicKey) {
        super(status, details);
        this.identityPublicKey = identityPublicKey;
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
    public static UpdateProfileMsjRespond parseContent(String content) {
        return GsonProvider.getGson().fromJson(content, UpdateProfileMsjRespond.class);
    }

}
