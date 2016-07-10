package com.bitdubai.fermat_dmp_android_clone_reference_nich_wallet.common.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWallet;

import com.bitdubai.fermat_dmp_android_clone_reference_nich_wallet.common.enums.ShowMoneyType;

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
    public static String formatBalanceString(long balance,int typeAmount) {
        String stringBalance = "";

        if(typeAmount== ShowMoneyType.BITCOIN.getCode()){
            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(2);
            df.setMinimumFractionDigits(2);
            String BTCFormat = df.format(balance / 100000000.0);
            stringBalance = BTCFormat + " BTC";
        }else if(typeAmount== ShowMoneyType.BITS.getCode()){
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

    /**
     *
     * @param strToValidate
     * @return
     */
    public static CryptoAddress validateAddress(String strToValidate,CryptoWallet cryptoWallet,BlockchainNetworkType blockchainNetworkType) {
        String[] tokens = strToValidate.split("-|\\.|:|,|;| ");

        CryptoAddress cryptoAddress = new CryptoAddress(null, CryptoCurrency.BITCOIN);
        for (String token : tokens) {
            token = token.trim();
            if (token.length() > 25 && token.length() < 40) {
                cryptoAddress.setAddress(token);
                if (cryptoWallet.isValidAddress(cryptoAddress,blockchainNetworkType)) {
                    return cryptoAddress;
                }
            }
        }
        //TODO METODO CON RETURN NULL - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
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
}
