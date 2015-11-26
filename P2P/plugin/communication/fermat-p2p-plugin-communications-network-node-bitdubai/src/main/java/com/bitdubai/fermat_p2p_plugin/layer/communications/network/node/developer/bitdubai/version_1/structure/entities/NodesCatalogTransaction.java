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

	private Timestamp createTimestamp;

	private Integer defaultPort;

	private String identityPublicKey;

	private String ip;

	private double latitude;

	private double longitude;

	private String name;

	private Timestamp registeredTimestamp;

	private String transactionType;

	public NodesCatalogTransaction() {
        super();
        this.registeredTimestamp = new Timestamp(System.currentTimeMillis());
	}

    public Timestamp getCreateTimestamp() {
        return createTimestamp;
    }

    public void setCreateTimestamp(Timestamp createTimestamp) {
        this.createTimestamp = createTimestamp;
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
        return Objects.equals(getLatitude(), that.getLatitude()) &&
                Objects.equals(getLongitude(), that.getLongitude()) &&
                Objects.equals(getHashId(), that.getHashId()) &&
                Objects.equals(getCreateTimestamp(), that.getCreateTimestamp()) &&
                Objects.equals(getDefaultPort(), that.getDefaultPort()) &&
                Objects.equals(getIdentityPublicKey(), that.getIdentityPublicKey()) &&
                Objects.equals(getIp(), that.getIp()) &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getTransactionType(), that.getTransactionType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getHashId(), getDefaultPort(), getIdentityPublicKey(), getIp(), getLatitude(), getLongitude(), getName(), getTransactionType());
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