package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;


/**
 * The persistent class for the "CHECKED_ACTORS_HISTORY" database table.
 * 
 */
public class CheckedActorsHistory extends AbstractBaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

	private String identityPublicKey;

	private String actorType;

	private String alias;

	private String checkType;

	private Timestamp checkedTimestamp;

	private String clientIdentityPublicKey;

	private String extraData;

	private double lastLatitude;

	private double lastLongitude;

	private String name;

	private byte[] photo;

	public CheckedActorsHistory() {
		super();
	}

	public String getActorType() {
		return actorType;
	}

	public void setActorType(String actorType) {
		this.actorType = actorType;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

    @Override
    public String getId() {
        return identityPublicKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CheckedActorsHistory)) return false;
        CheckedActorsHistory that = (CheckedActorsHistory) o;
        return Objects.equals(getLastLatitude(), that.getLastLatitude()) &&
                Objects.equals(getLastLongitude(), that.getLastLongitude()) &&
                Objects.equals(getIdentityPublicKey(), that.getIdentityPublicKey()) &&
                Objects.equals(getActorType(), that.getActorType()) &&
                Objects.equals(getAlias(), that.getAlias()) &&
                Objects.equals(getCheckType(), that.getCheckType()) &&
                Objects.equals(getCheckedTimestamp(), that.getCheckedTimestamp()) &&
                Objects.equals(getClientIdentityPublicKey(), that.getClientIdentityPublicKey()) &&
                Objects.equals(getExtraData(), that.getExtraData()) &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getPhoto(), that.getPhoto());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdentityPublicKey(), getActorType(), getAlias(), getCheckType(), getCheckedTimestamp(), getClientIdentityPublicKey(), getExtraData(), getLastLatitude(), getLastLongitude(), getName(), getPhoto());
    }

    @Override
    public String toString() {
        return "CheckedActorsHistory{" +
                "identityPublicKey='" + identityPublicKey + '\'' +
                ", alias='" + alias + '\'' +
                ", name='" + name + '\'' +
                ", actorType='" + actorType + '\'' +
                '}';
    }
}