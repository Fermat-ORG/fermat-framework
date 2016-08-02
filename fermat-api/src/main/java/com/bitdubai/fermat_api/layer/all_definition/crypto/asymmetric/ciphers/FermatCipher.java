/*
 * @#FermatCipher.java - 2016
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ciphers;


import java.io.UnsupportedEncodingException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;

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
     * Represent the UTF8_CHARSET
     */
    String UTF8_CHARSET = "UTF-8";

    /**
     * Represent the ALGORITHM
     */
    String ALGORITHM = "RSA";

    /**
     * Represent the DIGEST_SHA1
     */
    String DIGEST_SHA1 = "SHA1withRSA";

    /**
     * Represent the DIGEST_SHA1PRNG
     */
    String DIGEST_SHA1PRNG = "SHA1PRNG";

    /**
     * Represent the CURVE
     */
    String CURVE = "secp256k1";

    /**
     * Represent the KEY_SIZE
     */
    int KEY_SIZE = 512;

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
     * Generate a new key pair
     *
     * @return KeyPair
     * @throws Exception
     */
    KeyPair generateKeyPair() throws Exception;

    /**
     * Read a public key object from string key base64 encode
     *
     * @param keyStr
     * @return PublicKey
     * @throws Exception
     */
    PublicKey readPublicKey(String keyStr) throws Exception;

    /**
     * Read a private key object from string key base64 encode
     *
     * @param keyStr
     * @return PrivateKey
     * @throws Exception
     */
    PrivateKey readPrivateKey(String keyStr) throws Exception;

    /**
     * Create a public key derivable from private key
     *
     * @param privateKey
     * @return String
     * @throws InvalidKeySpecException
     * @throws NoSuchAlgorithmException
     */
    String createPublicKeyFromPrivateKey(PrivateKey privateKey) throws InvalidKeySpecException, NoSuchAlgorithmException;

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

}
