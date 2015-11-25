package com.bitdubai.fermat_bnk_core.layer.wallet;

import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractLayer;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantStartLayerException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;

/**
 * Created by memo on 25/11/15.
 */
public class WalletLayer extends AbstractLayer {


    public WalletLayer() {
        super(Layers.WALLET);
    }

    @Override
    public void start() throws CantStartLayerException {

    }
}
