package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles;

import com.bitdubai.fermat_api.layer.all_definition.location_system.NetworkNodeCommunicationDeviceLocation;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_api.layer.osa_android.location_system.LocationSource;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.enums.ProfileStatus;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.enums.ProfileTypes;
import com.google.gson.JsonObject;

import org.apache.commons.lang.NotImplementedException;

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
     * Represent the status of the profile
     */
    private ProfileStatus status;

    /**
     * Constructor
     */
    public Profile(final ProfileTypes type){

        this.type   = type;
        this.status = ProfileStatus.UNKNOWN;
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

    public ProfileStatus getStatus() {
        return status;
    }

    public void setStatus(ProfileStatus status) {
        this.status = status;
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

    public void setLocation(final Double latitude ,
                            final Double longitude) {

        this.location = new NetworkNodeCommunicationDeviceLocation(
                latitude,
                longitude,
                null,
                0,
                null,
                0,
                LocationSource.UNKNOWN
        );
    }

    public ProfileTypes getType() {
        return type;
    }

    public static Profile deserialize(final JsonObject jsonObject) {

        throw new NotImplementedException();
    }

    public JsonObject serialize() {

        JsonObject jsonObject = new JsonObject();

        if (type != null)
            jsonObject.addProperty("typ", type.getCode());

        if (identityPublicKey != null)
        jsonObject.addProperty("ipk", identityPublicKey);

        if (location != null) {
            jsonObject.addProperty("lat", location.getLatitude());
            jsonObject.addProperty("lng", location.getLongitude());
        }

        return jsonObject;
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
                ", status=" + status +
                '}';
    }
}
