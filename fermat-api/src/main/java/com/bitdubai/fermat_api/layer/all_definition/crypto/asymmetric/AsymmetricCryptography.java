package com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.interfaces.KeyPair;
import com.bitdubai.fermat_api.layer.all_definition.crypto.util.RandomBigIntegerGenerator;

import org.bitcoinj.core.ECKey;

import java.math.BigInteger;

public class AsymmetricCryptography {
    private final static AsymmetricCipher cipher = new AsymmetricCipher();
    private final static AsymmetricKeyCreator keyCreator = new AsymmetricKeyCreator();
    private final static RandomBigIntegerGenerator randomizer = new RandomBigIntegerGenerator();

    public static String createMessageSignature(final String messageHash, final String hexPrivateKey) throws IllegalArgumentException {
/*checkStringArgument(messageHash);
checkStringArgument(hexPrivateKey);
PrivateKey privateKey = new AsymmetricPrivateKey(new BigInteger(hexPrivateKey,16));
BigInteger message = new BigInteger(messageHash, 16);
Signature signature = new AsymmetricSignature(privateKey, message, randomizer.generateRandom());*/
        return "";
    }

    public static boolean verifyMessageSignature(final String signature, final String encryptedMessage, final String hexPublicKey) throws IllegalArgumentException {
/*checkStringArgument(signature);
checkStringArgument(encryptedMessage);
checkStringArgument(hexPublicKey);
Signature ecSignature = new AsymmetricSignature(signature);
BigInteger messageHash = new BigInteger(encryptedMessage, 16);
PublicKey publicKey = new AsymmetricPublicKey(hexPublicKey);
return ecSignature.verifyMessageSignature(messageHash, publicKey); */
        return true;
    }

    public static String encryptMessagePublicKey(final String plainMessage, final String hexPublicKey) throws IllegalArgumentException {
/*checkStringArgument(plainMessage);
checkStringArgument(hexPublicKey);
AsymmetricPublicKey publicKey = new AsymmetricPublicKey(hexPublicKey);
return cipher.encryptWithPublicKey(plainMessage, publicKey);*/
        return plainMessage;
    }

    public static String decryptMessagePrivateKey(final String encryptedMessage, final String hexPrivateKey) throws IllegalArgumentException {
/*checkStringArgument(encryptedMessage);
checkStringArgument(hexPrivateKey);
AsymmetricPrivateKey privateKey = new AsymmetricPrivateKey(new BigInteger(hexPrivateKey,16));
try{
return cipher.decryptWithPrivateKey(encryptedMessage, privateKey);
} catch(Exception ex){
System.out.println("ex = "+ex);
throw new IllegalArgumentException(ex.getMessage());
}*/
        return encryptedMessage;
    }

    public static String createPrivateKey() {
        return keyCreator.createPrivateKey().toString();
    }

    public static String derivePublicKey(final String hexPrivateKey) throws IllegalArgumentException {
        checkStringArgument(hexPrivateKey);
// AsymmetricPrivateKey privateKey = new AsymmetricPrivateKey(new BigInteger(hexPrivateKey, 16));
// AsymmetricKeyCreator keyCreator = new AsymmetricKeyCreator();
// return keyCreator.createPublicKey(privateKey).toString();
        ECKey key = ECKey.fromPrivate(new BigInteger(hexPrivateKey, 16), false);
        return key.getPublicKeyAsHex().toUpperCase();
    }

    public static String generatePublicAddress(final String hexPublicKey) throws IllegalArgumentException {
        checkStringArgument(hexPublicKey);
        AsymmetricPublicKey publicKey = new AsymmetricPublicKey(hexPublicKey);
        return keyCreator.createPublicAddress(publicKey);
    }

    public static String generateTestAddress(final String hexPublicKey) throws IllegalArgumentException {
        checkStringArgument(hexPublicKey);
        AsymmetricPublicKey publicKey = new AsymmetricPublicKey(hexPublicKey);
        return keyCreator.createTestAddress(publicKey);
    }

    private static void checkStringArgument(final String argument) {
        if (argument == null || argument.isEmpty())
            throw new IllegalArgumentException();
    }

    /**
     * Generate a new ECCKeyPair
     * <p/>
     * privateKey + publicKey
     *
     * @return ECCKeyPair
     */
    public static ECCKeyPair generateECCKeyPair() {
        String privateKey = createPrivateKey();
        String publicKey = derivePublicKey(privateKey);
        return new ECCKeyPair(privateKey, publicKey);
    }

    public static KeyPair createKeyPair(final String privateKey) {
        checkStringArgument(privateKey);
        String publicKey = derivePublicKey(privateKey);
        return new ECCKeyPair(privateKey, publicKey);
    }
}