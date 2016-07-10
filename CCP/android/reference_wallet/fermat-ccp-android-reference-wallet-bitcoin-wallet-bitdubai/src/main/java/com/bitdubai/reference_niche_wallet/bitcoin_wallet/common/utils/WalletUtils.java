package com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.utils;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Base64;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantDecryptException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantEncryptException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.enums.ShowMoneyType;

import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Matias Furszyfer on 2015.07.22..
 */
public class WalletUtils {

    private static final String CHARSET_NAME = "UTF-8";
    private static final String DIGEST_ALGORITHM = "MD5";
    private static final String KEYSPEC_ALGORITHM = "DESede";

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
            df.setMaximumFractionDigits(6);
            df.setMinimumFractionDigits(2);
            String BTCFormat = "";
            BTCFormat = df.format(balance / 100000000.0); //
            stringBalance = BTCFormat ;//+ " BTC";
        }else if(typeAmount== ShowMoneyType.BITS.getCode()){
            stringBalance = String.valueOf(balance / 100);
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


    /**
     * Private Encrypting Method.
     */

    public  static String encrypt(String text, String secretKey) throws CantEncryptException {

        String base64EncryptedString = "";

        try {

            MessageDigest md = MessageDigest.getInstance(DIGEST_ALGORITHM);
            byte[] digestOfPassword = md.digest(secretKey.getBytes(CHARSET_NAME));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);

            SecretKey key = new SecretKeySpec(keyBytes, KEYSPEC_ALGORITHM);
            Cipher cipher = Cipher.getInstance(KEYSPEC_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);

            byte[] plainTextBytes = text.getBytes(CHARSET_NAME);
            byte[] buf = cipher.doFinal(plainTextBytes);
            byte[] base64Bytes = Base64.encode(buf, 1);
            base64EncryptedString = new String(base64Bytes, CHARSET_NAME);

        } catch (Exception ex) {
            String message = CantEncryptException.DEFAULT_MESSAGE;
            FermatException cause = FermatException.wrapException(ex);
            String context = "Encription Digest Algorithm: " + DIGEST_ALGORITHM;
            context += CantDecryptException.CONTEXT_CONTENT_SEPARATOR;
            context += "Encription KeySpec Algorithm: " + KEYSPEC_ALGORITHM;
            context += CantDecryptException.CONTEXT_CONTENT_SEPARATOR;
            context += "Charset: " + CHARSET_NAME;
            String possibleReason = "This is most likely to happen due to a bad Secret Key passing";
            throw  new CantEncryptException(message, cause, context, possibleReason);
        }
        return base64EncryptedString;
    }

    /**
     * Private Decrypting Method.
     */

    public static String decrypt(String encryptedText, String secretKey) throws CantDecryptException {


        String base64EncryptedString = "";

        try {
            byte[] message = Base64.decode(encryptedText.getBytes(CHARSET_NAME), 1);
            MessageDigest md = MessageDigest.getInstance(DIGEST_ALGORITHM);
            byte[] digestOfPassword = md.digest(secretKey.getBytes(CHARSET_NAME));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
            SecretKey key = new SecretKeySpec(keyBytes, KEYSPEC_ALGORITHM);

            Cipher decipher = Cipher.getInstance(KEYSPEC_ALGORITHM);
            decipher.init(Cipher.DECRYPT_MODE, key);

            byte[] plainText = decipher.doFinal(message);

            base64EncryptedString = new String(plainText, CHARSET_NAME);
            return base64EncryptedString;

        } catch (Exception ex) {
            String message = CantEncryptException.DEFAULT_MESSAGE;
            FermatException cause = FermatException.wrapException(ex);
            String context = "Encription Digest Algorithm: " + DIGEST_ALGORITHM;
            context += CantDecryptException.CONTEXT_CONTENT_SEPARATOR;
            context += "Encription KeySpec Algorithm: " + KEYSPEC_ALGORITHM;
            context += CantDecryptException.CONTEXT_CONTENT_SEPARATOR;
            context += "Charset: " + CHARSET_NAME;
            String possibleReason = "This is most likely to happen due to a bad Secret Key passing";
            throw  new CantDecryptException(message, cause, context, possibleReason);

        }

    }

}
