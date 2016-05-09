package com.bitdubai.fermat_api.layer.all_definition.util;

import java.math.BigDecimal;

/**
 * Created by Frank Contreras (contrerasfrank@gmail.com) on 1/15/16.
 */
public class BitcoinConverter {
    public enum Currency {
        BITCOIN(1, "BTC"),
        MBTC(1000, "MBTC"),
        BIT(1000000, "BIT"),
        SATOSHI(100000000, "SATOSHI");

        private long rate;
        private String name;
        Currency(long rate, String name) {
            this.rate = rate;
            this.name = name;
        }
        long getRate() {
            return rate;
        }
        public String getName() {
            return name;
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
