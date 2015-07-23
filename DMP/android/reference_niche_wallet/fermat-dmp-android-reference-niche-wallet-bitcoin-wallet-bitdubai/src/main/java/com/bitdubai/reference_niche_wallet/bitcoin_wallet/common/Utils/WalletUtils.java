package com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.Utils;


import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.enums.ShowMoneyType;

import java.text.DecimalFormat;

/**
 * Created by Matias Furszyfer on 2015.07.22..
 */
public class WalletUtils {


    public static boolean showMoneyType=true;

    /**
     *  Formationg balance amount
     * @param balance
     * @return
     */
    public static String formatBalanceString(long balance) {
        String stringBalance = "";

        if(showMoneyType){
            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(2);
            df.setMinimumFractionDigits(2);
            String BTCFormat = df.format(balance / 100000000.0);
            stringBalance = BTCFormat + " BTC";
        }else{
            DecimalFormat df1 = new DecimalFormat();
            df1.setMaximumFractionDigits(2);
            df1.setMinimumFractionDigits(2);
            String BTCFormat1 = df1.format(balance / 100000000.0);
            stringBalance = BTCFormat1 + " BTC";
        }
        showMoneyType=!showMoneyType;

        /*switch (showMoneyType){

            case true:
                DecimalFormat df = new DecimalFormat();
                df.setMaximumFractionDigits(2);
                df.setMinimumFractionDigits(2);
                String BTCFormat = df.format(balance / 100000000.0);
                stringBalance = BTCFormat + " BTC";
                break;
            case false:
                stringBalance = (int) (balance / 100) + " bits";
                break;
            default:
                DecimalFormat df1 = new DecimalFormat();
                df1.setMaximumFractionDigits(2);
                df1.setMinimumFractionDigits(2);
                String BTCFormat1 = df1.format(balance / 100000000.0);
                stringBalance = BTCFormat1 + " BTC";
                break;
        }*/
        return stringBalance;
    }
}
