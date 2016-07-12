package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data;

import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
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
    private Location location;

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
     * Represent the lastConnectionTime
     */
    private Long lastConnectionTime;

    /**
     * Represent the isOnline
     */
    private Boolean isOnline;

    /**
     * Represent the originalPhoto
     */
    private Boolean originalPhoto;

    /**
     * Constructor with params
     *
     * @param identityPublicKey    represents the identity public key of the component to discover.
     * @param networkServiceType   if we're looking for network services we'll set this value with the type of network service.
     * @param actorType            if we're looking for actors we'll set this value with the type of the actor.
     * @param name                 we can set here the name of the component to search or discover.
     * @param alias                we can set here the alias of the component to search or discover.
     * @param extraData            we can set here the extraData of the actor component to search or discover.
     * @param location             this param indicates a point for doing the discovery near it.
     * @param distance             this param indicates the distance to the point to look around.
     * @param isOnline             with this param we ask to the node the status of the profiles to discover.
     * @param lastConnectionTime   with this param we'll ask to the node only the profiles connected after the long timestamp.
     * @param max                  this param will be used with the pagination stuff.
     * @param offset               this param will be used with the pagination stuff.
     * @param originalPhoto        this param will be used to get the photo original if it is true else it will be the Photo thumbnail.
     */
    public DiscoveryQueryParameters(final String             identityPublicKey ,
                                    final NetworkServiceType networkServiceType,
                                    final String             actorType         ,
                                    final String             name              ,
                                    final String             alias             ,
                                    final String             extraData         ,
                                    final Location           location          ,
                                    final Double             distance          ,
                                    final Boolean            isOnline          ,
                                    final Long               lastConnectionTime,
                                    final Integer            max               ,
                                    final Integer            offset            ,
                                    final Boolean            originalPhoto     ) {

        this.identityPublicKey  = identityPublicKey ;
        this.networkServiceType = networkServiceType;
        this.actorType          = actorType         ;
        this.name               = name              ;
        this.alias              = alias             ;
        this.extraData          = extraData         ;
        this.location           = location          ;
        this.distance           = distance          ;
        this.isOnline           = isOnline          ;
        this.lastConnectionTime = lastConnectionTime;
        this.max                = max               ;
        this.offset             = offset            ;
        this.originalPhoto      = originalPhoto     ;
    }

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
                                    final Location           location          ,
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
    public Location getLocation() {
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
     * Gets the value of isOnline
     * @return isOnline
     */
    public Boolean isOnline() {
        return isOnline;
    }

    /**
     * Gets the value of originalPhoto
     * @return originalPhoto
     */
    public Boolean getOriginalPhoto() {
        return originalPhoto;
    }

    /**
     * Gets the value of lastConnectionTime
     * @return lastConnectionTime
     */
    public Long getLastConnectionTime() {
        return lastConnectionTime;
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
                ", isOnline='" + isOnline + '\'' +
                '}';
    }
}
