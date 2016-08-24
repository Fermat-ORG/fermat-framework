package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.enums.ProfileTypes;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.util.GsonProvider;
import com.google.gson.JsonObject;

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

    public static Profile deserialize(final JsonObject jsonObject) {

        ClientProfile clientProfile = new ClientProfile();

        Double latitude = 0.0;
        Double longitude = 0.0;

        if (jsonObject.get("ipk") != null)
            clientProfile.setIdentityPublicKey(jsonObject.get("ipk").getAsString());

        if (jsonObject.get("lat") != null)
            latitude = jsonObject.get("lat").getAsDouble();

        if (jsonObject.get("lng") != null)
            longitude = jsonObject.get("lng").getAsDouble();

        if (jsonObject.get("det") != null)
            clientProfile.setDeviceType(jsonObject.get("det").getAsString());

        clientProfile.setLocation(latitude, longitude);

        return clientProfile;
    }

    @Override
    public JsonObject serialize() {

        JsonObject jsonObject = super.serialize();

        jsonObject.addProperty("det", deviceType);

        return jsonObject;
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
