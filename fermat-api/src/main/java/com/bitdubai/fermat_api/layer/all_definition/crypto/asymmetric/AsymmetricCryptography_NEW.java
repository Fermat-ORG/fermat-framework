package com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric;


import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ciphers.FermatBouncyCastleCipher;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ciphers.FermatCipher;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ciphers.FermatSpongyCastleCipher;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.interfaces.KeyPair;
import com.bitdubai.fermat_api.layer.all_definition.util.OperatingSystemCheck;


import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;

public class AsymmetricCryptography_NEW {

    static {

        if (OperatingSystemCheck.getOperatingSystemType() == OperatingSystemCheck.OperatingSystemType.Android){

            Security.addProvider(new org.spongycastle.jce.provider.BouncyCastleProvider());
            Security.insertProviderAt(new org.spongycastle.jce.provider.BouncyCastleProvider(), 1);

        }else {

            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            Security.insertProviderAt(new org.bouncycastle.jce.provider.BouncyCastleProvider(), 1);
        }

    }


    /**
     * Represent the fermatCipher
     */
    private FermatCipher fermatCipher;

    /**
     * Represent the instance
     */
    final static private AsymmetricCryptography instance = new AsymmetricCryptography();

    /**
     * Constructor
     */
    private AsymmetricCryptography_NEW(){
        super();

        try {

            if (OperatingSystemCheck.getOperatingSystemType() == OperatingSystemCheck.OperatingSystemType.Android){
                fermatCipher = new FermatBouncyCastleCipher();
            }else {
                fermatCipher =  new FermatSpongyCastleCipher();
            }

            System.out.println("AsymmetricCryptography - SecurityProvider Loaded = "+ Security.getProviders()[0]);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * Get the FermatCipher
     * @return FermatCipher
     */
    public static FermatCipher getFermatCipher(){
        return null; //instance.fermatCipher;
    }

    /**
     * Get a private key object from string key base64 encode
     * @param keyStringBase64
     * @return PrivateKey
     * @throws Exception
     */
    public static PrivateKey getPrivateKeyFromString(String keyStringBase64) throws Exception {
        return getFermatCipher().readPrivateKey(keyStringBase64);
    }

    /**
     * Get a public key object from string key base64 encode
     * @param keyStringBase64
     * @return PublicKey
     * @throws Exception
     */
    public static PublicKey getPublicKeyFromString(String keyStringBase64) throws Exception {
        return getFermatCipher().readPublicKey(keyStringBase64);
    }

    /**
     * Create a private key
     * @return String
     */
    public static String createPrivateKey(){

        try {

            java.security.KeyPair keyPair = getFermatCipher().generateKeyPair();
            return getFermatCipher().encode(keyPair.getPrivate().getEncoded());

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
            return getFermatCipher().createPublicKeyFromPrivateKey(getFermatCipher().readPrivateKey(privateKeyString));

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

            Signature signature = Signature.getInstance(FermatCipher.DIGEST_SHA1);
            signature.initSign(getPrivateKeyFromString(privateKey));
            signature.update(getFermatCipher().decode(message));
            return getFermatCipher().encode(signature.sign());

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

            Signature signer = Signature.getInstance(FermatCipher.DIGEST_SHA1);
            signer.initVerify(getPublicKeyFromString(publicKey));
            signer.update(getFermatCipher().decode(encryptedMessage));
            return (signer.verify(getFermatCipher().decode(signatureString)));

        } catch (Exception e){
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
            return getFermatCipher().encrypt(publicKey, plainMessage);

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
           return getFermatCipher().decrypt(privateKey, encryptedMessage);

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

            java.security.KeyPair keyPair = getFermatCipher().generateKeyPair();
            return new ECCKeyPair(getFermatCipher().encode(keyPair.getPrivate().getEncoded()), getFermatCipher().encode(keyPair.getPublic().getEncoded()));

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
