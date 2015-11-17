package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the "CLIENTS_CONNECTION_HISTORY" database table.
 * 
 */
@Entity
@Table(name="\"CLIENTS_CONNECTION_HISTORY\"")
@NamedQuery(name="ClientsConnectionHistory.findAll", query="SELECT c FROM ClientsConnectionHistory c")
public class ClientsConnectionHistory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="\"IDENTITY_PUBLIC_KEY\"", unique=true, nullable=false, length=255)
	private String identityPublicKey;

	@Column(name="\"CONNECTION_TIMESTAMP\"", nullable=false)
	private Timestamp connectionTimestamp;

	@Column(name="\"DEVICE_TYPE\"", nullable=false, length=50)
	private String deviceType;

	@Column(name="\"LAST_LATITUDE\"", nullable=false)
	private double lastLatitude;

	@Column(name="\"LAST_LONGITUDE\"", nullable=false)
	private double lastLongitude;

	@Column(name="\"STATUS\"", nullable=false, length=10)
	private String status;

	public ClientsConnectionHistory() {
	}

	public String getIdentityPublicKey() {
		return this.identityPublicKey;
	}

	public void setIdentityPublicKey(String identityPublicKey) {
		this.identityPublicKey = identityPublicKey;
	}

	public Timestamp getConnectionTimestamp() {
		return this.connectionTimestamp;
	}

	public void setConnectionTimestamp(Timestamp connectionTimestamp) {
		this.connectionTimestamp = connectionTimestamp;
	}

	public String getDeviceType() {
		return this.deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
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

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}