package com.bitdubai.fermat_ccp_api.all_definition.util;


import java.text.DecimalFormat;

/**
 * Created by Matias Furszyfer on 2015.09.04..
 */
public class WalletUtils {


    /**
     *  Formationg balance amount
     * @param balance
     * @return
     */
    public static String formatBalanceString(long balance) {
        String stringBalance = "";

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        df.setMinimumFractionDigits(2);
        String BTCFormat = df.format(balance / 100000000.0);
        stringBalance = BTCFormat;
        return stringBalance;
    }
}
