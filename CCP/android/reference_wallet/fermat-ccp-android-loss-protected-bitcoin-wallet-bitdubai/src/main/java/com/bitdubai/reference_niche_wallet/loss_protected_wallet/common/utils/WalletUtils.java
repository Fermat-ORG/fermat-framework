package com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.utils;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWallet;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.enums.ShowMoneyType;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
    public static String formatBalanceString(long balance,int typeAmount) {
        String stringBalance = "";

        if(typeAmount== ShowMoneyType.BITCOIN.getCode()){
            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(4);
            df.setMinimumFractionDigits(2);
            String BTCFormat = "";

              BTCFormat = df.format(balance / 100000000.0); //

            stringBalance = BTCFormat ;//+ " BTC";
        }else if(typeAmount== ShowMoneyType.BITS.getCode()){
            stringBalance = String.valueOf(balance / 100);
        }
        showMoneyType=!showMoneyType;


        return stringBalance;
    }

    public static String formatBalanceStringNotDecimal(long balance,int typeAmount) {
        String stringBalance = "";

        if(typeAmount== ShowMoneyType.BITCOIN.getCode()){
            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(0);
            df.setMinimumFractionDigits(0);
            String BTCFormat = "";

            BTCFormat = df.format(balance / 100000000.0); //

            stringBalance = BTCFormat ;//+ " BTC";
        }else if(typeAmount== ShowMoneyType.BITS.getCode()){
            stringBalance = String.valueOf(balance / 100);
        }
        showMoneyType=!showMoneyType;


        return stringBalance;
    }

    /**
     *
     * @param strToValidate
     * @return
     */
    public static CryptoAddress validateAddress(String strToValidate,LossProtectedWallet cryptoWallet) {
        String[] tokens = strToValidate.split("-|\\.|:|,|;| ");

        CryptoAddress cryptoAddress = new CryptoAddress(null, CryptoCurrency.BITCOIN);
        for (String token : tokens) {
            token = token.trim();
            if (token.length() > 25 && token.length() < 40) {
                cryptoAddress.setAddress(token);
                if (cryptoWallet.isValidAddress(cryptoAddress)) {
                    return cryptoAddress;
                }
            }
        }
        //TODO Return null method - OJO: only informative for visual aid during debug - remove if it bothers
        return null;
    }

    public static void showMessage(Context context,String text) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Warning");
        alertDialog.setMessage(text);
        alertDialog.setButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // aquí puedes añadir funciones
            }
        });
        // alertDialog.setIcon(R.drawable.icon);
        alertDialog.show();
    }
    /**
     *  Formationg Amount
     * @param amount
     * @return
     */
    public static String formatAmountString(double amount) {
        String stringAmount = "";


        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(5);
        df.setMinimumFractionDigits(2);

        stringAmount =df.format(amount);//+ " BTC";

        return stringAmount;
    }

    /**
     *  Formationg Amount
     * @param amount
     * @return
     */
    public static String formatAmountStringWithDecimalEntry(double amount,int maxDecimal, int minDecimal) {
        String stringAmount = "";


        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(maxDecimal);
        df.setMinimumFractionDigits(minDecimal);

        stringAmount =df.format(amount);//+ " BTC";

        return stringAmount;
    }
    /**
     *  Formationg Amount no decimal
     * @param amount
     * @return
     */
    public static String formatAmountStringNotDecimal(int amount) {
        String stringAmount = "";


        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(0);
        df.setMinimumFractionDigits(0);

        stringAmount =df.format(amount);//+ " BTC";

        return stringAmount;
    }



    /**
     *  Formationg Exchange Rate Amount
     * @param rateAmount
     * @return
     */
    public static String formatExchangeRateString(double rateAmount) {
        String stringAmount = "";


        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        df.setMinimumFractionDigits(2);

        stringAmount =df.format(rateAmount);//+ " BTC";

        return stringAmount;
    }


}
