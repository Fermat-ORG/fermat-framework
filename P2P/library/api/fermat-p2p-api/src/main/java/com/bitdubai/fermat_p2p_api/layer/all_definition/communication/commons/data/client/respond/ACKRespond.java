package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.base.MsgRespond;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.base.STATUS;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.util.GsonProvider;

import java.util.UUID;

/**
 * Created by mati on 14/08/16.
 */
public class ACKRespond extends MsgRespond {


    /**
     * Constructor with parameters
     *
     * @param packageId
     * @param status
     * @param details
     */
    public ACKRespond(UUID packageId, STATUS status, String details) {
        super(packageId, status, details);
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
    public static ACKRespond parseContent(String content) {
        return GsonProvider.getGson().fromJson(content, ACKRespond.class);
    }

    @Override
    public String toString() {
        return "ACKRespond{" +
                "packageId=" + getPackageId() +
                '}';
    }
}
