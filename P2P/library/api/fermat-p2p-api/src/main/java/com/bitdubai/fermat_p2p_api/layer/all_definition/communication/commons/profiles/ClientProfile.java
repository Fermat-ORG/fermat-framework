package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles;

import com.bitdubai.fermat_api.layer.all_definition.location_system.NetworkNodeCommunicationDeviceLocation;
import com.bitdubai.fermat_api.layer.osa_android.location_system.LocationSource;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.enums.ProfileTypes;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.util.GsonProvider;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.sql.Timestamp;

/**
 * The Class <code>ClientProfile</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 02/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class ClientProfile extends Profile {

    /**
     * Represent the deviceType
     */
    private String deviceType;

    /**
     * Constructor
     */
    public ClientProfile(){
        super(ProfileTypes.CLIENT);
    }

    /**
     * Gets the value of deviceType and returns
     *
     * @return deviceType
     */
    public String getDeviceType() {
        return deviceType;
    }

    /**
     * Sets the deviceType
     *
     * @param deviceType to set
     */
    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public static Profile readJson(final JsonReader in) throws IOException {

        ClientProfile clientProfile = new ClientProfile();

        Double latitude = 0.0;
        Double longitude = 0.0;

        while (in.hasNext()) {
            switch (in.nextName()) {
                case "ipk":
                    clientProfile.setIdentityPublicKey(in.nextString());
                    break;
                case "lat":
                    latitude = in.nextDouble();
                    break;
                case "lng":
                    longitude = in.nextDouble();
                    break;
                case "det":
                    clientProfile.setDeviceType(in.nextString());
                    break;
            }
        }

        clientProfile.setLocation(
                new NetworkNodeCommunicationDeviceLocation(
                        latitude ,
                        longitude,
                        null     ,
                        0        ,
                        null     ,
                        0        ,
                        LocationSource.UNKNOWN
                )
        );

        return clientProfile;
    }

    @Override
    public JsonWriter writeJson(final JsonWriter out) throws IOException {

        super.writeJson(out);

        out.name("det").value(deviceType);

        return out;
    }

    /**
     * Return this object in json string
     *
     * @return json string
     */
    public String toJson(){
        return GsonProvider.getGson().toJson(this, ClientProfile.class);
    }

    /**
     * Get the object
     *
     * @param jsonString
     * @return ClientProfile
     */
    public static ClientProfile fromJson(String jsonString) {
        return GsonProvider.getGson().fromJson(jsonString, ClientProfile.class);
    }

    @Override
    public String toString() {
        return "ClientProfile{" +
                "deviceType='" + deviceType + '\'' +
                '}';
    }
}
