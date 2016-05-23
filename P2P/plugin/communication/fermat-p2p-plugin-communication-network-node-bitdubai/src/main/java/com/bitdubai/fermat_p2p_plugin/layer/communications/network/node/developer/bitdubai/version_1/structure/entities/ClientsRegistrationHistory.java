package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities;

import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.enums.RegistrationResult;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.enums.RegistrationType;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

/**
 * The persistent class for the "ClientsRegistrationHistory" database table.
 *
 * Updated by Leon Acosta (laion.cj91@gmail.com) on 15/04/2016.
 *
 * @author
 * @version 1.0
 * @since Java JDK 1.7
 */
public class ClientsRegistrationHistory extends AbstractBaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

    private UUID               uuid             ;
    private String             identityPublicKey;
    private Double             lastLatitude     ;
    private Double             lastLongitude    ;
    private String             deviceType       ;
    private Timestamp          checkedTimestamp ;
    private RegistrationType   type             ;
    private RegistrationResult result           ;
    private String             detail           ;

	public ClientsRegistrationHistory() {
		super();
        this.uuid = UUID.randomUUID();
        this.checkedTimestamp = new Timestamp(System.currentTimeMillis());
	}

    public ClientsRegistrationHistory(final UUID               uuid             ,
                                      final String             identityPublicKey,
                                      final Double             lastLatitude     ,
                                      final Double             lastLongitude    ,
                                      final String             deviceType       ,
                                      final Timestamp          checkedTimestamp ,
                                      final RegistrationType   type             ,
                                      final RegistrationResult result           ,
                                      final String             detail           ) {

        this.uuid              = uuid             ;
        this.checkedTimestamp  = checkedTimestamp ;
        this.deviceType        = deviceType       ;
        this.lastLatitude      = lastLatitude     ;
        this.lastLongitude     = lastLongitude    ;
        this.identityPublicKey = identityPublicKey;
        this.type              = type             ;
        this.result            = result           ;
        this.detail            = detail           ;
    }

    public Timestamp getCheckedTimestamp() {
		return checkedTimestamp;
	}

	public void setCheckedTimestamp(Timestamp checkedTimestamp) {
		this.checkedTimestamp = checkedTimestamp;
	}

    public RegistrationType getType() {
        return type;
    }

    public void setType(RegistrationType type) {
        this.type = type;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getIdentityPublicKey() {
        return identityPublicKey;
    }

    public void setIdentityPublicKey(String identityPublicKey) {
        this.identityPublicKey = identityPublicKey;
    }

    public Double getLastLatitude() {
        return lastLatitude;
    }

    public void setLastLatitude(Double lastLatitude) {
        this.lastLatitude = lastLatitude;
    }

    public Double getLastLongitude() {
        return lastLongitude;
    }

    public void setLastLongitude(Double lastLongitude) {
        this.lastLongitude = lastLongitude;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public RegistrationResult getResult() {
        return result;
    }

    public void setResult(RegistrationResult result) {
        this.result = result;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Override
    public String getId() {
        return uuid.toString();
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (!(o instanceof ClientsRegistrationHistory)) return false;
        ClientsRegistrationHistory that = (ClientsRegistrationHistory) o;
        return Objects.equals(getLastLatitude(), that.getLastLatitude()) &&
                Objects.equals(getLastLongitude(), that.getLastLongitude()) &&
                Objects.equals(getUuid(), that.getUuid()) &&
                Objects.equals(getIdentityPublicKey(), that.getIdentityPublicKey()) &&
                Objects.equals(getType(), that.getType()) &&
                Objects.equals(getCheckedTimestamp(), that.getCheckedTimestamp()) &&
                Objects.equals(getDeviceType(), that.getDeviceType()
        );
    }

    @Override
    public int hashCode() {

        return Objects.hash(
                getUuid()             ,
                getIdentityPublicKey(),
                getType()             ,
                getResult(),
                getDetail()           ,
                getCheckedTimestamp() ,
                getDeviceType()       ,
                getLastLatitude()     ,
                getLastLongitude()
        );
    }

    @Override
    public String toString() {
        return "ClientsRegistrationHistory{" +
                "uuid=" + uuid +
                ", identityPublicKey='" + identityPublicKey + '\'' +
                ", type=" + type +
                ", result=" + result +
                ", detail='" + detail + '\'' +
                ", checkedTimestamp=" + checkedTimestamp +
                ", deviceType='" + deviceType + '\'' +
                ", lastLatitude=" + lastLatitude +
                ", lastLongitude=" + lastLongitude +
                '}';
    }
}