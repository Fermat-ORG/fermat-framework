package com.bitdubai.fermat_api.layer._1_definition.crypto.asymmetric.interfaces;

import java.math.BigInteger;

public interface Signature {
	public static String SIGNATURE_SEPARATOR = " ";
	public static int SIGNATURE_SEPARATOR_PARTS = 2;
	public BigInteger getR();
	public BigInteger getS();
	public boolean verifyMessageSignature(final BigInteger messageHash, final PublicKey publicKey);
} 
