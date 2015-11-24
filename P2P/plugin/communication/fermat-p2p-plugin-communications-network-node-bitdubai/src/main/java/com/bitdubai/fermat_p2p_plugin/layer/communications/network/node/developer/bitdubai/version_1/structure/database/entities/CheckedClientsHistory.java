package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;


/**
 * The persistent class for the "CHECKED_CLIENTS_HISTORY" database table.
 * 
 */
public class CheckedClientsHistory extends AbstractBaseEntity  implements Serializable {

	private static final long serialVersionUID = 1L;

	private String identityPublicKey;

	private String checkType;

	private Timestamp checkedTimestamp;

	private String deviceType;

	private double latitude;

	private double longitude;

	public CheckedClientsHistory() {
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
		if (!(o instanceof CheckedClientsHistory)) return false;
		CheckedClientsHistory that = (CheckedClientsHistory) o;
		return Objects.equals(getLatitude(), that.getLatitude()) &&
				Objects.equals(getLongitude(), that.getLongitude()) &&
				Objects.equals(getIdentityPublicKey(), that.getIdentityPublicKey()) &&
				Objects.equals(getCheckType(), that.getCheckType()) &&
				Objects.equals(getCheckedTimestamp(), that.getCheckedTimestamp()) &&
				Objects.equals(getDeviceType(), that.getDeviceType());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getIdentityPublicKey(), getCheckType(), getCheckedTimestamp(), getDeviceType(), getLatitude(), getLongitude());
	}

	@Override
	public String toString() {
		return "CheckedClientsHistory{" +
				"identityPublicKey='" + identityPublicKey + '\'' +
				'}';
	}
}