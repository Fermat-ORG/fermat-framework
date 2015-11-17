package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the "NODE_CONNECTION_HISTORY" database table.
 * 
 */
@Entity
@Table(name="\"NODE_CONNECTION_HISTORY\"")
@NamedQuery(name="NodeConnectionHistory.findAll", query="SELECT n FROM NodeConnectionHistory n")
public class NodeConnectionHistory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="\"IDENTITY_PUBLIC_KEY\"", unique=true, nullable=false, length=255)
	private String identityPublicKey;

	@Column(name="\"CONECCTION_TIMESTAMP\"", nullable=false)
	private Timestamp conecctionTimestamp;

	@Column(name="\"DEFAULT_PORT\"", nullable=false)
	private Integer defaultPort;

	@Column(name="\"IP\"", nullable=false, length=19)
	private String ip;

	@Column(name="\"LALTITUDE\"", nullable=false)
	private double laltitude;

	@Column(name="\"LOGITUDE\"", nullable=false)
	private double logitude;

	@Column(name="\"STATUS\"", nullable=false, length=10)
	private String status;

	public NodeConnectionHistory() {
	}

	public String getIdentityPublicKey() {
		return this.identityPublicKey;
	}

	public void setIdentityPublicKey(String identityPublicKey) {
		this.identityPublicKey = identityPublicKey;
	}

	public Timestamp getConecctionTimestamp() {
		return this.conecctionTimestamp;
	}

	public void setConecctionTimestamp(Timestamp conecctionTimestamp) {
		this.conecctionTimestamp = conecctionTimestamp;
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

	public double getLogitude() {
		return this.logitude;
	}

	public void setLogitude(double logitude) {
		this.logitude = logitude;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}