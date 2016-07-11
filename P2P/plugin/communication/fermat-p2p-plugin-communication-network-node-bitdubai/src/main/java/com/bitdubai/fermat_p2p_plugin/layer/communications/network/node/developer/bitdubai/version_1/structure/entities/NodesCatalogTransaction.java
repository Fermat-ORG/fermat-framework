package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities;

import com.bitdubai.fermat_api.layer.all_definition.location_system.NetworkNodeCommunicationDeviceLocation;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_api.layer.osa_android.location_system.LocationSource;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;


/**
 * The persistent class for the "NODES_CATALOG_TRANSACTIONS" database table.
 * 
 */
public class NodesCatalogTransaction extends AbstractBaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

    public static final String ADD_TRANSACTION_TYPE = "ADD";

    public static final String DELETE_TRANSACTION_TYPE = "DELETE";

    public static final String UPDATE_TRANSACTION_TYPE = "UPDATE";

	private String hashId;

	private Timestamp lastConnectionTimestamp;

	private Integer defaultPort;

	private String identityPublicKey;

	private String ip;

    private Location lastLocation;

	private String name;

	private Timestamp registeredTimestamp;

	private String transactionType;

	public NodesCatalogTransaction() {
        super();
        this.hashId = UUID.randomUUID().toString();
        this.lastConnectionTimestamp = new Timestamp(System.currentTimeMillis());
        this.registeredTimestamp = new Timestamp(System.currentTimeMillis());
        this.lastLocation = new NetworkNodeCommunicationDeviceLocation();
	}

    public Timestamp getLastConnectionTimestamp() {
        return lastConnectionTimestamp;
    }

    public void setLastConnectionTimestamp(Timestamp lastConnectionTimestamp) {
        this.lastConnectionTimestamp = lastConnectionTimestamp;
    }

    public Integer getDefaultPort() {
        return defaultPort;
    }

    public void setDefaultPort(Integer defaultPort) {
        this.defaultPort = defaultPort;
    }

    public String getHashId() {
        return hashId;
    }

    public void setHashId(String hashId) {
        this.hashId = hashId;
    }

    public String getIdentityPublicKey() {
        return identityPublicKey;
    }

    public void setIdentityPublicKey(String identityPublicKey) {
        this.identityPublicKey = identityPublicKey;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getRegisteredTimestamp() {
        return registeredTimestamp;
    }

    public void setRegisteredTimestamp(Timestamp registeredTimestamp) {
        this.registeredTimestamp = registeredTimestamp;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
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
    public String getId() {
        return hashId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NodesCatalogTransaction)) return false;

        NodesCatalogTransaction that = (NodesCatalogTransaction) o;

        if (getHashId() != null ? !getHashId().equals(that.getHashId()) : that.getHashId() != null)
            return false;
        if (getLastConnectionTimestamp() != null ? !getLastConnectionTimestamp().equals(that.getLastConnectionTimestamp()) : that.getLastConnectionTimestamp() != null)
            return false;
        if (getDefaultPort() != null ? !getDefaultPort().equals(that.getDefaultPort()) : that.getDefaultPort() != null)
            return false;
        if (getIdentityPublicKey() != null ? !getIdentityPublicKey().equals(that.getIdentityPublicKey()) : that.getIdentityPublicKey() != null)
            return false;
        if (getIp() != null ? !getIp().equals(that.getIp()) : that.getIp() != null) return false;
        if (getLastLocation() != null ? !getLastLocation().equals(that.getLastLocation()) : that.getLastLocation() != null)
            return false;
        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null)
            return false;
        if (getRegisteredTimestamp() != null ? !getRegisteredTimestamp().equals(that.getRegisteredTimestamp()) : that.getRegisteredTimestamp() != null)
            return false;
        return !(getTransactionType() != null ? !getTransactionType().equals(that.getTransactionType()) : that.getTransactionType() != null);

    }

    @Override
    public int hashCode() {
        int result = getHashId() != null ? getHashId().hashCode() : 0;
        result = 31 * result + (getLastConnectionTimestamp() != null ? getLastConnectionTimestamp().hashCode() : 0);
        result = 31 * result + (getDefaultPort() != null ? getDefaultPort().hashCode() : 0);
        result = 31 * result + (getIdentityPublicKey() != null ? getIdentityPublicKey().hashCode() : 0);
        result = 31 * result + (getIp() != null ? getIp().hashCode() : 0);
        result = 31 * result + (getLastLocation() != null ? getLastLocation().hashCode() : 0);
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getRegisteredTimestamp() != null ? getRegisteredTimestamp().hashCode() : 0);
        result = 31 * result + (getTransactionType() != null ? getTransactionType().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "NodesCatalog{" +
                "identityPublicKey='" + identityPublicKey + '\'' +
                ", name='" + name + '\'' +
                ", ip='" + ip + '\'' +
                ", defaultPort=" + defaultPort +
                '}';
    }
}