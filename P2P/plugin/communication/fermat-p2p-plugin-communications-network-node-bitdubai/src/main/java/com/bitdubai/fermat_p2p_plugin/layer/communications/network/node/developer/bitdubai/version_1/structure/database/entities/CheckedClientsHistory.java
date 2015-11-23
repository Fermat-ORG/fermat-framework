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
 * The persistent class for the "CHECKED_CLIENTS_HISTORY" database table.
 * 
 */
@Entity
@Table(name="\"CHECKED_CLIENTS_HISTORY\"")
@NamedQuery(name="CheckedClientsHistory.findAll", query="SELECT c FROM CheckedClientsHistory c")
public class CheckedClientsHistory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="\"IDENTITY_PUBLIC_KEY\"", unique=true, nullable=false, length=255)
	private String identityPublicKey;

	@Column(name="\"CHECK_TYPE\"", nullable=false, length=3)
	private String checkType;

	@Column(name="\"CHECKED_TIMESTAMP\"", nullable=false)
	private Timestamp checkedTimestamp;

	@Column(name="\"DEVICE_TYPE\"", nullable=false, length=50)
	private String deviceType;

	@Column(name="\"LATITUDE\"", nullable=false)
	private double latitude;

	@Column(name="\"LONGITUDE\"", nullable=false)
	private double longitude;

	public CheckedClientsHistory() {
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

	public String getDeviceType() {
		return this.deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
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

}