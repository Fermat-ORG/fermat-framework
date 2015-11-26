package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities;

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

	private double lastLatitude;

	private Timestamp lastConnectionTimestamp;

	private Integer lateNotificationsCounter;

	private double lastLongitude;

	private String name;

	private Integer offlineCounter;

	private Timestamp registeredTimestamp;

	public NodesCatalog() {
		super();
		this.registeredTimestamp     = new Timestamp(System.currentTimeMillis());
		this.lastConnectionTimestamp = new Timestamp(System.currentTimeMillis());
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

	public double getLastLatitude() {
		return this.lastLatitude;
	}

	public void setLastLatitude(double lastLatitude) {
		this.lastLatitude = lastLatitude;
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

	public double getLastLongitude() {
		return this.lastLongitude;
	}

	public void setLastLongitude(double lastLongitude) {
		this.lastLongitude = lastLongitude;
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

	@Override
	public String getId() {
		return identityPublicKey;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NodesCatalog)) return false;
        NodesCatalog that = (NodesCatalog) o;
        return Objects.equals(getLastLatitude(), that.getLastLatitude()) &&
                Objects.equals(getLastLongitude(), that.getLastLongitude()) &&
                Objects.equals(getIdentityPublicKey(), that.getIdentityPublicKey()) &&
                Objects.equals(getDefaultPort(), that.getDefaultPort()) &&
                Objects.equals(getIp(), that.getIp()) &&
                Objects.equals(getLastConnectionTimestamp(), that.getLastConnectionTimestamp()) &&
                Objects.equals(getLateNotificationsCounter(), that.getLateNotificationsCounter()) &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getOfflineCounter(), that.getOfflineCounter()) &&
                Objects.equals(getRegisteredTimestamp(), that.getRegisteredTimestamp());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdentityPublicKey(), getDefaultPort(), getIp(), getLastLatitude(), getLastConnectionTimestamp(), getLateNotificationsCounter(), getLastLongitude(), getName(), getOfflineCounter(), getRegisteredTimestamp());
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