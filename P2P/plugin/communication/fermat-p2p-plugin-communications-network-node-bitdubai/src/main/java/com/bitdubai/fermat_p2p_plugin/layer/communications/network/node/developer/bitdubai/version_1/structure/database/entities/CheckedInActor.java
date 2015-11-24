package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;


/**
 * The persistent class for the "CHECKED_IN_ACTORS" database table.
 * 
 */
public class CheckedInActor extends AbstractBaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private String identityPublicKey;

	private String actorType;

	private String alias;

	private Timestamp checkedInTimestamp;

	private String extraData;

	private double latitude;

	private double longitude;

	private String name;

	private byte[] photo;

	private String nsIdentityPublicKey;

	public CheckedInActor() {
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

	public Timestamp getCheckedInTimestamp() {
		return checkedInTimestamp;
	}

	public void setCheckedInTimestamp(Timestamp checkedInTimestamp) {
		this.checkedInTimestamp = checkedInTimestamp;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNsIdentityPublicKey() {
		return nsIdentityPublicKey;
	}

	public void setNsIdentityPublicKey(String nsIdentityPublicKey) {
		this.nsIdentityPublicKey = nsIdentityPublicKey;
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
		if (!(o instanceof CheckedInActor)) return false;
		CheckedInActor that = (CheckedInActor) o;
		return Objects.equals(getLatitude(), that.getLatitude()) &&
				Objects.equals(getLongitude(), that.getLongitude()) &&
				Objects.equals(getIdentityPublicKey(), that.getIdentityPublicKey()) &&
				Objects.equals(getActorType(), that.getActorType()) &&
				Objects.equals(getAlias(), that.getAlias()) &&
				Objects.equals(getCheckedInTimestamp(), that.getCheckedInTimestamp()) &&
				Objects.equals(getExtraData(), that.getExtraData()) &&
				Objects.equals(getName(), that.getName()) &&
				Objects.equals(getPhoto(), that.getPhoto()) &&
				Objects.equals(getNsIdentityPublicKey(), that.getNsIdentityPublicKey());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getIdentityPublicKey(), getActorType(), getAlias(), getCheckedInTimestamp(), getExtraData(), getLatitude(), getLongitude(), getName(), getPhoto(), getNsIdentityPublicKey());
	}

	@Override
	public String toString() {
		return "CheckedInActor{" +
				"identityPublicKey='" + identityPublicKey + '\'' +
				", alias='" + alias + '\'' +
				", name='" + name + '\'' +
				", actorType='" + actorType + '\'' +
				'}';
	}
}