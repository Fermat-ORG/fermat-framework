package com.bitdubai.fermat_tky_plugin.layer.external_api.tokenly.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_tky_api.all_definitions.enums.TokenlyRequestMethod;
import com.bitdubai.fermat_tky_api.all_definitions.interfaces.User;
import com.bitdubai.fermat_tky_plugin.layer.external_api.tokenly.developer.bitdubai.version_1.exceptions.CantGenerateTokenlyAuthSignatureException;


import org.bouncycastle.util.encoders.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 23/03/16.
 */
public class TokenlyAuthenticationComponentGenerator {

    private static final String ENCODE_ALGORITHM = "HmacSHA256";
    private static final String ENCODE_KEY_CHARSET_NAME = "UTF-8";
    private static final String TEXT_CHARSET_NAME = "ASCII";

    public static String generateTokenlyAuthSignature(
            User user,
            String requestUrl,
            TokenlyRequestMethod tokenlyRequestMethod) throws
            CantGenerateTokenlyAuthSignatureException {
        try{
            //X-Tokenly-Auth-Api-Token
            String apiToken = user.getApiToken();
            //X-Tokenly-Auth-Nonce
            long nonce = System.currentTimeMillis();
            //long nonce=1457530047;
            //API secret key
            String apiSecretKey = user.getApiSecretKey();
            //Request message (in this version I don't use parameters)
            String requestMessage = "{"+tokenlyRequestMethod.getCode()+"}\n{"+requestUrl+"}\n{}\n{"+apiToken+"}\n{"+nonce+"}";
            //Calculate Sha-256 HMAC
            String shaEncodedMessage = calculateSha256HMAC(
                    requestMessage,
                    apiSecretKey);
            //Base64 encode
            byte[] bytes = shaEncodedMessage.getBytes();
            String authSignature = Base64.toBase64String(bytes);
            return authSignature;
        } catch (NoSuchAlgorithmException e) {
            throw new CantGenerateTokenlyAuthSignatureException(
                    e,
                    "Generating Auth Signature",
                    ENCODE_ALGORITHM+" is not recognized");
        } catch (InvalidKeyException e) {
            throw new CantGenerateTokenlyAuthSignatureException(
                    e,
                    "Generating Auth Signature",
                    "Invalid key is used");
        } catch (UnsupportedEncodingException e) {
            throw new CantGenerateTokenlyAuthSignatureException(
                    e,
                    "Generating Auth Signature",
                    "Cannot support encoding");
        }
    }

    /**
     * This method returns an encoded string using Sha-256 HMAC
     * @param text
     * @param encodeKey
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    private static String calculateSha256HMAC(
            String text,
            String encodeKey) throws
            UnsupportedEncodingException,
            NoSuchAlgorithmException,
            InvalidKeyException {

        //Generate encode key string
        SecretKeySpec secretKeySpec = new SecretKeySpec(
                (encodeKey).getBytes(ENCODE_KEY_CHARSET_NAME),
                ENCODE_ALGORITHM);
        Mac mac = Mac.getInstance(ENCODE_ALGORITHM);
        mac.init(secretKeySpec);
        //Encode text
        byte[] bytes = mac.doFinal(text.getBytes(TEXT_CHARSET_NAME));
        StringBuffer hash = new StringBuffer();
        String hex;
        for (byte element : bytes) {
            hex = Integer.toHexString(0xFF & element);
            if (hex.length() == 1) {
                hash.append('0');
            }
            hash.append(hex);
        }
        String encoded = hash.toString();
        return encoded;
    }

}
