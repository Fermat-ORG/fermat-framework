/*
 * @#FermatBouncyCastleCipher.java - 2016
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ciphers;


import org.bouncycastle.asn1.pkcs.RSAPublicKey;
import org.bouncycastle.crypto.AsymmetricBlockCipher;
import org.bouncycastle.crypto.encodings.PKCS1Encoding;
import org.bouncycastle.crypto.engines.RSAEngine;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.util.PrivateKeyFactory;
import org.bouncycastle.crypto.util.PublicKeyFactory;
import org.bouncycastle.util.encoders.Base64;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;


/**
 * The Class <code>com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ciphers.FermatBouncyCastleCipher</code>
 * use the BouncyCastle implementation<p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 17/04/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class FermatBouncyCastleCipher implements FermatCipher {

    /**
     * (non-javadoc)
     * @see FermatCipher#encrypt(String, String)
     */
    @Override
    public String encrypt(String pubKey, String plaintTex) throws Exception{

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.reset();
        AsymmetricKeyParameter publicKey = PublicKeyFactory.createKey(decode(pubKey));
        AsymmetricBlockCipher asymmetricBlockCipher = new RSAEngine();
        asymmetricBlockCipher = new PKCS1Encoding(asymmetricBlockCipher);
        asymmetricBlockCipher.init(true, publicKey);

        byte[] messageBytes = plaintTex.getBytes("UTF-8");

        int i = 0;
        int blockSize = asymmetricBlockCipher.getInputBlockSize();
        while (i < messageBytes.length){

            if (i + blockSize > messageBytes.length) {
                blockSize = messageBytes.length - i;
            }

            byteArrayOutputStream.write( asymmetricBlockCipher.processBlock(messageBytes, i, blockSize));
            i += asymmetricBlockCipher.getInputBlockSize();
        }

        return encode(byteArrayOutputStream.toByteArray());
    }

    /**
     * (non-javadoc)
     * @see FermatCipher#decrypt(String, String)
     */
    @Override
    public String decrypt(String privateKey, String encryptedTex) throws Exception{

        StringBuilder decryptedMsj = new StringBuilder();

        AsymmetricKeyParameter privateKeyParameter = PrivateKeyFactory.createKey(decode(privateKey));
        AsymmetricBlockCipher asymmetricBlockCipher = new RSAEngine();
        asymmetricBlockCipher = new PKCS1Encoding(asymmetricBlockCipher);
        asymmetricBlockCipher.init(false, privateKeyParameter);

        byte[] messageBytes = decode(encryptedTex);

        int i = 0;
        int blockSize = asymmetricBlockCipher.getInputBlockSize();
        while (i < messageBytes.length) {
            if (i + blockSize > messageBytes.length) {
                blockSize = messageBytes.length - i;
            }
            byte[] hexEncodedCipher = asymmetricBlockCipher.processBlock(messageBytes, i, blockSize);
            decryptedMsj.append(new String(hexEncodedCipher));
            i += asymmetricBlockCipher.getInputBlockSize();
        }

        return new String(decryptedMsj.toString().getBytes(), "UTF-8");

    }

    /**
     * (non-javadoc)
     * @see FermatCipher#encrypt(String, String)
     */
    public PublicKey getPublicKeyFromString(String keyStringBase64) throws Exception{

        byte[] publicKeyBytes = decode(keyStringBase64);
        byte[] exponentByte   = decode("AQAB");
        RSAPublicKeySpec pubKeySpec = new RSAPublicKeySpec(new BigInteger(publicKeyBytes), new BigInteger(exponentByte));
        BigInteger modulus = pubKeySpec.getModulus();
        BigInteger publicExponent = pubKeySpec.getPublicExponent();
        RSAPublicKeySpec keySpec = new RSAPublicKeySpec(modulus, publicExponent);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PublicKey generatedPublic = kf.generatePublic(keySpec);
        System.out.printf("Modulus: %X%n", modulus);
        System.out.printf("Public exponent: %d ... 17? Why?%n", publicExponent); // 17? OK.
        System.out.printf("See, Java class result: %s, is RSAPublicKey: %b%n", generatedPublic.getClass().getName(), generatedPublic instanceof RSAPublicKey);

        return generatedPublic;
    }


    /**
     * (non-javadoc)
     * @see FermatCipher#encode(byte[])
     */
    public String encode(byte[] data){

        return Base64.toBase64String(data);
    }

    /**
     * (non-javadoc)
     * @see FermatCipher#decode(String)
     */
    public byte[] decode(String data) throws UnsupportedEncodingException {
        return Base64.decode(data.getBytes("UTF-8"));
    }

}
