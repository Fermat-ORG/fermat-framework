package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;


/**
 * The persistent class for the "CLIENTS_CONNECTION_HISTORY" database table.
 * 
 */
public class ClientsConnectionHistory extends AbstractBaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private UUID uuid;

	private String identityPublicKey;

	private Timestamp connectionTimestamp;

	private String deviceType;

	private double lastLatitude;

	private double lastLongitude;

	private String status;

	public ClientsConnectionHistory() {
        super();
        this.uuid = UUID.randomUUID();
        this.connectionTimestamp = new Timestamp(System.currentTimeMillis());
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
        if (!(o instanceof ClientsConnectionHistory)) return false;
        ClientsConnectionHistory that = (ClientsConnectionHistory) o;
        return Objects.equals(getLastLatitude(), that.getLastLatitude()) &&
                Objects.equals(getLastLongitude(), that.getLastLongitude()) &&
                Objects.equals(getUuid(), that.getUuid()) &&
                Objects.equals(getIdentityPublicKey(), that.getIdentityPublicKey()) &&
                Objects.equals(getConnectionTimestamp(), that.getConnectionTimestamp()) &&
                Objects.equals(getDeviceType(), that.getDeviceType()) &&
                Objects.equals(getStatus(), that.getStatus());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUuid(), getIdentityPublicKey(), getConnectionTimestamp(), getDeviceType(), getLastLatitude(), getLastLongitude(), getStatus());
    }

    @Override
    public String toString() {
        return "ClientsConnectionHistory{" +
                "identityPublicKey='" + identityPublicKey + '\'' +
                '}';
    }
}