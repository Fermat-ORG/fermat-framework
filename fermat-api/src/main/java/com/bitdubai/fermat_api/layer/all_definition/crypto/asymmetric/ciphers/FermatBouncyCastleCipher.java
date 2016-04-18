/*
 * @#FermatBouncyCastleCipher.java - 2016
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ciphers;

import com.google.common.base.Stopwatch;

import org.bouncycastle.crypto.AsymmetricBlockCipher;
import org.bouncycastle.crypto.encodings.PKCS1Encoding;
import org.bouncycastle.crypto.engines.RSAEngine;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.util.PrivateKeyFactory;
import org.bouncycastle.crypto.util.PublicKeyFactory;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.Security;


/**
 * The Class <code>com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ciphers.FermatBouncyCastleCipher</code>
 * use the BouncyCastle implementation<p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 17/04/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class FermatBouncyCastleCipher extends FermatCipher {

    /**
     * (non-javadoc)
     * @see FermatCipher#encrypt(String, String)
     */
    @Override
    public String encrypt(String pubKey, String plaintTex) throws Exception{

        String encryptedData = "";
        AsymmetricKeyParameter publicKey = PublicKeyFactory.createKey(getBase64Decoder().decodeBuffer(pubKey));
        AsymmetricBlockCipher asymmetricBlockCipher = new RSAEngine();
        asymmetricBlockCipher = new PKCS1Encoding(asymmetricBlockCipher);
        asymmetricBlockCipher.init(true, publicKey);

        byte[] messageBytes = plaintTex.getBytes();
        int i = 0;
        int blockSize = asymmetricBlockCipher.getInputBlockSize();
        while (i < messageBytes.length){

            if (i + blockSize > messageBytes.length) {
                blockSize = messageBytes.length - i;
            }

            byte[] hexEncodedCipher = asymmetricBlockCipher.processBlock(messageBytes, i, blockSize);
            encryptedData = encryptedData + convertHexString(hexEncodedCipher);
            i += asymmetricBlockCipher.getInputBlockSize();
        }

        return encryptedData;
    }

    /**
     * (non-javadoc)
     * @see FermatCipher#decrypt(String, String)
     */
    @Override
    public String decrypt(String privateKey, String encryptedTex) throws Exception{

        String decryptedMsj = "";

        AsymmetricKeyParameter privateKeyParameter = PrivateKeyFactory.createKey(getBase64Decoder().decodeBuffer(privateKey));
        AsymmetricBlockCipher asymmetricBlockCipher = new RSAEngine();
        asymmetricBlockCipher = new org.bouncycastle.crypto.encodings.PKCS1Encoding(asymmetricBlockCipher);
        asymmetricBlockCipher.init(false, privateKeyParameter);

        byte[] messageBytes = convertHexStringToByteArray(encryptedTex);

        int i = 0;
        int blockSize = asymmetricBlockCipher.getInputBlockSize();
        while (i < messageBytes.length) {
            if (i + blockSize > messageBytes.length) {
                blockSize = messageBytes.length - i;
            }
            byte[] hexEncodedCipher = asymmetricBlockCipher.processBlock(messageBytes, i, blockSize);
            decryptedMsj = decryptedMsj + new String(hexEncodedCipher);
            i += asymmetricBlockCipher.getInputBlockSize();
        }

        return decryptedMsj;

    }

    public static void main(String [] args){


        try {

            FermatBouncyCastleCipher fermatBouncyCastleCipher = new FermatBouncyCastleCipher();

            Stopwatch stopwatch = Stopwatch.createStarted();
            Security.insertProviderAt(new BouncyCastleProvider(), 1);
            //String msj = "Hello world!";
            String msj = "Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!";
            //System.out.println("msj = " + msj);

            // Create the public and private keys
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", "BC");
            generator.initialize(1024,random);

            KeyPair pair = generator.generateKeyPair();
            Key pubKey = pair.getPublic();
            Key privKey = pair.getPrivate();

            System.out.println("publicKey : " + fermatBouncyCastleCipher.getBase64Encoder().encode(pubKey.getEncoded()));
            System.out.println(" ----------------------------------------------- ");
            System.out.println("privateKey : " + fermatBouncyCastleCipher.getBase64Encoder().encode(privKey.getEncoded()));

            String encryptedData = fermatBouncyCastleCipher.encrypt(fermatBouncyCastleCipher.getBase64Encoder().encode(pubKey.getEncoded()), msj);

            System.out.println(" ----------------------------------------------- ");
            System.out.println(" encryptedData : " + encryptedData);

            System.out.println(" ----------------------------------------------- ");

            String decryptedData = fermatBouncyCastleCipher.decrypt(fermatBouncyCastleCipher.getBase64Encoder().encode(privKey.getEncoded()), encryptedData);
            System.out.println(" decryptedData : " + decryptedData);

            System.out.println(stopwatch.stop());

        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
