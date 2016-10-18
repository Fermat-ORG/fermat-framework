package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.events;

import com.bitdubai.fermat_api.layer.all_definition.events.common.AbstractEvent;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.base.STATUS;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.enums.ProfileStatus;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;

import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 17/08/16.
 */
public class NetworkClientIsActorOnlineEvent extends AbstractEvent<P2pEventType> {

    /**
     * Represent the network client call.
     */
    private NetworkServiceType networkServiceType;

    /**
     * Represents the actor profile
     */
    private String actorProfilePublicKey      ;

    /**
     * Represents the profile status
     */
    private ProfileStatus profileStatus;

    /**
     * Represent the status
     */
    private STATUS status;

    /**
     * Represents the packageId
     */
    private UUID packageId;

    /**
     * Constructor with parameters
     *
     * @param p2pEventType
     */
    public NetworkClientIsActorOnlineEvent(P2pEventType p2pEventType) {
        super(p2pEventType);
    }

    public NetworkServiceType getNetworkServiceType() {
        return networkServiceType;
    }

    public void setNetworkServiceType(NetworkServiceType networkServiceType) {
        this.networkServiceType = networkServiceType;
    }

    public String getActorProfilePublicKey() {
        return actorProfilePublicKey;
    }

    public void setActorProfilePublicKey(String actorProfilePublicKey) {
        this.actorProfilePublicKey = actorProfilePublicKey;
    }

    public ProfileStatus getProfileStatus() {
        return profileStatus;
    }

    public void setProfileStatus(ProfileStatus profileStatus) {
        this.profileStatus = profileStatus;
    }

    public STATUS getStatus() {
        return status;
    }

    public void setStatus(STATUS status) {
        this.status = status;
    }

    public UUID getPackageId() {
        return packageId;
    }

    public void setPackageId(UUID packageId) {
        this.packageId = packageId;
    }

    @Override
    public String toString() {
        return "NetworkClientActorUnreachableEvent{" +
                "networkServiceType=" + networkServiceType +
                ", actorProfilePublicKey=" + actorProfilePublicKey +
                ", profileStatus=" + profileStatus +
                ", status=" + status +
                ", packageId=" + packageId +
                '}';
    }
}
