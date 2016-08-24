package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.enums.ProfileTypes;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.util.GsonProvider;
import com.google.gson.JsonObject;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.NodeProfile</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 26/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class NodeProfile extends Profile {

    /**
     * Represent the defaultPort
     */
    private Integer defaultPort;

    /**
     * Represent the ip
     */
    private String ip;

    /**
     * Represent the name
     */
    private String name;

    /**
     * Constructor
     */
    public NodeProfile(){
        super(ProfileTypes.NODE);
    }

    /**
     * Gets the value of defaultPort and returns
     *
     * @return defaultPort
     */
    public Integer getDefaultPort() {
        return defaultPort;
    }

    /**
     * Sets the defaultPort
     *
     * @param defaultPort to set
     */
    public void setDefaultPort(Integer defaultPort) {
        this.defaultPort = defaultPort;
    }

    /**
     * Gets the value of ip and returns
     *
     * @return ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * Sets the ip
     *
     * @param ip to set
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * Gets the value of name and returns
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name
     *
     * @param name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    public static Profile deserialize(final JsonObject jsonObject) {

        NodeProfile profile = new NodeProfile();

        profile.setIdentityPublicKey(jsonObject.get("ipk").getAsString());
        Double latitude = jsonObject.get("lat").getAsDouble();
        Double longitude = jsonObject.get("lng").getAsDouble();

        profile.setDefaultPort(jsonObject.get("dp").getAsInt());
        profile.setIp(jsonObject.get("ip").getAsString());
        profile.setName(jsonObject.get("na").getAsString());

        profile.setLocation(latitude, longitude);

        return profile;
    }

    @Override
    public JsonObject serialize() {

        JsonObject jsonObject = super.serialize();

        jsonObject.addProperty("dp", defaultPort);
        jsonObject.addProperty("ip", ip);
        jsonObject.addProperty("na", name);

        return jsonObject;
    }

    /**
     * Return this object in json string
     *
     * @return json string
     */
    public String toJson(){
        return GsonProvider.getGson().toJson(this, NodeProfile.class);
    }

    /**
     * Get the object
     *
     * @param jsonString
     * @return NodeProfile
     */
    public static NodeProfile fromJson(String jsonString) {
        return GsonProvider.getGson().fromJson(jsonString, NodeProfile.class);
    }

    /**
     * (non-javadoc)
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return "NodeProfile{" +
                "defaultPort=" + defaultPort +
                ", ip='" + ip + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    /**
     * (non-javadoc)
     * @see Object#equals(Object)
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NodeProfile)) return false;

        NodeProfile that = (NodeProfile) o;

        if (!getIdentityPublicKey().equals(that.getIdentityPublicKey())) return false;
        if ((!getLocation().getLatitude().equals(that.getLocation().getLatitude())) || !getLocation().getLongitude().equals(that.getLocation().getLongitude())) return false;
        if (!getDefaultPort().equals(that.getDefaultPort())) return false;
        if (!getIp().equals(that.getIp())) return false;
        return getName().equals(that.getName());

    }

    /**
     * (non-javadoc)
     * @see Object#hashCode()
     */
    @Override
    public int hashCode() {
        int result = getDefaultPort().hashCode();
        result = 31 * result + getIp().hashCode();
        result = 31 * result + getName().hashCode();
        return result;
    }
}
