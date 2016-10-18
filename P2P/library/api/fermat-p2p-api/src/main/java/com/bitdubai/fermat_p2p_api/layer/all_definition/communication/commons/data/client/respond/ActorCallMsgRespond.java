package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond;

import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.base.MsgRespond;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.base.STATUS;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.util.GsonProvider;

import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.request.ActorCallMsgRespond</code>
 * represent the message to respond a network call between actors<p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/05/2016.
 *
 * @author  lnacosta
 * @version 1.0
 * @since   Java JDK 1.7
 */
public class ActorCallMsgRespond extends MsgRespond {

    private NetworkServiceType        networkServiceType;
    private ResultDiscoveryTraceActor traceActor        ;

    public ActorCallMsgRespond(UUID packageId, final NetworkServiceType        networkServiceType,
                               final ResultDiscoveryTraceActor traceActor        ,
                               final STATUS status            ,
                               final String                    details           ) {

        super(packageId,status, details);

        this.networkServiceType = networkServiceType;
        this.traceActor         = traceActor        ;
    }

    public NetworkServiceType getNetworkServiceType() {
        return networkServiceType;
    }

    public ResultDiscoveryTraceActor getTraceActor() {
        return traceActor;
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
    public static ActorCallMsgRespond parseContent(String content) {
        return GsonProvider.getGson().fromJson(content, ActorCallMsgRespond.class);
    }

    @Override
    public String toString() {
        return "ActorCallMsgRespond{" +
                "networkServiceType=" + networkServiceType +
                ", traceActor=" + traceActor +
                '}';
    }
}
