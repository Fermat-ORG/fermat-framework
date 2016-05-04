/*
 * @#PruebaEnc.java - 2016
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.interfaces.KeyPair;
import com.bitdubai.fermat_api.layer.all_definition.util.OperatingSystemCheck;
import com.google.common.base.Stopwatch;

import org.bouncycastle.util.encoders.Base64;

import java.security.KeyFactory;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Enumeration;
import java.util.Properties;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * The Class <code>com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.PruebaEnc</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 17/04/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class PruebaEnc {

    public static void main(String [] args){

        //AsymmetricCryptography.getFermatCipher();

        try {

            //System.getProperties().list(System.out);

            Stopwatch stopwatch = Stopwatch.createStarted();

            // Generate a 126-bit Digital Signature Algorithm (ECDH) key pair
           // generateKeys("ECDH", 126);

            // Generate a 1024-bit Digital Signature Algorithm (DSA) key pair
          //  generateKeys("DSA", 1024);

            // Generate a 576-bit DH key pair
            //generateKeys("DH", 576);

            // Generate a 1024-bit RSA key pair
            //generateKeys("RSA", 1024);

            //String msj = "Hello world!";
            String msj = "You need to find a way to reduce compilation times when developing source code in our development environment (Android-Studio) to also have efficiency and effectiveness when working on the development and testing of code, there are currently members of fermat that have a high compilation time, the overall average is 2-5 minutes compilation about, some members have more time about 15-30 minutes and if this time is shared among all members of fermat it is a very large idle time when compiling and testing development.";
            //System.out.println("msj = " + msj);

            /*

                 -----------------------------------------------
privateKey : MIGNAgEAMBAGByqGSM49AgEGBSuBBAAKBHYwdAIBAQQgPDcJOlICwNYa6ebip/KszH4qKR1MotOms4U1aI3B1SCgBwYFK4EEAAqhRANCAAQpg8ZpHggJ7F1/gczvL1WQJYJsVI4NqcYFlCm8L//lBKpi9gG4JHn0U+UNJqr+ru+37xqYW2aSNmC7MW8z4oXf
 -----------------------------------------------
publicKey : MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAEeb5mfvncu6xVoGKVzocLBwKb/NstzijZWfKBWxb4F5hIOtp3JqPEZV2k+/wOEQio/Re0SKaFVBmcR9CP+xDUuA==


             */


            final String ppK = "MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEArnTl+ahNqXN5KeXKTe3zJ9EGMbNXq/K1KKHAUgmLZmQF3GKHX03btsbNrtCnHIf7/FhCTHAXvIohnfCBxqp9rwIDAQABAkBPNIa3Jxj/boxbn9cOM5LaiHWJ+hU7XYmvRWTWOjweg1mwVP8nj2iQBSAtpd7gbs2uPBYhTAnV9JWIGj8ylxmVAiEA4a7zNcMsl/3zmTsrqv+njQmhik64DrxRQ4RywR/Er4UCIQDF5E5FnaOst+Tp+s30o0l+IQrnNraBwSGwcBtjQEqMowIhALN4YjbN2Ceir1aaxHY+ymqRTyJiyWIGhgVQTcWg0tPVAiA6t9GZynqkZzRfUPIHouPNrxRDIvLocVwDtGAMBSmZrQIgMuQuP9SWinc4fg8pjyWlwvUmlvjOlYh/BS80prJVlYo=";
            ECCKeyPair eccKeyPair = new ECCKeyPair(ppK);
           // PrivateKey privateKey = AsymmetricCryptography.getPrivateKeyFromString(ppK);
         //   System.out.println("privateKey : " + privateKey);
           // System.out.println("privateKey encode : " + AsymmetricCryptography.getFermatCipher().encode(privateKey.getEncoded()));


            //KeyPair eccKeyPair = AsymmetricCryptography.createNewKeyPair();
     /*      ECCKeyPair eccKeyPair = new ECCKeyPair();
            System.out.println(" ----------------------------------------------- ");
            System.out.println("privateKey : " + eccKeyPair.getPrivateKey());
            System.out.println(" ----------------------------------------------- ");
            System.out.println("publicKey : " + eccKeyPair.getPublicKey());
            System.out.println(" ----------------------------------------------- ");

            String encryptedData = AsymmetricCryptography.encryptMessagePublicKey(msj, eccKeyPair.getPublicKey());

            System.out.println(" ----------------------------------------------- ");
            System.out.println(" encryptedData : " + encryptedData);
            System.out.println(" ----------------------------------------------- ");

            String decryptedData = AsymmetricCryptography.decryptMessagePrivateKey(encryptedData, eccKeyPair.getPrivateKey());
            System.out.println(" decryptedData : " + decryptedData);
            System.out.println("");

            String sing = AsymmetricCryptography.createMessageSignature(eccKeyPair.getPrivateKey(), encryptedData);

            System.out.println(" sing : " + sing);
            System.out.println("");
            System.out.println(" valid sing : " + AsymmetricCryptography.verifyMessageSignature(sing, encryptedData, eccKeyPair.getPublicKey()));

            System.out.println(stopwatch.stop());

        /*     KeyPair keyPair = AsymmetricCryptography.createNewKeyPair();
            String hexPk = AsymmetricCryptography.getFermatCipher().convertHexString(keyPair.getPublic().getEncoded());

            System.out.println("HEX = "+hexPk);


            System.out.println("HEX2 = "+AsymmetricCryptography.getFermatCipher().getPublicKeyFromString(hexPk));


           // AsymmetricCryptography.getFermatCipher().getPublicKeyFromString(hexPk);


           byte [] raw  = keyPair.getPrivate().getEncoded();

            Stopwatch stopwatch1 = Stopwatch.createStarted();
            AsymmetricCryptography.getFermatCipher().convertHexString(raw);
            System.out.println("method 1 = "+stopwatch1.stop());

            Stopwatch stopwatch2 = Stopwatch.createStarted();
            getHex(raw);
            System.out.println("method 2 = "+stopwatch2.stop());

            Stopwatch stopwatch3 = Stopwatch.createStarted();
            bytesToHex(raw);
            System.out.println("method 3 = "+stopwatch3.stop());

            Stopwatch stopwatch4 = Stopwatch.createStarted();
            BaseEncoding.base16().encode(raw);
            System.out.println("method 4 = "+stopwatch4.stop());

            String hex = "970D2F0A34BB179C9BD3A28C21F0CA3BE642E2DDA0C7F4FC5B6432E028451E1FA4CBBFCD90597F72704A66B77F19C102B02EEE5BE057F73458616350DDEAC50D65AC5D0032B3AAEE2083762182F892926E1B05241F5836FE9DBFA588852DD4B1CFACDCE25563939DB3DAB5DF964185A70AE23D546FB4D3FBC0C0C9F9E18D4DE1202155AC3D06E8E77D7228680DDBFD02B7C2EDF345DDC82C25B1E9A80876E6C839426B496560F77A2625FEB67EA796321C2632A312D7954E451C776E9E92BFB99D7D60A344B4A152A9E31B7EA007EEC99DD7D1AF8C35DC1CA263AC05FB5C437F0179E202EF730867ECE314C6E52D253386B553670B01262EE3814CBB5B7D4E14BDB49184689897A48B2B1390189D06696B8F6057AB573FF7B0A1E3B9252A264A4658F51656A43804C9FE8EE60EA83C932651A355F44427F2615B0CFA09A4215979DFDF78FFC90EC5233507E652255080EFBB8D26A06D872608F51ECC9915DF80970863D1CF6A971AF47F2DBAB029D6396704359FB73C73D9C7A4089C5EC79346A149D7386D54F930348BA09CB9A4759EBBEBD2F4EC0F759BB52152E972B697CA9F2EA15848F9CD72CF5F0CACD3F1CF0A2E0885CA775514EEACA5AABE9119AD5A0E4CEBA784E97D4C9C43F2454BD0F69285DB90046751828154BFC11B25873C8F8940D0352D9F98D0451AB927716E914E3745BB5FE72C7CB0377820708CB40E89094DE9853A5883AEAE1892C7BFAAB44BD585043972784F92A1C8847127082172EAA5FE0078EED0B9548E7D35BF0264F936E95D12EEB538548F3A2124F53FE17991686C826F0610D75832655CF0316F84E00002BCE89C1EB983D517A8982BFBD970DFD1DC7F2F5688EAEC6BB3C3726966138788BFA5F4984A372C8376E68A6A8564A375EB3657A44E3B9798361F1FA743A6B90805536149EE43ED66E5FEA4293D1FFE624AD62BE4D2B3ADFDACF58EACDB46967F747D7FF7154F69B80CE7D203596C25A2F578C5937933E6FEA09645CEB42F7B28959311D8AFD3792E43B36746C9FE088A23724B30E4689649DFC2C77935C474FF60051E50AD74CAD31BC62D0F00B2BAF5B591FE17235AC06A3F7D079B61190CDDA3E34DD21386F6655DBEEDD0E368330E8A50EE231FB32C573D75999ADFB20AD5A27BE681FE109A7EB04CB8D5A83839805CBF888E88B69D6B7986C2A9FBFE7387B49D0132D1EE37704AFFA47999A56676071E7D722F98642C9A840BFD5DA56BC0EFEBD6B2E402A168449CA346A70C5401DA9A482766822EF515087EA9E671833D64E897F92EBD370F458AF9CF42CEFAB586774A7F81A1E165516E01EC129DD77356B6454685A9DE4965002A1C746E2EEA325A4A4FAB2078C81FB8ED67A5DAB1A9442F26FC79CB6EF3F7DCC02C62070CA7F29DE882DC79037BA7501107409B20D79224D6A94A89BEA68018BA567D";

            Stopwatch stopwatch1 = Stopwatch.createStarted();
            AsymmetricCryptography.getFermatCipher().convertHexStringToByteArray(hex);
            System.out.println("method 1 = " + stopwatch1.stop());

            Stopwatch stopwatch2 = Stopwatch.createStarted();
            hexStringToByteArray(hex);
            System.out.println("method 2 = " + stopwatch2.stop());

            Stopwatch stopwatch3 = Stopwatch.createStarted();
            BaseEncoding.base16().decode(hex);
            System.out.println("method 3 = "+stopwatch3.stop());*/


        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private static void generateKeys(String keyAlgorithm, int numBits) {

        try {
            // Get the public/private key pair
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance(keyAlgorithm);
            keyGen.initialize(numBits);
            java.security.KeyPair keyPair = keyGen.genKeyPair();
            PrivateKey privateKey = keyPair.getPrivate();
            PublicKey publicKey = keyPair.getPublic();

            System.out.println("\n" + "Generating key/value pair using " + privateKey.getAlgorithm() + " algorithm");

            // Get the bytes of the public and private keys
            byte[] privateKeyBytes = privateKey.getEncoded();
            byte[] publicKeyBytes = publicKey.getEncoded();

            // Get the formats of the encoded bytes
            String formatPrivate = privateKey.getFormat(); // PKCS#8
            String formatPublic = publicKey.getFormat(); // X.509

         //   System.out.println("Private Key : " + AsymmetricCryptography.getFermatCipher().encode((privateKeyBytes)));
            System.out.println("formatPrivate : " + formatPrivate);
           // System.out.println("Public Key : " + AsymmetricCryptography.getFermatCipher().encode((publicKeyBytes)));
            System.out.println("formatPublic : " + formatPublic);

            // The bytes can be converted back to public and private key objects
            KeyFactory keyFactory = KeyFactory.getInstance(keyAlgorithm);
            EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            PrivateKey privateKey2 = keyFactory.generatePrivate(privateKeySpec);

            EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
            PublicKey publicKey2 = keyFactory.generatePublic(publicKeySpec);

            // The original and new keys are the same
            System.out.println("  Are both private keys equal? " + privateKey.equals(privateKey2));
            System.out.println("  Are both public keys equal? " + publicKey.equals(publicKey2));
        } catch (InvalidKeySpecException specException) {
            System.out.println("Exception");
            System.out.println("Invalid Key Spec Exception");
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Exception");
            System.out.println("No such algorithm: " + keyAlgorithm);
        }

    }



}
