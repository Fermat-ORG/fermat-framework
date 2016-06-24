package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.request;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.DiscoveryQueryParameters;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.PackageContent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.util.GsonProvider;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.MessageContentType;

import java.util.UUID;

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

    private UUID                     queryId                ;
    private String                   networkServicePublicKey;
    private DiscoveryQueryParameters parameters             ;
    private String                   clientPublicKey        ;

    public ActorListMsgRequest(final UUID                     queryId                ,
                               final String                   networkServicePublicKey,
                               final DiscoveryQueryParameters parameters             ,
                               final String                   clientPublicKey        ) {

        super(MessageContentType.JSON);

        this.queryId                 = queryId                ;
        this.networkServicePublicKey = networkServicePublicKey;
        this.parameters              = parameters             ;
        this.clientPublicKey         = clientPublicKey        ;
    }

    public UUID getQueryId() {
        return queryId;
    }

    public String getNetworkServicePublicKey() {
        return networkServicePublicKey;
    }

    public DiscoveryQueryParameters getParameters() {
        return parameters;
    }

    public String getClientPublicKey() {
        return clientPublicKey;
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
                "queryId=" + queryId +
                ", networkServicePublicKey='" + networkServicePublicKey + '\'' +
                ", parameters=" + parameters +
                ", clientPublicKey='" + clientPublicKey + '\'' +
                '}';
    }
}
