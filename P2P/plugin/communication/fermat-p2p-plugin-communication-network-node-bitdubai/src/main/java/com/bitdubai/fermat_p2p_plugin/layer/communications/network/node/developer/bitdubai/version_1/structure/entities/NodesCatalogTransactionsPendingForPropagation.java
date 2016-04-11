package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities;

import java.io.Serializable;


/**
 * The persistent class for the "NODES_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION" database table.
 * 
 */
public class NodesCatalogTransactionsPendingForPropagation extends NodesCatalogTransaction implements Serializable {

	public NodesCatalogTransactionsPendingForPropagation() {
		super();
	}

	/**
	 * Get the NodesCatalogTransaction representation
	 * @return NodesCatalogTransaction
     */
	public NodesCatalogTransaction getNodesCatalogTransactions(){
		return (NodesCatalogTransaction) this;
	}
}