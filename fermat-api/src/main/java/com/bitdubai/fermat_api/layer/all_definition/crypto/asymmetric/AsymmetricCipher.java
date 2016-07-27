package com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.interfaces.PrivateKey;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.interfaces.PublicKey;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.SecureRandom;

//import org.bouncycastle.jcajce.provider.asymmetric.ec.IESCipher;
//import org.bouncycastle.jce.interfaces.ECPrivateKey;
//import org.bouncycastle.jce.interfaces.ECPublicKey;
//import org.bouncycastle.jce.provider.BouncyCastleProvider;
//import org.bouncycastle.jce.provider.JCEECPrivateKey;
//import org.bouncycastle.jce.provider.JCEECPublicKey;
//import org.bouncycastle.util.encoders.Hex;
//todo: mover a otra api: Mati
public class AsymmetricCipher {

    private SecureRandom randomizer;

    private final static Charset charsetUTF8 = Charset.forName("UTF-8");
//	private final static IESCipher cipher = new IESCipher.ECIES();

    public AsymmetricCipher() {
        this(new SecureRandom());
    }

    public AsymmetricCipher(SecureRandom randomizer) {
        this.randomizer = randomizer;
//		Security.addProvider(new BouncyCastleProvider());
    }

    public String encryptWithPublicKey(final String message, final PublicKey publicKey) {
        String encrypted = null;
        try {
//			byte[] messageBytes = message.getBytes(charsetUTF8);
//			ECPublicKey ecPublicKey = new JCEECPublicKey(publicKey);
//			cipher.engineInit(Cipher.ENCRYPT_MODE, ecPublicKey, randomizer);
//			byte[] encryptedBytes = cipher.engineDoFinal(messageBytes, 0, messageBytes.length);
//			encrypted = Hex.toHexString(encryptedBytes);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return encrypted;
    }

    public String decryptWithPrivateKey(final String encryptedMessage, final PrivateKey privateKey) {
        try {
            BigInteger encrypted = new BigInteger(encryptedMessage, 16);
            byte[] encryptedBytes = encrypted.toByteArray();

//			ECPrivateKey ecPrivateKey = new JCEECPrivateKey(privateKey);
//			cipher.engineInit(Cipher.DECRYPT_MODE, ecPrivateKey, randomizer);
//			byte[] decryptedBytes = cipher.engineDoFinal(encryptedBytes, 0, encryptedBytes.length);
//			return new String(decryptedBytes, charsetUTF8);
            return null;
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
    }

}
