package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;


/**
 * The persistent class for the "CHECKED_CLIENTS_HISTORY" database table.
 * 
 */
public class CheckedClientsHistory extends AbstractBaseEntity  implements Serializable {

	private static final long serialVersionUID = 1L;

	private UUID uuid;

	private String identityPublicKey;

	private String checkType;

	private Timestamp checkedTimestamp;

	private String deviceType;

	private double lastLatitude;

	private double lastLongitude;

	public CheckedClientsHistory() {
		super();
        this.uuid = UUID.randomUUID();
        this.checkedTimestamp = new Timestamp(System.currentTimeMillis());
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
        if (!(o instanceof CheckedClientsHistory)) return false;
        CheckedClientsHistory that = (CheckedClientsHistory) o;
        return Objects.equals(getLastLatitude(), that.getLastLatitude()) &&
                Objects.equals(getLastLongitude(), that.getLastLongitude()) &&
                Objects.equals(getUuid(), that.getUuid()) &&
                Objects.equals(getIdentityPublicKey(), that.getIdentityPublicKey()) &&
                Objects.equals(getCheckType(), that.getCheckType()) &&
                Objects.equals(getCheckedTimestamp(), that.getCheckedTimestamp()) &&
                Objects.equals(getDeviceType(), that.getDeviceType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUuid(), getIdentityPublicKey(), getCheckType(), getCheckedTimestamp(), getDeviceType(), getLastLatitude(), getLastLongitude());
    }

    @Override
    public String toString() {
        return "CheckedClientsHistory{" +
                "checkedTimestamp=" + checkedTimestamp +
                ", uuid=" + uuid +
                ", identityPublicKey='" + identityPublicKey + '\'' +
                ", checkType='" + checkType + '\'' +
                ", deviceType='" + deviceType + '\'' +
                ", lastLatitude=" + lastLatitude +
                ", lastLongitude=" + lastLongitude +
                '}';
    }
}