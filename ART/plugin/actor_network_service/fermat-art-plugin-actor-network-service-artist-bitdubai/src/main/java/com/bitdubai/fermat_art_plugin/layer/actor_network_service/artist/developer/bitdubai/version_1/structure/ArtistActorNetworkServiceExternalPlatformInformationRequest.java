package com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_art_api.layer.actor_network_service.enums.ProtocolState;
import com.bitdubai.fermat_art_api.layer.actor_network_service.enums.RequestType;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.ArtArtistExtraData;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.util.ArtistExternalPlatformInformation;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.enums.MessageTypes;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.messages.NetworkServiceMessage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 11/04/16.
 */
public class ArtistActorNetworkServiceExternalPlatformInformationRequest
        extends NetworkServiceMessage
        implements ArtArtistExtraData<
        ArtistExternalPlatformInformation> {

    private final UUID requestId;
    private final String requesterPublicKey;
    private final PlatformComponentType requesterActorType;
    private final String cryptoBrokerPublicKey;
    private final long updateTime;
    private final RequestType type;
    private final ProtocolState state;
    private final List<ArtistExternalPlatformInformation> informationList;

    /**
     * Default constructor with parameters.
     * @param requestId
     * @param requesterPublicKey
     * @param requesterActorType
     * @param cryptoBrokerPublicKey
     * @param updateTime
     * @param type
     * @param state
     * @param informationList
     */
    public ArtistActorNetworkServiceExternalPlatformInformationRequest(
            UUID requestId,
            String requesterPublicKey,
            PlatformComponentType requesterActorType,
            String cryptoBrokerPublicKey,
            long updateTime,
            RequestType type,
            ProtocolState state,
            List<ArtistExternalPlatformInformation> informationList) {
        super(MessageTypes.INFORMATION_REQUEST);
        this.requestId = requestId;
        this.requesterPublicKey = requesterPublicKey;
        this.requesterActorType = requesterActorType;
        this.cryptoBrokerPublicKey = cryptoBrokerPublicKey;
        this.updateTime = updateTime;
        this.type = type;
        this.state = state;
        this.informationList = informationList;
    }

    /**
     * This method returns an ArtistActorNetworkServiceExternalPlatformInformationRequest from a
     * Json message.
     * @param jsonMessage
     * @return
     */
    public static ArtistActorNetworkServiceExternalPlatformInformationRequest fromJson(
            String jsonMessage) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(
                jsonMessage,
                ArtistActorNetworkServiceExternalPlatformInformationRequest.class);
    }

    /**
     * This method returns the request id.
     * @return
     */
    @Override
    public UUID getRequestId() {
        return this.requestId;
    }

    /**
     * This method returns the requester public key.
     * @return
     */
    @Override
    public String getRequesterPublicKey() {
        return this.requesterPublicKey;
    }

    /**
     * This method returns the requester actor type.
     * @return
     */
    @Override
    public PlatformComponentType getRequesterActorType() {
        return this.requesterActorType;
    }

    /**
     * This method returns the artist public key.
     * @return
     */
    @Override
    public String getArtistPublicKey() {
        return this.cryptoBrokerPublicKey;
    }

    /**
     * This method returns the last update time
     * @return
     */
    @Override
    public long getUpdateTime() {
        return this.updateTime;
    }

    /**
     * This method returns the list information.
     * @return
     */
    @Override
    public List<ArtistExternalPlatformInformation> listInformation() {
        return this.informationList;
    }

    /**
     * This method returns the request type
     * @return
     */
    public RequestType getType() {
        return type;
    }

    /**
     * This method returns the protocol state
     * @return
     */
    public ProtocolState getState() {
        return state;
    }

    @Override
    public String toString() {
        return "ArtistActorNetworkServiceExternalPlatformInformationRequest{" +
                "requestId=" + requestId +
                ", requesterPublicKey='" + requesterPublicKey + '\'' +
                ", requesterActorType=" + requesterActorType +
                ", cryptoBrokerPublicKey='" + cryptoBrokerPublicKey + '\'' +
                ", updateTime=" + updateTime +
                ", type=" + type +
                ", state=" + state +
                ", informationList=" + informationList +
                '}';
    }
}
