package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;


/**
 * The persistent class for the "CHECKED_NETWORK_SERVICES_HISTORY" database table.
 * 
 */
public class CheckedNetworkServicesHistory extends AbstractBaseEntity  implements Serializable {

	private static final long serialVersionUID = 1L;

	private UUID uuid;

	private String identityPublicKey;

	private String checkType;

	private Timestamp checkedTimestamp;

	private String clientIdentityPublicKey;

	private double lastLatitude;

	private double lastLongitude;

	private String networkServiceType;

	public CheckedNetworkServicesHistory() {
		super();
        this.uuid = UUID.randomUUID();
        this.checkedTimestamp = new Timestamp(System.currentTimeMillis());
	}

	public Timestamp getCheckedTimestamp() {
		return checkedTimestamp;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public void setCheckedTimestamp(Timestamp checkedTimestamp) {
		this.checkedTimestamp = checkedTimestamp;
	}

	public String getCheckType() {
		return checkType;
	}

	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}

	public String getClientIdentityPublicKey() {
		return clientIdentityPublicKey;
	}

	public void setClientIdentityPublicKey(String clientIdentityPublicKey) {
		this.clientIdentityPublicKey = clientIdentityPublicKey;
	}

	public String getIdentityPublicKey() {
		return identityPublicKey;
	}

	public void setIdentityPublicKey(String identityPublicKey) {
		this.identityPublicKey = identityPublicKey;
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

	public String getNetworkServiceType() {
		return networkServiceType;
	}

	public void setNetworkServiceType(String networkServiceType) {
		this.networkServiceType = networkServiceType;
	}

    @Override
    public String getId() {
        return uuid.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CheckedNetworkServicesHistory)) return false;
        CheckedNetworkServicesHistory that = (CheckedNetworkServicesHistory) o;
        return Objects.equals(getLastLatitude(), that.getLastLatitude()) &&
                Objects.equals(getLastLongitude(), that.getLastLongitude()) &&
                Objects.equals(getUuid(), that.getUuid()) &&
                Objects.equals(getIdentityPublicKey(), that.getIdentityPublicKey()) &&
                Objects.equals(getCheckType(), that.getCheckType()) &&
                Objects.equals(getCheckedTimestamp(), that.getCheckedTimestamp()) &&
                Objects.equals(getClientIdentityPublicKey(), that.getClientIdentityPublicKey()) &&
                Objects.equals(getNetworkServiceType(), that.getNetworkServiceType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUuid(), getIdentityPublicKey(), getCheckType(), getCheckedTimestamp(), getClientIdentityPublicKey(), getLastLatitude(), getLastLongitude(), getNetworkServiceType());
    }

    @Override
    public String toString() {
        return "CheckedInNetworkService{" +
                "identityPublicKey='" + identityPublicKey + '\'' +
                ", networkServiceType='" + networkServiceType + '\'' +
                '}';
    }
}