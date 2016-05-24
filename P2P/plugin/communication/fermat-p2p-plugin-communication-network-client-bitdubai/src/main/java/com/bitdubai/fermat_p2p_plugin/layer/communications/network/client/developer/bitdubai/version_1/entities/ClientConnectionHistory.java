package com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.entities;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * The Class <code>ClientConnectionHistory</code>
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 13/11/15.
 * Updated by Leon Acosta - (laion.cj91@gmail.com) on 07/04/2016.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class ClientConnectionHistory extends AbstractBaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private String    identityPublicKey      ;
    private String    name                   ;
    private String    alias                  ;
    private String    componentType          ;
    private String    networkServiceType     ;
    private Double    lastLatitude           ;
    private Double    lastLongitude          ;
    private String    extraData              ;
    private Timestamp lastConnectionTimestamp;

    public ClientConnectionHistory(final String    identityPublicKey      ,
                                   final String    name                   ,
                                   final String    alias                  ,
                                   final String    componentType          ,
                                   final String    networkServiceType     ,
                                   final Double    lastLatitude           ,
                                   final Double    lastLongitude          ,
                                   final String    extraData              ,
                                   final Timestamp lastConnectionTimestamp){

        this.identityPublicKey       = identityPublicKey      ;
        this.name                    = name                   ;
        this.alias                   = alias                  ;
        this.componentType           = componentType          ;
        this.networkServiceType      = networkServiceType     ;
        this.lastLatitude            = lastLatitude           ;
        this.lastLongitude           = lastLongitude          ;
        this.extraData               = extraData              ;
        this.lastConnectionTimestamp = lastConnectionTimestamp;
    }

    public String getIdentityPublicKey() {
        return identityPublicKey;
    }

    public String getName() {
        return name;
    }

    public String getAlias() {
        return alias;
    }

    public String getComponentType() {
        return componentType;
    }

    public String getNetworkServiceType() {
        return networkServiceType;
    }

    public Double getLastLatitude() {
        return lastLatitude;
    }

    public Double getLastLongitude() {
        return lastLongitude;
    }

    public String getExtraData() {
        return extraData;
    }

    public Timestamp getLastConnectionTimestamp() {
        return lastConnectionTimestamp;
    }

    @Override
    public String getId() {
        return identityPublicKey;
    }

    @Override
    public String toString() {
        return "ClientConnectionHistory{" +
                "identityPublicKey='" + identityPublicKey + '\'' +
                ", name='" + name + '\'' +
                ", alias='" + alias + '\'' +
                ", componentType='" + componentType + '\'' +
                ", networkServiceType='" + networkServiceType + '\'' +
                ", lastLatitude=" + lastLatitude +
                ", lastLongitude=" + lastLongitude +
                ", extraData='" + extraData + '\'' +
                ", lastConnectionTimestamp=" + lastConnectionTimestamp +
                '}';
    }
}
