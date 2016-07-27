package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles;

import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.enums.ProfileTypes;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.util.GsonProvider;
import com.google.gson.JsonObject;

import java.security.InvalidParameterException;

/**
 * The Class <code>NetworkServiceProfile</code>
 * define the profile of the network service component
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 02/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class NetworkServiceProfile extends Profile {

    /**
     * Represent the networkServiceType
     */
    private NetworkServiceType networkServiceType;

    /**
     * Represent the clientIdentityPublicKey
     */
    private String clientIdentityPublicKey;

    /**
     * Constructor
     */
    public NetworkServiceProfile(){
        super(ProfileTypes.NETWORK_SERVICE);
    }

    /**
     * Gets the value of clientIdentityPublicKey and returns
     *
     * @return clientIdentityPublicKey
     */
    public String getClientIdentityPublicKey() {
        return clientIdentityPublicKey;
    }

    /**
     * Sets the clientIdentityPublicKey
     *
     * @param clientIdentityPublicKey to set
     */
    public void setClientIdentityPublicKey(String clientIdentityPublicKey) {
        this.clientIdentityPublicKey = clientIdentityPublicKey;
    }

    /**
     * Gets the value of networkServiceType and returns
     *
     * @return networkServiceType
     */
    public NetworkServiceType getNetworkServiceType() {
        return networkServiceType;
    }

    /**
     * Sets the networkServiceType
     *
     * @param networkServiceType to set
     */
    public void setNetworkServiceType(NetworkServiceType networkServiceType) {
        this.networkServiceType = networkServiceType;
    }

    public static Profile deserialize(final JsonObject jsonObject) {

        NetworkServiceProfile profile = new NetworkServiceProfile();

        Double latitude = 0.0;
        Double longitude = 0.0;

        if (jsonObject.get("ipk") != null)
            profile.setIdentityPublicKey(jsonObject.get("ipk").getAsString());

        if (jsonObject.get("lat") != null)
            latitude = jsonObject.get("lat").getAsDouble();

        if (jsonObject.get("lng") != null)
            longitude = jsonObject.get("lng").getAsDouble();

        if (jsonObject.get("clpk") != null)
            profile.setClientIdentityPublicKey(jsonObject.get("clpk").getAsString());

        try {
            profile.setNetworkServiceType(NetworkServiceType.getByCode(jsonObject.get("nst").getAsString()));
        } catch (Exception exception) {
            throw new InvalidParameterException("Bad NetworkServiceType value: "+(jsonObject.get("nst") == null ? null : jsonObject.get("nst").getAsString()));
        }

        profile.setLocation(latitude, longitude);

        return profile;
    }

    @Override
    public JsonObject serialize() {

        JsonObject jsonObject = super.serialize();

        if (networkServiceType != null)
            jsonObject.addProperty("nst", networkServiceType.getCode());

        if (clientIdentityPublicKey != null)
            jsonObject.addProperty("clpk", clientIdentityPublicKey);

        return jsonObject;
    }

    /**
     * Return this object in json string
     *
     * @return json string
     */
    public String toJson(){
        return GsonProvider.getGson().toJson(this, NetworkServiceProfile.class);
    }

    /**
     * Get the object
     *
     * @param jsonString
     * @return NetworkServiceProfile
     */
    public static NetworkServiceProfile fromJson(String jsonString) {
        return GsonProvider.getGson().fromJson(jsonString, NetworkServiceProfile.class);
    }

    @Override
    public String toString() {
        return "NetworkServiceProfile{" +
                "networkServiceType=" + networkServiceType +
                ", clientIdentityPublicKey='" + clientIdentityPublicKey + '\'' +
                '}';
    }
}
