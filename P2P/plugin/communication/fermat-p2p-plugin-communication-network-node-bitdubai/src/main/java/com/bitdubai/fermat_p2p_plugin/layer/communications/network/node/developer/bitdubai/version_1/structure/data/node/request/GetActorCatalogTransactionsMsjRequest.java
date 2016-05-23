package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.data.node.request;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.PackageContent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.util.GsonProvider;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.node.request.GetActorCatalogTransactionsMsjRequest</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 28/04/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class GetActorCatalogTransactionsMsjRequest extends PackageContent {

    /**
     * Represent the offset
     */
    private Integer offset;

    /**
     * Represent the max
     */
    private Integer max;

    /**
     * Constructor
     */
    public GetActorCatalogTransactionsMsjRequest(){
        super();
    }

    /**
     * Constructor with parameters
     *
     * @param offset
     * @param max
     */
    public GetActorCatalogTransactionsMsjRequest(Integer offset, Integer max){
        super();
        this.offset = offset;
        this.max = max;
    }

    /**
     * Get the first record to make pagination
     *
     * @return int
     */
    public Integer getOffset() {
        return offset;
    }

    /**
     * Get the number of max the record to return
     *
     * @return int
     */
    public Integer getMax() {
        return max;
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
    public static GetActorCatalogTransactionsMsjRequest parseContent(String content) {
        return GsonProvider.getGson().fromJson(content, GetActorCatalogTransactionsMsjRequest.class);
    }

}
