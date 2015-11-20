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
 * The persistent class for the "NODES_CATALOG" database table.
 * 
 */
@Entity
@Table(name="\"NODES_CATALOG\"")
@NamedQuery(name="NodesCatalog.findAll", query="SELECT n FROM NodesCatalog n")
public class NodesCatalog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="\"IDENTITY_PUBLIC_KEY\"", unique=true, nullable=false, length=255)
	private String identityPublicKey;

	@Column(name="\"DEFAULT_PORT\"", nullable=false)
	private Integer defaultPort;

	@Column(name="\"IP\"", nullable=false, length=19)
	private String ip;

	@Column(name="\"LALTITUDE\"", nullable=false)
	private double laltitude;

	@Column(name="\"LAST_CONNECTION_TIMESTAMP\"", nullable=false)
	private Timestamp lastConnectionTimestamp;

	@Column(name="\"LATE_NOTIFICATIONS_COUNTER\"", nullable=false)
	private Integer lateNotificationsCounter;

	@Column(name="\"LOGITUDE\"", nullable=false)
	private double logitude;

	@Column(name="\"NAME\"", nullable=false, length=50)
	private String name;

	@Column(name="\"OFFLINE_COUNTER\"", nullable=false)
	private Integer offlineCounter;

	@Column(name="\"REGISTERED_TIMESTAMP\"", nullable=false)
	private Timestamp registeredTimestamp;

	public NodesCatalog() {
	}

	public String getIdentityPublicKey() {
		return this.identityPublicKey;
	}

	public void setIdentityPublicKey(String identityPublicKey) {
		this.identityPublicKey = identityPublicKey;
	}

	public Integer getDefaultPort() {
		return this.defaultPort;
	}

	public void setDefaultPort(Integer defaultPort) {
		this.defaultPort = defaultPort;
	}

	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public double getLaltitude() {
		return this.laltitude;
	}

	public void setLaltitude(double laltitude) {
		this.laltitude = laltitude;
	}

	public Timestamp getLastConnectionTimestamp() {
		return this.lastConnectionTimestamp;
	}

	public void setLastConnectionTimestamp(Timestamp lastConnectionTimestamp) {
		this.lastConnectionTimestamp = lastConnectionTimestamp;
	}

	public Integer getLateNotificationsCounter() {
		return this.lateNotificationsCounter;
	}

	public void setLateNotificationsCounter(Integer lateNotificationsCounter) {
		this.lateNotificationsCounter = lateNotificationsCounter;
	}

	public double getLogitude() {
		return this.logitude;
	}

	public void setLogitude(double logitude) {
		this.logitude = logitude;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getOfflineCounter() {
		return this.offlineCounter;
	}

	public void setOfflineCounter(Integer offlineCounter) {
		this.offlineCounter = offlineCounter;
	}

	public Timestamp getRegisteredTimestamp() {
		return this.registeredTimestamp;
	}

	public void setRegisteredTimestamp(Timestamp registeredTimestamp) {
		this.registeredTimestamp = registeredTimestamp;
	}

}