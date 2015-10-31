package com.bitdubai.fermat_cbp_api.all_definition.world;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;

/**
 * Created by Yordin Alayn on 25,09,15.
 */
public interface Index {
    FermatEnum getCurrency();
    FermatEnum getReferenceCurrency();
    float getSalePrice();
    float getPurchasePrice();
    long getTimeStamp();
}
