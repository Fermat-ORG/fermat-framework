package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities;

import com.bitdubai.fermat_api.layer.all_definition.location_system.NetworkNodeCommunicationDeviceLocation;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_api.layer.osa_android.location_system.LocationSource;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.UUID;


/**
 * The persistent class for the "ACTORS_CATALOG_TRANSACTIONS" database table.
 * 
 */
public class ActorsCatalogTransaction extends AbstractBaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String ADD_TRANSACTION_TYPE = "ADD";

	public static final String DELETE_TRANSACTION_TYPE = "DEL";

	public static final String UPDATE_TRANSACTION_TYPE = "UPD";

	public static final String UPDATE_GEOLOCATION_TRANSACTION_TYPE = "GEO";

	public static final String UPDATE_LAST_CONNECTION_TRANSACTION_TYPE = "LST";

	private String hashId;

	private String actorType;

	private String alias;

	private String extraData;

	private Timestamp hostedTimestamp;

	private String identityPublicKey;

	private Location lastLocation;

	private String name;

	private String nodeIdentityPublicKey;

	private String clientIdentityPublicKey;

	private byte[] photo;

	private byte[] thumbnail;

	private String transactionType;

	private Timestamp generationTime;

	private Timestamp lastConnection;

	public ActorsCatalogTransaction() {
		super();

		this.hashId         = UUID.randomUUID().toString();
		this.generationTime = new Timestamp(System.currentTimeMillis());
	}

	public void setGenerationTime(Timestamp generationTime) {
		this.generationTime = generationTime;
	}

	public Timestamp getGenerationTime() {
		return generationTime;
	}

	public Timestamp getLastConnection() {
		return lastConnection;
	}

	public void setLastConnection(Timestamp lastConnection) {
		this.lastConnection = lastConnection;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
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

	public String getHashId() {
		return hashId;
	}

	public void setHashId(String hashId) {
		this.hashId = hashId;
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

	public byte[] getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(byte[] thumbnail) {
		this.thumbnail = thumbnail;
	}

	@Override
	public String getId() {
		return hashId;
	}

	public Location getLastLocation() {
		return lastLocation;
	}

	public void setLastLocation(Location lastLocation) {
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
		if (!(o instanceof ActorsCatalogTransaction)) return false;

		ActorsCatalogTransaction that = (ActorsCatalogTransaction) o;

		if (getHashId() != null ? !getHashId().equals(that.getHashId()) : that.getHashId() != null)
			return false;
		if (getActorType() != null ? !getActorType().equals(that.getActorType()) : that.getActorType() != null)
			return false;
		if (getAlias() != null ? !getAlias().equals(that.getAlias()) : that.getAlias() != null)
			return false;
		if (getExtraData() != null ? !getExtraData().equals(that.getExtraData()) : that.getExtraData() != null)
			return false;
		if (getHostedTimestamp() != null ? !getHostedTimestamp().equals(that.getHostedTimestamp()) : that.getHostedTimestamp() != null)
			return false;
		if (getLastConnection() != null ? !getLastConnection().equals(that.getLastConnection()) : that.getLastConnection() != null)
			return false;
		if (getIdentityPublicKey() != null ? !getIdentityPublicKey().equals(that.getIdentityPublicKey()) : that.getIdentityPublicKey() != null)
			return false;
		if (getLastLocation() != null ? !getLastLocation().equals(that.getLastLocation()) : that.getLastLocation() != null)
			return false;
		if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null)
			return false;
		if (getNodeIdentityPublicKey() != null ? !getNodeIdentityPublicKey().equals(that.getNodeIdentityPublicKey()) : that.getNodeIdentityPublicKey() != null)
			return false;
		if (getClientIdentityPublicKey() != null ? !getClientIdentityPublicKey().equals(that.getClientIdentityPublicKey()) : that.getClientIdentityPublicKey() != null)
			return false;
		if (!Arrays.equals(getPhoto(), that.getPhoto())) return false;
		if (!Arrays.equals(getThumbnail(), that.getThumbnail())) return false;

		return !(getTransactionType() != null ? !getTransactionType().equals(that.getTransactionType()) : that.getTransactionType() != null);

	}

	@Override
	public int hashCode() {
		int result = getHashId() != null ? getHashId().hashCode() : 0;
		result = 31 * result + (getActorType() != null ? getActorType().hashCode() : 0);
		result = 31 * result + (getAlias() != null ? getAlias().hashCode() : 0);
		result = 31 * result + (getExtraData() != null ? getExtraData().hashCode() : 0);
		result = 31 * result + (getHostedTimestamp() != null ? getHostedTimestamp().hashCode() : 0);
		result = 31 * result + (getLastConnection() != null ? getLastConnection().hashCode() : 0);
		result = 31 * result + (getIdentityPublicKey() != null ? getIdentityPublicKey().hashCode() : 0);
		result = 31 * result + (getLastLocation() != null ? getLastLocation().hashCode() : 0);
		result = 31 * result + (getName() != null ? getName().hashCode() : 0);
		result = 31 * result + (getNodeIdentityPublicKey() != null ? getNodeIdentityPublicKey().hashCode() : 0);
		result = 31 * result + (getClientIdentityPublicKey() != null ? getClientIdentityPublicKey().hashCode() : 0);
		result = 31 * result + (getPhoto() != null ? Arrays.hashCode(getPhoto()) : 0);
		result = 31 * result + (getThumbnail() != null ? Arrays.hashCode(getThumbnail()) : 0);
		result = 31 * result + (getTransactionType() != null ? getTransactionType().hashCode() : 0);
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