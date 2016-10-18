package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.checkin;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.base.MsgRespond;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.base.STATUS;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.util.GsonProvider;
import com.google.gson.annotations.Expose;

import java.util.UUID;

/**
 * Created by mati on 14/08/16.
 */
public class ActorCheckInRespond extends MsgRespond {

    @Expose(serialize = true, deserialize = true)
    private String publicKey;

    /**
     * Constructor with parameters
     *
     * @param packageId
     * @param status
     * @param details
     */
    public ActorCheckInRespond(UUID packageId, STATUS status, String details) {
        super(packageId, status, details);
    }


    @Override
    public String toJson() {
        return GsonProvider.getGson().toJson(this);
    }

    public static ActorCheckInRespond parseContent(String content){
        return GsonProvider.getGson().fromJson(content,ActorCheckInRespond.class);
    }


    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }
}
