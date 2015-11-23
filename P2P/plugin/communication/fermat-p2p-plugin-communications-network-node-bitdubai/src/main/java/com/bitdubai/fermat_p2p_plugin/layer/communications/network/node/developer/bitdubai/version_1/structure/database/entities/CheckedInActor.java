package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.entities;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the "CHECKED_IN_ACTORS" database table.
 * 
 */
@Entity
@Table(name="\"CHECKED_IN_ACTORS\"")
@NamedQuery(name="CheckedInActor.findAll", query="SELECT c FROM CheckedInActor c")
public class CheckedInActor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="\"IDENTITY_PUBLIC_KEY\"", unique=true, nullable=false, length=255)
	private String identityPublicKey;

	@Column(name="\"ACTOR_TYPE\"", nullable=false, length=50)
	private String actorType;

	@Column(name="\"ALIAS\"", nullable=false, length=50)
	private String alias;

	@Column(name="\"CHECKED_IN_TIMESTAMP\"", nullable=false)
	private Timestamp checkedInTimestamp;

	@Column(name="\"EXTRA_DATA\"", nullable=false, length=255)
	private String extraData;

	@Column(name="\"LATITUDE\"", nullable=false)
	private double latitude;

	@Column(name="\"LONGITUDE\"", nullable=false)
	private double longitude;

	@Column(name="\"NAME\"", nullable=false, length=50)
	private String name;

	@Column(name="\"PHOTO\"", nullable=false)
	private Object photo;

	//bi-directional many-to-one association to CheckedInNetworkService
	@ManyToOne
	@JoinColumn(name="\"NS_IDENTITY_PUBLIC_KEY\"")
	private CheckedInNetworkService checkedInNetworkService;

	public CheckedInActor() {
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

	public Timestamp getCheckedInTimestamp() {
		return this.checkedInTimestamp;
	}

	public void setCheckedInTimestamp(Timestamp checkedInTimestamp) {
		this.checkedInTimestamp = checkedInTimestamp;
	}

	public String getExtraData() {
		return this.extraData;
	}

	public void setExtraData(String extraData) {
		this.extraData = extraData;
	}

	public double getLatitude() {
		return this.latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return this.longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
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

	public CheckedInNetworkService getCheckedInNetworkService() {
		return this.checkedInNetworkService;
	}

	public void setCheckedInNetworkService(CheckedInNetworkService checkedInNetworkService) {
		this.checkedInNetworkService = checkedInNetworkService;
	}

}