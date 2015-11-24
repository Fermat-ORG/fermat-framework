package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;


/**
 * The persistent class for the "METHOD_CALLS_HISTORY" database table.
 * 
 */
public class MethodCallsHistory extends AbstractBaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private UUID uuid;

	private Timestamp createTimestamp;

	private String methodName;

	private String parameters;

	private String profileIdentityPublicKey;

	public MethodCallsHistory() {
		super();
	}

    public Timestamp getCreateTimestamp() {
        return createTimestamp;
    }

    public void setCreateTimestamp(Timestamp createTimestamp) {
        this.createTimestamp = createTimestamp;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public String getProfileIdentityPublicKey() {
        return profileIdentityPublicKey;
    }

    public void setProfileIdentityPublicKey(String profileIdentityPublicKey) {
        this.profileIdentityPublicKey = profileIdentityPublicKey;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MethodCallsHistory)) return false;
        MethodCallsHistory that = (MethodCallsHistory) o;
        return Objects.equals(getUuid(), that.getUuid()) &&
                Objects.equals(getCreateTimestamp(), that.getCreateTimestamp()) &&
                Objects.equals(getMethodName(), that.getMethodName()) &&
                Objects.equals(getParameters(), that.getParameters()) &&
                Objects.equals(getProfileIdentityPublicKey(), that.getProfileIdentityPublicKey());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUuid(), getCreateTimestamp(), getMethodName(), getParameters(), getProfileIdentityPublicKey());
    }

    @Override
    public String toString() {
        return "MethodCallsHistory{" +
                "uuid=" + uuid +
                '}';
    }
}