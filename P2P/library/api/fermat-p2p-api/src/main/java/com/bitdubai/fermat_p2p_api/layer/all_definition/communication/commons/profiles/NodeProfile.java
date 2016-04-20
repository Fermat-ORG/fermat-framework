package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles;

import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.util.InterfaceAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
        super();
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

    /**
     * (no-javadoc)
     * @see Profile#toJson()
     */
    @Override
    public String toJson() {
        return getGsonInstance().toJson(this);
    }

    /**
     * (no-javadoc)
     * @see Profile#fromJson(String)
     */
    public NodeProfile fromJson(String jsonString) {
        return getGsonInstance().fromJson(jsonString, this.getClass());
    }

    private static Gson getGsonInstance() {

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Profile.class, new InterfaceAdapter<Profile>());
        builder.registerTypeAdapter(Location.class, new InterfaceAdapter<Location>());
        return builder.create();
    }

    @Override
    public String toString() {
        return "NodeProfile{" +
                "defaultPort=" + defaultPort +
                ", ip='" + ip + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
