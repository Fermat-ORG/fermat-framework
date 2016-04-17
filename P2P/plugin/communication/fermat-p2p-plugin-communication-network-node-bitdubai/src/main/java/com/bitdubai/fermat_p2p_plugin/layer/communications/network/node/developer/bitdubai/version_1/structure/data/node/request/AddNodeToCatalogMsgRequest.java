package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.data.node.request;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.PackageContent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.NodeProfile;
import com.google.gson.Gson;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.node.request.AddNodeToCatalogMsgRequest</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 05/04/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class AddNodeToCatalogMsgRequest extends PackageContent {

    /**
     * Represent the node profile
     */
    private NodeProfile nodeProfile;

    /**
     * Constructor whit parameters
     * @param nodeProfile
     */
    public AddNodeToCatalogMsgRequest(NodeProfile nodeProfile) {
        this.nodeProfile = nodeProfile;
    }

    /**
     * Get the node profile to add
     * @return nodeProfile
     */
    public NodeProfile getNodeProfile() {
        return nodeProfile;
    }

    public static AddNodeToCatalogMsgRequest parseContent(String content) {

        return new Gson().fromJson(content, AddNodeToCatalogMsgRequest.class);
    }
}
