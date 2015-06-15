/*
 * @#IntraUserNetworkServiceLocal.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer._11_network_service.intra_user.IntraUserStatus;

import java.security.KeyPair;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.structure.IntraUserNetworkServiceLocal</code>
 * is the abstraction of a representation of Intra User Network Service Local of the platform
 * <p/>
 *
 * Created by ciencias on 2/13/15.
 * Update by Roberto Requena - (rart3001@gmail.com) on 31/05/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class IntraUserNetworkServiceLocal{

    /**
     * Represent the address
     */
    private String address;

    /**
     * Represent the userId
     */
    private UUID userId;

    /**
     * Represent the userName
     */
    private String userName;

    /**
     * Represent the lastLocation
     */
    private String lastLocation;

    /**
     * Represent the status
     */
    private IntraUserStatus status;

    /**
     * Represent the profilePicture
     */
    private String profilePicture;

    /**
     * Represent the keyPair
     */
    private KeyPair keyPair;

    /**
     * Hold all IntraUserNetworkServiceRemote instance references with have a communication channel open
     */
    private Map<UUID, IntraUserNetworkServiceRemote> intraUserNetworkServiceRemoteInstanceReferences;


    /**
     * Constructor
     */
    public IntraUserNetworkServiceLocal(){
        super();
    }

    /**
     * Constructor with parameters
     *
     * @param lastLocation
     * @param profilePicture
     * @param status
     * @param userId
     * @param userName
     */
    public IntraUserNetworkServiceLocal(String lastLocation, String profilePicture, IntraUserStatus status, UUID userId, String userName) {
        super();
        this.lastLocation = lastLocation;
        this.profilePicture = profilePicture;
        this.status = status;
        this.userId = userId;
        this.userName = userName;
        this.intraUserNetworkServiceRemoteInstanceReferences = new HashMap<>();
    }

    /**
     * Return the address
     *
     * @return String
     */
    public String getAddress() {
        return address;
    }

    /**
     * Set the address
     *
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Return the lastLocation
     *
     * @return String
     */
    public String getLastLocation() {
        return lastLocation;
    }

    /**
     * Set the lastLocation
     *
     * @param lastLocation
     */
    public void setLastLocation(String lastLocation) {
        this.lastLocation = lastLocation;
    }

    /**
     * Return the profilePicture
     *
     * @return String
     */
    public String getProfilePicture() {
        return profilePicture;
    }

    /**
     * Set the profilePicture
     *
     * @param profilePicture
     */
    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    /**
     * Return the status
     *
     * @return IntraUserStatus
     */
    public IntraUserStatus getStatus() {
        return status;
    }

    /**
     * Set the status
     *
     * @param status
     */
    public void setStatus(IntraUserStatus status) {
        this.status = status;
    }

    /**
     * Return the userId
     *
     * @return UUID
     */
    public UUID getUserId() {
        return userId;
    }

    /**
     * Set the userId
     *
     * @param userId
     */
    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    /**
     * Return the userName
     *
     * @return String
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Set the userName
     *
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Return the keyPair
     *
     * @return keyPair
     */
    public KeyPair getKeyPair() {
        return keyPair;
    }

    /**
     * Set the keyPair
     *
     * @param keyPair
     */
    public void setKeyPair(KeyPair keyPair) {
        this.keyPair = keyPair;
    }

    /**
     * (non-Javadoc)
     * @see Object#equals(Object)
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IntraUserNetworkServiceLocal)) return false;
        IntraUserNetworkServiceLocal that = (IntraUserNetworkServiceLocal) o;
        return Objects.equals(getUserId(), that.getUserId());
    }

    /**
     * (non-Javadoc)
     * @see Object#hashCode()
     */
    @Override
    public int hashCode() {
        return Objects.hash(getUserId());
    }

    /**
     * (non-Javadoc)
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return "IntraUserNetworkServiceLocal{" + "userId=" + userId + '}';
    }


    /**
     * Return the remote representation of this local
     *
     * @return IntraUserNetworkServiceRemote
     */
    public IntraUserNetworkServiceRemote getRemoteObject(){

        return new IntraUserNetworkServiceRemote(address,
                                                 lastLocation,
                                                 profilePicture,
                                                 status,
                                                 userId,
                                                 userName,
                                                 keyPair.getPublic());
    }

    /**
     * Put a new remote reference
     *
     * @param intraUserNetworkServiceRemote
     */
    public void addIntraUserNetworkServiceRemoteInstance(UUID remoteNetworkService, IntraUserNetworkServiceRemote intraUserNetworkServiceRemote){

        //Put the new reference
        intraUserNetworkServiceRemoteInstanceReferences.put(remoteNetworkService, intraUserNetworkServiceRemote);

    }

    /**
     * Get a remote reference
     *
     * @param remoteNetworkService
     * @return IntraUserNetworkServiceRemote
     */
    public IntraUserNetworkServiceRemote getIntraUserNetworkServiceRemoteInstance(UUID remoteNetworkService){

        //get the reference
       return intraUserNetworkServiceRemoteInstanceReferences.get(remoteNetworkService);

    }


    /**
     * Remove a remote reference
     *
     * @param remoteNetworkService
     */
    public void removeIntraUserNetworkServiceRemoteInstance(UUID remoteNetworkService){

        //remove the reference
        intraUserNetworkServiceRemoteInstanceReferences.remove(remoteNetworkService);

    }

}
