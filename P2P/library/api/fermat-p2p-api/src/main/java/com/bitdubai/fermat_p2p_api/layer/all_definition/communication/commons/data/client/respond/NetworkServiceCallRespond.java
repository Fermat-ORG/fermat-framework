package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.base.MsgRespond;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.base.STATUS;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.NetworkServiceProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.util.GsonProvider;

import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.request.NetworkServiceCallRespond</code>
 * represent the message to request a network call between network services<p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/04/2016.
 *
 * @author  lnacosta
 * @version 1.0
 * @since   Java JDK 1.7
 */
public class NetworkServiceCallRespond extends MsgRespond {

    private NetworkServiceProfile networkServiceFrom;
    private NetworkServiceProfile networkServiceTo  ;

    public NetworkServiceCallRespond(UUID packageId, final NetworkServiceProfile networkServiceFrom,
                                     final NetworkServiceProfile networkServiceTo  ,
                                     final STATUS status            ,
                                     final String                details           ) {

        super(packageId,status, details);

        this.networkServiceFrom = networkServiceFrom;
        this.networkServiceTo   = networkServiceTo  ;
    }

    public NetworkServiceProfile getNetworkServiceFrom() {
        return networkServiceFrom;
    }

    public NetworkServiceProfile getNetworkServiceTo() {
        return networkServiceTo;
    }

    @Override
    public String toString() {
        return "NetworkServiceCallRespond{" +
                "networkServiceFrom=" + networkServiceFrom +
                ", networkServiceTo=" + networkServiceTo +
                '}';
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
    public static NetworkServiceCallRespond parseContent(String content) {
        return GsonProvider.getGson().fromJson(content, NetworkServiceCallRespond.class);
    }
}
