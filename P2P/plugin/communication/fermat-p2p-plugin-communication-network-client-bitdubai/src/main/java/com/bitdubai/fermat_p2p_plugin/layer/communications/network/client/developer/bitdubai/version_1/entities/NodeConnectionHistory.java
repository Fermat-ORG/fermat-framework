package com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.entities;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * The Class <code>NodeConnectionHistory</code>
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 13/11/15.
 * Updated by Leon Acosta - (laion.cj91@gmail.com) on 07/04/2016.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class NodeConnectionHistory extends AbstractBaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private String    identityPublicKey      ;
    private String    ip                     ;
    private Integer   defaultPort            ;
    private Double    latitude               ;
    private Double    longitude              ;
    private Timestamp lastConnectionTimestamp;

    public NodeConnectionHistory(final String    identityPublicKey      ,
                                 final String    ip                     ,
                                 final Integer   defaultPort            ,
                                 final Double    latitude               ,
                                 final Double    longitude              ,
                                 final Timestamp lastConnectionTimestamp) {

        this.identityPublicKey       = identityPublicKey      ;
        this.ip                      = ip                     ;
        this.defaultPort             = defaultPort            ;
        this.latitude                = latitude               ;
        this.longitude               = longitude              ;
        this.lastConnectionTimestamp = lastConnectionTimestamp;
    }

    public String getIdentityPublicKey() {
        return identityPublicKey;
    }

    public String getIp() {
        return ip;
    }

    public Integer getDefaultPort() {
        return defaultPort;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
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
        return "NodeConnectionHistory{" +
                "identityPublicKey='" + identityPublicKey + '\'' +
                ", ip='" + ip + '\'' +
                ", defaultPort=" + defaultPort +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", lastConnectionTimestamp=" + lastConnectionTimestamp +
                '}';
    }
}
