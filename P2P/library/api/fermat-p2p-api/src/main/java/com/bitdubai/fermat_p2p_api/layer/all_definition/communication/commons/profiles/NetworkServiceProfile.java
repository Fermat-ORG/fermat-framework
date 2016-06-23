package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.location_system.NetworkNodeCommunicationDeviceLocation;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.osa_android.location_system.LocationSource;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.enums.ProfileTypes;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.util.GsonProvider;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

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

    public static Profile readJson(final JsonReader in) throws IOException {

        NetworkServiceProfile nsProfile = new NetworkServiceProfile();

        Double latitude = 0.0;
        Double longitude = 0.0;

        while (in.hasNext()) {
            switch (in.nextName()) {
                case "ipk":
                    nsProfile.setIdentityPublicKey(in.nextString());
                    break;
                case "lat":
                    latitude = in.nextDouble();
                    break;
                case "lng":
                    longitude = in.nextDouble();
                    break;
                case "nst":
                    try {
                        nsProfile.setNetworkServiceType(NetworkServiceType.getByCode(in.nextString()));
                    } catch (InvalidParameterException invalidParameterException) {
                        throw new IOException("Malformed network service type");
                    }
                    break;
                case "clpk":
                    nsProfile.setClientIdentityPublicKey(in.nextString());
                    break;
            }
        }

        nsProfile.setLocation(
                new NetworkNodeCommunicationDeviceLocation(
                        latitude,
                        longitude,
                        null,
                        0,
                        null,
                        0,
                        LocationSource.UNKNOWN
                )
        );

        return nsProfile;
    }

    @Override
    public JsonWriter writeJson(final JsonWriter out) throws IOException {

        super.writeJson(out);

        out.name("nst").value(networkServiceType.getCode());
        out.name("clpk").value(clientIdentityPublicKey);

        return out;
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
