package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;


/**
 * The persistent class for the "NODE_CONNECTION_HISTORY" database table.
 * 
 */
public class NodeConnectionHistory extends AbstractBaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private String identityPublicKey;

	private Timestamp connectionTimestamp;

	private Integer defaultPort;

	private String ip;

	private double latitude;

	private double longitude;

	private String status;

	public NodeConnectionHistory() {
		super();
	}

	public Timestamp getConnectionTimestamp() {
		return connectionTimestamp;
	}

	public void setConnectionTimestamp(Timestamp connectionTimestamp) {
		this.connectionTimestamp = connectionTimestamp;
	}

	public Integer getDefaultPort() {
		return defaultPort;
	}

	public void setDefaultPort(Integer defaultPort) {
		this.defaultPort = defaultPort;
	}

	public String getIdentityPublicKey() {
		return identityPublicKey;
	}

	public void setIdentityPublicKey(String identityPublicKey) {
		this.identityPublicKey = identityPublicKey;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

    @Override
    public String getId() {
        return identityPublicKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NodeConnectionHistory)) return false;
        NodeConnectionHistory that = (NodeConnectionHistory) o;
        return Objects.equals(getLatitude(), that.getLatitude()) &&
                Objects.equals(getLongitude(), that.getLongitude()) &&
                Objects.equals(getIdentityPublicKey(), that.getIdentityPublicKey()) &&
                Objects.equals(getConnectionTimestamp(), that.getConnectionTimestamp()) &&
                Objects.equals(getDefaultPort(), that.getDefaultPort()) &&
                Objects.equals(getIp(), that.getIp()) &&
                Objects.equals(getStatus(), that.getStatus());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdentityPublicKey(), getConnectionTimestamp(), getDefaultPort(), getIp(), getLatitude(), getLongitude(), getStatus());
    }

    @Override
    public String toString() {
        return "NodeConnectionHistory{" +
                "identityPublicKey='" + identityPublicKey + '\'' +
                '}';
    }
}