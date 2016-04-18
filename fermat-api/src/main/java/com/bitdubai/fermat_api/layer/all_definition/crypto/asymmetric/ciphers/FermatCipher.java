/*
 * @#FermatCipher.java - 2016
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ciphers;


import com.google.common.io.BaseEncoding;

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
     * Represent the HEXADECIMAL_ARRAY
     */
    protected final static char[] HEXADECIMAL_ARRAY = "0123456789ABCDEF".toCharArray();

    /**
     * Convert a byte array into a hexadecimal format string
     * @param bytes
     * @return String
     * @throws Exception
     */
    public String convertHexString(byte[] bytes) throws Exception {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEXADECIMAL_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEXADECIMAL_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

    /**
     * Convert a hexadecimal format string into a byte array
     *
     * @param string
     * @return byte[]
     */
    public byte[] convertHexStringToByteArray(String string) {
        /*int len = string.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(string.charAt(i), 16) << 4) + Character.digit(string.charAt(i+1), 16));
        }*/
        return BaseEncoding.base16().decode(string);
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
