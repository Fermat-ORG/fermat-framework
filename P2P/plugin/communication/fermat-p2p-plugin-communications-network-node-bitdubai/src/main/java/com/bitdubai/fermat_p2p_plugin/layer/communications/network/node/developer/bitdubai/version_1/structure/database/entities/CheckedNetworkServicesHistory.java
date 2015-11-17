package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the "CHECKED_NETWORK_SERVICES_HISTORY" database table.
 * 
 */
@Entity
@Table(name="\"CHECKED_NETWORK_SERVICES_HISTORY\"")
@NamedQuery(name="CheckedNetworkServicesHistory.findAll", query="SELECT c FROM CheckedNetworkServicesHistory c")
public class CheckedNetworkServicesHistory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="\"IDENTITY_PUBLIC_KEY\"", unique=true, nullable=false, length=255)
	private String identityPublicKey;

	@Column(name="\"CHECK_TYPE\"", nullable=false, length=3)
	private String checkType;

	@Column(name="\"CHECKED_TIMESTAMP\"", nullable=false)
	private Timestamp checkedTimestamp;

	@Column(name="\"CLIENT_IDENTITY_PUBLIC_KEY\"", nullable=false, length=255)
	private String clientIdentityPublicKey;

	@Column(name="\"EXTRA_DATA\"", nullable=false, length=255)
	private String extraData;

	@Column(name="\"LATITUDE\"", nullable=false)
	private double latitude;

	@Column(name="\"LONGITUDE\"", nullable=false)
	private double longitude;

	@Column(name="\"NETWORK_SERVICE_TYPE\"", nullable=false, length=50)
	private String networkServiceType;

	public CheckedNetworkServicesHistory() {
	}

	public String getIdentityPublicKey() {
		return this.identityPublicKey;
	}

	public void setIdentityPublicKey(String identityPublicKey) {
		this.identityPublicKey = identityPublicKey;
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

}