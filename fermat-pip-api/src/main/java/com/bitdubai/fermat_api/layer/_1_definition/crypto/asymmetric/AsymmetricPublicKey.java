package com.bitdubai.fermat_api.layer._1_definition.crypto.asymmetric;

import java.math.BigInteger;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;

import com.bitdubai.fermat_api.layer._1_definition.crypto.asymmetric.interfaces.Curve;
import com.bitdubai.fermat_api.layer._1_definition.crypto.asymmetric.interfaces.PublicKey;

public class AsymmetricPublicKey extends EllipticCurvePoint implements PublicKey {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8266148738409166071L;

	private static final String ECC_ALGORITHM = "ECIES";
	
	private transient final Curve curve;
	
	public AsymmetricPublicKey(){
		super();
		curve = EllipticCryptographyCurve.getSecP256K1();
	}

	public AsymmetricPublicKey(final BigInteger x, final BigInteger y){
		super(x, y);
		curve = EllipticCryptographyCurve.getSecP256K1();
	}

	public AsymmetricPublicKey(final String uncompressedKey){
		super(new BigInteger(uncompressedKey.substring(2,66),16), new BigInteger(uncompressedKey.substring(66),16));
		curve = EllipticCryptographyCurve.getSecP256K1();
	}

	/*
	 *	implementation of ECPublicKey interface
	 */
	@Override
	public ECPoint getW(){
		return new ECPoint(getX(), getY());
	}
	
	/*
	 *	implementation of PublicKey interface
	 */
	@Override
	public String getAlgorithm() {
		return ECC_ALGORITHM;
	}
	
	@Override
	public byte[] getEncoded() {
		return new BigInteger(toUncompressedString(),16).toByteArray();
	}

	@Override
	public String getFormat() {
		return null;
	}

	/*
	 *	implementation of ECKey interface
	 */ 
	@Override
	public ECParameterSpec getParams(){
		return curve.getParams();
	}
	
	@Override
	public String toString(){
		return toUncompressedString();
	}
}
