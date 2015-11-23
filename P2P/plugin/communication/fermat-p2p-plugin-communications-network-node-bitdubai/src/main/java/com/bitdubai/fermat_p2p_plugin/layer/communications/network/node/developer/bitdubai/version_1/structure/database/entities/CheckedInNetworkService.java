package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * The persistent class for the "CHECKED_IN_NETWORK_SERVICES" database table.
 * 
 */
@Entity
@Table(name="\"CHECKED_IN_NETWORK_SERVICES\"")
@NamedQuery(name="CheckedInNetworkService.findAll", query="SELECT c FROM CheckedInNetworkService c")
public class CheckedInNetworkService implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="\"IDENTITY_PUBLIC_KEY\"", unique=true, nullable=false, length=255)
	private String identityPublicKey;

	@Column(name="\"CHECKED_IN_TIMESTAMP\"", nullable=false)
	private Timestamp checkedInTimestamp;

	@Column(name="\"EXTRA_DATA\"", nullable=false, length=255)
	private String extraData;

	@Column(name="\"LATITUDE\"", nullable=false)
	private double latitude;

	@Column(name="\"LONGITUDE\"", nullable=false)
	private double longitude;

	@Column(name="\"NETWORK_SERVICE_TYPE\"", nullable=false, length=50)
	private String networkServiceType;

	//bi-directional many-to-one association to CheckedInActor
	@OneToMany(mappedBy="checkedInNetworkService")
	private List<CheckedInActor> checkedInActors;

	//bi-directional many-to-one association to CheckedInClient
	@ManyToOne
	@JoinColumn(name="\"CLIENT_IDENTITY_PUBLIC_KEY\"")
	private CheckedInClient checkedInClient;

	public CheckedInNetworkService() {
	}

	public String getIdentityPublicKey() {
		return this.identityPublicKey;
	}

	public void setIdentityPublicKey(String identityPublicKey) {
		this.identityPublicKey = identityPublicKey;
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

	public String getNetworkServiceType() {
		return this.networkServiceType;
	}

	public void setNetworkServiceType(String networkServiceType) {
		this.networkServiceType = networkServiceType;
	}

	public List<CheckedInActor> getCheckedInActors() {
		return this.checkedInActors;
	}

	public void setCheckedInActors(List<CheckedInActor> checkedInActors) {
		this.checkedInActors = checkedInActors;
	}

	public CheckedInActor addCheckedInActor(CheckedInActor checkedInActor) {
		getCheckedInActors().add(checkedInActor);
		checkedInActor.setCheckedInNetworkService(this);

		return checkedInActor;
	}

	public CheckedInActor removeCheckedInActor(CheckedInActor checkedInActor) {
		getCheckedInActors().remove(checkedInActor);
		checkedInActor.setCheckedInNetworkService(null);

		return checkedInActor;
	}

	public CheckedInClient getCheckedInClient() {
		return this.checkedInClient;
	}

	public void setCheckedInClient(CheckedInClient checkedInClient) {
		this.checkedInClient = checkedInClient;
	}

}