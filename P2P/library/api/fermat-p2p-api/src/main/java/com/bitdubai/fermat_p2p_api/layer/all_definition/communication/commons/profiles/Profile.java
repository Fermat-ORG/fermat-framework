package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles;

import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.enums.ProfileTypes;

import java.io.Serializable;

/**
 * The Class <code>Profile</code> is
 * the base of the component profile
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 02/12/15.
 * Updated by Leon Acosta - (laion.cj91@gmail.com) on 23/06/2016.
 *
 * @version 1.0
 * @since   Java JDK 1.7
 */
public abstract class Profile implements Serializable {

    /**
     * Represent the Identity public key
     */
    private String identityPublicKey;

    /**
     * Represent the location
     */
    private Location location;

    /**
     * Represent the type of the profile
     */
    private ProfileTypes type;

    /**
     * Constructor
     */
    public Profile(final ProfileTypes type){

        this.type = type;
    }

    /**
     * Gets the value of identityPublicKey and returns
     *
     * @return identityPublicKey
     */
    public String getIdentityPublicKey() {
        return identityPublicKey;
    }

    /**
     * Sets the identityPublicKey
     *
     * @param identityPublicKey to set
     */
    public void setIdentityPublicKey(String identityPublicKey) {
        this.identityPublicKey = identityPublicKey;
    }

    /**
     * Gets the value of location and returns
     *
     * @return location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Sets the location
     *
     * @param location to set
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    public ProfileTypes getType() {
        return type;
    }

    /**
     * Return this object in json string
     *
     * @return json string
     */
    public abstract String toJson();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Profile profile = (Profile) o;

        return !(identityPublicKey != null ? !identityPublicKey.equals(profile.identityPublicKey) : profile.identityPublicKey != null);

    }

    @Override
    public int hashCode() {
        return identityPublicKey != null ? identityPublicKey.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "identityPublicKey='" + identityPublicKey + '\'' +
                ", location=" + location +
                ", type=" + type +
                '}';
    }
}
