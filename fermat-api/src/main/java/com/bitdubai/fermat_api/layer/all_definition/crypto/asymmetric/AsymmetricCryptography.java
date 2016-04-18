package com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric;


import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ciphers.FermatBouncyCastleCipher;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ciphers.FermatCipher;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ciphers.FermatSpongyCastleCipher;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.interfaces.KeyPair;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

public class AsymmetricCryptography {

	/**
	 * Represent the RSA_ALGORITHM
	 */
	private static final String RSA_ALGORITHM = "RSA";

    /**
     * Represent the KEY_SIZE
     */
    private static final int KEY_SIZE = 1024;

    /**
     * Represent the OS
     */
    private final static String OS = System.getProperty("os.name");

    /**
     * Represent the fermatCipher
     */
    private final static FermatCipher fermatCipher = getFermatCipher();

    /**
     * Configure the Security Provider
     */
    static {
        Security.insertProviderAt(getSecurityProvider(), 1);
    }

    /**
     * Get the FermatCipher
     * @return FermatCipher
     */
    private static FermatCipher getFermatCipher(){
        if (OS.equals("Linux")){
            return  new FermatBouncyCastleCipher();
        }else {
            return new FermatSpongyCastleCipher();
        }
    }

    /**
     * Get the Security provider
     * @return Provider
     */
    private static Provider getSecurityProvider(){
        if (OS.equals("Linux")){
            return  new org.bouncycastle.jce.provider.BouncyCastleProvider();
        }else {
            return new org.bouncycastle.jce.provider.BouncyCastleProvider();
        }
    }

    /**
     * Create a key pair whit RSA algorithm
     * @return KeyPair
     */
    public static java.security.KeyPair createNewKeyPair(){

        try {

            SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
            KeyPairGenerator generator = KeyPairGenerator.getInstance(RSA_ALGORITHM, getSecurityProvider().getName());
            generator.initialize(KEY_SIZE, random);
            return generator.generateKeyPair();

        }catch (Exception e){
            throw new IllegalArgumentException(e);
        }

    }

    /**
     * Get a private key object from string key base64 encode
     * @param keyStringBase64
     * @return PrivateKey
     * @throws Exception
     */
    public static PrivateKey getPrivateKeyFromString(String keyStringBase64) throws Exception {
        byte[] clear = fermatCipher.getBase64Decoder().decodeBuffer(keyStringBase64);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(clear);
        KeyFactory fact = KeyFactory.getInstance(RSA_ALGORITHM);
        PrivateKey privateKey = fact.generatePrivate(keySpec);
        Arrays.fill(clear, (byte) 0);
        return privateKey;
    }

    /**
     * Get a public key object from string key base64 encode
     * @param keyStringBase64
     * @return PublicKey
     * @throws Exception
     */
    public static PublicKey getPublicKeyFromString(String keyStringBase64) throws Exception {
        byte[] data = fermatCipher.getBase64Decoder().decodeBuffer(keyStringBase64);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(data);
        KeyFactory fact = KeyFactory.getInstance(RSA_ALGORITHM);
        return fact.generatePublic(spec);
    }

    /**
     * Create a private key
     * @return String
     */
    public static String createPrivateKey(){

        try {

            java.security.KeyPair keyPair = createNewKeyPair();
            return fermatCipher.getBase64Encoder().encode(keyPair.getPrivate().getEncoded());

        }catch (Exception e){
            throw new IllegalArgumentException(e);
        }

    }


    /**
     * Create a public key derived from private key
     * @param privateKeyString
     * @return String
     * @throws IllegalArgumentException
     */
    public static String derivePublicKey(final String privateKeyString) throws IllegalArgumentException {

        try {
            checkStringArgument(privateKeyString);

            KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
            RSAPrivateKeySpec privateKeySpec = keyFactory.getKeySpec(getPrivateKeyFromString(privateKeyString), RSAPrivateKeySpec.class);
            RSAPublicKeySpec keySpec = new RSAPublicKeySpec(privateKeySpec.getModulus(), BigInteger.valueOf(65537));
            PublicKey publicKey = keyFactory.generatePublic(keySpec);
            return fermatCipher.getBase64Encoder().encode(publicKey.getEncoded());

        }catch (Exception e){
            e.printStackTrace();
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Create a message signature
     *
     * @param privateKey
     * @param message
     * @return String
     * @throws IllegalArgumentException
     */
    public static String createMessageSignature(final String privateKey, final String message) throws IllegalArgumentException {

        checkStringArgument(privateKey);
        checkStringArgument(message);

        try {

            Signature signature = Signature.getInstance("SHA256withRSA", getSecurityProvider().getName());
            signature.initSign(getPrivateKeyFromString(privateKey));
            signature.update(fermatCipher.getBase64Decoder().decodeBuffer(message));
            return fermatCipher.getBase64Encoder().encode(signature.sign());

        }catch (Exception e) {

            e.printStackTrace();
            throw new IllegalArgumentException(e);

        }
    }

    /**
     * Verify is a message signature are valid
     *
     * @param signatureString
     * @param encryptedMessage
     * @param publicKey
     *
     * @return boolean
     * @throws IllegalArgumentException
     */
	public static boolean verifyMessageSignature(final String signatureString, final String encryptedMessage, final String publicKey){

        checkStringArgument(signatureString);
        checkStringArgument(encryptedMessage);
        checkStringArgument(publicKey);

        try {

            Signature signature = Signature.getInstance("SHA256withRSA", getSecurityProvider().getName());
            signature.initSign(getPrivateKeyFromString(publicKey));
            return signature.verify(fermatCipher.getBase64Decoder().decodeBuffer(encryptedMessage));

        }catch (Exception e){
            e.printStackTrace();
            throw new IllegalArgumentException(e);
        }
	}

    /**
     * Method that encrypt a plaint text and return a
     *  encrypted string
     *
     * @param plainMessage
     * @param publicKey
     * @return String encrypted hexadecimal format
     * @throws Exception
     */
	public static String encryptMessagePublicKey(final String plainMessage, final String publicKey) throws IllegalArgumentException {

        try {

            checkStringArgument(plainMessage);
            checkStringArgument(publicKey);
            return fermatCipher.encrypt(publicKey, plainMessage);

        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
	}

    /**
     *  Method that receive a encrypted hexadecimal format string to
     *  decrypted.
     *
     * @param encryptedMessage
     * @param privateKey
     * @return String
     * @throws Exception
     */
	public static String decryptMessagePrivateKey(final  String encryptedMessage, final String privateKey) throws IllegalArgumentException {

        try {

            checkStringArgument(encryptedMessage);
            checkStringArgument(privateKey);
           return fermatCipher.decrypt(privateKey, encryptedMessage);

        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
	}

    /**
     *
     * @param argument
     */
	private static void checkStringArgument(final String argument){
		if(argument == null || argument.isEmpty())
			throw new IllegalArgumentException();
	}

    /**
     * Generate a new ECCKeyPair
     *
     * privateKey + publicKey
     *
     * @return ECCKeyPair
     */
	public static ECCKeyPair generateECCKeyPair() throws IllegalArgumentException {

        try {

            java.security.KeyPair keyPair = createNewKeyPair();
            return new ECCKeyPair(fermatCipher.getBase64Encoder().encode(keyPair.getPrivate().getEncoded()), fermatCipher.getBase64Encoder().encode(keyPair.getPublic().getEncoded()));

        }catch (Exception e){
            throw new IllegalArgumentException(e);
        }
    }

    /**
     *
     * @param privateKey
     * @return KeyPair
     */
	public static KeyPair createKeyPair(final String privateKey) throws IllegalArgumentException {
		checkStringArgument(privateKey);
		String publicKey  = derivePublicKey(privateKey);
		return new ECCKeyPair(privateKey, publicKey);
	}
}
