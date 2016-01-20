package com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces;

import java.util.List;

/**
 * Created by nelson on 22/10/15.
 */
public interface StockStatistics {
    List<Float> getDataX();

    List<Float> getDataY();

    List<Float> getStarts();

    List<Float> getEnds();
}
