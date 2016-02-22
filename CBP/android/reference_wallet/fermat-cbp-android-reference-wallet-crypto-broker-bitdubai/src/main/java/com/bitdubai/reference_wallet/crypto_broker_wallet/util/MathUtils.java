package com.bitdubai.reference_wallet.crypto_broker_wallet.util;

import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ClauseInformation;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;

/**
 * Created by nelson on 21/02/16.
 */
public final class MathUtils {
    private static final NumberFormat numberFormat = DecimalFormat.getInstance();

    public static BigDecimal getBigDecimal(ClauseInformation clause) {
        String value = clause.getValue();

        if (value == null)
            return BigDecimal.ZERO;

        try {
            return new BigDecimal(numberFormat.parse(value).doubleValue());
        } catch (ParseException e) {
            return BigDecimal.ZERO;
        }
    }
}
