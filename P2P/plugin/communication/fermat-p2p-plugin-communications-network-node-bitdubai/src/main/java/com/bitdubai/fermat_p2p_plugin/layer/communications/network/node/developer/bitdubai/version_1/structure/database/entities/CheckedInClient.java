package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;


/**
 * The persistent class for the "CHECKED_IN_CLIENTS" database table.
 * 
 */
public class CheckedInClient extends AbstractBaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private String identityPublicKey;

	private Timestamp checkedInTimestamp;

	private String deviceType;

	private double latitude;

	private double longitude;

	public CheckedInClient() {
		super();
	}

	public Timestamp getCheckedInTimestamp() {
		return checkedInTimestamp;
	}

	public void setCheckedInTimestamp(Timestamp checkedInTimestamp) {
		this.checkedInTimestamp = checkedInTimestamp;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
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

    @Override
    public String getId() {
        return identityPublicKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CheckedInClient)) return false;
        CheckedInClient that = (CheckedInClient) o;
        return Objects.equals(getLatitude(), that.getLatitude()) &&
                Objects.equals(getLongitude(), that.getLongitude()) &&
                Objects.equals(getIdentityPublicKey(), that.getIdentityPublicKey()) &&
                Objects.equals(getCheckedInTimestamp(), that.getCheckedInTimestamp()) &&
                Objects.equals(getDeviceType(), that.getDeviceType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdentityPublicKey(), getCheckedInTimestamp(), getDeviceType(), getLatitude(), getLongitude());
    }

    @Override
    public String toString() {
        return "CheckedInClient{" +
                "identityPublicKey='" + identityPublicKey + '\'' +
                '}';
    }
}