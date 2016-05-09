package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities;

import java.io.Serializable;


/**
 * The persistent class for the "ACTORS_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION" database table.
 * 
 */
public class ActorsCatalogTransactionsPendingForPropagation extends ActorsCatalogTransaction  implements Serializable {

	public ActorsCatalogTransactionsPendingForPropagation() {
		super();
	}

    /**
     * Get the ActorsCatalogTransaction representation
     *
     * @return ActorsCatalogTransaction
     */
	public ActorsCatalogTransaction getActorsCatalogTransaction(){
        return (ActorsCatalogTransaction) this;
    }
}