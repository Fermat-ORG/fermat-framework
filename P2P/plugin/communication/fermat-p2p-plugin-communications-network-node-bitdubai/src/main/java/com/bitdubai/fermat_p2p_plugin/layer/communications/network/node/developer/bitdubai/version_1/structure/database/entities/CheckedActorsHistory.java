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
 * The persistent class for the "CHECKED_ACTORS_HISTORY" database table.
 * 
 */
@Entity
@Table(name="\"CHECKED_ACTORS_HISTORY\"")
@NamedQuery(name="CheckedActorsHistory.findAll", query="SELECT c FROM CheckedActorsHistory c")
public class CheckedActorsHistory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="\"IDENTITY_PUBLIC_KEY\"", unique=true, nullable=false, length=255)
	private String identityPublicKey;

	@Column(name="\"ACTOR_TYPE\"", nullable=false, length=50)
	private String actorType;

	@Column(name="\"ALIAS\"", nullable=false, length=50)
	private String alias;

	@Column(name="\"CHECK_TYPE\"", nullable=false, length=3)
	private String checkType;

	@Column(name="\"CHECKED_TIMESTAMP\"", nullable=false)
	private Timestamp checkedTimestamp;

	@Column(name="\"CLIENT_IDENTITY_PUBLIC_KEY\"", nullable=false, length=255)
	private String clientIdentityPublicKey;

	@Column(name="\"EXTRA_DATA\"", nullable=false, length=255)
	private String extraData;

	@Column(name="\"LAST_LATITUDE\"", nullable=false)
	private double lastLatitude;

	@Column(name="\"LAST_LONGITUDE\"", nullable=false)
	private double lastLongitude;

	@Column(name="\"NAME\"", nullable=false, length=50)
	private String name;

	@Column(name="\"PHOTO\"", nullable=false)
	private Object photo;

	public CheckedActorsHistory() {
	}

	public String getIdentityPublicKey() {
		return this.identityPublicKey;
	}

	public void setIdentityPublicKey(String identityPublicKey) {
		this.identityPublicKey = identityPublicKey;
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

	public String getCheckType() {
		return this.checkType;
	}

	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}

	public Timestamp getCheckedTimestamp() {
		return this.checkedTimestamp;
	}

	public void setCheckedTimestamp(Timestamp checkedTimestamp) {
		this.checkedTimestamp = checkedTimestamp;
	}

	public String getClientIdentityPublicKey() {
		return this.clientIdentityPublicKey;
	}

	public void setClientIdentityPublicKey(String clientIdentityPublicKey) {
		this.clientIdentityPublicKey = clientIdentityPublicKey;
	}

	public String getExtraData() {
		return this.extraData;
	}

	public void setExtraData(String extraData) {
		this.extraData = extraData;
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

	public Object getPhoto() {
		return this.photo;
	}

	public void setPhoto(Object photo) {
		this.photo = photo;
	}

}