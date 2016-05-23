package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.data.node.request;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.PackageContent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.NodeProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.util.GsonProvider;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.node.request.UpdateNodeInCatalogMsgRequest</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 05/04/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class UpdateNodeInCatalogMsgRequest extends PackageContent {

    /**
     * Represent the node profile
     */
    private NodeProfile nodeProfile;

    /**
     * Constructor
     */
    public UpdateNodeInCatalogMsgRequest(){
        super();
    }

    /**
     * Constructor whit parameters
     * @param nodeProfile
     */
    public UpdateNodeInCatalogMsgRequest(NodeProfile nodeProfile) {
        this.nodeProfile = nodeProfile;
    }

    /**
     * Get the node profile to add
     * @return nodeProfile
     */
    public NodeProfile getNodeProfile() {
        return nodeProfile;
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
    public static UpdateNodeInCatalogMsgRequest parseContent(String content) {
        return GsonProvider.getGson().fromJson(content, UpdateNodeInCatalogMsgRequest.class);
    }
}
