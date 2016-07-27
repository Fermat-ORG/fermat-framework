package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities;

import com.bitdubai.fermat_api.layer.all_definition.location_system.NetworkNodeCommunicationDeviceLocation;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_api.layer.osa_android.location_system.LocationSource;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * The persistent class for the "NODES_CATALOG" database table.
 * 
 */
public class NodesCatalog extends AbstractBaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private String identityPublicKey;

	private Integer defaultPort;

	private String ip;

    private Location lastLocation;

	private Timestamp lastConnectionTimestamp;

	private Integer lateNotificationsCounter;

	private String name;

	private Integer offlineCounter;

	private Timestamp registeredTimestamp;

	public NodesCatalog() {
		super();
		this.registeredTimestamp      = new Timestamp(System.currentTimeMillis());
		this.lastConnectionTimestamp  = new Timestamp(System.currentTimeMillis());
		this.offlineCounter           = null;
		this.lateNotificationsCounter = null;
	}

	public String getIdentityPublicKey() {
		return this.identityPublicKey;
	}

	public void setIdentityPublicKey(String identityPublicKey) {
		this.identityPublicKey = identityPublicKey;
	}

	public Integer getDefaultPort() {
		return this.defaultPort;
	}

	public void setDefaultPort(Integer defaultPort) {
		this.defaultPort = defaultPort;
	}

	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Timestamp getLastConnectionTimestamp() {
		return this.lastConnectionTimestamp;
	}

	public void setLastConnectionTimestamp(Timestamp lastConnectionTimestamp) {
		this.lastConnectionTimestamp = lastConnectionTimestamp;
	}

	public Integer getLateNotificationsCounter() {
		return this.lateNotificationsCounter;
	}

	public void setLateNotificationsCounter(Integer lateNotificationsCounter) {
		this.lateNotificationsCounter = lateNotificationsCounter;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getOfflineCounter() {
		return this.offlineCounter;
	}

	public void setOfflineCounter(Integer offlineCounter) {
		this.offlineCounter = offlineCounter;
	}

	public Timestamp getRegisteredTimestamp() {
		return this.registeredTimestamp;
	}

	public void setRegisteredTimestamp(Timestamp registeredTimestamp) {
		this.registeredTimestamp = registeredTimestamp;
	}

    public Location getLastLocation() {

        return lastLocation;
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


    public void setLastLocation(Location lastLocation) {
        this.lastLocation = lastLocation;
    }

    @Override
	public String getId() {
		return identityPublicKey;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NodesCatalog)) return false;
        NodesCatalog that = (NodesCatalog) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdentityPublicKey());
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