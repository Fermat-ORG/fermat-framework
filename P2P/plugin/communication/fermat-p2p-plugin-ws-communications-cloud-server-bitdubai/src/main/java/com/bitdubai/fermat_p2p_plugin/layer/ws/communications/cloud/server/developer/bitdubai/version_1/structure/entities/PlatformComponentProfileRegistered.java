/*
 * @#PlatformComponentProfileRegistered.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.entities;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.components.PlatformComponentProfileCommunication;
import com.google.gson.Gson;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.entities.PlatformComponentProfileRegistered</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 22/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class PlatformComponentProfileRegistered extends PlatformComponentProfileCommunication {

    /**
     * Represent the lastLatitude
     */
    private Double lastLatitude;

    /**
     * Represent the lastLongitude
     */
    private Double lastLongitude;

    /**
     * Represent the lastConnectionTimestamp
     */
    private Timestamp lastConnectionTimestamp;

    /**
     * Constructor
     */
    public PlatformComponentProfileRegistered(){
        super();
    }

    /**
     * Constructor with parameters
     *
     * @param alias
     * @param identityPublicKey
     * @param location
     * @param name
     * @param networkServiceType
     * @param platformComponentType
     * @param extraData
     * @param lastConnectionTimestamp
     */
    public PlatformComponentProfileRegistered(String alias, String identityPublicKey, Location location, String name, NetworkServiceType networkServiceType, PlatformComponentType platformComponentType, String extraData, Timestamp lastConnectionTimestamp) {
        super(alias, null, identityPublicKey, location, name, networkServiceType, platformComponentType, extraData);
        this.lastLatitude  = location != null ? location.getLatitude() : 0;
        this.lastLongitude = location != null ? location.getLongitude(): 0;
        this.lastConnectionTimestamp = new Timestamp(System.currentTimeMillis());
    }

    /**
     * Constructor with parameter
     *
     * @param platformComponentProfileCommunication
     */
    public PlatformComponentProfileRegistered(PlatformComponentProfileCommunication platformComponentProfileCommunication){
        super(platformComponentProfileCommunication.getAlias(), null, platformComponentProfileCommunication.getIdentityPublicKey(), platformComponentProfileCommunication.getLocation(), platformComponentProfileCommunication.getName(), platformComponentProfileCommunication.getNetworkServiceType(), platformComponentProfileCommunication.getPlatformComponentType(), platformComponentProfileCommunication.getExtraData());
        this.lastLatitude  = platformComponentProfileCommunication.getLocation() != null ? platformComponentProfileCommunication.getLocation().getLatitude() : 0;
        this.lastLongitude = platformComponentProfileCommunication.getLocation() != null ? platformComponentProfileCommunication.getLocation().getLongitude(): 0;
        this.lastConnectionTimestamp = new Timestamp(System.currentTimeMillis());
    }

    /**
     * Get the LastLatitude
     * @return Double
     */
    public Double getLastLatitude() {
        return lastLatitude;
    }

    /**
     * Set the LastLatitude
     * @param lastLatitude
     */
    public void setLastLatitude(Double lastLatitude) {
        this.lastLatitude = lastLatitude;
    }

    /**
     * Get the LastLongitude
     * @return Double
     */
    public Double getLastLongitude() {
        return lastLongitude;
    }

    /**
     * Set the LastLongitude
     * @param lastLongitude
     */
    public void setLastLongitude(Double lastLongitude) {
        this.lastLongitude = lastLongitude;
    }

    /**
     * Get the LastConnectionTimestamp
     * @return Timestamp
     */
    public Timestamp getLastConnectionTimestamp() {
        return lastConnectionTimestamp;
    }

    /**
     * setLastConnectionTimestamp
     * @param lastConnectionTimestamp
     */
    public void setLastConnectionTimestamp(Timestamp lastConnectionTimestamp) {
        this.lastConnectionTimestamp = lastConnectionTimestamp;
    }

    /**
     * (non-javadoc)
     * @see PlatformComponentProfileCommunication#toJson()
     */
    @Override
    public String toJson() {

        Gson gson = new Gson();
        return gson.toJson(this);
    }

    /**
     * (non-javadoc)
     * @see PlatformComponentProfileCommunication#fromJson(String)
     */
    @Override
    public PlatformComponentProfile fromJson(String json) {

        Gson gson = new Gson();
        return gson.fromJson(json, PlatformComponentProfileRegistered.class);
    }

    /**
     * (non-javadoc)
     * @see Object#equals(Object)
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlatformComponentProfileRegistered)) return false;
        if (!super.equals(o)) return false;
        PlatformComponentProfileRegistered that = (PlatformComponentProfileRegistered) o;
        return Objects.equals(getLastLatitude(), that.getLastLatitude()) &&
                Objects.equals(getLastLongitude(), that.getLastLongitude()) &&
                Objects.equals(getLastConnectionTimestamp(), that.getLastConnectionTimestamp());
    }

    /**
     * (non-javadoc)
     * @see Object#hashCode()
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getLastLatitude(), getLastLongitude(), getLastConnectionTimestamp());
    }

    /**
     * (non-javadoc)
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return "PlatformComponentProfileRegistered{" +
                "lastConnectionTimestamp=" + lastConnectionTimestamp +
                ", lastLatitude=" + lastLatitude +
                ", lastLongitude=" + lastLongitude +
                '}';
    }
}
