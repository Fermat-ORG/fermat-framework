package com.bitdubai.fermat_api.layer.all_definition.util;

import java.math.BigDecimal;

/**
 * Created by Frank Contreras (contrerasfrank@gmail.com) on 1/15/16.
 */
public class BitcoinConverter {
    public enum Currency {
        BITCOIN(1),
        MBTC(1000),
        BIT(1000000),
        SATOSHI(100000000);

        private long rate;
        Currency(long rate) {
            this.rate = rate;
        }
        long getRate() {
            return rate;
        }
    }

    public static double convert(double amount, Currency from, Currency to) {
        BigDecimal convertAmount;
        BigDecimal amountInternal = new BigDecimal(amount);
        BigDecimal rateToInternal = new BigDecimal(to.getRate());
        BigDecimal rateFromInternal = new BigDecimal(from.getRate());
        if (from.equals(Currency.BITCOIN)) {
            convertAmount = amountInternal.multiply(rateToInternal);
        } else {
            BigDecimal btcAmount = amountInternal.divide(rateFromInternal);
            convertAmount = btcAmount.multiply(rateToInternal);
        }
        return convertAmount.doubleValue();
    }
}
