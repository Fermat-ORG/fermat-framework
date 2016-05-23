package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities;

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

	private Double latitude;

	private Double longitude;

	public CheckedInClient() {
		super();
        this.checkedInTimestamp = new Timestamp(System.currentTimeMillis());
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

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
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
				", checkedInTimestamp=" + checkedInTimestamp +
				", deviceType='" + deviceType + '\'' +
				", latitude=" + latitude +
				", longitude=" + longitude +
				'}';
	}
}