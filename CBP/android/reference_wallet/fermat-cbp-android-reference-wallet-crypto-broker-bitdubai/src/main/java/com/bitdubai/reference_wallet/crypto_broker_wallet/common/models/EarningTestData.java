package com.bitdubai.reference_wallet.crypto_broker_wallet.common.models;

import com.bitdubai.fermat_api.layer.world.interfaces.Currency;

import java.math.BigDecimal;

/**
 * Created by nelson on 27/01/16.
 */
public class EarningTestData {

    private double earningValue;
    private double previousEarningValue;
    private long timestamp;
    private Currency currency;

    public EarningTestData(Currency currency, double earningValue, double previousEarningValue, long timestamp) {
        this.currency = currency;
        this.earningValue = earningValue;
        this.timestamp = timestamp;
        this.previousEarningValue = previousEarningValue;
    }

    public Currency getCurrency() {
        return currency;
    }

    public double getDifference() {
        BigDecimal earningValueBigDec = BigDecimal.valueOf(earningValue);
        BigDecimal previousEarningValueBigDec = BigDecimal.valueOf(previousEarningValue);

        return earningValueBigDec.subtract(previousEarningValueBigDec).doubleValue();
    }

    public double getDifferencePercent() {
        double difference = getDifference();
        return difference != 0 ? Math.abs(difference / previousEarningValue) * 100 : 0;
    }

    public double getEarningValue() {
        return earningValue;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
