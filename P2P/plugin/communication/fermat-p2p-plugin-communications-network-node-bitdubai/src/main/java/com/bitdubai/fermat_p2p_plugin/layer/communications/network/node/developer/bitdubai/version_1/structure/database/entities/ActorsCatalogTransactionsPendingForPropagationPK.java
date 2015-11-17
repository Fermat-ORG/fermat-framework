package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the "ACTORS_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION" database table.
 * 
 */
@Embeddable
public class ActorsCatalogTransactionsPendingForPropagationPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="\"HASH_ID\"", unique=true, nullable=false, length=100)
	private String hashId;

	@Column(name="\"PHOTO\"", unique=true, nullable=false)
	private String photo;

	public ActorsCatalogTransactionsPendingForPropagationPK() {
	}
	public String getHashId() {
		return this.hashId;
	}
	public void setHashId(String hashId) {
		this.hashId = hashId;
	}
	public String getPhoto() {
		return this.photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ActorsCatalogTransactionsPendingForPropagationPK)) {
			return false;
		}
		ActorsCatalogTransactionsPendingForPropagationPK castOther = (ActorsCatalogTransactionsPendingForPropagationPK)other;
		return 
			this.hashId.equals(castOther.hashId)
			&& this.photo.equals(castOther.photo);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.hashId.hashCode();
		hash = hash * prime + this.photo.hashCode();
		
		return hash;
	}
}