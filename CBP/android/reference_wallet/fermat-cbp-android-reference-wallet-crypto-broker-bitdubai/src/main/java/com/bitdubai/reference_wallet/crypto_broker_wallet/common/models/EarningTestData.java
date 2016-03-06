package com.bitdubai.reference_wallet.crypto_broker_wallet.common.models;

import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningTransaction;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningsPairDetail;

import java.util.List;


/**
 * Created by nelson on 27/01/16.
 */
public class EarningTestData implements EarningsPairDetail {

    private final long timestamp;
    private final double earningValue;

    public EarningTestData(double earningValue,  long timestamp) {
        this.earningValue = earningValue;
        this.timestamp = timestamp;
    }

    @Override
    public List<EarningTransaction> listEarningTransactions() {
        return null;
    }

    @Override
    public double getAmount() {
        return earningValue;
    }

    @Override
    public long getFromTimestamp() {
        return timestamp;
    }

    @Override
    public long getToTimestamp() {
        return timestamp;
    }
}
