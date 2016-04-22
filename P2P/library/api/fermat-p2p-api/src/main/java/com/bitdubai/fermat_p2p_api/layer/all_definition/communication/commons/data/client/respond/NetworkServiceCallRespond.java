package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond;

import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.PackageContent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.NetworkServiceProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.util.InterfaceAdapter;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.MessageContentType;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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

    public NetworkServiceCallRespond(final NetworkServiceProfile networkServiceFrom,
                                     final NetworkServiceProfile networkServiceTo  ,
                                     final STATUS                status            ,
                                     final String                details           ) {

        super(status, details);

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

    @Override
    public String toJson() {

        return getGsonInstance().toJson(this, getClass());
    }

    public static NetworkServiceCallRespond parseContent(String content) {

        return getGsonInstance().fromJson(content, NetworkServiceCallRespond.class);
    }

    private static Gson getGsonInstance() {

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Location.class, new InterfaceAdapter<Location>());
        return builder.create();
    }
}
