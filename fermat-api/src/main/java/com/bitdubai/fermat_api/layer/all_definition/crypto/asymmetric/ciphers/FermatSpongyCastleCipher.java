/*
 * @#FermatSpongyCastleCipher.java - 2016
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ciphers;

import org.spongycastle.crypto.AsymmetricBlockCipher;
import org.spongycastle.crypto.encodings.PKCS1Encoding;
import org.spongycastle.crypto.engines.RSAEngine;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.crypto.util.PrivateKeyFactory;
import org.spongycastle.crypto.util.PublicKeyFactory;
import org.spongycastle.jce.provider.BouncyCastleProvider;
import org.spongycastle.util.encoders.Base64;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * The Class <code>com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ciphers.FermatSpongyCastleCipher</code>
 * use the SpongyCastle implementation that is specially for Android Operating System <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 17/04/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class FermatSpongyCastleCipher implements FermatCipher {

    static {
        Security.addProvider(new BouncyCastleProvider());
        Security.insertProviderAt(new BouncyCastleProvider(), 1);
    }

    /**
     * Represent the keyFactory
     */
    private KeyFactory keyFactory;

    /**
     * Represent the keyPairGenerator
     */
    private KeyPairGenerator keyPairGenerator;

    /**
     * Constructor
     * @throws NoSuchProviderException
     * @throws NoSuchAlgorithmException
     */
    public FermatSpongyCastleCipher() throws NoSuchProviderException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        keyFactory       = KeyFactory.getInstance(ALGORITHM, BouncyCastleProvider.PROVIDER_NAME);
        keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM, BouncyCastleProvider.PROVIDER_NAME);
        keyPairGenerator.initialize(KEY_SIZE, SecureRandom.getInstance(DIGEST_SHA1PRNG));
    }

    /**
     * (non-javadoc)
     * @see FermatCipher#generateKeyPair()
     */
    @Override
    public KeyPair generateKeyPair() throws Exception {

        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        return keyPair;
    }

    /**
     * (non-javadoc)
     * @see FermatCipher#readPublicKey(String)
     */
    @Override
    public PublicKey readPublicKey(String keyStr) throws Exception {

        X509EncodedKeySpec x509ks = new X509EncodedKeySpec(this.decode(keyStr));
        return keyFactory.generatePublic(x509ks);
    }

    /**
     * (non-javadoc)
     * @see FermatCipher#readPrivateKey(String)
     */
    @Override
    public PrivateKey readPrivateKey(String keyStr) throws Exception {

        PKCS8EncodedKeySpec p8ks = new PKCS8EncodedKeySpec(decode(keyStr));
        return keyFactory.generatePrivate(p8ks);

    }

    /**
     * (non-javadoc)
     * @see FermatCipher#createPublicKeyFromPrivateKey(PrivateKey)
     */
    @Override
    public String createPublicKeyFromPrivateKey(PrivateKey privateKey) throws InvalidKeySpecException, NoSuchAlgorithmException {

        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        RSAPrivateKeySpec privateKeySpec = keyFactory.getKeySpec(privateKey, RSAPrivateKeySpec.class);
        RSAPublicKeySpec keySpec = new RSAPublicKeySpec(privateKeySpec.getModulus(), BigInteger.valueOf(65537));
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return encode(publicKey.getEncoded());

       /* ECPrivateKeySpec ecPrivateKeySpec =  keyFactory.getKeySpec(privateKey, ECPrivateKeySpec.class);
        java.security.spec.ECPoint w = new java.security.spec.ECPoint(ecPrivateKeySpec.getParams().getGenerator().getAffineX(), ecPrivateKeySpec.getParams().getGenerator().getAffineY());
        ECPublicKeySpec ecPublicKeySpec = new ECPublicKeySpec(w, ecPrivateKeySpec.getParams());
        PublicKey publicKey = keyFactory.generatePublic(ecPublicKeySpec);
        return encode(publicKey.getEncoded());*/
    }


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

        byte[] messageBytes = plaintTex.getBytes(UTF8_CHARSET);

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

        return new String(decryptedMsj.toString().getBytes(), UTF8_CHARSET);

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
        return Base64.decode(data.getBytes(UTF8_CHARSET));
    }

}
