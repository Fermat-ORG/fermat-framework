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

/**
 * The Class <code>com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ciphers.FermatSpongyCastleCipher</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 17/04/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class FermatSpongyCastleCipher extends FermatCipher {

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
        asymmetricBlockCipher = new PKCS1Encoding(asymmetricBlockCipher);
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

}
