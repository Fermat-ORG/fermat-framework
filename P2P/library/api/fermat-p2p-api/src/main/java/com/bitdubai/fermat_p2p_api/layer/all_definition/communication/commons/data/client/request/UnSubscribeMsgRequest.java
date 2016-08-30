package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.request;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.PackageContent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.util.GsonProvider;

/**
 * Created by mati on 22/08/16.
 */
public class UnSubscribeMsgRequest extends PackageContent{

    private String unSubscribeEventId;

    public UnSubscribeMsgRequest(String unSubscribeEventId) {
        this.unSubscribeEventId = unSubscribeEventId;
    }

    public String getUnSubscribeEventId() {
        return unSubscribeEventId;
    }

    public void setUnSubscribeEventId(String unSubscribeEventId) {
        this.unSubscribeEventId = unSubscribeEventId;
    }

    public static UnSubscribeMsgRequest parseContent(String content) {
        return GsonProvider.getGson().fromJson(content, UnSubscribeMsgRequest.class);
    }

    public String toJson() {
        return GsonProvider.getGson().toJson(this);
    }

    @Override
    public String toString() {
        return "UnSubscribeMsgRequest{" +
                "unSubscribeEventId='" + unSubscribeEventId + '\'' +
                '}';
    }
}
