package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;


/**
 * The persistent class for the "NODE_CONNECTION_HISTORY" database table.
 * 
 */
public class NodeConnectionHistory extends AbstractBaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private UUID uuid;

	private String identityPublicKey;

	private Timestamp connectionTimestamp;

	private Integer defaultPort;

	private String ip;

	private double lastLatitude;

	private double lastLongitude;

	private String status;

	public NodeConnectionHistory() {
        super();
        this.uuid = UUID.randomUUID();
        this.connectionTimestamp = new Timestamp(System.currentTimeMillis());
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

    public double getLastLatitude() {
        return lastLatitude;
    }

    public void setLastLatitude(double lastLatitude) {
        this.lastLatitude = lastLatitude;
    }

    public double getLastLongitude() {
        return lastLongitude;
    }

    public void setLastLongitude(double lastLongitude) {
        this.lastLongitude = lastLongitude;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public String getId() {
        return uuid.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NodeConnectionHistory)) return false;
        NodeConnectionHistory that = (NodeConnectionHistory) o;
        return Objects.equals(getLastLatitude(), that.getLastLatitude()) &&
                Objects.equals(getLastLongitude(), that.getLastLongitude()) &&
                Objects.equals(getIdentityPublicKey(), that.getIdentityPublicKey()) &&
                Objects.equals(getConnectionTimestamp(), that.getConnectionTimestamp()) &&
                Objects.equals(getDefaultPort(), that.getDefaultPort()) &&
                Objects.equals(getIp(), that.getIp()) &&
                Objects.equals(getStatus(), that.getStatus());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdentityPublicKey(), getConnectionTimestamp(), getDefaultPort(), getIp(), getLastLatitude(), getLastLongitude(), getStatus());
    }

    @Override
    public String toString() {
        return "NodeConnectionHistory{" +
                "identityPublicKey='" + identityPublicKey + '\'' +
                '}';
    }
}