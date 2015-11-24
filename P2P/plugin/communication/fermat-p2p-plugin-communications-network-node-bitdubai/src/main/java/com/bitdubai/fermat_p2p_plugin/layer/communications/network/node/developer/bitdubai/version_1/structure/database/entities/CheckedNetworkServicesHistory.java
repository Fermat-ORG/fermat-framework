package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;


/**
 * The persistent class for the "CHECKED_NETWORK_SERVICES_HISTORY" database table.
 * 
 */
public class CheckedNetworkServicesHistory extends AbstractBaseEntity  implements Serializable {

	private static final long serialVersionUID = 1L;

	private String identityPublicKey;

	private String checkType;

	private Timestamp checkedTimestamp;

	private String clientIdentityPublicKey;

	private String extraData;

	private double latitude;

	private double longitude;

	private String networkServiceType;

	public CheckedNetworkServicesHistory() {
		super();
	}

	public Timestamp getCheckedTimestamp() {
		return checkedTimestamp;
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

	public String getExtraData() {
		return extraData;
	}

	public void setExtraData(String extraData) {
		this.extraData = extraData;
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

    @Override
    public String getId() {
        return identityPublicKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CheckedNetworkServicesHistory)) return false;
        CheckedNetworkServicesHistory that = (CheckedNetworkServicesHistory) o;
        return Objects.equals(getLatitude(), that.getLatitude()) &&
                Objects.equals(getLongitude(), that.getLongitude()) &&
                Objects.equals(getIdentityPublicKey(), that.getIdentityPublicKey()) &&
                Objects.equals(getCheckType(), that.getCheckType()) &&
                Objects.equals(getCheckedTimestamp(), that.getCheckedTimestamp()) &&
                Objects.equals(getClientIdentityPublicKey(), that.getClientIdentityPublicKey()) &&
                Objects.equals(getExtraData(), that.getExtraData()) &&
                Objects.equals(getNetworkServiceType(), that.getNetworkServiceType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdentityPublicKey(), getCheckType(), getCheckedTimestamp(), getClientIdentityPublicKey(), getExtraData(), getLatitude(), getLongitude(), getNetworkServiceType());
    }

    @Override
    public String toString() {
        return "CheckedInNetworkService{" +
                "identityPublicKey='" + identityPublicKey + '\'' +
                ", networkServiceType='" + networkServiceType + '\'' +
                '}';
    }
}