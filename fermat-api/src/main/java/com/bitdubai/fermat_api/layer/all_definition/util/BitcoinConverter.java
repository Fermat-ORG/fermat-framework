package com.bitdubai.fermat_api.layer.all_definition.util;

/**
 * Created by Frank Contreras (contrerasfrank@gmail.com) on 1/15/16.
 */
public class BitcoinConverter {
    public enum Currency {
        BITCOIN(1),
        MBTC(1000),
        BIT(1000000),
        SATOSHI(100000000);

        private double rate;
        Currency(double rate) {
            this.rate = rate;
        }
        double getRate() {
            return rate;
        }
    }

    public static double convert(double amount, Currency from, Currency to) {
        double convertAmount;
        if (from.equals(Currency.BITCOIN)) {
            convertAmount = amount * to.getRate();
        } else {
            double btcAmount = amount / from.getRate();
            convertAmount = btcAmount * to.getRate();
        }
        return convertAmount;
    }
}
