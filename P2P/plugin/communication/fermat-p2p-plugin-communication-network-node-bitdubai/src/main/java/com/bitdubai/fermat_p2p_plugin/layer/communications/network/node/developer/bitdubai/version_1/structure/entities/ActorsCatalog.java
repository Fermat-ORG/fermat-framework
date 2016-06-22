package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities;

import com.bitdubai.fermat_api.layer.all_definition.location_system.NetworkNodeCommunicationDeviceLocation;
import com.bitdubai.fermat_api.layer.osa_android.location_system.LocationSource;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Arrays;


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

	private NetworkNodeCommunicationDeviceLocation lastLocation;

	private String name;

	private String nodeIdentityPublicKey;

	private String clientIdentityPublicKey;

	private byte[] photo;

	public ActorsCatalog() {
		super();
		this.hostedTimestamp = new Timestamp(System.currentTimeMillis());
        this.lastLocation = new NetworkNodeCommunicationDeviceLocation();
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

	public NetworkNodeCommunicationDeviceLocation getLastLocation() {
		return lastLocation;
	}

	public void setLastLocation(NetworkNodeCommunicationDeviceLocation lastLocation) {
		this.lastLocation = lastLocation;
	}

	public void setLastLocation(double latitude, double longitude){
		lastLocation = new NetworkNodeCommunicationDeviceLocation(
				latitude,
				longitude,
				null     ,
				0        ,
				null     ,
				System.currentTimeMillis(),
				LocationSource.UNKNOWN);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof ActorsCatalog)) return false;

		ActorsCatalog that = (ActorsCatalog) o;

		if (getIdentityPublicKey() != null ? !getIdentityPublicKey().equals(that.getIdentityPublicKey()) : that.getIdentityPublicKey() != null)
			return false;
		if (getActorType() != null ? !getActorType().equals(that.getActorType()) : that.getActorType() != null)
			return false;
		if (getAlias() != null ? !getAlias().equals(that.getAlias()) : that.getAlias() != null)
			return false;
		if (getExtraData() != null ? !getExtraData().equals(that.getExtraData()) : that.getExtraData() != null)
			return false;
		if (getHostedTimestamp() != null ? !getHostedTimestamp().equals(that.getHostedTimestamp()) : that.getHostedTimestamp() != null)
			return false;
		if (getLastLocation() != null ? !getLastLocation().equals(that.getLastLocation()) : that.getLastLocation() != null)
			return false;
		if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null)
			return false;
		if (getNodeIdentityPublicKey() != null ? !getNodeIdentityPublicKey().equals(that.getNodeIdentityPublicKey()) : that.getNodeIdentityPublicKey() != null)
			return false;
		if (getClientIdentityPublicKey() != null ? !getClientIdentityPublicKey().equals(that.getClientIdentityPublicKey()) : that.getClientIdentityPublicKey() != null)
			return false;
		return Arrays.equals(getPhoto(), that.getPhoto());

	}

	@Override
	public int hashCode() {
		int result = getIdentityPublicKey() != null ? getIdentityPublicKey().hashCode() : 0;
		result = 31 * result + (getActorType() != null ? getActorType().hashCode() : 0);
		result = 31 * result + (getAlias() != null ? getAlias().hashCode() : 0);
		result = 31 * result + (getExtraData() != null ? getExtraData().hashCode() : 0);
		result = 31 * result + (getHostedTimestamp() != null ? getHostedTimestamp().hashCode() : 0);
		result = 31 * result + (getLastLocation() != null ? getLastLocation().hashCode() : 0);
		result = 31 * result + (getName() != null ? getName().hashCode() : 0);
		result = 31 * result + (getNodeIdentityPublicKey() != null ? getNodeIdentityPublicKey().hashCode() : 0);
		result = 31 * result + (getClientIdentityPublicKey() != null ? getClientIdentityPublicKey().hashCode() : 0);
		result = 31 * result + (getPhoto() != null ? Arrays.hashCode(getPhoto()) : 0);
		return result;
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