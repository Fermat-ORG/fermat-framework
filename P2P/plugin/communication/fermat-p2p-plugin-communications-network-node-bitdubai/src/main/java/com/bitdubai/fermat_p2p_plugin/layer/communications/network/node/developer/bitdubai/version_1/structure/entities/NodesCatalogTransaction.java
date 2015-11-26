package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;


/**
 * The persistent class for the "NODES_CATALOG_TRANSACTIONS" database table.
 * 
 */
public class NodesCatalogTransaction extends AbstractBaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private String hashId;

	private Timestamp lastConnectionTimestamp;

	private Integer defaultPort;

	private String identityPublicKey;

	private String ip;

	private double lastLatitude;

	private double lastLongitude;

	private String name;

	private Timestamp registeredTimestamp;

	private String transactionType;

	public NodesCatalogTransaction() {
        super();
        this.registeredTimestamp = new Timestamp(System.currentTimeMillis());
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

    @Override
    public String getId() {
        return hashId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NodesCatalogTransaction)) return false;
        NodesCatalogTransaction that = (NodesCatalogTransaction) o;
        return Objects.equals(getLastLatitude(), that.getLastLatitude()) &&
                Objects.equals(getLastLongitude(), that.getLastLongitude()) &&
                Objects.equals(getHashId(), that.getHashId()) &&
                Objects.equals(getLastConnectionTimestamp(), that.getLastConnectionTimestamp()) &&
                Objects.equals(getDefaultPort(), that.getDefaultPort()) &&
                Objects.equals(getIdentityPublicKey(), that.getIdentityPublicKey()) &&
                Objects.equals(getIp(), that.getIp()) &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getTransactionType(), that.getTransactionType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getHashId(), getDefaultPort(), getIdentityPublicKey(), getIp(), getLastLatitude(), getLastLongitude(), getName(), getTransactionType());
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