package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


/**
 * The persistent class for the "CHECKED_IN_NETWORK_SERVICES" database table.
 * 
 */
public class CheckedInNetworkService extends AbstractBaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

    private UUID uuid;

	private String identityPublicKey;

	private Timestamp checkedInTimestamp;

	private double latitude;

	private double longitude;

	private String networkServiceType;

	private String clientIdentityPublicKey;

	public CheckedInNetworkService() {
		super();
		this.uuid = UUID.randomUUID();
		this.checkedInTimestamp = new Timestamp(System.currentTimeMillis());
	}

	public Timestamp getCheckedInTimestamp() {
		return checkedInTimestamp;
	}

	public void setCheckedInTimestamp(Timestamp checkedInTimestamp) {
		this.checkedInTimestamp = checkedInTimestamp;
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

	public String getNetworkServiceType() {
		return networkServiceType;
	}

	public void setNetworkServiceType(String networkServiceType) {
		this.networkServiceType = networkServiceType;
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
		if (!(o instanceof CheckedInNetworkService)) return false;
		CheckedInNetworkService that = (CheckedInNetworkService) o;
		return Objects.equals(getLatitude(), that.getLatitude()) &&
				Objects.equals(getLongitude(), that.getLongitude()) &&
				Objects.equals(getUuid(), that.getUuid()) &&
				Objects.equals(getIdentityPublicKey(), that.getIdentityPublicKey()) &&
				Objects.equals(getCheckedInTimestamp(), that.getCheckedInTimestamp()) &&
				Objects.equals(getNetworkServiceType(), that.getNetworkServiceType()) &&
				Objects.equals(getClientIdentityPublicKey(), that.getClientIdentityPublicKey());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getUuid(), getIdentityPublicKey(), getCheckedInTimestamp(), getLatitude(), getLongitude(), getNetworkServiceType(), getClientIdentityPublicKey());
	}

	@Override
    public String toString() {
        return "CheckedInNetworkService{" +
                "identityPublicKey='" + identityPublicKey + '\'' +
                ", networkServiceType='" + networkServiceType + '\'' +
                '}';
    }
}