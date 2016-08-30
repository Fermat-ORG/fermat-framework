package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.request;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.PackageContent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.util.GsonProvider;
import org.apache.commons.lang.NotImplementedException;

/**
 * Created by mati on 22/08/16.
 */
public class SubscriberMsgRequest extends PackageContent{

    private short eventCode;

    private String condition;

    public SubscriberMsgRequest(short eventCode, String condition) {
        this.eventCode = eventCode;
        this.condition = condition;
    }

    public short getEventCode() {
        return eventCode;
    }

    public String getCondition() {
        return condition;
    }


    public static SubscriberMsgRequest parseContent(String content) {
        return GsonProvider.getGson().fromJson(content,SubscriberMsgRequest.class);
    }

    public String toJson() {
        return GsonProvider.getGson().toJson(this);
    }

    @Override
    public String toString() {
        return "SubscriberMsgRequest{" +
                "eventCode=" + eventCode +
                ", condition='" + condition + '\'' +
                '}';
    }
}
