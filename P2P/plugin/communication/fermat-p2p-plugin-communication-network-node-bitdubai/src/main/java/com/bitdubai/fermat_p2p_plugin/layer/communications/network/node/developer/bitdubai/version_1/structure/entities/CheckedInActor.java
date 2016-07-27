package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Arrays;
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

	private Double latitude;

	private Double longitude;

	private String name;

	private byte[] photo;

	private String nsIdentityPublicKey;

	private String clientIdentityPublicKey;

	public CheckedInActor() {
        this.checkedInTimestamp = new Timestamp(System.currentTimeMillis());
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

	public String getClientIdentityPublicKey() {
		return clientIdentityPublicKey;
	}

	public void setClientIdentityPublicKey(String clientIdentityPublicKey) {
		this.clientIdentityPublicKey = clientIdentityPublicKey;
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
        return Objects.equals(getIdentityPublicKey(), that.getIdentityPublicKey());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdentityPublicKey());
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