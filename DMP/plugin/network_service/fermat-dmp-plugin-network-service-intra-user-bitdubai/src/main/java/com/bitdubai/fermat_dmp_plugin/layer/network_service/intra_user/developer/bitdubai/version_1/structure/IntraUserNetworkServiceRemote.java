/*
 * @#IntraUserNetworkServiceRemote.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.dmp_network_service.intra_user.IntraUser;
import com.bitdubai.fermat_api.layer.dmp_network_service.intra_user.IntraUserStatus;

import java.security.PublicKey;
import java.util.Objects;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.structure.IntraUserNetworkServiceRemote</code>
 * is the abstraction of a representation of Intra User Network Service Remote of the platform
 * <p/>
 *
 * Created by ciencias on 2/13/15.
 * Update by Roberto Requena - (rart3001@gmail.com) on 31/05/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class IntraUserNetworkServiceRemote implements IntraUser {

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
     * Represent the createdTime
     */
    private Long createdTime;

    /**
     * Represent the updateTime
     */
    private Long updateTime;

    /**
     * Represent the publicKey
     */
    private PublicKey publicKey;



    /**
     * Constructor
     */
    public IntraUserNetworkServiceRemote(){
        super();
    }

    /**
     * Constructor with parameter
     * @param jsomObject
     */
    public IntraUserNetworkServiceRemote(String jsomObject){
        super();
        //deserialize the object from parse the jsomObject to configure a new instance
        //Utilizar mejor una libreria como Gsom para esta tarea
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
    protected IntraUserNetworkServiceRemote(String address, String lastLocation, String profilePicture, IntraUserStatus status, UUID userId, String userName,PublicKey publicKey) {
        super();
        this.address        = address;
        this.lastLocation   = lastLocation;
        this.profilePicture = profilePicture;
        this.status         = status;
        this.userId         = userId;
        this.userName       = userName;
        this.publicKey      = publicKey;
        this.createdTime    = System.currentTimeMillis();
        this.updateTime     = System.currentTimeMillis();
    }

    /**
     * Return the address
     *
     * @return String
     */
    @Override
    public String getAddress() {
        return address;
    }


    /**
     * Return the createdTime
     *
     * @return String
     */
    @Override
    public Long getCreatedTime() {
        return createdTime;
    }


    /**
     * Return the lastLocation
     *
     * @return String
     */
    @Override
    public String getLastLocation() {
        return lastLocation;
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
     * Return the SmallProfilePicture
     *
     * @return String
     */
    @Override
    public String getSmallProfilePicture (){
        return "small_" + getProfilePicture();
    }

    /**
     * Return the MediumProfilePicture
     *
     * @return String
     */
    @Override
    public String getMediumProfilePicture (){
        return "medium_" + getProfilePicture();
    }

    /**
     * Return the BigProfilePicture
     *
     * @return String
     */
    @Override
    public String getBigProfilePicture (){
        return "big_" + getProfilePicture();
    }

    /**
     * Return the status
     *
     * @return IntraUserStatus
     */
    @Override
    public IntraUserStatus getStatus() {
        return status;
    }


    /**
     * Return the updateTime
     *
     * @return String
     */
    @Override
    public Long getUpdateTime() {
        return updateTime;
    }

    /**
     * Set the updateTime
     *
     * @param updateTime
     */
    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * Return the userId
     *
     * @return UUID
     */
    @Override
    public UUID getUserId() {
        return userId;
    }


    /**
     * Return the userName
     *
     * @return String
     */
    @Override
    public String getUserName() {
        return userName;
    }


    /**
     * Return the publicKey
     *
     * @return PublicKey
     */
    public PublicKey getPublicKey() {
        return publicKey;
    }

    /**
     * (non-Javadoc)
     * @see Object#equals(Object)
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IntraUserNetworkServiceRemote)) return false;
        IntraUserNetworkServiceRemote that = (IntraUserNetworkServiceRemote) o;
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
        return "IntraUserNetworkServiceRemote{" + "userId=" + userId + '}';
    }

    /**
     * Return this object in jsom format
     *
     * @return String
     */
    public String serializeToJsomObject() {


        //Utilizar mejor una libreria como Gsom para esta tarea

        return "IntraUserNetworkServiceRemote{" +
                "address='" + address + '\'' +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", lastLocation='" + lastLocation + '\'' +
                ", status=" + status +
                ", profilePicture='" + profilePicture + '\'' +
                ", createdTime=" + createdTime +
                ", updateTime=" + updateTime +
                ", publicKey=" + publicKey +
                '}';
    }
}
