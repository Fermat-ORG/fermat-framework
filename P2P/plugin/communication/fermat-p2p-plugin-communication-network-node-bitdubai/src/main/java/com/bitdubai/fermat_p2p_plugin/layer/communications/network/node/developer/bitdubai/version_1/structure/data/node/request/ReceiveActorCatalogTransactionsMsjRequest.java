package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.data.node.request;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.PackageContent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.util.GsonProvider;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.ActorsCatalogTransaction;

import java.util.List;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.node.request.ReceiveActorCatalogTransactionsMsjRequest</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 05/04/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class ReceiveActorCatalogTransactionsMsjRequest extends PackageContent {

    /**
     * Represent the list of transactions
     */
    private List<ActorsCatalogTransaction> actorsCatalogTransactions;

    /**
     * Constructor
     */
    public ReceiveActorCatalogTransactionsMsjRequest(){
        super();
    }

    /**
     * Constructor with parameters
     * @param actorsCatalogTransactions
     */
    public ReceiveActorCatalogTransactionsMsjRequest(List<ActorsCatalogTransaction> actorsCatalogTransactions) {
        super();
        this.actorsCatalogTransactions = actorsCatalogTransactions;
    }

    /**
     * Get the list of actors catalog transactions
     *
     * @return List<ActorsCatalogTransaction>
     */
    public List<ActorsCatalogTransaction> getActorsCatalogTransactions() {
        return actorsCatalogTransactions;
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
    public static ReceiveActorCatalogTransactionsMsjRequest parseContent(String content) {
        return GsonProvider.getGson().fromJson(content, ReceiveActorCatalogTransactionsMsjRequest.class);
    }
}
