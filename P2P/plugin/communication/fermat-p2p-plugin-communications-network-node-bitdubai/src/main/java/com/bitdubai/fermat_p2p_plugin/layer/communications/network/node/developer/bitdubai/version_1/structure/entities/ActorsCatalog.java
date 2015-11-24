package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;


/**
 * The persistent class for the "ACTORS_CATALOG" database table.
 * 
 */
public class ActorsCatalog extends AbstractBaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private String identityPublicKey;

	private String actorType;

	private String alias;

	private String extraData;

	private Timestamp hostedTimestamp;

	private double lastLatitude;

	private double lastLongitude;

	private String name;

	private String nodeIdentityPublicKey;

	private byte[] photo;

	public ActorsCatalog() {
		super();
		this.hostedTimestamp = new Timestamp(System.currentTimeMillis());
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

	public String getExtraData() {
		return extraData;
	}

	public void setExtraData(String extraData) {
		this.extraData = extraData;
	}

	public Timestamp getHostedTimestamp() {
		return hostedTimestamp;
	}

	public void setHostedTimestamp(Timestamp hostedTimestamp) {
		this.hostedTimestamp = hostedTimestamp;
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

	public String getNodeIdentityPublicKey() {
		return nodeIdentityPublicKey;
	}

	public void setNodeIdentityPublicKey(String nodeIdentityPublicKey) {
		this.nodeIdentityPublicKey = nodeIdentityPublicKey;
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
        if (!(o instanceof ActorsCatalog)) return false;
        ActorsCatalog that = (ActorsCatalog) o;
        return Objects.equals(getLastLatitude(), that.getLastLatitude()) &&
                Objects.equals(getLastLongitude(), that.getLastLongitude()) &&
                Objects.equals(getIdentityPublicKey(), that.getIdentityPublicKey()) &&
                Objects.equals(getActorType(), that.getActorType()) &&
                Objects.equals(getAlias(), that.getAlias()) &&
                Objects.equals(getExtraData(), that.getExtraData()) &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getNodeIdentityPublicKey(), that.getNodeIdentityPublicKey()) &&
                Objects.equals(getPhoto(), that.getPhoto());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdentityPublicKey(), getActorType(), getAlias(), getExtraData(), getLastLatitude(), getLastLongitude(), getName(), getNodeIdentityPublicKey(), getPhoto());
    }

    @Override
    public String toString() {
        return "ActorsCatalog{" +
                "identityPublicKey='" + identityPublicKey + '\'' +
                ", alias='" + alias + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}