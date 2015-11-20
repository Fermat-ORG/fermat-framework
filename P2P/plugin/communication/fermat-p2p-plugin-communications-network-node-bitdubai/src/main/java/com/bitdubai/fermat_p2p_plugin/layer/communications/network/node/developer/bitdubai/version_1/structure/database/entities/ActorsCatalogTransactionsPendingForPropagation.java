package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.entities;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the "ACTORS_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION" database table.
 * 
 */
@Entity
@Table(name="\"ACTORS_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION\"")
@NamedQuery(name="ActorsCatalogTransactionsPendingForPropagation.findAll", query="SELECT a FROM ActorsCatalogTransactionsPendingForPropagation a")
public class ActorsCatalogTransactionsPendingForPropagation implements Serializable {
	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="\"HASH_ID\"", unique=true, nullable=false, length=100)
	private String hashId;

	@Column(name="\"PHOTO\"", unique=true, nullable=false)
	private String photo;

	@Column(name="\"ACTOR_TYPE\"", nullable=false, length=50)
	private String actorType;

	@Column(name="\"ALIAS\"", nullable=false, length=50)
	private String alias;

	@Column(name="\"EXTRA_DATA\"", nullable=false, length=255)
	private String extraData;

	@Column(name="\"HOME_NODE_IDENTITY_PUBLIC_KEY\"", nullable=false, length=255)
	private String homeNodeIdentityPublicKey;

	@Column(name="\"HOSTED_TIMESTAMP\"", nullable=false)
	private Timestamp hostedTimestamp;

	@Column(name="\"IDENTITY_PUBLIC_KEY\"", nullable=false, length=255)
	private String identityPublicKey;

	@Column(name="\"LAST_LATITUDE\"", nullable=false)
	private double lastLatitude;

	@Column(name="\"LAST_LONGITUDE\"", nullable=false)
	private double lastLongitude;

	@Column(name="\"NAME\"", nullable=false, length=50)
	private String name;

	@Column(name="\"TYPE\"", nullable=false, length=10)
	private String type;

	public ActorsCatalogTransactionsPendingForPropagation() {
	}

	public String getHashId() {
		return hashId;
	}

	public void setHashId(String hashId) {
		this.hashId = hashId;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getActorType() {
		return this.actorType;
	}

	public void setActorType(String actorType) {
		this.actorType = actorType;
	}

	public String getAlias() {
		return this.alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getExtraData() {
		return this.extraData;
	}

	public void setExtraData(String extraData) {
		this.extraData = extraData;
	}

	public String getHomeNodeIdentityPublicKey() {
		return this.homeNodeIdentityPublicKey;
	}

	public void setHomeNodeIdentityPublicKey(String homeNodeIdentityPublicKey) {
		this.homeNodeIdentityPublicKey = homeNodeIdentityPublicKey;
	}

	public Timestamp getHostedTimestamp() {
		return this.hostedTimestamp;
	}

	public void setHostedTimestamp(Timestamp hostedTimestamp) {
		this.hostedTimestamp = hostedTimestamp;
	}

	public String getIdentityPublicKey() {
		return this.identityPublicKey;
	}

	public void setIdentityPublicKey(String identityPublicKey) {
		this.identityPublicKey = identityPublicKey;
	}

	public double getLastLatitude() {
		return this.lastLatitude;
	}

	public void setLastLatitude(double lastLatitude) {
		this.lastLatitude = lastLatitude;
	}

	public double getLastLongitude() {
		return this.lastLongitude;
	}

	public void setLastLongitude(double lastLongitude) {
		this.lastLongitude = lastLongitude;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

}