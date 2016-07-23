package com.bitdubai.fermat_api.layer.all_definition.crypto.util;

//import org.bouncycastle.crypto.digests.RIPEMD160Digest;

import java.math.BigInteger;
import java.security.MessageDigest;

public class CryptoHasher {

    /*
     * 	Encryption public methods
     */
    public static String performSha256(final String text) {
        String hashed = "";
        try {
            hashed = performSha256(text.getBytes("UTF-8"));
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return hashed;
    }

    public static String performSha256(final BigInteger number) {
        String hashed = "";
        try {
            hashed = performSha256(number.toByteArray());
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return hashed;
    }

    public static String performRipemd160(final String text) {
        String hashed = "";
        try {
            hashed = performRipemd160(text.getBytes("UTF-8"));
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return hashed;
    }

    public static String performRipemd160(final BigInteger number) {
        String hashed = "";
        try {
            hashed = performRipemd160(number.toByteArray());
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return hashed;
    }

    /*
     * 	Encryption private methods
     */
    private static String performSha256(final byte[] data) {
        String hashed = "";
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            if (data.length == 20)
                digest.update(Byte.valueOf("00"));
            byte[] hash = digest.digest(data);
            BigInteger hashValue = new BigInteger(1, hash);
            hashed = String.format("%064X", hashValue);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return hashed;
    }

    private static String performRipemd160(final byte[] data) {
        String hashed = "";
        try {
            //Todo: pasar a otro lado esto
//			RIPEMD160Digest digest = new RIPEMD160Digest();
//			digest.update(data, 0, data.length);
//			byte[] hash = new byte[digest.getDigestSize()];
//			digest.doFinal(hash, 0);
//			BigInteger hashValue = new BigInteger(1,hash);
//			hashed = String.format("%040X", hashValue);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return hashed;
    }


}
