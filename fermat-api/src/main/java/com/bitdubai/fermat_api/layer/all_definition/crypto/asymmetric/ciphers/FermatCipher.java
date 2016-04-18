/*
 * @#FermatCipher.java - 2016
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ciphers;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * The Class <code>com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ciphers.FermatCipher</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 17/04/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public abstract class FermatCipher {

    /**
     * Represent the base64Decoder
     */
    private BASE64Decoder base64Decoder;

    /**
     * Represent the base64Encoder
     */
    private BASE64Encoder base64Encoder;

    /**
     * Constructor
     */
    public FermatCipher(){
        base64Decoder = new BASE64Decoder();
        base64Encoder = new BASE64Encoder();
    }

    /**
     * Convert a byte array into a hexadecimal format string
     * @param bytes
     * @return String
     * @throws Exception
     */
    public String convertHexString(byte[] bytes) throws Exception {
        String result = "";
        for (int i=0; i < bytes.length; i++) {
            result += Integer.toString(( bytes[i] & 0xff ) + 0x100, 16).substring( 1 );
        }
        return result;
    }

    /**
     * Convert a hexadecimal format string into a byte array
     *
     * @param string
     * @return byte[]
     */
    public byte[] convertHexStringToByteArray(String string) {
        int len = string.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(string.charAt(i), 16) << 4) + Character.digit(string.charAt(i+1), 16));
        }
        return data;
    }

    /**
     * Get the Base64Decoder value
     *
     * @return Base64Decoder
     */
    public BASE64Decoder getBase64Decoder() {
        return base64Decoder;
    }

    /**
     * Get the Base64Encoder value
     *
     * @return Base64Encoder
     */
    public BASE64Encoder getBase64Encoder() {
        return base64Encoder;
    }

    /**
     *  Method that encrypt a plaint text and return a
     *  encrypted string
     *
     * @param pubKey
     * @param plaintTex
     * @return String encode hexadecimal format
     */
    public abstract String encrypt(String pubKey, String plaintTex) throws Exception;

    /**
     *  Method that receive a encrypted hexadecimal format string to
     *  decrypted
     *
     * @param privateKey
     * @param encryptedTex
     * @return String
     */
    public abstract String decrypt(String privateKey, String encryptedTex) throws Exception;


}
