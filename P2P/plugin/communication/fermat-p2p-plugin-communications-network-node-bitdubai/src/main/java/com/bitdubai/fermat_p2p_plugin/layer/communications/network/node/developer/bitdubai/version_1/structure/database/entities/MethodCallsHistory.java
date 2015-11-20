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
 * The persistent class for the "METHOD_CALLS_HISTORY" database table.
 * 
 */
@Entity
@Table(name="\"METHOD_CALLS_HISTORY\"")
@NamedQuery(name="MethodCallsHistory.findAll", query="SELECT m FROM MethodCallsHistory m")
public class MethodCallsHistory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="\"ID\"", unique=true, nullable=false, length=100)
	private String id;

	@Column(name="\"CREATE_TIMESTAMP\"", nullable=false)
	private Timestamp createTimestamp;

	@Column(name="\"METHOD_NAME\"", nullable=false, length=100)
	private String methodName;

	@Column(name="\"PARAMETERS\"", nullable=false, length=255)
	private String parameters;

	@Column(name="\"PROFILE_IDENTITY_PUBLIC_KEY\"", nullable=false, length=255)
	private String profileIdentityPublicKey;

	public MethodCallsHistory() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Timestamp getCreateTimestamp() {
		return this.createTimestamp;
	}

	public void setCreateTimestamp(Timestamp createTimestamp) {
		this.createTimestamp = createTimestamp;
	}

	public String getMethodName() {
		return this.methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getParameters() {
		return this.parameters;
	}

	public void setParameters(String parameters) {
		this.parameters = parameters;
	}

	public String getProfileIdentityPublicKey() {
		return this.profileIdentityPublicKey;
	}

	public void setProfileIdentityPublicKey(String profileIdentityPublicKey) {
		this.profileIdentityPublicKey = profileIdentityPublicKey;
	}

}