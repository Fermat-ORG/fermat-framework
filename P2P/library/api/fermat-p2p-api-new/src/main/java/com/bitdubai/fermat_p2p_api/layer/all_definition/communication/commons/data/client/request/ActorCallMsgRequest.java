package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.request;

import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.PackageContent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.ActorProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.util.GsonProvider;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.MessageContentType;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.request.ActorCallMsgRequest</code>
 * represent the message to request a network call between actors<p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/04/2016.
 *
 * @author  lnacosta
 * @version 1.0
 * @since   Java JDK 1.7
 */
public class ActorCallMsgRequest extends PackageContent {

    private NetworkServiceType networkServiceType;
    private ActorProfile       actorTo           ;

    public ActorCallMsgRequest(final NetworkServiceType networkServiceType,
                               final ActorProfile actorTo) {

        super(MessageContentType.JSON);

        this.networkServiceType = networkServiceType;
        this.actorTo            = actorTo           ;
    }

    public NetworkServiceType getNetworkServiceType() {
        return networkServiceType;
    }

    public void setNetworkServiceType(NetworkServiceType networkServiceType) {
        this.networkServiceType = networkServiceType;
    }

    public ActorProfile getActorTo() {
        return actorTo;
    }

    public void setActorTo(ActorProfile actorTo) {
        this.actorTo = actorTo;
    }

    /**
     * Generate the json representation
     * @return String
     */
    @Override
    public String toJson() {
        return GsonProvider.getGson().toJson(this, getClass());
    }

    /**
     * Get the object
     *
     * @param content
     * @return PackageContent
     */
    public static ActorCallMsgRequest parseContent(String content) {
        return GsonProvider.getGson().fromJson(content, ActorCallMsgRequest.class);
    }

    @Override
    public String toString() {
        return "ActorCallRequest{" +
                "networkServiceType=" + networkServiceType +
                ", actorTo=" + actorTo +
                '}';
    }
}
