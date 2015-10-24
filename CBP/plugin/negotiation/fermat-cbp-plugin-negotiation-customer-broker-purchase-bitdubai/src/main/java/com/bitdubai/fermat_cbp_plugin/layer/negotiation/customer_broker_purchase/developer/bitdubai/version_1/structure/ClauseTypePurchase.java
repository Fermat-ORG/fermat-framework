package com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_purchase.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;

/**
 * Created by angel on 23/10/15.
 */
public class ClauseTypePurchase {

    private ClauseType type;
    private short indexOrder;

    public ClauseTypePurchase(ClauseType type, short indexOrder){
        this.type = type;
        this.indexOrder = indexOrder;
    }

    public ClauseType getType(){
        return this.type;
    }

    public short getIndexOrder(){
        return this.indexOrder;
    }

}
