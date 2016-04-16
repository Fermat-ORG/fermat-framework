package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.data.node.respond;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.MsgRespond;
import com.google.gson.Gson;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.node.respond.AddNodeToCatalogMsjRespond</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 05/04/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class AddNodeToCatalogMsjRespond extends MsgRespond {

    /**
     * Represent the identityPublicKey of the profile
     */
    private String identityPublicKey;

    /**
     * Constructor with parameters
     *
     * @param status
     * @param details
     */
    public AddNodeToCatalogMsjRespond(STATUS status, String details, String identityPublicKey) {
        super(status, details);
        this.identityPublicKey = identityPublicKey;
    }

    /**
     * Get the IdentityPublicKey
     * @return String
     */
    public String getIdentityPublicKey() {
        return identityPublicKey;
    }

    public static AddNodeToCatalogMsjRespond parseContent(String content) {

        return new Gson().fromJson(content, AddNodeToCatalogMsjRespond.class);
    }
}
