package com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.interfaces.Curve;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.interfaces.Point;

import java.math.BigInteger;
import java.security.spec.ECFieldFp;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.EllipticCurve;

public class EllipticCryptographyCurve extends EllipticCurve implements Curve {
	
	private static final int HASH_PRIME_NUMBER_PRODUCT = 6323;
	private static final int HASH_PRIME_NUMBER_ADD = 4951;

	private Point g;
	private int h;
	private BigInteger n;
	private BigInteger p;

	public EllipticCryptographyCurve(ECFieldFp field, BigInteger a, BigInteger b, Point g, int h, BigInteger n) {
		super(field, a, b);
		this.g = g;
		this.h = h;
		this.n = n;
		this.p = field.getP();
	}	

	private static EllipticCryptographyCurve ellipticCryptographyCurve;

	public static EllipticCryptographyCurve getSecP256K1() {
		if (ellipticCryptographyCurve == null)
			ellipticCryptographyCurve = new EllipticCryptographyCurve.SecP256K1();

		return ellipticCryptographyCurve;
	}

	@Override
        public Point getG(){
		return g;		
	}

	@Override
        public int getH(){
		return h;
	}

	@Override
        public BigInteger getN(){
		return n;
	}

	@Override
	public BigInteger getP() {
		return p;
	}

	@Override
	public ECParameterSpec getParams(){
		ECPoint gPoint = new ECPoint(g.getX(), g.getY());
		return new ECParameterSpec(this, gPoint, n, h);
	}
	
	@Override
	public boolean equals(Object o){
		if(o instanceof Curve){
			Curve compare = (Curve) o;
			return getA().equals(compare.getA()) && getB().equals(compare.getB()) && getG().equals(compare.getG())
					&& getH() == compare.getH() && getN().equals(compare.getN()) && getP().equals(compare.getP());
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		int c = 0;
		if(getA() != null)
			c += getA().hashCode();
		if(getB() != null)
			c += getB().hashCode();
		if(getG() != null)
			c += getG().hashCode();
		if(getH() != 0)
			c += getH();
		if(getN() != null)
			c += getN().hashCode();
		if(getP() != null)
			c += getP().hashCode();
		return 	HASH_PRIME_NUMBER_PRODUCT * HASH_PRIME_NUMBER_ADD + c;
	}

	static public class SecP256K1 extends EllipticCryptographyCurve {

		private static final BigInteger A = BigInteger.ZERO;
		private static final BigInteger B = new BigInteger("7",16);
		private static final EllipticCurvePoint G = new EllipticCurvePoint(new BigInteger("55066263022277343669578718895168534326250603453777594175500187360389116729240", 10), 
							new BigInteger("32670510020758816978083085130507043184471273380659243275938904335757337482424", 10));
		private static final int H = 1;
		private static final BigInteger N = new BigInteger("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEBAAEDCE6AF48A03BBFD25E8CD0364141", 16);
		private static final BigInteger P = new BigInteger("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFFC2F", 16);

		public SecP256K1(){
			super(new ECFieldFp(P), A, B, G, H, N);
		}

	}

}
