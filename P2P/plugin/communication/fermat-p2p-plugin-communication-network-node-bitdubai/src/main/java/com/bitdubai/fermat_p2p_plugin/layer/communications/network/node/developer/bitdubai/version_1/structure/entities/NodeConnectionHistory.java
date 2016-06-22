package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities;

import com.bitdubai.fermat_api.layer.all_definition.location_system.NetworkNodeCommunicationDeviceLocation;
import com.bitdubai.fermat_api.layer.osa_android.location_system.LocationSource;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;


/**
 * The persistent class for the "NODE_CONNECTION_HISTORY" database table.
 * 
 */
public class NodeConnectionHistory extends AbstractBaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private UUID uuid;

	private String identityPublicKey;

	private Timestamp connectionTimestamp;

	private Integer defaultPort;

	private String ip;

    private NetworkNodeCommunicationDeviceLocation lastLocation;

	private String status;

	public NodeConnectionHistory() {
        super();
        this.uuid = UUID.randomUUID();
        this.connectionTimestamp = new Timestamp(System.currentTimeMillis());
        this.lastLocation = new NetworkNodeCommunicationDeviceLocation();
	}

    public Timestamp getConnectionTimestamp() {
        return connectionTimestamp;
    }

    public void setConnectionTimestamp(Timestamp connectionTimestamp) {
        this.connectionTimestamp = connectionTimestamp;
    }

    public Integer getDefaultPort() {
        return defaultPort;
    }

    public void setDefaultPort(Integer defaultPort) {
        this.defaultPort = defaultPort;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
        if (!(o instanceof NodeConnectionHistory)) return false;

        NodeConnectionHistory that = (NodeConnectionHistory) o;

        if (getUuid() != null ? !getUuid().equals(that.getUuid()) : that.getUuid() != null)
            return false;
        if (getIdentityPublicKey() != null ? !getIdentityPublicKey().equals(that.getIdentityPublicKey()) : that.getIdentityPublicKey() != null)
            return false;
        if (getConnectionTimestamp() != null ? !getConnectionTimestamp().equals(that.getConnectionTimestamp()) : that.getConnectionTimestamp() != null)
            return false;
        if (getDefaultPort() != null ? !getDefaultPort().equals(that.getDefaultPort()) : that.getDefaultPort() != null)
            return false;
        if (getIp() != null ? !getIp().equals(that.getIp()) : that.getIp() != null) return false;
        if (getLastLocation() != null ? !getLastLocation().equals(that.getLastLocation()) : that.getLastLocation() != null)
            return false;
        return !(getStatus() != null ? !getStatus().equals(that.getStatus()) : that.getStatus() != null);

    }

    @Override
    public int hashCode() {
        int result = getUuid() != null ? getUuid().hashCode() : 0;
        result = 31 * result + (getIdentityPublicKey() != null ? getIdentityPublicKey().hashCode() : 0);
        result = 31 * result + (getConnectionTimestamp() != null ? getConnectionTimestamp().hashCode() : 0);
        result = 31 * result + (getDefaultPort() != null ? getDefaultPort().hashCode() : 0);
        result = 31 * result + (getIp() != null ? getIp().hashCode() : 0);
        result = 31 * result + (getLastLocation() != null ? getLastLocation().hashCode() : 0);
        result = 31 * result + (getStatus() != null ? getStatus().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "NodeConnectionHistory{" +
                "identityPublicKey='" + identityPublicKey + '\'' +
                '}';
    }
}