/*
 * @#FermatCipher.java - 2016
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ciphers;


import java.io.UnsupportedEncodingException;
import java.security.PublicKey;

/**
 * The Class <code>com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ciphers.FermatCipher</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 17/04/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface FermatCipher {

    /**
     * Convert the byte array into a string
     * encode in base 64 charset UTF-8
     *
     * @param data
     * @return String
     */
    String encode(byte[] data);

    /**
     * Convert the string in base 64 charset UTF-8 into
     * a byte array
     *
     * @param data
     * @return byte[]
     */
    byte[] decode(String data) throws UnsupportedEncodingException;

    /**
     *  Method that encrypt a plaint text and return a
     *  encrypted string
     *
     * @param pubKey
     * @param plaintTex
     * @return String encode hexadecimal format
     */
    String encrypt(String pubKey, String plaintTex) throws Exception;

    /**
     *  Method that receive a encrypted hexadecimal format string to
     *  decrypted
     *
     * @param privateKey
     * @param encryptedTex
     * @return String
     */
    String decrypt(String privateKey, String encryptedTex) throws Exception;

    /**
     * Method that get a PublicKey object from string base 64
     *
     * @param keyStringBase64
     * @return PublicKey
     * @throws Exception
     */
    PublicKey getPublicKeyFromString(String keyStringBase64) throws Exception;

}
