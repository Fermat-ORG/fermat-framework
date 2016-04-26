package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond;

import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.PackageContent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.ActorProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.NetworkServiceProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.util.GsonProvider;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.util.InterfaceAdapter;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.MessageContentType;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.request.ActorCallRequest</code>
 * represent the message to request a network call between actors<p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/04/2016.
 *
 * @author  lnacosta
 * @version 1.0
 * @since   Java JDK 1.7
 */
public class ActorCallRespond extends MsgRespond {

    private ActorProfile          actorFrom         ;
    private NetworkServiceProfile networkServiceFrom;
    private ActorProfile          actorTo           ;

    public ActorCallRespond(final ActorProfile          actorFrom         ,
                            final NetworkServiceProfile networkServiceFrom,
                            final ActorProfile          actorTo           ,
                            final STATUS                status            ,
                            final String                details           ) {

        super(status, details);

        this.actorFrom          = actorFrom         ;
        this.networkServiceFrom = networkServiceFrom;
        this.actorTo            = actorTo           ;
    }

    public NetworkServiceProfile getNetworkServiceFrom() {
        return networkServiceFrom;
    }

    public ActorProfile getActorFrom() {
        return actorFrom;
    }

    public ActorProfile getActorTo() {
        return actorTo;
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
    public static ActorCallRespond parseContent(String content) {
        return GsonProvider.getGson().fromJson(content, ActorCallRespond.class);
    }
}
