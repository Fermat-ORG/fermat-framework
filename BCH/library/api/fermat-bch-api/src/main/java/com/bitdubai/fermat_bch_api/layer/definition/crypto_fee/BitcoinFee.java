package com.bitdubai.fermat_bch_api.layer.definition.crypto_fee;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by rodrigo on 7/2/16.
 */
public enum BitcoinFee {
    SLOW(20000),
    NORMAL(30000),
    FAST(50000);

    private long fee;

    BitcoinFee(long fee) {
        this.fee = fee;
    }

    public long getFee() {
        return this.fee;
    }

    public static BitcoinFee getByFee(long fee) throws InvalidParameterException {
        if (fee < BitcoinFee.NORMAL.getFee())
            return SLOW;
        else if (fee < BitcoinFee.FAST.getFee())
            return NORMAL;
        else return FAST;

    }
}
