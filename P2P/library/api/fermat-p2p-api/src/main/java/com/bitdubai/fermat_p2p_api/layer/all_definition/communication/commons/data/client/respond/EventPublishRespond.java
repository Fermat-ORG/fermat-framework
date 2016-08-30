package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.PackageContent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.util.GsonProvider;
import com.google.gson.annotations.Expose;

import java.util.UUID;

/**
 * Created by mati on 14/08/16.
 */
public class EventPublishRespond extends PackageContent {

    // 1 is true or 0 is false (not happen)
    private byte isEventHappen;

    public EventPublishRespond(boolean isEventHappen) {
        byte happen = 1;
        this.isEventHappen = (isEventHappen)?happen:0;
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
    public static EventPublishRespond parseContent(String content) {
        return GsonProvider.getGson().fromJson(content, EventPublishRespond.class);
    }

    public boolean getIsEventHappen() {
        return (isEventHappen == 1);
    }

    @Override
    public String toString() {
        return "EventPublishRespond{" +
                "isEventHappen=" + getIsEventHappen() +
                '}';
    }
}
