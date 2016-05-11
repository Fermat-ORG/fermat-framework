package com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.interfaces.Curve;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.interfaces.Point;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.interfaces.PrivateKey;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.interfaces.PublicKey;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.interfaces.Signature;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.util.ECMath;

import java.math.BigInteger;

public class AsymmetricSignature implements Signature {

	private static final Curve SECP256K1 = EllipticCryptographyCurve.getSecP256K1();
	
	private final BigInteger r;
	private final BigInteger s;	

	
	public AsymmetricSignature(final PrivateKey privateKey, final BigInteger messageHash, final BigInteger randomNumber){
		r = calculateR(randomNumber);
		s = calculateS(privateKey, messageHash, randomNumber);
	}

	public AsymmetricSignature(final BigInteger r, final BigInteger s){
		this.r = r;
		this.s = s;
	}
	
	public AsymmetricSignature(final String signature){
		String[] values = signature.split(SIGNATURE_SEPARATOR);
		this.r = new BigInteger(values[0], 16);
		this.s = new BigInteger(values[1], 16);
	}
	
	@Override
	public BigInteger getR() {
		return r;
	}

	@Override
	public BigInteger getS() {
		return s;
	}

	@Override
	public boolean verifyMessageSignature(final BigInteger messageHash, final PublicKey publicKey){
		BigInteger w = ECMath.modinv(s, SECP256K1.getN());
		Point p1 = ECMath.multiplyPointScalar(SECP256K1.getG(), messageHash.multiply(w).mod(SECP256K1.getN()), SECP256K1);
		Point p2 = ECMath.multiplyPointScalar(publicKey, r.multiply(w).mod(SECP256K1.getN()), SECP256K1);
		Point p3 = ECMath.addPoint(p1, p2, SECP256K1);
		return r.equals(p3.getX()); 
	}

	@Override
	public String toString(){
		return r.toString(16) + SIGNATURE_SEPARATOR + s.toString(16);
	}


	private BigInteger calculateR(final BigInteger randomNumber){
		Point randomPoint = ECMath.multiplyPointScalar(SECP256K1.getG(), randomNumber, SECP256K1);
		return randomPoint.getX().mod(SECP256K1.getN());
	}

	private BigInteger calculateS(final PrivateKey privateKey, final BigInteger messageHash, final BigInteger randomNumber){
		BigInteger value = messageHash.add(r.multiply(privateKey.getS()));
		value = value.multiply(ECMath.modinv(randomNumber, SECP256K1.getN()));
		value = value.mod(SECP256K1.getN());
		return value;
	}

}
