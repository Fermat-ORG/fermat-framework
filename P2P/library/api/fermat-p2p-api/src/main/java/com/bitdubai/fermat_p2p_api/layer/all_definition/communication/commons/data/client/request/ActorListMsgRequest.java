package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.request;

import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.DiscoveryQueryParameters;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.PackageContent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.util.GsonProvider;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.MessageContentType;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.request.ActorListMsgRequest</code>
 * represent the message to request a list of actors<p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 24/06/2016.
 *
 * @author  lnacosta
 * @version 1.0
 * @since   Java JDK 1.7
 */
public class ActorListMsgRequest extends PackageContent {

    private NetworkServiceType       networkServiceType;
    private DiscoveryQueryParameters parameters        ;
    private String                   clientPublicKey   ;

    public ActorListMsgRequest(final NetworkServiceType       networkServiceType,
                               final DiscoveryQueryParameters parameters        ,
                               final String                   clientPublicKey   ) {

        super(MessageContentType.JSON);

        this.networkServiceType = networkServiceType;
        this.parameters         = parameters        ;
        this.clientPublicKey    = clientPublicKey   ;
    }

    public NetworkServiceType getNetworkServiceType() {
        return networkServiceType;
    }

    public void setNetworkServiceType(NetworkServiceType networkServiceType) {
        this.networkServiceType = networkServiceType;
    }

    public DiscoveryQueryParameters getParameters() {
        return parameters;
    }

    public void setParameters(DiscoveryQueryParameters parameters) {
        this.parameters = parameters;
    }

    public String getClientPublicKey() {
        return clientPublicKey;
    }

    public void setClientPublicKey(String clientPublicKey) {
        this.clientPublicKey = clientPublicKey;
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
    public static ActorListMsgRequest parseContent(String content) {
        return GsonProvider.getGson().fromJson(content, ActorListMsgRequest.class);
    }

    @Override
    public String toString() {
        return "ActorListMsgRequest{" +
                "networkServiceType=" + networkServiceType +
                ", parameters=" + parameters +
                ", clientPublicKey='" + clientPublicKey + '\'' +
                '}';
    }
}
