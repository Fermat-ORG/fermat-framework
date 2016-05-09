package com.bitdubai.fermat_tky_plugin.layer.external_api.tokenly.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.util.Base64;
import com.bitdubai.fermat_tky_api.all_definitions.enums.TokenlyRequestMethod;
import com.bitdubai.fermat_tky_api.all_definitions.interfaces.User;
import com.bitdubai.fermat_tky_plugin.layer.external_api.tokenly.developer.bitdubai.version_1.exceptions.CantGenerateTokenlyAuthSignatureException;

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

    /**
     * This method returns a String that represents a Auth-Signature for Tokenly protected API as is
     * described in:
     * <html>
     *     <a href='https://github.com/tokenly/hmac-auth/blob/master/README.md'>
     *         https://github.com/tokenly/hmac-auth/blob/master/README.md
     *     </a>
     * </html>
     * Please check this document to understand the signature generation.
     * Note: you must to use a converted nonce according the encryption method according the
     * document present in this comment. Please, use convertTimestamp(long timestamp) method
     * to generate the convertedNonce.
     * @param user
     * @param requestUrl
     * @param convertedNonce
     * @param tokenlyRequestMethod
     * @return
     * @throws CantGenerateTokenlyAuthSignatureException
     */
    public static String generateTokenlyAuthSignature(
            User user,
            String requestUrl,
            long convertedNonce,
            TokenlyRequestMethod tokenlyRequestMethod) throws
            CantGenerateTokenlyAuthSignatureException {
        try{
            //X-Tokenly-Auth-Api-Token
            String apiToken = user.getApiToken();
            //long nonce=1457530047;
            //API secret key
            String apiSecretKey = user.getApiSecretKey();
            //Request message (in this version I don't use parameters)
            String requestMessage = tokenlyRequestMethod.getCode()+"\n"+requestUrl+"\n{}\n"+apiToken+"\n"+convertedNonce;
            //Calculate Sha-256 HMAC
            byte[] shaEncodedMessage = calculateSha256HMAC(
                    requestMessage,
                    apiSecretKey);
            //Base64 encode
            String authSignature = Base64.encodeToString(shaEncodedMessage, Base64.DEFAULT);
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
    private static byte[] calculateSha256HMAC(
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
        return bytes;
    }

    /**
     * This method convert the timestamp according the encryption method described in:
     * <html>
     *     <a href='https://github.com/tokenly/hmac-auth/blob/master/README.md'>
     *         https://github.com/tokenly/hmac-auth/blob/master/README.md
     *     </a>
     * </html>
     * @param timestamp
     * @return
     */
    public static long convertTimestamp(long timestamp){
        /**
         * I decided to implement the nonce conversion in a separated method because this can be
         * change in the future.
         */
        return timestamp/1000L;
    }

}
