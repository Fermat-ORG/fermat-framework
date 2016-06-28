package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data;

import com.bitdubai.fermat_api.layer.all_definition.location_system.DeviceLocation;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.util.GsonProvider;

import java.io.Serializable;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.DiscoveryQueryParameters</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 27/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class DiscoveryQueryParameters implements Serializable {

    /**
     * Represent the identityPublicKey
     */
    private String identityPublicKey;

    /**
     * Represent the alias
     */
    private String alias;

    /**
     * Represent the name
     */
    private String name;

    /**
     * Represent the location
     */
    private DeviceLocation location;

    /**
     * Represent the distance
     */
    private Double distance;

    /**
     * Represent the networkServiceType
     */
    private NetworkServiceType networkServiceType;

    /**
     * Represent the networkServiceTypeIntermediate
     */
    private NetworkServiceType networkServiceTypeIntermediate;

    /**
     * Represent the extraData
     */
    private String extraData;

    /**
     * Represent the offset
     */
    private Integer offset;

    /**
     * Represent the max
     */
    private Integer max;

    /**
     * Represent the actorType
     */
    private String actorType;

    /**
     * Constructor with parameters
     *
     * @param actorType
     * @param alias
     * @param distance
     * @param extraData
     * @param identityPublicKey
     * @param location
     * @param max
     * @param name
     * @param networkServiceType
     * @param offset
     * @param networkServiceTypeIntermediate
     */
    public DiscoveryQueryParameters(final String             actorType         ,
                                    final String             alias             ,
                                    final Double             distance          ,
                                    final String             extraData         ,
                                    final String             identityPublicKey ,
                                    final DeviceLocation     location          ,
                                    final Integer            max               ,
                                    final String             name              ,
                                    final NetworkServiceType networkServiceType,
                                    final Integer            offset            ,
                                    final NetworkServiceType networkServiceTypeIntermediate) {

        this.actorType          = actorType         ;
        this.alias              = alias             ;
        this.distance           = distance          ;
        this.extraData          = extraData         ;
        this.identityPublicKey  = identityPublicKey ;
        this.location           = location          ;
        this.max                = max               ;
        this.name               = name              ;
        this.networkServiceType = networkServiceType;
        this.offset             = offset            ;
        this.networkServiceTypeIntermediate = networkServiceTypeIntermediate;
    }

    /**
     * Gets the value of actorType and returns
     *
     * @return actorType
     */
    public String getActorType() {
        return actorType;
    }

    /**
     * Gets the value of alias and returns
     *
     * @return alias
     */
    public String getAlias() {
        return alias;
    }

    /**
     * Gets the value of distance and returns
     *
     * @return distance
     */
    public Double getDistance() {
        return distance;
    }

    /**
     * Gets the value of extraData and returns
     *
     * @return extraData
     */
    public String getExtraData() {
        return extraData;
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
     * Gets the value of location and returns
     *
     * @return location
     */
    public DeviceLocation getLocation() {
        return location;
    }

    /**
     * Gets the value of max and returns
     *
     * @return max
     */
    public Integer getMax() {
        return max;
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
     * Gets the value of networkServiceType and returns
     *
     * @return networkServiceType
     */
    public NetworkServiceType getNetworkServiceType() {
        return networkServiceType;
    }

    /**
     * Gets the value of offset and returns
     *
     * @return offset
     */
    public Integer getOffset() {
        return offset;
    }

    /**
     * Gets the value of networkServiceTypeIntermediate
     * @return networkServiceTypeIntermediate
     */
    public NetworkServiceType getNetworkServiceTypeIntermediate() {
        return networkServiceTypeIntermediate;
    }

    /**
     * Generate the json representation
     * @return String
     */
    public String toJson() {
        return GsonProvider.getGson().toJson(this, getClass());
    }

    /**
     * Get the object
     *
     * @param content
     * @return DiscoveryQueryParameters
     */
    public static DiscoveryQueryParameters parseContent(String content) {
        return GsonProvider.getGson().fromJson(content, DiscoveryQueryParameters.class);
    }

    @Override
    public String toString() {
        return "DiscoveryQueryParameters{" +
                "identityPublicKey='" + identityPublicKey + '\'' +
                ", alias='" + alias + '\'' +
                ", name='" + name + '\'' +
                ", location=" + location +
                ", distance=" + distance +
                ", networkServiceType=" + networkServiceType +
                ", extraData='" + extraData + '\'' +
                ", offset=" + offset +
                ", max=" + max +
                ", actorType='" + actorType + '\'' +
                '}';
    }
}
