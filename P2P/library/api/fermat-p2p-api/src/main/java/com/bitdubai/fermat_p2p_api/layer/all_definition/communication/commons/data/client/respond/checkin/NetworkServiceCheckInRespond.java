package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.checkin;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.base.MsgRespond;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.base.STATUS;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.util.GsonProvider;

import java.util.UUID;

/**
 * Created by mati on 14/08/16.
 */
public class NetworkServiceCheckInRespond extends MsgRespond {


    /**
     * Constructor with parameters
     *
     * @param packageId
     * @param status
     * @param details
     */
    public NetworkServiceCheckInRespond(UUID packageId, STATUS status, String details) {
        super(packageId, status, details);
    }


    @Override
    public String toJson() {
        return GsonProvider.getGson().toJson(this);
    }

    public static NetworkServiceCheckInRespond parseContent(String content){
        return GsonProvider.getGson().fromJson(content,NetworkServiceCheckInRespond.class);
    }

}
