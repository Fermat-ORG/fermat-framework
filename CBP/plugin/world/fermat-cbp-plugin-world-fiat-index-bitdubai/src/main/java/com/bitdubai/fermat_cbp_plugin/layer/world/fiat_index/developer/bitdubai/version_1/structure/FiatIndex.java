package com.bitdubai.fermat_cbp_plugin.layer.world.fiat_index.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_cbp_api.all_definition.world.Index;

/**
 * Created by jorge on 30-10-2015.
 */
public class FiatIndex implements Index {
    @Override
    public FermatEnum getCurrency() {
        return null;
    }

    @Override
    public FermatEnum getReferenceCurrency() {
        return null;
    }

    @Override
    public float getSalePrice() {
        return 0;
    }

    @Override
    public float getPurchasePrice() {
        return 0;
    }

    @Override
    public long getTimeStamp() {
        return 0;
    }
}
