package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;


/**
 * The persistent class for the "CHECKED_IN_NETWORK_SERVICES" database table.
 * 
 */
public class CheckedInNetworkService extends AbstractBaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private String identityPublicKey;

	private Timestamp checkedInTimestamp;

	private String extraData;

	private double latitude;

	private double longitude;

	private String networkServiceType;

	private List<CheckedInActor> checkedInActors;

	private CheckedInClient checkedInClient;

	public CheckedInNetworkService() {
	}

	public String getIdentityPublicKey() {
		return this.identityPublicKey;
	}

	public void setIdentityPublicKey(String identityPublicKey) {
		this.identityPublicKey = identityPublicKey;
	}

	public Timestamp getCheckedInTimestamp() {
		return this.checkedInTimestamp;
	}

	public void setCheckedInTimestamp(Timestamp checkedInTimestamp) {
		this.checkedInTimestamp = checkedInTimestamp;
	}

	public String getExtraData() {
		return this.extraData;
	}

	public void setExtraData(String extraData) {
		this.extraData = extraData;
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

	public String getNetworkServiceType() {
		return this.networkServiceType;
	}

	public void setNetworkServiceType(String networkServiceType) {
		this.networkServiceType = networkServiceType;
	}

	public List<CheckedInActor> getCheckedInActors() {
		return this.checkedInActors;
	}

	public void setCheckedInActors(List<CheckedInActor> checkedInActors) {
		this.checkedInActors = checkedInActors;
	}

	public CheckedInClient getCheckedInClient() {
		return this.checkedInClient;
	}

	public void setCheckedInClient(CheckedInClient checkedInClient) {
		this.checkedInClient = checkedInClient;
	}

    @Override
    public String getId() {
        return identityPublicKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CheckedInNetworkService)) return false;
        CheckedInNetworkService that = (CheckedInNetworkService) o;
        return Objects.equals(getLatitude(), that.getLatitude()) &&
                Objects.equals(getLongitude(), that.getLongitude()) &&
                Objects.equals(getIdentityPublicKey(), that.getIdentityPublicKey()) &&
                Objects.equals(getCheckedInTimestamp(), that.getCheckedInTimestamp()) &&
                Objects.equals(getExtraData(), that.getExtraData()) &&
                Objects.equals(getNetworkServiceType(), that.getNetworkServiceType()) &&
                Objects.equals(getCheckedInActors(), that.getCheckedInActors()) &&
                Objects.equals(getCheckedInClient(), that.getCheckedInClient());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdentityPublicKey(), getCheckedInTimestamp(), getExtraData(), getLatitude(), getLongitude(), getNetworkServiceType(), getCheckedInActors(), getCheckedInClient());
    }

    @Override
    public String toString() {
        return "CheckedInNetworkService{" +
                "identityPublicKey='" + identityPublicKey + '\'' +
                ", networkServiceType='" + networkServiceType + '\'' +
                '}';
    }
}